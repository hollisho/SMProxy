package com.huawei.insa2.util;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;
import org.xml.sax.*;
import java.net.*;

/**
 * 基于XML格式的配置文件读写类。和JDK1.4的Preferences类用法类似。
 * @author InternetTeam3
 * @version 1.01
 */
public class Cfg{

  /** XML解析器工厂。*/
  private static DocumentBuilderFactory factory;

  /** XML解析器。*/
  private static DocumentBuilder builder;

  /** XML头。*/
  private static final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"" +
          System.getProperty("file.encoding") + "\"?>";

  /** 缩进前缀。*/
  private static String indent = "  ";

  /** 该配置内存中数据是否与配置文件内容一致，不一致则为脏。*/
  private boolean isDirty;

  /** 代表该配置文件的文档类。*/
  private Document doc;

  /** 配置根节点。*/
  private Element root;

  /** 配置文件名。*/
  private String file;

  /**
   * 创建配置读取对象。
   * @param url 保存配置信息的XML文件路径。
   */
  public Cfg(String url) throws IOException {
    this(url,false);
  }

  /**
   * 创建配置读取对象。
   * @param url 保存配置信息的XML文件路径。
   * @param create 当配置文件不存在时，是否允许新建文件。
   * @throws IOException 配置文件访问或内容解析异常。
   */
  public Cfg(String url, boolean create) throws IOException {

    //得到配置信息
    if (url==null) {
      throw new IllegalArgumentException("url is null");
    }
    if (url.indexOf(':')>1) {
      this.file = url;
    } else {
      this.file = new File(url).toURI().toString();
    }
    //为了检查参数file的合法性,如传的参数不合法,则抛出异常,调用者对其进行出错处理
    new URL(this.file);  //创建文件对象
    try {
      load(); //读取配置文件
    } catch (FileNotFoundException ex) {
      if(!create) { //文件不存在，且不允许新建
        throw ex;
      } else { //文件不存在，但允许新建
        loadXMLParser(); //加载XML解析器
        doc = builder.newDocument(); //创建空文档对象
        root = doc.createElement("config");
        doc.appendChild(root); //增加根节点
        isDirty = true;
        flush(); //保存到文件
        return;
      }
    }
  }

  /**
   * 取得某节点的一组子节点值。
   * @param key 父节点的路径。
   */
  public Args getArgs(String key) {
    Map args = new HashMap();
    String[] children = childrenNames(key);
    for (int i=0;i<children.length;i++) {
      args.put(children[i],get(key+'/'+children[i],null));
    }
    return new Args(args);
  }

  /**
   * 输出缩进空格。
   * @param pw 输出目的地。
   * @param level 缩进深度。
   */
  private static void writeIndent(PrintWriter pw,int level) {
    for (int i=0;i<level;i++) {
      pw.print(indent);
    }
  }

  /**
   * 以XML格式递归输出一个节点。
   * @param node 输出起始节点。
   * @param pw 输出目的地。
   * @param 递归调用的深度标记，请输入0。
   */
  private static void writeNode(Node node, PrintWriter pw, int deep) {
    switch (node.getNodeType()) {
      case Node.COMMENT_NODE: //注释节点
        writeIndent(pw,deep);
        pw.print("<!--");
        pw.print(node.getNodeValue());
        pw.println("-->");
        return;
      case Node.TEXT_NODE: //文本节点
        String value = node.getNodeValue().trim(); //文本trim防止破坏缩进格式
        if (value.length()==0) {
          return;
        }
        writeIndent(pw,deep);
        for (int i=0;i<value.length();i++) {
          char c = value.charAt(i);
          switch(c) {
            case '<':
              pw.print("&lt;");
              break;
            case '>':
              pw.print("&lt;");
              break;
            case '&':
              pw.print("&amp;");
              break;
            case '\'':
              pw.print("&apos;");
              break;
            case '\"':
              pw.print("&quot;");
              break;
            default:
              pw.print(c);
          }
        }
        pw.println();
        return;
      case Node.ELEMENT_NODE: //标记节点
        if (!node.hasChildNodes()) {
          return;
        }
        for (int i=0;i<deep;i++) {
          pw.print(indent);
        }
        String nodeName = node.getNodeName();
        pw.print('<');
        pw.print(nodeName);

        //输出属性
        NamedNodeMap nnm = node.getAttributes();
        if (nnm!=null) {
          for (int i=0; i<nnm.getLength(); i++) {
            Node attr = nnm.item(i);
            pw.print(' ');
            pw.print(attr.getNodeName());
            pw.print("=\"");
            pw.print(attr.getNodeValue());
            pw.print('\"');
          }
        }

        //输出子节点
        if (node.hasChildNodes()) {
          NodeList children = node.getChildNodes();
          if (children.getLength()==0) {
            pw.print('<');
            pw.print(nodeName);
            pw.println("/>");
            return;
          }
          if (children.getLength()==1) {
            Node n = children.item(0);
            if(n.getNodeType()==Node.TEXT_NODE) {
              String v = n.getNodeValue();
              if (v!=null) {
                v = v.trim();
              }
              if (v==null||v.length()==0) {
                pw.println(" />");
                return;
              } else {
                pw.print('>');
                pw.print(v);
                pw.print("</");
                pw.print(nodeName);
                pw.println('>');
                return;
              }
            }
          }
          pw.println(">");
          for (int i=0;i<children.getLength();i++) {
            writeNode(children.item(i),pw,deep+1);
          }
          for (int i=0;i<deep;i++) {
            pw.print(indent);
          }
          pw.print("</");
          pw.print(nodeName);
          pw.println(">");
        } else {
          pw.println("/>");
        }
        return;
      case Node.DOCUMENT_NODE: //文档节点
        pw.println(XML_HEAD);
        NodeList nl= node.getChildNodes();
        for (int i=0;i<nl.getLength();i++) {
          writeNode(nl.item(i),pw,0);
        }
        return;
    }
  }

  /**
   * 根据key指定的关键字查找节点，在查找的过程中顺便将不存在的节点创建出来。
   * @param key 所查找节点的关键字。
   * @return 返回查到的节点，没查到则返回null。
   */
  private Node findNode(String key) {
    Node ancestor = root;
    for (StringTokenizer st = new StringTokenizer(key,"/");
         st.hasMoreTokens();) {
      String nodeName = st.nextToken();
      NodeList nl = ancestor.getChildNodes();
      for (int i=0; i<nl.getLength(); i++) {
        Node n = nl.item(i);
        if (nodeName.equals(n.getNodeName())) {
          ancestor = n;
          if (!st.hasMoreTokens()) { //到达key的最低一级了
            return n;
          }
          break;
        }
      }
    }
    return null;
  }

  /**
   * 根据key指定的关键字创建节点，将会把key代表的整个路径上的节点都创建出来。
   * @param key 所查找节点的关键字。
   * @return 返回最底层节点，即使已经存在，没有创建新节点。
   */
  private Node createNode(String key) {
    Node ancestor = root;
    token:
    for (StringTokenizer st = new StringTokenizer(key,"/");
         st.hasMoreTokens();) {
      String nodeName = st.nextToken();
      NodeList nl = ancestor.getChildNodes();
      for (int i=0; i<nl.getLength(); i++) {
        Node n = nl.item(i);
        if (nodeName.equals(n.getNodeName())) { //该级子节点存在则继续找下一级子节点
          ancestor = n;
          if (st.hasMoreTokens()) {
            continue token;
          } else {
            return ancestor;
          }
        }
      }

      //该级子节点不存在
      for(;;){ //死循环
        Node n = doc.createElement(nodeName);
        ancestor.appendChild(n);
        ancestor = n;
        if(!st.hasMoreTokens()) {
          return ancestor;
        }
        nodeName = st.nextToken();
      }
    }
    return null; //不可能执行到这里
  }

  /**
   * 根据key指定的关键字查找节点，在查找的过程中顺便将不存在的节点创建出来。
   * @param ancestor 以该节点为祖先开始查找。
   * @param key 所查找节点的关键字。
   * @return 返回查到的节点，没查到则返回null。
   */
  private Node createNode(Node ancestor, String key) {

    searchToken: //用来跳出两层循环用的标记
    for (StringTokenizer st = new StringTokenizer(key,"/");
         st.hasMoreTokens();) {
      String nodeName = st.nextToken();
      NodeList nl = ancestor.getChildNodes();
      for (int i=0; i<nl.getLength(); i++) {
        if (nodeName.equals(nl.item(i).getNodeName())) {
          ancestor = nl.item(i);
          continue searchToken;
        }
      }
      return null;
    }
    return ancestor;
  }

  /**
   * 取名字为key的节点的值。如果该节点存在，但无内容则返回空字符串：""
   * @param key 和该关键字关联的配置项的值将被返回。
   * @param def 若key对应的配置项不存在，则返回该值。
   * @exception NullPointerException key值为null。
   */
  public String get(String key, String def) {
    if (key == null) {
      throw new NullPointerException("parameter key is null");
    }

    Node node = findNode(key);
    if (node==null) { //节点不存在返回空
      return def;
    }
    NodeList nl = node.getChildNodes();
    for (int i=0;i<nl.getLength();i++) {
      if (nl.item(i).getNodeType()==Node.TEXT_NODE) {
        return nl.item(i).getNodeValue().trim();
      }
    }
    node.appendChild(doc.createTextNode(def));
    return def;
  }

  /**
   * 设名字为key的节点的值。
   * @param key 设值节点对象的名字。
   * @param value 所设的值。
   * @exception NullPointerException 传入的key或value为null。
   */
  public void put(String key, String value) {
    if (key == null){
      throw new NullPointerException("parameter key is null");
    }
    if (value == null){
      throw new NullPointerException("parameter value is null");
    }
    value = value.trim();
    Node node = createNode(key);

    //node节点的第一个文本子节点(不包括trim后为空的)放置该节点的值
    NodeList nl = node.getChildNodes();
    for (int i=0;i<nl.getLength();i++) {
      Node child = nl.item(i);
      if (child.getNodeType()==Node.TEXT_NODE) { //遇到第一个文本子节点
        String childValue = child.getNodeValue().trim();
        if (childValue.length()==0) {
          continue;
        }
        //put的值和原来一样，直接返回即可
        if (childValue.equals(value)) {
          return;
        } else {
          child.setNodeValue(value);
          isDirty = true;
          return;
        }
      }
    }

    //没有trim后还有内容的文本子节点
    if (nl.getLength()==0) { //节点为空
      node.appendChild(doc.createTextNode(value));
    } else { //节点非空
      Node f = node.getFirstChild();
      if (f.getNodeType()==Node.TEXT_NODE) {
        f.setNodeValue(value);
      } else {
        node.insertBefore(doc.createTextNode(value),f);
      }
    }
    isDirty = true; //修改后,脏标记设为真
  }

  /**
   * 取名字为key的节点的布尔值。
   * @param key 取值节点对象的名字。
   * @param def 若没有取到，则返回该值。
   */
  public boolean getBoolean(String key, boolean def) {
    String str = String.valueOf(def);//把布尔型def变成字符串类str
    boolean result;
    String resstr = get(key,str);
    Boolean resboolean = Boolean.valueOf(resstr);
    result = resboolean.booleanValue();
    return result;
  }
  /**
   * 取名字为key的节点的整型值。
   * @param key 取值节点对象的名字。
   * @param def 若没有取到，则返回该值。
   */
  public int getInt(String key,int def) {
    int result;
    String str = String.valueOf(def);//把整型def变成字符串类str
    String resstr = get(key,str);
    try{
      result = Integer.parseInt(resstr);//把字符串类resstr变成整型result
    }catch(NumberFormatException e){
      return def;
    }
    return result;
  }
  /**
   * 取名字为key的节点的浮点值。
   * @param key 取值节点对象的名字。
   * @param def 若没有取到，则返回该值。
   */
  public float getFloat(String key,float def) {
    float result;
    String str = String.valueOf(def);//把浮点型def变成字符串类str
    String resstr = get(key,str);
    try{
      result = Float.parseFloat(resstr);//把字符串类resstr变成浮点型result
    }catch(NumberFormatException e){
      return def;
    }
    return result;
  }
  /**
   * 取名字为key的节点的双精度值。
   * @param key 取值节点对象的名字。
   * @param def 若没有取到，则返回该值。
   */
  public double getDouble(String key,double def) {
    double result;
    String str = String.valueOf(def);//把double型def变成字符串类str
    String resstr = get(key,str);
    try{
      result = Double.parseDouble(resstr);//把字符串类resstr变成double型result
    }catch(NumberFormatException e){
      return def;
    }
    return result;
  }
  /**
   * 取名字为key的节点的长整型值。
   * @param key 取值节点对象的名字。
   * @param def 若没有取到，则返回该值。
   */
  public long getLong(String key,long def) {
    long result;
    String str = String.valueOf(def);//把long型def变成字符串类str
    String resstr = get(key,str);
    try{
      result = Long.parseLong(resstr);//把字符串类resstr变成long型result
    }catch(NumberFormatException e){
      return def;
    }
    return result;
  }
  /**
   * 取名字为key的节点的字节数组值。
   * @param key 取值节点对象的名字。
   * @param def 若没有取到，则返回该值。
   */
  public byte[] getByteArray(String key,byte[] def) {
    byte[] result;
    String str = new String(def);//把byte[]型def变成字符串类str
    String resstr = get(key,str);
    result = resstr.getBytes();//把字符串类resstr变成byte[]型result
    return result;
  }

  /**
   * 设名字为key的节点的布尔型值。
   * @param key 设值节点对象的名字。
   * @param value 所设的值。
   */
  public void putBoolean(String key, boolean value) {
    String str = String.valueOf(value); //将boolean型转换成String类型
    try {
      put(key,str);
    }catch(RuntimeException e){
      throw e;
    }
  }
  /**
   * 设名字为key的节点的整型值。
   * @param key 设值节点对象的名字。
   * @param value 所设的值。
   */
  public void putInt(String key,int value) {
    String str = String.valueOf(value); //将int型转换成String类型
    try {
      put(key,str);
    }catch(RuntimeException e){
      throw e;
    }
  }
  /**
   * 设名字为key的节点的浮点型值。
   * @param key 设值节点对象的名字。
   * @param value 所设的值。
   */
  public void putFloat(String key,float value) {
    String str = String.valueOf(value); //将float型转换成String类型
    try {
      put(key,str);
    }catch(RuntimeException e){
      throw e;
    }
  }
  /**
   * 设名字为key的节点的双精度型值。
   * @param key 设值节点对象的名字。
   * @param value 所设的值。
   */
  public void putDouble(String key,double value) {
    String str = String.valueOf(value); //将double型转换成String类型
    try {
      put(key,str);
    }catch(RuntimeException e){
      throw e;
    }
  }
  /**
   * 设名字为key的节点的长整型值。
   * @param key 设值节点对象的名字。
   * @param value 所设的值。
   */
  public void putLong(String key,long value) {
    String str = String.valueOf(value); //将long型转换成String类型
    try {
      put(key,str);
    }catch(RuntimeException e){
      throw e;
    }
  }
  /**
   * 设名字为key的节点的字节数组值。
   * @param key 设值节点对象的名字。
   * @param value 所设的值。
   */
  public void putByteArray(String key,byte[] value) {
    put(key,Base64.encode(value));
  }

  /**
   * 删除某个节点。
   * @param key 该节点的名称。
   */
  public void removeNode(String key) {
    Node node = findNode(key);//查找该节点
    if (node==null) { //该节点不存在
      return;
    }
    Node parentnode = node.getParentNode();  //取父节点
    if (parentnode != null){
      parentnode.removeChild(node);   //删掉该节点
      isDirty = true;  //脏标志设为真
    }
  }

  /**
   * 清理某节点的所有子节点。
   * @param key 该节点的名称。
   */
  public void clear(String key) {
    Node node = findNode(key);//查找该节点
    if (node == null)//未找到就抛出异常
      throw new RuntimeException("InvalidName");
    Node lastnode = null;
    //依次把最后一个子节点清除
    while (node.hasChildNodes()) {
      lastnode = node.getLastChild();
      node.removeChild(lastnode);
    }
    //若有子节点被清除,脏标志设为真
    if (lastnode != null)
      isDirty = true;
  }

  /**
   * 查找某节点下的所有子节点的名字。
   * @param key 被查节点的名字。
   * @return String[] 子节点名字的字符串数组，如果不存在key对应的子节点，
   * 则返回长度为0的空数组。
   */
  public String[] childrenNames(String key) {
    Node node = findNode(key);//查找key节点
    if (node==null) {
      return new String[0];
    }
    NodeList nl = node.getChildNodes();
    LinkedList list = new LinkedList();
    for (int i=0;i<nl.getLength();i++) {
      Node child = nl.item(i);
      if (child.getNodeType()==Node.ELEMENT_NODE && child.hasChildNodes()) {
        list.add(child.getNodeName());
      }
    }
    String[] ret = new String[list.size()];
    for (int i=0;i<ret.length;i++) {
      ret[i] = (String)list.get(i);
    }
    return ret;
  }

  /**
   * 判断某个节点是否存在。
   * @param key 被查节点的全名。
   * @return true 节点存在。
   *         false 节点不存在。
   */
  public boolean nodeExist(String key) {
    Node theNode = this.findNode(key);
    if (theNode == null) {
      return false;
    } else
    if ( theNode.hasChildNodes()) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 加载XML解析器驱动程序。
   */
  private void loadXMLParser() throws IOException {

    //如果尚未加载过，则加载XML解析器驱动程序
    if (builder == null) {
      try {
        factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        builder = factory.newDocumentBuilder();
      } catch (ParserConfigurationException ex) {
        throw new IOException("XML Parser load error:" +
                ex.getLocalizedMessage());
      }
    }
  }

  /**
   * 重新读取XML文件。
   */
  public void load() throws IOException{

    loadXMLParser();

    //解析配置文件
    try {
      InputSource is = new InputSource(
              new InputStreamReader(new URL(file).openStream()));
      is.setEncoding(System.getProperty("file.encoding"));
      this.doc = builder.parse(is);
    } catch (SAXException ex) {
      ex.printStackTrace();
      String message = ex.getMessage();
      Exception e = ex.getException();
      if (e!=null) {
        message += "embedded exception:" + e;
      }
      throw new IOException("XML file parse error:" + message);
    }
    root = doc.getDocumentElement();
    if (!"config".equals(root.getNodeName())) {
      throw new IOException("Config file format error, " +
              "root node must be <config>");
    }
  }

  /**
   * 把配置写进配置文件。
   */
  public void flush() throws IOException{
    if(isDirty) {  //如果配置发生过修改
      String proc = new URL(this.file).getProtocol().toLowerCase();
      if (!proc.equalsIgnoreCase("file")) {
        throw new java.lang.UnsupportedOperationException("Unsupport write config URL on protocal " + proc);
      }
      String fileName = new URL(this.file).getPath();
      Debug.dump(new URL(this.file).getPath());
      Debug.dump(new URL(this.file).getFile());
      BufferedOutputStream bos = new BufferedOutputStream
              (new FileOutputStream(fileName),2048);
      PrintWriter pw = new PrintWriter(bos);
      writeNode(doc,pw,0);//输出整个文档
      pw.flush();
      pw.close();
      isDirty = false; //脏标志设为假
    }
  }

  /**
   * 将字符串中的大于号、小于号等特殊字符做转义处理。
   * @param str 转义前字符串。
   * @return 转义后的字符串。
   */
  private String change(String str) throws IOException{
    if (str.indexOf('&')!=-1 || str.indexOf('<')!=-1 || str.indexOf('>')!=-1) {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ByteArrayInputStream bis = new ByteArrayInputStream(str.getBytes());
      byte temp;
      byte[] ba1 = {'&','a','m','p',';'};
      byte[] ba2 = {'&','l','t',';'};
      byte[] ba3 = {'&','g','t',';'};
      while( (temp = (byte)bis.read()) != -1 ) {
        switch (temp){
          case '&':
            bos.write(ba1);
            break;
          case '<':
            bos.write(ba2);
            break;
          case '>':
            bos.write(ba3);
            break;
          default:
            bos.write(temp);
        }
      }
      return bos.toString();
    }
    return str; //不含有需转义的字符，则直接将原字符串返回
  }

  /**
   * 测试入口。
   */
  public static void main(String[] args) throws Exception {
    Cfg c = new Cfg("testcfg.xml",true);
    c.put("a/b","汉字");
    c.put("c","");
    c.put("a","avalusaaaaaaaaae");
    c.flush();
    c = new Cfg("testcfg.xml",true);
    System.out.println("Config file content:");
    BufferedReader in = new BufferedReader(new FileReader("testcfg.xml"));
    String line;
    while((line=in.readLine())!=null) {
      System.out.println(line);
    }
  }
}
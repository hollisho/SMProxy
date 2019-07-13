package com.huawei.insa2.util;

import java.io.*;
import java.util.*;
import java.text.*;
import java.lang.reflect.*;

/**
 * 调试工具类。该类提供两个主要的静态方法：
 * <OL><LI>myAssert(boolean 条件);断言，用来确保本应成立的条件确实成立，防止
 * 错误扩散。
 * <LI>dump(任何数据类型,递归深度);输出一个变量的内容，后一个参数可省，
 * 缺省值为3。该方法可以输出任何数据类型的变量的内容，包括基本数据类型、数
 * 组和对象。对一些常用对象，如Vector等，有专门的输出格式。其他对象将输出其
 * 成员变量，包括私有变量和静态变量。输出过程会一直递归，递归层次由deep参数
 * 来控制，如果递归层次超过限制值，则直接输出toString()的内容，如果递归出现
 * 循环，则在发现重复引用处停止递归。输出信息除对象的内容外还包括输出时间、
 * 调用dump函数的代码所在源文件，行号，若是对象还包括该对象的地址(注：虚拟
 * 机内部编址，非物理地址，可用来判断两个变量是否是同一个对象的引用)。</OL>
 * 该类未引用其他类，可单独编译。该类只供调试使用，未做性能优化。
 * @author InternetTeam3
 * @version 1.0
 */
public class Debug {

  /**
   * 构造方法私有，禁止实例化。
   */
  private Debug() {}

  /**
   * 断言，condition条件一定成立，否则断言失败，断言失败时抛出异常，断言使用在
   * 判断在任何情况下都不应该出现的错误，如果断言失败表示程序有BUG，在系统发布
   * 前必须保证所有断言都永远成立。不应用断言来做为程序运行期间出错处理的手段。
   * @param condition 断言条件。
   * @param message 断言失败时输出的消息。
   */
  public static final void myAssert(boolean condition) {
    if(!condition) throw new AssertFailed();
  }

//---------------dump方法，用法：Debug.dump(<任意数据类型>);-------------------

  /** dump分层结构时每级缩进固定为4个空格。*/
  private static String indentString = "    ";

  /** 当前系统行分隔符。*/
  private static final String lineSeparator =
      System.getProperty("line.separator");

  /**
   * dump()方法输出数据的目的流，缺省指向标准错误输出设备上。
   * 可以通过setDumpStream(OutputStream)方法修改,也可以直接对该对象赋值。
   */
  public static PrintWriter out = new PrintWriter(System.out);

  /**
   * 设置dump信息的输出目的流，此后调用dump都将输出到该流中。
   * @param os 输出流。
   */
  public static void setDumpStream(OutputStream os) {
    out = new PrintWriter(os);
  }

  /**
   * 设置dump信息的输出目的流，此后调用dump都将输出到该流中。
   * @param w 一个Writer对象。
   */
  public static void setDumpStream(Writer w) {
    out = new PrintWriter(w);
  }

  /**
   * 设置递归缩进时填补的字符串。缺省为4个空格。
   * @param indent 缩进时填补的字符串。
   */
  public static void setDumpIndent(String indent) {
    indentString = indent;
  }

  /**
   * 获取递归缩进时填补的字符串。
   * @return 缩进时填补的字符串。
   */
  public static String getDumpIndent() {
    return indentString;
  }

  /**
   * 输出空行。
   */
  public static final void dump() {
    out.println(dumpHead());
    out.flush();
  }

  /**
   * 输出整数。
   * @param i 要输出的整数。
   */
  public static final void dump(int i) {
    out.println(dumpHead() + i);
    out.flush();
  }

  /**
   * 输出长整数。
   * @param l 要输出的长整数。
   */
  public static final void dump(long l) {
    out.println(dumpHead() + l);
    out.flush();
  }

  /**
   * 输出浮点数。
   * @param f 要输出的浮点数。
   */
  public static final void dump(float f) {
    out.println(dumpHead() + f);
    out.flush();
  }

  /**
   * 输出双精度浮点数。
   * @param d 要输出的双精度浮点数。
   */
  public static final void dump(double d) {
    out.println(dumpHead() + d);
    out.flush();
  }

  /**
   * 输出布尔值。
   * @param d 要输出的布尔值。
   */
  public static final void dump(boolean b) {
    out.println(dumpHead() + b);
    out.flush();
  }

  /**
   * 输出一个字符ch。
   * @param ch 要输出的字符。
   */
  public static final void dump(char ch) {
    out.println(dumpHead() + ch);
    out.flush();
  }

  /**
   * 输出字节数组中的一部分数据。
   * @param data 待输出数据所在字节数组。
   * @param offset 待输出数据开始位置。
   * @param length 待输出数据长度。
   */
  public static final void dump(byte[] data,int offset,int length) {
    dump(dumpHead(),data,offset,length);
  }

  /**
   * 输出字节数组完整内容。
   * @param data 待输出字节数组。
   */
  public static final void dump(byte[] data){
    dump(dumpHead(),data);
  }

  /**
   * 输出一个对象（可以是任何对象、对象数组）
   * @param obj 要输出的对象。
   */
  public static final void dump(Object obj) {
    dump(dumpHead(),3,new Vector(),obj);
  }

  /**
   * 输出一个对象（可以是任何对象、对象数组），带前缀
   * @param obj 要输出的对象。
   * @param prefix 输出信息的前缀。
   */
  public static final void dump(Object obj,String prefix) {
    dump(prefix + dumpHead(),3,new Vector(),obj);
  }

  /**
   * 输出一个对象（可以是任何对象、对象数组）
   * @param obj 要输出的对象。
   * @param depth 递归深度。
   */
  public static final void dump(Object obj,int depth) {
    dump(dumpHead(),depth,new Vector(),obj);
  }

  //------------------------ 以下是私有方法，不对外开放 -----------------------

  /**
   * 输出向量内容。
   * @param prefix 输出前缀。
   * @param depth 最大dump深度。
   * @param v 输出的向量。
   */
  private static final void dump(String prefix,int depth,Vector checkCircuit,Vector v){
    if (v==null) {
      dump(prefix,"null");
      return;
    }
    dumpBegin(prefix,checkCircuit,v);
    for (int i=0;i<v.size();i++) {
      Object item = v.elementAt(i);
      StringBuffer itemPrefix = new StringBuffer();
      itemPrefix.append(indent(prefix));
      itemPrefix.append('[');
      itemPrefix.append(i);
      itemPrefix.append("] ");
      itemPrefix.append(formatClassName(item.getClass(),item));
      itemPrefix.append(" @");
      itemPrefix.append(System.identityHashCode(item));
      dump(itemPrefix.toString(),depth,checkCircuit,item);
    }
    dumpEnd(prefix,checkCircuit,v);
  }

  /**
   * 输出Servlet的request请求中的参数。
   * @param prefix 输出前缀。
   * @param request 要输出的请求。
   */
  private static final void dumpServletRequest(String prefix,Object request) {
    try {
      if (request == null) {
        dump(prefix,"null");
        return;
      }
      dumpBegin(prefix,new Vector(),request);
      Class c = request.getClass();
      Method m1 = null;
      m1 = c.getMethod("getParameterNames",new Class[]{});

      for (Enumeration e = (Enumeration) m1.invoke(request,new Object[]{});
           e.hasMoreElements();) {
        String name = e.nextElement().toString();
        Method m2 = c.getMethod("getParameterValues",new Class[]{String.class});
        String[] values = (String[]) m2.invoke(request,new Object[]{name});
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<values.length; i++) {
          sb.append(values[i]);
          if (i!=values.length -1) {
            sb.append(" ; ");
          }
        }
        dump(indent(prefix),name + " = " + sb);
      }
      dumpEnd(prefix,new Vector(),request);
    } catch (Exception ex) {
      ex.printStackTrace(out);
    }
  }

  /**
   * 输出符合Enumeration接口的对象的内容
   * @param prefix 输出前缀。
   * @param depth 最大dump深度。
   * @param e 输出的枚举对象。
   */
  private static final void dump(String prefix,int depth,Vector checkCircuit,Enumeration e){
    if (e==null) {
      dump(prefix,"null");
      return;
    }
    dumpBegin(prefix,checkCircuit,e);
    int i=0;
    while (e.hasMoreElements()) {
      dump(indent(prefix) + '[' + (i++) + "] ",depth,checkCircuit,e.nextElement());
    }
    dumpEnd(prefix,checkCircuit,e);
  }

  /**
   * 输出异常信息。
   * @param prefix 输出前缀。
   * @param t 待输出的异常。
   */
  private static final void dump (String prefix,Throwable t) {
    if (t==null) {
      dump(prefix,"null");
      return;
    }
    dumpBegin(prefix,new Vector(),t);
    t.printStackTrace(out);
    dumpEnd(prefix,new Vector(),t);
  }

  /**
   * 输出字节数组片段。
   * @param prefix 输出前缀。
   * @param data 输出的字节数组。
   * @param offset 输出开始的偏移地址。
   * @param length 长度。
   */
  private static final void dump(String prefix,byte[]data,int offset,int length) {
    if (data ==null) {
      dump(prefix,"null");
      return;
    }
    if ( offset<0||data.length<offset+length) {
      dump(prefix,"IndexOutOfBounds:data.length=" + data.length + " offset=" +
                                           offset + " length=" + length);
      return;
    }
    dumpBegin(prefix,new Vector(),data);
    int end = offset + length;
    dump(indent(prefix),"[HEX]  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f | 0123456789abcdef");
    dump(indent(prefix),"------------------------------------------------------------------------");
    for (int i=offset; i<end; i+=16) {
      byte[] row = new byte[] { 0x30,0x30,0x30,0x30,0x3A,0x20,
        0x30,0x30,0x20,0x30,0x30,0x20,0x30,0x30,0x20,0x30,0x30,0x20,
        0x30,0x30,0x20,0x30,0x30,0x20,0x30,0x30,0x20,0x30,0x30,0x20,
        0x30,0x30,0x20,0x30,0x30,0x20,0x30,0x30,0x20,0x30,0x30,0x20,
        0x30,0x30,0x20,0x30,0x30,0x20,0x30,0x30,0x20,0x30,0x30,0x20,
        0x7C,0x20,0x2E,0x2E,0x2E,0x2E,0x2E,0x2E,0x2E,0x2E,0x2E,0x2E,
        0x2E,0x2E,0x2E,0x2E,0x2E,0x2E
      };
      setHex(row,3,i);
      for (int j=i; j<i+16; j++) {
        if (j<end) {
          int b = data[j];
          if (b<0) b += 256;
          setHex(row,7+(j-i)*3,b);
          if (b>=32 && b<127) {
            row[56+j-i]=(byte)b;
          }
        }
        else {
          row[6+(j-i)*3]=(byte)' ';
          row[7+(j-i)*3]=(byte)' ';
          row[56+(j-i)] =(byte)' ';
        }
      }
      dump(indent(prefix),new String(row));
    }
    dumpEnd(prefix,new Vector(),data);
  }

  /**
   * 输出字节数组完整内容。
   * @param prefix 输出前缀
   * @param b 输出的字节数组。
   */
  private static final void dump(String prefix, byte[] b) {
    dump(prefix, b, 0, b.length>512?512:b.length);
  }

  /**
   * 输出哈希表内容。
   * @param prefix 输出前缀。
   * @param depth 最大dump深度。
   * @param map 输出的Map对象。
   */
  private static final void dump(String prefix,int depth,Vector checkCircuit,Map map){
    if (map==null) {
      dump(prefix,"null");
    }
    dumpBegin(prefix,checkCircuit,map);
    for (Iterator i = map.keySet().iterator();i.hasNext();){
      Object key = i.next();
      Object value = map.get(key);
      if (value instanceof String) {
        dump(indent(prefix),key.toString() + " = " + value);
      } else {
        dump(indent(prefix) + key.toString() + " = ",depth,checkCircuit,map.get(key));
      }
    }
    dumpEnd(prefix,checkCircuit,map);
  }

  /**
   * 输出字符串。
   * @param prefix 输出前缀。
   * @param str 输出的字符串。
   */
  private static final void dump(String prefix,String str) {
    out.println(prefix+str);
    out.flush();
  }

  /**
   * 输出对象数组内容。
   * @param prefix 输出前缀。
   * @param depth 最大dump深度。
   * @param objs 输出的对象数组。
   */
  private static final void dump(String prefix,int depth,Vector checkCircuit,Object[] objs) {
    if (objs==null) {
      dump(prefix,"null");
      return;
    }
    dumpBegin(prefix,checkCircuit,objs);
    for (int i=0;i<objs.length;i++) {
      dump(indent(prefix) + '[' + i + "] ",depth,checkCircuit,objs[i]);
    }
    dumpEnd(prefix,checkCircuit,objs);
  }

  /**
   * 对一般对象的dump行为是把传入对象的成员变量(包括私有)全部显示出来。
   * @param prefix 输出前缀。
   * @param depth 最大dump深度。
   * @param checkCircuit 保存递归层次中经过的所有对象的地址，防止循环递归。
   * @param obj需要dump的对象。
   */
  private static void dump(String prefix,int depth,Vector checkCircuit,Object obj) {
    if (obj==null) {
      dump(prefix,"null");
      return;
    }
    try {
      //对于这些常用数据类型，有他们自己的dump方法。
      if ((obj instanceof String)    || (obj instanceof Number) ||
          (obj instanceof Character) || (obj instanceof Boolean)) {
        dump(prefix,obj.toString());
        return;
      } else if(checkCircuit.contains(new Integer(System.identityHashCode(obj)))) {
        StringBuffer sb = new StringBuffer();
        sb.append(formatClassName(obj.getClass(),obj));
        sb.append(" @");
        sb.append(System.identityHashCode(obj));
        sb.append(' ');
        dump(prefix," {Circle recursion!}");
        return;
      } else if(getDepth(prefix)>depth) {//递归层数太多
        String str = formatClassName(obj.getClass(),obj) +
                     " @" + System.identityHashCode(obj);
        if (prefix.trim().endsWith(str.trim())) {
          str = "";
        }
        String toStr;
        try {
          toStr = obj.toString();
          if (toStr.indexOf((int)'@')>0) {
            toStr = " {Stack overflow!}";
          }
        } catch (StackOverflowError t) {
          toStr = " {Stack overflow!}";
        }
        dump(prefix,str + toStr);
        return;
      } else if (obj instanceof Vector) {
        dump(prefix,depth,checkCircuit,(Vector)obj);
        return;
      } else if (obj instanceof Map) {
        dump(prefix,depth,checkCircuit,(Map)obj);
        return;
      } else if (obj instanceof Enumeration) {
        dump(prefix,depth,checkCircuit,(Enumeration)obj);
        return;
      } else if (obj instanceof Object[]) {
        dump(prefix,depth,checkCircuit,(Object[]) obj);
        return;
      } else if (obj instanceof Throwable) {
        dump(prefix,(Throwable) obj);
        return;
      } else if (obj instanceof byte[]) {
        dump(prefix,(byte[]) obj);
        return;
      } else if (obj.getClass().isArray()) { //基本数据类型的数组
        int len = Array.getLength(obj);
        dumpBegin(prefix,checkCircuit,obj);
        StringBuffer content = new StringBuffer();
        for (int i=0;i<len;i++) {
          content.append(fixLength(Array.get(obj,i).toString(),4));
          if (i%8==7 && i<len-1) {
            content.append(lineSeparator + indent(prefix));
          }
        }
        dump(indent(prefix),content.toString());
        dumpEnd(prefix,checkCircuit,obj);
        return;
      } else if (Class.forName("javax.servlet.ServletRequest").isInstance(obj)) {
        dumpServletRequest(prefix,obj);
        return;
      }
    } catch (ClassNotFoundException ex) {
      //ex.printStackTrace(out);
    }

    //其他对象
    dumpBegin(prefix,checkCircuit,obj);
    Class c = obj.getClass();
    Field[] f;
    while (c!=null) {
      try {
        f = c.getDeclaredFields();
      } catch (SecurityException ex2) { //如果没有执行getFields()的权限
        dump(indent(prefix),"Can't dump object member for security reason.");
        return;
      }
      //输出成员变量
      for (int i=0;i<f.length;i++) {
        String m = Modifier.toString(f[i].getModifiers()); //修饰符
        if (m.indexOf("static")>0)
          continue;
        String n = f[i].getName(); //变量名
        Object v = "[unkonwn]";    //变量值
        try { //消除访问修饰符的限制
          f[i].setAccessible(true);
        } catch (SecurityException ex) {}
        try {
          v = f[i].get(obj); //变量值
          if (v!=null) {
            if (v instanceof String) { //给字符串加引号
              v = "\"" + v + '\"';
            } else if (v instanceof Character) { //给字符加单引号
              char cv = ((Character)v).charValue();
              if (cv<' ') {
                StringBuffer sbv = new StringBuffer();
                sbv.append("\\u");
                sbv.append(Integer.toHexString((int)cv));
                while(sbv.length()<6) { //补前导零
                  sbv.insert(2,'0');
                }
                v = sbv;
              }
              v = "\'" + v + '\'';
            }
          }
        }catch (Exception ex) {}
        Class ct = f[i].getType(); //变量类型
        String t = formatClassName(ct,v);//变量类型名称
        dump(indent(prefix) + (m + ' ' + t + ' ' + n).trim() + " = ",
                depth,checkCircuit,v);
      }
      c = c.getSuperclass();
    } //while end
    dumpEnd(prefix,checkCircuit,obj);
  }

  /**
   * 输出调试开始信息。
   * @param prefix 输出前缀。
   * @param dump的对象。
   */
  private static void dumpBegin(String prefix,Vector checkCircuit,Object obj) {
    String className = formatClassName(obj.getClass(),obj);
    int address = System.identityHashCode(obj);
    checkCircuit.addElement(new Integer(address));
    if (obj instanceof Array) {
      className = className.substring(2) + '[' + Array.getLength(obj) + "] ";
    }
    if (className.startsWith("java.lang.")) { //去掉缺省的包名
      className = className.substring(10);
    }
    if (prefix.trim().endsWith("@" + address)) {
      out.println(prefix + " {");
    } else {
      out.println(prefix + className + " @" + address + " {");
    }
  }

  /**
   * 对于复合对象，输出dump结束位置的大括号。
   * @param prefix 输出前缀。
   */
  private static void dumpEnd(String prefix,Vector checkCircuit,Object obj) {
    checkCircuit.removeElement(new Integer(System.identityHashCode(obj)));
    int p = prefix.lastIndexOf(indentString);
    if (p>0) {
      prefix = prefix.substring(0,p) + indentString;
    }
    for (int i=0;i<prefix.length();i++) {
      char c = prefix.charAt(i);
      if (c=='\t'|| c==' ') {
        out.print(c);
      } else {
        break;
      }
    }
    out.println("}");
    out.flush();
  }

  /**
   * 用于定位调用堆栈层次中调用本类某个方法(或者更高层次)时的位置。
   * @param esc 调用堆栈中需要剔除的层次中的字符串，传入null表示无剔除层次。
   * @return 位置信息(类、函数、代码行)。
   */
  public static String locate(String esc) {
    StringWriter sw = new StringWriter();
    new Exception().printStackTrace(new PrintWriter(sw));
    for (StringTokenizer st = new StringTokenizer(sw.toString(),"\n");
            st.hasMoreTokens();) {
      String str = st.nextToken();
      if (str.indexOf("Exception")!=-1) { //异常描述，跳过
        continue;
      } else if (str.indexOf(Debug.class.getName())!=-1) { //调用栈最里层，跳过
        continue;
      } else if (esc!=null && str.indexOf(esc)!=-1) { //传入参数指定的层，跳过
        continue;
      } else if (esc==fullInfo) { //一个内部使用的特殊标记
        return str;
      } else {
        int i = str.indexOf('(');
        int j = str.indexOf(')');
        if (i!=-1 && j!=-1) {
          return str.substring(i,j+1);
        }
        break;
      }
    }
    return "";
  }

  /**
   * 将整数按16进制字符编码放到字节数组某个位置（0~9，a~f小写字母）。
   * @param src 保存16进制字符的字节数组。
   * @param lowByte 最低字节的位置（注意是最低字节的位置，在地址高字节端）。
   * @param value 待转换成16进制数的整数值。
   */
  private static void setHex(byte[]src, int lowByte, int value) {
    for (int i=0;i<8;i++) {
      src[lowByte-i] = hexNumber[value & 0xf]; //取低4位
      value >>>= 4; //将value逻辑右移4bit
      if (value==0) {
        break;
      }
    }
  }

  /** 16进制编码函数setHex()使用的内部常量。*/
  private static final byte[] hexNumber = {
    (byte)'0',(byte)'1',(byte)'2',(byte)'3',(byte)'4',(byte)'5',(byte)'6',
    (byte)'7',(byte)'8',(byte)'9',(byte)'a',(byte)'b',(byte)'c',(byte)'d',
    (byte)'e',(byte)'f'
  };

  /**
   * 缩进一级。
   * @param prefix原来的前缀。
   * @return 缩进后的前缀。
   */
  private static String indent(String prefix) {
    int p = prefix.lastIndexOf(indentString);
    if (p>0) {
      prefix = prefix.substring(0,p) + indentString;
    }
    StringBuffer sb = new StringBuffer();
    for (int i=0;i<prefix.length();i++) {
      char c = prefix.charAt(i);
      if (c=='\t'|| c==' ') {
        sb.append(c);
      } else {
        break;
      }
    }
    sb.append(indentString);
    return sb.toString();
  }

  /**
   * 将类名格式化成符合Java语言风格。
   * @param c 要格式化的类。
   * @return 格式化后的类名。
   */
  private static String formatClassName(Class c,Object obj) {
    String t = c.getName();

    //去掉末尾分号
    if (t.charAt(t.length()-1)==';') {
      t = t.substring(0,t.length()-1);
    }

    //数组类型处理
    boolean isArray = false;
    boolean firstDimension = true;
    while (t.startsWith("[")) {
      isArray = true;
      if (firstDimension && obj!=null) { //是第一维
        t = t.substring(1) + '[' + Array.getLength(obj) + ']';
        firstDimension = false;
      } else {
        t = t.substring(1)+"[]";
      }
    }
    if (isArray) {
      char ch = t.charAt(0);
      t = t.substring(1);
      switch (ch) {
        case 'B':
          t = "byte" + t; break;
        case 'C':
          t = "char" + t; break;
        case 'F':
          t = "float" + t; break;
        case 'I':
          t = "int" + t; break;
        case 'J':
          t = "long" + t; break;
        case 'S':
          t = "short" + t; break;
        case 'Z':
          t = "boolean" + t; break;
      }
    }
    if (t.startsWith("java.lang.")) { //去掉缺省包名
      t = t.substring(10);
    } else if (t.startsWith("class ")) {
      t = t.substring(7);
    }
    return t;
  }

  /**
   * 将字符串长度变为固定长（后面补空格），若长度已经等于或超过期望长度则
   * 补成期望长度的整数倍。
   * @param str 期望改变长度的字符串。
   * @param len 期望的长度。
   * @return 改变长度后的字符串。
   */
  private static String fixLength(String str,int len) {
    StringBuffer sb = new StringBuffer(len);
    sb.append(str);
    int n = len - str.length()%len;
    for (int i=0;i<n;i++) {
      sb.append(' ');
    }
    return sb.toString();
  }

  /**
   * 外界调用dump时输出的头部信息。
   * @return 头部信息。
   */
  private static String dumpHead() {
    StringBuffer sb = new StringBuffer();
    sb.append(sdf.format(new Date()));
    sb.append(locate(null));
    sb.append(' ');
    return sb.toString();
  }

  /**
   * 根据前缀取得当前递归深度。
   * @param prefix 输出前缀。
   * @return 递归深度(最小为1)。
   */
  private static int getDepth(String prefix) {
    int count = 0;
    int indentLen = indentString.length();
    int i=-indentLen;
    while (true) {
      count ++;
      i = prefix.indexOf(indentString,i+indentLen);
      if (i<0) {
        return count;
      }
    }
  }
  /** 输出调试信息时间的格式。*/
  private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

  /** 无任何意义，locate内部定位标志。*/
  public static final String fullInfo = "!@*#~^?'/\""; //给locate使用的内部标记。
}

/**
 * 断言失败时抛出的错误。只是简单从Error类继承。错误不是异常，系统不应该捕获的。
 * 若系统抛出该错误说明有BUG。
 */
class AssertFailed extends Error {}
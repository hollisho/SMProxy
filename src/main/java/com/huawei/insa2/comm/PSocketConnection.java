package com.huawei.insa2.comm;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import com.huawei.insa2.util.*;
//import com.huawei.insa2.comm.cngp.message.*;
//import anymusic.cngp.util.convertTools;
//import anymusic.cngp.util.LogUtil;
/**
 * 这是一个基于Socket通信的消息通信的增强类。提供比JDK原始Socket更高一级的功能，例如：
 * 非阻塞通信，连接中断重连，改变连接属性立即生效。
 * @author 李大伟
 * @version 1.0
 */
public abstract class PSocketConnection
    extends PLayer {

  /** 状态常量-尚未初始化。*/
  protected static String NOT_INIT;

  /** 状态常量-正在建立连接。*/
  protected static String CONNECTING;

  /** 状态常量-正在重新建立连接。*/
  protected static String RECONNECTING;

  /** 状态常量-建立连接成功。*/
  protected static String CONNECTED;

  /** 状态常量－正在心跳。*/
  protected static String HEARTBEATING;

  /** 状态常量－正在接收数据。*/
  protected static String RECEIVEING;

  /** 状态常量－正在关闭连接。*/
  protected static String CLOSEING;

  /** 状态常量－连接已经正常关闭。*/
  protected static String CLOSED;

  /** 运行期错误常量-不认识的地址。(无法得到IP地址，可能DNS查询失败或IP地址格式错误)*/
  protected static String UNKNOWN_HOST;

  /** 运行期错误常量-端口号配置错误。(不是整数或范围不在0~65535内)。*/
  protected static String PORT_ERROR;

  /** 运行期错误常量-连接被拒绝。(目的主机存在，但在指定的端口上没有TCP监听)。*/
  protected static String CONNECT_REFUSE;

  /** 运行期错误常量-主机不可达。(地址正确，但路由不通或主机没开)。*/
  protected static String NO_ROUTE_TO_HOST;

  /** 运行期错误常量-接收数据超时。(接收数据时出现错误)。*/
  protected static String RECEIVE_TIMEOUT;

  /** 运行期错误常量-连接被对方关闭。*/
  protected static String CLOSE_BY_PEER;

  /** 运行期错误常量-连接被对方重置。*/
  protected static String RESET_BY_PEER;

  /** 运行期错误常量－连接已经关闭。*/
  protected static String CONNECTION_CLOSED;

  /** 错误类别常量-通信异常。*/
  protected static String COMMUNICATION_ERROR;

  /** 错误类别常量-连接异常。*/
  protected static String CONNECT_ERROR;

  /** 错误类别常量-发送数据异常。*/
  protected static String SEND_ERROR;

  /** 错误类别常量-接收数据异常。*/
  protected static String RECEIVE_ERROR;

  /** 错误类别常量-关闭连接异常。*/
  protected static String CLOSE_ERROR;

  /** 最近一次通信故障原因。null表示通信正常。*/
  private String error;

  /** 最近一次出错状态变化时刻。*/
  protected Date errorTime = new Date();

  /** 该连接的名称。*/
  protected String name;

  /** 连接目的主机(IP或者主机名或域名)。*/
  protected String host;

  /** 连接目的主机端口号。*/
  protected int port = -1;

  /** 连接目的主机(IP或者主机名或域名)。*/
  protected String localHost;

  /** 连接目的主机端口号。*/
  protected int localPort = -1;

  /** 心跳间隔。*/
  protected int heartbeatInterval;

  /** 协议解码器。*/
  protected PReader in;

  /** 协议编码器。*/
  protected PWriter out;

  /** 屏幕输出的时间格式。*/
  protected static DateFormat df =
      new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

  /**
   * Socket读超时时间。读时超过该时间没收到数据认为连接中断，重新尝试连接。单位毫秒，0表示
   * 永不超时。其值至少应大于通信对方发送心跳间隔的时间，若对方不发心跳则该值应设置为0。
   */
  protected int readTimeout;

  /** 重连间隔时间。单位毫秒，缺省值10秒。*/
  protected int reconnectInterval;

  /** 该连接对应的Socket。*/
  protected Socket socket;

  /** 心跳线程。若该协议不需发送心跳则该成员为null。*/
  protected WatchThread heartbeatThread;

  /** 接收线程。*/
  protected WatchThread receiveThread;

  /** 事务超时时间。在事务上调用waitResponse()方法时最长阻塞时间。单位毫秒。*/
  protected int transactionTimeout;

  /** 用来保存国际化字符串的资源对象。该成员必须由子类赋值。*/
  protected Resource resource;

  /**
   * 根据参数设置创建一个PSocket。由于本类的构造需要的参数较多，且很多是可选参数,因此采用
   * Args类来专门保存参数，而不是给构造方法定义一大堆参数。Args中保存的key数据类型应为字符
   * 串，value数据类型可以是字符串或Number等对象(例如："123"或new Integer(123)都可以)。
   * 目前支持的参数有：<ul>
   * <li>host ---- 主机地址。必选。可以是IP地址也可以是机器名或域名。
   * <li>port ---- 主机端口号。必选。有效范围0~65535。
   * <li>name ---- 为本连接起的名字。可选。缺省值是host与port的组合。
   * <li>reconnect-interval ---- 重连间隔时间，如果发现连接中断则等待这么长时间后尝试
   * 重新建立连接。可选。单位：秒，缺省60秒。
   * <li>heartbeat-interval ---- 心跳间隔时间。可选。单位：秒，缺省为0表示不发心跳消息。
   * <li>read-timeout ---- 读数据超时时间。可选。单位：秒，缺省为0表示永不超时。该值至少
   * 要大于通信对方发送心跳的间隔时间。
   * <li>transaction-timeout ---- transaction timeout. optional, second.
   * @param args 参数列表。
   */
  public PSocketConnection(Args args) {
    super(null);
    init(args);
  }

  /**
   * 该构造函数供子类继承使用，构造后必须执行init()。
   */
  protected PSocketConnection() {
    super(null);
  }

  /**
   * 初始化操作。
   */
  protected void init(Args args) {
    resource = getResource();
    initResource();
    error = NOT_INIT;
    setAttributes(args); //设置属性
    //接收线程定义
    class ReceiveThread
        extends WatchThread {
      //convertTools cts=null;//字符编码转化
      public ReceiveThread() {
        super(name + "-receive");
        //cts=convertTools.getInstance();
      }

      public void task() {
        try {
          if (error == null) { //如果连接正常则阻塞在接收方法中
            PMessage m = null;
              m = in.read();
            if (m != null) {
              //CNGPMessage msg= (CNGPMessage)m;
              onReceive(m);
              //anymusic.cngp.cngpsmproxy.smsService.getInstance().setMsg(msg);
            }
          }
          else { //连接不正常则进行重连
            if (!error.equals(NOT_INIT)) { //如果不是第一次建立连接
              try {
                sleep(reconnectInterval); //休眠一会儿，防止频繁连接
              }
              catch (InterruptedException ex) {
                //忽略
              }
            }
            //LogUtil.info(cts.toISO("系统重新与网关连接！........................"),PSocketConnection.class);
            connect();
          }
        }
        catch (Exception ex) {
          setError(RECEIVE_ERROR + explain(ex));
        }
      }
    };

    //创建并启动接收线程
    receiveThread = new ReceiveThread();
    receiveThread.start();

    //这里不建立连接，在接收线程里做，防止被阻塞

    //心跳发送线程定义
    class HeartbeatThread
        extends WatchThread {
      public HeartbeatThread() {
        super(name + "-heartbeat");
        setState(State.WAITING);
      }

      public void task() {
      try {
          sleep(heartbeatInterval);
        }
        catch (InterruptedException ex) {
          //忽略
        }
        if (error == null && out != null) { //连接没有出错并且编码器存在
          try {
            heartbeat();
          }
          catch (IOException ex) {
            setError(SEND_ERROR + explain(ex));
          }
        }
      }
    };

    //如果需要，创建并启动心跳线程
    if (heartbeatInterval > 0) {
      heartbeatThread = new HeartbeatThread();
      heartbeatThread.start();
    }
  }

  /**
   * 设置连接属性。参数列表中没有的属性保持不变。改变的参数立即生效，只有当地址或端口号改变
   * 时才会对连接进行重连。
   * @param args 新属性内容。
   */
  public void setAttributes(Args args) {

    //如果名字是自动生成的，则清空，下面需要重新生成
    if (name != null && name.equals(String.valueOf(host) + ':' + port)) {
      name = null;
    }

    //保存原来的一些参数，用来判断是否改变。因为这些参数改变必须进行重连。
    String oldHost = host;
    int oldPort = port;
    String oldLocalHost = localHost;
    int oldLocalPort = localPort;

    //下面这四个是必选参数

    //主机地址（域名或IP）
    host = args.get("host", null);

    //端口
    port = args.get("port", -1);

    //本地地址（域名或IP）
    localHost = args.get("local-host", null);

    //本地端口
    localPort = args.get("local-port", -1);

    //下面这些是可选参数

    //连接名称
    name = args.get("name", null);
    if (name == null) {
      name = host + ':' + port; //缺省名字由地址和端口号组成
    }

    //读取数据最长等待时间，超过此时间没有读到数据认为连接中断。0表示永不超时。
    readTimeout = 1000 * args.get("read-timeout", readTimeout / 1000);
    //修改参数时使其立即生效
    if (socket != null) {
      try {
        socket.setSoTimeout(readTimeout);
      }
      catch (SocketException ex) {}
    }

    //连接中断时重连间隔时间
    reconnectInterval = 1000 * args.get("reconnect-interval", -1);

    //心跳间隔时间，0则不发送心跳
    heartbeatInterval = 1000 * args.get("heartbeat-interval", -1);

    //事务超时时间
    transactionTimeout = 1000 * args.get("transaction-timeout", -1);

    //主机地址或端口号改变则需要重连
    if (error == null && host != null && port != -1 &&
        (!host.equals(oldHost) || (port != port) ||
         !host.equals(oldHost) || (port != port))) {

      //令接收线程立即重连
      this.setError(resource.get("comm/need-reconnect"));
      receiveThread.interrupt();
    }
  }

  /**
   * 发送消息。
   * @param message 发送的消息。
   */
  public void send(PMessage message) throws PException {
    //Log.debug(message);
    if (error != null) { //连接可用
      throw new PException(SEND_ERROR + getError());
    }
    try {
      out.write(message);

      fireEvent(new PEvent(PEvent.MESSAGE_SEND_SUCCESS, this, message));
    }
    catch (PException ex) {
      fireEvent(new PEvent(PEvent.MESSAGE_SEND_FAIL, this, message));
      setError(SEND_ERROR + explain(ex));
      throw ex;
    }
    catch (Exception ex) {
      fireEvent(new PEvent(PEvent.MESSAGE_SEND_FAIL, this, message));
      setError(SEND_ERROR + explain(ex));
    }
  }

  /**
   * 取得该连接的名字。
   * @return 返回链接的名字。（肯定是非空字符串）
   */
  public String getName() {
    return name;
  }

  /**
   * 取得该连接对应的主机地址。
   * @return 主机地址(IP或域名)。
   */
  public String getHost() {
    return host;
  }

  /**
   * 取得该连接对方的端口号。
   * @return 连接对方的端口号。
   */
  public int getPort() {
    return port;
  }

  /**
   * 取得该连接的重连间隔时间。
   * @return 毫秒为单位的重连间隔时间。
   */
  public int getReconnectInterval() {
    return reconnectInterval / 1000;
  }

  /**
   * 返回该连接的字符串描述（包括name、host、port）。
   * @return 描述信息。
   */
  public String toString() {
    return "PSocketConnection:" + name + '(' + host + ':' + port + ')';
  }

  /**
   * 取得该连接的读数据超时时间。
   * @return 毫秒为单位的读超时时间。
   */
  public int getReadTimeout() {
    return readTimeout / 1000;
  }

  /**
   * 此连接是否处于可用状态。
   * @return 返回状态是否可用，true表示此连接可用。
   */
  public boolean available() {
    return error == null;
  }

  /**
   * 取得错误描述，导致连接不可用的原因。
   * @return 当前错误描述，若连接正常则返回null。
   */
  public String getError() {
    return error;
  }

  /**
   * 取得出错时间，若没出错该时间无任何意义。
   * @return 出错时间，非null。
   */
  public Date getErrorTime() {
    return errorTime;
  }

  /**
   * 关闭这个连接。
   */
  public synchronized void close() {
    try {
      if (socket != null) {
        //Log.info(CLOSEING + this);

        //Socket关闭时其输入、输出流自动关闭，不必再调用流的关闭
        socket.close();
        in = null;
        out = null;
        socket = null;

        //杀死心跳线程和接收线程
        if (heartbeatThread != null) {
          heartbeatThread.kill();
        }
        receiveThread.kill();
      }
    }
    catch (Exception ex) {}
    setError(NOT_INIT); //重新回到尚未初始化状态
  }

  /**
   * 根据设置的参数初始化连接，可以是首次建立TCP连接，也可以是重连。
   */
  protected synchronized void connect() {
    //这个实际不是错误，不应记录错误日志。但此时连接不可用
    if (error == NOT_INIT) { //如果当前连接尚未初始化(这里故意用等号判断字符串相等)
      error = CONNECTING;
    }
    else if (error == null) { //如果连接正常
      error = RECONNECTING;
    }
    errorTime = new Date();

    if (socket != null) { //如果socket对象存在，在连接前先把它关闭。
      try {
        socket.close();
      }
      catch (IOException ex) {
        //Log.warn(CLOSE_ERROR + explain(ex));
      }
    }

    //尝试连接或重连
    try {
      if (port <= 0 || port > 65535) {
        setError(PORT_ERROR + "port:" + port);
        return;
      }

      //localPort为-1表示建立Socket时不使用localPort
      if (localPort < -1 || localPort > 65535) {
        setError(PORT_ERROR + "local-port:" + localPort);
        return;
      }

      //如果指定了本地地址，并且也指定了本地端口号，则绑定指定的地址和端口号；
      //如果只指定本地地址，而没有指定本地端口号，则在该地址上寻找任意一个可用端口号
      if (localHost != null) {
        boolean isConnected = false;
        InetAddress localAddr = InetAddress.getByName(localHost);
        if (localPort == -1) {
          for (int p = (int) (Math.random() * 64500); p < 64500 * 14; p += 13) {
            try {
              socket = new Socket(host, port, localAddr, 1025 + (p % 64500));
              isConnected = true;
              break;
            }
            catch (IOException ex) {
              //忽略，进行下次尝试
            }
            catch (SecurityException ex) {
              //忽略，进行下次尝试
            }
          }
          if (!isConnected) {
            throw new SocketException("Can not find an avaliable local port");
          }
        }
        else {
          socket = new Socket(host, port, localAddr, localPort);
        }
      }
      else {
        socket = new Socket(host, port);
      }

      //读超时时间，单位毫秒，非负，=0表示不超时。read()方法超过此时间若还没有
      //数据返回则抛出InterruptedIOException异常。用来处理SMP心跳超时。
      socket.setSoTimeout(readTimeout);

      //如果加缓冲则在使用前嵌套一个BufferedXXXStream，即可在这里加，也可在
      //Reader和Writer的实现类里加。这里约定统一在编解码器处加，防止重复。
      out = null;
      out = getWriter(socket.getOutputStream()
                      //new CountableOutputStream(socket.getOutputStream(),
                      //                          new Counter(Args.EMPTY))
                      );
      in = null;
      in = getReader(socket.getInputStream()
                     //new CountableInputStream(socket.getInputStream(),
                     //                       new Counter(Args.EMPTY))
                     );
      setError(null); //连接成功标志
    }
    catch (IOException ex) {
      setError(CONNECT_ERROR + explain(ex));
    }
  }

  /**
   * 设置出错描述，可设置为null，表示设成正常状态。
   * @param desc 出错描述。
   */
  protected void setError(String desc) {
    //已经处于该状态则直接返回(保持出错时间不变)
    if ( (error == null && desc == null) || (desc != null && desc.equals(error))) {
      return;
    }
    error = desc;
    errorTime = new Date();
    //if (desc!=null && Log.isInited() && Log.getLevel()<=Log.WARN) {
    //  Log.warn(COMMUNICATION_ERROR + desc);
    //}
    if (desc == null) {
      desc = CONNECTED;
    }
  }

  /**
   * 取得协议编码器，由具体协议的子类实现。
   * @param out 编码后数据的输出流。
   */
  protected abstract PWriter getWriter(OutputStream out);

  /**
   * 取得协议解码器，由具体协议的子类实现。
   * @param in 解码器读取数据的输入流。
   */
  protected abstract PReader getReader(InputStream in);

  /**
   * 由子类提供得到资源字符串的方法。
   * @return 保存有国际化字符串的资源对象。
   */
  protected abstract Resource getResource();

  /**
   * 执行定期心跳。缺省什么也不做，须心跳的协议子类须重载该方法。
   * 如果协议中心跳是有响应的，则等待响应。可在连续失败计数达到累计值时抛出IOException异常
   * 则连接将会自动重新建立。
   */
  protected void heartbeat() throws IOException {
    //由子类实现，但也可不实现，因此不是抽象方法。
  }

  /**
   * 系统初始化时调用，读取常量资源字符串。
   */
  public void initResource() {
    NOT_INIT = resource.get("comm/not-init");
    CONNECTING = resource.get("comm/connecting");
    RECONNECTING = resource.get("comm/reconnecting");
    CONNECTED = resource.get("comm/connected");
    HEARTBEATING = resource.get("comm/heartbeating");
    RECEIVEING = resource.get("comm/receiveing");
    CLOSEING = resource.get("comm/closeing");
    CLOSED = resource.get("comm/closed");
    UNKNOWN_HOST = resource.get("comm/unknown-host");
    PORT_ERROR = resource.get("comm/port-error");
    CONNECT_REFUSE = resource.get("comm/connect-refused");
    NO_ROUTE_TO_HOST = resource.get("comm/no-route");
    RECEIVE_TIMEOUT = resource.get("comm/receive-timeout");
    CLOSE_BY_PEER = resource.get("comm/close-by-peer");
    RESET_BY_PEER = resource.get("comm/reset-by-peer");
    CONNECTION_CLOSED = resource.get("comm/connection-closed");
    COMMUNICATION_ERROR = resource.get("comm/communication-error");
    CONNECT_ERROR = resource.get("comm/connect-error");
    SEND_ERROR = resource.get("comm/send-error");
    RECEIVE_ERROR = resource.get("comm/receive-error");
    CLOSE_ERROR = resource.get("comm/close-error");
  }

  /**
   * 对通信过程抛出的IO异常的原因进行分析。
   * @return 浅显易懂的故障描述。
   */
  protected String explain(Exception ex) {
    String msg = ex.getMessage();
    if (msg == null) {
      msg = "";
    }
    if (ex instanceof PException) { //PException异常提供的描述信息比较明确
      return ex.getMessage();
    }
    else if (ex instanceof EOFException) {
      return CLOSE_BY_PEER;
    }
    else if (msg.indexOf("Connection reset by peer") != -1) {
      return RESET_BY_PEER;
    }
    else if (msg.indexOf("SocketTimeoutException") != -1) {
      return RECEIVE_TIMEOUT;
    }
    else if (ex instanceof NoRouteToHostException) {
      return NO_ROUTE_TO_HOST;
    }
    else if (ex instanceof ConnectException) {
      return CONNECT_REFUSE;
    }
    else if (ex instanceof UnknownHostException) {
      return UNKNOWN_HOST;
    }
    else if (msg.indexOf("errno: 128") != -1) {
      // UNIX下主机不可达可能返回该信息
      return NO_ROUTE_TO_HOST;
    }
    else { //漏掉的其他错误情况
      ex.printStackTrace();
      return ex.toString();
    }
  }
}

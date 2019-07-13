package com.huawei.insa2.comm.cngp;



import java.io.*;
import java.net.*;
import java.util.*;
import com.huawei.insa2.util.*;
import com.huawei.insa2.comm.cngp.message.*;
import com.huawei.insa2.comm.*;
//import  anymusic.cngp.util.convertTools;
//import  anymusic.cngp.util.LogUtil;
/**
 * PCNGP协议的连接层。
 * @author       linmeilong
 * @version      1.0
 */
public class CNGPConnection extends PSocketConnection{

   private int degree = 0;   //计数器,计算连续发出的心跳消息没有响应的个数

   private int hbnoResponseOut = 3;  //连续发出心跳消息没有响应达到该数，需要重连

   private String clientid = null; //SP编号或者SMGW编号

   private int loginMode = 2;     //登录类型

   private int version;   //双方协商的版本号

   private String shared_secret; //事先商定的值，用于生成SP认证码

   //private  convertTools cts=null;//字符编码转化
   /**
   * 创建一个CNGP协议连接。
   * @param args 连接创建所需要的参数。
   */
   public CNGPConnection(Args args) {
       hbnoResponseOut = args.get("heartbeat-noresponseout",3);
       clientid = args.get("clientid","huawei");
       //loginMode = args.get("loginmode",0);
       version = args.get("version",1);
       loginMode=args.get("loginmode",2);
       shared_secret = args.get("shared-secret","");
       CNGPConstant.debug = args.get("debug",false);
       //cts=convertTools.getInstance();
       //初始化系统的错误提示常量
       CNGPConstant.initConstant(this.getResource());
       init(args);
   }

  /**
   * 被父类调用，获得编码器。向得到的编码器中写CNGPMessage各子类对象将导致向out参数指向的输出流
   * 写根据消息对象内容编码的字节数据。
   * @param out 编码结果输出流。
   * @return 消息编码器。
   */
   protected PWriter getWriter(OutputStream out) {
       return new CNGPWriter(out);
   }

  /**
   * 被父类调用，获得解码器。调用解码器的read()方法将导致从参数in指向的输入流中读取字节数据
   * 并分析组合成CNGPMessage个子类的消息对象。
   * @param in 解码数据来源。
   * @return 消息解码器。
   */
   protected PReader getReader(InputStream in) {
       return new CNGPReader(in);
   }

  /**
   * 覆盖父类的方法，从消息中取得调度所需要的事务自动机号。
   * @param message 来自网关的消息。
   * @return 事务自动机号。
   */
   public int getChildId(PMessage message) {
       CNGPMessage mes = (CNGPMessage)message;
       int sequenceId = mes.getSequenceId();

       if( mes.getRequestId() == CNGPConstant.Deliver_Request_Id)
       {//假如消息为SMC主动下发的消息则返回-1
           return -1;
       }
       return sequenceId;
   }

  /**
   * 覆盖父类的方法，创建一个事务。
   * @return 在该连接上创建的事务。
   */
   public PLayer createChild() {
       return new CNGPTransaction(this);
   }

  /**
   * 取得事务超时时间。等待MSC发来的响应消息最长等待时间。单位毫秒。
   * @return 事务超时时间，单位毫秒。
   */
   public int getTransactionTimeout() {
       return transactionTimeout;
   }

  /**
   * 提供得到资源字符串的方法。
   * @return 保存有国际化字符串的资源对象。
   */
   public Resource getResource(){
       try{
           return new Resource(getClass(),"resource");
       }
       catch(IOException e){
           e.printStackTrace();
           return null;
       }
   }

  /**
   * 等待连接可用。由于建立连接不是在主线程中进行，因此如果想确认连接已经建立完成可调用本
   * 方法。
   */
  public synchronized void waitAvailable() {
    try {
      if (this.getError()==NOT_INIT) {
        wait(this.transactionTimeout);
      }
    } catch(InterruptedException ex) {
      //忽略
    }
  }

    /**
     * 终止连接。调用之后连接将永久不可用。
     */
    public void close() {
      //发送终止连接请求后，收回响应
      try {
        CNGPExitMessage msg = new CNGPExitMessage();
        send(msg);
      }
      catch (PException ex) {
        //忽略
      }
      super.close();
     }

  /**
   * 执行定期心跳。
   * 如果协议中心跳是有响应的，则等待响应。可在连续失败计数达到累计值10时抛出IOException异常
   * 则连接将会自动重新建立。
   */
    protected void heartbeat() throws IOException {
       //发送心跳消息
       //LogUtil.info(cts.toISO("系统向网关发送心跳消息........................"),CNGPConnection.class);
       CNGPTransaction t = (CNGPTransaction)this.createChild();
       CNGPActiveTestMessage hbmes = new CNGPActiveTestMessage();

       t.send(hbmes);
       t.waitResponse();
       CNGPActiveTestRespMessage rsp = (CNGPActiveTestRespMessage)t.getResponse();
       if( rsp == null ){ //没有接收到心跳的返回消息
           this.degree ++;
           //LogUtil.info(cts.toISO("心跳消息第"+degree+"次无返回！......................."),CNGPConnection.class);
           if( this.degree == this.hbnoResponseOut ){
               this.degree = 0;
               //LogUtil.info(cts.toISO("心跳消息第"+this.hbnoResponseOut +"次无返回！此时系统连接重启！......................."),CNGPConnection.class);
               throw new IOException(CNGPConstant.HEARTBEAT_ABNORMITY); //抛出异常，需要重连.判断连续发送连续多次心跳，都没有返回，才抛出异常
          }
       }
       else{
           this.degree = 0;
           //LogUtil.info(cts.toISO("成功接收心跳返回消息！..........................."),CNGPConnection.class);
       }
       t.close();
  }

  /**
   * 重载父类的connect方法,根据设置的参数初始化连接，可以是首次建立TCP连接，并建立逻辑连接,
   * 也可以是重连并建立逻辑连接。
   */
   protected synchronized void connect(){

       super.connect();
       if (!available()) { //物理连接没建成功，返回，等待下次重连
         return;
       }

       //定义一连接请求消息
       CNGPLoginMessage request = null;
       //接收的请求消息
       CNGPLoginRespMessage rsp = null;

       try{
           request = new CNGPLoginMessage(clientid,shared_secret,loginMode,
                        new Date(),version);
       }
       catch(IllegalArgumentException e){
           //定义消息不成功，抛出异常
           e.printStackTrace();
           this.close();
           this.setError(CNGPConstant.CONNECT_INPUT_ERROR);
       }
       //建立连接成功后，发送逻辑连接请求
       //建立一事务
       CNGPTransaction t = (CNGPTransaction)this.createChild();
       try {
           t.send(request);
           PMessage m = in.read();
           onReceive(m);
       }
       catch(IOException e){
           e.printStackTrace();
           this.close();
           setError(CNGPConstant.LOGIN_ERROR + explain(e));
       }

       rsp = (CNGPLoginRespMessage)t.getResponse();
       if (rsp==null) {
            //没有接收到消息，初始化不成功，抛出异常
           this.close();
           this.setError(CNGPConstant.CONNECT_TIMEOUT);
       }
       t.close();

       //对返回的消息进行判断
       if( rsp != null )
       {
           if(rsp.getStatus() !=0 )
           {
               this.close();
               //逻辑连接不成功，抛出异常
               this.setError("Fail to login,the status code id " + rsp.getStatus());
           }
       }
       this.notifyAll();
   }
}

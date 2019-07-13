package com.huawei.smproxy;

import java.util.*;
import java.io.IOException;
import com.huawei.insa2.util.*;
import com.huawei.insa2.comm.cngp.message.*;
import com.huawei.insa2.comm.cngp.*;
import com.huawei.insa2.comm.*;

/**
 * 对外提供的API接口。
 */
public class CNGPSMProxy {

  /**
   * 收发消息使用的连接对象。
   */
  private CNGPConnection conn;

  /**
   * 建立连接并登录。
   * @param args 保存建立连接所需的参数。
   */
  public CNGPSMProxy(Map args) {
    this(new Args(args));
  }

  /**
   * 建立连接并登录。
   * @param args 保存建立连接所需的参数。
   */
  public CNGPSMProxy(Args args) {
    conn = new CNGPConnection(args);
    //增加事件监听器，对SMC主动发送的消息进行处理
    conn.addEventListener(new CNGPEventAdapter(this));
    conn.waitAvailable(); //等待连接建立完成
    if (!conn.available()) {
      throw new IllegalStateException(conn.getError());
    }
  }

  /**
   * 发送消息，阻塞直到收到响应或超时。
   * 返回为收到的消息
   * @exception PException 超时或通信异常。
   */
  public CNGPMessage send(CNGPMessage message) throws IOException {
    if (message == null) { //消息为空，不发送
      return null;
    }
    //建立一事务
    CNGPTransaction t = (CNGPTransaction) conn.createChild();
    try {
      t.send(message);
      t.waitResponse();
      CNGPMessage rsp = t.getResponse();
      return rsp;
    }
    finally {
      t.close();
    }
  }

  /**
   * 连接终止的处理，由API使用者实现
   * SMC连接终止后，需要执行动作的接口
   */
  public void onTerminate() {
  }

  /**
   * 对收到消息的处理。API使用者应该重载本方法来处理来自短信中心的Deliver消息，
   * 并返回响应消息。这里缺省的实现是返回一个成功收到的响应。
   * @param msg 从短消息中心来的消息。
   * @return 应该回的响应，由API使用者生成。
   */
  public CNGPMessage onDeliver(CNGPDeliverMessage msg) {
//Debug.dump("debug,cngpsmproxy,收到上行："+msg.getSrcTermID()+":content:"+msg.getMsgContent());
    return new CNGPDeliverRespMessage(msg.getMsgId(), 0);
  }

  /**
   * 终止连接。调用之后连接将永久不可用。
   */
  public void close() {
    conn.close();
  }

  /**
   * 提供给外部调用获取TCP连接对象
   */
  public CNGPConnection getConn() {
    return conn;
  }

  /**
   * 提供给业务层调用的获取连接状态的方法
   */
  public String getConnState() {
    return conn.getError();
  }

}


package com.huawei.insa2.comm.cngp;

import com.huawei.insa2.comm.*;
import com.huawei.insa2.comm.cngp.message.*;
import com.huawei.insa2.util.Debug;
/**
 * PCNGP事务自动机。
 * @author linmeilong
 * @version 1.0
 */
/// PLayer
public class CNGPTransaction extends PLayer {

  /** 收到的MSC发来的消息。*/
  private CNGPMessage receive;

  private int sequenceId;

  /**
   * 事务对象由连接对象创建。
   * @param conn 该事务所属连接。
   */
   public CNGPTransaction(PLayer connection) {
       super(connection);
       this.sequenceId=this.id;
   }

  /**
   * 收到下层上报的消息，保存起来。通知所有等待消息的线程。覆盖父类的消息分发实现。
   * @param msg 响应消息。
   */
   public synchronized void onReceive(PMessage msg) {
       receive = (CNGPMessage)msg;
       this.sequenceId = receive.getSequenceId();

       if( CNGPConstant.debug == true )
       {//假如属于调试状态，则把收到的消息打印到屏幕
           Debug.dump(receive.toString());
       }
       //通知所有阻塞在waitResponse()的线程
       notifyAll();
   }

  /**
   * 发送一条消息。
   * @param message 待发送消息。
   */
   public void send(PMessage message) throws PException {

       //重新设置消息的流水号，然后交给连接层
       CNGPMessage msg = (CNGPMessage)message;
       msg.setSequenceId(this.sequenceId);
       parent.send(message); //parent是从PLayer继承而来的成员变量，代表父协议层，即连接层
       if( CNGPConstant.debug == true )
       {//假如属于调试状态，则把发送到的消息打印到屏幕
           Debug.dump(msg.toString());
       }
   }

  /**
   * 取得该事务的响应数据。
   * @return 响应消息对象。
   */
   public CNGPMessage getResponse() {
       return receive;
   }

  /**
   * 等待一条响应到达。
   */
   public synchronized void waitResponse() {
       if (receive==null) {
           try {
               wait(((CNGPConnection)this.parent).getTransactionTimeout());
           }
           catch (InterruptedException ex) {}
       }
   }

}

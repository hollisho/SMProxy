package com.huawei.smproxy;


import com.huawei.insa2.comm.*;
import com.huawei.insa2.comm.cngp.*;
import com.huawei.insa2.comm.cngp.message.*;


class CNGPEventAdapter
    extends PEventAdapter {
  private CNGPSMProxy smProxy = null;
  private CNGPConnection conn = null;
  public CNGPEventAdapter(CNGPSMProxy smProxy) {
    this.smProxy = smProxy;
    conn = smProxy.getConn();
  }

  /**
   * 覆盖父类该方法，处理PEvent.CHILD_CREATED事件，也就是SMC主动下发的消息
   * @child 为SMC主动下发的消息所建立的对应事务
   */
  public void childCreated(PLayer child) {
//Debug.dump("debug,childCreated,收到child：");
      //获取消息后，删除事务
    CNGPTransaction t = (CNGPTransaction) child;
    CNGPMessage msg = t.getResponse();
    //定义响应消息
    CNGPMessage resmsg = null;

    if (msg.getRequestId() == CNGPConstant.Exit_Request_Id) { //对下发终止应用层连接的响应
      resmsg = (CNGPMessage)new CNGPExitRespMessage();
      smProxy.onTerminate();
    }
    else if (msg.getRequestId() == CNGPConstant.Deliver_Request_Id) { //对主动下发的短信消息的响应
       CNGPDeliverMessage tmpmes = (CNGPDeliverMessage) msg;
       //调用客户程序处理主动下发短信消息的接口
       resmsg = smProxy.onDeliver(tmpmes);
    }else if(msg.getRequestId()== CNGPConstant.Active_Test_Resp_Request_Id){
       resmsg = smProxy.onDeliver(null);
    }
    else { //不进行任何处理,只删除事务
      t.close();
    }
    //对SMC主动下发的消息进行响应消息发送后，删除事务
    if (resmsg != null) {
      try {
        t.send(resmsg);
      }
      catch (PException e) {
        e.printStackTrace();
      }
      t.close();
    }
  }
}
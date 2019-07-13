package com.huawei.insa2.comm.cngp.message;

import com.huawei.insa2.comm.cngp.CNGPConstant;
import com.huawei.insa2.util.TypeConvert;

/**
 * 定义下发短信响应消息,发送给SMC。
 * @author       linmeilong
 * @version      1.0
 */

public class CNGPDeliverRespMessage extends CNGPMessage{

  //存放调试时输出信息的变量
   private StringBuffer strBuf;

  /**
   * 定义下发消息的响应
   * @param msgId  消息标识
   * @param congestionState  结果
   */
   public CNGPDeliverRespMessage(
      byte[] msgId,
      int congestionState
   )  throws IllegalArgumentException{

       //对消息的参数进行判断
       if( msgId.length > 10 ){
          throw new IllegalArgumentException(CNGPConstant.DELIVER_REPINPUT_ERROR + ":msg_Id" + CNGPConstant.STRING_LENGTH_GREAT + "10");
       }
       if (congestionState < 0 || congestionState > 255) {
         throw new IllegalArgumentException(CNGPConstant.DELIVER_REPINPUT_ERROR
                                            + ":congestionState " +
                                            CNGPConstant.INT_SCOPE_ERROR);
       }
         //计算出消息的长度，消息头＋消息体

         int len = 31;
         this.buf = new byte[31]; //根据消息长度开辟空间

         //填充消息头参数
         setMsgLength(len);
         setRequestId(CNGPConstant.Deliver_Resp_Request_Id);
         //流水号8-11位为命令状态，此时不填充
         //流水号12-15位为流水号，发送时填充

         //填充消息体
         //消息标识
         System.arraycopy(msgId, 0, this.buf, 16, msgId.length);
         //拥塞状态
         this.buf[30] = (byte) congestionState;

         //把输入的参数记录到outStr变量中，以便输出到屏幕

         strBuf = new StringBuffer(100);
         strBuf.append(",MsgId=" + msgId);
         strBuf.append(",congestionState=" + congestionState);
   }


   /**
    * 返回消息体所有的字段与内容
    */
    public String toString(){
        StringBuffer outStr = new StringBuffer(100);
        outStr.append("CNGPDeliverRespMessage:");
        strBuf.append("PacketLength=" + this.getMsgLength());
        strBuf.append(",RequestID=" + this.getRequestId());
        strBuf.append(",Status=" + this.getStatus());
        outStr.append(",SequenceId=" + this.getSequenceId());
        if(strBuf != null)
        {
           outStr.append(strBuf.toString());
        }
        return outStr.toString();
    }

}

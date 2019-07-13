package com.huawei.insa2.comm.cngp.message;

import java.lang.IllegalArgumentException;
import com.huawei.insa2.comm.cngp.CNGPConstant;
import com.huawei.insa2.util.TypeConvert;

/**
 * 定义发送短信响应消息结构。
 * @author       linmeilong
 * @version      1.0
 */

public class CNGPSubmitRespMessage extends CNGPMessage{

  /**
   * 定义一发送短信响应消息
   * @param buf SMC发回的数据
   */
   public CNGPSubmitRespMessage(byte[] buf) throws IllegalArgumentException{

       this.buf = new byte[31];
       if( buf.length != 31 ){//消息长度不正确
           throw new IllegalArgumentException( CNGPConstant.SMC_MESSAGE_ERROR );
       }
       System.arraycopy(buf,0,this.buf,0,31);
   }

   /**
   * 返回消息标识
   */
   public byte[] getMsgId(){
       byte[] tmpMsgId = new byte[10];
       System.arraycopy(this.buf,16,tmpMsgId,0,10);
       return tmpMsgId;
   }

   /**
    * 取得拥塞状态
    */
   public int getCongestionState(){
     return this.buf[30];
   }

  /**
   * 返回消息体所有的字段与内容
   */
   public String toString(){
       StringBuffer strBuf = new StringBuffer(200);
       strBuf.append("CNGPSubmitRespMessage: ");
       strBuf.append("PacketLength=" + this.getMsgLength());
       strBuf.append(",RequestID=" + this.getRequestId());
       strBuf.append(",Status=" + this.getStatus());
       strBuf.append(",SequenceId=" + this.getSequenceId());
       strBuf.append(",MsgId=" + new String(this.getMsgId()));
       strBuf.append(",CongestionState=" + this.getCongestionState());
       return strBuf.toString();
   }
}

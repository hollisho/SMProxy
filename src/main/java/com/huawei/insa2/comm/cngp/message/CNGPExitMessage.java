package com.huawei.insa2.comm.cngp.message;

import java.io.IOException;
import com.huawei.insa2.comm.cngp.CNGPConstant;
import com.huawei.insa2.util.TypeConvert;
/**
 * 定义退出请求的消息。
 * @author       linmeilong
 * @version      1.0
 */

public class CNGPExitMessage extends CNGPMessage{


   /**
    * 定义一发送给SMC退出请求消息，消息体为空
    */
    public CNGPExitMessage() {
        //计算出消息的长度，
        int len = 16;
        this.buf = new byte[len]; //根据消息长度开辟空间

        //填充消息头参数
        setMsgLength(len);
        setRequestId(CNGPConstant.Exit_Request_Id);
    }

   /**
    * 定义一SMC主动发送过来的退出通知消息
    * @param buf SMC发送过来的数据
    */
    public CNGPExitMessage(byte[] buf) throws IllegalArgumentException{
      this.buf = new byte[16];
      if( buf.length != 16 ){//流水号
          throw new IllegalArgumentException( CNGPConstant.SMC_MESSAGE_ERROR );
      }
      System.arraycopy(buf,0,this.buf,0,16);
   }

   /**
    * 返回消息体所有的字段与内容
    */
   /**
    * 返回消息体所有的字段与内容
    */
    public String toString(){
        StringBuffer strBuf = new StringBuffer(100);
        strBuf.append("CNGPExitMessage: ");
        strBuf.append("PacketLength=" + this.getMsgLength());
        strBuf.append(",RequestID=" + this.getRequestId());
        strBuf.append(",Status=" + this.getStatus());
        strBuf.append(",SequenceId=" + this.getSequenceId());
        return strBuf.toString();
    }

}

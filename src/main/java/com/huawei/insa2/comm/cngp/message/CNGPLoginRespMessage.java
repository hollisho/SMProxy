package com.huawei.insa2.comm.cngp.message;


import com.huawei.insa2.comm.cngp.CNGPConstant;
import com.huawei.insa2.util.TypeConvert;

/**
 * 定义请求登录返回的消息结构。
 * @author       linmeilong
 * @version      1.0
 */

public class CNGPLoginRespMessage extends CNGPMessage{

  /**
   * 定义返回的消息格式
   * @param buf 返回的数据,消息流水号以后的信息，包括流水号
   */
   public CNGPLoginRespMessage(byte buf[]) throws IllegalArgumentException{

       this.buf = new byte[33];
       if( buf.length != 33 ){//消息长度不正确,流水号+消息体
           throw new IllegalArgumentException( CNGPConstant.SMC_MESSAGE_ERROR );
       }
       System.arraycopy(buf,0,this.buf,0,buf.length);
  }


  /**
   * 返回服务器端的认证码
   */
   public byte[] getAuthenticatorServer(){
       byte tmpbuf[] = new byte[16];
       System.arraycopy(this.buf,16,tmpbuf,0,16);
       return tmpbuf;
   }

  /**
   * 返回服务器支持的最高版本号
   */
   public byte getVersion(){
       return this.buf[32];
   }

  /**
   * 返回消息体所有的字段与内容
   */
   public String toString(){
       StringBuffer strBuf = new StringBuffer(300);
       strBuf.append("CNGPLoginRespMessage: ");
       strBuf.append("PacketLength=" + this.buf.length);
       strBuf.append(",RequestID=" + this.getRequestId());
       strBuf.append(",Status=" + this.getStatus()) ;
       strBuf.append(",SequenceId=" + this.getSequenceId());
       strBuf.append(",AuthenticatorServer="  + new String(this.getAuthenticatorServer()));
       strBuf.append(",Version=" + this.getVersion());
       return strBuf.toString();
   }

}

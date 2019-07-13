package com.huawei.insa2.comm.cngp.message;

import com.huawei.insa2.comm.PMessage;
import com.huawei.insa2.util.*;
/**
 * CNGP消息的父类
 * @author linmeilong
 * @version 1.0
 */

public abstract class CNGPMessage extends PMessage implements Cloneable{


   /**消息存放的byte类型数组**/
   //对于发送的消息存放所有的子段，包括消息头与消息体
   //对于接收的消息存放流水号与消息体的信息
   protected byte[] buf;

   public CNGPMessage() {
   }

   /**
    *复制本消息的方法
    *@return 返回该类对象的一复制消息对象
    */
   public Object clone() {
     try {
       CNGPMessage m = (CNGPMessage) super.clone();
       m.buf = (byte[]) this.buf.clone();
       return m;
     } catch(CloneNotSupportedException ex) {
       ex.printStackTrace();
       return null;
     }
   }
   //抽象函数,把消息的内容转换为字符串,由子类实现
   public abstract String toString();

   //获取消息的长度
   public int getMsgLength(){
     int msgLength = TypeConvert.byte2int(this.buf,0);
     return msgLength;
   }

   //设置消息的长度
   public void setMsgLength(int msgLength){
     TypeConvert.int2byte(msgLength,buf,0);
   }


   //获取消息的RequestId
   public int getRequestId(){
     int requestId = TypeConvert.byte2int(this.buf,4);
     return requestId;
   }

   //设置消息的RequestId
   public void setRequestId(int requestId){
     TypeConvert.int2byte(requestId,buf,4);
   }

   //获取消息的状态
   public int getStatus(){
     int status = TypeConvert.byte2int(this.buf,8);
     return status;
   }

   //设置消息的状态
   public void setStatus(int status){
     TypeConvert.int2byte(status,buf,8);
   }

   /**
    * 获取流水号
    */
   public int getSequenceId()
   {
     int sequenceId = TypeConvert.byte2int(buf,12);
       return sequenceId;
   }

   /**
    * 重新设置流水号码
    */
   public void setSequenceId(int sequenceId){
        TypeConvert.int2byte(sequenceId,buf,12);
   }

   /**
    * 获取byte[] 类型的消息
    */
   public byte[] getBytes(){
       return buf;
   }
}

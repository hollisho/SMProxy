package com.huawei.insa2.comm.cngp;

import java.io.*;
//import java.util.*;
//import java.security.*;
//import java.lang.reflect.*;
import com.huawei.insa2.comm.*;
import com.huawei.insa2.comm.cngp.message.*;
import com.huawei.insa2.util.*;

/**
 * CNGP协议解码器。被类的对象只在接收线程中被执行，因此不用考虑多线程保护。
 * @author       linmeilong
 * @version      1.0
 */

public class CNGPReader extends PReader{

   /** 解码后消息输出到该输出流。*/
   protected DataInputStream in;

  /**
   * 构造CNGP协议解码器。
   */
   public CNGPReader(InputStream is) {

       in = new DataInputStream(is);
   }

  /**
   * 将输入流中数据解码成消息。如果遇到一个心跳消息则返回null。
   * @param message 待编码的消息对象。
   */
   public PMessage read() throws IOException {

       int totalLength = in.readInt();  //消息的总长度
       int commandId = in.readInt();    //消息的类型
       byte[] tmp = new byte[totalLength-8];
       in.readFully(tmp);

       //CNGP消息，消息头 + 消息体
       byte[] buf = new byte[totalLength];
       TypeConvert.int2byte(totalLength,buf,0);
       TypeConvert.int2byte(commandId,buf,4);
       System.arraycopy(tmp,0,buf,8,totalLength-8);
       /*
       for(int k=0;k<totalLength;k++){
         System.out.print(buf[k]);
       }*/
       if( commandId == CNGPConstant.Login_Resp_Request_Id )
       {//响应类型为请求连接应答
           return new CNGPLoginRespMessage(buf);
       }
       else if( commandId == CNGPConstant.Deliver_Request_Id )
       {//消息类型为短信下发
           return new CNGPDeliverMessage(buf);
       }
       else if( commandId == CNGPConstant.Submit_Resp_Request_Id )
       {//消息类型为提交短信应答
           return new CNGPSubmitRespMessage(buf);
       }else if( commandId == CNGPConstant.Active_Test_Resp_Request_Id)
       {//消息类型为心跳短信应答
             return new CNGPActiveTestRespMessage(buf);
        }
        else{
           return null;
       }
   }
}

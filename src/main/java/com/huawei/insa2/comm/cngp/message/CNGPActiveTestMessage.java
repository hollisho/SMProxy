package com.huawei.insa2.comm.cngp.message;

import java.io.IOException;
import com.huawei.insa2.comm.cngp.CNGPConstant;
import com.huawei.insa2.util.TypeConvert;
/**
 * 定义激活测试消息。
 * @author       linmeilong
 * @version      1.0
 */

public class CNGPActiveTestMessage extends CNGPMessage{
   /**
    * 定义一发送给SMC激活测试消息，消息体为空
    */
    public CNGPActiveTestMessage() {
        //计算出消息的长度，
        int len = 16;
        this.buf = new byte[len]; //根据消息长度开辟空间
        //填充消息头参数
        setMsgLength(len);
        setRequestId(CNGPConstant.Active_Test_Request_Id);
        //流水号8-11位为命令状态，此时不填充
        //流水号12-15位为流水号，发送时填充
    }
   /**
    * 返回消息体所有的字段与内容
    */
    public String toString(){
        StringBuffer strBuf = new StringBuffer(100);
        strBuf.append("CNGPActiveTestMessage: ");
        strBuf.append("PacketLength=" + this.getMsgLength());
        strBuf.append(",RequestID=" + this.getRequestId());
        strBuf.append(",Status=" + this.getStatus());
        strBuf.append(",SequenceId=" + this.getSequenceId());
        return strBuf.toString();
    }
}

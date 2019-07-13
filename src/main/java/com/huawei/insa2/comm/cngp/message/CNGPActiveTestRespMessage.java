package com.huawei.insa2.comm.cngp.message;



import java.io.IOException;
import com.huawei.insa2.comm.cngp.CNGPConstant;
import com.huawei.insa2.util.TypeConvert;
/**
 * 定义一激活测试响应消息。
 * @author       linmeilong
 * @version      1.0
 */

public class CNGPActiveTestRespMessage extends CNGPMessage{

   /**
    * 定义一条返回给SMC的激活响应消息
    * @param buf SMC发过来的数据
    */
    public CNGPActiveTestRespMessage(byte[] buf) throws IllegalArgumentException{

        this.buf = new byte[16];
        if( buf.length != 16 ){//流水号
            throw new IllegalArgumentException( CNGPConstant.SMC_MESSAGE_ERROR );
        }
        System.arraycopy(buf,0,this.buf,0,16);
    }

    /**
    * 返回消息体所有的字段与内容
    */
    public String toString(){
        StringBuffer strBuf = new StringBuffer(100);
        strBuf.append("CNGPActiveTestRespMessage: ");
        strBuf.append("PacketLength=" + this.getMsgLength());
        strBuf.append(",RequestID=" + this.getRequestId());
        strBuf.append(",Status=" + this.getStatus());
        strBuf.append(",SequenceId=" + this.getSequenceId());
        return strBuf.toString();
    }
}

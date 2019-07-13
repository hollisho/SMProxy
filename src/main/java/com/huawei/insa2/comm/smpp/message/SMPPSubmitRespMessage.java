// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   SMPPSubmitRespMessage.java

package com.huawei.insa2.comm.smpp.message;

import com.huawei.insa2.comm.smpp.SMPPConstant;

// Referenced classes of package com.huawei.insa2.comm.smpp.message:
//            SMPPMessage

public class SMPPSubmitRespMessage extends SMPPMessage
{

    public SMPPSubmitRespMessage(byte buf[])
        throws IllegalArgumentException
    {
        super.buf = new byte[25];
        if(buf.length < 17 || buf.length > 25)
        {
            throw new IllegalArgumentException(SMPPConstant.SMC_MESSAGE_ERROR);
        } else
        {
            System.arraycopy(buf, 0, super.buf, 0, buf.length);
            return;
        }
    }

    public String getMessageId()
    {
        return getFirstStr(buf, 16);
    }

    private String getFirstStr(byte buf[], int sPos)
    {
        int deli = 0;
        byte tmpBuf[] = new byte[21];
        int pos;
        for(pos = sPos; buf[pos] != 0 && pos < buf.length; pos++)
            tmpBuf[pos - sPos] = buf[pos];

        if(pos == sPos)
        {
            return "";
        } else
        {
            String tmpStr = new String(tmpBuf);
            return tmpStr.substring(0, pos - sPos);
        }
    }

    public String toString()
    {
        StringBuffer strBuf = new StringBuffer(200);
        strBuf.append("SMPPSubmitRespMessage: ");
        strBuf.append("PacketLength=".concat(String.valueOf(String.valueOf(getMsgLength()))));
        strBuf.append(",CommandID=".concat(String.valueOf(String.valueOf(getCommandId()))));
        strBuf.append(",Status=".concat(String.valueOf(String.valueOf(getStatus()))));
        strBuf.append(",SequenceId=".concat(String.valueOf(String.valueOf(getSequenceId()))));
        strBuf.append(",MessageId=".concat(String.valueOf(String.valueOf(new String(getMessageId())))));
        return strBuf.toString();
    }
}

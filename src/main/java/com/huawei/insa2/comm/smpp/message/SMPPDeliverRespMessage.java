// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   SMPPDeliverRespMessage.java

package com.huawei.insa2.comm.smpp.message;


// Referenced classes of package com.huawei.insa2.comm.smpp.message:
//            SMPPMessage

public class SMPPDeliverRespMessage extends SMPPMessage
{

    private StringBuffer strBuf;

    public SMPPDeliverRespMessage(int status)
        throws IllegalArgumentException
    {
        int len = 17;
        buf = new byte[17];
        setMsgLength(len);
        setCommandId(0x80000005);
        setStatus(status);
        buf[16] = 0;
        strBuf = new StringBuffer(100);
        strBuf.append(",MsgId= ");
    }

    public String toString()
    {
        StringBuffer outStr = new StringBuffer(100);
        outStr.append("SMPPDeliverRespMessage:");
        strBuf.append("PacketLength=".concat(String.valueOf(String.valueOf(getMsgLength()))));
        strBuf.append(",CommandID=".concat(String.valueOf(String.valueOf(getCommandId()))));
        strBuf.append(",Status=".concat(String.valueOf(String.valueOf(getStatus()))));
        outStr.append(",SequenceId=".concat(String.valueOf(String.valueOf(getSequenceId()))));
        if(strBuf != null)
            outStr.append(strBuf.toString());
        return outStr.toString();
    }
}

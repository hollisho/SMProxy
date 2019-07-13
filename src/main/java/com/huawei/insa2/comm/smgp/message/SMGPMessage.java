// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   SMGPMessage.java

package com.huawei.insa2.comm.smgp.message;

import com.huawei.insa2.comm.PMessage;
import com.huawei.insa2.util.TypeConvert;

public abstract class SMGPMessage extends PMessage
    implements Cloneable
{

    protected byte buf[];
    protected int sequence_Id;

    public SMGPMessage()
    {
    }

    public Object clone()
    {
        try
        {
            SMGPMessage m = (SMGPMessage)super.clone();
            m.buf = (byte[])buf.clone();
            SMGPMessage smgpmessage = m;
            return smgpmessage;
        }
        catch(CloneNotSupportedException ex)
        {
            ex.printStackTrace();
        }
        Object obj = null;
        return obj;
    }

    public abstract String toString();

    public abstract int getRequestId();

    public int getSequenceId()
    {
        return sequence_Id;
    }

    public void setSequenceId(int sequence_Id)
    {
        this.sequence_Id = sequence_Id;
        TypeConvert.int2byte(sequence_Id, buf, 8);
    }

    public byte[] getBytes()
    {
        return buf;
    }
}

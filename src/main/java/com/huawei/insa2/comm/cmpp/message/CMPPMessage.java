// Decompiled by DJ v2.9.9.60 Copyright 2000 Atanas Neshkov  Date: 2005-7-11 17:43:13
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CMPPMessage.java

package com.huawei.insa2.comm.cmpp.message;

import com.huawei.insa2.comm.PMessage;
import com.huawei.insa2.util.TypeConvert;

public abstract class CMPPMessage extends PMessage
    implements Cloneable
{

    public CMPPMessage()
    {
    }

    public Object clone()
    {
        try
        {
            CMPPMessage m = (CMPPMessage)super.clone();
            m.buf = (byte[])buf.clone();
            CMPPMessage cmppmessage = m;
            return cmppmessage;
        }
        catch(CloneNotSupportedException ex)
        {
            ex.printStackTrace();
        }
        Object obj = null;
        return obj;
    }

    public abstract String toString();

    public abstract int getCommandId();

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

    protected byte buf[];
    protected int sequence_Id;
}
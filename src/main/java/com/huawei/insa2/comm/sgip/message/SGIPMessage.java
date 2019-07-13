// Decompiled by DJ v2.9.9.60 Copyright 2000 Atanas Neshkov  Date: 2005-7-16 19:20:59
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   SGIPMessage.java

package com.huawei.insa2.comm.sgip.message;

import com.huawei.insa2.comm.PMessage;
import com.huawei.insa2.util.TypeConvert;

public abstract class SGIPMessage extends PMessage
    implements Cloneable
{

    public SGIPMessage()
    {
    }

    public Object clone()
    {
        try
        {
            SGIPMessage m = (SGIPMessage)super.clone();
            m.buf = (byte[])buf.clone();
            SGIPMessage sgipmessage = m;
            return sgipmessage;
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

    public int getSrcNodeId()
    {
        return src_node_Id;
    }

    public void setSrcNodeId(int src_node_Id)
    {
        this.src_node_Id = src_node_Id;
        TypeConvert.int2byte(src_node_Id, buf, 8);
    }

    public int getTimeStamp()
    {
        return time_Stamp;
    }

    public void setTimeStamp(int time_Stamp)
    {
        this.time_Stamp = time_Stamp;
        TypeConvert.int2byte(time_Stamp, buf, 12);
    }

    public int getSequenceId()
    {
        return sequence_Id;
    }

    public void setSequenceId(int sequence_Id)
    {
        this.sequence_Id = sequence_Id;
        TypeConvert.int2byte(sequence_Id, buf, 16);
    }

    public byte[] getBytes()
    {
        return buf;
    }

    protected byte buf[];
    protected int src_node_Id;
    protected int time_Stamp;
    protected int sequence_Id;
}
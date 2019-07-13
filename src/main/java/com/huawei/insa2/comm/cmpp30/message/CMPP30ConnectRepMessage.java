// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   CMPP30ConnectRepMessage.java

package com.huawei.insa2.comm.cmpp30.message;

import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.util.TypeConvert;

public class CMPP30ConnectRepMessage extends CMPPMessage
{

    public CMPP30ConnectRepMessage(byte buf[])
        throws IllegalArgumentException
    {
        super.buf = new byte[25];
        if(buf.length != 25)
        {
            throw new IllegalArgumentException(CMPPConstant.SMC_MESSAGE_ERROR);
        } else
        {
            System.arraycopy(buf, 0, super.buf, 0, buf.length);
            sequence_Id = TypeConvert.byte2int(super.buf, 0);
            return;
        }
    }

    public int getStatus()
    {
        return TypeConvert.byte2int(buf, 4);
    }

    public byte[] getAuthenticatorISMG()
    {
        byte tmpbuf[] = new byte[16];
        System.arraycopy(buf, 8, tmpbuf, 0, 16);
        return tmpbuf;
    }

    public byte getVersion()
    {
        return buf[24];
    }

    public String toString()
    {
        String tmpStr = "CMPP_Connect_REP: ";
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append("Sequence_Id=").append(getSequenceId())));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",Status=").append(getStatus())));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",AuthenticatorISMG=").append(new String(getAuthenticatorISMG()))));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",Version=").append(getVersion())));
        return tmpStr;
    }

    public int getCommandId()
    {
        return 0x80000001;
    }
}

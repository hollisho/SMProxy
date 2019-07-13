// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   CMPP30DeliverRepMessage.java

package com.huawei.insa2.comm.cmpp30.message;

import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.util.TypeConvert;

public class CMPP30DeliverRepMessage extends CMPPMessage
{

    private String outStr;

    public CMPP30DeliverRepMessage(byte msg_Id[], int result)
        throws IllegalArgumentException
    {
        if(msg_Id.length > 8)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.DELIVER_REPINPUT_ERROR)))).append(":msg_Id").append(CMPPConstant.STRING_LENGTH_GREAT).append("8"))));
        if(result < 0 || result > 0x7fffffff)
        {
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.DELIVER_REPINPUT_ERROR)))).append(":result").append(CMPPConstant.INT_SCOPE_ERROR))));
        } else
        {
            int len = 24;
            buf = new byte[len];
            TypeConvert.int2byte(len, buf, 0);
            TypeConvert.int2byte(0x80000005, buf, 4);
            System.arraycopy(msg_Id, 0, buf, 12, msg_Id.length);
            TypeConvert.int2byte(result, buf, 20);
            outStr = ",result=".concat(String.valueOf(String.valueOf(result)));
            return;
        }
    }

    public String toString()
    {
        String tmpStr = "CMPP_Deliver_REP: ";
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append("Sequence_Id=").append(getSequenceId())));
        tmpStr = String.valueOf(tmpStr) + String.valueOf(outStr);
        return tmpStr;
    }

    public int getCommandId()
    {
        return 0x80000005;
    }
}

// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   SMGPDeliverRespMessage.java

package com.huawei.insa2.comm.smgp.message;

import com.huawei.insa2.comm.smgp.SMGPConstant;
import com.huawei.insa2.util.TypeConvert;

// Referenced classes of package com.huawei.insa2.comm.smgp.message:
//            SMGPMessage

public class SMGPDeliverRespMessage extends SMGPMessage
{

    private StringBuffer strBuf;

    public SMGPDeliverRespMessage(byte msg_Id[], int status)
        throws IllegalArgumentException
    {
        if(msg_Id.length > 10)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.DELIVER_REPINPUT_ERROR)))).append(":msg_Id").append(SMGPConstant.STRING_LENGTH_GREAT).append("10"))));
        if(status < 0)
        {
            throw new IllegalArgumentException(String.valueOf(String.valueOf(SMGPConstant.DELIVER_REPINPUT_ERROR)).concat(":result"));
        } else
        {
            int len = 26;
            buf = new byte[len];
            TypeConvert.int2byte(len, buf, 0);
            TypeConvert.int2byte(0x80000003, buf, 4);
            System.arraycopy(msg_Id, 0, buf, 12, msg_Id.length);
            TypeConvert.int2byte(status, buf, 22);
            strBuf = new StringBuffer(100);
            strBuf.append(",status=".concat(String.valueOf(String.valueOf(status))));
            return;
        }
    }

    public String toString()
    {
        StringBuffer outStr = new StringBuffer(100);
        outStr.append("SMGPDeliverRespMessage:");
        outStr.append(",Sequence_Id=".concat(String.valueOf(String.valueOf(getSequenceId()))));
        if(strBuf != null)
            outStr.append(strBuf.toString());
        return outStr.toString();
    }

    public int getRequestId()
    {
        return 0x80000003;
    }
}

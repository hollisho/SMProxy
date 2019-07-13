// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   SMGPQueryMessage.java

package com.huawei.insa2.comm.smgp.message;

import com.huawei.insa2.comm.smgp.SMGPConstant;
import com.huawei.insa2.util.TypeConvert;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// Referenced classes of package com.huawei.insa2.comm.smgp.message:
//            SMGPMessage

public class SMGPQueryMessage extends SMGPMessage
{

    private StringBuffer strBuf;

    public SMGPQueryMessage(Date time, int queryType, String queryCode)
        throws IllegalArgumentException
    {
        if(time == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.QUERY_INPUT_ERROR)))).append(":Time ").append(SMGPConstant.STRING_NULL))));
        if(queryType < 0 || queryType > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.QUERY_INPUT_ERROR)))).append(":QueryType ").append(SMGPConstant.INT_SCOPE_ERROR))));
        if(queryCode != null && queryCode.length() > 10)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.QUERY_INPUT_ERROR)))).append(":QueryCode ").append(SMGPConstant.STRING_LENGTH_GREAT).append("10"))));
        int len = 31;
        buf = new byte[len];
        TypeConvert.int2byte(len, buf, 0);
        TypeConvert.int2byte(7, buf, 4);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        System.arraycopy(dateFormat.format(time).getBytes(), 0, buf, 12, dateFormat.format(time).length());
        buf[20] = (byte)queryType;
        if(queryCode != null)
            System.arraycopy(queryCode.getBytes(), 0, buf, 21, queryCode.length());
        strBuf = new StringBuffer(200);
        strBuf.append(",time=".concat(String.valueOf(String.valueOf(time))));
        strBuf.append(",Query_Type=".concat(String.valueOf(String.valueOf(queryType))));
        strBuf.append(",Query_Code=".concat(String.valueOf(String.valueOf(queryCode))));
    }

    public String toString()
    {
        StringBuffer outStr = new StringBuffer(200);
        outStr.append("SMGPQueryMessage:");
        outStr.append("PacketLength=".concat(String.valueOf(String.valueOf(buf.length))));
        outStr.append(",RequestID=7");
        outStr.append(",Sequence_Id=".concat(String.valueOf(String.valueOf(getSequenceId()))));
        if(strBuf != null)
            outStr.append(strBuf.toString());
        return outStr.toString();
    }

    public int getRequestId()
    {
        return 7;
    }
}

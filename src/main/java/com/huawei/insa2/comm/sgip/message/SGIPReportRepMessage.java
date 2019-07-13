// Decompiled by DJ v2.9.9.60 Copyright 2000 Atanas Neshkov  Date: 2005-7-16 19:21:00
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   SGIPReportRepMessage.java

package com.huawei.insa2.comm.sgip.message;

import com.huawei.insa2.comm.sgip.SGIPConstant;
import com.huawei.insa2.util.TypeConvert;

// Referenced classes of package com.huawei.insa2.comm.sgip.message:
//            SGIPMessage

public class SGIPReportRepMessage extends SGIPMessage
{

    public SGIPReportRepMessage(int result)
        throws IllegalArgumentException
    {
        if(result < 0 || result > 255)
        {
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.REPORT_REPINPUT_ERROR)))).append(":result").append(SGIPConstant.INT_SCOPE_ERROR))));
        } else
        {
            int len = 29;
            super.buf = new byte[len];
            TypeConvert.int2byte(len, super.buf, 0);
            TypeConvert.int2byte(0x80000005, super.buf, 4);
            super.buf[20] = (byte)result;
            outStr = ",result=".concat(String.valueOf(String.valueOf(result)));
            return;
        }
    }

    public String toString()
    {
        String tmpStr = "SGIP_REPORT_REP: ";
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append("Sequence_Id=").append(getSequenceId())));
        tmpStr = String.valueOf(tmpStr) + String.valueOf(outStr);
        return tmpStr;
    }

    public int getCommandId()
    {
        return 0x80000005;
    }

    private String outStr;
}
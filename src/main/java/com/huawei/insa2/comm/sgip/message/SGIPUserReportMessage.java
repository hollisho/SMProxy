// Decompiled by DJ v2.9.9.60 Copyright 2000 Atanas Neshkov  Date: 2005-7-16 19:21:03
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   SGIPUserReportMessage.java

package com.huawei.insa2.comm.sgip.message;

import com.huawei.insa2.comm.sgip.SGIPConstant;
import com.huawei.insa2.util.TypeConvert;

// Referenced classes of package com.huawei.insa2.comm.sgip.message:
//            SGIPMessage

public class SGIPUserReportMessage extends SGIPMessage
{

    public SGIPUserReportMessage(byte buf[])
        throws IllegalArgumentException
    {
        super.buf = new byte[63];
        if(buf.length != 63)
        {
            throw new IllegalArgumentException(SGIPConstant.SMC_MESSAGE_ERROR);
        } else
        {
            System.arraycopy(buf, 0, super.buf, 0, 63);
            super.src_node_Id = TypeConvert.byte2int(super.buf, 0);
            super.time_Stamp = TypeConvert.byte2int(super.buf, 4);
            super.sequence_Id = TypeConvert.byte2int(super.buf, 8);
            return;
        }
    }

    public String getSPNumber()
    {
        byte tmpId[] = new byte[21];
        System.arraycopy(super.buf, 12, tmpId, 0, 21);
        String tmpStr = (new String(tmpId)).trim();
        if(tmpStr.indexOf('\0') >= 0)
            return tmpStr.substring(0, tmpStr.indexOf('\0'));
        else
            return tmpStr;
    }

    public String getUserNumber()
    {
        byte tmpId[] = new byte[21];
        System.arraycopy(super.buf, 33, tmpId, 0, 21);
        String tmpStr = (new String(tmpId)).trim();
        if(tmpStr.indexOf('\0') >= 0)
            return tmpStr.substring(0, tmpStr.indexOf('\0'));
        else
            return tmpStr;
    }

    public int getUserCondition()
    {
        int tmpId = super.buf[54];
        return tmpId;
    }

    public String getReserve()
    {
        byte tmpReserve[] = new byte[8];
        System.arraycopy(super.buf, 48, tmpReserve, 0, 8);
        return (new String(tmpReserve)).trim();
    }

    public String toString()
    {
        String tmpStr = "SGIP_USERREPORT: ";
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append("Sequence_Id=").append(getSequenceId())));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",SPNumber=").append(getSPNumber())));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",UserNumber=").append(getUserNumber())));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",UserCondition=").append(getUserCondition())));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",Reserve=").append(getReserve())));
        return tmpStr;
    }

    public int getCommandId()
    {
        return 17;
    }
}
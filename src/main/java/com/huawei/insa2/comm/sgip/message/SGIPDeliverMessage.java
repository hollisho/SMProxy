// Decompiled by DJ v2.9.9.60 Copyright 2000 Atanas Neshkov  Date: 2005-7-16 19:20:58
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3)
// Source File Name:   SGIPDeliverMessage.java

package com.huawei.insa2.comm.sgip.message;

import com.huawei.insa2.comm.sgip.SGIPConstant;
import com.huawei.insa2.util.TypeConvert;

// Referenced classes of package com.huawei.insa2.comm.sgip.message:
//            SGIPMessage

public class SGIPDeliverMessage extends SGIPMessage
{

    public SGIPDeliverMessage(byte buf[])
        throws IllegalArgumentException
    {
        int len = TypeConvert.byte2int(buf, 57);
        len = 69 + len;
        if(buf.length != len)
        {
            throw new IllegalArgumentException(SGIPConstant.SMC_MESSAGE_ERROR);
        } else
        {
            super.buf = new byte[len];
            System.arraycopy(buf, 0, super.buf, 0, buf.length);
            super.src_node_Id = TypeConvert.byte2int(super.buf, 0);
            super.time_Stamp = TypeConvert.byte2int(super.buf, 4);
            super.sequence_Id = TypeConvert.byte2int(super.buf, 8);
            return;
        }
    }

    public String getUserNumber()
    {
        byte tmpId[] = new byte[21];
        System.arraycopy(super.buf, 12, tmpId, 0, 21);
        String tmpStr = (new String(tmpId)).trim();
        if(tmpStr.indexOf('\0') >= 0)
            return tmpStr.substring(0, tmpStr.indexOf('\0'));
        else
            return tmpStr;
    }

    public String getSPNumber()
    {
        byte tmpId[] = new byte[21];
        System.arraycopy(super.buf, 33, tmpId, 0, 21);
        String tmpStr = (new String(tmpId)).trim();
        if(tmpStr.indexOf('\0') >= 0)
            return tmpStr.substring(0, tmpStr.indexOf('\0'));
        else
            return tmpStr;
    }

    public int getTpPid()
    {
        int tmpId = super.buf[54];
        return tmpId;
    }

    public int getTpUdhi()
    {
        int tmpId = super.buf[55];
        return tmpId;
    }

    public int getMsgFmt()
    {
        int tmpFmt = super.buf[56];
        return tmpFmt;
    }

    public int getMsgLength()
    {
        return TypeConvert.byte2int(super.buf, 57);
    }

    public byte[] getMsgContent()
    {
        int len = getMsgLength();
        byte tmpContent[] = new byte[len];
        System.arraycopy(super.buf, 61, tmpContent, 0, len);
        return tmpContent;
    }

    public String getReserve()
    {
        int loc = 61 + getMsgLength();
        byte tmpReserve[] = new byte[8];
        System.arraycopy(super.buf, loc, tmpReserve, 0, 8);
        //return (new String(tmpReserve)).trim();//by lhj
        return new String(tmpReserve);
    }

    public String toString()
    {
        String tmpStr = "SGIP_DELIVER: ";
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append("Sequence_Id=").append(getSequenceId())));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",UserNumber=").append(getUserNumber())));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",SPNumber=").append(getSPNumber())));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",TpPid=").append(getTpPid())));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",TpUdhi=").append(getTpUdhi())));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",MsgFmt=").append(getMsgFmt())));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",MsgLength=").append(getMsgLength())));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",MsgContent=").append(new String(getMsgContent()))));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",Reserve=").append(getReserve())));
        return tmpStr;
    }

    public int getCommandId()
    {
        return 4;
    }
}
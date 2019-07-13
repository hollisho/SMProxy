// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   CMPP30DeliverMessage.java

package com.huawei.insa2.comm.cmpp30.message;

import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.util.TypeConvert;
import java.util.Calendar;
import java.util.Date;

public class CMPP30DeliverMessage extends CMPPMessage
{

    private int deliverType;

    public CMPP30DeliverMessage(byte buf[])
        throws IllegalArgumentException
    {
        deliverType = 0;
        deliverType = buf[79];
        int len = 101 + (buf[80] & 0xff);
        if(buf.length != len)
        {
            throw new IllegalArgumentException(CMPPConstant.SMC_MESSAGE_ERROR);
        } else
        {
            super.buf = new byte[len];
            System.arraycopy(buf, 0, super.buf, 0, buf.length);
            sequence_Id = TypeConvert.byte2int(super.buf, 0);
            return;
        }
    }

    public byte[] getMsgId()
    {
        byte tmpMsgId[] = new byte[8];
        System.arraycopy(buf, 4, tmpMsgId, 0, 8);
        return tmpMsgId;
    }

    public String getDestnationId()
    {
        byte tmpId[] = new byte[21];
        System.arraycopy(buf, 12, tmpId, 0, 21);
        return (new String(tmpId)).trim();
    }

    public String getServiceId()
    {
        byte tmpId[] = new byte[10];
        System.arraycopy(buf, 33, tmpId, 0, 10);
        return (new String(tmpId)).trim();
    }

    public int getTpPid()
    {
        int tmpId = buf[43];
        return tmpId;
    }

    public int getTpUdhi()
    {
        int tmpId = buf[44];
        return tmpId;
    }

    public int getMsgFmt()
    {
        int tmpFmt = buf[45];
        return tmpFmt;
    }

    public String getSrcterminalId()
    {
        byte tmpId[] = new byte[32];
        System.arraycopy(buf, 46, tmpId, 0, 32);
        return (new String(tmpId)).trim();
    }

    public int getSrcterminalType()
    {
        int tmpFmt = buf[78];
        return tmpFmt;
    }

    public int getRegisteredDeliver()
    {
        return buf[79];
    }

    public int getMsgLength()
    {
        return buf[80] & 0xff;
    }

    public byte[] getMsgContent()
    {
        if(deliverType == 0)
        {
            int len = getMsgLength();
            byte tmpContent[] = new byte[len];
            System.arraycopy(buf, 81, tmpContent, 0, len);
            return tmpContent;
        } else
        {
            return null;
        }
    }

    public String getLinkID()
    {
        int loc = 81 + getMsgLength();
        byte tmpReserve[] = new byte[20];
        System.arraycopy(buf, loc, tmpReserve, 0, 20);
        return (new String(tmpReserve)).trim();
    }

    public byte[] getStatusMsgId()
    {
        if(deliverType == 1)
        {
            byte tmpId[] = new byte[8];
            System.arraycopy(buf, 81, tmpId, 0, 8);
            return tmpId;
        } else
        {
            return null;
        }
    }

    public String getStat()
    {
        if(deliverType == 1)
        {
            byte tmpStat[] = new byte[7];
            System.arraycopy(buf, 89, tmpStat, 0, 7);
            return (new String(tmpStat)).trim();
        } else
        {
            return null;
        }
    }

    public Date getSubmitTime()
    {
        if(deliverType == 1)
        {
            byte tmpbyte[] = new byte[2];
            System.arraycopy(buf, 96, tmpbyte, 0, 2);
            String tmpstr = new String(tmpbyte);
            int tmpYear = 2000 + Integer.parseInt(tmpstr);
            System.arraycopy(buf, 98, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpMonth = Integer.parseInt(tmpstr) - 1;
            System.arraycopy(buf, 100, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpDay = Integer.parseInt(tmpstr);
            System.arraycopy(buf, 102, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpHour = Integer.parseInt(tmpstr);
            System.arraycopy(buf, 104, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpMinute = Integer.parseInt(tmpstr);
            Calendar calendar = Calendar.getInstance();
            calendar.set(tmpYear, tmpMonth, tmpDay, tmpHour, tmpMinute);
            return calendar.getTime();
        } else
        {
            return null;
        }
    }

    public Date getDoneTime()
    {
        if(deliverType == 1)
        {
            byte tmpbyte[] = new byte[2];
            System.arraycopy(buf, 106, tmpbyte, 0, 2);
            String tmpstr = new String(tmpbyte);
            int tmpYear = 2000 + Integer.parseInt(tmpstr);
            System.arraycopy(buf, 108, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpMonth = Integer.parseInt(tmpstr) - 1;
            System.arraycopy(buf, 110, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpDay = Integer.parseInt(tmpstr);
            System.arraycopy(buf, 112, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpHour = Integer.parseInt(tmpstr);
            System.arraycopy(buf, 114, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpMinute = Integer.parseInt(tmpstr);
            Calendar calendar = Calendar.getInstance();
            calendar.set(tmpYear, tmpMonth, tmpDay, tmpHour, tmpMinute);
            return calendar.getTime();
        } else
        {
            return null;
        }
    }

    public String getDestTerminalId()
    {
        if(deliverType == 1)
        {
            byte tmpId[] = new byte[32];
            System.arraycopy(buf, 116, tmpId, 0, 32);
            return (new String(tmpId)).trim();
        } else
        {
            return null;
        }
    }

    public int getSMSCSequence()
    {
        if(deliverType == 1)
        {
            int tmpSequence = TypeConvert.byte2int(buf, 148);
            return tmpSequence;
        } else
        {
            return -1;
        }
    }

    public String toString()
    {
        String tmpStr = "CMPP_Deliver: ";
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append("Sequence_Id=").append(getSequenceId())));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",MsgId=").append(new String(getMsgId()))));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",DestnationId=").append(getDestnationId())));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",ServiceId=").append(getServiceId())));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",TpPid=").append(getTpPid())));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",TpUdhi=").append(getTpUdhi())));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",MsgFmt=").append(getMsgFmt())));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",SrcterminalId=").append(getSrcterminalId())));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",SrcterminalType=").append(getSrcterminalType())));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",RegisteredDeliver=").append(getRegisteredDeliver())));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",MsgLength=").append(getMsgLength())));
        if(getRegisteredDeliver() == 1)
        {
            tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",Stat=").append(getStat())));
            tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",SubmitTime=").append(getSubmitTime())));
            tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",DoneTime=").append(getDoneTime())));
            tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",DestTerminalId=").append(getDestTerminalId())));
            tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",SMSCSequence=").append(getSMSCSequence())));
        } else
        {
            tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",MsgContent=").append(getMsgContent())));
        }
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",LinkID=").append(getLinkID())));
        return tmpStr;
    }

    public int getCommandId()
    {
        return 5;
    }
}

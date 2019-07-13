// Decompiled by DJ v2.9.9.60 Copyright 2000 Atanas Neshkov  Date: 2005-7-11 17:43:12
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CMPPDeliverMessage.java

package com.huawei.insa2.comm.cmpp.message;

import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.util.TypeConvert;
import java.util.Calendar;
import java.util.Date;

// Referenced classes of package com.huawei.insa2.comm.cmpp.message:
//            CMPPMessage

public class CMPPDeliverMessage extends CMPPMessage
{

    public CMPPDeliverMessage(byte buf[])
        throws IllegalArgumentException
    {
        deliverType = 0;
        deliverType = buf[67];
        int len = 77 + (buf[68] & 0xff);
        if(buf.length != len)
        {
            throw new IllegalArgumentException(CMPPConstant.SMC_MESSAGE_ERROR);
        } else
        {
            super.buf = new byte[len];
            System.arraycopy(buf, 0, super.buf, 0, buf.length);
            super.sequence_Id = TypeConvert.byte2int(super.buf, 0);
            return;
        }
    }

    public byte[] getMsgId()
    {
        byte tmpMsgId[] = new byte[8];
        System.arraycopy(super.buf, 4, tmpMsgId, 0, 8);
        return tmpMsgId;
    }

    public String getDestnationId()
    {
        byte tmpId[] = new byte[21];
        System.arraycopy(super.buf, 12, tmpId, 0, 21);
        return (new String(tmpId)).trim();
    }

    public String getServiceId()
    {
        byte tmpId[] = new byte[10];
        System.arraycopy(super.buf, 33, tmpId, 0, 10);
        return (new String(tmpId)).trim();
    }

    public int getTpPid()
    {
        int tmpId = super.buf[43];
        return tmpId;
    }

    public int getTpUdhi()
    {
        int tmpId = super.buf[44];
        return tmpId;
    }

    public int getMsgFmt()
    {
        int tmpFmt = super.buf[45];
        return tmpFmt;
    }

    public String getSrcterminalId()
    {
        byte tmpId[] = new byte[21];
        System.arraycopy(super.buf, 46, tmpId, 0, 21);
        return (new String(tmpId)).trim();
    }

    public int getRegisteredDeliver()
    {
        return super.buf[67];
    }

    public int getMsgLength()
    {
        return super.buf[68] & 0xff;
    }

    public byte[] getMsgContent()
    {
        if(deliverType == 0)
        {
            int len = super.buf[68] & 0xff;
            byte tmpContent[] = new byte[len];
            System.arraycopy(super.buf, 69, tmpContent, 0, len);
            return tmpContent;
        } else
        {
            return null;
        }
    }

    public String getReserve()
    {
        int loc = 69 + (super.buf[68] & 0xff);
        byte tmpReserve[] = new byte[8];
        System.arraycopy(super.buf, loc, tmpReserve, 0, 8);
        return (new String(tmpReserve)).trim();
    }

    public byte[] getStatusMsgId()
    {
        if(deliverType == 1)
        {
            byte tmpId[] = new byte[8];
            System.arraycopy(super.buf, 69, tmpId, 0, 8);
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
            System.arraycopy(super.buf, 77, tmpStat, 0, 7);
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
            System.arraycopy(super.buf, 84, tmpbyte, 0, 2);
            String tmpstr = new String(tmpbyte);
            int tmpYear = 2000 + Integer.parseInt(tmpstr);
            System.arraycopy(super.buf, 86, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpMonth = Integer.parseInt(tmpstr) - 1;
            System.arraycopy(super.buf, 88, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpDay = Integer.parseInt(tmpstr);
            System.arraycopy(super.buf, 90, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpHour = Integer.parseInt(tmpstr);
            System.arraycopy(super.buf, 92, tmpbyte, 0, 2);
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
            System.arraycopy(super.buf, 94, tmpbyte, 0, 2);
            String tmpstr = new String(tmpbyte);
            int tmpYear = 2000 + Integer.parseInt(tmpstr);
            System.arraycopy(super.buf, 96, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpMonth = Integer.parseInt(tmpstr) - 1;
            System.arraycopy(super.buf, 98, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpDay = Integer.parseInt(tmpstr);
            System.arraycopy(super.buf, 100, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpHour = Integer.parseInt(tmpstr);
            System.arraycopy(super.buf, 102, tmpbyte, 0, 2);
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
            byte tmpId[] = new byte[21];
            System.arraycopy(super.buf, 104, tmpId, 0, 21);
            return new String(tmpId);
        } else
        {
            return null;
        }
    }

    public int getSMSCSequence()
    {
        if(deliverType == 1)
        {
            int tmpSequence = TypeConvert.byte2int(super.buf, 125);
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
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",Reserve=").append(getReserve())));
        return tmpStr;
    }

    public int getCommandId()
    {
        return 5;
    }

    private int deliverType;
}
// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi
// Source File Name:   SMPPDeliverMessage.java

package com.huawei.insa2.comm.smpp.message;

import com.huawei.insa2.comm.smpp.SMPPConstant;

// Referenced classes of package com.huawei.insa2.comm.smpp.message:
//            SMPPMessage

public class SMPPDeliverMessage extends SMPPMessage
{

    private String serviceType;
    private int sourceAddrTon;
    private int sourceAddrNpi;
    private String sourceAddr;
    private int destAddrTon;
    private int destAddrNpi;
    private String destinationAddr;
    private int esmClass;
    private int protocolId;
    private int priorityFlag;
    private int registeredDelivery;
    private int dataCoding;
    private int smDefaultMsgId; // not used
    private int smLength;
    private String shortMessage;

    public SMPPDeliverMessage(byte buf[])
        throws IllegalArgumentException
    {
        int len = buf.length;
        if(buf.length > 332 || buf.length < 33)
        {
            throw new IllegalArgumentException(SMPPConstant.SMC_MESSAGE_ERROR);
        } else
        {
            super.buf = new byte[len];
            System.arraycopy(buf, 0, super.buf, 0, buf.length);
            int pos = 16;
            serviceType = getFirstStr(buf, pos);
            pos = pos + serviceType.length() + 1;
            sourceAddrTon = buf[pos];
            sourceAddrNpi = buf[pos + 1];
            pos += 2;
            sourceAddr = getFirstStr(buf, pos);
            pos = pos + sourceAddr.length() + 1;
            destAddrTon = buf[pos];
            destAddrNpi = buf[pos + 1];
            pos += 2;
            destinationAddr = getFirstStr(buf, pos);
            pos = pos + destinationAddr.length() + 1;
            esmClass = buf[pos];
            protocolId = buf[pos + 1];
            priorityFlag = buf[pos + 2];
            registeredDelivery = buf[pos + 5];
            dataCoding = buf[pos + 7];
            smDefaultMsgId = buf[pos + 8];
            smLength = buf[pos + 9];
            byte tmpBuf[] = new byte[smLength];
            System.arraycopy(buf, pos + 10, tmpBuf, 0, smLength);
            /*
            smDefaultMsgId = buf[pos + 9];
            smLength = buf[pos + 10];
            byte tmpBuf[] = new byte[smLength];
            System.arraycopy(buf, pos + 11, tmpBuf, 0, smLength);
*/
            shortMessage = new String(tmpBuf);
            return;
        }
    }

    private String getFirstStr(byte buf[], int sPos)
    {
        int deli = 0;
        byte tmpBuf[] = new byte[21];
        int pos;
        for(pos = sPos; buf[pos] != 0 && pos < buf.length; pos++)
            tmpBuf[pos - sPos] = buf[pos];

        if(pos == sPos)
        {
            return "";
        } else
        {
            String tmpStr = new String(tmpBuf);
            return tmpStr.substring(0, pos - sPos);
        }
    }

    public String getServiceType()
    {
        return serviceType;
    }

    public int getSourceAddrTon()
    {
        return sourceAddrTon;
    }

    public int getSourceAddrNpi()
    {
        return sourceAddrNpi;
    }

    public String getSourceAddr()
    {
        return sourceAddr;
    }

    public int getDestAddrTon()
    {
        return destAddrTon;
    }

    public int getDestAddrNpi()
    {
        return destAddrNpi;
    }

    public String getDestinationAddr()
    {
        return destinationAddr;
    }

    public int getEsmClass()
    {
        return esmClass;
    }

    public int getProtocolId()
    {
        return protocolId;
    }

    public int getPriorityFlag()
    {
        return priorityFlag;
    }

    public int getRegisteredDelivery()
    {
        return registeredDelivery;
    }

    public int getDataCoding()
    {
        return dataCoding;
    }

    public int getSmDefaultMsgId()
    {
        return smDefaultMsgId;
    }

    public int getSmLength()
    {
        return smLength;
    }

    public String getShortMessage()
    {
        return shortMessage;
    }

    public String toString()
    {
        StringBuffer strBuf = new StringBuffer(600);
        strBuf.append("SMPPDeliverMessage: ");
        strBuf.append("PacketLength=".concat(String.valueOf(String.valueOf(getMsgLength()))));
        strBuf.append(",CommandID=".concat(String.valueOf(String.valueOf(getCommandId()))));
        strBuf.append(",Status=".concat(String.valueOf(String.valueOf(getStatus()))));
        strBuf.append(",Sequence_Id=".concat(String.valueOf(String.valueOf(getSequenceId()))));
        strBuf.append(",Service_Type=".concat(String.valueOf(String.valueOf(getServiceType()))));
        strBuf.append(",SourceAddrTon=".concat(String.valueOf(String.valueOf(getSourceAddrTon()))));
        strBuf.append(",SourceAddrNpi=".concat(String.valueOf(String.valueOf(getSourceAddrNpi()))));
        strBuf.append(",SourceAddr=".concat(String.valueOf(String.valueOf(getSourceAddr()))));
        strBuf.append(",DestAddrTon=".concat(String.valueOf(String.valueOf(getDestAddrTon()))));
        strBuf.append(",DestAddrNpi=".concat(String.valueOf(String.valueOf(getDestAddrNpi()))));
        strBuf.append(",DestinationAddr=".concat(String.valueOf(String.valueOf(getDestinationAddr()))));
        strBuf.append(",EsmClass=".concat(String.valueOf(String.valueOf(getEsmClass()))));
        strBuf.append(",ProtocolId=".concat(String.valueOf(String.valueOf(getProtocolId()))));
        strBuf.append(",PriorityFlag=".concat(String.valueOf(String.valueOf(getPriorityFlag()))));
        strBuf.append(",RegisteredDelivery=".concat(String.valueOf(String.valueOf(getRegisteredDelivery()))));
        strBuf.append(",DataCoding=".concat(String.valueOf(String.valueOf(getDataCoding()))));
        strBuf.append(",smDefaultMsgId=".concat(String.valueOf(String.valueOf(getSmDefaultMsgId()))));
        strBuf.append(",SmLength=".concat(String.valueOf(String.valueOf(getSmLength()))));
        strBuf.append(",ShortMessage=".concat(String.valueOf(String.valueOf(getShortMessage()))));
        return strBuf.toString();
    }
}

// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   SMPPSubmitMessage.java

package com.huawei.insa2.comm.smpp.message;

import com.huawei.insa2.comm.smpp.SMPPConstant;

// Referenced classes of package com.huawei.insa2.comm.smpp.message:
//            SMPPMessage

public class SMPPSubmitMessage extends SMPPMessage
{

    private StringBuffer strBuf;

    public SMPPSubmitMessage(String serviceType, byte sourceAddrTon, byte sourceAddrNpi, String sourceAddr, byte destAddrTon, byte destAddrNpi, String destinationAddr, 
            byte esmClass, byte protocolId, byte priorityFlag, String scheduleDeliveryTime, String validityPeriod, byte registeredDelivery, byte replaceIfPresentFlag, 
            byte dataCoding, byte smDefaultMsgId, byte smLength, String shortMessage)
        throws IllegalArgumentException
    {
        if(serviceType.length() > 5)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMPPConstant.SUBMIT_INPUT_ERROR)))).append(":serviceType ").append(SMPPConstant.STRING_LENGTH_GREAT).append("5"))));
        if(sourceAddr.length() > 20)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMPPConstant.SUBMIT_INPUT_ERROR)))).append(":sourceAddr ").append(SMPPConstant.STRING_LENGTH_GREAT).append("20"))));
        if(destinationAddr == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMPPConstant.SUBMIT_INPUT_ERROR)))).append(":destinationAddr ").append(SMPPConstant.STRING_NULL))));
        if(destinationAddr.length() > 20)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMPPConstant.SUBMIT_INPUT_ERROR)))).append(":destinationAddr ").append(SMPPConstant.STRING_LENGTH_GREAT).append("20"))));
        if(scheduleDeliveryTime.length() != 0 && scheduleDeliveryTime.length() != 16)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMPPConstant.SUBMIT_INPUT_ERROR)))).append(":scheduleDeliveryTime ").append(SMPPConstant.STRING_LENGTH_NOTEQUAL).append("16"))));
        if(validityPeriod.length() != 0 && validityPeriod.length() != 16)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMPPConstant.SUBMIT_INPUT_ERROR)))).append(":validityPeriod ").append(SMPPConstant.STRING_LENGTH_NOTEQUAL).append("16"))));
        if(smLength > 254)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMPPConstant.SUBMIT_INPUT_ERROR)))).append(":smLength ").append(SMPPConstant.STRING_LENGTH_GREAT).append("254"))));
        if(shortMessage.length() > 254)
        {
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMPPConstant.SUBMIT_INPUT_ERROR)))).append(":shortMessage ").append(SMPPConstant.STRING_LENGTH_GREAT).append("254"))));
        } else
        {
            int len = 33 + serviceType.length() + sourceAddr.length() + destinationAddr.length() + scheduleDeliveryTime.length() + validityPeriod.length() + smLength;
            buf = new byte[len];
            setMsgLength(len);
            setCommandId(4);
            setStatus(0);
            int pos = 16;
            System.arraycopy(serviceType.getBytes(), 0, buf, pos, serviceType.length());
            pos = pos + serviceType.length() + 1;
            buf[pos] = sourceAddrTon;
            pos++;
            buf[pos] = sourceAddrNpi;
            pos++;
            System.arraycopy(sourceAddr.getBytes(), 0, buf, pos, sourceAddr.length());
            pos = pos + sourceAddr.length() + 1;
            buf[pos] = destAddrTon;
            pos++;
            buf[pos] = destAddrNpi;
            pos++;
            System.arraycopy(destinationAddr.getBytes(), 0, buf, pos, destinationAddr.length());
            pos = pos + destinationAddr.length() + 1;
            buf[pos] = esmClass;
            pos++;
            buf[pos] = protocolId;
            pos++;
            buf[pos] = priorityFlag;
            pos++;
            System.arraycopy(scheduleDeliveryTime.getBytes(), 0, buf, pos, scheduleDeliveryTime.length());
            pos = pos + scheduleDeliveryTime.length() + 1;
            System.arraycopy(validityPeriod.getBytes(), 0, buf, pos, validityPeriod.length());
            pos++;
            buf[pos] = registeredDelivery;
            pos++;
            buf[pos] = replaceIfPresentFlag;
            pos++;
            buf[pos] = dataCoding;
            pos++;
            buf[pos] = smDefaultMsgId;
            pos++;
            buf[pos] = smLength;
            pos++;
            System.arraycopy(shortMessage.getBytes(), 0, buf, pos, smLength);
            strBuf = new StringBuffer(600);
            strBuf.append(",serviceType=".concat(String.valueOf(String.valueOf(serviceType))));
            strBuf.append(",sourceAddrTon=".concat(String.valueOf(String.valueOf(sourceAddrTon))));
            strBuf.append(",sourceAddrNpi=".concat(String.valueOf(String.valueOf(sourceAddrNpi))));
            strBuf.append(",sourceAddr=".concat(String.valueOf(String.valueOf(sourceAddr))));
            strBuf.append(",destAddrTon=".concat(String.valueOf(String.valueOf(destAddrTon))));
            strBuf.append(",destAddrNpi=".concat(String.valueOf(String.valueOf(destAddrNpi))));
            strBuf.append(",destinationAddr=".concat(String.valueOf(String.valueOf(destinationAddr))));
            strBuf.append(",esmClass=".concat(String.valueOf(String.valueOf(esmClass))));
            strBuf.append(",protocolId=".concat(String.valueOf(String.valueOf(protocolId))));
            strBuf.append(",priorityFlag=".concat(String.valueOf(String.valueOf(priorityFlag))));
            strBuf.append(",scheduleDeliveryTime=".concat(String.valueOf(String.valueOf(scheduleDeliveryTime))));
            strBuf.append(",validityPeriod=".concat(String.valueOf(String.valueOf(validityPeriod))));
            strBuf.append(",registeredDelivery=".concat(String.valueOf(String.valueOf(registeredDelivery))));
            strBuf.append(",replaceIfPresentFlag=".concat(String.valueOf(String.valueOf(replaceIfPresentFlag))));
            strBuf.append(",dataCoding=".concat(String.valueOf(String.valueOf(dataCoding))));
            strBuf.append(",smDefaultMsgId=".concat(String.valueOf(String.valueOf(smDefaultMsgId))));
            strBuf.append(",smLength=".concat(String.valueOf(String.valueOf(smLength))));
            strBuf.append(",shortMessage=".concat(String.valueOf(String.valueOf(shortMessage))));
            return;
        }
    }

    public String toString()
    {
        StringBuffer outBuf = new StringBuffer(600);
        outBuf.append("SMPPSubmitMessage: ");
        outBuf.append("PacketLength=".concat(String.valueOf(String.valueOf(getMsgLength()))));
        outBuf.append(",CommandID=".concat(String.valueOf(String.valueOf(getCommandId()))));
        outBuf.append(",Status=".concat(String.valueOf(String.valueOf(getStatus()))));
        outBuf.append(",SequenceID=".concat(String.valueOf(String.valueOf(getSequenceId()))));
        if(strBuf != null)
            outBuf.append(strBuf.toString());
        return outBuf.toString();
    }
}

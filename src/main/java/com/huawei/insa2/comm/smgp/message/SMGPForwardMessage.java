// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   SMGPForwardMessage.java

package com.huawei.insa2.comm.smgp.message;

import com.huawei.insa2.comm.smgp.SMGPConstant;
import com.huawei.insa2.util.TypeConvert;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// Referenced classes of package com.huawei.insa2.comm.smgp.message:
//            SMGPMessage

public class SMGPForwardMessage extends SMGPMessage
{

    private StringBuffer strBuf;

    public SMGPForwardMessage(byte msgId[], String destSMGWNo, String srcSMGWNo, String smcNo, int msgType, int reportFlag, int priority, 
            String serviceId, String feeType, String feeCode, String fixedFee, int msgFormat, Date validTime, Date atTime, 
            String srcTermId, String destTermId, String chargeTermId, String msgContent, String reserve)
        throws IllegalArgumentException
    {
        if(msgId == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":MsgID ").append(SMGPConstant.STRING_NULL))));
        if(msgId.length > 10)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":MsgID ").append(SMGPConstant.STRING_LENGTH_GREAT).append("10"))));
        if(destSMGWNo == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":DestSMGWNo ").append(SMGPConstant.STRING_NULL))));
        if(destSMGWNo.length() > 6)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":DestSMGWNo ").append(SMGPConstant.STRING_LENGTH_GREAT).append("6"))));
        if(srcSMGWNo == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":SrcSMGWNo ").append(SMGPConstant.STRING_NULL))));
        if(srcSMGWNo.length() > 6)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":SrcSMGWNo ").append(SMGPConstant.STRING_LENGTH_GREAT).append("6"))));
        if(smcNo == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":SMCNo ").append(SMGPConstant.STRING_NULL))));
        if(smcNo.length() > 6)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":SMCNo ").append(SMGPConstant.STRING_LENGTH_GREAT).append("6"))));
        if(msgType < 0 || msgType > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":MsgType ").append(SMGPConstant.INT_SCOPE_ERROR))));
        if(reportFlag < 0 || reportFlag > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":ReportFlag ").append(SMGPConstant.INT_SCOPE_ERROR))));
        if(priority < 0 || priority > 9)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":").append(SMGPConstant.PRIORITY_ERROR))));
        if(serviceId == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":ServiceID ").append(SMGPConstant.STRING_NULL))));
        if(serviceId.length() > 10)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":ServiceID ").append(SMGPConstant.STRING_LENGTH_GREAT).append("10"))));
        if(feeType == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":FeeType ").append(SMGPConstant.STRING_NULL))));
        if(feeType.length() > 2)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":FeeType ").append(SMGPConstant.STRING_LENGTH_GREAT).append("2"))));
        if(feeCode == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":FeeCode ").append(SMGPConstant.STRING_NULL))));
        if(feeCode.length() > 6)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":FeeCode ").append(SMGPConstant.STRING_LENGTH_GREAT).append("6"))));
        if(fixedFee == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":FixedFee ").append(SMGPConstant.STRING_NULL))));
        if(fixedFee.length() > 6)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":FixedFee ").append(SMGPConstant.STRING_LENGTH_GREAT).append("6"))));
        if(msgFormat < 0 || msgFormat > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":MsgFormat ").append(SMGPConstant.INT_SCOPE_ERROR))));
        if(srcTermId == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":SrcTermID ").append(SMGPConstant.STRING_NULL))));
        if(srcTermId.length() > 21)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":SrcTermID ").append(SMGPConstant.STRING_LENGTH_GREAT).append("21"))));
        if(destTermId == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":DestTermId ").append(SMGPConstant.STRING_NULL))));
        if(destTermId.length() > 21)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":DestTermId ").append(SMGPConstant.STRING_LENGTH_GREAT).append("21"))));
        if(chargeTermId == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":ChargeTermID ").append(SMGPConstant.STRING_NULL))));
        if(chargeTermId.length() > 21)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":ChargeTermID ").append(SMGPConstant.STRING_LENGTH_GREAT).append("21"))));
        if(msgContent == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":MsgContent ").append(SMGPConstant.STRING_NULL))));
        if(msgContent.length() > 252)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":MsgContent ").append(SMGPConstant.STRING_LENGTH_GREAT).append("252"))));
        if(reserve != null && reserve.length() > 8)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.FORWARD_INPUT_ERROR)))).append(":reserve ").append(SMGPConstant.STRING_LENGTH_GREAT).append("8"))));
        int len = 174 + msgContent.length();
        buf = new byte[len];
        TypeConvert.int2byte(len, buf, 0);
        TypeConvert.int2byte(5, buf, 4);
        System.arraycopy(msgId, 0, buf, 12, 10);
        System.arraycopy(destSMGWNo.getBytes(), 0, buf, 22, destSMGWNo.length());
        System.arraycopy(srcSMGWNo.getBytes(), 0, buf, 28, srcSMGWNo.length());
        System.arraycopy(smcNo.getBytes(), 0, buf, 34, smcNo.length());
        buf[40] = (byte)msgType;
        buf[41] = (byte)reportFlag;
        buf[42] = (byte)priority;
        System.arraycopy(serviceId.getBytes(), 0, buf, 43, serviceId.length());
        System.arraycopy(feeType.getBytes(), 0, buf, 53, feeType.length());
        System.arraycopy(feeCode.getBytes(), 0, buf, 55, feeCode.length());
        System.arraycopy(fixedFee.getBytes(), 0, buf, 61, fixedFee.length());
        buf[67] = (byte)msgFormat;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        if(validTime != null)
        {
            String tmpTime = String.valueOf(String.valueOf(dateFormat.format(validTime))).concat("032+");
            System.arraycopy(tmpTime.getBytes(), 0, buf, 68, 16);
        }
        if(atTime != null)
        {
            String tmpTime = String.valueOf(String.valueOf(dateFormat.format(atTime))).concat("032+");
            System.arraycopy(tmpTime.getBytes(), 0, buf, 85, 16);
        }
        System.arraycopy(srcTermId.getBytes(), 0, buf, 102, srcTermId.length());
        System.arraycopy(destTermId.getBytes(), 0, buf, 123, destTermId.length());
        System.arraycopy(chargeTermId.getBytes(), 0, buf, 144, chargeTermId.length());
        buf[165] = (byte)msgContent.length();
        System.arraycopy(msgContent.getBytes(), 0, buf, 166, msgContent.length());
        if(reserve != null)
            System.arraycopy(reserve.getBytes(), 0, buf, 166 + msgContent.length(), reserve.length());
        strBuf = new StringBuffer(600);
        strBuf.append(",DestSMGWNo=".concat(String.valueOf(String.valueOf(destSMGWNo))));
        strBuf.append(",SrcSMGWNo=".concat(String.valueOf(String.valueOf(srcSMGWNo))));
        strBuf.append(",SMCNo=".concat(String.valueOf(String.valueOf(smcNo))));
        strBuf.append(",MsgType=".concat(String.valueOf(String.valueOf(msgType))));
        strBuf.append(",ReportFlag=".concat(String.valueOf(String.valueOf(reportFlag))));
        strBuf.append(",Priority=".concat(String.valueOf(String.valueOf(priority))));
        strBuf.append(",ServiceID=".concat(String.valueOf(String.valueOf(serviceId))));
        strBuf.append(",FeeType=".concat(String.valueOf(String.valueOf(feeType))));
        strBuf.append(",FeeCode=".concat(String.valueOf(String.valueOf(feeCode))));
        strBuf.append(",fixedFee=".concat(String.valueOf(String.valueOf(fixedFee))));
        strBuf.append(",MsgFormat=".concat(String.valueOf(String.valueOf(msgFormat))));
        if(validTime != null)
            strBuf.append(",ValidTime=".concat(String.valueOf(String.valueOf(dateFormat.format(validTime)))));
        else
            strBuf.append(",ValidTime=null");
        if(atTime != null)
            strBuf.append(",AtTime=".concat(String.valueOf(String.valueOf(dateFormat.format(atTime)))));
        else
            strBuf.append(",at_Time=null");
        strBuf.append(",SrcTermID=".concat(String.valueOf(String.valueOf(srcTermId))));
        strBuf.append(",DestTermID=".concat(String.valueOf(String.valueOf(destTermId))));
        strBuf.append(",ChargeTermID=".concat(String.valueOf(String.valueOf(chargeTermId))));
        strBuf.append(",MsgLength=".concat(String.valueOf(String.valueOf(msgContent.length()))));
        strBuf.append(",MsgContent=".concat(String.valueOf(String.valueOf(msgContent))));
        strBuf.append(",Reserve=".concat(String.valueOf(String.valueOf(reserve))));
    }

    public String toString()
    {
        StringBuffer outBuf = new StringBuffer(600);
        outBuf.append("SMGPForwardMessage: ");
        outBuf.append("PacketLength=".concat(String.valueOf(String.valueOf(buf.length))));
        outBuf.append(",RequestID=5");
        outBuf.append(",SequenceID=".concat(String.valueOf(String.valueOf(getSequenceId()))));
        if(strBuf != null)
            outBuf.append(strBuf.toString());
        return outBuf.toString();
    }

    public int getRequestId()
    {
        return 5;
    }
}

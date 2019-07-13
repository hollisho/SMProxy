// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   SMGPSubmitMessage.java

package com.huawei.insa2.comm.smgp.message;

import com.huawei.insa2.comm.smgp.SMGPConstant;
import com.huawei.insa2.util.TypeConvert;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// Referenced classes of package com.huawei.insa2.comm.smgp.message:
//            SMGPMessage

public class SMGPSubmitMessage extends SMGPMessage
{

    private StringBuffer strBuf;

    public SMGPSubmitMessage(int msgType, int needReport, int priority, String serviceId, String feeType, String feeCode, String fixedFee, 
            int msgFormat, Date validTime, Date atTime, String srcTermId, String chargeTermId, String destTermId[], String msgContent, 
            String reserve)
        throws IllegalArgumentException
    {
        if(msgType < 0 || msgType > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":MsgType ").append(SMGPConstant.INT_SCOPE_ERROR))));
        if(needReport < 0 || needReport > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":NeedReport ").append(SMGPConstant.INT_SCOPE_ERROR))));
        if(priority < 0 || priority > 9)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":").append(SMGPConstant.PRIORITY_ERROR))));
        if(serviceId == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":ServiceID ").append(SMGPConstant.STRING_NULL))));
        if(serviceId.length() > 10)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":ServiceID ").append(SMGPConstant.STRING_LENGTH_GREAT).append("10"))));
        if(feeType == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":FeeType ").append(SMGPConstant.STRING_NULL))));
        if(feeType.length() > 2)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":FeeType ").append(SMGPConstant.STRING_LENGTH_GREAT).append("2"))));
        if(feeCode == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":FeeCode ").append(SMGPConstant.STRING_NULL))));
        if(feeCode.length() > 6)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":FeeCode ").append(SMGPConstant.STRING_LENGTH_GREAT).append("6"))));
        if(fixedFee == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":FixedFee ").append(SMGPConstant.STRING_NULL))));
        if(fixedFee.length() > 6)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":FixedFee ").append(SMGPConstant.STRING_LENGTH_GREAT).append("6"))));
        if(msgFormat < 0 || msgFormat > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":MsgFormat ").append(SMGPConstant.INT_SCOPE_ERROR))));
        if(srcTermId == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":SrcTermID ").append(SMGPConstant.STRING_NULL))));
        if(srcTermId.length() > 21)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":SrcTermID ").append(SMGPConstant.STRING_LENGTH_GREAT).append("21"))));
        if(chargeTermId == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":ChargeTermID ").append(SMGPConstant.STRING_NULL))));
        if(chargeTermId.length() > 21)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":ChargeTermID ").append(SMGPConstant.STRING_LENGTH_GREAT).append("21"))));
        if(destTermId == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":DestTermID ").append(SMGPConstant.STRING_NULL))));
        if(destTermId.length > 100)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":").append(SMGPConstant.DESTTERMID_ERROR))));
        for(int i = 0; i < destTermId.length; i++)
            if(destTermId[i].length() > 21)
                throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":one DestTermID ").append(SMGPConstant.STRING_LENGTH_GREAT).append("21"))));

        if(msgContent == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":MsgContent ").append(SMGPConstant.STRING_NULL))));
        if(msgContent.length() > 252)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":MsgContent ").append(SMGPConstant.STRING_LENGTH_GREAT).append("252"))));
        if(reserve != null && reserve.length() > 8)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":reserve ").append(SMGPConstant.STRING_LENGTH_GREAT).append("8"))));
        int len = 126 + 21 * destTermId.length + msgContent.length();
        buf = new byte[len];
        TypeConvert.int2byte(len, buf, 0);
        TypeConvert.int2byte(2, buf, 4);
        buf[12] = (byte)msgType;
        buf[13] = (byte)needReport;
        buf[14] = (byte)priority;
        System.arraycopy(serviceId.getBytes(), 0, buf, 15, serviceId.length());
        System.arraycopy(feeType.getBytes(), 0, buf, 25, feeType.length());
        System.arraycopy(feeCode.getBytes(), 0, buf, 27, feeCode.length());
        System.arraycopy(fixedFee.getBytes(), 0, buf, 33, fixedFee.length());
        buf[39] = (byte)msgFormat;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        if(validTime != null)
        {
            String tmpTime = String.valueOf(String.valueOf(dateFormat.format(validTime))).concat("032+");
            System.arraycopy(tmpTime.getBytes(), 0, buf, 40, 16);
        }
        if(atTime != null)
        {
            String tmpTime = String.valueOf(String.valueOf(dateFormat.format(atTime))).concat("032+");
            System.arraycopy(tmpTime.getBytes(), 0, buf, 57, 16);
        }
        System.arraycopy(srcTermId.getBytes(), 0, buf, 74, srcTermId.length());
        System.arraycopy(chargeTermId.getBytes(), 0, buf, 95, chargeTermId.length());
        buf[116] = (byte)destTermId.length;
        int i = 0;
        for(i = 0; i < destTermId.length; i++)
            System.arraycopy(destTermId[i].getBytes(), 0, buf, 117 + i * 21, destTermId[i].length());

        int loc = 117 + i * 21;
        buf[loc] = (byte)msgContent.length();
        System.arraycopy(msgContent.getBytes(), 0, buf, loc + 1, msgContent.length());
        loc = loc + 1 + msgContent.length();
        if(reserve != null)
            System.arraycopy(reserve.getBytes(), 0, buf, loc, reserve.length());
        strBuf = new StringBuffer(600);
        strBuf.append(",MsgType=".concat(String.valueOf(String.valueOf(msgType))));
        strBuf.append(",NeedReport=".concat(String.valueOf(String.valueOf(needReport))));
        strBuf.append(",Priority=".concat(String.valueOf(String.valueOf(priority))));
        strBuf.append(",ServiceID=".concat(String.valueOf(String.valueOf(serviceId))));
        strBuf.append(",FeeType=".concat(String.valueOf(String.valueOf(feeType))));
        strBuf.append(",FeeCode=".concat(String.valueOf(String.valueOf(feeCode))));
        strBuf.append(",FixedFee=".concat(String.valueOf(String.valueOf(fixedFee))));
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
        strBuf.append(",ChargeTermID=".concat(String.valueOf(String.valueOf(chargeTermId))));
        strBuf.append(",DestTermIDCount=".concat(String.valueOf(String.valueOf(destTermId.length))));
        for(int t = 0; t < destTermId.length; t++)
            strBuf.append(String.valueOf(String.valueOf((new StringBuffer(",DestTermID[")).append(t).append("]=").append(destTermId[t]))));

        strBuf.append(",MsgLength=".concat(String.valueOf(String.valueOf(msgContent.length()))));
        strBuf.append(",MsgContent=".concat(String.valueOf(String.valueOf(msgContent))));
        strBuf.append(",Reserve=".concat(String.valueOf(String.valueOf(reserve))));
    }

    public String toString()
    {
        StringBuffer outBuf = new StringBuffer(600);
        outBuf.append("SMGPSubmitMessage: ");
        outBuf.append("PacketLength=".concat(String.valueOf(String.valueOf(buf.length))));
        outBuf.append(",RequestID=2");
        outBuf.append(",SequenceID=".concat(String.valueOf(String.valueOf(getSequenceId()))));
        if(strBuf != null)
            outBuf.append(strBuf.toString());
        return outBuf.toString();
    }

    public int getRequestId()
    {
        return 2;
    }
}

// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   CMPP30SubmitMessage.java

package com.huawei.insa2.comm.cmpp30.message;

import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.util.TypeConvert;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CMPP30SubmitMessage extends CMPPMessage
{

    private String outStr;

    public CMPP30SubmitMessage(int pk_Total, int pk_Number, int registered_Delivery, int msg_Level, String service_Id, int fee_UserType, String fee_Terminal_Id, 
            int fee_Terminal_Type, int tp_Pid, int tp_Udhi, int msg_Fmt, String msg_Src, String fee_Type, String fee_Code, 
            Date valid_Time, Date at_Time, String src_Terminal_Id, String dest_Terminal_Id[], int dest_Terminal_Type, byte msg_Content[], String LinkID)
        throws IllegalArgumentException
    {
        if(pk_Total < 1 || pk_Total > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":").append(CMPPConstant.PK_TOTAL_ERROR))));
        if(pk_Number < 1 || pk_Number > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":").append(CMPPConstant.PK_NUMBER_ERROR))));
        if(registered_Delivery < 0 || registered_Delivery > 1)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":").append(CMPPConstant.REGISTERED_DELIVERY_ERROR))));
        if(msg_Level < 0 || msg_Level > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":msg_Level").append(CMPPConstant.INT_SCOPE_ERROR))));
        if(service_Id.length() > 10)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":service_Id").append(CMPPConstant.STRING_LENGTH_GREAT).append("10"))));
        if(fee_UserType < 0 || fee_UserType > 3)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":").append(CMPPConstant.FEE_USERTYPE_ERROR))));
        if(fee_Terminal_Id.length() > 32)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":fee_Terminal_Id").append(CMPPConstant.STRING_LENGTH_GREAT).append("32"))));
        if(fee_Terminal_Type < 0 || fee_Terminal_Type > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":fee_terminal_type").append(CMPPConstant.INT_SCOPE_ERROR))));
        if(tp_Pid < 0 || tp_Pid > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":tp_Pid").append(CMPPConstant.INT_SCOPE_ERROR))));
        if(tp_Udhi < 0 || tp_Udhi > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":tp_Udhi").append(CMPPConstant.INT_SCOPE_ERROR))));
        if(msg_Fmt < 0 || msg_Fmt > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":msg_Fmt").append(CMPPConstant.INT_SCOPE_ERROR))));
        if(msg_Src.length() > 6)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":msg_Src").append(CMPPConstant.STRING_LENGTH_GREAT).append("6"))));
        if(fee_Type.length() > 2)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":fee_Type").append(CMPPConstant.STRING_LENGTH_GREAT).append("2"))));
        if(fee_Code.length() > 6)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":fee_Code").append(CMPPConstant.STRING_LENGTH_GREAT).append("6"))));
        if(src_Terminal_Id.length() > 21)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":src_Terminal_Id").append(CMPPConstant.STRING_LENGTH_GREAT).append("21"))));
        if(dest_Terminal_Id.length > 100)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":dest_Terminal_Id").append(CMPPConstant.STRING_LENGTH_GREAT).append("100"))));
        for(int i = 0; i < dest_Terminal_Id.length; i++)
            if(dest_Terminal_Id[i].length() > 32)
                throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":dest_Terminal_Id").append(CMPPConstant.STRING_LENGTH_GREAT).append("32"))));

        if(dest_Terminal_Type < 0 || dest_Terminal_Type > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":dest_terminal_type").append(CMPPConstant.INT_SCOPE_ERROR))));
        if(msg_Fmt == 0)
        {
            if(msg_Content.length > 160)
                throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":msg_Content").append(CMPPConstant.STRING_LENGTH_GREAT).append("160"))));
        } else
        if(msg_Content.length > 140)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":msg_Content").append(CMPPConstant.STRING_LENGTH_GREAT).append("140"))));
        if(LinkID.length() > 20)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":LinkID").append(CMPPConstant.STRING_LENGTH_GREAT).append("20"))));
        int len = 163 + 32 * dest_Terminal_Id.length + msg_Content.length;
        buf = new byte[len];
        TypeConvert.int2byte(len, buf, 0);
        TypeConvert.int2byte(4, buf, 4);
        buf[20] = (byte)pk_Total;
        buf[21] = (byte)pk_Number;
        buf[22] = (byte)registered_Delivery;
        buf[23] = (byte)msg_Level;
        System.arraycopy(service_Id.getBytes(), 0, buf, 24, service_Id.length());
        buf[34] = (byte)fee_UserType;
        System.arraycopy(fee_Terminal_Id.getBytes(), 0, buf, 35, fee_Terminal_Id.length());
        buf[67] = (byte)fee_Terminal_Type;
        buf[68] = (byte)tp_Pid;
        buf[69] = (byte)tp_Udhi;
        buf[70] = (byte)msg_Fmt;
        System.arraycopy(msg_Src.getBytes(), 0, buf, 71, msg_Src.length());
        System.arraycopy(fee_Type.getBytes(), 0, buf, 77, fee_Type.length());
        System.arraycopy(fee_Code.getBytes(), 0, buf, 79, fee_Code.length());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        if(valid_Time != null)
        {
            String tmpTime = String.valueOf(String.valueOf(dateFormat.format(valid_Time))).concat("032+");
            System.arraycopy(tmpTime.getBytes(), 0, buf, 85, 16);
        }
        if(at_Time != null)
        {
            String tmpTime = String.valueOf(String.valueOf(dateFormat.format(at_Time))).concat("032+");
            System.arraycopy(tmpTime.getBytes(), 0, buf, 102, 16);
        }
        System.arraycopy(src_Terminal_Id.getBytes(), 0, buf, 119, src_Terminal_Id.length());
        buf[140] = (byte)dest_Terminal_Id.length;
        int i = 0;
        for(i = 0; i < dest_Terminal_Id.length; i++)
            System.arraycopy(dest_Terminal_Id[i].getBytes(), 0, buf, 141 + i * 32, dest_Terminal_Id[i].length());

        int loc = 141 + i * 32;
        buf[loc] = (byte)dest_Terminal_Type;
        loc++;
        buf[loc] = (byte)msg_Content.length;
        loc++;
        System.arraycopy(msg_Content, 0, buf, loc, msg_Content.length);
        loc += msg_Content.length;
        System.arraycopy(LinkID.getBytes(), 0, buf, loc, LinkID.length());
        outStr = ",msg_id=00000000";
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",pk_Total=").append(pk_Total)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",pk_Number=").append(pk_Number)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",registered_Delivery=").append(registered_Delivery)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",msg_Level=").append(msg_Level)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",service_Id=").append(service_Id)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",fee_UserType=").append(fee_UserType)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",fee_Terminal_Id=").append(fee_Terminal_Id)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",fee_Terminal_type=").append(fee_Terminal_Type)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",tp_Pid=").append(tp_Pid)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",tp_Udhi=").append(tp_Udhi)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",msg_Fmt=").append(msg_Fmt)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",msg_Src=").append(msg_Src)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",fee_Type=").append(fee_Type)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",fee_Code=").append(fee_Code)));
        if(valid_Time != null)
            outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",valid_Time=").append(dateFormat.format(valid_Time))));
        else
            outStr = String.valueOf(String.valueOf(outStr)).concat(",valid_Time=null");
        if(at_Time != null)
            outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",at_Time=").append(dateFormat.format(at_Time))));
        else
            outStr = String.valueOf(String.valueOf(outStr)).concat(",at_Time=null");
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",src_Terminal_Id=").append(src_Terminal_Id)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",destusr_Tl=").append(dest_Terminal_Id.length)));
        for(int t = 0; t < dest_Terminal_Id.length; t++)
            outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",dest_Terminal_Id[").append(t).append("]=").append(dest_Terminal_Id[t])));

        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",dest_Terminal_type=").append(dest_Terminal_Type)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",msg_Length=").append(msg_Content.length)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",msg_Content=").append(new String(msg_Content))));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",LinkID=").append(LinkID)));
    }

    public String toString()
    {
        String tmpStr = "CMPP_Submit: ";
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append("Sequence_Id=").append(getSequenceId())));
        tmpStr = String.valueOf(tmpStr) + String.valueOf(outStr);
        return tmpStr;
    }

    public int getCommandId()
    {
        return 4;
    }
}

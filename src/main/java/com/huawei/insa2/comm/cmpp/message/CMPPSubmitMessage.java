// Decompiled by DJ v2.9.9.60 Copyright 2000 Atanas Neshkov  Date: 2005-7-11 17:43:14
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3)
// Source File Name:   CMPPSubmitMessage.java

package com.huawei.insa2.comm.cmpp.message;

import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.util.TypeConvert;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// Referenced classes of package com.huawei.insa2.comm.cmpp.message:
//            CMPPMessage

public class CMPPSubmitMessage extends CMPPMessage
{

    /**
       * 创建一个发送短信消息,传入消息的各字段的值，并对各参数值进行判断，不符合要求则抛出异常
       * 按要求把输入的参数转换为一个byte类型的数组
       * 1  @pk_Total 相同msg_Id消息总条数
       * 2  @pk_Number 相同msg_Id的消息序号
       * 3  @registered_Delivery 是否要求返回状态报告
       * 4  @msg_Level  信息级别
       * 5  @service_Id 业务类型
       * 6  @fee_UserType 计费用户类型字段
       * 7  @fee_Terminal_Id 被计费用户的号码
       * 9  @tp_Pid GSM协议类型
       * 10  @tp_Udhi GSM协议类型
       * 11 @msg_Fmt 消息格式
       * 12 @msg_Src 消息内容来源
       * 13 @fee_Type 资费类别
       * 14 @fee_Code 资费代码(以分为单位)
       * 15 @valid_Time 存活有效期
       * 16 @at_Time 定时发送时间
       * 17 @src_Terminal_Id 源号码
       * 18 @dest_Terminal_Id 接收短信的MSISDN号码
       * 20 @msg_Content 消息内容
       * 21 @reserve    点播业务使用的LinkID
       */

    public CMPPSubmitMessage(
        int pk_Total,
        int pk_Number,
        int registered_Delivery,
        int msg_Level,
        String service_Id,
        int fee_UserType,
        String fee_Terminal_Id,
        int tp_Pid,
        int tp_Udhi,
        int msg_Fmt,
        String msg_Src,
        String fee_Type,
        String fee_Code,
        Date valid_Time,
        Date at_Time,
        String src_Terminal_Id,
        String dest_Terminal_Id[],
        byte msg_Content[],
        String reserve
        )
        throws IllegalArgumentException
    {
        if(pk_Total < 1 || pk_Total > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":").append(CMPPConstant.PK_TOTAL_ERROR))));
        if(pk_Number < 1 || pk_Number > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":").append(CMPPConstant.PK_NUMBER_ERROR))));
        if(registered_Delivery < 0 || registered_Delivery > 2)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":").append(CMPPConstant.REGISTERED_DELIVERY_ERROR))));
        if(msg_Level < 0 || msg_Level > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":msg_Level").append(CMPPConstant.INT_SCOPE_ERROR))));
        if(service_Id.length() > 10)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":service_Id").append(CMPPConstant.STRING_LENGTH_GREAT).append("10"))));
        if(fee_UserType < 0 || fee_UserType > 3)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":").append(CMPPConstant.FEE_USERTYPE_ERROR))));
        if(fee_Terminal_Id.length() > 21)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":fee_Terminal_Id").append(CMPPConstant.STRING_LENGTH_GREAT).append("21"))));
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
            if(dest_Terminal_Id[i].length() > 21)
                throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":dest_Terminal_Id").append(CMPPConstant.STRING_LENGTH_GREAT).append("21"))));

        if(msg_Fmt == 0)
        {
            if(msg_Content.length > 160)
                throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":msg_Content").append(CMPPConstant.STRING_LENGTH_GREAT).append("160"))));
        } else
        if(msg_Content.length > 140)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":msg_Content").append(CMPPConstant.STRING_LENGTH_GREAT).append("140"))));
        if(reserve.length() > 8)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CMPPConstant.SUBMIT_INPUT_ERROR)))).append(":reserve").append(CMPPConstant.STRING_LENGTH_GREAT).append("8"))));
        int len = 138 + 21 * dest_Terminal_Id.length + msg_Content.length;
        super.buf = new byte[len];
        TypeConvert.int2byte(len, super.buf, 0);
        TypeConvert.int2byte(4, super.buf, 4);
        super.buf[20] = (byte)pk_Total;
        super.buf[21] = (byte)pk_Number;
        super.buf[22] = (byte)registered_Delivery;
        super.buf[23] = (byte)msg_Level;
        System.arraycopy(service_Id.getBytes(), 0, super.buf, 24, service_Id.length());
        super.buf[34] = (byte)fee_UserType;
        System.arraycopy(fee_Terminal_Id.getBytes(), 0, super.buf, 35, fee_Terminal_Id.length());
        super.buf[56] = (byte)tp_Pid;
        super.buf[57] = (byte)tp_Udhi;
        super.buf[58] = (byte)msg_Fmt;
        System.arraycopy(msg_Src.getBytes(), 0, super.buf, 59, msg_Src.length());
        System.arraycopy(fee_Type.getBytes(), 0, super.buf, 65, fee_Type.length());
        System.arraycopy(fee_Code.getBytes(), 0, super.buf, 67, fee_Code.length());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        if(valid_Time != null)
        {
            String tmpTime = String.valueOf(String.valueOf(dateFormat.format(valid_Time))).concat("032+");
            System.arraycopy(tmpTime.getBytes(), 0, super.buf, 73, 16);
        }
        if(at_Time != null)
        {
            String tmpTime = String.valueOf(String.valueOf(dateFormat.format(at_Time))).concat("032+");
            System.arraycopy(tmpTime.getBytes(), 0, super.buf, 90, 16);
        }
        System.arraycopy(src_Terminal_Id.getBytes(), 0, super.buf, 107, src_Terminal_Id.length());
        super.buf[128] = (byte)dest_Terminal_Id.length;
        int i = 0;
        for(i = 0; i < dest_Terminal_Id.length; i++)
            System.arraycopy(dest_Terminal_Id[i].getBytes(), 0, super.buf, 129 + i * 21, dest_Terminal_Id[i].length());

        int loc = 129 + i * 21;
        super.buf[loc] = (byte)msg_Content.length;
        System.arraycopy(msg_Content, 0, super.buf, loc + 1, msg_Content.length);
        loc = loc + 1 + msg_Content.length;
        System.arraycopy(reserve.getBytes(), 0, super.buf, loc, reserve.length());
        outStr = ",msg_id=00000000";
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",pk_Total=").append(pk_Total)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",pk_Number=").append(pk_Number)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",registered_Delivery=").append(registered_Delivery)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",msg_Level=").append(msg_Level)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",service_Id=").append(service_Id)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",fee_UserType=").append(fee_UserType)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",fee_Terminal_Id=").append(fee_Terminal_Id)));
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

        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",msg_Length=").append(msg_Content.length)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",msg_Content=").append(new String(msg_Content))));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",reserve=").append(reserve)));
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

    private String outStr;
}
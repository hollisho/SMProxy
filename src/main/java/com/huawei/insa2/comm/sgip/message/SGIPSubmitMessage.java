// Decompiled by DJ v2.9.9.60 Copyright 2000 Atanas Neshkov  Date: 2005-7-16 19:21:01
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3)
// Source File Name:   SGIPSubmitMessage.java

package com.huawei.insa2.comm.sgip.message;

import com.huawei.insa2.comm.sgip.SGIPConstant;
import com.huawei.insa2.util.TypeConvert;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// Referenced classes of package com.huawei.insa2.comm.sgip.message:
//            SGIPMessage

public class SGIPSubmitMessage extends SGIPMessage
{
    /**
       * 创建一个发送短信消息,传入消息的各字段的值，并对各参数值进行判断，不符合要求则抛出异常
       * 按要求把输入的参数转换为一个byte类型的数组
       * @param SPNumber SP的接入号码
       * @param ChargeNumber 付费号码
       * @param UserNumber 接收该短消息的手机号，最多100个号码
       * @param CorpId 企业代码，取值范围0-99999
       * @param ServiceType 业务代码，由SP定义
       * @param FeeType 计费类型
       * @param FeeValue 该条短消息的收费值
       * @param GivenValue 赠送用户的话费
       * @param AgentFlag 代收费标志，0：应收；1：实收
       * @param MorelatetoMTFlag 引起MT消息的原因
       * @param Priority 优先级0-9从低到高，默认为0
       * @param ExpireTime 短消息寿命的终止时间
       * @param ScheduleTime 短消息定时发送的时间
       * @param ReportFlag 状态报告标记
       * @param TP_pid GSM协议类型
       * @param TP_udhi GSM协议类型
       * @param MessageCoding 短消息的编码格式
       * @param MessageType 信息类型
       * @param MessageLen 信息字节长度
       * @param MessageContent 短消息的内容
       * @param reserve 保留，扩展用
       */
      public SGIPSubmitMessage(
        String SPNumber,//SP的接入号码
        String ChargeNumber,//付费号码
        String UserNumber[],//接收该短消息的手机号，最多100个号码
        String CorpId,//企业代码，取值范围0-99999
        String ServiceType,//业务代码，由SP定义
        int FeeType,//计费类型
/*用户计费类别	描述
0	"短消息类型"为"发送"，对"计费用户号码"不计信息费，此类话单仅用于核减SP对称的信道费
1	对"计费用户号码"免费
2	对"计费用户号码"按条计信息费
3	对"计费用户号码"按包月收取信息费
4	对"计费用户号码"的收费是由SP实现*/
        String FeeValue,//该条短消息的收费值
        String GivenValue,//赠送用户的话费
        int AgentFlag,//代收费标志，0：应收；1：实收
        int MorelatetoMTFlag,//引起MT消息的原因
/*0-MO点播引起的第一条MT消息；
1-MO点播引起的非第一条MT消息；
2-非MO点播引起的MT消息；
3-系统反馈引起的MT消息。限制SP不能对该字段填写3*/
        int Priority,//优先级0-9从低到高，默认为0
        Date ExpireTime,//短消息寿命的终止时间
        Date ScheduleTime,//短消息定时发送的时间
        int ReportFlag,//状态报告标记
        int TP_pid,//GSM协议类型
        int TP_udhi,//GSM协议类型
        int MessageCoding,//短消息的编码格式
        int MessageType,//信息类型
        int MessageLen,//信息字节长度
        byte MessageContent[],//短消息的内容
        String reserve//保留，扩展用
        //该Reserve字段为8个字节的保留字段；现将该字段作为MO和MT之间一一对应的LinkID来用。
        //对于非短信MO所引起的MT，SP对该MT的Reserve字段填写数字0。
        ) throws IllegalArgumentException {
        if(SPNumber.length() > 21)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":SPNumber").append(SGIPConstant.STRING_LENGTH_GREAT).append("21"))));
        if(ChargeNumber.length() > 21)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":ChargeNumber").append(SGIPConstant.STRING_LENGTH_GREAT).append("21"))));
        if(UserNumber.length > 100)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":UserNumber").append(SGIPConstant.STRING_LENGTH_GREAT).append("100"))));
        for(int i = 0; i < UserNumber.length; i++)
            if(UserNumber[i].length() > 21)
                throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":UserNumber[").append(i).append("]").append(SGIPConstant.STRING_LENGTH_GREAT).append("21"))));

        if(CorpId.length() > 5)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":CorpId").append(SGIPConstant.STRING_LENGTH_GREAT).append("5"))));
        if(ServiceType.length() > 10)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":ServiceType").append(SGIPConstant.STRING_LENGTH_GREAT).append("10"))));
        if(FeeType < 0 || FeeType > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":FeeType").append(SGIPConstant.INT_SCOPE_ERROR))));
        if(FeeValue.length() > 6)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":FeeValue").append(SGIPConstant.STRING_LENGTH_GREAT).append("6"))));
        if(GivenValue.length() > 6)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":GivenValue").append(SGIPConstant.STRING_LENGTH_GREAT).append("6"))));
        if(AgentFlag < 0 || AgentFlag > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":AgentFlag").append(SGIPConstant.INT_SCOPE_ERROR))));
        if(MorelatetoMTFlag < 0 || MorelatetoMTFlag > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":MorelatetoMTFlag").append(SGIPConstant.INT_SCOPE_ERROR))));
        if(MorelatetoMTFlag < 0 || MorelatetoMTFlag > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":MorelatetoMTFlag").append(SGIPConstant.INT_SCOPE_ERROR))));
        if(Priority < 0 || Priority > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":Priority").append(SGIPConstant.INT_SCOPE_ERROR))));
        if(ReportFlag < 0 || ReportFlag > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":ReportFlag").append(SGIPConstant.INT_SCOPE_ERROR))));
        if(TP_pid < 0 || TP_pid > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":TP_pid").append(SGIPConstant.INT_SCOPE_ERROR))));
        if(TP_udhi < 0 || TP_udhi > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":TP_udhi").append(SGIPConstant.INT_SCOPE_ERROR))));
        if(MessageCoding < 0 || MessageCoding > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":MessageCoding").append(SGIPConstant.INT_SCOPE_ERROR))));
        if(MessageType < 0 || MessageType > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":MessageType").append(SGIPConstant.INT_SCOPE_ERROR))));
        if(MessageLen < 0 || MessageLen > 160)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":MessageLen").append(SGIPConstant.INT_SCOPE_ERROR))));
        if(MessageContent.length > 160)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":MessageContent").append(SGIPConstant.STRING_LENGTH_GREAT).append("160"))));
        if(reserve.length() > 8)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":reserve").append(SGIPConstant.STRING_LENGTH_GREAT).append("8"))));
        int len = 143 + 21 * UserNumber.length + MessageLen;
        super.buf = new byte[len];
        TypeConvert.int2byte(len, super.buf, 0);
        TypeConvert.int2byte(3, super.buf, 4);
        System.arraycopy(SPNumber.getBytes(), 0, super.buf, 20, SPNumber.length());
        System.arraycopy(ChargeNumber.getBytes(), 0, super.buf, 41, ChargeNumber.length());
        super.buf[62] = (byte)UserNumber.length;
        int i = 0;
        for(i = 0; i < UserNumber.length; i++)
            System.arraycopy(UserNumber[i].getBytes(), 0, super.buf, 63 + i * 21, UserNumber[i].length());

        int loc = 63 + i * 21;
        System.arraycopy(CorpId.getBytes(), 0, super.buf, loc, CorpId.length());
        loc += 5;
        System.arraycopy(ServiceType.getBytes(), 0, super.buf, loc, ServiceType.length());
        loc += 10;
        super.buf[loc++] = (byte)FeeType;
        System.arraycopy(FeeValue.getBytes(), 0, super.buf, loc, FeeValue.length());
        loc += 6;
        System.arraycopy(GivenValue.getBytes(), 0, super.buf, loc, GivenValue.length());
        loc += 6;
        super.buf[loc++] = (byte)AgentFlag;
        super.buf[loc++] = (byte)MorelatetoMTFlag;
        super.buf[loc++] = (byte)Priority;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        if(ExpireTime != null)
        {
            String tmpTime = String.valueOf(String.valueOf(dateFormat.format(ExpireTime))).concat("032+");
            System.arraycopy(tmpTime.getBytes(), 0, super.buf, loc, 16);
        }
        loc += 16;
        if(ScheduleTime != null)
        {
            String tmpTime = String.valueOf(String.valueOf(dateFormat.format(ScheduleTime))).concat("032+");
            System.arraycopy(tmpTime.getBytes(), 0, super.buf, loc, 16);
        }
        loc += 16;
        super.buf[loc++] = (byte)ReportFlag;
        super.buf[loc++] = (byte)TP_pid;
        super.buf[loc++] = (byte)TP_udhi;
        super.buf[loc++] = (byte)MessageCoding;
        super.buf[loc++] = (byte)MessageType;
        TypeConvert.int2byte(MessageLen, super.buf, loc);
        loc += 4;
        System.arraycopy(MessageContent, 0, super.buf, loc, MessageLen);
        loc += MessageLen;
        System.arraycopy(reserve.getBytes(), 0, super.buf, loc, reserve.length());
        outStr = ",SPNumber=".concat(String.valueOf(String.valueOf(SPNumber)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",ChargeNumber=").append(ChargeNumber)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",UserCount=").append(UserNumber.length)));
        for(int t = 0; t < UserNumber.length; t++)
            outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",UserNumber[").append(t).append("]=").append(UserNumber[t])));

        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",CorpId=").append(CorpId)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",ServiceType=").append(ServiceType)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",FeeType=").append(FeeType)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",FeeValue=").append(FeeValue)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",GivenValue=").append(GivenValue)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",AgentFlag=").append(AgentFlag)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",MorelatetoMTFlag=").append(MorelatetoMTFlag)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",Priority=").append(Priority)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",ExpireTime=").append(ExpireTime)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",ScheduleTime=").append(ScheduleTime)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",ReportFlag=").append(ReportFlag)));
        if(ExpireTime != null)
            outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",ExpireTime=").append(dateFormat.format(ExpireTime))));
        else
            outStr = String.valueOf(String.valueOf(outStr)).concat(",ExpireTime=null");
        if(ScheduleTime != null)
            outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",ScheduleTime=").append(dateFormat.format(ScheduleTime))));
        else
            outStr = String.valueOf(String.valueOf(outStr)).concat(",ScheduleTime=null");
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",ReportFlag=").append(ReportFlag)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",TP_pid=").append(TP_pid)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",TP_udhi=").append(TP_udhi)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",MessageCoding=").append(MessageCoding)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",MessageType=").append(MessageType)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",MessageLength=").append(MessageLen)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",MessageContent=").append(new String(MessageContent))));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",reserve=").append(reserve)));
    }

    public String toString()
    {
        String tmpStr = "SGIP_Submit: ";
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append("Sequence_Id=").append(getSequenceId())));
        tmpStr = String.valueOf(tmpStr) + String.valueOf(outStr);
        return tmpStr;
    }

    public int getCommandId()
    {
        return 3;
    }

    private String outStr;
}
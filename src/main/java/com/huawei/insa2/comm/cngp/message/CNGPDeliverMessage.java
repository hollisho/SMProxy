package com.huawei.insa2.comm.cngp.message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;
import com.huawei.insa2.comm.cngp.CNGPConstant;
import com.huawei.insa2.util.TypeConvert;
import java.util.Calendar;

/**
 * 定义SMC下发的短信消息,SMC主动下发的。
 * @author       linmeilong
 * @version      1.0
 */

public class CNGPDeliverMessage extends CNGPMessage{

    private int deliverType;//是否状态报告:0:不是,1:是
  /**
   * 构造一SMC下发短信消息
   * @param buf  SMC下发的短信消息数据
   */
   public CNGPDeliverMessage( byte[] buf )throws IllegalArgumentException {
       deliverType = 0;
       deliverType = buf[26];
       //计算消息长度，固定长度+不固定长度
       int len = 90 + ((int)buf[84] & 0xff);
       if( buf.length != len ){//消息长度不正确,流水号+消息体
           throw new IllegalArgumentException( CNGPConstant.SMC_MESSAGE_ERROR );
       }
       this.buf = new byte[len];
       System.arraycopy(buf,0,this.buf,0,buf.length);
    }

  /**
   * 返回消息标识
   */
   public byte[] getMsgId(){
       byte[] msgId = new byte[10];
       System.arraycopy(this.buf,16,msgId,0,10);
       return msgId;
   }

  /**
   * 返回是否为状态报告，0：不是,1：是
   */
   public int getIsReport(){
       return (int)this.buf[26];
  }

  /**
   * 返回消息格式
   */
  public int getMsgFormat(){
     return (int)this.buf[27];
  }

  /**
   * 返回短消息接收的时间,27-40
   * @return
   */
  public Date getRecvTime()
  {
     try
     {
       //取年份
       int tmpYear = TypeConvert.byte2int(this.buf,27);
       //取月份
       byte[] tmpbyte = new byte[2];
       System.arraycopy(this.buf,31,tmpbyte,0,2);
       String tmpstr = new String(tmpbyte);
       int tmpMonth = Integer.parseInt(tmpstr);
       //取日期
       System.arraycopy(this.buf,33,tmpbyte,0,2);
       tmpstr = new String(tmpbyte);
       int tmpDay = Integer.parseInt(tmpstr);
       //取小时
       System.arraycopy(this.buf,35,tmpbyte,0,2);
       tmpstr = new String(tmpbyte);
       int tmpHour = Integer.parseInt(tmpstr);
       //取分钟
       System.arraycopy(this.buf,37,tmpbyte,0,2);
       tmpstr = new String(tmpbyte);
       int tmpMinute = Integer.parseInt(tmpstr);
       //取秒
       System.arraycopy(this.buf,39,tmpbyte,0,2);
       tmpstr = new String(tmpbyte);
       int tmpSecond = Integer.parseInt(tmpstr);
       //设置时间
       Calendar calendar = Calendar.getInstance();
       calendar.set(tmpYear,tmpMonth,tmpDay,tmpHour,tmpMinute,tmpSecond);

       return calendar.getTime();
     }
     catch(Exception e)
     {
        return null;
     }
  }


  /**
   * 返回短消息发送号码
   * @return
   */
  public String getSrcTermID()
  {
     byte[] srcTermId = new byte[21];
     System.arraycopy(this.buf,41,srcTermId,0,21);
     return new String(srcTermId).trim();
  }

  /**
   * 返回短消息的接收号码
   * @return
   */
  public String getDestTermID()
  {
    byte[] destTermId = new byte[21];
    System.arraycopy(this.buf,62,destTermId,0,21);
    return new String(destTermId).trim();
  }

  public int getMsgLength()
  {
    return ((int)this.buf[84] & 0xff);
  }

  /**
   * 返回消息的内容
   */
   public String getMsgContent()
   {
     int len = ((int)this.buf[84] & 0xff);
     byte[] content = new byte[len];
     System.arraycopy(this.buf,85,content,0,len);
     return new String(content).trim();

   }

   /**
    * 返回拥塞状态
    */
    public int getCongestionState()
    {
      int pos = 89 + ((int)this.buf[84] & 0xff);
      return this.buf[pos];

    }

    /**
     * 返回状态报告
     */
    public String getStat()
    {
        if(deliverType == 1)
        {
            byte tmpStat[] = new byte[7];
            System.arraycopy(super.buf, 166, tmpStat, 0, 7);
            return (new String(tmpStat)).trim();
        } else
        {
            return null;
        }
    }

    /**
     * 返回状态报告中,SMGW提交短消息到SMSC的时间
     */
    public Date getSubmitDate()
    {
        if(deliverType == 1)
        {
            byte tmpbyte[] = new byte[2];
            int i = 129;
            System.arraycopy(super.buf, i, tmpbyte, 0, 2);
            String tmpstr = new String(tmpbyte);
            i += 2;
            int tmpYear = 2000 + Integer.parseInt(tmpstr);
            System.arraycopy(super.buf, i, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            i += 2;
            int tmpMonth = Integer.parseInt(tmpstr) - 1;
            System.arraycopy(super.buf, i, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            i += 2;
            int tmpDay = Integer.parseInt(tmpstr);
            System.arraycopy(super.buf, i, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            i += 2;
            int tmpHour = Integer.parseInt(tmpstr);
            System.arraycopy(super.buf, i, tmpbyte, 0, 2);
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

    /**
     * 返回状态报告中,短消息产生状态报告时间
     */
    public Date getDoneDate()
    {
        if(deliverType == 1)
        {
            byte tmpbyte[] = new byte[2];
            int i = 150;
            System.arraycopy(super.buf, i, tmpbyte, 0, 2);
            String tmpstr = new String(tmpbyte);
            i += 2;
            int tmpYear = 2000 + Integer.parseInt(tmpstr);
            System.arraycopy(super.buf, i, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            i += 2;
            int tmpMonth = Integer.parseInt(tmpstr) - 1;
            System.arraycopy(super.buf, i, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            i += 2;
            int tmpDay = Integer.parseInt(tmpstr);
            System.arraycopy(super.buf, i, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            i += 2;
            int tmpHour = Integer.parseInt(tmpstr);
            System.arraycopy(super.buf, i, tmpbyte, 0, 2);
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

  /**
   * 返回消息体所有的字段与内容
   */
   public String toString(){

       StringBuffer strBuf = new StringBuffer(600);
       strBuf.append("CNGPDeliverMessage: ");
       strBuf.append("PacketLength=" + this.getMsgLength());
       strBuf.append(",RequestID=" + this.getRequestId());
       strBuf.append(",Status=" + this.getStatus());
       strBuf.append(",Sequence_Id=" + this.getSequenceId());
       strBuf.append(",MsgID=" + new String(this.getMsgId()));
       strBuf.append(",IsReport=" + this.getIsReport());
       strBuf.append(",MsgFormat=" + this.getMsgFormat());
       strBuf.append(",RecvTime=" + this.getRecvTime());
       strBuf.append(",SrcTermID=" + this.getSrcTermID());
       strBuf.append(",DestTermID=" + this.getDestTermID());
       strBuf.append(",MsgLength=" + this.getMsgLength());
       strBuf.append(",MsgContent=" + new String(this.getMsgContent()));
       if(deliverType == 1)
       {
           strBuf.append("[Stat=").append(this.getStat());
           strBuf.append(",SubmitDate=").append(this.getSubmitDate());
           strBuf.append(",DoneDate=").append(this.getDoneDate()).append("]");
       }

       strBuf.append(",CongestionState=" + this.getCongestionState());
       return strBuf.toString();
   }

}

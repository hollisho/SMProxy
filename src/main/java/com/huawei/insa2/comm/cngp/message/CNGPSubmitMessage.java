package com.huawei.insa2.comm.cngp.message;

import java.io.*;
import java.util.*;
import java.lang.IllegalArgumentException;
import java.util.Date;
import com.huawei.insa2.util.*;
import com.huawei.insa2.comm.cngp.CNGPConstant;
import java.text.SimpleDateFormat;

/**
 * 定义发送短信消息(CNGP_Submit)。
 * @author       linmeilong
 * @version      1.0
 */

public class CNGPSubmitMessage
    extends CNGPMessage {
  //存放调试时输出信息的变量
  private StringBuffer strBuf;

  /**
   * 创建一个发送短信消息,传入消息的各字段的值，并对各参数值进行判断，不符合要求则抛出异常
   * 按要求把输入的参数转换为一个byte类型的数组
   * @param SPId  SP的企业代码
   * @param subType 短消息子类型
   * @param needReport  是否返回状态报告
   * @param priority 发送优先级
   * @param serviceId 业务类型
   * @param feeType 资费类型
   * @param feeUserType 计费用户类型字段
   * @param feeCode 每条短消息的信息费
   * @param msgFormat 短消息格式
   * @param validTime 有效时间
   * @param atTime 定时发送时间
   * @param srcTermId 短消息发送用户号码
   * @param chargeTermId 计费用户号码
   * @param destTermIdCount 短消息接收号码总数
   * @param destTermId 短消息接收号码
   * @param msgLength 短消息长度
   * @param msgContent 短消息内容
   * @param protocolValue 协议标识
   * @throws IllegalArgumentException
   */
  public CNGPSubmitMessage(
      String SPId,//SP的企业代码
      int subType,//短消息子类型（0＝取消订阅，1＝订阅或点播请求，2＝点播下发，3＝订阅下发，其他保留）
      int needReport,//是否返回状态报告（0＝不要求，1＝要求）
      int priority,//发送优先级（从0到3）3为最高级
      String serviceId,//业务类型
      String feeType,//资费类型:00=免费 01=按条收费 02=包月 03=封顶 04=包月扣费请求 05=CR话单  其他：保留
      int feeUserType,//计费用户类型字段,0：对目的终端计费 1：对源终端计费 2：对SP计费 3：按照计费用户号码计费 其他: 保留
      String feeCode,//每条短消息的信息费，单位：分
      int msgFormat,//短消息格式
      Date validTime,//有效时间
      Date atTime,//定时发送时间(null:立即发送)
      String srcTermId,//短消息发送用户号码
      String chargeTermId,//计费用户号码
      int destTermIdCount,//短消息接收号码总数
      String[] destTermId,//短消息接收号码
      int msgLength,//短消息长度
      String msgContent,//短消息内容
      int protocolValue//协议标识
      ) throws IllegalArgumentException {
    if (SPId == null) {
      throw new IllegalArgumentException(CNGPConstant.SUBMIT_INPUT_ERROR
                                         + ":SPId " + CNGPConstant.STRING_NULL);
    }

    if (SPId.length() > 10) {
      throw new IllegalArgumentException(CNGPConstant.SUBMIT_INPUT_ERROR
                                         + ":SPId " +
                                         CNGPConstant.STRING_LENGTH_GREAT +
                                         "10");
    }
    if (subType < 0 || subType > 3) {
      throw new IllegalArgumentException(CNGPConstant.SUBMIT_INPUT_ERROR
                                         + ":subType " +
                                         CNGPConstant.INT_SCOPE_ERROR);
    }
    if (needReport < 0 || needReport > 1) {
      throw new IllegalArgumentException(CNGPConstant.SUBMIT_INPUT_ERROR
                                         + ":needReport " +
                                         CNGPConstant.INT_SCOPE_ERROR);
    }

    if (priority < 0 || priority > 3) {
      throw new IllegalArgumentException(CNGPConstant.SUBMIT_INPUT_ERROR
                                         + ":priority " +
                                         CNGPConstant.INT_SCOPE_ERROR);
    }
    if (serviceId == null) {
      throw new IllegalArgumentException(CNGPConstant.SUBMIT_INPUT_ERROR
                                         + ":serviceId " +
                                         CNGPConstant.STRING_NULL);
    }

    if (serviceId.length() > 10) {
      throw new IllegalArgumentException(CNGPConstant.SUBMIT_INPUT_ERROR
                                         + ":serviceId " +
                                         CNGPConstant.STRING_LENGTH_GREAT +
                                         "10");
    }
    if (feeType == null) {
      throw new IllegalArgumentException(CNGPConstant.SUBMIT_INPUT_ERROR
                                         + ":feeType " +
                                         CNGPConstant.STRING_NULL);
    }

    if (feeType.length() > 2) {
      throw new IllegalArgumentException(CNGPConstant.SUBMIT_INPUT_ERROR
                                         + ":feeType " +
                                         CNGPConstant.STRING_LENGTH_GREAT + "2");
    }

    if (feeUserType < 0 || feeUserType > 255) {
      throw new IllegalArgumentException(CNGPConstant.SUBMIT_INPUT_ERROR
                                         + ":feeUserType " +
                                         CNGPConstant.INT_SCOPE_ERROR);
    }

    if (feeCode == null) {
      throw new IllegalArgumentException(CNGPConstant.SUBMIT_INPUT_ERROR
                                         + ":feeCode " +
                                         CNGPConstant.STRING_NULL);
    }

    if (feeCode.length() > 6) {
      throw new IllegalArgumentException(CNGPConstant.SUBMIT_INPUT_ERROR
                                         + ":feeCode " +
                                         CNGPConstant.STRING_LENGTH_GREAT + "6");
    }
    if (msgFormat < 0 || msgFormat > 255) {
      throw new IllegalArgumentException(CNGPConstant.SUBMIT_INPUT_ERROR
                                         + ":msgFormat " +
                                         CNGPConstant.INT_SCOPE_ERROR);
    }
      if (srcTermId == null) {
        throw new IllegalArgumentException(CNGPConstant.SUBMIT_INPUT_ERROR
                                           + ":srcTermId " +
                                           CNGPConstant.STRING_NULL);
      }

      if (srcTermId.length() > 21) {
        throw new IllegalArgumentException(CNGPConstant.SUBMIT_INPUT_ERROR
                                           + ":srcTermId " +
                                           CNGPConstant.STRING_LENGTH_GREAT +
                                           "21");
      }
      if (chargeTermId == null) {
        throw new IllegalArgumentException(CNGPConstant.SUBMIT_INPUT_ERROR
                                           + ":chargeTermId " +
                                           CNGPConstant.STRING_NULL);
      }

      if (chargeTermId.length() > 21) {
        throw new IllegalArgumentException(CNGPConstant.SUBMIT_INPUT_ERROR
                                           + ":chargeTermId " +
                                           CNGPConstant.STRING_LENGTH_GREAT +
                                           "21");
      }
      if (destTermIdCount < 0 || destTermIdCount > 100) {
        throw new IllegalArgumentException(CNGPConstant.SUBMIT_INPUT_ERROR
                                           + ":destTermIdCount " +
                                           CNGPConstant.INT_SCOPE_ERROR);
      }
      if (destTermId == null) {
        throw new IllegalArgumentException(CNGPConstant.SUBMIT_INPUT_ERROR
                                           + ":destTermId " +
                                           CNGPConstant.STRING_NULL);
      }

      if (msgLength < 0 || msgLength > 255) {
        throw new IllegalArgumentException(CNGPConstant.SUBMIT_INPUT_ERROR
                                           + ":msgLength " +
                                           CNGPConstant.INT_SCOPE_ERROR);
      }
      if (msgContent == null) {
        throw new IllegalArgumentException(CNGPConstant.SUBMIT_INPUT_ERROR
                                           + ":msgContent " +
                                           CNGPConstant.STRING_NULL);
      }

      if (msgContent.length() > 254) {
        throw new IllegalArgumentException(CNGPConstant.SUBMIT_INPUT_ERROR
                                           + ":msgContent " +
                                           CNGPConstant.STRING_LENGTH_GREAT +
                                           "254");
     }
      if (protocolValue < 0 || protocolValue > 255) {
        throw new IllegalArgumentException(CNGPConstant.SUBMIT_INPUT_ERROR
                                           + ":protocolValue " +
                                           CNGPConstant.INT_SCOPE_ERROR);
      }

      /************转化输入的参数与byte类型数组，也就是编码*******************/

      //计算出消息的长度，消息头＋消息体，为定长＋不定长的字段长度(destTermId,msgContent)
      byte[] ms = null;
      try {
        ms = msgContent.getBytes("gb2312");
      }
      catch (UnsupportedEncodingException ex) {
        ex.printStackTrace();
      }

      int len = 132 + 21 * destTermIdCount + ms.length;

      this.buf = new byte[len]; //根据消息长度开辟空间

      //填充消息头参数
      setMsgLength(len);
      setRequestId(CNGPConstant.Submit_Request_Id);
      //8-11位为消息状态，此时不填充
      //12-15位为流水号，发送时产生，此时不填充

      //填充消息体参数
      System.arraycopy(SPId.getBytes(), 0, this.buf, 16, SPId.length());
      this.buf[26] = (byte) subType;
      this.buf[27] = (byte) needReport;
      this.buf[28] = (byte) priority;
      System.arraycopy(serviceId.getBytes(), 0, this.buf, 29, serviceId.length());
      System.arraycopy(feeType.getBytes(), 0, this.buf, 39, feeType.length());
      this.buf[41] = (byte)feeUserType;
      System.arraycopy(feeCode.getBytes(), 0, this.buf, 42, feeCode.length());
      this.buf[48] = (byte) msgFormat;

      SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");

      if (validTime != null) {
        String tmpTime = dateFormat.format(validTime) + "032+";
        System.arraycopy(tmpTime.getBytes(), 0, this.buf, 49, 16);
      }
      if (atTime != null) {
        String tmpTime = dateFormat.format(atTime) + "032+";
        System.arraycopy(tmpTime.getBytes(), 0, this.buf, 66, 16);
      }
      System.arraycopy(srcTermId.getBytes(), 0, this.buf, 83, srcTermId.length());
      System.arraycopy(chargeTermId.getBytes(), 0, this.buf, 104,
                       chargeTermId.length());
      this.buf[125] = (byte) destTermIdCount;
      int i = 0;
      for (i = 0; i < destTermIdCount; i++) {
        System.arraycopy(destTermId[i].getBytes(), 0, this.buf, 126 + i * 21,
                         destTermId[i].length());
      }
      int pos = 126 + 21 * destTermIdCount;
      this.buf[pos] = (byte) ms.length;
      pos++;
      //短消息内容
      System.arraycopy(ms, 0, this.buf, pos,
                       ms.length);
      pos += ms.length;

      //protocolTag
      TypeConvert.short2byte(0x100, this.buf, pos);
      pos += 2;

      TypeConvert.short2byte((short)1, this.buf, pos);
      pos += 2;

      this.buf[pos] = (byte) protocolValue;

      //把输入的参数记录到strBuf变量中，以便输出到屏幕
      strBuf = new StringBuffer(600);
      strBuf.append(",SPId=" + SPId);
      strBuf.append(",subType=" + subType);
      strBuf.append(",NeedReport=" + needReport);
      strBuf.append(",Priority=" + priority);
      strBuf.append(",ServiceID=" + serviceId);
      strBuf.append(",FeeType=" + feeType);
      strBuf.append(",feeUserType=" + feeUserType);
      strBuf.append(",FeeCode=" + feeCode);
      strBuf.append(",msgFormat=" + msgFormat);
      strBuf.append(",validTime=" + validTime);
      strBuf.append(",atTime=" + atTime);
      strBuf.append(",SrcTermID=" + srcTermId);
      strBuf.append(",ChargeTermID=" + chargeTermId);
      strBuf.append(",DestTermIDCount=" + destTermIdCount);
      for (int t = 0; t < destTermIdCount; t++) {
        strBuf.append(",DestTermID[" + t + "]=" + destTermId[t]);
      }
      strBuf.append(",MsgLength=" + ms.length);
      strBuf.append(",MsgContent=" + msgContent);
      strBuf.append(",protocolValue=" + protocolValue);
    }

    /**
     * 返回消息体所有的字段与内容
     */
    public String toString() {

      StringBuffer outBuf = new StringBuffer(600);
      outBuf.append("CNGPSubmitMessage: ");
      outBuf.append("PacketLength=" + this.getMsgLength());
      outBuf.append(",RequestID=" + this.getRequestId());
      outBuf.append(",Status=" + this.getStatus());
      outBuf.append(",SequenceID=" + this.getSequenceId());
      if (strBuf != null) {
        outBuf.append(strBuf.toString());
      }
      return outBuf.toString();
    }

  }

package com.huawei.insa2.comm.cngp.message;

/**
 * 定义请求登录消息结构
 * @author linmeilong
 * @version 1.0
 */
import java.util.Date;
import java.text.SimpleDateFormat;
import com.huawei.insa2.comm.cngp.CNGPConstant;
import com.huawei.insa2.util.*;
import java.text.SimpleDateFormat;

public class CNGPLoginMessage extends CNGPMessage{

  //存放调试时输出信息的变量
   private StringBuffer strBuf;

  /**
   * 构造登录的消息,对消息进行判断，并按要求转换为一个byte类型的数组
   * @param clientId clientId
   * @loginMode   登录类型
   * @param shared_Secret 客户端密码
   * @param timeStamp 时间戳的明文
   * @param version 双方协商的版本号
   */
  public CNGPLoginMessage(
     String clientId,
     String shared_Secret,
     int loginMode,
     Date timeStamp,
     int version
  ) throws IllegalArgumentException {

       /**对输入的参数进行检查**/
       if( clientId == null ){
          throw new IllegalArgumentException(CNGPConstant.CONNECT_INPUT_ERROR
                                                   + ":clientId " + CNGPConstant.STRING_NULL);
       }
       if( clientId.length() > 10){
          throw new IllegalArgumentException(CNGPConstant.CONNECT_INPUT_ERROR
                                                   + ":clientId " + CNGPConstant.STRING_LENGTH_GREAT + "8");
       }
       if( shared_Secret.length() > 15){
          throw new IllegalArgumentException(CNGPConstant.CONNECT_INPUT_ERROR
                                                   + ":shared_Secret " + CNGPConstant.STRING_LENGTH_GREAT + "8");
       }

       if(loginMode <0 || loginMode >255){
            throw new IllegalArgumentException(CNGPConstant.CONNECT_INPUT_ERROR
                                                   + ":loginMode " + CNGPConstant.INT_SCOPE_ERROR);
       }

       if( version <0 || version > 255){
            throw new IllegalArgumentException(CNGPConstant.CONNECT_INPUT_ERROR
                                                   + ":version " + CNGPConstant.INT_SCOPE_ERROR);
       }

       //计算出消息的长度，消息头＋消息体
       int len = 48;

       this.buf = new byte[len]; //根据消息长度开辟空间

       //填充消息头参数
       //设置消息的总长度
       setMsgLength(len);
       //设置消息的请求标识
       setRequestId(CNGPConstant.Login_Request_Id);
       //8-11位为命令状态，发送时不需要填充
       //12-15位为消息流水号，发送时产生，此时不需要填充

       //填充消息体参数
       //ClientID,16-25位
       System.arraycopy(clientId.getBytes(),0,this.buf,16,clientId.length());
       //SP认证码26－41位经过MD5加密的 MD5(clientId + 7个0 + shared_Secret + 10位时间)
       if( shared_Secret != null ){
           len = clientId.length() + 17  + shared_Secret.length();
       }
       else{
          len = clientId.length() + 17;
       }
       byte[] tmpbuf = new byte[len];
       int tmploc = 0;
       System.arraycopy(clientId.getBytes(),0,tmpbuf,0,clientId.length());
       tmploc = clientId.length()+7;
       if( shared_Secret != null ){
           System.arraycopy(shared_Secret.getBytes(),0,tmpbuf,tmploc,shared_Secret.length());
           tmploc = tmploc + shared_Secret.length();
       }
       //把Date型的timeStamp转换为10位字符串
       SimpleDateFormat  dateFormat = new SimpleDateFormat("yyMMddHHmmss");
       String strTimeStamp = dateFormat.format(timeStamp).substring(2);
       System.arraycopy(strTimeStamp.toString().getBytes(),0,tmpbuf,tmploc,10);
       SecurityTools.md5(tmpbuf,0,len,this.buf,26);

       //登录类型
       this.buf[42] = (byte)loginMode;

       //43-46位，时间
       TypeConvert.int2byte(Integer.parseInt(strTimeStamp),buf,43);

       //版本号
       this.buf[47] = (byte)version;

       //把输入的参数记录到outStr变量中，以便输出到屏幕
       strBuf = new StringBuffer(300);
       strBuf.append(",clientId=" + clientId);
       strBuf.append(",shared_Secret=" + shared_Secret);
       strBuf.append(",loginMode=" + loginMode);
       strBuf.append(",timeStamp=" + timeStamp);
       strBuf.append(",version=" + version);
    }

   /**
    * 返回消息体所有的字段与内容
    */
    public String toString(){
        StringBuffer outStr = new StringBuffer(300);
        outStr.append("CNGPLoginMessage:");
        outStr.append("PacketLength=" + this.buf.length);
        outStr.append(",RequestID=" + CNGPConstant.Login_Request_Id);
        outStr.append(",SequenceId=" + this.getSequenceId());

        if(strBuf != null)
        {
           outStr.append(strBuf.toString());
        }
        return outStr.toString();
    }
}

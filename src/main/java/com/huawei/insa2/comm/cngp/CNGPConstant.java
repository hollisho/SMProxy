package com.huawei.insa2.comm.cngp;

/**
 * 定义系统所用到的常量
 * @author linmeilong
 * @version 1.0
 */

import com.huawei.insa2.util.Resource;

public class CNGPConstant {

  /**存放系统是否为调试状态*/
  public static boolean debug;

  /**错误常量-正在登录*/
  public static String LOGINING;

  /**错误常量-登录过程出错*/
  public static String LOGIN_ERROR;

  /**错误常量-消息发送有误*/
  public static String SEND_ERROR;

  /**错误常量-连接不成功*/
  public static String CONNECT_TIMEOUT;

  /**错误常量-连接消息结构错*/
  public static String STRUCTURE_ERROR;

  /**错误常量-非法SP_ID*/
  public static String NONLICETSP_ID;

  /**错误常量-SP认证错*/
  public static String SP_ERROR;

  /**错误常量-版本太高*/
  public static String VERSION_ERROR;

  /**错误常量-其它错误*/
  public static String OTHER_ERROR;

  /**错误常量-心跳异常(心跳发出多次，没有返回值或返回的值为不成功)*/
  public static String HEARTBEAT_ABNORMITY;

  /**错误常量-发送短信消息参数输入有误*/
  public static String SUBMIT_INPUT_ERROR;

  /**错误常量-转发短信消息参数输入有误*/
  public static String FORWARD_INPUT_ERROR;

  /**错误常量-mt_route_update请求消息参数输入有误*/
  public static String MTROUTEUPDATE_INPUT_ERROR;

  /**错误常量-mo_route_update请求消息参数输入有误*/
  public static String MOROUTEUPDATE_INPUT_ERROR;

  /**错误常量-请求连接消息参数输入有误*/
  public static String CONNECT_INPUT_ERROR;

  /**错误常量-删除短信请求消息参数输入有误*/
    public static String CANCEL_INPUT_ERROR;

  /**错误常量-发送短信状态查询请求参数输入有误*/
  public static String QUERY_INPUT_ERROR;

  /**错误常量-下发短信消息参数输入有误*/
  public static String DELIVER_REPINPUT_ERROR;

  /**错误常量-定义响应激活请求的消息参数输入有误*/
  public static String ACTIVE_REPINPUT_ERROR;

  /**错误常量-SMC发送的消息错误*/
  public static String SMC_MESSAGE_ERROR;

  /**错误常量-小于0或大于255*/
  public static String INT_SCOPE_ERROR;

  /**错误常量-字符串的长度大于*/
  public static String STRING_LENGTH_GREAT;

  /**错误常量-值为空*/
  public static String STRING_NULL;

  /**错误常量－不等于0或1*/
  public static String VALUE_ERROR;

  /**错误常量-fee_UserType的值小于0或大于3*/
  public static String FEE_USERTYPE_ERROR;

  /**错误常量-registered_Delivery的值小于0或大于2*/
  public static String REGISTERED_DELIVERY_ERROR;

  /**错误常量-pk_Total的值小于1或大于255*/
  public static String PK_TOTAL_ERROR;

  /**错误常量-pk_Number的值小于1或大于255*/
  public static String PK_NUMBER_ERROR;

  /**错误常量-NeedReport的值不为0或1*/
  public static String NEEDREPORT_ERROR;

  /**错误常量-Priority的值小于0或大于9*/
  public static String PRIORITY_ERROR;

  /**错误常量-短消息接收号码个数大于100*/
  public static String DESTTERMID_ERROR;


  //定义协议中用到Command_ID的值
  public static final int Login_Request_Id = 0x00000001;

  public static final int Login_Resp_Request_Id = 0x80000001;

  public static final int Submit_Request_Id = 0x00000002 ;

  public static final int Submit_Resp_Request_Id = 0x80000002;

  public static final int Deliver_Request_Id = 0x00000003;

  public static final int Deliver_Resp_Request_Id = 0x80000003;

  public static final int Active_Test_Request_Id = 0x00000004;

  public static final int Active_Test_Resp_Request_Id =0x80000004;

  public static final int Forward_Request_Id = 0x00000005;

  public static final int Forward_Resp_Request_Id = 0x80000005;

  public static final int Exit_Request_Id = 0x00000006;

  public static final int Exit_Resp_Request_Id = 0x80000006;

  public CNGPConstant() {
    }

  /**
   * 系统初始化时调用，读取常量资源字符串。
   */
   public static void initConstant(Resource resource){
     if( LOGINING == null ){
       LOGINING = resource.get("smproxy/logining");
       LOGIN_ERROR = resource.get("smproxy/login-error");
       SEND_ERROR = resource.get("smproxy/send-error");
       CONNECT_TIMEOUT = resource.get("smproxy/connect-timeout");
       STRUCTURE_ERROR = resource.get("smproxy/structure-error");
       NONLICETSP_ID = resource.get("smproxy/nonlicetsp-id");
       SP_ERROR = resource.get("smproxy/sp-error");
       VERSION_ERROR = resource.get("smproxy/version-error");
       OTHER_ERROR = resource.get("smproxy/other-error");
       HEARTBEAT_ABNORMITY = resource.get("smproxy/heartbeat-abnormity");
       SUBMIT_INPUT_ERROR = resource.get("smproxy/submit-input-error");
       CONNECT_INPUT_ERROR = resource.get("smproxy/connect-input-error");
       CANCEL_INPUT_ERROR = resource.get("smproxy/cancel-input-error");
       QUERY_INPUT_ERROR = resource.get("smproxy/query-input-error");
       DELIVER_REPINPUT_ERROR = resource.get("smproxy/deliver-repinput-error");
       ACTIVE_REPINPUT_ERROR = resource.get("smproxy/active-repinput-error");
       SMC_MESSAGE_ERROR = resource.get("smproxy/smc-message-error");
       INT_SCOPE_ERROR = resource.get("smproxy/int-scope-error");
       STRING_LENGTH_GREAT = resource.get("smproxy/string-length-great");
       STRING_NULL = resource.get("smproxy/string-null");
       VALUE_ERROR = resource.get("smproxy/value-error");
       FEE_USERTYPE_ERROR = resource.get("smproxy/fee-usertype-error");
       REGISTERED_DELIVERY_ERROR = resource.get("smproxy/registered-delivery-erro");
       PK_TOTAL_ERROR = resource.get("smproxy/pk-total-error");
       PK_NUMBER_ERROR = resource.get("smproxy/pk-number-error");
       FORWARD_INPUT_ERROR = resource.get("smproxy/forward_input_error");
       MTROUTEUPDATE_INPUT_ERROR = resource.get("smproxy/mtrouteupdate_input_error");
       MOROUTEUPDATE_INPUT_ERROR = resource.get("smproxy/morouteupdate_input_error");
       NEEDREPORT_ERROR = resource.get("smproxy/needreport_error");
       PRIORITY_ERROR = resource.get("smproxy/priority_error");
       DESTTERMID_ERROR = resource.get("smproxy/desttermid_error");
     }
  }
}

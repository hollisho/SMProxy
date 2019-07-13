package com.huawei.insa2.comm.smpp;

/**
 * 定义系统所用到的常量
 * @author linmeilong
 * @version 1.0
 */

import com.huawei.insa2.util.Resource;

public class SMPPConstant
{

  /**存放系统是否为调试状态*/
  public static boolean debug;

/**错误常量-正在登录*/
    public static String LOGINING;
    public static String LOGIN_ERROR;
    public static String SEND_ERROR;
    public static String CONNECT_TIMEOUT;
    public static String STRUCTURE_ERROR;
    public static String VERSION_ERROR;
    public static String OTHER_ERROR;
    public static String HEARTBEAT_ABNORMITY;
    public static String SUBMIT_INPUT_ERROR;
    public static String CONNECT_INPUT_ERROR;
    public static String DELIVER_REPINPUT_ERROR;
    public static String ACTIVE_REPINPUT_ERROR;
    public static String SMC_MESSAGE_ERROR;
    public static String STRING_LENGTH_GREAT;
    public static String STRING_LENGTH_NOTEQUAL;
    public static String STRING_NULL;

  //定义协议中用到Command_ID的值
    public static final int Generic_Nack_Command_Id = 0;
    public static final int Bind_Receiver_Command_Id = 1;
    public static final int Bind_Receiver_Rep_Command_Id = 0x80000001;
    public static final int Bind_Transmitter_Command_Id = 2;
    public static final int Bind_Transmitter_Rep_Command_Id = 0x80000002;
    public static final int Submit_Command_Id = 4;
    public static final int Submit_Rep_Command_Id = 0x80000004;
    public static final int Deliver_Command_Id = 5;
    public static final int Deliver_Rep_Command_Id = 0x80000005;
    public static final int Unbind_Command_Id = 6;
    public static final int Unbind_Rep_Command_Id = 0x80000006;
    public static final int Enquire_Link_Command_Id = 21;
    public static final int Enquire_Link_Rep_Command_Id = 0x80000015;

    public SMPPConstant()
    {
    }

    /**
     * 系统初始化时调用，读取常量资源字符串。
     */
    public static void initConstant(Resource resource)
    {
        if(LOGINING == null)
        {
            LOGINING = resource.get("smproxy/logining");
            LOGIN_ERROR = resource.get("smproxy/login-error");
            SEND_ERROR = resource.get("smproxy/send-error");
            CONNECT_TIMEOUT = resource.get("smproxy/connect-timeout");
            STRUCTURE_ERROR = resource.get("smproxy/structure-error");
            VERSION_ERROR = resource.get("smproxy/version-error");
            OTHER_ERROR = resource.get("smproxy/other-error");
            HEARTBEAT_ABNORMITY = resource.get("smproxy/heartbeat-abnormity");
            SUBMIT_INPUT_ERROR = resource.get("smproxy/submit-input-error");
            CONNECT_INPUT_ERROR = resource.get("smproxy/connect-input-error");
            DELIVER_REPINPUT_ERROR = resource.get("smproxy/deliver-repinput-error");
            ACTIVE_REPINPUT_ERROR = resource.get("smproxy/active-repinput-error");
            SMC_MESSAGE_ERROR = resource.get("smproxy/smc-message-error");
            STRING_LENGTH_NOTEQUAL = resource.get("smproxy/string-length-notequal");
            STRING_LENGTH_GREAT = resource.get("smproxy/string-length-great");
            STRING_NULL = resource.get("smproxy/string-null");
        }
    }
}

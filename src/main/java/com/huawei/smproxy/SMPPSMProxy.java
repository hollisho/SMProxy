// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi
// Source File Name:   SMPPSMProxy.java

package com.huawei.smproxy;

import com.huawei.insa2.comm.PLayer;
import com.huawei.insa2.comm.PSocketConnection;
import com.huawei.insa2.comm.smpp.SMPPConnection;
import com.huawei.insa2.comm.smpp.SMPPTransaction;
import com.huawei.insa2.comm.smpp.message.*;
import com.huawei.insa2.util.Args;
import java.io.IOException;
import java.util.Map;

// Referenced classes of package com.huawei.smproxy:
//            SMPPEventAdapter

/**
 * 对外提供的API接口。
 */

public class SMPPSMProxy
{

  /**
   * 收发消息使用的连接对象。
   */
    private SMPPConnection conn;

    /**
     * 建立连接并登录。
     * @param args 保存建立连接所需的参数。
     */
    public SMPPSMProxy(Map args)
    {
        this(new Args(args));
    }

    /**
     * 建立连接并登录。
     * @param args 保存建立连接所需的参数。
     */
    public SMPPSMProxy(Args args)
    {
        conn = new SMPPConnection(args);
        //增加事件监听器，对SMC主动发送的消息进行处理
        conn.addEventListener(new SMPPEventAdapter(this));
        conn.waitAvailable();//等待连接建立完成
        if(!conn.available())
            throw new IllegalStateException(conn.getError());
        else
            return;
    }

    /**
     * 发送消息，阻塞直到收到响应或超时。
     * 返回为收到的消息
     * @exception PException 超时或通信异常。
     */
    public SMPPMessage send(SMPPMessage message)
        throws IOException
    {
        if(message == null)//消息为空，不发送
            return null;
          //建立一事务
        SMPPTransaction t = (SMPPTransaction)conn.createChild();
        try
        {
            t.send(message);
            t.waitResponse();
            SMPPMessage rsp = t.getResponse();
            SMPPMessage smppmessage = rsp;
            return smppmessage;
        }
        finally
        {
            t.close();
        }
    }

    /**
     * 连接终止的处理，由API使用者实现
     * SMC连接终止后，需要执行动作的接口
     */
    public void onTerminate()
    {
    }

    /**
     * 对收到消息的处理。API使用者应该重载本方法来处理来自短信中心的Deliver消息，
     * 并返回响应消息。这里缺省的实现是返回一个成功收到的响应。
     * @param msg 从短消息中心来的消息。
     * @return 应该回的响应，由API使用者生成。
     */
    public SMPPMessage onDeliver(SMPPDeliverMessage msg)
    {
        return new SMPPDeliverRespMessage(0);
    }

    /**
     * 终止连接。调用之后连接将永久不可用。
     */
    public void close()
    {
        conn.close();
    }

    /**
     * 提供给外部调用获取TCP连接对象
     */
    public SMPPConnection getConn()
    {
        return conn;
    }

    /**
     * 提供给业务层调用的获取连接状态的方法
     */
    public String getConnState()
    {
        return conn.getError();
    }
}

// Decompiled by DJ v2.9.9.60 Copyright 2000 Atanas Neshkov  Date: 2005-7-11 18:01:14
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3)
// Source File Name:   SMProxy.java

package com.huawei.smproxy;

import com.huawei.insa2.comm.PLayer;
import com.huawei.insa2.comm.PSocketConnection;
import com.huawei.insa2.comm.cmpp.CMPPConnection;
import com.huawei.insa2.comm.cmpp.CMPPTransaction;
import com.huawei.insa2.comm.cmpp.message.*;
import com.huawei.insa2.util.Args;
import java.io.IOException;
import java.util.Map;

// Referenced classes of package com.huawei.smproxy:
//            CMPPEventAdapter

/**
 * 对外提供的API接口。
 */
public class SMProxy
{

  /**
   * 收发消息使用的连接对象。
   */
  private CMPPConnection conn;

  /**
   * 建立连接并登录。
   * @param args 保存建立连接所需的参数。
   */
    public SMProxy(Map args)
    {
        this(new Args(args));
    }

    /**
     * 建立连接并登录。
     * @param args 保存建立连接所需的参数。
     */
    public SMProxy(Args args)
    {
        conn = new CMPPConnection(args);
        //增加事件监听器，对SMC主动发送的消息进行处理
        conn.addEventListener(new CMPPEventAdapter(this));
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
    public CMPPMessage send(CMPPMessage message)
        throws IOException
    {
        if(message == null)//消息为空，不发送
            return null;
          //建立一事务
        CMPPTransaction t = (CMPPTransaction)conn.createChild();
        try
        {
            t.send(message);
            t.waitResponse();
            CMPPMessage rsp = t.getResponse();
            CMPPMessage cmppmessage = rsp;
            return cmppmessage;
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
    public CMPPMessage onDeliver(CMPPDeliverMessage msg)
    {
        return new CMPPDeliverRepMessage(msg.getMsgId(), 0);
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
    public CMPPConnection getConn()
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
// Decompiled by DJ v2.9.9.60 Copyright 2000 Atanas Neshkov  Date: 2005-7-11 18:01:14
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3)
// Source File Name:   SMProxy30.java

package com.huawei.smproxy;

import com.huawei.insa2.comm.PLayer;
import com.huawei.insa2.comm.PSocketConnection;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.comm.cmpp30.CMPP30Connection;
import com.huawei.insa2.comm.cmpp30.CMPP30Transaction;
import com.huawei.insa2.comm.cmpp30.message.CMPP30DeliverMessage;
import com.huawei.insa2.comm.cmpp30.message.CMPP30DeliverRepMessage;
import com.huawei.insa2.util.Args;
import java.io.IOException;
import java.util.Map;

// Referenced classes of package com.huawei.smproxy:
//            CMPP30EventAdapter

/**
 * 对外提供的API接口。
 */
public class SMProxy30
{

  /**
   * 收发消息使用的连接对象。
   */
  private CMPP30Connection conn;

  /**
   * 建立连接并登录。
   * @param args 保存建立连接所需的参数。
   */
    public SMProxy30(Map args)
    {
         //完成初始化和向ISMG登录等工作
        this(new Args(args));
    }

    /**
     * 建立连接并登录。
     * @param args 保存建立连接所需的参数。
     */
    public SMProxy30(Args args)
    {
         //完成初始化和向ISMG登录等工作
        conn = new CMPP30Connection(args);
        //增加事件监听器，对SMC主动发送的消息进行处理
        conn.addEventListener(new CMPP30EventAdapter(this));
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
        CMPP30Transaction t = (CMPP30Transaction)conn.createChild();
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
     * 对收到消息的处理。由API使用者实现。缺省返回成功收到的响应
     * @param msg 从短消息中心来的消息。
     * @return 应该回的响应，由API使用者生成。
     */
    public CMPPMessage onDeliver(CMPP30DeliverMessage msg)
    {
        return new CMPP30DeliverRepMessage(msg.getMsgId(), 0);
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
    public CMPP30Connection getConn()
    {
        return conn;
    }

    /**
    * 提供给业务层调用的获取连接状态的方法
    */
    public String getConnState()
    {
        //返回连接状态的描述
        return conn.getError();
    }

}
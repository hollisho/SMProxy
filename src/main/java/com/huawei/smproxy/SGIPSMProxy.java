// Decompiled by DJ v2.9.9.60 Copyright 2000 Atanas Neshkov  Date: 2005-7-11 18:01:11
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3)
// Source File Name:   SGIPSMProxy.java

package com.huawei.smproxy;

import com.huawei.insa2.comm.PLayer;
import com.huawei.insa2.comm.sgip.*;
import com.huawei.insa2.comm.sgip.message.*;
import com.huawei.insa2.util.Args;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

// Referenced classes of package com.huawei.smproxy:
//            SGIPEventAdapter

/**
 * 对外提供的API接口。
 */
public class SGIPSMProxy
    implements SSEventListener
{

  /**
   * 收发消息使用的连接对象。
   */
  private SGIPConnection conn;
  private SSListener listener;
  private Args args;
  private HashMap serconns;
  private int src_nodeid;

  /**
   * 建立连接并登录。
   * @param args 保存建立连接所需的参数。
   */
    public SGIPSMProxy(Map args)
    {
        this(new Args(args));
    }

    /**
     * 建立连接并登录。
     * @param args 保存建立连接所需的参数。
     */
    public SGIPSMProxy(Args args)
    {
        this.args = args;
        src_nodeid = args.get("source-addr", 0);
    }

    /**
     * 作为客户端建立连接并登录。
     */
    public synchronized boolean connect(String loginName, String loginPass)
    {
        boolean result = true;
        if(loginName != null)
            args.set("login-name", loginName.trim());
        if(loginPass != null)
            args.set("login-pass", loginPass.trim());
        conn = new SGIPConnection(args, true, null);
        //增加事件监听器，对SMC主动发送的消息进行处理
        conn.addEventListener(new SGIPEventAdapter(this, conn));
        conn.waitAvailable();//等待连接建立完成
        if(!conn.available())
        {
            result = false;
            throw new IllegalStateException(conn.getError());
        } else
        {
            return result;
        }
    }

    /**
     * 创建并启动监听器
     */
    public synchronized void startService(String localhost, int localport)
    {
        if(listener != null)
            return;
        try
        {
            listener = new SSListener(localhost, localport, this);
            listener.beginListen();
        }
        catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 关闭监听器以及监听器创建的所有连接
     */
    public synchronized void stopService()
    {
        if(listener == null)
            return;
        listener.stopListen();
        if(serconns != null)
        {
            SGIPConnection conn;
            for(Iterator iterator = serconns.keySet().iterator(); iterator.hasNext(); conn.close())
            {
                String addr = (String)iterator.next();
                conn = (SGIPConnection)serconns.get(addr);
            }

            serconns.clear();
        }
    }

    public synchronized void onConnect(Socket socket)
    {
        String peerIP = socket.getInetAddress().getHostAddress();
        int port = socket.getPort();
        if(serconns == null)
            serconns = new HashMap();
        SGIPConnection conn = new SGIPConnection(args, false, serconns);
        conn.addEventListener(new SGIPEventAdapter(this, conn));
        conn.attach(args, socket);
        serconns.put(new String(String.valueOf(peerIP) + String.valueOf(port)), conn);
    }

    /**
     * 发送消息，阻塞直到收到响应或超时。
     * 返回为收到的消息
     * @exception PException 超时或通信异常。
     */
    public SGIPMessage send(SGIPMessage message)
        throws IOException
    {
        if(message == null)//消息为空，不发送
            return null;
        //建立一事务
        SGIPTransaction t = (SGIPTransaction)conn.createChild();
        t.setSPNumber(src_nodeid);
        Date nowtime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmss");
        String tmpTime = dateFormat.format(nowtime);
        Integer timestamp = new Integer(tmpTime);
        t.setTimestamp(timestamp.intValue());
        try
        {
            t.send(message);
            t.waitResponse();
            SGIPMessage rsp = t.getResponse();
            SGIPMessage sgipmessage = rsp;
            return sgipmessage;
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
    public SGIPMessage onDeliver(SGIPDeliverMessage msg)
    {
        return new SGIPDeliverRepMessage(0);
    }

    /**
     * 对收到状态报告消息的处理。API使用者应该重载本方法来处理来自短信中心的Report消息，
     * 并返回响应消息。这里缺省的实现是返回一个成功收到的响应。
     * @param msg 从短消息中心来的消息。
     * @return 应该回的响应，由API使用者生成。
     */
    public SGIPMessage onReport(SGIPReportMessage msg)
    {
        return new SGIPReportRepMessage(0);
    }

    /**
     * 对收到手机状态配置消息的处理。API使用者应该重载本方法来处理来自短信中心的UserReport消息，
     * 并返回响应消息。这里缺省的实现是返回一个成功处理的响应。
     * @param msg 从短消息中心来的消息。
     * @return 应该回的响应，由API使用者生成。
     */
    public SGIPMessage onUserReport(SGIPUserReportMessage msg)
    {
        return new SGIPUserReportRepMessage(0);
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
    public SGIPConnection getConn()
    {
        return conn;
    }

    /**
     * 提供给业务层调用的获取连接状态的方法
     */
    public String getConnState()
    {
        if(conn != null)
            return conn.getError();
        else
            return "尚未建立连接";
    }
}
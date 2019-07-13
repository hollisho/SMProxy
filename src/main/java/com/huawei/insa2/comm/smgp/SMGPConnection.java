// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi
// Source File Name:   SMGPConnection.java

package com.huawei.insa2.comm.smgp;

import com.huawei.insa2.comm.*;
import com.huawei.insa2.comm.smgp.message.SMGPActiveTestMessage;
import com.huawei.insa2.comm.smgp.message.SMGPActiveTestRespMessage;
import com.huawei.insa2.comm.smgp.message.SMGPExitMessage;
import com.huawei.insa2.comm.smgp.message.SMGPLoginMessage;
import com.huawei.insa2.comm.smgp.message.SMGPLoginRespMessage;
import com.huawei.insa2.comm.smgp.message.SMGPMessage;
import com.huawei.insa2.util.Args;
import com.huawei.insa2.util.Resource;
import java.io.*;
import java.util.Date;

// Referenced classes of package com.huawei.insa2.comm.smgp:
//            SMGPWriter, SMGPReader, SMGPTransaction, SMGPConstant

public class SMGPConnection extends PSocketConnection
{

    private int degree = 0;//计数器,计算连续发出的心跳消息没有响应的个数
    private int hbnoResponseOut = 3;//连续发出心跳消息没有响应达到该数，需要重连
    private String clientid = null;//SP编号或者SMGW编号
    private int version;//双方协商的版本号
    private String shared_secret;

    public SMGPConnection(Args args)
    {
        hbnoResponseOut = args.get("heartbeat-noresponseout", 3);
        clientid = args.get("clientid", "huawei");
        version = args.get("version", 1);
        shared_secret = args.get("shared-secret", "");
        SMGPConstant.debug = args.get("debug", false);
        SMGPConstant.initConstant(getResource());
        init(args);
    }

    protected PWriter getWriter(OutputStream out)
    {
        return new SMGPWriter(out);
    }

    protected PReader getReader(InputStream in)
    {
        return new SMGPReader(in);
    }

    public int getChildId(PMessage message)
    {
        SMGPMessage mes = (SMGPMessage)message;
        int sequenceId = mes.getSequenceId();
        if(mes.getRequestId() == 3 || mes.getRequestId() == 4 || mes.getRequestId() == 6)
            return -1;
        else
            return sequenceId;
    }

    public PLayer createChild()
    {
        return new SMGPTransaction(this);
    }

    public int getTransactionTimeout()
    {
        return transactionTimeout;
    }

    public Resource getResource()
    {
        try
        {
            Resource resource = new Resource(getClass(), "resource");
            return resource;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        Resource resource1 = null;
        return resource1;
    }

    public synchronized void waitAvailable()
    {
        try
        {
            if(getError() == PSocketConnection.NOT_INIT)
                wait(transactionTimeout);
        }
        catch(InterruptedException interruptedexception) { }
    }

    public void close()
    {
        try
        {
            SMGPExitMessage msg = new SMGPExitMessage();
            send(msg);
        }
        catch(PException pexception) { }
        super.close();
    }

    protected void heartbeat()
        throws IOException
    {
        SMGPTransaction t = (SMGPTransaction)createChild();
        SMGPActiveTestMessage hbmes = new SMGPActiveTestMessage();
        t.send(hbmes);
        t.waitResponse();
        SMGPActiveTestRespMessage rsp = (SMGPActiveTestRespMessage)t.getResponse();
        if(rsp == null)
        {
            degree++;
            if(degree == hbnoResponseOut)
            {
                degree = 0;
                throw new IOException(SMGPConstant.HEARTBEAT_ABNORMITY);
            }
        } else
        {
            degree = 0;
        }
        t.close();
    }

    protected synchronized void connect()
    {
        super.connect();
        if(!available())
            return;
        SMGPLoginMessage request = null;
        SMGPLoginRespMessage rsp = null;
        try
        {
            request = new SMGPLoginMessage(clientid, shared_secret, 3, new Date(), version);
        }
        catch(IllegalArgumentException e)
        {
            e.printStackTrace();
            close();
            setError(SMGPConstant.CONNECT_INPUT_ERROR);
        }
        SMGPTransaction t = (SMGPTransaction)createChild();
        try
        {
            t.send(request);
            PMessage m = in.read();
            onReceive(m);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            close();
            setError(String.valueOf(SMGPConstant.LOGIN_ERROR) + String.valueOf(explain(e)));
        }
        rsp = (SMGPLoginRespMessage)t.getResponse();
        if(rsp == null)
        {
            close();
            setError(SMGPConstant.CONNECT_TIMEOUT);
        }
        t.close();
        if(rsp != null && rsp.getStatus() != 0)
        {
            close();
            setError("Fail to login,the status code id ".concat(String.valueOf(String.valueOf(rsp.getStatus()))));
        }
        notifyAll();
    }
}

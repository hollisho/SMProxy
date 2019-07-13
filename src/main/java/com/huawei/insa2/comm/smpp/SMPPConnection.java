// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi
// Source File Name:   SMPPConnection.java

package com.huawei.insa2.comm.smpp;

import com.huawei.insa2.comm.*;
import com.huawei.insa2.comm.smpp.message.SMPPEnquireLinkMessage;
import com.huawei.insa2.comm.smpp.message.SMPPEnquireLinkRespMessage;
import com.huawei.insa2.comm.smpp.message.SMPPLoginMessage;
import com.huawei.insa2.comm.smpp.message.SMPPLoginRespMessage;
import com.huawei.insa2.comm.smpp.message.SMPPMessage;
import com.huawei.insa2.comm.smpp.message.SMPPUnbindMessage;
import com.huawei.insa2.util.Args;
import com.huawei.insa2.util.Resource;
import java.io.*;

// Referenced classes of package com.huawei.insa2.comm.smpp:
//            SMPPWriter, SMPPReader, SMPPTransaction, SMPPConstant

public class SMPPConnection extends PSocketConnection
{

    private int degree = 0;//计数器,计算连续发出的心跳消息没有响应的个数
    private int hbnoResponseOut = 3;//连续发出心跳消息没有响应达到该数，需要重连
    private String systemId;
    private String password;
    private String systemType;
    private byte interfaceVersion;
    private byte addrTon;
    private byte addrNpi;
    private String addressRange;

    public SMPPConnection(Args args)
    {
        systemId = args.get("system-id", "");
        password = args.get("password", "");
        systemType = args.get("system-type", "");
        interfaceVersion = (byte)args.get("interface-version", 34);
        addrTon = (byte)args.get("addr-ton", 0);
        addrNpi = (byte)args.get("addr-npi", 0);
        addressRange = args.get("address-range", "");
        SMPPConstant.debug = args.get("debug", false);
        SMPPConstant.initConstant(getResource());
        init(args);
    }

    protected PWriter getWriter(OutputStream out)
    {
        return new SMPPWriter(out);
    }

    protected PReader getReader(InputStream in)
    {
        return new SMPPReader(in);
    }

    public int getChildId(PMessage message)
    {
        SMPPMessage mes = (SMPPMessage)message;
        int sequenceId = mes.getSequenceId();
        if(mes.getCommandId() == 5 || mes.getCommandId() == 21 || mes.getCommandId() == 6)
            return -1;
        else
            return sequenceId;
    }

    public PLayer createChild()
    {
        return new SMPPTransaction(this);
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
            SMPPUnbindMessage msg = new SMPPUnbindMessage();
            send(msg);
        }
        catch(PException pexception) { }
        super.close();
    }

    protected void heartbeat()
        throws IOException
    {
        SMPPTransaction t = (SMPPTransaction)createChild();
        SMPPEnquireLinkMessage hbmes = new SMPPEnquireLinkMessage();
        t.send(hbmes);
        t.waitResponse();
        SMPPEnquireLinkRespMessage rsp = (SMPPEnquireLinkRespMessage)t.getResponse();
        if(rsp == null)
        {
            degree++;
            if(degree == hbnoResponseOut)
            {
                degree = 0;
                throw new IOException(SMPPConstant.HEARTBEAT_ABNORMITY);
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
        SMPPLoginMessage request = null;
        SMPPLoginRespMessage rsp = null;
        try
        {
            request = new SMPPLoginMessage(1, systemId, password, systemType, interfaceVersion, addrTon, addrNpi, addressRange);
        }
        catch(IllegalArgumentException e)
        {
            e.printStackTrace();
            close();
            setError(SMPPConstant.CONNECT_INPUT_ERROR);
        }
        SMPPTransaction t = (SMPPTransaction)createChild();
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
            setError(String.valueOf(SMPPConstant.LOGIN_ERROR) + String.valueOf(explain(e)));
        }
        rsp = (SMPPLoginRespMessage)t.getResponse();
        if(rsp == null)
        {
            close();
            setError(SMPPConstant.CONNECT_TIMEOUT);
        }
        t.close();
        if(rsp != null && rsp.getStatus() != 0)
        {
            close();
            setError(SMPPConstant.OTHER_ERROR);
        }
        notifyAll();
    }
}

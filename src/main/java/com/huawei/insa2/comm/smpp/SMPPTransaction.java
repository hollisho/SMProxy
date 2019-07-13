// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   SMPPTransaction.java

package com.huawei.insa2.comm.smpp;

import com.huawei.insa2.comm.*;
import com.huawei.insa2.comm.smpp.message.SMPPMessage;
import com.huawei.insa2.util.Debug;

// Referenced classes of package com.huawei.insa2.comm.smpp:
//            SMPPConnection, SMPPConstant

public class SMPPTransaction extends PLayer
{

    private SMPPMessage receive;
    private int sequenceId;

    public SMPPTransaction(PLayer connection)
    {
        super(connection);
        sequenceId = id;
    }

    public synchronized void onReceive(PMessage msg)
    {
        receive = (SMPPMessage)msg;
        sequenceId = receive.getSequenceId();
        if(SMPPConstant.debug)
            Debug.dump(receive.toString());
        notifyAll();
    }

    public void send(PMessage message)
        throws PException
    {
        SMPPMessage mes = (SMPPMessage)message;
        mes.setSequenceId(sequenceId);
        parent.send(message);
        if(SMPPConstant.debug)
            Debug.dump(mes.toString());
    }

    public SMPPMessage getResponse()
    {
        return receive;
    }

    public synchronized void waitResponse()
    {
        if(receive == null)
            try
            {
                wait(((SMPPConnection)parent).getTransactionTimeout());
            }
            catch(InterruptedException interruptedexception) { }
    }
}

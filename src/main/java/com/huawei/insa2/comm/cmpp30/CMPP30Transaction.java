// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   CMPP30Transaction.java

package com.huawei.insa2.comm.cmpp30;

import com.huawei.insa2.comm.*;
import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.util.Debug;

// Referenced classes of package com.huawei.insa2.comm.cmpp30:
//            CMPP30Connection

public class CMPP30Transaction extends PLayer
{

    private CMPPMessage receive;
    private int sequenceId;

    public CMPP30Transaction(PLayer connection)
    {
        super(connection);
        sequenceId = id;
    }

    public synchronized void onReceive(PMessage msg)
    {
        receive = (CMPPMessage)msg;
        sequenceId = receive.getSequenceId();
        if(CMPPConstant.debug)
            Debug.dump(receive.toString());
        notifyAll();
    }

    public void send(PMessage message)
        throws PException
    {
        CMPPMessage mes = (CMPPMessage)message;
        mes.setSequenceId(sequenceId);
        parent.send(message);
        if(CMPPConstant.debug)
            Debug.dump(mes.toString());
    }

    public CMPPMessage getResponse()
    {
        return receive;
    }

    public synchronized void waitResponse()
    {
        if(receive == null)
            try
            {
                wait(((CMPP30Connection)parent).getTransactionTimeout());
            }
            catch(InterruptedException interruptedexception) { }
    }
}

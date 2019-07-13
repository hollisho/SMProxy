// Decompiled by DJ v2.9.9.60 Copyright 2000 Atanas Neshkov  Date: 2005-7-11 17:43:34
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CMPPTransaction.java

package com.huawei.insa2.comm.cmpp;

import com.huawei.insa2.comm.*;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.util.Debug;

// Referenced classes of package com.huawei.insa2.comm.cmpp:
//            CMPPConnection, CMPPConstant

public class CMPPTransaction extends PLayer
{

    public CMPPTransaction(PLayer connection)
    {
        super(connection);
        sequenceId = super.id;
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
        super.parent.send(message);
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
                wait(((CMPPConnection)super.parent).getTransactionTimeout());
            }
            catch(InterruptedException interruptedexception) { }
    }

    private CMPPMessage receive;
    private int sequenceId;
}
// Decompiled by DJ v2.9.9.60 Copyright 2000 Atanas Neshkov  Date: 2005-7-16 19:21:47
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3)
// Source File Name:   SGIPTransaction.java

package com.huawei.insa2.comm.sgip;

import com.huawei.insa2.comm.*;
import com.huawei.insa2.comm.sgip.message.SGIPMessage;
import com.huawei.insa2.util.Debug;

import java.util.*;
import java.text.*;

// Referenced classes of package com.huawei.insa2.comm.sgip:
//            SGIPConnection, SGIPConstant

public class SGIPTransaction extends PLayer
{

    public SGIPTransaction(PLayer connection)
    {
        super(connection);
        sequenceId = super.id;
    }

    public void setSPNumber(int spNumber)
    {
        src_nodeid = spNumber;
    }

    public void setTimestamp(int timestamp)
    {
        this.timestamp = timestamp;
    }

    public synchronized void onReceive(PMessage msg)
    {
        receive = (SGIPMessage)msg;
        src_nodeid = receive.getSrcNodeId();
        timestamp = receive.getTimeStamp();
        sequenceId = receive.getSequenceId();
        if(SGIPConstant.debug)
            Debug.dump(receive.toString());
        notifyAll();
    }

    public void send(PMessage message)
        throws PException
    {
        SGIPMessage mes = (SGIPMessage)message;
        mes.setSrcNodeId(src_nodeid);
        mes.setTimeStamp(timestamp);
        mes.setSequenceId(sequenceId);
        super.parent.send(message);
        if(SGIPConstant.debug)
            Debug.dump(mes.toString());
    }

    public SGIPMessage getResponse()
    {
        return receive;
    }

    public boolean isChildOf(PLayer connection)
    {
        if(super.parent == null)
            return false;
        else
            return connection == super.parent;
    }

    public PLayer getParent()
    {
        return super.parent;
    }

    public synchronized void waitResponse()
    {
        if(receive == null)
            try
            {
                wait(((SGIPConnection)super.parent).getTransactionTimeout());
            }
            catch(InterruptedException interruptedexception) { }
    }

    private SGIPMessage receive;
    private int src_nodeid;
    private int timestamp;
    private int sequenceId;
}
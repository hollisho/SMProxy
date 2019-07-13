// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   SMPPLoginMessage.java

package com.huawei.insa2.comm.smpp.message;

import com.huawei.insa2.comm.smpp.SMPPConstant;

// Referenced classes of package com.huawei.insa2.comm.smpp.message:
//            SMPPMessage

public class SMPPLoginMessage extends SMPPMessage
{

    private StringBuffer strBuf;
    private int loginCommandId;

    public SMPPLoginMessage(int logintype, String systemId, String password, String systemType, byte interfaceVersion, byte addrTon, byte addrNpi, 
            String addressRange)
        throws IllegalArgumentException
    {
        if(logintype != 1 && logintype != 2)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMPPConstant.CONNECT_INPUT_ERROR)))).append(":loginCommandId ").append(SMPPConstant.OTHER_ERROR))));
        loginCommandId = logintype;
        if(systemId.length() > 15)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMPPConstant.CONNECT_INPUT_ERROR)))).append(":systemId ").append(SMPPConstant.STRING_LENGTH_GREAT).append("15"))));
        if(password.length() > 8)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMPPConstant.CONNECT_INPUT_ERROR)))).append(":password ").append(SMPPConstant.STRING_LENGTH_GREAT).append("8"))));
        if(systemType.length() > 12)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMPPConstant.CONNECT_INPUT_ERROR)))).append(":systemType ").append(SMPPConstant.STRING_LENGTH_GREAT).append("12"))));
        if(addressRange.length() > 40)
        {
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMPPConstant.CONNECT_INPUT_ERROR)))).append(":addressRange ").append(SMPPConstant.STRING_LENGTH_GREAT).append("40"))));
        } else
        {
            int len = 23 + systemId.length() + password.length() + systemType.length() + addressRange.length();
            buf = new byte[len];
            setMsgLength(len);
            setCommandId(loginCommandId);
            setStatus(0);
            int pos = 16;
            System.arraycopy(systemId.getBytes(), 0, buf, pos, systemId.length());
            pos = pos + systemId.length() + 1;
            System.arraycopy(password.getBytes(), 0, buf, pos, password.length());
            pos = pos + password.length() + 1;
            System.arraycopy(systemType.getBytes(), 0, buf, pos, systemType.length());
            pos = pos + systemType.length() + 1;
            buf[pos] = interfaceVersion;
            pos++;
            buf[pos] = addrTon;
            pos++;
            buf[pos] = addrNpi;
            pos++;
            System.arraycopy(addressRange.getBytes(), 0, buf, pos, addressRange.length());
            strBuf = new StringBuffer(200);
            strBuf.append(",systemID=".concat(String.valueOf(String.valueOf(systemId))));
            strBuf.append(",password=".concat(String.valueOf(String.valueOf(password))));
            strBuf.append(",systemType=".concat(String.valueOf(String.valueOf(systemType))));
            strBuf.append(",interfaceVersion=".concat(String.valueOf(String.valueOf(interfaceVersion))));
            strBuf.append(",addrTon=".concat(String.valueOf(String.valueOf(addrTon))));
            strBuf.append(",addrNpi=".concat(String.valueOf(String.valueOf(addrNpi))));
            strBuf.append(",addressRange=".concat(String.valueOf(String.valueOf(addressRange))));
            return;
        }
    }

    public String toString()
    {
        StringBuffer outStr = new StringBuffer(300);
        outStr.append("SMPPLoginMessage:");
        outStr.append("PacketLength=".concat(String.valueOf(String.valueOf(buf.length))));
        outStr.append(",CommandID=".concat(String.valueOf(String.valueOf(getCommandId()))));
        outStr.append(",SequenceId=".concat(String.valueOf(String.valueOf(getSequenceId()))));
        if(strBuf != null)
            outStr.append(strBuf.toString());
        return outStr.toString();
    }
}

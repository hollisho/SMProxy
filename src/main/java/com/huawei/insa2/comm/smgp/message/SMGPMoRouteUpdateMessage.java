// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   SMGPMoRouteUpdateMessage.java

package com.huawei.insa2.comm.smgp.message;

import com.huawei.insa2.comm.smgp.SMGPConstant;
import com.huawei.insa2.util.TypeConvert;

// Referenced classes of package com.huawei.insa2.comm.smgp.message:
//            SMGPMessage

public class SMGPMoRouteUpdateMessage extends SMGPMessage
{

    private StringBuffer strBuf;

    public SMGPMoRouteUpdateMessage(int updateType, int routeId, String srcGatewayId, String srcGatewayIP, int srcGatewayPort, String srcTermId)
        throws IllegalArgumentException
    {
        if(updateType < 0 || updateType > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.MOROUTEUPDATE_INPUT_ERROR)))).append(":UpdateType ").append(SMGPConstant.INT_SCOPE_ERROR))));
        if(srcGatewayId == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.MOROUTEUPDATE_INPUT_ERROR)))).append(":SrcGatewayId ").append(SMGPConstant.STRING_NULL))));
        if(srcGatewayId.length() > 6)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.MOROUTEUPDATE_INPUT_ERROR)))).append(":SrcGatewayId ").append(SMGPConstant.STRING_LENGTH_GREAT).append("6"))));
        if(srcGatewayIP == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.MOROUTEUPDATE_INPUT_ERROR)))).append(":SrcGatewayIP ").append(SMGPConstant.STRING_NULL))));
        if(srcGatewayIP.length() > 15)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.MOROUTEUPDATE_INPUT_ERROR)))).append(":SrcGatewayIP ").append(SMGPConstant.STRING_LENGTH_GREAT).append("15"))));
        if(srcTermId == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.MOROUTEUPDATE_INPUT_ERROR)))).append(":SrcTermId ").append(SMGPConstant.STRING_NULL))));
        if(srcTermId.length() > 21)
        {
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.MOROUTEUPDATE_INPUT_ERROR)))).append(":SrcTermId ").append(SMGPConstant.STRING_LENGTH_GREAT).append("21"))));
        } else
        {
            int len = 61;
            buf = new byte[len];
            TypeConvert.int2byte(len, buf, 0);
            TypeConvert.int2byte(8, buf, 4);
            buf[12] = (byte)updateType;
            TypeConvert.int2byte(routeId, buf, 13);
            System.arraycopy(srcGatewayId.getBytes(), 0, buf, 17, srcGatewayId.length());
            System.arraycopy(srcGatewayIP.getBytes(), 0, buf, 23, srcGatewayIP.length());
            TypeConvert.short2byte(srcGatewayPort, buf, 38);
            System.arraycopy(srcTermId.getBytes(), 0, buf, 40, srcTermId.length());
            strBuf = new StringBuffer(300);
            strBuf.append(",updateType=".concat(String.valueOf(String.valueOf(updateType))));
            strBuf.append(",RouteID=".concat(String.valueOf(String.valueOf(routeId))));
            strBuf.append(",SrcGatewayID=".concat(String.valueOf(String.valueOf(srcGatewayId))));
            strBuf.append(",SrcGatewayIP=".concat(String.valueOf(String.valueOf(srcGatewayIP))));
            strBuf.append(",SrcGatewayPort=".concat(String.valueOf(String.valueOf(srcGatewayPort))));
            strBuf.append(",SrcTermId=".concat(String.valueOf(String.valueOf(srcTermId))));
            return;
        }
    }

    public String toString()
    {
        StringBuffer outStr = new StringBuffer(300);
        outStr.append("SMGPMtRouteUpdateMessage:");
        outStr.append("PacketLength=".concat(String.valueOf(String.valueOf(buf.length))));
        outStr.append(",RequestID=9");
        outStr.append(",Sequence_Id=".concat(String.valueOf(String.valueOf(getSequenceId()))));
        if(strBuf != null)
            outStr.append(strBuf.toString());
        return outStr.toString();
    }

    public int getRequestId()
    {
        return 9;
    }
}

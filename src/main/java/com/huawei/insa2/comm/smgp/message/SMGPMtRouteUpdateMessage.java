// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   SMGPMtRouteUpdateMessage.java

package com.huawei.insa2.comm.smgp.message;

import com.huawei.insa2.comm.smgp.SMGPConstant;
import com.huawei.insa2.util.TypeConvert;

// Referenced classes of package com.huawei.insa2.comm.smgp.message:
//            SMGPMessage

public class SMGPMtRouteUpdateMessage extends SMGPMessage
{

    private StringBuffer strBuf;

    public SMGPMtRouteUpdateMessage(int updateType, int routeId, String srcGatewayId, String srcGatewayIP, int srcGatewayPort, String startTermId, String endTermId, 
            String areaCode)
        throws IllegalArgumentException
    {
        if(updateType < 0 || updateType > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.MTROUTEUPDATE_INPUT_ERROR)))).append(":UpdateType ").append(SMGPConstant.INT_SCOPE_ERROR))));
        if(srcGatewayId == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.MTROUTEUPDATE_INPUT_ERROR)))).append(":SrcGatewayId ").append(SMGPConstant.STRING_NULL))));
        if(srcGatewayId.length() > 6)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.MTROUTEUPDATE_INPUT_ERROR)))).append(":SrcGatewayId ").append(SMGPConstant.STRING_LENGTH_GREAT).append("6"))));
        if(srcGatewayIP == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.MTROUTEUPDATE_INPUT_ERROR)))).append(":SrcGatewayIP ").append(SMGPConstant.STRING_NULL))));
        if(srcGatewayIP.length() > 15)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.MTROUTEUPDATE_INPUT_ERROR)))).append(":SrcGatewayIP ").append(SMGPConstant.STRING_LENGTH_GREAT).append("15"))));
        if(startTermId == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.MTROUTEUPDATE_INPUT_ERROR)))).append(":StartTermId ").append(SMGPConstant.STRING_NULL))));
        if(startTermId.length() > 6)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.MTROUTEUPDATE_INPUT_ERROR)))).append(":StartTermId ").append(SMGPConstant.STRING_LENGTH_GREAT).append("6"))));
        if(endTermId == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.MTROUTEUPDATE_INPUT_ERROR)))).append(":EndTermId ").append(SMGPConstant.STRING_NULL))));
        if(endTermId.length() > 6)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.MTROUTEUPDATE_INPUT_ERROR)))).append(":EndTermId ").append(SMGPConstant.STRING_LENGTH_GREAT).append("6"))));
        if(areaCode == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.MTROUTEUPDATE_INPUT_ERROR)))).append(":AreaCode ").append(SMGPConstant.STRING_NULL))));
        if(areaCode.length() > 4)
        {
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.MTROUTEUPDATE_INPUT_ERROR)))).append(":AreaCode ").append(SMGPConstant.STRING_LENGTH_GREAT).append("4"))));
        } else
        {
            int len = 56;
            buf = new byte[len];
            TypeConvert.int2byte(len, buf, 0);
            TypeConvert.int2byte(8, buf, 4);
            buf[12] = (byte)updateType;
            TypeConvert.int2byte(routeId, buf, 13);
            System.arraycopy(srcGatewayId.getBytes(), 0, buf, 17, srcGatewayId.length());
            System.arraycopy(srcGatewayIP.getBytes(), 0, buf, 23, srcGatewayIP.length());
            TypeConvert.short2byte(srcGatewayPort, buf, 38);
            System.arraycopy(startTermId.getBytes(), 0, buf, 40, startTermId.length());
            System.arraycopy(endTermId.getBytes(), 0, buf, 46, endTermId.length());
            System.arraycopy(areaCode.getBytes(), 0, buf, 52, areaCode.length());
            strBuf = new StringBuffer(300);
            strBuf.append(",updateType=".concat(String.valueOf(String.valueOf(updateType))));
            strBuf.append(",RouteID=".concat(String.valueOf(String.valueOf(routeId))));
            strBuf.append(",SrcGatewayID=".concat(String.valueOf(String.valueOf(srcGatewayId))));
            strBuf.append(",SrcGatewayIP=".concat(String.valueOf(String.valueOf(srcGatewayIP))));
            strBuf.append(",SrcGatewayPort=".concat(String.valueOf(String.valueOf(srcGatewayPort))));
            strBuf.append(",StartTermId=".concat(String.valueOf(String.valueOf(startTermId))));
            strBuf.append(",EndTermId=".concat(String.valueOf(String.valueOf(endTermId))));
            strBuf.append(",AreaCode=".concat(String.valueOf(String.valueOf(areaCode))));
            return;
        }
    }

    public String toString()
    {
        StringBuffer outStr = new StringBuffer(300);
        outStr.append("SMGPMtRouteUpdateMessage:");
        outStr.append("PacketLength=".concat(String.valueOf(String.valueOf(buf.length))));
        outStr.append(",RequestID=8");
        outStr.append("Sequence_Id=".concat(String.valueOf(String.valueOf(getSequenceId()))));
        if(strBuf != null)
            outStr.append(strBuf.toString());
        return outStr.toString();
    }

    public int getRequestId()
    {
        return 8;
    }
}

package com.gongyunhaoyyy.wustweschool;


import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lqy on 2016/7/15.
 * 此类通过android提供的Ksoap2包调用webservice接口
 * 其中接口不可泄露！！！
 */
public class Ksoap2 {

    private static final String key="webservice_whkdapp";
    // 命名空间
    private static final String nameSpace = "http://webservices.qzdatasoft.com";
    // EndPoint
    private static final String endPoint = "http://jwxt.wust.edu.cn/whkjdx/services/whkdapp";



    public static String getScoreInfo(String xh) {

        // 调用的方法名称
        String methodName = "getxscj";

        // SOAP Action
        final String soapAction = "http://webservices.qzdatasoft.com/getxscj";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        //相关参数
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time=format.format(date);
        String chkvalue=key+time;
        chkvalue= Md5Util.MD5(chkvalue);
        chkvalue=chkvalue.substring(2).toLowerCase();

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("in0", xh);
        rpc.addProperty("in1", time);
        rpc.addProperty("in2", chkvalue);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty("out").toString();

        return result;
    }

    public static String getCourseInfo(String xh, String xq) {

        // 调用的方法名称
        String methodName = "getyxkclb";

        // SOAP Action
        final String soapAction = "http://webservices.qzdatasoft.com/getyxkclb";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        //相关参数
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time=format.format(date);
        String chkvalue=key+time;
        chkvalue= Md5Util.MD5(chkvalue);
        chkvalue=chkvalue.substring(2).toLowerCase();

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("in0", xh);
        rpc.addProperty("in1",xq);
        rpc.addProperty("in2", time);
        rpc.addProperty("in3", chkvalue);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty("out").toString();

        return result;
    }

    public static String getLoginInfo(String xh, String pwd) {

        String result;

        // 调用的方法名称
        // 坑惨了，换成newlogin就ok了
//        String methodName = "xslogin";
        String methodName = "newlogin";

        // SOAP Action
        final String soapAction = "http://webservices.qzdatasoft.com/newlogin";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        //相关参数
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time=format.format(date);
        String chkvalue=key+time;
        chkvalue= Md5Util.MD5(chkvalue);
        chkvalue=chkvalue.substring(2).toLowerCase();

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("in0", xh);
        rpc.addProperty("in1", pwd);
        rpc.addProperty("in2", time);
        rpc.addProperty("in3", chkvalue);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        if(object == null){
            Log.i("KSOAP2", "null object");
            result = "100";
        }else{
            result = object.getProperty("out").toString();
        }
        return result;
    }
    //选课阶段
    public static String getXkjd(String paramString) throws IOException, XmlPullParserException {
        //        String xh,String time,String chkvalue)
        SoapObject localSoapObject = new SoapObject("http://webservices.qzdatasoft.com", "getxkjdlb");
        localSoapObject.addProperty("in0", paramString);
        return getResult("getxkjdlb", localSoapObject, 1);
    }
    /**
     * 获取可选课程
     * @param paramString1 学生学号
     * @param paramString2 选课控制子表ID
     * @param paramString3 学年学期ID
    Json字符串
     * @return
     */
    public static String getKxkc(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
            throws IOException, XmlPullParserException
    {
//        String xh,String jx0502id,String xnxq,String kcmc,String skjs,String sksj,String time,String chkvalue
        SoapObject localSoapObject = new SoapObject("http://webservices.qzdatasoft.com", "getkykc");
        localSoapObject.addProperty("in0", paramString1);
        localSoapObject.addProperty("in1", paramString2);
        localSoapObject.addProperty("in2", paramString3);
        localSoapObject.addProperty("in3", paramString4);
        localSoapObject.addProperty("in4", paramString5);
        localSoapObject.addProperty("in5", paramString6);
        return getResult("getkykc", localSoapObject, 6);
    }
    public static String getTerm()
            throws IOException, XmlPullParserException
    {
        return getResult("getpjxnxq", new SoapObject("http://webservices.qzdatasoft.com", "getpjxnxq"), 0);
    }
    /**
     * 获取评价批次名称
     * @return
    Pj05id 评价批次ID
    Pjpcmc 评价批次名称
    Json字符串
     * @param xnxq01id
     */
    public static String getPjpcmc(String xnxq01id)
            throws IOException, XmlPullParserException
    {
        SoapObject localSoapObject = new SoapObject("http://webservices.qzdatasoft.com", "getpjpcmc");
        localSoapObject.addProperty("in0", xnxq01id);
        return getResult("getpjpcmc", localSoapObject, 1);
    }

    /**
     *
     * @param paramString
     * @param paramSoapObject
     * @param paramInt
     * @return
     */
    static String getResult(String paramString, SoapObject paramSoapObject, int paramInt) throws IOException, XmlPullParserException {
        Date localDate = new Date();
        String str1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(localDate);
        String str2 = Md5Util.MD5("webservice_whkdapp" + str1).substring(2).toLowerCase();
        int i = paramInt + 1;
        paramSoapObject.addProperty("in"+paramInt, str1);
        paramSoapObject.addProperty("in"+i, str2);
        SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(110);
        localSoapSerializationEnvelope.bodyOut = paramSoapObject;
        localSoapSerializationEnvelope.dotNet = true;
        localSoapSerializationEnvelope.setOutputSoapObject(paramSoapObject);
        new HttpTransportSE("http://jwxt.wust.edu.cn/whkjdx/services/whkdapp").call("http://webservices.qzdatasoft.com/" + paramString, localSoapSerializationEnvelope);
        return ((SoapObject)localSoapSerializationEnvelope.bodyIn).getProperty("out").toString();
    }


}

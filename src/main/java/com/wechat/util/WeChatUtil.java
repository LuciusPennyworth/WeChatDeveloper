package com.wechat.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * User: Matt
 * Date: 2017/1/14
 * Time: 22:19
 * Description: 获取access_token
 */
public class WeChatUtil {

    private static final String APPID="wx69bbf5d5b2bd0d1f";
    private static final String APPSECRET="e6dd8da8af62ccddfb04b6c265f3e433";

    /**
     * doGet 方法
     * @param url
     * @return
     * @throws Exception
     */
    public static JSONObject doGetStr(String url)throws Exception{
        DefaultHttpClient httpClient=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(url);
        HttpResponse httpResponse=httpClient.execute(httpGet);
        HttpEntity entity=httpResponse.getEntity();
        JSONObject jsonObject=new JSONObject();
        if (entity!=null){
            String response= EntityUtils.toString(entity,"UTF-8");
            jsonObject=JSONObject.parseObject(response);
            System.out.println(response);
        }
        return jsonObject;
    }

    /**
     * post 请求
     * @param url
     * @param outStr
     * @return
     * @throws Exception
     */
    public static JSONObject doPostStr(String url,String outStr)throws Exception{
        DefaultHttpClient httpClient=new DefaultHttpClient();
        HttpPost httpPost=new HttpPost(url);
        httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
        HttpResponse response=httpClient.execute(httpPost);
        String result=EntityUtils.toString(response.getEntity(),"UTF-8");
        return JSONObject.parseObject(result);
    }


}

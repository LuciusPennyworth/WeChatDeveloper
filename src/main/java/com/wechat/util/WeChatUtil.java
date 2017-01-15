package com.wechat.util;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pojo.AccessToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * User: Matt
 * Date: 2017/1/14
 * Time: 22:19
 * Description: 获取access_token
 */
public class WeChatUtil {

    private static final String APPID = "wx69bbf5d5b2bd0d1f";
    private static final String APPSECRET = "e6dd8da8af62ccddfb04b6c265f3e433";

    private static final String getToken = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

    /**
     * doGet 请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static JSONObject doGetStr(String url) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity entity = httpResponse.getEntity();
        JSONObject jsonObject = new JSONObject();
        if (entity != null) {
            String response = EntityUtils.toString(entity, "UTF-8");
            jsonObject = JSONObject.parseObject(response);
        }
        return jsonObject;
    }

    /**
     * post 请求
     *
     * @param url
     * @param outStr
     * @return
     * @throws Exception
     */
    public static JSONObject doPostStr(String url, String outStr) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);
        String result = EntityUtils.toString(response.getEntity(), "UTF-8");
        return JSONObject.parseObject(result);
    }

    /**
     * 获取accesstoken
     */
    public static AccessToken getAccessToken() throws Exception {
        AccessToken accessToken = new AccessToken();
        String url = getToken.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
        JSONObject jsonObject = doGetStr(url);
        if (jsonObject != null) {
            accessToken.setAccess_token(jsonObject.getString("access_token"));
            accessToken.setExpires_in(jsonObject.getInteger("expires_in"));
        }
        return accessToken;
    }

    /**
     * 上传临时素材
     */
    public static String upload(String filePath, String accessToken, String type) throws Exception {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在");
        }

        String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
        URL url1 = new URL(url);
        //建立连接
        HttpURLConnection connection = (HttpURLConnection) url1.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);

        //设置请求头信息
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Charset", "UTF-8");

        //设置边界
        String BOUNDARY = "--------" + System.currentTimeMillis();
        connection.setRequestProperty("Content-Type", "multipart/form-data;boundary" + BOUNDARY);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("--");
        stringBuilder.append(BOUNDARY);
        stringBuilder.append("\r\n");
        stringBuilder.append("Content-Disposition:form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        stringBuilder.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = stringBuilder.toString().getBytes("UTF-8");

        //获取输出流
        OutputStream out = new DataOutputStream(connection.getOutputStream());
        //输出表头
        out.write(head);

        //文件正文
        //把文件以流文件的方式推入url中
        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = dis.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        dis.close();

        //结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");
        out.write(foot);

        out.flush();
        out.close();

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;

        try {
            //定义BufferedReader输入流来读取URL响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null)
                reader.close();
        }
        JSONObject json=JSONObject.parseObject(result);
        String mediaId=json.getString("media_id");
        return mediaId;
    }

}

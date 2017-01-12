package com.wechat.util;


import java.awt.*;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * User: Matt
 * Date: 2017/1/12
 * Time: 9:39
 * Description: description
 */
public class CheckUtil {

    private static String token = "hellowechat";

    public static boolean CheckSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[]{token, timestamp, nonce};
        Arrays.sort(arr);

        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            stringBuffer.append(arr[i]);
        }

        String temp=CheckUtil.getSha1(stringBuffer.toString());
        return temp.equals(signature);
    }

    public static String getSha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int length = md.length;
            char buf[] = new char[length * 2];
            for (int i = 0, k = 0; i < length; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String (buf);
        } catch (Exception e) {
            return null;
        }
    }

}

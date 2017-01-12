package com.wechat.util;

import com.thoughtworks.xstream.XStream;
import com.wechat.pojo.TextMessage;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Matt
 * Date: 2017/1/12
 * Time: 13:30
 * Description: 将xml与Map集合类型相互转换
 */
public class MessageUtil {

    public static final String MESSAGE_TEXT="text";
    public static final String MESSAGE_IMAGE="image";
    public static final String MESSAGE_VOICE="voice";
    public static final String MESSAGE_VIDEO="video";
    public static final String MESSAGE_LINKT="link";
    public static final String MESSAGE_LOCATION="location";
    public static final String MESSAGE_EVENTT="event";
    public static final String MESSAGE_SUBSCRIBE="subscribe";
    public static final String MESSAGE_UNSUBSCRIBET="unsubscribet";
    public static final String MESSAGE_CLICK="CLICK";
    public static final String MESSAGE_VIEW="VIEW";

    /*
    * 将xml文件通过SAXReader转为Map集合
    * */
    public static Map<String, String> xmlToMap(HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();

        InputStream inputStream = request.getInputStream();
        Document document = reader.read(inputStream);

        Element root = document.getRootElement();
        List<Element> list = root.elements();

        for (Element e : list) {
            map.put(e.getName(), e.getText());
        }
        inputStream.close();
        return map;
    }

    /*
    * 将文本消息转换为xml文件
    */
    public static String textMsgToXML(TextMessage textMessage) {
        XStream xStream = new XStream();
        xStream.alias("xml",textMessage.getClass());
        return xStream.toXML(textMessage);
    }

    /*
    * 拼接文本消息
    * */
    public static String initText(String ToUserName,String FromUserName,String Content){
        TextMessage textMessage=new TextMessage();
        textMessage.setFromUserName(ToUserName);
        textMessage.setToUserName(FromUserName);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MessageUtil.MESSAGE_TEXT);
        textMessage.setContent(Content);
        return  MessageUtil.textMsgToXML(textMessage);
    }

    /*
    * 主菜单
    * */
    public static String menuText(){
        StringBuffer sBuffer=new StringBuffer();
        sBuffer.append("欢迎你的关注，请回复数字进行操作\n");
        sBuffer.append("1.公众号介绍\n");
        sBuffer.append("2.个人介绍\n");
        sBuffer.append("回复?调出提示菜单");
        return sBuffer.toString();
    }

    public static String firstMenu(){
        StringBuffer sBuffer=new StringBuffer();
        sBuffer.append("这个公众号可能是假的");
        return sBuffer.toString();
    }

    public static String secondMenu(){
        StringBuffer sBuffer=new StringBuffer();
        sBuffer.append("作者本人也可能是假的");
        return sBuffer.toString();
    }
}

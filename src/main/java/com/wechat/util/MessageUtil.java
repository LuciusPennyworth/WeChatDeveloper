package com.wechat.util;

import com.thoughtworks.xstream.XStream;
import com.wechat.pojo.TextMessage;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
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

}

package com.wechat.util;

import com.thoughtworks.xstream.XStream;
import com.wechat.Message.ImageMessage;
import com.wechat.Message.MusicMessage;
import com.wechat.pojo.Image;
import com.wechat.pojo.Music;
import com.wechat.pojo.News;
import com.wechat.Message.NewsMessage;
import com.wechat.Message.TextMessage;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.*;

/**
 * User: Matt
 * Date: 2017/1/12
 * Time: 13:30
 * Description: 将xml与Map集合类型相互转换
 */
public class MessageUtil {

    public static final String MESSAGE_TEXT="text";
    public static final String MESSAGE_NEWS="news";
    public static final String MESSAGE_IMAGE= "image";
    public static final String MESSAGE_VOICE="voice";
    public static final String MESSAGE_MUSIC="music";
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

    /**
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

    /**
     * 公众号介绍
     */
    public static String firstMenu(){
        StringBuffer sBuffer=new StringBuffer();
        sBuffer.append("这个公众号可能是假的");
        return sBuffer.toString();
    }

    /**
     * 个人介绍
     */
    public static String secondMenu(){
        StringBuffer sBuffer=new StringBuffer();
        sBuffer.append("作者本人也可能是假的");
        return sBuffer.toString();
    }

    /**
    *将图文消息转换为xml文件
    */
    public static String newsMsgToXML(NewsMessage newsMessage) {
        XStream xStream = new XStream();
        xStream.alias("xml",newsMessage.getClass());
        xStream.alias("item",new News().getClass());
        return xStream.toXML(newsMessage);
    }

    /**
     *创建一个图文消息列表
     */
    public static String initNewsMessage(String toUserName,String fromUserName){
        NewsMessage newsMessage=new NewsMessage();
        List<News> newsList=new LinkedList<News>();

        News news=new News();
        news.setTitle("记一场假的面试");
        news.setPicUrl("http://mattchenvip.viphk.ngrok.org/html/image/mianshi.jpg");
        if (fromUserName.equals("oXARvxMW_Tk2WF7ZcdX4h-x2q8Bc")){
            news.setDescription("x艺,雷猴啊");
        }else {
            news.setDescription("you lead me down ,to the ocean");
        }
        news.setUrl("wwww.baidu.com");
        newsList.add(news);

        News news1=new News();
        news1.setTitle("只有x艺能打开,不信试试");
        news1.setPicUrl("http://mattchenvip.viphk.ngrok.org/html/image/github.jpg");

        if ( fromUserName.equals("oXARvxMW_Tk2WF7ZcdX4h-x2q8Bc") ){
            news1.setUrl("http://mattchenvip.viphk.ngrok.org/html/siyi.html");
        }else {
            news1.setUrl("http://mattchenvip.viphk.ngrok.org/html/error.html");
        }

        newsList.add(news1);

        newsMessage.setFromUserName(toUserName);
        newsMessage.setToUserName(fromUserName);
        newsMessage.setArticles(newsList);
        newsMessage.setArticleCount(newsList.size());
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.MESSAGE_NEWS);

        return MessageUtil.newsMsgToXML(newsMessage);
    }

    /**
     * 将图片消息组装为xml
     * @param imageMessage
     * @return
     */
    public static String ImageToXML(ImageMessage imageMessage){
        XStream xStream=new XStream();
        xStream.alias("xml",imageMessage.getClass());
        return xStream.toXML(imageMessage);
    }

    /**
     * 创建一个图片信息
     */
    public static String initImageMessage(String toUserName,String fromUserName){
        ImageMessage imageMessage=new ImageMessage();
        Image image=new Image();
        image.setMediaId("T7cf3BDaCIy0b1INIRD74NMikor1abr6JohdUk2_n6n5Gze3ZDRjXkbcqlFOLber");

        imageMessage.setImage(image);
        imageMessage.setMsgType(MessageUtil.MESSAGE_IMAGE);
        imageMessage.setFromUserName(toUserName);
        imageMessage.setToUserName(fromUserName);
        imageMessage.setCreateTime(new Date().getTime());

        String message=ImageToXML(imageMessage);
        return message;
    }

    public static String musicToXML(MusicMessage musicMessage){
        XStream xStream=new XStream();
        xStream.alias("xml",musicMessage.getClass());
        return xStream.toXML(musicMessage);
    }

    public static String initMusicMessage(String toUserName,String fromUserName){
        MusicMessage musicMessage=new MusicMessage();
        Music music=new Music();

        music.setDescription("巴赫");
        music.setTitle("巴赫E大调");
        music.setHQMusicUrl("http://mattchenvip.viphk.ngrok.org/music/E.mp3");
        music.setMusicUrl("http://mattchenvip.viphk.ngrok.org/music/E.mp3");
        music.setThumbMediaId("homhALc07Injf4G4GwAVeegbV7pF9GQWoZUgHJc2hiNRV4p7yYQuUExNIjaMvJkm");

        musicMessage.setMusic(music);
        musicMessage.setMsgType(MessageUtil.MESSAGE_MUSIC);
        musicMessage.setFromUserName(toUserName);
        musicMessage.setToUserName(fromUserName);
        musicMessage.setCreateTime(new Date().getTime());

        return MessageUtil.musicToXML(musicMessage);
    }


}

package com.wechat.servlet;

import com.wechat.util.CheckUtil;
import com.wechat.util.MessageUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * User: Matt
 * Date: 2017/1/12
 * Time: 9:34
 * Description: description
 */
public class wechatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String signature =req.getParameter("signature");
        String timestamp=req.getParameter("timestamp");
        String nonce=req.getParameter("nonce");
        String echostr=req.getParameter("echostr");

        PrintWriter out=resp.getWriter();
        if (CheckUtil.CheckSignature(signature,timestamp,nonce)){
            out.print(echostr);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        try {
            Map<String,String> map= MessageUtil.xmlToMap(req);
            String ToUserName=map.get("ToUserName");
            String FromUserName=map.get("FromUserName");
            String MsgType=map.get("MsgType");
            String Content=map.get("Content");

            String message=null;
            PrintWriter out=resp.getWriter();
            /*
                关注返回欢迎信息

                回复1或2返回一级与二级提示菜单
                回复3返回一条图文消息列表，内可有多条消息：
                    1.第一条消息展示标题，图片和描述
                    2.其余消息展示标题和图片
                    3.最多展示10条消息
                回复4返回一条图片消息
                回复5返回一条音乐消息
                回复中文或英文问号返回提示目录
                回复其他返回一条调侃信息
             */
            if (MsgType.equals(MessageUtil.MESSAGE_TEXT)){
                if (Content.equals("1")){
                    message=MessageUtil.initText(ToUserName,FromUserName,MessageUtil.firstMenu());
                }else if (Content.equals("2")){
                    message=MessageUtil.initText(ToUserName,FromUserName,MessageUtil.secondMenu());
                }else if (Content.equals("3")){
                    message=MessageUtil.initNewsMessage(ToUserName,FromUserName);
                    System.out.println(message);
                }else if (Content.equals("4")){
                    message=MessageUtil.initImageMessage(ToUserName,FromUserName);
                    System.out.println(message);
                }else if (Content.equals("5")){
                    message=MessageUtil.initMusicMessage(ToUserName,FromUserName);
                    System.out.println(message);
                }else if (Content.equals("?") || Content.equals("？") ){
                    message=MessageUtil.initText(ToUserName,FromUserName,MessageUtil.menuText());
                }else{
                    message=MessageUtil.initText(ToUserName,FromUserName,"伙计,你玩这么久,难道你考试都过了么");
                }
            }else if( MsgType.equals(MessageUtil.MESSAGE_EVENTT) ){
                String eventType=map.get("Event");
                if ( eventType.equals((MessageUtil.MESSAGE_SUBSCRIBE)) ){
                    message=MessageUtil.initText(ToUserName,FromUserName,"欢迎你的关注，请回复?获取提示");
                }
            }
            out.print(message);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

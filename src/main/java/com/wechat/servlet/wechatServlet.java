package com.wechat.servlet;

import com.wechat.pojo.TextMessage;
import com.wechat.util.CheckUtil;
import com.wechat.util.MessageUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
            if (MsgType.equals(MessageUtil.MESSAGE_TEXT)){
                if (Content.equals("1")){
                    message=MessageUtil.initText(ToUserName,FromUserName,MessageUtil.firstMenu());
                }else if (Content.equals("2")){
                    message=MessageUtil.initText(ToUserName,FromUserName,MessageUtil.secondMenu());
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

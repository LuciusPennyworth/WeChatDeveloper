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
            if (MsgType.equals("text")){
                TextMessage textMessage=new TextMessage();
                textMessage.setFromUserName(ToUserName);
                textMessage.setToUserName(FromUserName);
                textMessage.setCreateTime(new Date().getTime());
                textMessage.setMsgType("text");
                textMessage.setContent("您发送的是:"+Content);
                message=MessageUtil.textMsgToXML(textMessage);
                System.out.println(message);
            }
            out.print(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

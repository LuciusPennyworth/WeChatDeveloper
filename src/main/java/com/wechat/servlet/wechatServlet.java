package com.wechat.servlet;

import com.wechat.util.CheckUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PipedWriter;
import java.io.PrintWriter;

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
}

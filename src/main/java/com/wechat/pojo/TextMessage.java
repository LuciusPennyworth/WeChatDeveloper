package com.wechat.pojo;

/**
 * User: Matt
 * Date: 2017/1/12
 * Time: 14:39
 * Description: 保存文本对象
 */
public class TextMessage extends BaseMessage{

    private String Content;
    private String MsgId;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }
}

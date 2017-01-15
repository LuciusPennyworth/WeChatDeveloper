package com.wechat.Message;

import com.wechat.pojo.Image;

/**
 * User: Matt
 * Date: 2017/1/15
 * Time: 20:03
 * Description: ImageMessage
 */
public class ImageMessage extends BaseMessage{

    private Image Image;

    public com.wechat.pojo.Image getImage() {
        return Image;
    }

    public void setImage(com.wechat.pojo.Image image) {
        Image = image;
    }
}

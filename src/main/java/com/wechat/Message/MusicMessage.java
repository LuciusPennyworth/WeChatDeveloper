package com.wechat.Message;

import com.wechat.pojo.Music;

/**
 * User: Matt
 * Date: 2017/1/16
 * Time: 23:02
 * Description: description
 */
public class MusicMessage extends BaseMessage {

    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        this.Music = music;
    }
}

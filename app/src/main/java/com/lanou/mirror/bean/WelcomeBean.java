package com.lanou.mirror.bean;

/**
 * Created by dllo on 16/4/8.
 */
public class WelcomeBean {

    /**
     * text : &quot;雪屋&quot;07号
     * img : http://pic1.zhimg.com/e1cc747cbf2076a378d2fe0f8c3b2e20.jpg
     */

    private String text;
    private String img;

    public void setText(String text) {
        this.text = text;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public String getImg() {
        return img;
    }
}

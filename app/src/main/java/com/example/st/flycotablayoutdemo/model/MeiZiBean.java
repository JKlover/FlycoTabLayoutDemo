package com.example.st.flycotablayoutdemo.model;

/**
 * Created by st on 18-2-20.
 */

public class MeiZiBean {
    String meiziTitle;
    String meiziHref;
    String meiziImg;

    public String getMeiziTitle() {
        return meiziTitle;
    }

    public void setMeiziTitle(String meiziTitle) {
        this.meiziTitle = meiziTitle;
    }

    public String getMeiziHref() {
        return meiziHref;
    }

    public void setMeiziHref(String meiziHref) {
        this.meiziHref = meiziHref;
    }

    public String getMeiziImg() {
        return meiziImg;
    }

    public void setMeiziImg(String meiziImg) {
        this.meiziImg = meiziImg;
    }

    @Override
    public String toString() {
        return "MeiZiBean{" +
                "meiziTitle='" + meiziTitle + '\'' +
                ", meiziHref='" + meiziHref + '\'' +
                ", meiziImg='" + meiziImg + '\'' +
                '}';
    }
}

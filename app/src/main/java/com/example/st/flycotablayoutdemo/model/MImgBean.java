package com.example.st.flycotablayoutdemo.model;

/**
 * Created by st on 18-2-11.
 */

public class MImgBean {
    String imgurl;
    String imghref;
    String imgsize;
    String imgtime;
    String imgtitle;

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getImghref() {
        return imghref;
    }

    public void setImghref(String imghref) {
        this.imghref = imghref;
    }

    public String getImgsize() {
        return imgsize;
    }

    public void setImgsize(String imgsize) {
        this.imgsize = imgsize;
    }

    public String getImgtime() {
        return imgtime;
    }

    public void setImgtime(String imgtime) {
        this.imgtime = imgtime;
    }

    public String getImgtitle() {
        return imgtitle;
    }

    public void setImgtitle(String imgtitle) {
        this.imgtitle = imgtitle;
    }

    @Override
    public String toString() {
        return "MImgBean{" +
                "imgurl='" + imgurl + '\'' +
                ", imghref='" + imghref + '\'' +
                ", imgsize='" + imgsize + '\'' +
                ", imgtime='" + imgtime + '\'' +
                ", imgtitle='" + imgtitle + '\'' +
                '}';
    }
}

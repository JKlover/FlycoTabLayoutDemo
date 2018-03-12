package com.example.st.flycotablayoutdemo.utils;

import android.util.Log;

import com.example.st.flycotablayoutdemo.Api;
import com.example.st.flycotablayoutdemo.model.MImgBean;
import com.example.st.flycotablayoutdemo.model.MImgUrlBean;
import com.example.st.flycotablayoutdemo.model.MeiZiBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import static com.example.st.flycotablayoutdemo.Api.A_URL;

/**
 * Created by st on 18-2-17.
 */

public class MJsoupSpider {
//    public static String baseurl="http://www.tesetu.com";
    public  static String getPager;
    private static String IMAGE_SUFFIX="01.jpg";
//    public static ArrayList<MImgBean> parseJson(String html) {
//        ArrayList<MImgBean>mImgBeans=new ArrayList<>();
//        Document doc= Jsoup.parse(html);
//        Elements lis=doc.select("div.tag_ssxz ul li");
//        for(Element e:lis){
//            MImgBean mImgBean=new MImgBean();
//            String mHref=e.getElementsByTag("a").attr("href");
//            String mhref=baseurl+mHref;
//            mImgBean.setImghref(mhref);
//            Log.e("链接",mhref);
//            String mImgurl=e.select("img").attr("src");
//            mImgBean.setImgurl(mImgurl);
//            Log.e("图片地址",mImgurl);
////            String mSize=e.select("div").text();
//            String mSize=e.child(0).child(1).child(0).text();
//            mImgBean.setImgsize(mSize);
//            Log.e(" 大小",mSize);
//            String mTime=e.select("span").text();
//            mImgBean.setImgtime(mTime);
//            Log.e("时间",mTime);
//            String mTitle=e.select("h4").text();
//            mImgBean.setImgtitle(mTitle);
//            Log.e("标题",mTitle);
//            mImgBeans.add(mImgBean);
//        }
//        Elements pagers=doc.select("div.tag_page a");
//        for (Element element:pagers){
//            if(element.text().equals("下一页")){
//                String mPager=element.attr("href");
//                String baseUrl=element.baseUri();
//                getPager=mPager;
////                Log.e("下一页链接",getPager);
//            }
//
//        }
//        return  mImgBeans;
//    }


    public static ArrayList<MImgUrlBean> parseMziTudetails(String html){
        ArrayList<MImgUrlBean>mImgUrlBeans=new ArrayList<>();
        Document doc= Jsoup.parse(html);
        String testData=doc.select("div.main-image").select("a").select("img").attr("src").replace(IMAGE_SUFFIX,"");
        Log.e("基本地址",testData);
        //http://pic.tesetu.com/2017/0414/15/
        //获取这个类型的图片总数，循环拼Url，不去循环请求网址了，这样会造成大量的数据请求，图片的URL由自己拼接
        Elements select = doc.select("div.pagenavi").select("a");
        Integer integer = Integer.valueOf(select.eq(select.size()-2 ).text());
        Log.e("页数", String.valueOf(integer));
        for (Integer i = 1; i <= integer; i++) {
            MImgUrlBean  mImgUrlBean=new MImgUrlBean();
            if (i > 9) {
                String mImgurl = testData + i + ".jpg";
                mImgUrlBean.setmImgdetail(mImgurl);
                Log.e("地址",mImgurl);
            } else {
                String mImgurl  =testData + 0 + i + ".jpg";
                mImgUrlBean.setmImgdetail(mImgurl);
                Log.e("地址",mImgurl);
            }
            mImgUrlBeans.add(mImgUrlBean);
        }

        return mImgUrlBeans;
    }
   public static ArrayList<MeiZiBean> parseDataFromHtml(String html) {
        ArrayList<MeiZiBean>meiZiBeans=new ArrayList<>();
        Document doc= Jsoup.parse(html);
        Elements getData=doc.select("figure");
        for (Element e:getData){
            MeiZiBean meiZiBean=new MeiZiBean();
//            String title=e.select("a").val();
//            Log.e("标题是",title);
//            meiZiBean.setMeiziTitle(title);
            String href=e.select("a").attr("href");
            Log.e("链接",href);
            meiZiBean.setMeiziHref(href);
            String imgurl=e.select("img").attr("data-original");

            Log.e("图片地址",imgurl);
            meiZiBean.setMeiziImg(imgurl);
            meiZiBeans.add(meiZiBean);
        }
//<a class="button radius" href="http://m.mzitu.com/xinggan/page/2/">下一页 »</a>
       //#pagebtn > a:nth-child(3)//*[@id="pagebtn"]/a[2]
       //<a class="button radius" href="http://m.mzitu.com/xinggan/page/4/">下一页 »</a>
//        String pagers=doc.select("a.button.radius").attr("href");
//        getPager=pagers;
//       Log.e("下一页", getPager);
        Log.e("总数", String.valueOf(meiZiBeans.size()));
        return  meiZiBeans;
    }


}

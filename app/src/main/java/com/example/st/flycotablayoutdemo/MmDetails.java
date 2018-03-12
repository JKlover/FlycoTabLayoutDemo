package com.example.st.flycotablayoutdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;

import com.example.st.flycotablayoutdemo.adapter.ImgPagerAdapter;
import com.example.st.flycotablayoutdemo.model.MImgUrlBean;
import com.example.st.flycotablayoutdemo.utils.MJsoupSpider;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.st.flycotablayoutdemo.Api.BASE_URL;
import static com.example.st.flycotablayoutdemo.utils.MJsoupSpider.parseMziTudetails;

/**
 * Created by st on 18-2-19.
 */

public class MmDetails extends Activity {
    private ArrayList<MImgUrlBean> mImgUrlBeans;
    ViewPager mViewPager;
    String Uri;
    //http://m.mzitu.com/121418 http://www.mzitu.com/121418
    private OkHttpClient mHttpClient;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("原来地址为",A);
            Log.e("转换后的地址",Uri);
            mViewPager.setAdapter(new ImgPagerAdapter(mImgUrlBeans));
            mViewPager.setCurrentItem(0);
        }
    };
    private String A;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmdetails);
        mViewPager = (HackyViewPager) findViewById(R.id.my_img_viewpager);
        Intent intent = getIntent();
        String mUrl = intent.getStringExtra("DETAILS_URL");//DETAILS_URL
        A=mUrl;
        Uri=BASE_URL+mUrl.replace("http://m.mzitu.com/","");
        getDetailsData(Uri);

    }

    private void getDetailsData(String url) {

        mHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                mImgUrlBeans =  parseMziTudetails(json);
                handler.sendEmptyMessage(10);

            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return super.onKeyDown(keyCode, event);
    }
}

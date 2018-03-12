package com.example.st.flycotablayoutdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.st.flycotablayoutdemo.adapter.MZiAdapter;
import com.example.st.flycotablayoutdemo.adapter.MlistAdapter;
import com.example.st.flycotablayoutdemo.model.MImgUrlBean;

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
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　       	██ ━██  ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 * <p>
 * Created by st on 2018/3/12.
 */

public class MmDetailsList  extends AppCompatActivity{
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MlistAdapter mAdapter;
    private ArrayList<MImgUrlBean> mImgUrlBeans;
    private String Uri;
    private String A;
    private OkHttpClient mHttpClient;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mAdapter.setNewData(mImgUrlBeans);
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mm_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView=findViewById(R.id.rl_mm_list);
        mAdapter=new MlistAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
//        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        initData();

    }

    private void initData() {
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

}

package com.example.st.flycotablayoutdemo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.st.flycotablayoutdemo.MmDetails;
import com.example.st.flycotablayoutdemo.MmDetailsList;
import com.example.st.flycotablayoutdemo.R;
import com.example.st.flycotablayoutdemo.adapter.MZiAdapter;
import com.example.st.flycotablayoutdemo.base.BaseFragment;
import com.example.st.flycotablayoutdemo.model.MeiZiBean;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import static com.example.st.flycotablayoutdemo.Api.A_URL;
import static com.example.st.flycotablayoutdemo.Api.BASE_PAGE;
import static com.example.st.flycotablayoutdemo.Api.B_URL;
import static com.example.st.flycotablayoutdemo.Api.C_URL;
import static com.example.st.flycotablayoutdemo.Api.D_URL;
import static com.example.st.flycotablayoutdemo.utils.MJsoupSpider.parseDataFromHtml;

/**
 * Created by st on 18-1-30.
 */

public class FragmentA extends BaseFragment {
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private String title;
    private MZiAdapter mAdapter;
    private  ArrayList<MeiZiBean>mBeans;
    private OkHttpClient mHttpClient;
    int  Page=1;

//    private Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            mAdapter.setNewData(mBeans);
//        }
//    };
    public static FragmentA getInstance(String title) {
        FragmentA mf = new FragmentA();
        mf.title = title;
        return mf;
    }


//加载布局
    @Override
    protected int getLayoutID() {
        return R.layout.fragment_mian;
    }

    @Override
    protected void initChildView() {
        initView();
        refreshData();
    }

    @Override
    protected void judgeAndLoadData() {
        super.judgeAndLoadData();
        if(title=="性感妹子"){
            getDataFromNet(A_URL);
        }else if(title=="日本妹子"){
            getDataFromNet(B_URL);
        }else if(title=="台湾妹子"){
            getDataFromNet(C_URL);
        }else{
            getDataFromNet(D_URL);
        }
//        if(title=="性感妹子"){
//            getDataFromNet(A_URL);
//        }
//        if(title=="日本妹子"){
//            getDataFromNet(B_URL);
//        }
//        if(title=="台湾妹子"){
//            getDataFromNet(C_URL);
//        }
//        if (title=="清纯妹子"){
//            getDataFromNet(D_URL);
//        }
    }
    private void initView() {
        recyclerView=view.findViewById(R.id.rv_mian);
        mAdapter=new MZiAdapter(mcontext);
        recyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
        recyclerView.setAdapter(mAdapter);
        mBeans=new ArrayList<>();
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        //上拉刷新下拉加载更多
        refreshLayout = view.findViewById(R.id.srl_m_refresh);
        //设置 Header 为 Material样式
//        refreshLayout.setRefreshHeader(new MaterialHeader(mcontext).getResources().getColor());
//        //设置 Footer 为 球脉冲
//        refreshLayout.setRefreshFooter(new BallPulseFooter(mcontext).setSpinnerStyle(SpinnerStyle.Scale));
        mAdapter.setOnItemClickListener(new myItemClickListener());
    }

    private void refreshData() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if(title=="性感妹子"){
                    getDataFromNet(A_URL);
                }else if(title=="日本妹子"){
                    getDataFromNet(B_URL);
                }else if(title=="台湾妹子"){
                    getDataFromNet(C_URL);
                }else{
                    getDataFromNet(D_URL);
                }
                mAdapter.notifyDataSetChanged();
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                ++Page;
                if(title=="性感妹子"){
                    getDataFromNet(A_URL+BASE_PAGE+Page+"/");
                } else if(title=="日本妹子"){

                    getDataFromNet(B_URL+BASE_PAGE+Page+"/");
                }else if(title=="台湾妹子"){
                    getDataFromNet(C_URL+BASE_PAGE+Page+"/");
                }else  {
                    getDataFromNet(D_URL+BASE_PAGE+Page+"/");
                }
                refreshlayout.finishLoadmore(2000);
            }
        });
    }

    class  myItemClickListener implements BaseQuickAdapter.OnItemClickListener{
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            MeiZiBean mBean=mBeans.get(position);
//            Toast.makeText(mcontext,"点击了第"+position+"个item,链接为"+mBean.getMeiziHref(),Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(mcontext, MmDetails.class);
            Intent intent = new Intent(mcontext, MmDetailsList.class);
            intent.putExtra("DETAILS_URL",mBean.getMeiziHref());
            startActivity(intent);
        }
    }

    private void getDataFromNet(String url) {
        mHttpClient = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(url)
                .build();
        mHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //OkHttp3设置编码为GBK（默认编码是UTF-8）的两个步骤
//                byte[] responseBytes=response.body().bytes();
//
//
//                String responseUrl = new String(responseBytes,"GBK");
//                Log.d("okHttp", responseUrl);
//                processData(responseUrl);

                String json = response.body().string();
                spiderData(json);
//                handler.sendEmptyMessage(10);

            }
        });
    }
    private void spiderData(String html) {
        Document doc= Jsoup.parse(html);
        Elements getData=doc.select("figure");
        for (Element e:getData){
            MeiZiBean meiZiBean=new MeiZiBean();

            String href=e.select("a").attr("href");
            Log.e("链接",href);
            meiZiBean.setMeiziHref(href);
            String imgurl=e.select("img").attr("data-original");
            Log.e("图片地址",imgurl);
            meiZiBean.setMeiziImg(imgurl);
            mBeans.add(meiZiBean);
        }
        Log.e("总数", String.valueOf(mBeans.size()));
            ((Activity)mcontext).runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // 在这里执行你要想的操作 比如直接在这里更新ui或者调用回调在 在回调中更新ui
                    mAdapter.setNewData(mBeans);
                }
            });



    }

}

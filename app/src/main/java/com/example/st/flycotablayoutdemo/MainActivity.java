package com.example.st.flycotablayoutdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.st.flycotablayoutdemo.base.BaseFragment;
import com.example.st.flycotablayoutdemo.fragment.FragmentA;
import com.example.st.flycotablayoutdemo.fragment.FragmentB;
import com.example.st.flycotablayoutdemo.fragment.FragmentC;
import com.example.st.flycotablayoutdemo.utils.ViewFindUtils;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tablayout)
    SlidingTabLayout tablayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private ArrayList<BaseFragment> mFagments = new ArrayList<>();
    private String[] mTitles = {"性感妹子", "日本妹子", "台湾妹子", "清纯妹子", "妹子自拍", "西方美女"};

    private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        View decorView = getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(decorView, R.id.view_pager);
        SlidingTabLayout tabLayout_6 = ViewFindUtils.find(decorView, R.id.tablayout);
        initView();
    }

    private void initView() {
        // mToolbar.setTitle(R.string.discover);
        //initToolbarNav(mToolbar);

//        toolbar.setTitle(R.string.app_name);
        mFagments.add(FragmentA.getInstance(mTitles[0]));
        mFagments.add(FragmentA.getInstance(mTitles[1]));
        mFagments.add(FragmentA.getInstance(mTitles[2]));
        mFagments.add(FragmentA.getInstance(mTitles[3]));
        mFagments.add(FragmentB.getInstance(mTitles[4]));
        mFagments.add(FragmentC.getInstance(mTitles[5]));
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tablayout.setViewPager(viewPager, mTitles);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFagments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFagments.get(position);
        }
    }


}


package com.example.st.flycotablayoutdemo.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.st.flycotablayoutdemo.R;
import com.example.st.flycotablayoutdemo.model.MImgUrlBean;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by st on 18-2-19.
 */

public class ImgPagerAdapter extends PagerAdapter {
 private ArrayList<MImgUrlBean> mImgUrlBean;
    public   ImgPagerAdapter(ArrayList<MImgUrlBean>mImgUrlBean) {
        this.mImgUrlBean=mImgUrlBean;
    }

    @Override
    public int getCount() {
        return mImgUrlBean.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        final PhotoViewAttacher attacher = new PhotoViewAttacher(photoView);
//       Glide.with(container.getContext())
////                .load(mImgUrlBean.get(position))
//                .load(mImgUrlBean.get(position).getmImgdetail())
//                .into(photoView);
        GlideUrl url = new GlideUrl(mImgUrlBean.get(position).getmImgdetail(), new LazyHeaders.Builder().addHeader("Referer", "http://www.mzitu.com/").build());
        Glide.with(container.getContext()).load(url).into(photoView);
//        Glide.with(container.getContext()).load(mImgUrlBean.get(position).getmImgdetail()).into(photoView);

//        Picasso.with(container.getContext())
////                .load(mImgUrlBean.get(position))
//                .load(mImgUrlBean.get(position).getmImgdetail())
//                .into(photoView, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        attacher.update();
//                    }
//
//                    @Override
//                    public void onError() {
//
//                    }
//                });

        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
//        return false;
        return view == object;
    }
}

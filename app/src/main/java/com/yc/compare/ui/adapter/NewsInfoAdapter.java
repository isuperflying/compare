package com.yc.compare.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.compare.R;
import com.yc.compare.bean.NewsInfo;

import java.util.List;

/**
 * Created by admin on 2018/1/8.
 */

public class NewsInfoAdapter extends BaseQuickAdapter<NewsInfo, BaseViewHolder> {

    private Context mContext;

    public NewsInfoAdapter(Context context, List<NewsInfo> datas) {
        super(R.layout.fragment_news_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final NewsInfo item) {
        helper.setText(R.id.tv_news_title, item.getTitle()).setText(R.id.tv_news_date, item.getPublishDate()).setText(R.id.tv_news_read_count, item.getClickNum() + "阅读");

        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.news_def_img)//图片加载出来前，显示的图片
                .fallback(R.mipmap.news_def_img) //url为空的时候,显示的图片
                .error(R.mipmap.news_def_img);//图片加载失败后，显示的图片

        Glide.with(mContext).load(item.getCoverImage()).apply(options).into((ImageView) helper.itemView.findViewById(R.id.iv_news_cover));
    }
}
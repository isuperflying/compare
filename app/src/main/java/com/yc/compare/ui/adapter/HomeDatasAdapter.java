package com.yc.compare.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.yc.compare.R;
import com.yc.compare.bean.HomeDataInfo;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by admin on 2018/1/8.
 */

public class HomeDatasAdapter extends BaseQuickAdapter<HomeDataInfo, BaseViewHolder> {

    private Context mContext;

    public HomeDatasAdapter(Context context, List<HomeDataInfo> datas) {
        super(R.layout.home_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final HomeDataInfo item) {
        helper.setText(R.id.item_title_tv, item.getTitle());
        RequestOptions options = new RequestOptions();
        options.override(ScreenUtils.getScreenWidth()/2,ScreenUtils.getScreenWidth()/2 - SizeUtils.dp2px(60));
        Glide.with(mContext).load(item.getSmallImg()).apply(options).into((ImageView) helper.itemView.findViewById(R.id.item_iv));
    }
}
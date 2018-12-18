package com.yc.compare.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.yc.compare.R;
import com.yc.compare.bean.RecordItem;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by admin on 2018/1/8.
 */

public class HotDatasAdapter extends BaseQuickAdapter<RecordItem, BaseViewHolder> {

    private Context mContext;

    public HotDatasAdapter(Context context, List<RecordItem> datas) {
        super(R.layout.hot_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final RecordItem item) {
        RequestOptions options = new RequestOptions();
        //options.skipMemoryCache(true);
        //options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        int width = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(15)) / 2;
        //options.transform(new GlideRoundTransform(mContext, 5));
        options.override(width, width);
        Logger.i("imageurl--->" + item.getFullCover());
        Glide.with(mContext).asBitmap().load(item.getFullCover()).apply(options).into((ImageView) helper.itemView.findViewById(R.id.item_iv));
    }
}
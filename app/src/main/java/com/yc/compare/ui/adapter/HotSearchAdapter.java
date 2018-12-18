package com.yc.compare.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.compare.R;
import com.yc.compare.bean.BrandInfo;

import java.util.List;

/**
 * Created by admin on 2018/1/8.
 */

public class HotSearchAdapter extends BaseQuickAdapter<BrandInfo, BaseViewHolder> {

    private Context mContext;

    public HotSearchAdapter(Context context, List<BrandInfo> datas) {
        super(R.layout.hot_search_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final BrandInfo item) {
        //helper.setText(R.id.tv_hot_word, item.getBrandName());
    }
}
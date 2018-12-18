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

public class SearchBrandAdapter extends BaseQuickAdapter<BrandInfo, BaseViewHolder> {

    private Context mContext;

    public SearchBrandAdapter(Context context, List<BrandInfo> datas) {
        super(R.layout.search_brand_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final BrandInfo item) {
        helper.setText(R.id.tv_search_brand, item.getBrandName());
        if (item.isSelected()) {
            helper.setBackgroundRes(R.id.iv_country_checked, R.mipmap.country_selected_icon);
        } else {
            helper.setBackgroundRes(R.id.iv_country_checked, R.color.transparent);
        }
    }
}
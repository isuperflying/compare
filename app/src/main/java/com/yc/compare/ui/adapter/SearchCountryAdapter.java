package com.yc.compare.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.compare.R;
import com.yc.compare.bean.CountryInfo;

import java.util.List;

/**
 * Created by admin on 2018/1/8.
 */

public class SearchCountryAdapter extends BaseQuickAdapter<CountryInfo, BaseViewHolder> {

    private Context mContext;

    public SearchCountryAdapter(Context context, List<CountryInfo> datas) {
        super(R.layout.search_country_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CountryInfo item) {
        helper.setText(R.id.tv_country, item.getCountryName());
    }
}
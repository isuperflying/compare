package com.yc.compare.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.compare.R;
import com.yc.compare.bean.OtherPriceInfo;

import java.util.List;

/**
 * Created by admin on 2018/1/8.
 */

public class OtherPriceAdapter extends BaseQuickAdapter<OtherPriceInfo, BaseViewHolder> {

    private Context mContext;

    public OtherPriceAdapter(Context context, List<OtherPriceInfo> datas) {
        super(R.layout.other_price_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final OtherPriceInfo item) {
        helper.setText(R.id.tv_country_name, item.getCountryName())
                .setText(R.id.tv_local_price, item.getLocalPrice() + "")
                .setText(R.id.tv_rmb_price, item.getChinaPrice() + "")
                .setText(R.id.tv_rate, item.getRate() + "");
    }
}
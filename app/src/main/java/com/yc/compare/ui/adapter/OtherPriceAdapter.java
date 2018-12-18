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
        //helper.setText(R.id.tv_brand_name, item.getBrandName());
        //Glide.with(mContext).asBitmap().load(item.getBrandImage()).into((ImageView) helper.itemView.findViewById(R.id.iv_brand));
    }
}
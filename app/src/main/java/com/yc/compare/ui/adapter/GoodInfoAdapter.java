package com.yc.compare.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.compare.R;
import com.yc.compare.bean.GoodInfo;

import java.util.List;

/**
 * Created by admin on 2018/1/8.
 */

public class GoodInfoAdapter extends BaseQuickAdapter<GoodInfo, BaseViewHolder> {

    private Context mContext;

    public GoodInfoAdapter(Context context, List<GoodInfo> datas) {
        super(R.layout.good_info_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GoodInfo item) {
        helper.setText(R.id.tv_good_name, item.getGoodName())
                .setText(R.id.tv_good_price, item.getGoodPrice())
                .setText(R.id.tv_buy_country_num, item.getGoodsCountryNum() + "个国家有货");
        Glide.with(mContext).asBitmap().load(item.getGoodImage()).into((ImageView) helper.itemView.findViewById(R.id.iv_good));
    }
}
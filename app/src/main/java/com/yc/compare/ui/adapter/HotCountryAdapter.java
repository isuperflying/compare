package com.yc.compare.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.compare.R;
import com.yc.compare.bean.CountryInfo;

import java.util.List;

/**
 * Created by admin on 2018/1/8.
 */

public class HotCountryAdapter extends BaseQuickAdapter<CountryInfo, BaseViewHolder> {

    private Context mContext;

    public HotCountryAdapter(Context context, List<CountryInfo> datas) {
        super(R.layout.hot_country_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CountryInfo item) {
        switch (helper.getAdapterPosition()) {
            case 0:
                helper.setTextColor(R.id.tv_num, ContextCompat.getColor(mContext, R.color.country1_color));
                break;
            case 1:
                helper.setTextColor(R.id.tv_num, ContextCompat.getColor(mContext, R.color.country2_color));
                break;
            case 2:
                helper.setTextColor(R.id.tv_num, ContextCompat.getColor(mContext, R.color.country3_color));
                break;
            default:
                helper.setTextColor(R.id.tv_num, ContextCompat.getColor(mContext, R.color.country_def_color));
                break;
        }
        helper.setText(R.id.tv_country_name, item.getCountryName()).setText(R.id.tv_num, (helper.getAdapterPosition() + 1) + "");
        Glide.with(mContext).asBitmap().load(item.getCountryImage()).into((ImageView) helper.itemView.findViewById(R.id.iv_country));
    }
}
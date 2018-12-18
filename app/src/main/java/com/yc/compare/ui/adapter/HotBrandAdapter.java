package com.yc.compare.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.compare.R;
import com.yc.compare.bean.BrandInfo;

import java.util.List;

/**
 * Created by admin on 2018/1/8.
 */

public class HotBrandAdapter extends BaseQuickAdapter<BrandInfo, BaseViewHolder> {

    private Context mContext;

    public HotBrandAdapter(Context context, List<BrandInfo> datas) {
        super(R.layout.brand_info_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final BrandInfo item) {
        helper.setText(R.id.tv_brand_name, item.getBrandName());
        Glide.with(mContext).asBitmap().load(item.getBrandImage()).into((ImageView) helper.itemView.findViewById(R.id.iv_brand));
    }
}
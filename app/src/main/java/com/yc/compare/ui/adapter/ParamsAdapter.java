package com.yc.compare.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.compare.R;
import com.yc.compare.bean.GoodDetailInfo;

import java.util.List;

/**
 * Created by admin on 2018/1/8.
 */

public class ParamsAdapter extends BaseQuickAdapter<GoodDetailInfo.AttrItem, BaseViewHolder> {

    private Context mContext;

    public ParamsAdapter(Context context, List<GoodDetailInfo.AttrItem> datas) {
        super(R.layout.good_params_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GoodDetailInfo.AttrItem item) {
        helper.setText(R.id.tv_left_params, item.getName()).setText(R.id.tv_right_value, item.getValue() + "");
    }
}
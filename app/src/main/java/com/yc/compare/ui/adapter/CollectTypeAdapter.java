package com.yc.compare.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.compare.R;
import com.yc.compare.bean.CollectType;

import java.util.List;

/**
 * Created by admin on 2018/1/8.
 */

public class CollectTypeAdapter extends BaseQuickAdapter<CollectType, BaseViewHolder> {

    private Context mContext;

    public CollectTypeAdapter(Context context, List<CollectType> datas) {
        super(R.layout.collect_type_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CollectType item) {
        helper.setText(R.id.tv_collect_type_name, item.getName()).setText(R.id.tv_collect_type_count, item.getCount() + "");
        if (item.isSelected()) {
            helper.setBackgroundRes(R.id.layout_type_item, R.drawable.collect_type_select_item_bg);
            helper.setVisible(R.id.iv_selected, true).setVisible(R.id.tv_collect_type_count, false);
        } else {
            helper.setBackgroundRes(R.id.layout_type_item, R.drawable.collect_type_item_bg);
            helper.setVisible(R.id.iv_selected, false).setVisible(R.id.tv_collect_type_count, true);
        }
    }
}
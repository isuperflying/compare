package com.yc.compare.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.compare.R;
import com.yc.compare.bean.CategoryInfo;

import java.util.List;

/**
 * Created by admin on 2018/1/8.
 */

public class CategoryLeftAdapter extends BaseQuickAdapter<CategoryInfo, BaseViewHolder> {

    private Context mContext;

    public CategoryLeftAdapter(Context context, List<CategoryInfo> datas) {
        super(R.layout.category_left_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CategoryInfo item) {
        helper.setText(R.id.tv_left_type_name, item.getName());
        if(item.isSelected()){
            helper.setVisible(R.id.left_line,true);
            helper.setTextColor(R.id.tv_left_type_name,ContextCompat.getColor(mContext,R.color.good_price_color));
        }else{
            helper.setVisible(R.id.left_line,false);
            helper.setTextColor(R.id.tv_left_type_name,ContextCompat.getColor(mContext,R.color.top_type_text_color));
        }
    }
}
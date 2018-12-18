package com.yc.compare.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.compare.R;
import com.yc.compare.bean.CollectInfo;

import java.util.List;

/**
 * Created by admin on 2018/1/8.
 */

public class CollectAdapter extends BaseQuickAdapter<CollectInfo, BaseViewHolder> {

    private Context mContext;

    private boolean isEdit;

    private boolean isCheckAll;

    public CollectAdapter(Context context, List<CollectInfo> datas) {
        super(R.layout.collect_info_item, datas);
        this.mContext = context;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public void setCheckAll(boolean checkAll) {
        isCheckAll = checkAll;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CollectInfo item) {

        LinearLayout leftLayout = helper.itemView.findViewById(R.id.layout_left_checkbox);
        ImageView leftCheck = helper.itemView.findViewById(R.id.iv_check_box);

        if (isEdit) {
            leftLayout.setVisibility(View.VISIBLE);
        } else {
            leftLayout.setVisibility(View.GONE);
        }

        if(item.isChecked()){
            helper.setBackgroundRes(R.id.iv_check_box,R.mipmap.item_selected_icon);
        }else{
            helper.setBackgroundRes(R.id.iv_check_box,R.mipmap.item_normal_icon);
        }

//        helper.setText(R.id.tv_brand_name, item.getBrandName());
//        Glide.with(mContext).asBitmap().load(item.getBrandImage()).into((ImageView) helper.itemView.findViewById(R.id.iv_brand));
    }
}
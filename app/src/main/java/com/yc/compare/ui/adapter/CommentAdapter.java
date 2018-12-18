package com.yc.compare.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.compare.R;
import com.yc.compare.bean.CommentInfo;

import java.util.List;

/**
 * Created by admin on 2018/1/8.
 */

public class CommentAdapter extends BaseQuickAdapter<CommentInfo, BaseViewHolder> {

    private Context mContext;

    public CommentAdapter(Context context, List<CommentInfo> datas) {
        super(R.layout.comment_info_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CommentInfo item) {
        //helper.setText(R.id.tv_brand_name, item.getBrandName());
        //Glide.with(mContext).asBitmap().load(item.getBrandImage()).into((ImageView) helper.itemView.findViewById(R.id.iv_brand));
    }
}
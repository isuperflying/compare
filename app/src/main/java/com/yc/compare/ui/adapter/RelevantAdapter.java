package com.yc.compare.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.compare.R;
import com.yc.compare.bean.RelevantInfo;

import java.util.List;

/**
 * Created by admin on 2018/1/8.
 */

public class RelevantAdapter extends BaseQuickAdapter<RelevantInfo, BaseViewHolder> {

    private Context mContext;

    public RelevantAdapter(Context context, List<RelevantInfo> datas) {
        super(R.layout.relevant_item_view, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final RelevantInfo item) {
        helper.setText(R.id.tv_relevant_title, item.getTitle()).setText(R.id.tv_relevant_date, item.getPublishTime());
        Glide.with(mContext).asBitmap().load(item.getThumb()).into((ImageView) helper.itemView.findViewById(R.id.iv_relevant_img));
    }
}
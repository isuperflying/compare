package com.yc.compare.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.compare.R;
import com.yc.compare.bean.NewsComment;

import java.util.List;

/**
 * Created by admin on 2018/1/8.
 */

public class ReplyListAdapter extends BaseQuickAdapter<NewsComment, BaseViewHolder> {

    private Context mContext;

    public ReplyListAdapter(Context context, List<NewsComment> datas) {
        super(R.layout.reply_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final NewsComment item) {
        helper.setText(R.id.tv_user_name, item.getNickname())
                .setText(R.id.tv_reply_content, item.getContent())
                .setText(R.id.tv_reply_date, item.getAddTime());

        Glide.with(mContext).load(item.getHeadPic()).into((ImageView) helper.itemView.findViewById(R.id.iv_user_head));
    }
}
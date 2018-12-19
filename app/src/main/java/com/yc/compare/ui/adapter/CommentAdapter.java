package com.yc.compare.ui.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.compare.R;
import com.yc.compare.bean.CommentInfo;
import com.yc.compare.bean.ImageInfo;

import java.util.ArrayList;
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
        Glide.with(mContext).asBitmap().load(item.getUserHeadImage()).into((ImageView) helper.itemView.findViewById(R.id.iv_user_head));
        helper.setText(R.id.tv_user_name, item.getUserNickName())
                .setText(R.id.tv_comment_date, item.getCommentDate())
                .setText(R.id.tv_comment_content, item.getCommentContent())
                .setText(R.id.tv_good_name, item.getSubTitle())
                .setText(R.id.tv_see_count, "浏览" + item.getViewCount() + "次");

        List<ImageInfo> imageInfos = new ArrayList<>();
        if (item.getGoodImages() != null && item.getGoodImages().length > 0) {
            for (int i = 0; i < item.getGoodImages().length; i++) {
                imageInfos.add(new ImageInfo(i + "", item.getGoodImages()[i]));
            }
            CommentImageAdapter commentImageAdapter = new CommentImageAdapter(mContext, imageInfos);
            RecyclerView itemImageView = helper.itemView.findViewById(R.id.comment_img_list);
            itemImageView.setLayoutManager(new GridLayoutManager(mContext, item.getGoodImages().length));
            itemImageView.setAdapter(commentImageAdapter);
        }

    }
}
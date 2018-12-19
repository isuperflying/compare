package com.yc.compare.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.compare.R;
import com.yc.compare.bean.ImageInfo;

import java.util.List;

/**
 * Created by admin on 2018/1/8.
 */

public class CommentImageAdapter extends BaseQuickAdapter<ImageInfo, BaseViewHolder> {

    private Context mContext;

    public CommentImageAdapter(Context context, List<ImageInfo> datas) {
        super(R.layout.comment_image_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ImageInfo item) {
        Glide.with(mContext).asBitmap().load(item.getImgUrl()).into((ImageView) helper.itemView.findViewById(R.id.iv_good_item));
    }
}
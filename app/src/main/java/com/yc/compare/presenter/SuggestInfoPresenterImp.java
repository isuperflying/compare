package com.yc.compare.presenter;

import android.content.Context;

import com.yc.compare.base.BasePresenterImp;
import com.yc.compare.bean.ResultInfo;
import com.yc.compare.model.SuggestInfoModelImp;
import com.yc.compare.view.SuggestInfoView;

/**
 * Created by admin on 2017/3/13.
 */

public class SuggestInfoPresenterImp extends BasePresenterImp<SuggestInfoView, ResultInfo> implements SuggestInfoPresenter {

    private Context context = null;
    private SuggestInfoModelImp suggestInfoModelImp = null;

    public SuggestInfoPresenterImp(SuggestInfoView view, Context context) {
        super(view);
        this.context = context;
        this.suggestInfoModelImp = new SuggestInfoModelImp(context);
    }

    @Override
    public void addSuggest(String userId, String content) {
        suggestInfoModelImp.addSuggest(userId, content, this);
    }
}

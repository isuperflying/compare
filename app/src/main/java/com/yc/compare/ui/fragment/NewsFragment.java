package com.yc.compare.ui.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yc.compare.R;
import com.yc.compare.bean.NewsInfoRet;
import com.yc.compare.common.Constants;
import com.yc.compare.presenter.NewsInfoPresenterImp;
import com.yc.compare.ui.NewsSearchResultActivity;
import com.yc.compare.ui.adapter.SubFragmentAdapter;
import com.yc.compare.ui.base.BaseFragment;
import com.yc.compare.ui.fragment.sub.NewsItemFragment;
import com.yc.compare.view.NewsInfoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iflying on 2017/12/14.
 */

public class NewsFragment extends BaseFragment implements NewsInfoView {

    private NewsInfoPresenterImp newsInfoPresenterImp;

    @BindView(R.id.search_layout)
    LinearLayout mSearchLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.et_news_key_word)
    EditText mKeyWordEditText;

    List<String> mTitleDataList;

    private String keyWord;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_new_info, null);
        ButterKnife.bind(this, root);
        initViews();
        return root;
    }

    public void initViews() {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth() - SizeUtils.dp2px(30), SizeUtils.dp2px(30));
        params.setMargins(SizeUtils.dp2px(15), BarUtils.getStatusBarHeight(), 0, 0);
        mSearchLayout.setLayoutParams(params);

        newsInfoPresenterImp = new NewsInfoPresenterImp(this, getActivity());
        newsInfoPresenterImp.getNewsInfoList("", "", 1);

        mKeyWordEditText.setOnEditorActionListener(new EditorActionListener());
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(NewsInfoRet tData) {
        if (tData != null && tData.getCode() == Constants.SUCCESS) {

            if (tData.getData() != null && tData.getData().getCategory_list() != null && tData.getData().getCategory_list().size() > 0) {
                List<Fragment> fragments = new ArrayList<>();
                mTitleDataList = new ArrayList<>();
                for (int i = 0; i < tData.getData().getCategory_list().size(); i++) {
                    String cid = tData.getData().getCategory_list().get(i).getId();

                    fragments.add(NewsItemFragment.newInstance(cid));
                    mTitleDataList.add(tData.getData().getCategory_list().get(i).getName());
                }

                SubFragmentAdapter viewPageAdapter = new SubFragmentAdapter(getChildFragmentManager(), fragments, mTitleDataList);
                mViewPager.setAdapter(viewPageAdapter);

                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

                tabLayout.setupWithViewPager(mViewPager);
            }

        }
    }

    @Override
    public void loadDataError(Throwable throwable) {

    }

    private class EditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            switch (textView.getId()) {
                case R.id.et_news_key_word:
                    if (i == EditorInfo.IME_ACTION_SEARCH) {
                        if (StringUtils.isEmpty(textView.getText())) {
                            ToastUtils.showLong("请输入关键词后搜索");
                            break;
                        }

                        keyWord = textView.getText().toString();
                        KeyboardUtils.hideSoftInput(getActivity());

                        Intent intent = new Intent(getActivity(), NewsSearchResultActivity.class);
                        intent.putExtra("key_word", keyWord);
                        startActivity(intent);
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    }
}

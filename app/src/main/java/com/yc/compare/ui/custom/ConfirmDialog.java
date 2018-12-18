package com.yc.compare.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yc.compare.R;

public class ConfirmDialog extends Dialog {

    private Context context;
    private ClickListenerInterface clickListenerInterface;

    EditText mNickName;

    public interface ClickListenerInterface {
        public void doConfirm(String nickName);

        public void doCancel();
    }

    public ConfirmDialog(Context context) {
        super(context, com.qmuiteam.qmui.R.style.QMUI_Dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.common_dialog, null);
        setContentView(view);

        TextView tvConfirm = view.findViewById(R.id.tv_config);
        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        mNickName = view.findViewById(R.id.et_nick_name);

        tvConfirm.setOnClickListener(new clickListener());
        tvCancel.setOnClickListener(new clickListener());

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.75); // 高度设置为屏幕的0.6
        lp.height = SizeUtils.dp2px(150);
        dialogWindow.setAttributes(lp);
    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            int id = v.getId();
            switch (id) {
                case R.id.tv_config:
                    if (StringUtils.isEmpty(mNickName.getText())) {
                        ToastUtils.showLong("请输入昵称");
                        return;
                    }
                    clickListenerInterface.doConfirm(mNickName.getText().toString());
                    break;
                case R.id.tv_cancel:
                    clickListenerInterface.doCancel();
                    break;
            }
        }

    }

    ;

}
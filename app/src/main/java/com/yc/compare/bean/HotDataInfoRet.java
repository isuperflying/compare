package com.yc.compare.bean;

import java.util.List;

/**
 * Created by admin on 2018/1/8.
 */

public class HotDataInfoRet {

    private int code;
    private HotEmojiBean data;
    private String msg;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public HotEmojiBean getData() {
        return data;
    }

    public void setData(HotEmojiBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

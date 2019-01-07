package com.yc.compare.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateInfoRet extends ResultInfo {
    private List<UpdateResult> data;

    public List<UpdateResult> getData() {
        return data;
    }

    public void setData(List<UpdateResult> data) {
        this.data = data;
    }

    public class UpdateResult {
        @SerializedName("head_pic")
        private String pic;

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }

}

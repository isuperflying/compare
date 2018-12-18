package com.yc.compare.bean;

/**
 * Created by myflying on 2018/12/7.
 */
public class ImageInfo {
    private String id;
    private String imgUrl;

    public ImageInfo(String id,String imgurl){
        this.id = id;
        this.imgUrl = imgurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

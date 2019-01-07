package com.yc.compare.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by myflying on 2018/12/18.
 */
public class GoodDetailInfo {

    @SerializedName("is_collect")
    private int isCollect;

    private GdInfo info;

    private PriceInfo prices;

    private GdComment comments;

    private List<GoodInfo> similar;

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public GdInfo getInfo() {
        return info;
    }

    public void setInfo(GdInfo info) {
        this.info = info;
    }

    public PriceInfo getPrices() {
        return prices;
    }

    public void setPrices(PriceInfo prices) {
        this.prices = prices;
    }

    public GdComment getComments() {
        return comments;
    }

    public void setComments(GdComment comments) {
        this.comments = comments;
    }

    public List<GoodInfo> getSimilar() {
        return similar;
    }

    public void setSimilar(List<GoodInfo> similar) {
        this.similar = similar;
    }

    public class GdInfo {
        @SerializedName("goods_id")
        private String id;

        @SerializedName("goods_name")
        private String goodName;

        @SerializedName("img")
        private String goodImage;

        @SerializedName("market_price")
        private String goodPrice;

        @SerializedName("goods_content")
        private String goodsContent;

        @SerializedName("goods_content_imgs")
        private String goodsContentImgs;

        @SerializedName("goods_images")
        private String[] goodsImages;

        @SerializedName("attr_value")
        private List<AttrItem> attrValue;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoodName() {
            return goodName;
        }

        public void setGoodName(String goodName) {
            this.goodName = goodName;
        }

        public String getGoodImage() {
            return goodImage;
        }

        public void setGoodImage(String goodImage) {
            this.goodImage = goodImage;
        }

        public String getGoodPrice() {
            return goodPrice;
        }

        public void setGoodPrice(String goodPrice) {
            this.goodPrice = goodPrice;
        }

        public String getGoodsContent() {
            return goodsContent;
        }

        public void setGoodsContent(String goodsContent) {
            this.goodsContent = goodsContent;
        }

        public String getGoodsContentImgs() {
            return goodsContentImgs;
        }

        public void setGoodsContentImgs(String goodsContentImgs) {
            this.goodsContentImgs = goodsContentImgs;
        }

        public String[] getGoodsImages() {
            return goodsImages;
        }

        public void setGoodsImages(String[] goodsImages) {
            this.goodsImages = goodsImages;
        }

        public List<AttrItem> getAttrValue() {
            return attrValue;
        }

        public void setAttrValue(List<AttrItem> attrValue) {
            this.attrValue = attrValue;
        }
    }

    public class PriceInfo {
        private double min;
        private double max;
        private List<PriceItem> list;

        public double getMin() {
            return min;
        }

        public void setMin(double min) {
            this.min = min;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        public List<PriceItem> getList() {
            return list;
        }

        public void setList(List<PriceItem> list) {
            this.list = list;
        }
    }

    public class PriceItem {
        private String name;

        @SerializedName("market_price")
        private String marketPrice;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(String marketPrice) {
            this.marketPrice = marketPrice;
        }
    }

    public class AttrItem {
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public class GdComment {
        @SerializedName("list_count")
        private String listCount;

        private List<GdCommentItem> list;

        public String getListCount() {
            return listCount;
        }

        public void setListCount(String listCount) {
            this.listCount = listCount;
        }

        public List<GdCommentItem> getList() {
            return list;
        }

        public void setList(List<GdCommentItem> list) {
            this.list = list;
        }
    }

    public class GdCommentItem {
        @SerializedName("add_time")
        private String addTime;
        @SerializedName("nickname")
        private String nickName;
        @SerializedName("head_pic")
        private String headPic;
        private String content;

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}

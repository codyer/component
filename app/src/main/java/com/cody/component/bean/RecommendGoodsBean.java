/*
 * ************************************************************
 * 文件：RecommendGoodsBean.java  模块：app  项目：component
 * 当前修改时间：2019年04月28日 17:02:49
 * 上次修改时间：2019年04月27日 14:34:35
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.bean;

import java.util.List;

/**
 * Created by xu.yi. on 2019/4/24.
 * p-gbb-android 邦邦优选 推荐
 */
public class RecommendGoodsBean {

    /**
     * bgColor : string
     * bgUrl : string
     * catalogId : 0
     * hotKeywords : [{"keyword":"string","link":"string"}]
     * recommendBrands : {"items":[{"brandId":0,"brandName":"string","brandSmallLogo":"string","id":0,"link":"string"}],"title":"string"}
     * secondCatalogs : [{"catalogId":0,"catalogName":"string","id":0,"link":"string","slogan":"string","url":"string"}]
     * title : string
     */

    private String bgColor;
    private String bgUrl;
    private int catalogId;
    private RecommendBrandsBean recommendBrands;
    private String title;
    private List<HotKeywordsBean> hotKeywords;
    private List<SecondCatalogsBean> secondCatalogs;

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getBgUrl() {
        return bgUrl;
    }

    public void setBgUrl(String bgUrl) {
        this.bgUrl = bgUrl;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    public RecommendBrandsBean getRecommendBrands() {
        return recommendBrands;
    }

    public void setRecommendBrands(RecommendBrandsBean recommendBrands) {
        this.recommendBrands = recommendBrands;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HotKeywordsBean> getHotKeywords() {
        return hotKeywords;
    }

    public void setHotKeywords(List<HotKeywordsBean> hotKeywords) {
        this.hotKeywords = hotKeywords;
    }

    public List<SecondCatalogsBean> getSecondCatalogs() {
        return secondCatalogs;
    }

    public void setSecondCatalogs(List<SecondCatalogsBean> secondCatalogs) {
        this.secondCatalogs = secondCatalogs;
    }

    public static class RecommendBrandsBean {
        /**
         * items : [{"brandId":0,"brandName":"string","brandSmallLogo":"string","id":0,"link":"string"}]
         * title : string
         */

        private String title;
        private List<ItemsBean> items;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * brandId : 0
             * brandName : string
             * brandSmallLogo : string
             * id : 0
             * link : string
             */

            private int brandId;
            private String brandName;
            private String brandSmallLogo;
            private int id;
            private String link;

            public int getBrandId() {
                return brandId;
            }

            public void setBrandId(int brandId) {
                this.brandId = brandId;
            }

            public String getBrandName() {
                return brandName;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }

            public String getBrandSmallLogo() {
                return brandSmallLogo;
            }

            public void setBrandSmallLogo(String brandSmallLogo) {
                this.brandSmallLogo = brandSmallLogo;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }
    }

    public static class HotKeywordsBean {
        /**
         * keyword : string
         * link : string
         */

        private String keyword;
        private String link;

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }

    public static class SecondCatalogsBean {
        /**
         * catalogId : 0
         * catalogName : string
         * id : 0
         * link : string
         * slogan : string
         * url : string
         */

        private int catalogId;
        private String catalogName;
        private int id;
        private String link;
        private String slogan;
        private String url;

        public int getCatalogId() {
            return catalogId;
        }

        public void setCatalogId(int catalogId) {
            this.catalogId = catalogId;
        }

        public String getCatalogName() {
            return catalogName;
        }

        public void setCatalogName(String catalogName) {
            this.catalogName = catalogName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getSlogan() {
            return slogan;
        }

        public void setSlogan(String slogan) {
            this.slogan = slogan;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}

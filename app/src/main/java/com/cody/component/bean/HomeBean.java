/*
 * ************************************************************
 * 文件：HomeBean.java  模块：app  项目：component
 * 当前修改时间：2019年04月28日 17:02:49
 * 上次修改时间：2019年04月27日 17:14:30
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
 * p-gbb-android 首页数据
 */
public class HomeBean {

    /**
     * banners : [{"id":1,"link":"https://m.gongbangbang.com/bannerpage/hjjx201904/index.html","url":"https://file.gongbangbang.com/resources/u/2019/04/26/5de424490cc14fe4acbad3ea23efd143ELtJSW.jpg"},{"id":2,"link":"https://m.gongbangbang.com/bannerpage/hjjx201904/index.html","url":"https://file.gongbangbang.com/resources/u/2019/04/26/a0a8d9118fac4f40a27590a2d3390c8erkV0L8.jpg"},{"id":3,"link":" ","url":"https://file.gongbangbang.com/resources/u/2019/04/26/1943c2f51c424d2ba904880a5e82ed516g1RHb.jpg"},{"id":4,"link":" ","url":"https://file.gongbangbang.com/resources/u/2019/04/26/8a2fb223aab04ac1b4075806eb682933T9gwKQ.jpg"},{"id":5,"link":" ","url":"https://file.gongbangbang.com/resources/u/2019/04/26/72456f69505d46b19e3f032549152c6dvrNhGp.jpg"}]
     * clearanceSales : {"title":"清仓特卖"}
     * expertRecommends : {"title":"行家精选"}
     * firstLevelLists : [{"catalogId":19,"title":"电气"},{"catalogId":13,"title":"个人防护"},{"catalogId":10,"title":"安防"},{"catalogId":5,"title":"仪器仪表"},{"catalogId":12,"title":"刀量磨具"},{"catalogId":30,"title":"手工具"},{"catalogId":4,"title":"动力工具"}]
     * hotBrands : {"items":[{"bigPic":2,"brandBigLogo":"https://file.gongbangbang.com/resources/u/2019/04/24/6c0dcbb437964bf18b9dbdeb94afe7b0pErhH1.png","brandDesc":"创新 智能 高效","brandId":243,"brandName":"ABB","brandSmallLogo":"http://pathfinder-privateuat.oss-cn-hangzhou.aliyuncs.com/BRAND/LOGO_197547_01.jpg?x-oss-process=style/gongbangbang_style","id":1,"link":"gbb://search?k={\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"productFilter\":{\"brandIds\":[243]},\"size\":20}"},{"bigPic":2,"brandBigLogo":"https://file.gongbangbang.com/resources/u/2019/04/24/3502e8c222ec40acbb8177ded58522b5KUFmzJ.png","brandDesc":"节能高效 安全可靠","brandId":208,"brandName":"SCHNEIDER/施耐德","brandSmallLogo":"http://pathfinder-privateuat.oss-cn-hangzhou.aliyuncs.com/BRAND/LOGO_197512_01.jpg?x-oss-process=style/gongbangbang_style","id":2,"link":"gbb://search?k={\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"productFilter\":{\"brandIds\":[208]},\"size\":20}"},{"bigPic":1,"brandBigLogo":"https://file.gongbangbang.com/resources/u/2019/04/25/e804e45f65de46268c10d8488244ac1aYw9pHG.png","brandDesc":"安全耐用 精准易用","brandId":474,"brandName":"FLUKE/福禄克","brandSmallLogo":"http://pathfinder-privateuat.oss-cn-hangzhou.aliyuncs.com/BRAND/LOGO_197778_01.jpg?x-oss-process=style/gongbangbang_style","id":3,"link":"gbb://search?k={\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"productFilter\":{\"brandIds\":[474]},\"size\":20}"},{"bigPic":1,"brandBigLogo":"https://file.gongbangbang.com/resources/u/2019/04/24/ea1ac62b506e4641a9edbf910d660b66H8Gkzi.png","brandDesc":"科技 改善生活","brandId":240,"brandName":"3M","brandSmallLogo":"http://pathfinder-privateuat.oss-cn-hangzhou.aliyuncs.com/BRAND/LOGO_197544_01.jpg?x-oss-process=style/gongbangbang_style","id":4,"link":"gbb://search?k={\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"productFilter\":{\"brandIds\":[240]},\"size\":20}"},{"bigPic":1,"brandBigLogo":"https://file.gongbangbang.com/resources/u/2019/04/24/1addcccd794d4f5097c5e77e672e433boceEsV.png","brandDesc":"全球防护 本地服务","brandId":391,"brandName":"DELTA/代尔塔","brandSmallLogo":"http://pathfinder-privateuat.oss-cn-hangzhou.aliyuncs.com/Others/iH5w3jP8hp.png?x-oss-process=style/gongbangbang_style","id":5,"link":"gbb://search?k={\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"productFilter\":{\"brandIds\":[391]},\"size\":20}"}],"title":"精选品牌"}
     * hotCatalogs : [{"bgUrl":"https://file.gongbangbang.com/resources/u/2019/04/24/5c52326ab6f3405b992f031d18a54d80qSpgaL.png","catalogId":19,"catalogName":"电气","id":1,"link":"gbb://search?k= {\"catalogueId\":19,\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"size\":20}"},{"bgUrl":"https://file.gongbangbang.com/resources/u/2019/04/24/4a36effbab9049c6b841af4b8a0f848cQul6Tz.png","catalogId":13,"catalogName":"个人防护","id":2,"link":"gbb://search?k= {\"catalogueId\":13,\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"size\":20}"},{"bgUrl":"https://file.gongbangbang.com/resources/u/2019/04/24/d721352bfad24da69d52786f9a75a96drrWeL3.png","catalogId":10,"catalogName":"安防","id":3,"link":"gbb://search?k= {\"catalogueId\":10,\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"size\":20}"},{"bgUrl":"https://file.gongbangbang.com/resources/u/2019/04/24/92de031ceb194b5e8671d8947f026029TBRB5E.png","catalogId":11,"catalogName":"仪器仪表","id":4,"link":"gbb://search?k= {\"catalogueId\":11,\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"size\":20}"},{"bgUrl":"https://file.gongbangbang.com/resources/u/2019/04/24/881d7246dd4f46ebb21782b574edad78MKhdu2.png","catalogId":12,"catalogName":"刀量磨具","id":5,"link":"gbb://search?k= {\"catalogueId\":12,\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"size\":20}"},{"bgUrl":"https://file.gongbangbang.com/resources/u/2019/04/24/563bdb859b3941ad84bbcf4ca1ba65a0S1qzdu.png","catalogId":3,"catalogName":"胶粘剂","id":6,"link":"gbb://search?k= {\"catalogueId\":3,\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"size\":20}"},{"bgUrl":"https://file.gongbangbang.com/resources/u/2019/04/24/549f9913e1fa468887b5deee5f028cb4YXnJ3T.png","catalogId":15,"catalogName":"润滑剂","id":7,"link":"gbb://search?k= {\"catalogueId\":15,\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"size\":20}"},{"bgUrl":"https://file.gongbangbang.com/resources/u/2019/04/24/0982752e19464698850d3a797d904699L3EiO1.png","catalogId":30,"catalogName":"手工具","id":8,"link":"gbb://search?k= {\"catalogueId\":30,\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"size\":20}"},{"bgUrl":"https://file.gongbangbang.com/resources/u/2019/04/24/28866d23027042f2be1abd325594eeb0CctCCo.png","catalogId":4,"catalogName":"动力工具","id":9,"link":"gbb://search?k= {\"catalogueId\":4,\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"size\":20}"},{"bgUrl":"https://file.gongbangbang.com/resources/u/2019/04/24/5fa68d941d7340dba9efdd9af4612bc3UgD4tR.png","catalogId":-1,"catalogName":"查看更多","id":10,"link":"gbb://search?k= {\"catalogueId\":-1,\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"size\":20}"}]
     * hotKeywords : [{"keyword":"安全鞋","link":"gbb://search?k= {\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"keyword\":\"安全鞋\",\"size\":20}"},{"keyword":"耳塞","link":"gbb://search?k= {\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"keyword\":\"耳塞\",\"size\":20}"},{"keyword":"角磨机","link":"gbb://search?k= {\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"keyword\":\"角磨机\",\"size\":20}"},{"keyword":"钻头","link":"gbb://search?k= {\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"keyword\":\"钻头\",\"size\":20}"},{"keyword":"口罩","link":"gbb://search?k= {\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"keyword\":\"口罩\",\"size\":20}"},{"keyword":"工具","link":"gbb://search?k= {\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"keyword\":\"工具\",\"size\":20}"},{"keyword":"手套","link":"gbb://search?k= {\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"keyword\":\"手套\",\"size\":20}"}]
     */

    private GoodsBean clearanceSales;
    private GoodsBean expertRecommends;
    private GoodsBean hotBrands;
    private List<BannersBean> banners;
    private List<FirstLevelListsBean> firstLevelLists;
    private List<HotCatalogsBean> hotCatalogs;
    private List<HotKeywordsBean> hotKeywords;

    public GoodsBean getClearanceSales() {
        return clearanceSales;
    }

    public void setClearanceSales(GoodsBean clearanceSales) {
        this.clearanceSales = clearanceSales;
    }

    public GoodsBean getExpertRecommends() {
        return expertRecommends;
    }

    public void setExpertRecommends(GoodsBean expertRecommends) {
        this.expertRecommends = expertRecommends;
    }

    public GoodsBean getHotBrands() {
        return hotBrands;
    }

    public void setHotBrands(GoodsBean hotBrands) {
        this.hotBrands = hotBrands;
    }

    public List<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannersBean> banners) {
        this.banners = banners;
    }

    public List<FirstLevelListsBean> getFirstLevelLists() {
        return firstLevelLists;
    }

    public void setFirstLevelLists(List<FirstLevelListsBean> firstLevelLists) {
        this.firstLevelLists = firstLevelLists;
    }

    public List<HotCatalogsBean> getHotCatalogs() {
        return hotCatalogs;
    }

    public void setHotCatalogs(List<HotCatalogsBean> hotCatalogs) {
        this.hotCatalogs = hotCatalogs;
    }

    public List<HotKeywordsBean> getHotKeywords() {
        return hotKeywords;
    }

    public void setHotKeywords(List<HotKeywordsBean> hotKeywords) {
        this.hotKeywords = hotKeywords;
    }

    public static class GoodsBean {
        /**
         * items : [{"bigPic":2,"brandBigLogo":"https://file.gongbangbang.com/resources/u/2019/04/24/6c0dcbb437964bf18b9dbdeb94afe7b0pErhH1.png","brandDesc":"创新 智能 高效","brandId":243,"brandName":"ABB","brandSmallLogo":"http://pathfinder-privateuat.oss-cn-hangzhou.aliyuncs.com/BRAND/LOGO_197547_01.jpg?x-oss-process=style/gongbangbang_style","id":1,"link":"gbb://search?k={\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"productFilter\":{\"brandIds\":[243]},\"size\":20}"},{"bigPic":2,"brandBigLogo":"https://file.gongbangbang.com/resources/u/2019/04/24/3502e8c222ec40acbb8177ded58522b5KUFmzJ.png","brandDesc":"节能高效 安全可靠","brandId":208,"brandName":"SCHNEIDER/施耐德","brandSmallLogo":"http://pathfinder-privateuat.oss-cn-hangzhou.aliyuncs.com/BRAND/LOGO_197512_01.jpg?x-oss-process=style/gongbangbang_style","id":2,"link":"gbb://search?k={\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"productFilter\":{\"brandIds\":[208]},\"size\":20}"},{"bigPic":1,"brandBigLogo":"https://file.gongbangbang.com/resources/u/2019/04/25/e804e45f65de46268c10d8488244ac1aYw9pHG.png","brandDesc":"安全耐用 精准易用","brandId":474,"brandName":"FLUKE/福禄克","brandSmallLogo":"http://pathfinder-privateuat.oss-cn-hangzhou.aliyuncs.com/BRAND/LOGO_197778_01.jpg?x-oss-process=style/gongbangbang_style","id":3,"link":"gbb://search?k={\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"productFilter\":{\"brandIds\":[474]},\"size\":20}"},{"bigPic":1,"brandBigLogo":"https://file.gongbangbang.com/resources/u/2019/04/24/ea1ac62b506e4641a9edbf910d660b66H8Gkzi.png","brandDesc":"科技 改善生活","brandId":240,"brandName":"3M","brandSmallLogo":"http://pathfinder-privateuat.oss-cn-hangzhou.aliyuncs.com/BRAND/LOGO_197544_01.jpg?x-oss-process=style/gongbangbang_style","id":4,"link":"gbb://search?k={\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"productFilter\":{\"brandIds\":[240]},\"size\":20}"},{"bigPic":1,"brandBigLogo":"https://file.gongbangbang.com/resources/u/2019/04/24/1addcccd794d4f5097c5e77e672e433boceEsV.png","brandDesc":"全球防护 本地服务","brandId":391,"brandName":"DELTA/代尔塔","brandSmallLogo":"http://pathfinder-privateuat.oss-cn-hangzhou.aliyuncs.com/Others/iH5w3jP8hp.png?x-oss-process=style/gongbangbang_style","id":5,"link":"gbb://search?k={\"channel\":2,\"clp\":true,\"from\":0,\"fz\":false,\"productFilter\":{\"brandIds\":[391]},\"size\":20}"}]
         * title : 精选品牌
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
             * bigPic : 2
             * brandBigLogo : https://file.gongbangbang.com/resources/u/2019/04/24/6c0dcbb437964bf18b9dbdeb94afe7b0pErhH1.png
             * brandDesc : 创新 智能 高效
             * brandId : 243
             * brandName : ABB
             * brandSmallLogo : http://pathfinder-privateuat.oss-cn-hangzhou.aliyuncs.com/BRAND/LOGO_197547_01.jpg?x-oss-process=style/gongbangbang_style
             * id : 1
             * link : gbb://search?k={"channel":2,"clp":true,"from":0,"fz":false,"productFilter":{"brandIds":[243]},"size":20}
             */

            private int bigPic;
            private String brandBigLogo;
            private String brandDesc;
            private int brandId;
            private String brandName;
            private String brandSmallLogo;
            private int id;
            private String link;

            public int getBigPic() {
                return bigPic;
            }

            public void setBigPic(int bigPic) {
                this.bigPic = bigPic;
            }

            public String getBrandBigLogo() {
                return brandBigLogo;
            }

            public void setBrandBigLogo(String brandBigLogo) {
                this.brandBigLogo = brandBigLogo;
            }

            public String getBrandDesc() {
                return brandDesc;
            }

            public void setBrandDesc(String brandDesc) {
                this.brandDesc = brandDesc;
            }

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

    public static class BannersBean {
        /**
         * id : 1
         * link : https://m.gongbangbang.com/bannerpage/hjjx201904/index.html
         * url : https://file.gongbangbang.com/resources/u/2019/04/26/5de424490cc14fe4acbad3ea23efd143ELtJSW.jpg
         */

        private int id;
        private String link;
        private String url;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class FirstLevelListsBean {
        /**
         * catalogId : 19
         * title : 电气
         */

        private int catalogId;
        private String title;

        public int getCatalogId() {
            return catalogId;
        }

        public void setCatalogId(int catalogId) {
            this.catalogId = catalogId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class HotCatalogsBean {
        /**
         * bgUrl : https://file.gongbangbang.com/resources/u/2019/04/24/5c52326ab6f3405b992f031d18a54d80qSpgaL.png
         * catalogId : 19
         * catalogName : 电气
         * id : 1
         * link : gbb://search?k= {"catalogueId":19,"channel":2,"clp":true,"from":0,"fz":false,"size":20}
         */

        private String bgUrl;
        private int catalogId;
        private String catalogName;
        private int id;
        private String link;

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
    }

    public static class HotKeywordsBean {
        /**
         * keyword : 安全鞋
         * link : gbb://search?k= {"channel":2,"clp":true,"from":0,"fz":false,"keyword":"安全鞋","size":20}
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
}

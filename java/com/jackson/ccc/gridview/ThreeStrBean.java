package com.jackson.ccc.gridview;

/**
 * Created by LXP on 17-4-17.
 */

public class ThreeStrBean {

    public String newsIconUrl;
    public String newsTitle;
    public String newsContent;

    public ThreeStrBean() {
    }

    public ThreeStrBean(String newsIconUrl, String newsTitle, String newsContent) {
        this.newsIconUrl = newsIconUrl;
        this.newsTitle = newsTitle;
        this.newsContent = newsContent;
    }

    public String getNewsIconUrl() {
        return newsIconUrl;
    }

    public void setNewsIconUrl(String newsIconUrl) {
        this.newsIconUrl = newsIconUrl;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }
}

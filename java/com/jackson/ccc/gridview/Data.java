package com.jackson.ccc.gridview;

/**
 * Created by LXP on 17-3-22.
 */

public class Data {
    private int imgId;
    private String content;

    public Data() {}

    public Data(int imgId, String content) {
        this.imgId = imgId;
        this.content = content;
    }

    public int getImgId() {
        return imgId;
    }

    public String getContent() {
        return content;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

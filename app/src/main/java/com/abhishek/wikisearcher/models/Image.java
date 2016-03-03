package com.abhishek.wikisearcher.models;

/**
 * Created by abhishek on 03/03/16 at 6:31 PM.
 */
public class Image {

    private int mPageId;
    private String mTitle;
    private String mThumbUrl;

    public Image(int pageId, String title, String thumbUrl) {
        mPageId = pageId;
        mTitle = title;
        mThumbUrl = thumbUrl;
    }

    public int getPageId() {
        return mPageId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getThumbUrl() {
        return mThumbUrl;
    }
}

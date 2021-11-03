package com.example.see.domain;

import java.io.Serializable;

public class Content implements Serializable {
    private Integer contentId;
    private String contentPath;
    private Integer contentBookId;
    private int contentNo;
    private String contentDesri;

    public Content() {
    }

    public Content(Integer contentId, String contentPath, Integer contentBookId, int contentNo, String contentDesri) {
        this.contentId = contentId;
        this.contentPath = contentPath;
        this.contentBookId = contentBookId;
        this.contentNo = contentNo;
        this.contentDesri = contentDesri;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public Integer getContentBookId() {
        return contentBookId;
    }

    public void setContentBookId(Integer contentBookId) {
        this.contentBookId = contentBookId;
    }

    public int getContentNo() {
        return contentNo;
    }

    public void setContentNo(int contentNo) {
        this.contentNo = contentNo;
    }

    public String getContentDesri() {
        return contentDesri;
    }

    public void setContentDesri(String contentDesri) {
        this.contentDesri = contentDesri;
    }

    @Override
    public String toString() {
        return "Content{" +
                "contentId=" + contentId +
                ", contentPath='" + contentPath + '\'' +
                ", contentBookId=" + contentBookId +
                ", contentNo=" + contentNo +
                ", contentDesri='" + contentDesri + '\'' +
                '}';
    }
}


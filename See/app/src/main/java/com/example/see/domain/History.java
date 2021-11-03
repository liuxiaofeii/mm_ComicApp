package com.example.see.domain;

import java.io.Serializable;

public class History implements Serializable {
    private Integer historyId;
    private Integer userId;
    private Integer contentId;


    public History() {
    }

    public History(Integer historyId, Integer userId, Integer contentId) {
        this.historyId = historyId;
        this.userId = userId;
        this.contentId = contentId;
    }

    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    @Override
    public String toString() {
        return "History{" +
                "historyId=" + historyId +
                ", userId=" + userId +
                ", contentId=" + contentId +
                '}';
    }
}

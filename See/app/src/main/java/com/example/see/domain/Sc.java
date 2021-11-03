package com.example.see.domain;

import java.io.Serializable;

public class Sc implements Serializable {
    private Integer ScId;
    private Integer userId;
    private Integer sc;

    public Sc() {
    }

    public Sc(Integer scId, Integer userId, Integer sc) {
        ScId = scId;
        this.userId = userId;
        this.sc = sc;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSc() {
        return sc;
    }

    public void setSc(Integer sc) {
        this.sc = sc;
    }

    public Integer getScId() {
        return ScId;
    }

    public void setScId(Integer scId) {
        ScId = scId;
    }

    @Override
    public String toString() {
        return "Sc{" +
                "ScId=" + ScId +
                ", userId=" + userId +
                ", sc=" + sc +
                '}';
    }
}

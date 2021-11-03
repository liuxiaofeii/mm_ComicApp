package com.example.see.domain;


import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 书籍类
 */
public class MiniBook implements Serializable {
    private Integer bookId;
    private String bookName;
    private String bookIntro;
    private String bookPhoto;
    private String bookLabel;
    private String bookFree;
    private double bookPrice;
    private String bookAuthor;
    private Integer bookReadNums;
    private String isDelete;
    private Integer scNum;
    private Integer historyReadContentId;
    private String dowPhoto;


    public MiniBook() {

    }

    public MiniBook(Integer bookId, String bookName, String bookIntro, String bookPhoto, String bookLabel, String bookFree, double bookPrice, String bookAuthor, Integer bookReadNums, String isDelete, Integer scNum, Integer historyReadContentId, String dowPhoto) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookIntro = bookIntro;
        this.bookPhoto = bookPhoto;
        this.bookLabel = bookLabel;
        this.bookFree = bookFree;
        this.bookPrice = bookPrice;
        this.bookAuthor = bookAuthor;
        this.bookReadNums = bookReadNums;
        this.isDelete = isDelete;
        this.scNum = scNum;
        this.historyReadContentId = historyReadContentId;
        this.dowPhoto = dowPhoto;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookIntro() {
        return bookIntro;
    }

    public void setBookIntro(String bookIntro) {
        this.bookIntro = bookIntro;
    }

    public String getBookPhoto() {
        return bookPhoto;
    }

    public void setBookPhoto(String bookPhoto) {
        this.bookPhoto = bookPhoto;
    }

    public String getBookLabel() {
        return bookLabel;
    }

    public void setBookLabel(String bookLabel) {
        this.bookLabel = bookLabel;
    }

    public String getBookFree() {
        return bookFree;
    }

    public void setBookFree(String bookFree) {
        this.bookFree = bookFree;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public Integer getBookReadNums() {
        return bookReadNums;
    }

    public void setBookReadNums(Integer bookReadNums) {
        this.bookReadNums = bookReadNums;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getScNum() {
        return scNum;
    }

    public void setScNum(Integer scNum) {
        this.scNum = scNum;
    }

    public Integer getHistoryReadContentId() {
        return historyReadContentId;
    }

    public void setHistoryReadContentId(Integer historyReadContentId) {
        this.historyReadContentId = historyReadContentId;
    }



    public String getDowPhoto() {
        return dowPhoto;
    }

    public void setDowPhoto(String dowPhoto) {
        this.dowPhoto = dowPhoto;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", bookIntro='" + bookIntro + '\'' +
                ", bookPhoto='" + bookPhoto + '\'' +
                ", bookLabel='" + bookLabel + '\'' +
                ", bookFree='" + bookFree + '\'' +
                ", bookPrice=" + bookPrice +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookReadNums=" + bookReadNums +
                ", isDelete='" + isDelete + '\'' +
                ", scNum=" + scNum +
                ", historyReadContentId=" + historyReadContentId +
                ", dowPhoto='" + dowPhoto + '\'' +
                '}';
    }
}


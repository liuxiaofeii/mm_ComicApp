package com.example.see;

import android.app.Application;
import com.example.see.dao.DBOpenHelper;
import com.example.see.domain.*;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private String myString="start";
    private DBOpenHelper dbOpenHelper;
    private User user;
    private List<Book> books;
    private List<Content> contents;
    private List<Sc> scs;
    private List<History> historys;
    private Thread thread1;
    private Thread thread2;


    public String getMyString() {
        return myString;

    }

    public void setMyString(String myString) {
        this.myString = myString;

    }

    public DBOpenHelper getDbOpenHelper() {
        return dbOpenHelper;
    }

    public void setDbOpenHelper(DBOpenHelper dbOpenHelper) {
        this.dbOpenHelper = dbOpenHelper;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Thread getThread1() {
        return thread1;
    }

    public void setThread1(Thread thread1) {
        this.thread1 = thread1;
    }

    public Thread getThread2() {
        return thread2;
    }

    public void setThread2(Thread thread2) {
        this.thread2 = thread2;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public List<Sc> getScs() {
        return scs;
    }

    public void setScs(List<Sc> scs) {
        this.scs = scs;
    }

    public List<History> getHistorys() {
        return historys;
    }

    public void setHistorys(List<History> historys) {
        this.historys = historys;
    }
}

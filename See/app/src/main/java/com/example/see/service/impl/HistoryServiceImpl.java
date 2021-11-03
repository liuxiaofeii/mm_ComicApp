package com.example.see.service.impl;

import android.content.ContentValues;
import android.database.Cursor;
import com.example.see.base.BaseServiceImpl;
import com.example.see.dao.DBOpenHelper;
import com.example.see.domain.History;
import com.example.see.domain.History;
import com.example.see.service.HistoryService;
import com.example.see.service.HistoryService;

import java.util.ArrayList;
import java.util.List;

public class HistoryServiceImpl extends BaseServiceImpl<History> implements HistoryService {
    @Override
    public DBOpenHelper setDBOpenHelper(DBOpenHelper db) {
        dbOpenHelper=db;
        return dbOpenHelper;
    }



    @Override
    public List<History> selectHistoryById(Integer id,String historysname){
        List<History> historylist=new ArrayList<>();
        String sql="select historyId as _id,userId,contentId from "+historysname+" where userId="+id;
        Cursor cursor=dbOpenHelper.query(sql,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            History history=new History();
            history.setHistoryId(cursor.getInt(0));
            history.setUserId(cursor.getInt(1));
            history.setContentId(cursor.getInt(2));
            historylist.add(history);
            cursor.moveToNext();
        }
        if(historylist==null||historylist.size()==0){
            return null;
        }else{
            return historylist;

        }

    }

    @Override
    public long insertOneHistory(History history,String historysname) {
        if(!dbOpenHelper.isTblExists(historysname)){
            dbOpenHelper.createHistoryTable(historysname);
        }
        Integer historyId=history.getHistoryId();
        Integer userId=history.getUserId();
        Integer contentId=history.getContentId();
        ContentValues cv=new ContentValues();
        cv.put("historyId",historyId);
        cv.put("userId",userId);
        cv.put("contentId",contentId);
        long row=dbOpenHelper.insertData(historysname,cv);
        if(row<0){
            updateHistoryById(history,historysname);

        }
        return row;

    }

    @Override
    public void createTable(String historysname) {
        if(!dbOpenHelper.isTblExists(historysname)){
            dbOpenHelper.createHistoryTable(historysname);
        }
    }

    @Override
    public void updateHistoryById(History history, String historysname) {
        Integer historyId=history.getHistoryId();
        Integer userId=history.getUserId();
        Integer contentId=history.getContentId();
        ContentValues cv=new ContentValues();
        cv.put("historyId",historyId);
        cv.put("userId",userId);
        cv.put("contentId",contentId);
        dbOpenHelper.update(historysname,cv,historyId);

    }
}

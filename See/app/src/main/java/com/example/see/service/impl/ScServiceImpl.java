package com.example.see.service.impl;

import android.content.ContentValues;
import android.database.Cursor;
import com.example.see.base.BaseServiceImpl;
import com.example.see.dao.DBOpenHelper;
import com.example.see.domain.Book;
import com.example.see.domain.Sc;
import com.example.see.domain.User;
import com.example.see.service.ScService;
import com.example.see.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class ScServiceImpl extends BaseServiceImpl<Sc> implements ScService {
    @Override
    public DBOpenHelper setDBOpenHelper(DBOpenHelper db) {
        dbOpenHelper=db;
        return dbOpenHelper;
    }



    @Override
    public List<Sc> selectScById(Integer id,String scsname){
        List<Sc> sclist=new ArrayList<>();
        String sql="select scId as _id,userId from "+scsname+" where userId="+id;
        Cursor cursor=dbOpenHelper.query(sql,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Sc sc=new Sc();
            sc.setScId(cursor.getInt(0));
            sc.setUserId(cursor.getInt(1));
            sclist.add(sc);
            cursor.moveToNext();
        }
        if(sclist==null||sclist.size()==0){
            return null;
        }else{
            return sclist;

        }

    }

    @Override
    public long insertOneSc(Sc sc,String scsname) {
        if(!dbOpenHelper.isTblExists(scsname)){
            dbOpenHelper.createScTable(scsname);
        }
        Integer scId=sc.getScId();
        Integer userId=sc.getUserId();
        ContentValues cv=new ContentValues();
        cv.put("scId",scId);
        cv.put("userId",userId);
        long row=dbOpenHelper.insertData(scsname,cv);
        return row;

    }

    @Override
    public void createTable(String scsname) {
        if(!dbOpenHelper.isTblExists(scsname)){
            dbOpenHelper.createScTable(scsname);
        }
    }
}

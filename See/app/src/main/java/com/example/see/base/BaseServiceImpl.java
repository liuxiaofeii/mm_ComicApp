package com.example.see.base;

import android.content.ContentValues;
import android.database.Cursor;
import com.example.see.dao.DBOpenHelper;
import com.example.see.domain.Book;

import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T>{
    public DBOpenHelper dbOpenHelper;
    public abstract DBOpenHelper setDBOpenHelper(DBOpenHelper db);
    @Override
    public List<T> listAll() {
        return null;
    }

    @Override
    public Cursor listAllBySql(String sql) {
        Cursor cursor=dbOpenHelper.query(sql,null);
        return cursor;
    }

    @Override
    public long insert(String tableName,ContentValues cv) {

        long row = dbOpenHelper.insertData(tableName,cv);
        return row;
    }
}

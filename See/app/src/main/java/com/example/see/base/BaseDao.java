package com.example.see.base;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

public interface BaseDao<T> {
    //列出所有内容
    public List<T> listAll();
    //根据sql查询内容
    public Cursor listAllBySql(String sql);

    //插入操作
    public long insert(String tableName, ContentValues cv);

}

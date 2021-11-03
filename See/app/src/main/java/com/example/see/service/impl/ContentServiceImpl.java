package com.example.see.service.impl;

import android.database.Cursor;
import com.example.see.base.BaseService;
import com.example.see.base.BaseServiceImpl;
import com.example.see.dao.DBOpenHelper;
import com.example.see.service.ContentService;
import com.example.see.domain.Content;

import java.util.List;

public class ContentServiceImpl extends BaseServiceImpl<Content> implements ContentService {

    @Override
    public DBOpenHelper setDBOpenHelper(DBOpenHelper db) {
        dbOpenHelper=db;
        return dbOpenHelper;
    }
}

package com.example.see.service.impl;

import android.content.ContentValues;
import android.database.Cursor;
import com.example.see.base.BaseServiceImpl;
import com.example.see.dao.DBOpenHelper;
import com.example.see.service.UserService;
import com.example.see.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Override
    public DBOpenHelper setDBOpenHelper(DBOpenHelper db) {
        dbOpenHelper=db;
        return dbOpenHelper;
    }

    @Override
    public User selectByUserName(String username,String password) {
        String sql="select userId as _id,userName,password,phone,email,sc,history from users where userName=? and password=?";
        Cursor cursor=dbOpenHelper.selectByArgs(sql,new String[]{username,password});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            User user=new User();
            user.setUserId(cursor.getInt(0));
            user.setUserName(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setPhone(cursor.getString(3));
            user.setEmail(cursor.getString(4));
            user.setSc(cursor.getString(5));
            user.setHistory(cursor.getString(6));
            return user;
        }



        return null;
    }

    @Override
    public long insertOneUser(User user) {
        String username=user.getUserName();
        String password=user.getPassword();
        String email=user.getEmail();
        String phone=user.getPhone();
        String history=user.getHistory();
        String sc=user.getSc();
        ContentValues cv=new ContentValues();
        cv.put("username",username);
        cv.put("password",password);
        cv.put("email",email);
        cv.put("phone",phone);
        cv.put("sc",sc);
        cv.put("history",history);
        long row=dbOpenHelper.insertData("users",cv);
        return row;
    }

}

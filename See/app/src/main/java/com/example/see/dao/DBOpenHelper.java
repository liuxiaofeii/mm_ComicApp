package com.example.see.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import com.example.see.R;
import com.example.see.domain.Content;

import java.io.*;

public class DBOpenHelper{
    private static final int BUFFER_SIZE=400000;
    public static final String PACKAGE_NAME="com.example.see";
    public static final String DB_PATH="/data"+ Environment.getDataDirectory().getAbsolutePath()+"/"+PACKAGE_NAME+"/databases";
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public DBOpenHelper(Context context){
        this.context=context;
    }
    public SQLiteDatabase openDatabase(String DB_NAME){
        //System.out.println(DB_PATH);// /data/data/com.example.see/databases
        InputStream is=null;
        FileOutputStream fo=null;
        String DB_File=null;

        try {
            File myDataPath=new File(context.getExternalFilesDir(null),"data");
            //File myDataPath = new File(DB_PATH);
            if(!myDataPath.exists()){
                myDataPath.mkdirs();
            }
            DB_File=myDataPath+"/"+DB_NAME;
            if(!new File(DB_File).exists()){
                is=context.getResources().openRawResource(R.raw.test);
                System.out.println(is);
                fo=new FileOutputStream(DB_File);
                System.out.println(fo);
                byte[] buffer=new byte[BUFFER_SIZE];
                int count=0;
                while((count=is.read(buffer))>0){
                    fo.write(buffer,0,count);
                }
            }
            sqLiteDatabase=SQLiteDatabase.openOrCreateDatabase(DB_File,null);
            return sqLiteDatabase;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                fo.close();
                is.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }


        return null;
    }

    public Cursor select(String tableName){
        System.out.println(sqLiteDatabase);
        Cursor cursor=sqLiteDatabase.query(tableName,null,null,null,null,null,null);
        return cursor;
    }

    //根据sql查询
    public Cursor query(String sql,String[] args){
        Cursor cursor=sqLiteDatabase.rawQuery(sql,args);
        return cursor;
    }
    //根据参数查询
    //根据sql查询
    public Cursor selectByArgs(String sql,String[] args){
        Cursor cursor=sqLiteDatabase.rawQuery(sql,args);
        return cursor;
    }

    public boolean isSql(String sql,String[] args){
        Cursor cursor=sqLiteDatabase.rawQuery(sql,args);
        int n=cursor.getCount();
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public long insertData(String tableName, ContentValues cv){
        long row=sqLiteDatabase.insert(tableName,null,cv);
        return row;
    }

    public boolean isTblExists(String tblName)
    {
        String sql ="select * from sqlite_master where name = ?;";
        boolean res=isSql(sql,new String[]{tblName});
        if(!isSql(sql,new String[]{tblName}))
        {
            return false;
        }
        return true;    // 判断是否存在
    }

    public void createScTable(String TABLE_NAME) {
        String sql = "CREATE TABLE " + TABLE_NAME
                + "(scId INTEGER NOT NULL,"
                + " userId integer,"
                + " PRIMARY KEY (scId))";
        sqLiteDatabase.execSQL(sql);
    }

    public void createHistoryTable(String TABLE_NAME) {
        String sql = "CREATE TABLE " + TABLE_NAME
                + "(historyId INTEGER NOT NULL,"
                + " userId integer,"
                +" contentId integer,"
                + " PRIMARY KEY (historyId))";
        sqLiteDatabase.execSQL(sql);
    }

    public void createContentTable(String TABLE_NAME) {
        String sql = "CREATE TABLE " + TABLE_NAME
                + "(contentId INTEGER NOT NULL,"
                + " contentPath VARCHAR(1024),"
                +" contentNO INT,"
                + " PRIMARY KEY (contentId))";
        sqLiteDatabase.execSQL(sql);
    }

    public void update(String TABLE_NAME, ContentValues cv,Integer id){
        String where = "historyId = ?";
        String[] whereValue = { Integer.toString(id) };
        sqLiteDatabase.update(TABLE_NAME, cv, where, whereValue);
    }
    public void close(){
        sqLiteDatabase.close();
    }



}

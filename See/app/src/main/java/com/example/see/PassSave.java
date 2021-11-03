package com.example.see;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PassSave extends Application {
    SharedPreferences sharedPre; //数据存储
    SharedPreferences.Editor editor; //创建SharedPreferences.Editor对象接口


    @Override
    public void onCreate() {
        super.onCreate(  );
        sharedPre= PreferenceManager.getDefaultSharedPreferences( this );
        editor=sharedPre.edit();

    }
    public String getUserPassword() {return sharedPre.getString("pass","");}

    public void setUserPassword(String strPass) {
        if (editor!=null){
            editor.putString("pass",strPass);
            editor.commit();
        }
    }
}


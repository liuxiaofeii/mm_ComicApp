package com.example.see;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import com.example.see.MainActivity;
import com.example.see.util.Consts;

import java.lang.ref.WeakReference;

public class MyHandler extends Handler {
    WeakReference<MainActivity> mactivity;

    //构造函数，传来的是外部类的this
    public MyHandler(@NonNull Looper looper, MainActivity activity){
        super(looper);//调用父类的显式指明的构造函数
        mactivity = new WeakReference<MainActivity>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        MainActivity nactivity=mactivity.get();
        if(nactivity == null) {
            return;
        }//avtivity都没了还处理个XXX

        switch (msg.what) {
            case 0:
                //在这里通过nactivity引用外部类
                nactivity.m_var=0;
                break;
            case 1:
                Consts.HANDLER_MSG1="ok";
                break;
            case 2:
                Consts.HANDLER_MSG2="ok";
                break;
            default:
                break;
        }
    }
}
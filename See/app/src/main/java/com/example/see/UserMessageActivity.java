package com.example.see;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.example.see.dao.DBOpenHelper;
import com.example.see.domain.User;


public class UserMessageActivity extends FragmentActivity {

    MyApplication myApplication;
    private DBOpenHelper dbOpenHelper=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("hello message");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_message);
        if(dbOpenHelper!=null){
            dbOpenHelper.close();
        }
        myApplication= (MyApplication) getApplication();
        User user=myApplication.getUser();
        TextView username_message=findViewById(R.id.username_message);
        TextView email_message=findViewById(R.id.email_message);
        TextView phone_message=findViewById(R.id.phone_message);

        username_message.setText(user.getUserName());
        email_message.setText(user.getEmail());
        phone_message.setText(user.getPhone());

        ImageView backhome=findViewById(R.id.backmine);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Title back","key down");

                finish();
            }
        });




    }


}

package com.example.see;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.see.dao.DBOpenHelper;
import com.example.see.domain.User;
import com.example.see.service.impl.HistoryServiceImpl;
import com.example.see.service.impl.ScServiceImpl;
import com.example.see.service.impl.UserServiceImpl;
import com.example.see.ui.login.LoginFragment;
import com.example.see.ui.register.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {
    EditText editname, editpass;
    Button btn1,btn2;
    String strPlayer="",strPassword="";
    DBOpenHelper dbOpenHelper;
    MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        View view;
        System.out.println("我是register的fragment");
        if(dbOpenHelper!=null){
            dbOpenHelper.close();
        }
        myApplication= (MyApplication) getApplication();
        DBOpenHelper dbOpenHelper=new DBOpenHelper(this);
        dbOpenHelper.openDatabase("test.db");
        EditText usernameE=findViewById(R.id.username_register);
        EditText passwordE=findViewById(R.id.password_register);
        EditText emailE=findViewById(R.id.email_register);
        EditText phoneE=findViewById(R.id.phone_register);




        Button registerB=findViewById(R.id.register);
        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=usernameE.getText().toString();
                String password=passwordE.getText().toString();
                String email=emailE.getText().toString();
                String phone=phoneE.getText().toString();
                if((username.equals(null)||password.equals(null)||email.equals(null)||phone.equals(null)||("").equals(username)||("").equals(password)||("").equals(email)||("").equals(phone))){
                    Toast.makeText(RegisterActivity.this, "请填好全部信息", Toast.LENGTH_SHORT).show();
                }else {
                    User user = new User();
                    user.setUserName(username);
                    user.setPassword(password);
                    user.setEmail(email);
                    user.setPhone(phone);
                    user.setSc(username + "scs");
                    user.setHistory(username + "historys");
                    UserServiceImpl userService = new UserServiceImpl();
                    userService.setDBOpenHelper(dbOpenHelper);
                    long row = userService.insertOneUser(user);
                    if (row > 0) {
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        ScServiceImpl scService=new ScServiceImpl();
                        scService.setDBOpenHelper(dbOpenHelper);
                        scService.createTable(user.getSc());

                        HistoryServiceImpl historyService=new HistoryServiceImpl();
                        historyService.setDBOpenHelper(dbOpenHelper);
                        historyService.createTable(user.getHistory());

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this, "注册失败，请重写注册", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Button cancel=findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

//    public class OnClickListener implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.register: {
//                    String strname = editname.getText().toString();
//                    String strpass = editpass.getText().toString();
//
//                    if (strname.equals( strPlayer ) && strpass.equals( strPassword )) {
//
//                        Toast.makeText( RegisterActivity.this, "账号或密码错误！请重新注册！", Toast.LENGTH_LONG ).show();
//
//                    } else {
//                        Toast.makeText(RegisterActivity.this, "用户注册成功!", Toast.LENGTH_LONG).show();
//
//                    }
//
//                    break;
//                }
//                case R.id.button7:{
//                    finish();
//                    break;
//                }
//
//
//
//            }
//        }
//    }
}

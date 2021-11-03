package com.example.see;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.see.dao.DBOpenHelper;
import com.example.see.domain.User;
import com.example.see.service.impl.UserServiceImpl;

public class LoginActivity extends AppCompatActivity {
    //声明控件类对象
    PassSave app;
    Button login_button;
    EditText username, password;
    TextView register_href;
    CheckBox checkPass;
    String usernameV = "";
    String passwordV = "";
    MyApplication myApplication;
    UserServiceImpl userService=new UserServiceImpl();
    DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        System.out.println("我是login的activity");
        if(dbOpenHelper!=null){
            dbOpenHelper.close();
        }
        myApplication= (MyApplication) getApplication();
        DBOpenHelper dbOpenHelper=new DBOpenHelper(this);
        dbOpenHelper.openDatabase("test.db");

        userService.setDBOpenHelper(dbOpenHelper);


        //获取指定id控件，并关联到控件类对象上
        login_button = (Button) findViewById(R.id.login_button);
//        txt0 = (TextView) findViewById(R.id.textView);
//        txt1 = (TextView) findViewById(R.id.textView7);
//        txt2 = (TextView) findViewById(R.id.textView9);
        register_href = (TextView) findViewById(R.id.register_href);
        checkPass = (CheckBox) findViewById(R.id.checkBox);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());

//声明监听器类对象，并为按钮和文本控件绑定监听器
        OnBtnClickListener listener = new OnBtnClickListener();
        login_button.setOnClickListener(listener);
        OnTxtClickListener listener1 = new OnTxtClickListener();
//        txt0.setOnClickListener(listener1);
//        txt1.setOnClickListener(listener1);
//        txt2.setOnClickListener(listener1);
        register_href.setOnClickListener(listener1);
        MyCheckedChangeListener checkListener = new MyCheckedChangeListener();
        checkPass.setOnCheckedChangeListener(checkListener);
        //获取app的实例


    }
    //监听器类：监听响应按钮控件的点击事件，并作出响应
    public class OnBtnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String usernameV = username.getText().toString();
            String passwordV = password.getText().toString();
            System.out.println(("").equals(usernameV));
            System.out.println(("").equals(passwordV));
            System.out.println(usernameV.equals(null));
            System.out.println(passwordV.equals(null));
            if(!(usernameV.equals(null)||passwordV.equals(null)||("").equals(usernameV)||("").equals(passwordV))){
                User user=userService.selectByUserName(usernameV,passwordV);
                if(user!=null) {
                    myApplication.setUser(user);
                    Intent it = new Intent(LoginActivity.this, MainActivity.class);
                    it.putExtra("user", user);
                    startActivity(it);
                }else{
                    Toast.makeText( LoginActivity.this, "账号或密码错误！请重新登陆！", Toast.LENGTH_LONG ).show();
                }

            } else {
                Toast.makeText( LoginActivity.this, "请填好全部信息", Toast.LENGTH_LONG ).show();

            }


        }

    }
    //监听器类：监听响应文本框控件的点击事件，并作出响应
    public class OnTxtClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//                case R.id.textView7: {
//                    Intent it = new Intent();
//                    it.setAction( Intent.ACTION_VIEW );
//                    it.setData( Uri.parse( "http://www.baidu.com" ) );
//                    startActivity( it );
//                    break;
//                }
//                case R.id.textView:{
//                    Intent it = new Intent(LoginActivity.this,MainActivity.class);
//                    startActivity(it);
//                    break;
//                }
//
                case R.id.register_href: {
                    Intent it = new Intent(LoginActivity.this,RegisterActivity.class);
                    startActivity( it );
                    break;
                }
               
                

            }
        }


    }
    //为当前Activity添加选项菜单项
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,1,1,"关于");
        menu.add(1,2,2,"退出");
        return super.onCreateOptionsMenu(menu);
    }
    //实现菜单项点击事件响应
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==1){
            Dialog dlg=new AlertDialog.Builder(this).setTitle("关于APP").setMessage("作者：第四组")
                    .setPositiveButton("点赞", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setNegativeButton("无情拒绝", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();
            dlg.show();
        } else if (item.getItemId()==2){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public class MyCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.checkBox: {
                    if (isChecked) {
                        passwordV= password.getText().toString();
                    } else {
                        passwordV = "";
                    }
                    break;
                }
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();


    }
}


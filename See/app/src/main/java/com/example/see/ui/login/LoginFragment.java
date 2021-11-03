package com.example.see.ui.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.example.see.*;
import com.example.see.dao.DBOpenHelper;
import com.example.see.domain.User;
import com.example.see.service.impl.UserServiceImpl;
import com.example.see.ui.notifications.NotificationsViewModel;
import com.example.see.ui.register.RegisterFragment;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LoginFragment extends Fragment {
    private LoginViewModel loginViewModel;
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
    private DBOpenHelper dbOpenHelper=null;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view;
        System.out.println("我是login的fragment");
        if(dbOpenHelper!=null){
            dbOpenHelper.close();
        }

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        view = inflater.inflate(R.layout.fragment_login, container, false);
        myApplication= (MyApplication) getActivity().getApplication();


        myApplication= (MyApplication)getActivity().getApplication();

        DBOpenHelper dbOpenHelper=new DBOpenHelper(getActivity());
        dbOpenHelper.openDatabase("test.db");

        userService.setDBOpenHelper(dbOpenHelper);


        //获取指定id控件，并关联到控件类对象上
        login_button = (Button) view.findViewById(R.id.login_button);
//        txt0 = (TextView) findViewById(R.id.textView);
//        txt1 = (TextView) findViewById(R.id.textView7);
//        txt2 = (TextView) findViewById(R.id.textView9);
        register_href = (TextView) view.findViewById(R.id.register_href);
        checkPass = (CheckBox) view.findViewById(R.id.checkBox);
        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
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
        return view;

    }
    //监听器类：监听响应按钮控件的点击事件，并作出响应
    public class OnBtnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String usernameV = username.getText().toString();
            String passwordV = password.getText().toString();

            if(!(username==null||password==null||("").equals(username)||("").equals(password))) {
                User user = userService.selectByUserName(usernameV, passwordV);

                if(user!=null) {

                    myApplication.setUser(user);
                    Intent it = new Intent();
                    it.putExtra("user", user);
                    it.setClass(getContext(), MainActivity.class);
                    startActivity(it);
                }
            } else {
                Toast.makeText( getContext(), "账号或密码错误！请重新登陆！", Toast.LENGTH_LONG ).show();

            }


        }

    }
    //监听器类：监听响应文本框控件的点击事件，并作出响应
    public class OnTxtClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
            FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
            switch (v.getId()) {
                case R.id.register_href: {
                    ft.replace(R.id.nav_host_fragment,new RegisterFragment());
                    ft.commit();
                    break;
                }
//                case R.id.textView:{
//                    Intent it = new Intent(LoginActivity.this,MainActivity.class);
//                    startActivity(it);
//                    break;
//                }
//
//                case R.id.textView9: {
//                    Intent it = new Intent();
//                    it.setAction( Intent.ACTION_DIAL );
//                    it.setData( Uri.parse( "tel://18816742878" ) );
//                    startActivity( it );
//                    break;
//                }


            }
        }


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

}

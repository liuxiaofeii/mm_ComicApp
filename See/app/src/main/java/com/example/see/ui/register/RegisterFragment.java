package com.example.see.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.example.see.*;
import com.example.see.dao.DBOpenHelper;
import com.example.see.domain.User;
import com.example.see.service.impl.ScServiceImpl;
import com.example.see.service.impl.UserServiceImpl;
import com.example.see.ui.login.LoginFragment;
import com.example.see.ui.login.LoginViewModel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RegisterFragment extends Fragment {
    private RegisterViewModel registerViewModel;
    private DBOpenHelper dbOpenHelper=null;
    //声明控件类对象
    MyApplication myApplication;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view;
        System.out.println("我是register的fragment");
        if(dbOpenHelper!=null){
            dbOpenHelper.close();
        }
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        view = inflater.inflate(R.layout.fragment_register, container, false);
        myApplication= (MyApplication) getActivity().getApplication();
        DBOpenHelper dbOpenHelper=new DBOpenHelper(getActivity());
        dbOpenHelper.openDatabase("test.db");
        EditText usernameE=view.findViewById(R.id.username_register);
        EditText passwordE=view.findViewById(R.id.password_register);
        EditText emailE=view.findViewById(R.id.email_register);
        EditText phoneE=view.findViewById(R.id.phone_register);




        Button registerB=view.findViewById(R.id.register);
        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=usernameE.getText().toString();
                String password=passwordE.getText().toString();
                String email=emailE.getText().toString();
                String phone=phoneE.getText().toString();
                if(!(username==null||password==null||email==null||phone==null||("").equals(username)||("").equals(password)||("").equals(email)||("").equals(phone))) {
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
                        Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT).show();
                        ScServiceImpl scService=new ScServiceImpl();
                        scService.setDBOpenHelper(dbOpenHelper);
                        scService.createTable(user.getSc());
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new LoginFragment()).commit();
                    } else {
                        Toast.makeText(getActivity(), "注册失败，请重写注册", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "注册失败，请重写注册", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button cancel=view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new RegisterFragment()).commit();
            }
        });

        return view;

    }


}

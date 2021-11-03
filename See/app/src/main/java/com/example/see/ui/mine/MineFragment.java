package com.example.see.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.see.*;

import com.example.see.adapter.BookAdapter;
import com.example.see.dao.DBOpenHelper;
import com.example.see.domain.Book;
import com.example.see.domain.User;
import com.example.see.service.BookService;
import com.example.see.service.impl.BookServiceImpl;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineFragment extends Fragment {


    private MineViewModel homeViewModel;
    private DBOpenHelper dbOpenHelper=null;
    MyApplication myApplication;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if(dbOpenHelper!=null){
            dbOpenHelper.close();
        }
        homeViewModel = new ViewModelProvider(this).get(MineViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        myApplication= (MyApplication) getActivity().getApplication();
        User user=myApplication.getUser();

        TextView login_username=root.findViewById(R.id.login_username);
        TextView login_email=root.findViewById(R.id.login_email);
        TextView history_text=root.findViewById(R.id.history_text);
        TextView sc_text=root.findViewById(R.id.sc_text);
        TextView userMessage_text=root.findViewById(R.id.userMessage_text);
        TextView quit_text=root.findViewById(R.id.quit_text);
        login_username.setText(user.getUserName());
        login_email.setText(user.getEmail());


        userMessage_text.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(MineActivity.this, "我是个人信息...", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getActivity(), UserMessageActivity.class);
                        startActivity(intent);

                    }
                }
        );
        sc_text.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(),ScActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                    }
                }
        );
        return root;

    }





}






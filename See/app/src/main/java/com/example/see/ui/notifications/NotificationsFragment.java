package com.example.see.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.see.*;
import com.example.see.dao.DBOpenHelper;
import com.example.see.domain.History;
import com.example.see.domain.User;
import com.example.see.ui.home.HomeFragment;
import com.example.see.ui.login.LoginFragment;
import com.example.see.ui.register.RegisterFragment;

import java.util.List;

public class NotificationsFragment extends Fragment {

    private static final String TAG = "";
    private NotificationsViewModel notificationsViewModel;
    private  onTxtClickListener listener;
    MyApplication myApplication;
    private DBOpenHelper dbOpenHelper=null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        System.out.println("hello 啊5");
        View view;
        if(dbOpenHelper!=null){
            dbOpenHelper.close();
        }
        myApplication= (MyApplication) getActivity().getApplication();
        User user=myApplication.getUser();
        if(user==null){
            view = inflater.inflate(R.layout.fragment_login, container, false);
            Toast.makeText(getActivity(), "请先登录...", Toast.LENGTH_SHORT).show();
//            List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new LoginFragment()).commit();
//            System.out.println(fragments);
//            LoginFragment loginFragment=new LoginFragment();
//            getActivity().getSupportFragmentManager().beginTransaction().show(loginFragment).commit();

//            Intent intent=new Intent(getActivity(), LoginFragment.class);
//////            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
////           // startActivityForResult(intent,2);
//            startActivity(intent);
//
//            return view;
        }else {

            notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
            view = inflater.inflate(R.layout.fragment_notifications, container, false);
            myApplication= (MyApplication) getActivity().getApplication();

            TextView login_username=view.findViewById(R.id.login_username);
            TextView login_email=view.findViewById(R.id.login_email);
            TextView history_text=view.findViewById(R.id.history_text);
            TextView sc_text=view.findViewById(R.id.sc_text);
            TextView userMessage_text=view.findViewById(R.id.userMessage_text);
            TextView quit_text=view.findViewById(R.id.quit_text);
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
            quit_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getActivity(), "我是退出...", Toast.LENGTH_SHORT).show();
                    myApplication.setUser(null);
//                    Intent intent=new Intent(getActivity(),MainActivity.class);
//                    startActivity(intent);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment()).commit();
                }
            });
            sc_text.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(getActivity(), "我是收藏...", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getActivity(),ScActivity.class);
                            intent.putExtra("user",user);
                            startActivity(intent);
                        }
                    }
            );
            history_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getActivity(), "我是历史记录...", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getActivity(), HistoryActivity.class);
                    intent.putExtra("user",user);
                    startActivity(intent);

                }
            });



//            Intent intent2=new Intent(getActivity(),MineActivity.class);
//            startActivity(intent2);
//            view.findViewById(R.id.history_text).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    System.out.println("hello 啊1");
//                    if (listener != null) {
//                        listener.onClick(view);
//                    }
//                }
//
//            });

        }
        return view;
    }
    public interface onTxtClickListener {
        void onClick(View view);

    }
    public  void setOnTxtClickListener(onTxtClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onPause() {
        Log.e(TAG, "onStart: 我暂停了" );
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e(TAG, "onStart: 我停止了" );
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.e(TAG, "onStart: 我销毁了view" );
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onStart: 我销毁了" );
        super.onDestroy();
    }
}

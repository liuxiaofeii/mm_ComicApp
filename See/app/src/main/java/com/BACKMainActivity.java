package com;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.see.MyApplication;
import com.example.see.R;
import com.example.see.ui.home.HomeFragment;
import com.example.see.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class BACKMainActivity extends AppCompatActivity {
    private static final String TAG ="" ;
    //创建碎片管理器
    private FragmentManager manager;//碎片管理器
    //创建三个碎片的对象
    private HomeFragment mHomeFragment;
    private NotificationsFragment mNotificationsFragment;
    MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Application user=getApplication();
        //AppTool.RequestPermissions(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

//        //保存历史记录，判断SDcard是否存在并且可读写
//        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            System.out.println("路上"+Environment.getExternalStorageDirectory().getAbsolutePath());
//            System.out.println("成功！");
//        }else{
//            System.out.println("失败");
//        }

        //File Folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "data");
//        System.out.println(getExternalFilesDir(null));
//        File Folder=new File(getExternalFilesDir(null),"data");
//        if (!Folder.exists())
//        {
//            boolean mkdirs = Folder.mkdirs();
//            if (!mkdirs) {
//                Log.e("TAG", "文件夹创建失败");
//            } else {
//                Log.e("TAG", "文件夹创建成功");
//            }
//        }
//        InputStream is=null;
//        try {
//            is=getResources().openRawResource(R.raw.dao);
//            ReadDaoProperties.readProperties(is);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                is.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,  R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        System.out.println("hello 啊4");

        NavigationUI.setupWithNavController(navView, navController);//问题语句
    }


    //根据ID选择碎片的方法
    private void selectFragment(int ID){
        //创建碎片事务管理  每一次碎片的显示与隐藏都要通过事务管理来操作
        FragmentTransaction ft=manager.beginTransaction();
        //操作：碎片为空 创建添加
        //     碎片不为空，直接显示
        switch (ID) {
            case R.id.navigation_home:
                if(mHomeFragment==null){//为空，创建
                    mHomeFragment=new HomeFragment();//创建
                    ft.add(R.id.nav_host_fragment, mHomeFragment);//将碎片添加到专门存放碎片的容器中
                }else{
                    ft.show(mHomeFragment);//不为空，直接显示
                }
                break;

            case R.id.navigation_notifications:
                if(mNotificationsFragment==null){
                    mNotificationsFragment=new NotificationsFragment();
                    ft.add(R.id.nav_host_fragment, mNotificationsFragment);
                }else{
                    ft.show(mHomeFragment);
                }
                break;


        }
        ft.commit();//提交   一定一定要提交
    }
//    //隐藏所有碎片的饿方法
//    private void goneFragment(){
//        FragmentTransaction ft=manager.beginTransaction();
//        if(mHomeFragment!=null){
//            ft.hide(mHomeFragment);
//        }
//        if(mTJFragment!=null){
//            ft.hide(mTJFragment);
//        }
//        if(mJPFragment!=null){
//            ft.hide(mJPFragment);
//        }
//        ft.commit();
//    }
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
    @Override
    protected void onStart() {
        Log.e(TAG, "onStart: 我开始了");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.e(TAG, "onStart: 我开始了" );
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            // SearchAddressInfo info = (SearchAddressInfo) data.getParcelableExtra("position");
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            System.out.println(fragments);
//            String position = data.getStringExtra("position");
//            LoginFragment loginFragment=new LoginFragment();
//            getSupportFragmentManager().beginTransaction().hide(R.id.navigation_home)
        }

    }
}




package com.example.see;
import android.Manifest;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.*;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.see.dao.DBOpenHelper;
import com.example.see.domain.Book;
import com.example.see.domain.History;
import com.example.see.domain.Sc;
import com.example.see.domain.User;
import com.example.see.service.BookService;
import com.example.see.service.impl.BookServiceImpl;
import com.example.see.service.impl.HistoryServiceImpl;
import com.example.see.service.impl.ScServiceImpl;
import com.example.see.ui.home.HomeFragment;
import com.example.see.ui.login.LoginFragment;
import com.example.see.ui.notifications.NotificationsFragment;
import com.example.see.util.AppTool;
import com.example.see.util.Consts;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.*;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="" ;
    //创建碎片管理器
    private FragmentManager manager;//碎片管理器
    //创建三个碎片的对象
    private HomeFragment mHomeFragment;
    private NotificationsFragment mNotificationsFragment;
    MyApplication myApplication;
    List<Book> books=null;
    private URL murl;
    public int m_var; //需要在消息处理中访问的成员变量，一定要声明成public
    List<Book> dowBooks=new ArrayList<>();
    Thread mthread;
    MyHandler mhandler = new MyHandler(Looper.myLooper(),this);
    DBOpenHelper dbOpenHelper;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        int id = intent.getIntExtra("flag", 0);
        if (id == 8) {
            //fragment的切换采用的是viewpage的形式,然后1是指底部第2个Fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new LoginFragment());
        }
        myApplication= (MyApplication) getApplication();
        user=myApplication.getUser();
        DBOpenHelper dbOpenHelper = new DBOpenHelper(this);
        dbOpenHelper.openDatabase("test.db");
        if(books==null) {


            BookService bookService = new BookServiceImpl();
            ((BookServiceImpl) bookService).setDBOpenHelper(dbOpenHelper);
            String sql = "select bookId as _id,bookName,bookPhoto,bookAuthor from books";
            Cursor cursor = bookService.listAllBySql(sql);
            books = getBooks(cursor);




            myApplication.setBooks(books);


            imaLoading();
        }
        dbOpenHelper.close();
        loadscandhistory();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
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
                    R.id.navigation_home, R.id.navigation_notifications)
                    .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        System.out.println("hello 啊4");

        NavigationUI.setupWithNavController(navView, navController);//问题语句
    }





//    //根据ID选择碎片的方法
//    private void selectFragment(int ID){
//        //创建碎片事务管理  每一次碎片的显示与隐藏都要通过事务管理来操作
//        FragmentTransaction ft=manager.beginTransaction();
//        //操作：碎片为空 创建添加
//        //     碎片不为空，直接显示
//        switch (ID) {
//            case R.id.navigation_home:
//                if(mHomeFragment==null){//为空，创建
//                    mHomeFragment=new HomeFragment();//创建
//                    ft.add(R.id.nav_host_fragment, mHomeFragment);//将碎片添加到专门存放碎片的容器中
//                }else{
//                    ft.show(mHomeFragment);//不为空，直接显示
//                }
//                break;
//
//            case R.id.navigation_notifications:
//                if(mNotificationsFragment==null){
//                    mNotificationsFragment=new NotificationsFragment();
//                    ft.add(R.id.nav_host_fragment, mNotificationsFragment);
//                }else{
//                    ft.show(mHomeFragment);
//                }
//                break;
//
//
//        }
//        ft.commit();//提交   一定一定要提交
//    }
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
        Log.e(TAG, "onstop: 我暂停了" );
        mthread.interrupt();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "我销毁了" );
        mthread.interrupt();
        super.onDestroy();
    }

    public List<Book> getBooks(Cursor cursor){
        List<Book> coll = new ArrayList<>();
        Book book;

        cursor.moveToFirst();  // 重中之重，千万不能忘了
        while(!cursor.isAfterLast()){
            book=new Book();
            book.setBookId(cursor.getInt(0));
            book.setBookName(cursor.getString(1));
            book.setBookPhoto(cursor.getString(2));
            book.setBookAuthor(cursor.getString(3));
            coll.add(book);
            cursor.moveToNext();
        }
        return coll;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 2 && resultCode == RESULT_OK) {
//            // SearchAddressInfo info = (SearchAddressInfo) data.getParcelableExtra("position");
//            List<Fragment> fragments = getSupportFragmentManager().getFragments();
//            System.out.println(fragments);
////            String position = data.getStringExtra("position");
////            LoginFragment loginFragment=new LoginFragment();
////            getSupportFragmentManager().beginTransaction().hide(R.id.navigation_home)
//        }
//
//    }


    public void imaLoading() {
     mthread=new Thread(
             new Runnable() {

      @Override
      public void run() {
              Log.e("", "我是thread头部");
              URL url = null;
              HttpURLConnection con = null;
              FileOutputStream fos = null;
              InputStream in = null;
              int i=0;
              for (Book book : books) {
                  Log.e("","mailifan888888"+i++);
                  try {
                      // 构造URL
                      url = new URL(Consts.PHOTO_BASE_URI + book.getBookPhoto());
                      // 打开连接
                      con = (HttpURLConnection) url.openConnection();
                      //请求方式
                      con.setRequestMethod("GET");
                      //设置超时时间
                      con.setReadTimeout(5000);
                      // 设置是否从httpUrlConnection读入，默认情况下是true（可以不写）;
                      con.setDoInput(true);
                      //InputStream in = con.getInputStream();
                      //存放路劲
                      File file1 = new File(getExternalFilesDir(null), "cover");
                      //不存在创建
                      if (!file1.exists()) {
                          file1.mkdir();
                      }
                      File file = new File(file1, String.valueOf(System.currentTimeMillis()));
                      // 输出的文件流
                      fos = new FileOutputStream(file);
                      // 输入流
                      in = con.getInputStream();
                      // 2K的数据缓冲
                      byte ch[] = new byte[2 * 1024];
                      // 读取到的数据长度
                      int len;
                      if (fos != null) {
                          // 开始读取
                          while   ((len = in.read(ch)) != -1) {
                              fos.write(ch, 0, len);
                          }
                          book.setDowPhoto(file.getAbsolutePath());

                      }
                      //根据本地绝对路径获取文件file.getAbsolutePath()
                      final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                      book.setBitmap(getBytes(bitmap));
                      dowBooks.add(book);
                      //通知主线程更新UI

                  } catch (MalformedURLException e) {
                      e.printStackTrace();
                  } catch (IOException e) {
                      e.printStackTrace();
                  } finally {
                      // 完毕，关闭所有链接
                      try {


                          in.close();
                          fos.close();
                      } catch (Exception e) {
                          e.printStackTrace();
                      }
                      Log.e("","homefragment"+book);
                  }
              }
//          Message obtain = Message.obtain();
//          obtain.what = 2;
//          mhandler.sendMessage(obtain);


          Log.e("", "我是thread尾巴");



      }
    });
    mthread.start();

  }

  public void loadscandhistory(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(user!=null){
                    System.out.println(user);
                    DBOpenHelper dbOpenHelper = new DBOpenHelper(MainActivity.this);
                    dbOpenHelper.openDatabase("test.db");
                    ScServiceImpl scService=new ScServiceImpl();
                    scService.setDBOpenHelper(dbOpenHelper);
                    scService.createTable(user.getSc());
                    List<Sc> scs=scService.selectScById(user.getUserId(),user.getSc());
                    myApplication.setScs(scs);
                    HistoryServiceImpl historyService=new HistoryServiceImpl();
                    historyService.setDBOpenHelper(dbOpenHelper);
                    historyService.createTable(user.getHistory());
                    List<History> historys=historyService.selectHistoryById(user.getUserId(),user.getHistory());
                    myApplication.setHistorys(historys);
                    dbOpenHelper.close();
                }
            }
        }).start();

  }

    public void imaLoading2() {
                Log.e("","我是thread");
                URL url = null;
                HttpURLConnection con = null;
                FileOutputStream fos =null;
                InputStream in=null;
                try {
                    for(Book book:books){

                                    // 构造URL
                                    url = new URL(Consts.PHOTO_BASE_URI+book.getBookPhoto());
                                    // 打开连接
                                    con = (HttpURLConnection) url.openConnection();
                                    //请求方式
                                    con.setRequestMethod("GET");
                                    //设置超时时间
                                    con.setReadTimeout(5000);
                                    // 设置是否从httpUrlConnection读入，默认情况下是true（可以不写）;
                                    con.setDoInput(true);
                                    //InputStream in = con.getInputStream();
                                    //存放路劲
                                    File file1 = new File(getExternalFilesDir(null),"cover");
                                    //不存在创建
                                    if(!file1.exists()){
                                        file1.mkdir();
                                    }
                                    File file = new File(file1, String.valueOf(System.currentTimeMillis()));
                                    // 输出的文件流
                                    fos = new FileOutputStream(file);
                                    // 输入流
                                    try {
                                        in = con.getInputStream();
                                    }catch (IOException e){
                                        e.printStackTrace();
                                    }
                                    // 2K的数据缓冲
                                    byte ch[] = new byte[2 * 1024];
                                    // 读取到的数据长度
                                    int len;
                                    if (fos != null) {
                                        // 开始读取
                                        while ((len = in.read(ch)) != -1) {
                                            fos.write(ch, 0, len);
                                        }
                                        // 完毕，关闭所有链接
                                        in.close();
                                        fos.close();
                                    }
                                    //根据本地绝对路径获取文件file.getAbsolutePath()
                                    final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                    book.setBitmap(getBytes(bitmap));
                                    dowBooks.add(book);


                            }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }


    public static byte[] getBytes(Bitmap bitmap){
        //实例化字节数组输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);//压缩位图
        return baos.toByteArray();//创建分配字节数组
    }

}




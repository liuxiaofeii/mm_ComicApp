package com.example.see.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.see.*;

import com.example.see.adapter.BookAdapter;
import com.example.see.dao.DBOpenHelper;
import com.example.see.domain.Book;
import com.example.see.domain.MiniBook;
import com.example.see.service.BookService;
import com.example.see.service.impl.BookServiceImpl;
import com.example.see.util.Consts;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {


    private static final String TAG = "";
    private HomeViewModel homeViewModel;
    private DBOpenHelper dbOpenHelper=null;
    MyApplication myApplication;
    private URL murl;
    public int m_var; //需要在消息处理中访问的成员变量，一定要声明成public
    Thread mthread=null;
    MyHandler mhandler = new MyHandler(Looper.myLooper(),(MainActivity) getActivity());
    List<Book> books=null;
    List<Book> dowbooks=null;
    ListView bookListView=null;
    String msg="ok";
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if(dbOpenHelper!=null){
            dbOpenHelper.close();
        }
        //imaLoading();


        DBOpenHelper dbOpenHelper=new DBOpenHelper(getActivity());
        dbOpenHelper.openDatabase("test.db");
//        BookService bookService=new BookServiceImpl();
//        ((BookServiceImpl) bookService).setDBOpenHelper(dbOpenHelper);
//        String sql="select bookId as _id,bookName,bookPhoto,bookAuthor from books";
//        Cursor cursor=bookService.listAllBySql(sql);
//        List<Book> books=getBooks(cursor);
        //SimpleCursorAdapter simpleAdapter=new SimpleCursorAdapter(getActivity(),R.layout.fragment_home, cursor, new String[]{"bookName"}, new int[]{R.id.book1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        myApplication= (MyApplication) getActivity().getApplication();
        books=myApplication.getBooks();
        dowbooks=myApplication.getBooks();
        System.out.println("hello 啊66");

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        bookListView=root.findViewById(R.id.book1);

        BookAdapter bookAdapter = new BookAdapter(getActivity(), R.layout.books_item, books);


        bookListView.setAdapter(bookAdapter);

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    if (Consts.HANDLER_MSG2=="ok") {
                        Book book = books.get(position);
                        Intent intent = new Intent(getActivity(), ShowActivity.class);
//
//                MiniBook miniBook=new MiniBook();
//                miniBook.setBookId(book.getBookId());
//                miniBook.setBookAuthor(book.getBookAuthor());
//                miniBook.setBookName(book.getBookName());
//                miniBook.setBookPhoto(book.getBookPhoto());
//                miniBook.setDowPhoto(book.getDowPhoto());
                        Bundle bundle = new Bundle();
                        intent.putExtra("book", book);
                        //intent.putExtra("db",dbOpenHelper);

                        startActivity(intent);
                        //System.out.println(books);

                        //Toast.makeText(getActivity(), book.getBookName(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), "数据未加载完毕，请稍等一下", Toast.LENGTH_SHORT).show();
                    }
                }


            });
        if(root==null){
            System.out.println("这是空的");
        }
        return root;
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

    public void imaLoading() {
        mthread=new Thread(new Runnable() {

            @Override
            public void run() {
                Log.e("", "我是thread");
                URL url = null;
                HttpURLConnection con = null;
                FileOutputStream fos = null;
                InputStream in = null;
                for (Book book : books) {
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
                        File file1 = new File(getActivity().getExternalFilesDir(null), "cover");
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
                            while ((len = in.read(ch)) != -1) {
                                fos.write(ch, 0, len);
                            }
                            book.setDowPhoto(file.getAbsolutePath());

                        }
                        //根据本地绝对路径获取文件file.getAbsolutePath()
                        final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        book.setBitmap(getBytes(bitmap));
                        dowbooks.add(book);
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

                    }
                    Log.e("","homefragment"+book);
                }
                books.clear();
                for(Book book1:dowbooks){
                    books.add(book1);
                }
                Message obtain = Message.obtain();
                obtain.what = 1;

                BookAdapter bookAdapter = new BookAdapter(getActivity(), R.layout.books_item, dowbooks);
                bookListView.setAdapter(bookAdapter);
                mhandler.sendMessage(obtain);
                Log.e("", "我是thread");

            }
        });
        myApplication.setThread1(mthread);
        mthread.start();
    }

    public static byte[] getBytes(Bitmap bitmap){
        //实例化字节数组输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);//压缩位图
        return baos.toByteArray();//创建分配字节数组
    }




}






package com.example.see;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.example.see.adapter.ContentAdapter;
import com.example.see.dao.DBOpenHelper;
import com.example.see.domain.*;
import com.example.see.service.ContentService;
import com.example.see.service.ScService;
import com.example.see.service.impl.BookServiceImpl;
import com.example.see.service.impl.ContentServiceImpl;
import com.example.see.service.impl.HistoryServiceImpl;
import com.example.see.service.impl.ScServiceImpl;
import com.example.see.ui.login.LoginFragment;
import com.example.see.util.Consts;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends Activity {
    /** Called when the activity is first created. */
    private static final String TAG = "MainActivity";
    private static final int haimian=0;
    private static final int paidaxin=1;
    private TextView tv=null;
    MyApplication myApplication;
    private History history;
    private User user;
    DBOpenHelper dbOpenHelper=null;
    //一个完整生命周期开始时被调用，初始化Activity
    List<Book> books=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        if(dbOpenHelper!=null){
            dbOpenHelper.close();
        }
        myApplication= (MyApplication) getApplication();
        user=myApplication.getUser();
        books=myApplication.getBooks();
        Intent intent=getIntent();
        MiniBook book=(MiniBook) intent.getSerializableExtra("book");
        final Bitmap bitmap = BitmapFactory.decodeFile(book.getDowPhoto());
        ImageView imageView=findViewById(R.id.iv_cover_act_book_detail);
//        try {
//            URL url = new URL(Consts.PHOTO_BASE_URI + book.getBookPhoto());
//            Bitmap bm = BitmapFactory.decodeStream(url.openStream());
//            imageView.setImageBitmap(bm);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        imageView.setImageBitmap(bitmap);
        TextView bookName=findViewById(R.id.tv_name_act_book_detail);
        bookName.setText(book.getBookName());

        TextView bookAuthor=findViewById(R.id.tv_author_act_book_detail);
        bookAuthor.setText(book.getBookAuthor());
        dbOpenHelper=new DBOpenHelper(this);
        dbOpenHelper.openDatabase("test.db");
        ContentServiceImpl contentService=new ContentServiceImpl();
        contentService.setDBOpenHelper(dbOpenHelper);
        String contentTableName="book"+book.getBookId()+"contents";
        String sql="select contentId as _id,contentBookId,contentNO,contentDescri from "+contentTableName+" where contentBookId="+book.getBookId()+" group by contentNO";
        Cursor cursor=contentService.listAllBySql(sql);
        List<Content> contents=getContents(cursor);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.contents_view);
        if(contents==null||contents.size()==0){
            //添加一个判断为空则插入”目前无章节“
        }
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        ContentAdapter adapter = new ContentAdapter(contents);
        recyclerView.setAdapter(adapter);

        ImageView backhome=findViewById(R.id.backhome);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Title back","key down");

                finish();
            }
        });
        Button scbutton=findViewById(R.id.btn_offline_cache_act_book_detail);
        scbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    Integer userId = user.getUserId();
                    Integer scId = book.getBookId();
                    Sc sc = new Sc();
                    sc.setScId(scId);
                    sc.setUserId(userId);
                    List<Sc> scs=myApplication.getScs();
                    if(scs==null) {
                        scs = new ArrayList<>();
                        myApplication.setScs(scs);
                    }
                    boolean chongfu=false;
                    for(Sc sc1:scs){
                        int sc1Id=sc1.getScId();
                        int tempScId=book.getBookId();
                        if(sc1Id==tempScId){
                            chongfu=true;
                            break;
                        }
                    }
                    if(chongfu==false) {
                        scs.add(sc);
                        myApplication.setScs(scs);
                        ScServiceImpl scService = new ScServiceImpl();
                        scService.setDBOpenHelper(dbOpenHelper);
                        long row = scService.insertOneSc(sc,user.getSc());
                        if (row > 0) {
                            Toast.makeText(ShowActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ShowActivity.this, "已收藏", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(ShowActivity.this, "已在收藏列表中", Toast.LENGTH_SHORT).show();
                    }



                } else {
//                    Intent intent2 = new Intent(ShowActivity.this, LoginFragment.class);
//                    startActivity(intent2);
                    Intent intent = new Intent(ShowActivity.this, LoginActivity.class);
                    intent.putExtra("flag", 8);
                    startActivity(intent);
                    finish();

                }
            }
        });

        Button startread=findViewById(R.id.btn_start_read_act_book_detail);
        startread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ShowActivity.this, "我是开始阅读", Toast.LENGTH_SHORT).show();
                if (user != null) {
                    History history = new History();
                    history.setHistoryId(book.getBookId());
                    history.setUserId(user.getUserId());
                    history.setContentId(contents.get(0).getContentNo());
                    List<History> historys=myApplication.getHistorys();
                    if(historys==null){
                        historys=new ArrayList<>();
                    }
                    boolean chongfu=false;
                    for(History history1:historys){
                        int history1Id=history1.getHistoryId();
                        int tempHistoryId=book.getBookId();
                        if(history1Id==tempHistoryId){
                            chongfu=true;
                            break;
                        }
                    }
                    if(chongfu==false) {
                        historys.add(history);
                        HistoryServiceImpl historyService = new HistoryServiceImpl();
                        historyService.setDBOpenHelper(dbOpenHelper);
                        long row = historyService.insertOneHistory(history, user.getHistory());
                        if (row > 0) {
                            Log.e("", "插入成功");
                        }
                    }else{
                        //Toast.makeText(ShowActivity.this, "已在历史记录中", Toast.LENGTH_SHORT).show();
                    }



                }

                Intent intent = new Intent(ShowActivity.this, ChapterActivity.class);
                intent.putExtra("content", contents.get(0));
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            default:break;

        }
    }

    public List<Content> getContents(Cursor cursor){
        List<Content> coll = new ArrayList<>();
        Content content;

        cursor.moveToFirst();  // 重中之重，千万不能忘了
        while(!cursor.isAfterLast()){
            content=new Content();
            content.setContentId(cursor.getInt(0));
            content.setContentBookId(cursor.getInt(1));
            content.setContentNo(cursor.getInt(2));
            content.setContentDesri(cursor.getString(3));
            coll.add(content);
            cursor.moveToNext();
        }
        return coll;
    }



}

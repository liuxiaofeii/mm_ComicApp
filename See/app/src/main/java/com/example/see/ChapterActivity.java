package com.example.see;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.example.see.adapter.BookAdapter;
import com.example.see.adapter.ContentPathAdapter;
import com.example.see.dao.DBOpenHelper;
import com.example.see.domain.Book;
import com.example.see.domain.Content;
import com.example.see.domain.History;
import com.example.see.domain.User;
import com.example.see.service.BookService;
import com.example.see.service.impl.BookServiceImpl;
import com.example.see.service.impl.ContentServiceImpl;
import com.example.see.service.impl.HistoryServiceImpl;
import com.example.see.util.Consts;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChapterActivity extends Activity {
    MyApplication myApplication;
    private DBOpenHelper dbOpenHelper;
    private User user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //System.out.println("我是chapterActivity");
        setContentView(R.layout.activity_book_load);
        if(dbOpenHelper!=null){
            dbOpenHelper.close();
        }
        myApplication= (MyApplication) getApplication();
        Intent intent=getIntent();
        Content content=(Content)intent.getSerializableExtra("content");
        user=myApplication.getUser();
        DBOpenHelper dbOpenHelper=new DBOpenHelper(this);
        dbOpenHelper.openDatabase("test.db");
        List<History> historys=myApplication.getHistorys();
        if(historys==null){
            historys=new ArrayList<>();
            myApplication.setHistorys(historys);
        }
        if (user != null) {
            History history = new History();
            history.setHistoryId(content.getContentBookId());
            history.setUserId(user.getUserId());
            history.setContentId(content.getContentNo());
            int contentBookId=content.getContentBookId();

            if (history != null) {
                boolean chongfu=false;
                if(historys.size()>0) {
                    for (History history1 : historys) {
                        int history1Id = history1.getHistoryId();
                        int tempHistoryId = contentBookId;
                        int contentId = history1.getContentId();
                        int tempContentId = content.getContentNo();
                        if (history1Id == tempHistoryId) {
                            if (contentId != tempContentId) {
                                historys.remove(history1);
                                historys.add(history);
                                HistoryServiceImpl historyService = new HistoryServiceImpl();
                                historyService.setDBOpenHelper(dbOpenHelper);
                                long row = historyService.insertOneHistory(history, user.getHistory());
                                if (row > 0) {
                                    Log.e("", "插入成功");
                                }
                                break;
                            }
                            break;
                        }
                    }
                }else{
                    historys.add(history);
                }

            }

        }


        ImageView imageView=findViewById(R.id.contentPath);
        String contentTableName="book"+content.getContentBookId()+"contents";
        String sql2="select contentId as _id,contentPath,contentNO,contentDescri from "+contentTableName+" where contentBookId="+content.getContentBookId()+" and contentNo="+content.getContentNo();
        System.out.println("我的id是"+content.getContentBookId());
        ContentServiceImpl contentService=new ContentServiceImpl();
        contentService.setDBOpenHelper(dbOpenHelper);
        Cursor cursor=contentService.listAllBySql(sql2);
        List<Content> contents=getContents(cursor);


        ContentPathAdapter contentPathAdapter = new ContentPathAdapter(ChapterActivity.this, R.layout.contentpaths_item, contents);
        ListView contentPathListView=findViewById(R.id.contentPath1);
        contentPathListView.setAdapter(contentPathAdapter);
        contentPathListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //
            }
        });
        ImageView backshow=findViewById(R.id.backshow);
        backshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView nextcontent=findViewById(R.id.nextcontent);
        nextcontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), ChapterActivity.class);
                Integer contentno=content.getContentNo();
                content.setContentNo(contentno+1);
                intent.putExtra("content",content);
                startActivity(intent);
                finish();
            }
        });
    }

    public List<Content> getContents(Cursor cursor){
        List<Content> coll = new ArrayList<>();
        Content content;

        cursor.moveToFirst();  // 重中之重，千万不能忘了
        while(!cursor.isAfterLast()){
            content=new Content();
            content.setContentId(cursor.getInt(0));
            content.setContentPath(cursor.getString(1));
            content.setContentNo(cursor.getInt(2));
            content.setContentDesri(cursor.getString(3));
            coll.add(content);
            cursor.moveToNext();
        }
        return coll;
    }



}

package com.example.see;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.example.see.adapter.BookHistoryAdapter;
import com.example.see.dao.DBOpenHelper;
import com.example.see.domain.Book;
import com.example.see.domain.History;
import com.example.see.domain.User;
import com.example.see.service.impl.BookServiceImpl;
import com.example.see.service.impl.HistoryServiceImpl;

import java.util.ArrayList;
import java.util.List;


public class HistoryActivity extends FragmentActivity {

    private FragmentManager manager;
    MyApplication myApplication;
    DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("hello 啊3");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        if(dbOpenHelper!=null){
            dbOpenHelper.close();
        }
        myApplication= (MyApplication) getApplication();
        Intent intent=getIntent();
        User user= (User) intent.getSerializableExtra("user");
        DBOpenHelper dbOpenHelper=new DBOpenHelper(this);
        dbOpenHelper.openDatabase("test.db");
        HistoryServiceImpl historyService=new HistoryServiceImpl();
        historyService.setDBOpenHelper(dbOpenHelper);
        historyService.createTable(user.getHistory());
        BookServiceImpl bookService=new BookServiceImpl();
        bookService.setDBOpenHelper(dbOpenHelper);
        List<History> historys=myApplication.getHistorys();
        if(historys==null){
            historys=new ArrayList<>();
            myApplication.setHistorys(historys);
        }
        List<Book> abooks=myApplication.getBooks();
        List<Book> books=bookService.getBooksByHistory2(abooks,historys);

        ListView historyListView=findViewById(R.id.history_listview);
        if(books.size()==0){
            Toast.makeText(HistoryActivity.this, "还未有历史记录", Toast.LENGTH_SHORT).show();
        }


        BookHistoryAdapter bookHistoryAdapter = new BookHistoryAdapter(this, R.layout.activity_history_list, books);


        historyListView.setAdapter(bookHistoryAdapter);

        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(HistoryActivity.this, ShowActivity.class);
                Book book = books.get(position);
                Toast.makeText(HistoryActivity.this, "收藏的书是"+book.getBookName(), Toast.LENGTH_SHORT).show();
            }


        });
        ImageView backhome=findViewById(R.id.backmine2);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Title back","key down");

                finish();
            }
        });









    }


}

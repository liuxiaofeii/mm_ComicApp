package com.example.see;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.see.adapter.BookAdapter;
import com.example.see.adapter.BookScAdapter;
import com.example.see.dao.DBOpenHelper;
import com.example.see.domain.Book;
import com.example.see.domain.Sc;
import com.example.see.domain.User;
import com.example.see.service.impl.BookServiceImpl;
import com.example.see.service.impl.ScServiceImpl;

import java.util.ArrayList;
import java.util.List;


public class ScActivity extends FragmentActivity {

    private FragmentManager manager;
    MyApplication myApplication;
    DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("hello 啊3");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sc);
        if(dbOpenHelper!=null){
            dbOpenHelper.close();
        }
        myApplication= (MyApplication) getApplication();
        Intent intent=getIntent();
        User user= (User) intent.getSerializableExtra("user");
        DBOpenHelper dbOpenHelper=new DBOpenHelper(this);
        dbOpenHelper.openDatabase("test.db");
//        ScServiceImpl scService=new ScServiceImpl();
//        scService.setDBOpenHelper(dbOpenHelper);
//        scService.createTable(user.getSc());
//        List<Sc> scs=scService.selectScById(user.getUserId(),user.getSc());
        List<Sc> scs=myApplication.getScs();
        if(scs==null){
            scs=new ArrayList<>();
            myApplication.setScs(scs);
        }

        ListView scListView=findViewById(R.id.sc_listview);
        BookServiceImpl bookService=new BookServiceImpl();
        bookService.setDBOpenHelper(dbOpenHelper);
        List<Book> abooks=myApplication.getBooks();
        List<Book> books=bookService.getBooksBySc2(abooks,scs);
        if(books.size()==0){
            Toast.makeText(ScActivity.this, "还未有收藏", Toast.LENGTH_SHORT).show();
        }


        BookScAdapter bookScAdapter = new BookScAdapter(this, R.layout.activity_sc_list, books);


        scListView.setAdapter(bookScAdapter);

        scListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ScActivity.this, ShowActivity.class);
                Book book = books.get(position);
                Toast.makeText(ScActivity.this, "收藏的书是"+book.getBookName(), Toast.LENGTH_SHORT).show();
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

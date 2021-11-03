package com.example.see.service.impl;

import android.database.Cursor;
import com.example.see.base.BaseService;
import com.example.see.base.BaseServiceImpl;
import com.example.see.dao.DBOpenHelper;
import com.example.see.domain.History;
import com.example.see.domain.Sc;
import com.example.see.service.BookService;
import com.example.see.domain.Book;

import java.util.ArrayList;
import java.util.List;

public class BookServiceImpl extends BaseServiceImpl<Book> implements BookService {
    @Override
    public DBOpenHelper setDBOpenHelper(DBOpenHelper db) {
        dbOpenHelper=db;
        return dbOpenHelper;
    }

    @Override
    public List<Book> getBooksBySc(List<Sc> scs) {
        List<Book> books=new ArrayList<>();
        if(scs!=null&&scs.size()>0) {
            for (Sc sc : scs) {
                Integer bookId = sc.getScId();
                Book book = getBookById(bookId);
                books.add(book);
            }
        }

            return books;

    }

    @Override
    public List<Book> getBooksByHistory(List<History> historys) {
        List<Book> books=new ArrayList<>();
        if(historys!=null&&historys.size()>0) {
            for (History history : historys) {
                Integer bookId = history.getHistoryId();
                Book book = getBookById(bookId);
                book.setHistoryReadContentId(history.getContentId());
                book.setHistoryReadContentId(history.getContentId());
                books.add(book);
            }
        }

        return books;

    }

    @Override
    public List<Book> getBooksByHistory2(List<Book> books, List<History> historys) {
        List<Book> historybooks=new ArrayList<>();
        for(History history:historys){
            for(Book book:books) {
                int bookId=book.getBookId();
                int historyId=history.getHistoryId();
                if (bookId==historyId){
                    book.setHistoryReadContentId(history.getContentId());
                    historybooks.add(book);
                }
            }
        }
        return historybooks;
    }

    @Override
    public List<Book> getBooksBySc2(List<Book> books, List<Sc> scs) {
        List<Book> scbooks=new ArrayList<>();
        for(Sc sc:scs){
            for(Book book:books) {
                int bookId=book.getBookId();
                int scId=sc.getScId();
                if (bookId==scId){
                    scbooks.add(book);
                }
            }
        }
        return scbooks;
    }

    @Override
    public Book getBookById(Integer id) {
        if(id==null){
            return null;
        }
        String sql="select bookId as _id,bookName,bookPhoto from books where bookId="+id;
        Cursor cursor=dbOpenHelper.query(sql,null);
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            Book book=new Book();
            book.setBookId(cursor.getInt(0));
            book.setBookName(cursor.getString(1));
            book.setBookPhoto(cursor.getString(2));
            return book;
        }
        return null;

    }
}

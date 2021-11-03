package com.example.see.service;

import com.example.see.base.BaseService;
import com.example.see.domain.Book;
import com.example.see.domain.History;
import com.example.see.domain.Sc;

import java.util.List;

public interface BookService extends BaseService<Book> {

    public List<Book> getBooksBySc(List<Sc> scs);
    public List<Book> getBooksByHistory(List<History> historys);
    public List<Book> getBooksByHistory2(List<Book> books,List<History> historys);
    public List<Book> getBooksBySc2(List<Book> books,List<Sc> scs);
    public Book getBookById(Integer id);

}

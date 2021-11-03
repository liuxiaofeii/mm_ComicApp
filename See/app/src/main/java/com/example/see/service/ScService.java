package com.example.see.service;

import com.example.see.base.BaseService;
import com.example.see.domain.Book;
import com.example.see.domain.Sc;
import com.example.see.domain.User;

import java.util.List;

public interface ScService extends BaseService<Sc> {
    public List<Sc> selectScById(Integer id,String scsname);
    public long insertOneSc(Sc sc,String scsname);
    public void createTable(String scsname);
}

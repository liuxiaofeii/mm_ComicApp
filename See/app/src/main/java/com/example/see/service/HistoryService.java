package com.example.see.service;

import com.example.see.base.BaseService;
import com.example.see.domain.History;
import com.example.see.domain.History;

import java.util.List;

public interface HistoryService extends BaseService<History> {
    public List<History> selectHistoryById(Integer id,String historysname);
    public long insertOneHistory(History history,String historysname);
    public void createTable(String historysname);
    public void updateHistoryById(History history,String historysname);
}

package com.example.see.service;

import com.example.see.base.BaseService;
import com.example.see.domain.Sc;
import com.example.see.domain.User;

import java.io.Serializable;
import java.util.List;

public interface UserService extends BaseService<User> {
    public User selectByUserName(String username,String password);
    public long insertOneUser(User user);
}

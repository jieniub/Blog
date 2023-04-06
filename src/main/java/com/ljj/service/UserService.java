package com.ljj.service;

import com.ljj.pojo.User;

public interface UserService {
    public User checkUser(String username, String password);
}

package com.ljj.service;


import com.ljj.dao.UserRepository;
import com.ljj.pojo.User;
import com.ljj.utils.MD5Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository UserRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = UserRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}

package com.ljj.dao;

import com.ljj.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.LinkedList;
import java.util.Queue;

public interface UserRepository extends JpaRepository<User,Long> {
    public User findByUsernameAndPassword(String username,String password);
}

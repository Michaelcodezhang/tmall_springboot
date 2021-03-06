package com.how2java.tmall.dao;

import com.how2java.tmall.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User,Integer> {
    public User findByName(String name);
    public User findByNameAndPassword(String name,String password);
}

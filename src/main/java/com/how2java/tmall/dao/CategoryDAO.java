package com.how2java.tmall.dao;

import com.how2java.tmall.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;


//***********负责与数据库进行交互（dao层/继承了JPA的一系列方法）***********

public interface CategoryDAO extends JpaRepository<Category,Integer> {
}

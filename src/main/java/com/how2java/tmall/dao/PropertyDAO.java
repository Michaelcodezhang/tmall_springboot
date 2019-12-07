package com.how2java.tmall.dao;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//***********负责与数据库进行交互（dao层/继承了JPA的一系列方法）***********

public interface PropertyDAO extends JpaRepository<Property,Integer> {
    Page<Property> findByCategory(Category category, Pageable pageable);
    List<Property> findByCategory(Category category);
}

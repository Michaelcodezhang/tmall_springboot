package com.how2java.tmall.web;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//***********负责接受View层请求的Servlet（controller层/注入了service层获得的数据）***********

@RestController
public class CategoryController {

    @Autowired CategoryService categoryService;

    @GetMapping("/categories")
    public List<Category> list() throws Exception{
        return categoryService.list();
    }
}

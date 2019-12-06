package com.how2java.tmall.web;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class ProductController {
    @Autowired ProductService productService;

    @GetMapping("/categories/{cid}/products")
    public Page4Navigator<Product> list(
            @RequestParam(value = "start",defaultValue = "0") int start,
            @RequestParam(value = "size",defaultValue = "5") int size,
            @PathVariable("cid") int cid) throws Exception{
        start=start<0?0:start;
        Page4Navigator page4Navigator=productService.list(cid,start,size,5);
        return page4Navigator;
    }

    @PostMapping("/products")
    public Object add(@RequestBody Product bean) throws Exception{
        bean.setCreateDate(new Date());
        productService.add(bean);
        return bean;
    }

    @DeleteMapping("/products/{id}")
    public String delete(@PathVariable("id") int id) throws Exception{
        productService.delete(id);
        return null;
    }

    @PutMapping("/products")
    public Object update(@RequestBody Product bean) throws Exception{
        productService.update(bean);
        return bean;
    }

    @GetMapping("/products/{id}")
    public Object get(@PathVariable("id") int id) throws Exception{
        Product bean=productService.get(id);
        return bean;
    }
}

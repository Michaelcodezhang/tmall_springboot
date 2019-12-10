package com.how2java.tmall.web;

import com.how2java.tmall.comparator.*;
import com.how2java.tmall.pojo.*;
import com.how2java.tmall.service.*;
import com.how2java.tmall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
public class ForeRESTController {

    @Autowired CategoryService categoryService;
    @Autowired ProductService productService;
    @Autowired UserService userService;
    @Autowired ProductImageService productImageService;
    @Autowired PropertyValueService propertyValueService;
    @Autowired ReviewService reviewService;

    @GetMapping("/forehome")
    public Object home() throws Exception{
        List<Category> cs=categoryService.list();
        productService.fill(cs);
        productService.fillByRow(cs);
        categoryService.removeCategoryFromProduct(cs);
        return cs;
    }

    @PostMapping("/foreregister")
    public Object register(@RequestBody User user)throws Exception{
        String name=user.getName();
        String password=user.getPassword();
        name= HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean exist=userService.isExist(name);

        if(exist){
            String message="用户名已经被使用，不能使用";
            return Result.fail(message);
        }

        user.setPassword(password);
        userService.add(user);

        return Result.success();
    }

    @PostMapping("/forelogin")
    public Object login(@RequestBody User userParam, HttpSession session) throws Exception{
        String name=userParam.getName();
        String password=userParam.getPassword();
        name=HtmlUtils.htmlEscape(name);

        User user=userService.getByNameAndPassword(name,password);
        if(user==null){
            String message="账号密码错误";
            return Result.fail(message);
        }else {
            session.setAttribute("user",user);
            return Result.success();
        }
    }

    @GetMapping("/foreproduct/{pid}")
    public Object product(@PathVariable("pid") int pid) throws Exception{
        Product product=productService.get(pid);
        //设置产品页面图片
        List<ProductImage> productSingleImages=productImageService.listSingleProductImage(product);
        List<ProductImage> productDetailImages=productImageService.listDetailProductImage(product);
        product.setProductSingleImages(productSingleImages);
        product.setProductDetailImages(productDetailImages);

        List<PropertyValue> propertyValues=propertyValueService.list(product);
        List<Review> reviews=reviewService.list(product);
        productService.setSaleAndReviewNumber(product);
        productImageService.setFirstProductImage(product);

        Map<String,Object> map=new HashMap<>();
        map.put("product",product);
        map.put("pvs",propertyValues);
        map.put("reviews",reviews);
        return Result.success(map);
    }

    @GetMapping("/forecheckLogin")
    public Object checkLogin(HttpSession session) throws Exception{
        User user=(User) session.getAttribute("user");
        if(user!=null){
            return Result.success();
        }
        return Result.fail("未登录");
    }

    @GetMapping("/forecategory/{cid}")
    public Object category(@PathVariable("cid") int cid,String sort)
            throws Exception{
        Category category=categoryService.get(cid);
        productService.fill(category);
        productService.setSaleAndReviewNumber(category.getProducts());
        categoryService.removeCategoryFromProduct(category);

        if(sort!=null){
            switch (sort){
                case "review":
                    Collections.sort(category.getProducts(),new ProductReviewComparator());
                    break;
                case "date":
                    Collections.sort(category.getProducts(),new ProductDateComparator());
                    break;
                case "saleCount":
                    Collections.sort(category.getProducts(),new ProductSaleCountComparator());
                    break;
                case "price":
                    Collections.sort(category.getProducts(),new ProductPriceComparator());
                    break;
                case "all":
                    Collections.sort(category.getProducts(),new ProductAllComparator());
                    break;
            }
        }
        return category;
    }

    @PostMapping("foresearch")
    public Object search(String keyword){
        if(keyword==null){
            keyword="";
        }
        List<Product> products=productService.search(keyword,0,20);
        productImageService.setFirstProductImages(products);
        productService.setSaleAndReviewNumber(products);
        return products;
    }
}

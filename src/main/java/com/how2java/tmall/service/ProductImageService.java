package com.how2java.tmall.service;

import com.how2java.tmall.dao.ProductImageDAO;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "productImages")
public class ProductImageService {
    public static final String type_single="single";
    public static final String type_detail="detail";

    @Autowired ProductImageDAO productImageDAO;
    @Autowired ProductService productService;

    @CacheEvict(allEntries = true)
    public void add(ProductImage bean){
        productImageDAO.save(bean);
    }

    @CacheEvict(allEntries = true)
    public void delete(int id){
        productImageDAO.delete(id);
    }

    @Cacheable(key = "'productImages-one-'+#p0")
    public ProductImage get(int id){
        return productImageDAO.findOne(id);
    }

    @Cacheable(key = "'productImage-single-pid-'+#p0.id")
    public List<ProductImage> listSingleProductImage(Product product){
        return productImageDAO.findByProductAndTypeOrderByIdDesc(product,type_single);
    }

    @Cacheable(key = "'productImages-detail-pid-'+#p0.id")
    public List<ProductImage> listDetailProductImage(Product product){
        return productImageDAO.findByProductAndTypeOrderByIdDesc(product,type_detail);
    }

    public void setFirstProductImage(Product product){
        ProductImageService productImageService= SpringContextUtil.getBean(ProductImageService.class);
        List<ProductImage> singleImages=productImageService.listSingleProductImage(product);
        if(!singleImages.isEmpty()){
            product.setFirstProductImage(singleImages.get(0));
        }else {
            product.setFirstProductImage(new ProductImage());
        }
    }

    public void setFirstProductImages(List<Product> products){
        for (Product product:products){
            setFirstProductImage(product);
        }
    }

    public void setFirstProductImageOnOrderItems(List<OrderItem> onOrderItems){
        for (OrderItem orderItem:onOrderItems){
            setFirstProductImage(orderItem.getProduct());
        }
    }
}

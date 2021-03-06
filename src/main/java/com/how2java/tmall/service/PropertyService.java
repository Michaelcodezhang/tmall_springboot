package com.how2java.tmall.service;

import com.how2java.tmall.dao.PropertyDAO;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "properties")
public class PropertyService {

    @Autowired CategoryService categoryService;
    @Autowired PropertyDAO propertyDAO;

    @CacheEvict(allEntries = true)
    public void add(Property bean){
        propertyDAO.save(bean);
    }

    @CacheEvict(allEntries = true)
    public void delete(int id){
        propertyDAO.delete(id);
    }

    @CacheEvict(allEntries = true)
    public void update(Property bean){
        propertyDAO.save(bean);
    }

    @Cacheable(key = "'property-one-'+#p0")
    public Property get(int id){
        return propertyDAO.findOne(id);
    }

    @Cacheable(key="'properties-cid-'+#p0+'-page-'+#p1+'-'+#p2")
    public Page4Navigator<Property> list(int cid,int start,int size,int navigatePages){
        Category category=categoryService.get(cid);

        Sort sort=new Sort(Sort.Direction.DESC,"id");
        Pageable pageable=new PageRequest(start,size,sort);

        Page<Property> pageFromJPA=propertyDAO.findByCategory(category,pageable);

        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    @Cacheable(key = "'properties-cid-'+#p0.id")
    public List<Property> listByCategory(Category category){
        return propertyDAO.findByCategory(category);
    }
}

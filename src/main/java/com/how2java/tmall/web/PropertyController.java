package com.how2java.tmall.web;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.service.PropertyService;
import com.how2java.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PropertyController {
    @Autowired PropertyService propertyService;

    @GetMapping("/categories/{cid}/properties")
    public Page4Navigator<Property> list(
            @PathVariable("cid") int cid,
            @RequestParam(value = "start",defaultValue = "0") int start,
            @RequestParam(value = "size",defaultValue = "5") int size)
            throws Exception{
        Page4Navigator<Property> page4Navigator=propertyService.list(cid,start,size,5);
        return page4Navigator;
    }

    @PostMapping("/properties")
    public Object add(@RequestBody Property bean) throws Exception{
        propertyService.add(bean);
        return bean;
    }

    @DeleteMapping("/properties/{id}")
    public String delete(@PathVariable("id") int id) throws Exception{
        propertyService.delete(id);
        return null;
    }

    @PutMapping("/properties")
    public Object update(@RequestBody Property bean) throws Exception{
        propertyService.update(bean);
        return bean;
    }

    @GetMapping("/properties/{id}")
    public Object get(@PathVariable("id") int id)throws Exception{
        Property bean=propertyService.get(id);
        return bean;
    }
}

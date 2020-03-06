package com.how2java.tmall.service;

import com.how2java.tmall.dao.OrderDAO;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.util.Page4Navigator;
import com.how2java.tmall.util.SpringContextUtil;
import org.aspectj.weaver.ast.Or;
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
@CacheConfig(cacheNames = "orders")
public class OrderService {
    public static final String waitPay="waitPay";
    public static final String waitDelivery="waitDelivery";
    public static final String waitConfirm="waitConfirm";
    public static final String waitReview="waitReview";
    public static final String finish="finish";
    public static final String delete="delete";

    @Autowired OrderDAO orderDAO;
    @Autowired OrderItemService orderItemService;

    @Cacheable(key = "'orders-page-'+#p0+'-'+#p1")
    public Page4Navigator<Order> list(int start,int size,int navigatePages){
        Sort sort=new Sort(Sort.Direction.DESC,"id");
        Pageable pageable=new PageRequest(start,size,sort);
        Page pageFromJPA=orderDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    public void removeOrderFromOrderItem(List<Order> orders){
        for(Order order:orders){
            removeOrderFromOrderItem(order);
        }
    }

    public void removeOrderFromOrderItem(Order order){
        List<OrderItem> orderItems=order.getOrderItems();
        for(OrderItem orderItem:orderItems){
            orderItem.setOrder(null);
        }
    }

    @Cacheable(key = "'orders-one-'+#p0")
    public Order get(int oid){
        return orderDAO.findOne(oid);
    }

    @CacheEvict(allEntries = true)
    public void update(Order bean){
        orderDAO.save(bean);
    }

    @CacheEvict(allEntries = true)

    public void add(Order bean){
        orderDAO.save(bean);
    }

    @CacheEvict(allEntries = true)
    public float add(Order order,List<OrderItem> orderItems){
        float total=0;
        add(order);

        for(OrderItem orderItem:orderItems){
            orderItem.setOrder(order);
            orderItemService.update(orderItem);
            total+=orderItem.getProduct().getPromotePrice()*orderItem.getNumber();
        }
        return total;
    }

    public List<Order> listByUserWithoutDelete(User user){
        OrderService orderService= SpringContextUtil.getBean(OrderService.class);
        List<Order> orders=orderService.listByUserAndNotDeleted(user);
        orderItemService.fill(orders);
        return orders;
    }

    @Cacheable(key = "'orders-uid-'+#p0.id")
    public List<Order> listByUserAndNotDeleted(User user){
        return orderDAO.findByUserAndStatusNotOrderByIdDesc(user,OrderService.delete);
    }

    public void cacl(Order order){
        List<OrderItem> orderItems=order.getOrderItems();
        float total=0;
        for(OrderItem orderItem:orderItems){
            total+=orderItem.getProduct().getPromotePrice()*orderItem.getNumber();
        }
        order.setTotal(total);
    }
}

package com.how2java.tmall.web;

import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.OrderService;
import com.how2java.tmall.util.Page4Navigator;
import com.how2java.tmall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class OrderController {
    @Autowired OrderService orderService;
    @Autowired OrderItemService orderItemService;

    @GetMapping("/orders")
    public Page4Navigator<Order> list(
            @RequestParam(value = "start",defaultValue = "0") int start,
            @RequestParam(value = "size",defaultValue = "5") int size)
            throws Exception{
        start=start<0?0:start;
        Page4Navigator<Order> page4Navigator=orderService.list(start,size,5);
        orderItemService.fill(page4Navigator.getContent());
        orderService.removeOrderFromOrderItem(page4Navigator.getContent());
        return page4Navigator;
    }

    @PutMapping("deliveryOrder/{oid}")
    public Object deliverOrder(@PathVariable("oid") int oid)throws Exception{
        Order o=orderService.get(oid);
        o.setDeliveryDate(new Date());
        o.setStatus(OrderService.waitConfirm);
        orderService.update(o);
        return Result.success();
    }
}

package com.how2java.tmall.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.how2java.tmall.service.OrderService;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Table(name = "order_")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;

    private String orderCode;
    private String address;
    private String post;
    private String receiver;
    private String mobile;
    private String UserMessage;
    private Date createDate;
    private Date payDate;
    private Date deliveryDate;
    private Date confirmDate;
    private String status;

    @Transient
    private List<OrderItem> orderItems;
    @Transient
    private float total;
    @Transient
    private int totalNumber;
    @Transient
    private String statusDesc;

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public String getAddress() {
        return address;
    }

    public String getPost() {
        return post;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMobile() {
        return mobile;
    }

    public String getUserMessage() {
        return UserMessage;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public String getStatus() {
        return status;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public float getTotal() {
        return total;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public String getStatusDesc() {
        if(statusDesc!=null){
            return statusDesc;
        }
        String desc="未知";
        switch (status){
            case OrderService.waitPay:
                desc="待付";
                break;
            case OrderService.waitDelivery:
                desc="待发";
                break;
            case OrderService.waitConfirm:
                desc="等收";
                break;
            case OrderService.waitReview:
                desc="等评";
                break;
            case OrderService.finish:
                desc="完成";
                break;
            case OrderService.delete:
                desc="删除";
                break;
            default:
                desc="未知";
        }

        statusDesc=desc;
        return statusDesc;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setUserMessage(String userMessage) {
        UserMessage = userMessage;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}

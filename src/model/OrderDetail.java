/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class OrderDetail {
    private int orderID;
    private int ApplicatonId;
    private double price;
    private double sale;
    private String discountCode;

    public OrderDetail() {
    }

    public OrderDetail(int orderID, int ApplicatonId, double price, double sale, String discountCode) {
        this.orderID = orderID;
        this.ApplicatonId = ApplicatonId;
        this.price = price;
        this.sale = sale;
        this.discountCode = discountCode;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getApplicatonId() {
        return ApplicatonId;
    }

    public void setApplicatonId(int ApplicatonId) {
        this.ApplicatonId = ApplicatonId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }
    
    
    
    
}

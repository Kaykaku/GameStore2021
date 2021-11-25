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
public class Order {
    private int orderID;
    private Date creationDate;
    private int status;
    private int accountId;

    public Order() {
    }

    public Order(int orderID, Date creationDate, int status, int accountId) {
        this.orderID = orderID;
        this.creationDate = creationDate;
        this.status = status;
        this.accountId = accountId;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    public Object[] toObjects() {
        return new Object[]{orderID,creationDate,status,accountId};
    }

    @Override
    public String toString() {
        return "ID:" + orderID + ", CreationDate=" + creationDate + ", Status=" + status + ", AccountId=" + accountId;
    }
}

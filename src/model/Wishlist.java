/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author leminhthanh
 */
public class Wishlist {
    int ApplicatonId, AccountId;

    public Wishlist() {
    }

    public Wishlist(int ApplicatonId, int AccountId) {
        this.ApplicatonId = ApplicatonId;
        this.AccountId = AccountId;
    }

    public int getApplicatonId() {
        return ApplicatonId;
    }

    public void setApplicatonId(int ApplicatonId) {
        this.ApplicatonId = ApplicatonId;
    }

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int AccountId) {
        this.AccountId = AccountId;
    }
           
}

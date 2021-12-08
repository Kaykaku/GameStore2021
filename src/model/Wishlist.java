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
    int ApplicationId, AccountId;

    public Wishlist() {
    }

    public Wishlist( int AccountId,int ApplicationId) {
        this.ApplicationId = ApplicationId;
        this.AccountId = AccountId;
    }

    public int getApplicationId() {
        return ApplicationId;
    }

    public void setApplicatonId(int ApplicationId) {
        this.ApplicationId = ApplicationId;
    }

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int AccountId) {
        this.AccountId = AccountId;
    }
           
}

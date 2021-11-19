/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author TUẤN KIỆT
 */
public class ApplicationView {
    private int applicatonViewId ;
    private java.util.Date   ratingDate ;
    private int rate  ;
    private int views  ;
    private int applicatonId  ;
    private int accountId ; 

    public ApplicationView() {
    }

    public ApplicationView(int applicatonViewId, Date ratingDate, int rate, int views, int applicatonId, int accountId) {
        this.applicatonViewId = applicatonViewId;
        this.ratingDate = ratingDate;
        this.rate = rate;
        this.views = views;
        this.applicatonId = applicatonId;
        this.accountId = accountId;
    }

    public int getApplicatonViewId() {
        return applicatonViewId;
    }

    public void setApplicatonViewId(int applicatonViewId) {
        this.applicatonViewId = applicatonViewId;
    }

    public Date getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(Date ratingDate) {
        this.ratingDate = ratingDate;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getApplicatonId() {
        return applicatonId;
    }

    public void setApplicatonId(int applicatonId) {
        this.applicatonId = applicatonId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    
    
}

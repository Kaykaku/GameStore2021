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
    private int ApplicatonViewId ;
    private java.util.Date   RatingDate ;
    private int Rate  ;
    private int Views  ;
    private int ApplicatonId  ;
    private int AccountId ; 

    public ApplicationView() {
    }

    public ApplicationView(int ApplicatonViewId, Date RatingDate, int Rate, int Views, int ApplicatonId, int AccountId) {
        this.ApplicatonViewId = ApplicatonViewId;
        this.RatingDate = RatingDate;
        this.Rate = Rate;
        this.Views = Views;
        this.ApplicatonId = ApplicatonId;
        this.AccountId = AccountId;
    }

    public int getApplicatonViewId() {
        return ApplicatonViewId;
    }

    public void setApplicatonViewId(int ApplicatonViewId) {
        this.ApplicatonViewId = ApplicatonViewId;
    }

    public Date getRatingDate() {
        return RatingDate;
    }

    public void setRatingDate(Date RatingDate) {
        this.RatingDate = RatingDate;
    }

    public int getRate() {
        return Rate;
    }

    public void setRate(int Rate) {
        this.Rate = Rate;
    }

    public int getViews() {
        return Views;
    }

    public void setViews(int Views) {
        this.Views = Views;
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
    public int toSting() {
        return this.AccountId;
    }
    
}

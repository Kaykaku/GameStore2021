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
public class Comment {
    private int commentsID  ;
    private java.util.Date creationDate ;
    private String title  ;
    private String description ;
    private int	applicatonViewId ;

    public Comment() {
    }

    public Comment(int commentsID, Date creationDate, String title, String description, int applicatonViewId) {
        this.commentsID = commentsID;
        this.creationDate = creationDate;
        this.title = title;
        this.description = description;
        this.applicatonViewId = applicatonViewId;
    }

    public int getCommentsID() {
        return commentsID;
    }

    public void setCommentsID(int commentsID) {
        this.commentsID = commentsID;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getApplicatonViewId() {
        return applicatonViewId;
    }

    public void setApplicatonViewId(int applicatonViewId) {
        this.applicatonViewId = applicatonViewId;
    }

    
    
}

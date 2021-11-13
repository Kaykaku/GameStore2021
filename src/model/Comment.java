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
    private int CommentsID  ;
    private java.util.Date CreationDate ;
    private String Title  ;
    private String Description ;
    private int	ApplicatonViewId ;

    public Comment() {
    }

    public Comment(int CommentsID, Date CreationDate, String Title, String Description, int ApplicatonViewId) {
        this.CommentsID = CommentsID;
        this.CreationDate = CreationDate;
        this.Title = Title;
        this.Description = Description;
        this.ApplicatonViewId = ApplicatonViewId;
    }

    public int getCommentsID() {
        return CommentsID;
    }

    public void setCommentsID(int CommentsID) {
        this.CommentsID = CommentsID;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date CreationDate) {
        this.CreationDate = CreationDate;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getApplicatonViewId() {
        return ApplicatonViewId;
    }

    public void setApplicatonViewId(int ApplicatonViewId) {
        this.ApplicatonViewId = ApplicatonViewId;
    }
    
    public int toSting(){
        return this.CommentsID;
    }
    
}

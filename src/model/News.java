/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author leminhthanh
 */
public class News {
    int NewsId, AccountId;
    Date CreationDate;
    String Title, Description, Contents;
    byte[] Image;

    public News() {
    }

    public News(int NewsId, int AccountId, Date CreationDate, String Title, String Description, String Contents, byte[] Image) {
        this.NewsId = NewsId;
        this.AccountId = AccountId;
        this.CreationDate = CreationDate;
        this.Title = Title;
        this.Description = Description;
        this.Contents = Contents;
        this.Image = Image;
    }

    public int getNewsId() {
        return NewsId;
    }

    public void setNewsId(int NewsId) {
        this.NewsId = NewsId;
    }

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int AccountId) {
        this.AccountId = AccountId;
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

    public String getContents() {
        return Contents;
    }

    public void setContents(String Contents) {
        this.Contents = Contents;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] Image) {
        this.Image = Image;
    }


	
}

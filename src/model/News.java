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
    int NewsID;
    String name;
    int AccountId;
    int Views;
    Date CreationDate;
    boolean toggle_Views;
    String Title, Description, Contents;
    byte[] Image;
    
    public News() {
    }

    public News(String name) {
        this.name = name;
    }
    
    public News(int Views, Date CreationDate, String Title, String Description) {
        this.Views = Views;
        this.CreationDate = CreationDate;
        this.Title = Title;
        this.Description = Description;
    }
    
    public News(int NewsID, int AccountId) {
        this.NewsID = NewsID;
        this.AccountId = AccountId;
    }

    public News(int NewsID, Date CreationDate, String Title, String Description, String Contents,byte[] Image,boolean toggle_Views,int AccountId) {
        this.NewsID = this.NewsID;
        this.CreationDate = CreationDate;
        this.Title = Title;
        this.Description = Description;
        this.Contents = Contents;
        this.Image = Image;
        this.toggle_Views = toggle_Views;
        this.AccountId = AccountId;
    }   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getViews() {
        return Views;
    }

    public void setViews(int Views) {
        this.Views = Views;
    }

    public int getNewsID() {
        return NewsID;
    }

    public void setNewsID(int NewsID) {
        this.NewsID = NewsID;
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

    public boolean isToggle_Views() {
        return toggle_Views;
    }

    public void setToggle_Views(boolean toggle_Views) {
        this.toggle_Views = toggle_Views;
    }
    public Object[] toObjects() {
         return new Object[]{NewsID,CreationDate,Title,Description, Contents,Image,AccountId,Views};
    }
   public String toStrings() {
        return "ID:" + NewsID + ", Name:" + name + ", AccountId:" + AccountId + ", Views:" + Views + ", CreationDate:" + CreationDate +", Title:" + Title + ", Description=" + Description + ", Contents:" + Contents + ", Image:" + Image + '}';
    }
    

	
}

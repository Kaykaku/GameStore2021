/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author NguyenHuan
 */
public class Application {
    int ApplicationID;
    String Name;
    float Price,Size;
    int Type;
    String Image;
    String Developer,Publisher;
    Date ReleaseDay,CreationDate;
    String Languages;
    float Sale;
    String Description;
    int Active,EnableBuy;

    public Application() {
    }

    public Application(int ApplicationID, String Name, float Price, float Size,int Type, String Image, String Developer, String Publisher, Date ReleaseDay, Date CreationDate, String Languages, float Sale, String Description, int Active, int EnableBuy) {
        this.ApplicationID = ApplicationID;
        this.Name = Name;
        this.Price = Price;
        this.Size = Size;
        this.Type = Type;
        this.Image = Image;
        this.Developer = Developer;
        this.Publisher = Publisher;
        this.ReleaseDay = ReleaseDay;
        this.CreationDate = CreationDate;
        this.Languages = Languages;
        this.Sale = Sale;
        this.Description = Description;
        this.Active = Active;
        this.EnableBuy = EnableBuy;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public int getApplicationID() {
        return ApplicationID;
    }

    public void setApplicationID(int ApplicationID) {
        this.ApplicationID = ApplicationID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float Price) {
        this.Price = Price;
    }

    public float getSize() {
        return Size;
    }

    public void setSize(float Size) {
        this.Size = Size;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getDeveloper() {
        return Developer;
    }

    public void setDeveloper(String Developer) {
        this.Developer = Developer;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String Publisher) {
        this.Publisher = Publisher;
    }

    public Date getReleaseDay() {
        return ReleaseDay;
    }

    public void setReleaseDay(Date ReleaseDay) {
        this.ReleaseDay = ReleaseDay;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date CreationDate) {
        this.CreationDate = CreationDate;
    }

    public String getLanguages() {
        return Languages;
    }

    public void setLanguages(String Languages) {
        this.Languages = Languages;
    }

    public float getSale() {
        return Sale;
    }

    public void setSale(float Sale) {
        this.Sale = Sale;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getActive() {
        return Active;
    }

    public void setActive(int Active) {
        this.Active = Active;
    }

    public int getEnableBuy() {
        return EnableBuy;
    }

    public void setEnableBuy(int EnableBuy) {
        this.EnableBuy = EnableBuy;
    }

    @Override
    public String toString() {
        return "Application{" + "ApplicationID=" + ApplicationID + ", Name=" + Name + ", Price=" + Price + ", Size=" + Size + ", Type=" + Type + ", Image=" + Image + ", Developer=" + Developer + ", Publisher=" + Publisher + ", ReleaseDay=" + ReleaseDay + ", CreationDate=" + CreationDate + ", Languages=" + Languages + ", Sale=" + Sale + ", Description=" + Description + ", Active=" + Active + ", EnableBuy=" + EnableBuy + '}';
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author NguyenHuan
 */
public class Application {

    private int applicationID;
    private String name;
    private float price, size;
    private boolean type;
    private byte[] appImage, appIcon;
    private String developer, publisher;
    private Date releaseDay, creationDate;
    private String languages;
    private float sale;
    private String description;
    private boolean active, enableBuy;

    public Application() {
    }

    public Application(int applicationID, String name, float price, float size, boolean type, byte[] appImage, byte[] appIcon, String developer, String publisher, Date releaseDay, Date creationDate, String languages, float sale, String description, boolean active, boolean enableBuy) {
        this.applicationID = applicationID;
        this.name = name;
        this.price = price;
        this.size = size;
        this.type = type;
        this.appImage = appImage;
        this.appIcon = appIcon;
        this.developer = developer;
        this.publisher = publisher;
        this.releaseDay = releaseDay;
        this.creationDate = creationDate;
        this.languages = languages;
        this.sale = sale;
        this.description = description;
        this.active = active;
        this.enableBuy = enableBuy;
    }

    public int getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(int applicationID) {
        this.applicationID = applicationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public byte[] getAppImage() {
        return appImage;
    }

    public void setAppImage(byte[] appImage) {
        this.appImage = appImage;
    }

    public byte[] getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(byte[] appIcon) {
        this.appIcon = appIcon;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getReleaseDay() {
        return releaseDay;
    }

    public void setReleaseDay(Date releaseDay) {
        this.releaseDay = releaseDay;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public float getSale() {
        return sale;
    }

    public void setSale(float sale) {
        this.sale = sale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isEnableBuy() {
        return enableBuy;
    }

    public void setEnableBuy(boolean enableBuy) {
        this.enableBuy = enableBuy;
    }

}

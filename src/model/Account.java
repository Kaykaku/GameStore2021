/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import javafx.scene.image.Image;

/**
 *
 * @author leminhthanh
 */
public class Account {
    private int accountId ;
    private String name ;
    private Date birthDay;
    private boolean gender;
    private byte[] image=null;
    private String email;
    private String address;
    private String country;
    private Date creationDate;
    private String userName;
    private String password;
    private boolean active;
    private int role;
    private boolean comment;

    public Account() {
    }

    public Account(int accountId, String name, Date birthDay, boolean gender, byte[] image, String email, String address, String country, Date creationDate, String userName, String password, boolean active, int role, boolean comment) {
        this.accountId = accountId;
        this.name = name;
        this.birthDay = birthDay;
        this.gender = gender;
        this.image = image;
        this.email = email;
        this.address = address;
        this.country = country;
        this.creationDate = creationDate;
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.role = role;
        this.comment = comment;
    }
    
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creattionDate) {
        this.creationDate = creattionDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean isComment() {
        return comment;
    }

    public void setComment(boolean comment) {
        this.comment = comment;
    }
    
}

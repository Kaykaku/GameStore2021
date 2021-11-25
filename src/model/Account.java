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
    private String Email;
    private String address;
    private String country;
    private Date creationDate;
    private String Username;
    private String Password;
    private boolean active;
    private int role=-1;
    private boolean comment;

    public Account() {
    }

    public Account(int accountId, String name, Date birthDay, boolean gender, String Email, String address, String country, Date creationDate, String UserName, String Password, boolean active, int role, boolean comment) {
        this.accountId = accountId;
        this.name = name;
        this.birthDay = birthDay;
        this.gender = gender;
        this.Email = Email;
        this.address = address;
        this.country = country;
        this.creationDate = creationDate;
        this.Username = UserName;
        this.Password = Password;
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
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
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

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserName() {
        return Username;
    }

    public void setUserName(String UserName) {
        this.Username = UserName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
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

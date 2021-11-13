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
public class Account {
    int AccountId, Role;
    String Name, Email, Address, Country;
    Date BirthDay, CreationDate;
    boolean Gender, Active, Comment;
    byte[] Image;

    public Account() {
    }

    public Account(int AccountId, int Role, String Name, String Email, String Address, String Country, Date BirthDay, Date CreationDate, boolean Gender, boolean Active, boolean Comment, byte[] Image) {
        this.AccountId = AccountId;
        this.Role = Role;
        this.Name = Name;
        this.Email = Email;
        this.Address = Address;
        this.Country = Country;
        this.BirthDay = BirthDay;
        this.CreationDate = CreationDate;
        this.Gender = Gender;
        this.Active = Active;
        this.Comment = Comment;
        this.Image = Image;
    }

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int AccountId) {
        this.AccountId = AccountId;
    }

    public int getRole() {
        return Role;
    }

    public void setRole(int Role) {
        this.Role = Role;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public Date getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(Date BirthDay) {
        this.BirthDay = BirthDay;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date CreationDate) {
        this.CreationDate = CreationDate;
    }

    public boolean isGender() {
        return Gender;
    }

    public void setGender(boolean Gender) {
        this.Gender = Gender;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean Active) {
        this.Active = Active;
    }

    public boolean isComment() {
        return Comment;
    }

    public void setComment(boolean Comment) {
        this.Comment = Comment;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] Image) {
        this.Image = Image;
    }
            



}

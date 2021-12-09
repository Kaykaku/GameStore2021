/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.jfoenix.controls.JFXCheckBox;
import java.util.Date;
import javafx.scene.control.CheckBox;
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
    private String username;
    private String password;
    private boolean active;
    private int role=-1;
    private boolean comment;
    private String QRcode;
    private JFXCheckBox checkbox =new JFXCheckBox();
    public Account() {
        checkbox.setDisable(true);
    }
    
    public int getAccountId() {
        return accountId;
    }

    public Account(String email) {
        this.email = email;
    }

    public Account(int accountId, String name, String email, String username) {
        this.accountId = accountId;
        this.name = name;
        this.email = email;
        this.username = username;
        this.checkbox =  new JFXCheckBox();
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

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getQRcode() {
        return QRcode;
    }

    public void setQRcode(String QRcode) {
        this.QRcode = QRcode;
    }
    public CheckBox getCheckbox() {
        return checkbox;
    }
 
    public void setCheckBox(JFXCheckBox checkbox) {
        this.checkbox = checkbox;
    }
    

    @Override
    public String toString() {
         return  email;
    }
    public Object[] toObjects() { 
        return new Object[]{accountId,name,birthDay, gender?"Male":"Famale",email,address, country,creationDate,username,
            active?"Active":"Inactive",role==0?"Admin":role==1?"Manager":"User",comment?"Yes":"No" };
    
    }

    public String toStrings() {
        return  "ID:" + accountId + ", Name:" + name + ", BirthDay:" + birthDay + ", Gender" + ( gender?"Male":"Famale") 
                + ", Email=" + email + ", Address=" + address + ", Country=" + country + ", CreationDate=" + creationDate
                + ", Username=" + username + ", Active=" + (active?"Active":"Inactive") + ", Role=" + (role==0?"Admin":role==1?"Manager":"User") 
                + ", Comment=" + (comment?"Yes":"No" ) + '}';
    }



}

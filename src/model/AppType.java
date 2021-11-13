/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author NguyenHuan
 */
public class AppType {
     int ApplicationID;
     int CategoryId;

    public AppType() {
    }

    public AppType(int ApplicationID, int CategoryId) {
        this.ApplicationID = ApplicationID;
        this.CategoryId = CategoryId;
    }

    public int getApplicationID() {
        return ApplicationID;
    }

    public void setApplicationID(int ApplicationID) {
        this.ApplicationID = ApplicationID;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int CategoryId) {
        this.CategoryId = CategoryId;
    }
     
}

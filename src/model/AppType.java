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
     int applicationID;
     int categoryId;

    public AppType() {
    }

    public AppType(int applicationID, int categoryId) {
        this.applicationID = applicationID;
        this.categoryId = categoryId;
    }

    public int getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(int applicationID) {
        this.applicationID = applicationID;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
     
}

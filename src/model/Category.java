/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import DAO.AppTypeDAO;

/**
 *
 * @author NguyenHuan
 */
public class Category {
    int categoryId;
    String name;
    String color;
    
    public Category() {
    }

    public Category(int categoryId, String name, String color) {
        this.categoryId = categoryId;
        this.name = name;
        this.color = color;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public  int getAppCount(){
        return new AppTypeDAO().selectByCategoryId(categoryId).size();
    }
      public Object[] toObjects() {
        return new Object[]{categoryId,name,color};
    }
    @Override
    public String toString() {
        return  name;
    }
    
    public String toString1() {
        return  "ID:" + categoryId + ", Name:" + name + ", Color:" + color;
    }
    
}

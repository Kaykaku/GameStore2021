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
public class Category {
    int CategoryId;
    String Name;
    String Color;

    public Category() {
    }

    public Category(int CategoryId, String Name, String Color) {
        this.CategoryId = CategoryId;
        this.Name = Name;
        this.Color = Color;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int CategoryId) {
        this.CategoryId = CategoryId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String Color) {
        this.Color = Color;
    }
    
    
}

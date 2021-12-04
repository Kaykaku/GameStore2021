/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Admin
 */
public class RBY {
    private int year,month,total1,total2;
    private double revenue1,revenue2;

    public RBY() {
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTotal1() {
        return total1;
    }

    public void setTotal1(int total1) {
        this.total1 = total1;
    }

    public int getTotal2() {
        return total2;
    }

    public void setTotal2(int total2) {
        this.total2 = total2;
    }

    public double getRevenue1() {
        return revenue1;
    }

    public void setRevenue1(double revenue1) {
        this.revenue1 = (double) Math.round(revenue1*100)/100;
    }

    public double getRevenue2() {
        return revenue2;
    }

    public void setRevenue2(double revenue2) {
        this.revenue2 =(double) Math.round(revenue2*100)/100;
    }
    public Object[] toObjectRBY(){
        return new Object[]{
            year,total1,revenue1,total2,revenue2
        };
    }
    public Object[] toObjectRBM(){
        return new Object[]{
            month+"/"+year,total1,revenue1,total2,revenue2
        };
    }
}

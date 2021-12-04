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
public class OAB {
    private int year,month,orders,processingOrders,acceptedOrders,refundedOrders,apps,processingApps,acceptedApp,refundedApps;

    public OAB() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public int getProcessingOrders() {
        return processingOrders;
    }

    public void setProcessingOrders(int processingOrders) {
        this.processingOrders = processingOrders;
    }

    public int getAcceptedOrders() {
        return acceptedOrders;
    }

    public void setAcceptedOrders(int acceptedOrders) {
        this.acceptedOrders = acceptedOrders;
    }

    public int getRefundedOrders() {
        return refundedOrders;
    }

    public void setRefundedOrders(int refundedOrders) {
        this.refundedOrders = refundedOrders;
    }

    public int getApps() {
        return apps;
    }

    public void setApps(int apps) {
        this.apps = apps;
    }

    public int getProcessingApps() {
        return processingApps;
    }

    public void setProcessingApps(int processingApps) {
        this.processingApps = processingApps;
    }

    public int getAcceptedApp() {
        return acceptedApp;
    }

    public void setAcceptedApp(int acceptedApp) {
        this.acceptedApp = acceptedApp;
    }

    public int getRefundedApps() {
        return refundedApps;
    }

    public void setRefundedApps(int refundedApps) {
        this.refundedApps = refundedApps;
    }
    public Object[] toObjectOAY(){
        return new Object[]{
            year,orders,processingOrders,acceptedOrders,refundedOrders,apps,processingApps,acceptedApp,refundedApps
        };
    }
    public Object[] toObjectOAM(){
        return new Object[]{
            month+"/"+year,orders,processingOrders,acceptedOrders,refundedOrders,apps,processingApps,acceptedApp,refundedApps
        };
    }
    
}

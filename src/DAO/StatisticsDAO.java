/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.OAB;
import model.RBY;
import until.Connect_Jdbc;

/**
 *
 * @author Admin
 */
public class StatisticsDAO {
    private List<Object[]> getListOfArray (String sql, String[] col, Object... args){
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = Connect_Jdbc.query(sql, args);
            while (rs.next()) {                
                Object[] vals = new Object[col.length];
                for (int i = 0; i < col.length; i++) {
                    vals[i]= rs.getObject(col[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    public List<Object[]> getRatingApp(int applicationID){
        String sql="{CALL sp_RatingApplication(?)}";
        String[] col ={"star","turns"};
        return this.getListOfArray(sql, col, applicationID);
    }
    public Object[] getAccountStatistics(int accountID){
        String sql="{CALL sp_AccountStatictis(?)}";
        String[] col ={"Application Views","Total Orders","Ratings","Comments","App Purchased","Amount Paid"};       
        List<Object[]> list= this.getListOfArray(sql, col, accountID);
        return  list.size()>0?list.get(0):null;
    }
    public List<RBY> getRevenue_ByYear(){
        String sql="{CALL sp_Statictis_Revenue_ByYear}";
        String[] col ={"Year","Total Orders(Etimate)","Total revenue(Etimate)","Total Orders(Reality)","Total revenue(Reality)"};       
        List<RBY> list = new ArrayList<>();
        try {
            ResultSet resultSet = Connect_Jdbc.query(sql);
            while (resultSet.next()) {
                RBY entity = new RBY();
                entity.setYear(resultSet.getInt(col[0]));
                entity.setTotal1(resultSet.getInt(col[1]));
                entity.setRevenue1(resultSet.getDouble(col[2]));
                entity.setTotal2(resultSet.getInt(col[3]));
                entity.setRevenue2(resultSet.getDouble(col[4]));
                list.add(entity);
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<RBY> getRevenue_ByMonth(){
        String sql="{CALL sp_Statictis_Revenue_ByMonth}";
        String[] col ={"Year","Month","Total Orders(Etimate)","Total revenue(Etimate)","Total Orders(Reality)","Total revenue(Reality)"};       
        List<RBY> list = new ArrayList<>();
        try {
            ResultSet resultSet = Connect_Jdbc.query(sql);
            while (resultSet.next()) {
                RBY entity = new RBY();
                entity.setYear(resultSet.getInt(col[0]));
                entity.setMonth(resultSet.getInt(col[1]));
                entity.setTotal1(resultSet.getInt(col[2]));
                entity.setRevenue1(resultSet.getDouble(col[3]));
                entity.setTotal2(resultSet.getInt(col[4]));
                entity.setRevenue2(resultSet.getDouble(col[5]));
                list.add(entity);
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<OAB> getOdersApps_ByYear(){
        String sql="{CALL sp_Statictis_Orders_ByYear}";
        String[] col ={"Year","Total orders","Processing","Accepted","Refunded","Total apps","Processing apps","Accepted apps","Refunded apps"};       
        List<OAB> list = new ArrayList<>();
        try {
            ResultSet resultSet = Connect_Jdbc.query(sql);
            while (resultSet.next()) {
                OAB entity = new OAB();
                entity.setYear(resultSet.getInt(col[0]));
                entity.setOrders(resultSet.getInt(col[1]));
                entity.setProcessingOrders(resultSet.getInt(col[2]));
                entity.setAcceptedOrders(resultSet.getInt(col[3]));
                entity.setRefundedOrders(resultSet.getInt(col[4]));
                entity.setApps(resultSet.getInt(col[5]));
                entity.setProcessingApps(resultSet.getInt(col[6]));
                entity.setAcceptedApp(resultSet.getInt(col[7]));
                entity.setRefundedApps(resultSet.getInt(col[8]));         
                list.add(entity);
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<OAB> getOdersApps_ByMonth(){
        String sql="{CALL sp_Statictis_Orders_ByMonth}";
        String[] col ={"Year","Month","Total orders","Processing","Accepted","Refunded","Total apps","Processing apps","Accepted apps","Refunded apps"};       
        List<OAB> list = new ArrayList<>();
        try {
            ResultSet resultSet = Connect_Jdbc.query(sql);
            while (resultSet.next()) {
                OAB entity = new OAB();
                entity.setYear(resultSet.getInt(col[0]));
                entity.setMonth(resultSet.getInt(col[1]));
                entity.setOrders(resultSet.getInt(col[2]));
                entity.setProcessingOrders(resultSet.getInt(col[3]));
                entity.setAcceptedOrders(resultSet.getInt(col[4]));
                entity.setRefundedOrders(resultSet.getInt(col[5]));
                entity.setApps(resultSet.getInt(col[6]));
                entity.setProcessingApps(resultSet.getInt(col[7]));
                entity.setAcceptedApp(resultSet.getInt(col[8]));
                entity.setRefundedApps(resultSet.getInt(col[9]));         
                list.add(entity);
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

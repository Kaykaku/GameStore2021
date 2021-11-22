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
}

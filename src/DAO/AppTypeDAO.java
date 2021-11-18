/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import model.AppType;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import until.Connect_Jdbc;

/**
 *
 * @author NguyenHuan
 */
public class AppTypeDAO extends DAO<AppType, Integer>{

    @Override
    public void insert(AppType entity) {
        String sql = "insert into App_Type (ApplicationId,CategoryId) values (?,?)";
        Connect_Jdbc.update(sql, entity.getApplicationID(),entity.getCategoryId());
    }

    @Override
    public void update(AppType entity) {
       String sql = "update App_Type set CategoryId =? where ApplicationId=? ";
       Connect_Jdbc.update(sql, entity.getCategoryId(),entity.getApplicationID());
    }

    @Override
    public void delete(Integer key) {
       
    }

    @Override
    public List<AppType> selectAll() {
       String sql = " select * from App_Type";
       return selectBySql(sql);
    }

    @Override
    public AppType selectByID(Integer keys) {
       return  null;
    }

    @Override
    public List<AppType> selectByKeyWord(String keys) {
        String sql= "select * from App_Type where CategoryId like ?";
       keys= "%"+keys+"%";
       return selectBySql(sql,keys);
    }

    @Override
    public List<AppType> selectBySql(String sql, Object... args) {
        List<AppType> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = Connect_Jdbc.query(sql, args);
            while (rs.next()) {                
                AppType apt = new AppType();
                apt.setApplicationID(rs.getInt("ApplicationId"));
                apt.setCategoryId(rs.getInt("CategoryId"));
                list.add(apt);
            }
                    
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return list;
    }
    public boolean isContainAppType(AppType entity) {
       String sql= "Select * from App_Type where ApplicationID = ? and CategoryId=?";
       return selectBySql(sql,entity.getApplicationID(),entity.getCategoryId()).size()>0;
    }
    public List<AppType> selectByApplicationId(Integer keys) {
       String sql= "select * from App_Type where ApplicationID=?";
       return selectBySql(sql,keys);
    }
    public List<AppType> selectByCategoryId(Integer keys) {
       String sql= "select * from App_Type where CategoryId=?";
       return selectBySql(sql,keys);
    }
    public void delete(AppType entity) {
       String sql = "delete from App_Type where ApplicationID = ? and CategoryId=? ";
       Connect_Jdbc.update(sql,entity.getApplicationID(),entity.getCategoryId());
    }
}

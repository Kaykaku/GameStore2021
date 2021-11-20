/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.*;
import model.Application;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import until.Connect_Jdbc;

/**
 *
 * @author NguyenHuan
 */
public class ApplicationDAO extends DAO<Application, Integer> {

    @Override
    public void insert(Application entity) {
        String sql = "insert into Applications (Name,Price,Size,AppImage,AppIcon,Developer,Publisher,ReleaseDay,CreationDate,Languages,Sale,Description,Active,EnableBuy) "
                + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connect_Jdbc.update(sql, entity.getName(), entity.getPrice(),entity.getSize(),entity.getAppImage(),entity.getAppIcon(),entity.getDeveloper()
                ,entity.getPublisher(),entity.getReleaseDay(),entity.getCreationDate(),entity.getLanguages(),entity.getSale(),entity.getDescription(),entity.isActive(),entity.isEnableBuy());

    }

    @Override
    public void update(Application entity) {
        String sql = "update Applications set Name=?,Price=?,Size=?,AppImage=?, AppIcon=?,Developer=?,Publisher=?,ReleaseDay=?,CreationDate=?,Languages=?,Sale=?,Description=?,Active=?,EnableBuy=?"
                + " WHERE ApplicationId=? " ;
        Connect_Jdbc.update(sql, entity.getName(), entity.getPrice(),entity.getSize(),entity.getAppImage(),entity.getAppIcon(),entity.getDeveloper()
                ,entity.getPublisher(),entity.getReleaseDay(),entity.getCreationDate(),entity.getLanguages(),entity.getSale()
                ,entity.getDescription(),entity.isActive(),entity.isEnableBuy(),entity.getApplicationID());
    }

    @Override
    public void delete(Integer key) {
        String spl ="delete from Applications where ApplicationId=? ";
        Connect_Jdbc.update(spl, key);
    }

    @Override
    public List<Application> selectAll() {
        String sql= "SELECT * FROM Applications";
        return selectBySql(sql);
    }

    @Override
    public Application selectByID(Integer keys) {
        String sql= "SELECT * FROM Applications WHERE ApplicationId=?";
        return selectBySql(sql,keys).isEmpty()? null:selectBySql(sql,keys).get(0);
    }

    @Override
    public List<Application> selectByKeyWord(String keys) {
       String sql= "SELECT * FROM Applications where Name like ? or ApplicationId like ?";
       keys= "%"+keys+"%";
       return selectBySql(sql,keys,keys);
    }

    @Override
    public List<Application> selectBySql(String sql, Object... args) {
        List<Application> list = new ArrayList<>();
        try {
            ResultSet rs = null;
//Name,Price,Size,Image,Developer,Publisher,ReleaseDay,CreationDate,Languages,Sale,Description,Active,EnableBuy
                rs = Connect_Jdbc.query(sql, args);
                while (rs.next()) {
                    Application entity = new Application();
                    entity.setApplicationID(rs.getInt("ApplicationId"));
                    entity.setName(rs.getString("Name"));
                    entity.setPrice(rs.getFloat("Price"));
                    entity.setSize(rs.getFloat("Size"));
                    entity.setAppIcon(rs.getBytes("AppIcon"));
                    entity.setAppImage(rs.getBytes("AppImage"));
                    entity.setDeveloper(rs.getString("Developer"));
                    entity.setPublisher(rs.getString("Publisher"));
                    entity.setReleaseDay(rs.getDate("ReleaseDay"));
                    entity.setCreationDate(rs.getDate("CreationDate"));
                    entity.setLanguages(rs.getString("Languages"));
                    entity.setSale(rs.getFloat("Sale"));
                    entity.setDescription(rs.getString("Description"));
                    entity.setActive(rs.getBoolean("Active"));
                    entity.setEnableBuy(rs.getBoolean("EnableBuy"));
                    list.add(entity);
                }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return list;
    }
    public List<Application> selectNonPurchaseApplications(Integer accountID,String keyword) {
       String sql= "select * from Applications where (ApplicationId not in " 
               +" (select ApplicationId from Orders a join OrderDetails b on a.OrderID =b.OrderID where a.AccountId=? and Status = 1)) "
               +"and (ApplicationId like ? or Name like ?)";
       keyword = "%"+keyword+"%";
       return selectBySql(sql,accountID,keyword,keyword);
    }
    
    public List<Application> selectPurchaseApplications(Integer accountID,String keyword) {
       String sql= "select * from Applications where (ApplicationId in " 
               +" (select ApplicationId from Orders a join OrderDetails b on a.OrderID =b.OrderID where a.AccountId=? and Status = 1)) "
               +"and (ApplicationId like ? or Name like ?)";
       keyword = "%"+keyword+"%";
       return selectBySql(sql,accountID,keyword,keyword);
    }
    
    public boolean isPurchaseApplication(Integer accountID,Integer applicationId){
        for (Application application : selectPurchaseApplications(accountID, "")) {      
            if(application.getApplicationID()==applicationId){
                return true;
            }
        }
        return false;
    }

    public List<Integer> selectYears() {
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = Connect_Jdbc.query("select distinct year(ReleaseDay) from Applications order by year(ReleaseDay) desc");
                while (rs.next()) {
                    int year = rs.getInt(1);
                    list.add(year);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Application> getReleaseDay_SearchByYear(int year) {
        List<Application> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            String sql = "{call sp_ReleaseDay_SearchByYear (?)}";
            rs = Connect_Jdbc.query(sql, year);

            While(rs, list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public List<Application> getReleaseDay_ThisWeek() {
        List<Application> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            String sql = "{call sp_ReleaseDay_ThisWeek}";
            rs = Connect_Jdbc.query(sql);

            While(rs, list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public List<Application> getReleaseDay_ThisMonth() {
        List<Application> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            String sql = "{call sp_ReleaseDay_ThisMonth}";
            rs = Connect_Jdbc.query(sql);

            While(rs, list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public List<Application> getReleaseDay_ThisYear() {
        List<Application> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            String sql = "{call sp_ReleaseDay_ThisYear}";
            rs = Connect_Jdbc.query(sql);

            While(rs, list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    private void While(ResultSet rs, List<Application> list) throws SQLException {
        while (rs.next()) {
            Application entity = new Application();
            
            entity.setName(rs.getString("Name"));
            entity.setPrice(rs.getFloat("Price"));
            entity.setAppIcon(rs.getBytes("AppIcon"));
            entity.setPublisher(rs.getString("Publisher"));
            
            list.add(entity);
        }
    }

}

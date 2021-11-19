/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import model.Application;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import until.Connect_Jdbc;

/**
 *
 * @author NguyenHuan
 */
//
//
public class ApplicationDAO extends DAO<Application, Integer> {

    @Override
    public void insert(Application entity) {
        String sql = "insert into Applications (Name,Price,Size,Type,Image,Developer,Publisher,ReleaseDay,CreationDate,Languages,Sale,Description,Active,EnableBuy) "
                + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connect_Jdbc.update(sql, entity.getName(), entity.getPrice(),entity.getSize(),entity.getType(),entity.getAppIcon(),entity.getAppImage(),entity.getDeveloper()
                ,entity.getPublisher(),entity.getReleaseDay(),entity.getCreationDate(),entity.getLanguages(),entity.getSale()
                ,entity.getDescription(),entity.isActive(),entity.isEnableBuy());

    }

    @Override
    public void update(Application entity) {
        String sql = "update Applications set Name=?,Price=?,Size=?,Type=?,AppIcon=?,AppImage=?,Developer=?,Publisher=?,ReleaseDay=?,CreationDate=?,Languages=?,Sale=?,Description=?,Active=?,EnableBuy=?"
                + " WHERE ApplicationId=? " ;
        Connect_Jdbc.update(sql, entity.getName(), entity.getPrice(),entity.getSize(),entity.getType(),entity.getAppIcon(),entity.getAppImage(),entity.getDeveloper()
                ,entity.getPublisher(),entity.getReleaseDay(),entity.getCreationDate(),entity.getLanguages(),entity.getSale()
                ,entity.getDescription(),entity.isActive(),entity.isEnableBuy(),entity.getApplicationID());
    }

    @Override
    public void delete(Integer key) {
        String spl ="delete * from Applications where = ApplicationId=? ";
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
}

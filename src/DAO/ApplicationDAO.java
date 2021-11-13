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
public class ApplicationDAO extends DAO<Application, Integer> {

    @Override
    public void insert(Application entity) {
        String sql = "insert into Applicatons (Name,Price,Size,Type,Image,Developer,Publisher,ReleaseDay,CreationDate,Languages,Sale,Description,Active,EnableBuy) "
                + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connect_Jdbc.update(sql, entity.getName(), entity.getPrice(),entity.getSize(),entity.getType(),entity.getImage(),entity.getDeveloper()
                ,entity.getPublisher(),entity.getReleaseDay(),entity.getCreationDate(),entity.getLanguages(),entity.getSale()
                ,entity.getDescription(),entity.getActive(),entity.getEnableBuy());

    }

    @Override
    public void update(Application entity) {
        String sql = "update Applicatons set Name=?,Price=?,Size=?,Type=?,Image=?,Developer=?,Publisher=?,ReleaseDay=?,CreationDate=?,Languages=?,Sale=?,Description=?,Active=?,EnableBuy=?"
                + " WHERE ApplicatonId=? " ;
        Connect_Jdbc.update(sql, entity.getName(), entity.getPrice(),entity.getSize(),entity.getType(),entity.getImage(),entity.getDeveloper()
                ,entity.getPublisher(),entity.getReleaseDay(),entity.getCreationDate(),entity.getLanguages(),entity.getSale()
                ,entity.getDescription(),entity.getActive(),entity.getEnableBuy(),entity.getApplicationID());
    }

    @Override
    public void delete(Integer key) {
        String spl ="delete * from Applicatons where = ApplicatonId=? ";
        Connect_Jdbc.update(spl, key);
    }

    @Override
    public List<Application> selectAll() {
        String sql= "SELECT * FROM Applicatons";
        return selectBySql(sql);
    }

    @Override
    public Application selectByID(Integer keys) {
        String sql= "SELECT * FROM Applicatons WHERE ApplicatonId=?";
        return selectBySql(sql,keys).isEmpty()? null:selectBySql(sql,keys).get(0);
    }

    @Override
    public List<Application> selectByKeyWord(String keys) {
       String sql= "SELECT * FROM Applicatons where Name like ?";
       keys= "%"+keys+"%";
       return selectBySql(sql,keys);
    }

    @Override
    public List<Application> selectBySql(String sql, Object... args) {
        List<Application> list = new ArrayList<>();
        try {
            ResultSet rs = null;
//Name,Price,Size,Image,Developer,Publisher,ReleaseDay,CreationDate,Languages,Sale,Description,Active,EnableBuy
                rs = Connect_Jdbc.query(sql, args);
                while (rs.next()) {
                    Application ap = new Application();
                    ap.setApplicationID(rs.getInt("ApplicatonId"));
                    ap.setName(rs.getString("Name"));
                    ap.setPrice(rs.getFloat("Price"));
                    ap.setSize(rs.getFloat("Size"));
                    ap.setImage(rs.getString("Image"));
                    ap.setDeveloper(rs.getString("Developer"));
                    ap.setPublisher(rs.getString("Publisher"));
                    ap.setReleaseDay(rs.getDate("ReleaseDay"));
                    ap.setCreationDate(rs.getDate("CreationDate"));
                    ap.setLanguages(rs.getString("Languages"));
                    ap.setSale(rs.getFloat("Sale"));
                    ap.setDescription(rs.getString("Description"));
                    ap.setActive(rs.getInt("Active"));
                    ap.setEnableBuy(rs.getInt("EnableBuy"));
                    list.add(ap);
                }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return list;
    }

}

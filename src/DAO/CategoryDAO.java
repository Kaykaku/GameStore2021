/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import model.Category;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import until.Connect_Jdbc;

/**
 *
 * @author NguyenHuan
 */
public class CategoryDAO extends DAO<Category, Integer> {

    @Override
    public void insert(Category entity) {
        String sql = "insert into Categories (Name,Color) values(?,?)";
        Connect_Jdbc.update(sql, entity.getName(), entity.getColor());
    }

    @Override
    public void update(Category entity) {
        String sql = "update Categories set Name=?,Color=? where CategoryId = ?";
        Connect_Jdbc.update(sql, entity.getName(), entity.getColor(), entity.getCategoryId());
    }

    @Override
    public void delete(Integer key) {
        String sql = "delete from Categories where CategoryId = ?";
        Connect_Jdbc.update(sql, key);
    }

    @Override
    public List<Category> selectAll() {
       String sql = " select * from Categories";
       return selectBySql(sql);
    }

    @Override
    public Category selectByID(Integer keys) {
        String sql= "select * from Categories where CategoryId=?";
        return selectBySql(sql,keys).isEmpty()? null:selectBySql(sql,keys).get(0);
    }

    @Override
    public List<Category> selectByKeyWord(String keys) {
       String sql= "select * from Categories where Name like ?";
       keys= "%"+keys+"%";
       return selectBySql(sql,keys);
    }

    @Override
    public List<Category> selectBySql(String sql, Object... args) {
        List<Category> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = Connect_Jdbc.query(sql, args);
            while (rs.next()) { 
                Category cate = new Category();
                cate.setCategoryId(rs.getInt("CategoryId"));
                cate.setName(rs.getString("Name"));
                cate.setColor(rs.getString("Color"));
                list.add(cate); 
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return list;
    }
    public Category selectByName(String keys) {
       String sql= "select * from Categories where Name like ?";
       keys= "%"+keys+"%";
       return selectBySql(sql,keys).isEmpty()? null:selectBySql(sql,keys).get(0);
    }
}

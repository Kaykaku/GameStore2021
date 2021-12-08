/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Order;
import until.Connect_Jdbc;

/**
 *
 * @author Admin
 */
public class OrderDAO extends DAO<Order,Integer>{

    @Override
    public void insert(Order entity) {
        String sql= "INSERT  INTO  Orders  (CreationDate,  Status,  AccountId) VALUES  (?,  ?,  ?)";
        Connect_Jdbc.update(sql, entity.getCreationDate(),entity.getStatus(),entity.getAccountId());
    }

    @Override
    public void update(Order entity) {
        String sql= "UPDATE  Orders  SET  CreationDate=?,  Status=?,  AccountId=?  WHERE  OrderId=?";
        Connect_Jdbc.update(sql, entity.getCreationDate(),entity.getStatus(),entity.getAccountId(),entity.getOrderID());
    }

    @Override
    public void delete(Integer key) {
        String sql= "DELETE FROM Orders WHERE OrderId=?";
        Connect_Jdbc.update(sql, key);
    }

    @Override
    public List<Order> selectAll() {
        String sql= "SELECT * FROM Orders order by OrderId desc";
        return selectBySql(sql);
    }

    @Override
    public Order selectByID(Integer keys) {
        String sql= "SELECT * FROM Orders WHERE OrderId=?";
        return selectBySql(sql,keys).isEmpty() ? null:selectBySql(sql,keys).get(0);
    }

    @Override
    public List<Order> selectByKeyWord(String keys) {
        String sql= "SELECT * FROM Orders WHERE OrderId like ? or AccountId like ? order by OrderId desc";
        keys= "%"+keys+"%";
        return selectBySql(sql,keys,keys);
    }

    @Override
    public List<Order> selectBySql(String sql, Object... args) {
        List<Order> list=new ArrayList<Order>();       
        try {
            ResultSet rs=null;
            try {
                rs= Connect_Jdbc.query(sql,args);
                while (rs.next()) {
                    Order e =new Order();
                    e.setOrderID(rs.getInt(1));
                    e.setCreationDate(rs.getDate(2));
                    e.setStatus(rs.getInt(3));
                    e.setAccountId(rs.getInt(4));
                    list.add(e);
                }
            }finally{
            rs.getStatement().getConnection().close();
            }
        } catch (Exception ex) {
            throw new RuntimeException();
        }
        return list;
    }

    public Order selectByAccountAppID(Integer acccountID,Integer AppID) {
        String sql= "SELECT * FROM Orders a inner join OrderDetails b on a.OrderID = b.OrderID WHERE a.AccountId=? and ApplicationId =? order by a.OrderID desc";
        return selectBySql(sql,acccountID,AppID).isEmpty() ? null:selectBySql(sql,acccountID,AppID).get(0);
    }
    public Order selectByLastOrder(Integer acccountID) {
        String sql= "SELECT * FROM Orders  WHERE AccountId=?";
        List<Order> list = selectBySql(sql,acccountID);
        return list.isEmpty() ? null:list.get(list.size()-1);
    }
    
}

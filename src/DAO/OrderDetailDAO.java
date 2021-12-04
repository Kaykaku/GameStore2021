/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.OrderDetail;
import until.Connect_Jdbc;

/**
 *
 * @author Admin
 */
public class OrderDetailDAO extends DAO<OrderDetail,Integer>{
    @Override
    public void insert(OrderDetail entity) {
        String sql= "INSERT  INTO  OrderDetails  (OrderId,  ApplicationId, Price ,Sale ,Discount ) VALUES  (?,  ?,  ?,?,?)";
        Connect_Jdbc.update(sql, entity.getOrderID(),entity.getApplicationId(),entity.getPrice(),entity.getSale(),entity.getDiscountCode());
    }

    @Override
    public void update(OrderDetail entity) {
        String sql= "UPDATE  OrderDetails  SET Price=?,  Sale=? , Discount=?  WHERE  OrderId=? and ApplicationId=?";
        Connect_Jdbc.update(sql,entity.getPrice(),entity.getSale(),entity.getDiscountCode(),entity.getOrderID(), entity.getApplicationId());
    }

    @Override
    public void delete(Integer key) {
        String sql= "DELETE FROM OrderDetails WHERE OrderId=?";
        Connect_Jdbc.update(sql, key);
    }

    @Override
    public List<OrderDetail> selectAll() {
        String sql= "SELECT * FROM OrderDetails";
        return selectBySql(sql);
    }

    @Override
    public OrderDetail selectByID(Integer keys) {
        return null;
    }

    @Override
    public List<OrderDetail> selectByKeyWord(String keys) {
        String sql= "SELECT * FROM OrderDetails WHERE OrderId like ? ";
        return selectBySql(sql,keys);
    }

    @Override
    public List<OrderDetail> selectBySql(String sql, Object... args) {
        List<OrderDetail> list=new ArrayList<OrderDetail>();       
        try {
            ResultSet rs=null;
            try {
                rs= Connect_Jdbc.query(sql,args);
                while (rs.next()) {
                    OrderDetail e =new OrderDetail();
                    e.setOrderID(rs.getInt("OrderId"));
                    e.setApplicationId(rs.getInt("ApplicationId"));
                    e.setPrice(rs.getDouble("Price"));
                    e.setSale(rs.getDouble("Sale"));
                    e.setDiscountCode(rs.getString("Discount"));
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
    public List<OrderDetail> selectByOrderID(Integer keys) {
        String sql= "SELECT * FROM OrderDetails WHERE OrderId=?";
        return selectBySql(sql,keys);
    }
    public OrderDetail SelectByPrimaryKey(OrderDetail entity) {
        String sql= "Select * FROM OrderDetails WHERE OrderId=? and ApplicationId=?";
        List<OrderDetail> list= selectBySql(sql,entity.getOrderID(),entity.getApplicationId());       
        return list.isEmpty()?null:list.get(0);
    }
    
    public void deleteByPrimaryKey(Integer orderId,Integer applicationId) {
        String sql= "DELETE FROM OrderDetails WHERE OrderId=? and ApplicationId=?";
        Connect_Jdbc.update(sql, orderId,applicationId);
    }

}

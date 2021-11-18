/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import model.Wishlist;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import until.Connect_Jdbc;

/**
 *
 * @author leminhthanh
 */
public class WishlistDAO extends DAO<Wishlist, String> {

    private String insert_sql = "insert into Wishlists(ApplicaitonId, AccountId) values (?, ?)";
    private String update_sql = "update Wishlists set ApplicationId = ?, AccountId = ? where AccountId = ?";
    private String delete_sql = "delete from Wishlists where AccountId = ?";
    private String select_all_sql = "select * from Wishlists";
    private String select_By_ID_sql = "select * from Wishlists where AccountId = ?";
//    private String select_By_Name = "select * from Wishlist where Name like ?";

    @Override
    public void insert(Wishlist entity) {
        Connect_Jdbc.update(insert_sql, entity.getApplicatonId(), entity.getAccountId());

    }

    @Override
    public void update(Wishlist entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String key) {
        Connect_Jdbc.update(delete_sql, key);
    }

    @Override
    public List<Wishlist> selectAll() {
        return this.selectBySql(select_all_sql);

    }

    @Override
    public Wishlist selectByID(String keys) {
        List<Wishlist> list = this.selectBySql(select_By_ID_sql, keys);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Wishlist> selectByKeyWord(String keys) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Wishlist> selectBySql(String sql, Object... args) {
        List<Wishlist> list = new ArrayList<Wishlist>();
        try {
            ResultSet resultSet = Connect_Jdbc.query(sql, args);
            while (resultSet.next()) {
                Wishlist wishlists = new Wishlist();

                wishlists.setAccountId(resultSet.getInt("AccountID"));
                wishlists.setApplicatonId(resultSet.getInt("ApplicationId"));

                list.add(wishlists);
            }
            resultSet.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}

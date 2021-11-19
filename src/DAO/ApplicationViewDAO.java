/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import model.ApplicationView;
import until.Connect_Jdbc;
/**
 *
 * @author TUẤN KIỆT
 */
public class ApplicationViewDAO extends DAO<ApplicationView, Integer > {
    final String INSERT_SQL ="INSERT into ApplicationViews(RatingDate,Rate,Views,ApplicationId,AccountId) values (?,?,?,?,?)";
    final String UPDATE_SQL ="UPDATE  ApplicationViews  SET    RatingDate=?,  Rate=?,  Views=? WHERE ApplicationViewId=?";
    final String DELETE_SQL ="DELETE FROM ApplicationViews WHERE ApplicationViewId=?";
    String SELECT_ALL_SQL = "SELECT * FROM ApplicationViews";
    String SELECT_BY_ID_SQL ="SELECT * FROM ApplicationViews where ApplicationViewId=?";
    String SELECT_BY_NAME ="select * from Account where Name Like?";
    String SELECT_BY_ACCOUNT ="select * from ApplicationViews where AccountId =? and ApplicationId=?";

    /**
     * @param args the command line arguments
     */

    @Override
    public void insert(ApplicationView entity) {
        Connect_Jdbc.update(INSERT_SQL,entity.getRatingDate(),entity.getRate(),entity.getViews(), entity.getApplicatonId(),entity.getAccountId());
    }

    @Override
    public void update(ApplicationView entity) {
       Connect_Jdbc.update(UPDATE_SQL,entity.getRatingDate(),entity.getRate(),entity.getViews(),entity.getApplicatonViewId());
    }

    @Override
    public void delete(Integer key) {
       Connect_Jdbc.update(UPDATE_SQL, key);
    }

    @Override
    public List<ApplicationView> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public ApplicationView selectByID(Integer keys) {
        List <ApplicationView> list = this.selectBySql(SELECT_BY_ID_SQL, keys); 
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<ApplicationView> selectByKeyWord(String keys) {
        return this.selectBySql(SELECT_BY_NAME, "%" + keys + "%");
    }

    @Override
    public List<ApplicationView> selectBySql(String sql, Object... args) {
        List <ApplicationView> list = new ArrayList<>();
        try {
            ResultSet rs =  Connect_Jdbc.query(sql, args);
            while (rs.next() ){
                ApplicationView entity = new ApplicationView();
                entity.setApplicatonViewId(rs.getInt("ApplicationViewID"));
                entity.setRatingDate(rs.getDate("RatingDate"));
                entity.setRate(rs.getInt("Rate"));
                entity.setViews(rs.getInt("Views"));
                entity.setApplicatonId(rs.getInt("ApplicationId"));
                entity.setAccountId(rs.getInt("AccountId"));
                list.add(entity);
               
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
        
    }
    public ApplicationView selectByAccount(Integer accountID,Integer applicationID) {
        List <ApplicationView> list = this.selectBySql(SELECT_BY_ACCOUNT, accountID,applicationID); 
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}

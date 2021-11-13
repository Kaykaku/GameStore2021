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
public class ApplicatonViewDAO extends DAO<ApplicationView, Integer > {
    final String INSERT_SQL ="INSERT into ApplicatonViews(ApplicatonViewId,RatingDate,Rate,Views,ApplicatonId,AccountId) values (?,?,?,?,?,?)";
    final String UPDATE_SQL ="UPDATE  ApplicatonViews  SET    RatingDate=?,  Rate=?,  Views=?,  ApplicatonId=?, AccountId=? WHERE ApplicatonViewId=?";
    final String DELETE_SQL ="DELETE FROM ApplicatonViews WHERE ApplicatonViewId=?";
    String SELECT_ALL_SQL = "SELECT * FROM ApplicatonViews";
    String SELECT_BY_ID_SQL ="SELECT * FROM ApplicatonViews where ApplicatonViewId=?";
    String SELECT_BY_NAME ="select * from Account where Name Like?";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }

    @Override
    public void insert(ApplicationView entity) {
        Connect_Jdbc.update(INSERT_SQL, entity.getApplicatonId(),entity.getRatingDate(),entity.getRate(),entity.getViews(), entity.getApplicatonId(),entity.getAccountId());
    }

    @Override
    public void update(ApplicationView entity) {
       Connect_Jdbc.update(UPDATE_SQL,entity.getRatingDate(),entity.getRate(),entity.getViews(), entity.getApplicatonId(),entity.getAccountId(),entity.getApplicatonId());
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
                entity.setApplicatonViewId(rs.getInt("ApplicatonViewsID"));
                entity.setRatingDate(rs.getDate("RatingDate"));
                entity.setRate(rs.getInt("Rate"));
                entity.setViews(rs.getInt("Views"));
                entity.setApplicatonId(rs.getInt("ApplicatonId"));
                entity.setAccountId(rs.getInt("AccountId"));
                list.add(entity);
               
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
        
    }
    
}

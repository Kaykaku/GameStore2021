/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Comment;
import until.Connect_Jdbc;

/**
 *
 * @author TUẤN KIỆT
 */
public class CommentDAO extends DAO<Comment, Integer > {
    final String INSERT_SQL ="insert into Comments ( CreationDate, Title, Description , ApplicationViewId ) values (?,?,?,?)";
    final String UPDATE_SQL ="UPDATE  Comments  SET    CreationDate=?,  Title=?,  Description=?,  ApplicationViewId=? WHERE CommentID=? ";
    final String DELETE_SQL ="DELETE FROM Comments WHERE CommentID=?";
    String SELECT_ALL_SQL = "SELECT * FROM Comments";
    String SELECT_BY_ID_SQL ="SELECT * FROM Comments where CommentID=?";
    String SELECT_BY_ID_APPID ="SELECT * FROM Comments where ApplicationViewId in (select ApplicationViewId from ApplicationViews where ApplicationId =?)";
    String SELECT_BY_NAME ="select * from Account where Name Like?";
    

    @Override
    public void insert(Comment entity) {
        Connect_Jdbc.update(INSERT_SQL,entity.getCreationDate(),entity.getTitle(),entity.getDescription(), entity.getApplicatonViewId());
    }

    @Override
    public void update(Comment entity) {
        Connect_Jdbc.update(UPDATE_SQL,entity.getCreationDate(),entity.getTitle(),entity.getDescription(), entity.getApplicatonViewId(),entity.getCommentsID());
    }

    @Override
    public void delete(Integer key) {
        Connect_Jdbc.update(DELETE_SQL, key);
    }

    @Override
    public List<Comment> selectAll() {
         return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public Comment selectByID(Integer keys) {
        List <Comment> list = this.selectBySql(SELECT_BY_ID_SQL, keys); 
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Comment> selectByKeyWord(String keys) {
        return this.selectBySql(SELECT_BY_NAME, "%" + keys + "%");
    }

    @Override
    public List<Comment> selectBySql(String sql, Object... args) {
        List <Comment> list = new ArrayList<>();
        try {
            ResultSet rs =  Connect_Jdbc.query(sql, args);
            while (rs.next() ){
                Comment entity = new Comment();
                entity.setCommentsID(rs.getInt("CommentID"));
                entity.setCreationDate(rs.getDate("CreationDate"));
                entity.setTitle(rs.getString("Title"));
                entity.setDescription(rs.getString("Description"));
                entity.setApplicatonViewId(rs.getInt("ApplicationViewId"));
                list.add(entity);
               
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
     
    }
    public List<Comment> selectByAppId(Integer keys) {
        return this.selectBySql(SELECT_BY_ID_APPID,keys);
    }
}

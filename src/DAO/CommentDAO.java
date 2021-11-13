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
public class CommentDAO extends DAO<Comment, Object > {
    final String INSERT_SQL ="insert into Comments (CommentsID, CreationDate, Title, Description , ApplicatonViewId ) values (?,?,?,?,?)";
    final String UPDATE_SQL ="UPDATE  Comments  SET    CreationDate=?,  Title=?,  Description=?,  ApplicatonViewId=? WHERE CommentsID=? ";
    final String DELETE_SQL ="DELETE FROM Comments WHERE CommentsID=?";
    String SELECT_ALL_SQL = "SELECT * FROM Comments";
    String SELECT_BY_ID_SQL ="SELECT * FROM Comments where CommentsID=?";
    String SELECT_BY_NAME ="select * from Account where Name Like?";
    
    public static void main(String[] args) {
        // TODO code application logic here
    }

    @Override
    public void insert(Comment entity) {
        Connect_Jdbc.update(INSERT_SQL, entity.getCommentsID(),entity.getCreationDate(),entity.getTitle(),entity.getDescription(), entity.getApplicatonViewId());
    }

    @Override
    public void update(Comment entity) {
        Connect_Jdbc.update(UPDATE_SQL,entity.getCreationDate(),entity.getTitle(),entity.getDescription(), entity.getApplicatonViewId(),entity.getCommentsID());
    }

    @Override
    public void delete(Object key) {
        Connect_Jdbc.update(UPDATE_SQL, key);
    }

    @Override
    public List<Comment> selectAll() {
         return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public Comment selectByID(Object keys) {
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
                entity.setCommentsID(rs.getInt("CommentsID"));
                entity.setCreationDate(rs.getDate("CreationDate"));
                entity.setTitle(rs.getString("Title"));
                entity.setDescription(rs.getString("Description"));
                entity.setApplicatonViewId(rs.getInt("ApplicatonId"));
                list.add(entity);
               
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
     
    }
    
}

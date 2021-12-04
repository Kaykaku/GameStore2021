/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import model.News;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import javax.sql.rowset.serial.SerialBlob;
import model.Account;
import until.Connect_Jdbc;


/**
 *
 * @author Adminstator
 */
public class NewsDAO extends DAO<News, String> {

    private final String insert_sql = "insert into News(CreationDate,Title, Description, Contents,Image,EnableView,AccountId) values (?,?,?,?,?,?,?)";
    private final String update_sql = "update News set CreationDate = ?, Title = ?, Description = ?, Contents = ?, Image = ?,EnableView =?, AccountId = ? where NewsId = ?";
    private final String update_view_sql = "update News set views=? where NewsId = ?";
    private final String delete_sql = "delete from News where NewsId = ?";
    private final String select_all_sql = "select * from News";
    private final String select_active_sql = "select * from News where EnableView=1";
    private final String select_By_ID_sql = "select * from News where NewsId = ?";
    private final String select_By_Name = "select * from News where Title like ?";
    private final String select_By_KeyWord = "select * from News where (Title like ? or Description like ?) and EnableView=1";
    private final String select_By_Date = "select * from News where EnableView=1 order by CreationDate desc";

    @Override
    public void insert(News entity) {
        Connect_Jdbc.update(insert_sql,
                 entity.getCreationDate(),
                 entity.getTitle(),
                 entity.getDescription(),
                 entity.getContents(),
                 entity.getImage(),
                 entity.isToggle_Views(),
                 entity.getAccountId());
    }
    
    @Override
    public void update(News entity) {
        Connect_Jdbc.update(update_sql,
                 entity.getCreationDate(),
                 entity.getTitle(),
                 entity.getDescription(),
                 entity.getContents(),
                 entity.getImage(),
                 entity.isToggle_Views(),
                 entity.getAccountId(),
                 entity.getNewsID());
    }

    public void updateViews(News entity) {
        Connect_Jdbc.update(update_view_sql,
                 entity.getViews(),entity.getNewsID());
    }
    
    @Override
    public void delete(String key) {
        Connect_Jdbc.update(delete_sql, key);
    }

    @Override
    public List<News> selectAll() {
        return this.selectBySql(select_all_sql);
    }
 
    public List<News> selectActiveAll() {
        return this.selectBySql(select_active_sql);
    }
    @Override
    public News selectByID(String keys) {
        List<News> list = this.selectBySql(select_By_ID_sql, keys);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<News> selectByKeyWord(String keys) {
        return this.selectBySql(select_By_Name, "%" + keys + "%");
    }
    
    public List<News> selectActiveByKeyWord(String keys) {
        return this.selectBySql(select_By_KeyWord, "%" + keys + "%","%" + keys + "%");
    }

    @Override
    public List<News> selectBySql(String sql, Object... args) {
        List<News> list = new ArrayList<News>();
        try {
            ResultSet resultSet = Connect_Jdbc.query(sql, args);
            while (resultSet.next()) {
                News news = new News();
                news.setNewsID(resultSet.getInt("NewsID"));
                news.setCreationDate(resultSet.getDate("CreationDate"));
                news.setTitle(resultSet.getString("Title"));
                news.setDescription(resultSet.getString("Description"));
                news.setContents(resultSet.getString("Contents"));
                news.setImage(resultSet.getBytes("Image"));
                news.setAccountId(resultSet.getInt("AccountId"));
                news.setToggle_Views(resultSet.getBoolean("EnableView"));
                news.setViews(resultSet.getInt("Views"));
                list.add(news);
            }
            resultSet.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<News> selectByDate() {
        return this.selectBySql(select_By_Date);
    }
}

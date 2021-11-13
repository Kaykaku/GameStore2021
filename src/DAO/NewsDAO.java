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
import until.Connect_Jdbc;

/**
 *
 * @author leminhthanh
 */
public class NewsDAO extends DAO<News, String> {

    private String insert_sql = "insert into News(NewsId, CreationDate, Title, Description, Contents, Image, AccountId) values (?, ?, ?, ?, ?)";
    private String update_sql = "update News set CreationDate = ?, Title = ?, Description = ?, Contents = ?, Image = ?, AccountId = ? where NewsId = ?";
    private String delete_sql = "delete from News where NewsId = ?";
    private String select_all_sql = "select * from News";
    private String select_By_ID_sql = "select * from News where NewsId = ?";
    private String select_By_Name = "select * from News where Title like ?";

    @Override
    public void insert(News entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(News entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String key) {
        Connect_Jdbc.update(delete_sql, key);
    }

    @Override
    public List<News> selectAll() {
        return this.selectBySql(select_all_sql);
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

    @Override
    public List<News> selectBySql(String sql, Object... args) {
        List<News> list = new ArrayList<News>();
        try {
            ResultSet resultSet = Connect_Jdbc.query(sql, args);
            while (resultSet.next()) {
                News news = new News();

                news.setNewsId(resultSet.getInt("NewsID"));
                news.setCreationDate(resultSet.getDate("CreationDate"));
                news.setTitle(resultSet.getString("Title"));
                news.setDescription(resultSet.getString("Description"));
                news.setContents(resultSet.getString("Contents"));
                Blob blob = resultSet.getBlob("Hinh");
                if (blob != null) {
                    news.setImage(blob.getBytes(1, (int) blob.length()));
                }
                news.setAccountId(resultSet.getInt("AccountId"));

                list.add(news);
            }
            resultSet.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.ResultSet;
import model.Account;
import java.util.ArrayList;
import java.util.List;
import until.Connect_Jdbc;

/**
 *
 * @author leminhthanh
 */
public class AccountDAO extends DAO<Account, Integer> {

    private final String insert_sql = "insert into Accounts(Name, BirthDay, Gender, Image, Email, Address, Country, CreationDate,UserName,"
            + "Password, Active, Role, Comment) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
    private final String update_sql = "update Accounts set Name = ?, BirthDay = ?, Gender = ?, Image = ?, Email = ?, Address = ?, "
            + "Country = ?, CreationDate = ?,UserName =?,Password=?, Active = ?, Role = ?, Comment = ? where AccountId = ?";
    private final String delete_sql = "delete from Accounts where AccountId = ?";
    private final String select_all_sql = "select * from Accounts";
    private final String select_By_ID_sql = "select * from Accounts where AccountId = ?";
    private final String select_By_User_sql = "select * from Accounts where UserName = ?";
    private final String select_By_KeyWord = "select * from Accounts where (AccountId like ? or Name like ? or UserName like ? ) and Role like ? and Active like ? and Comment like ?";
    private final String update_Register = "update Accounts set Password = ? where Username = ?";
    private final String select_By_Email = "select * from Accounts where Email = ?";
    private final String insert_Register = "INSERT Accounts (Username, Email, [Password]) VALUES (?, ?, ?)";
    private final String select_By_AppView = "select * from Accounts where AccountId = (select Top 1 AccountId from ApplicationViews where ApplicationViewId =?)";
    
    @Override
    public void insert(Account entity) {
        Connect_Jdbc.update(insert_sql, entity.getName(), entity.getBirthDay(), entity.isGender(),
                entity.getImage(), entity.getEmail(), entity.getAddress(), entity.getCountry(), entity.getCreationDate(),
                entity.getUserName(), entity.getPassword(), entity.isActive(), entity.getRole(), entity.isComment());
    }

    @Override
    public void update(Account entity) {
        Connect_Jdbc.update(update_sql, entity.getName(), entity.getBirthDay(), entity.isGender(), entity.getImage(),
                entity.getEmail(), entity.getAddress(), entity.getCountry(), entity.getCreationDate(), entity.getUserName(), entity.getPassword(),
                entity.isActive(), entity.getRole(), entity.isComment(), entity.getAccountId());
    }

    @Override
    public void delete(Integer key) {
        Connect_Jdbc.update(delete_sql, key);
    }

    @Override
    public List<Account> selectAll() {
        return this.selectBySql(select_all_sql);
    }

    @Override
    public Account selectByID(Integer keys) {
        List<Account> list = this.selectBySql(select_By_ID_sql, keys);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<Account> selectByKeyWord(String keys) {
        return null ;
    }
    public List<Account> selectByKeyWord(String keys,int role,int active,int comment) {
        keys = "%" + keys + "%";
        String roleString="%";
        String activeString="%";
        String commentString="%";
        if(role!=-1){
            roleString+=role;
        }
        if(active!=-1){
            activeString+=active;
        }
        if(comment!=-1){
            commentString+=comment;
        }
        
        return this.selectBySql(select_By_KeyWord, keys, keys, keys,roleString,activeString,commentString);
    }
    
    @Override
    public List<Account> selectBySql(String sql, Object... args) {
        List<Account> list = new ArrayList<Account>();
        try {
            ResultSet resultSet = Connect_Jdbc.query(sql, args);
            while (resultSet.next()) {
                Account entity = new Account();
                entity.setAccountId(resultSet.getInt("AccountID"));
                entity.setName(resultSet.getString("Name"));
                entity.setBirthDay(resultSet.getDate("BirthDay"));
                entity.setGender(resultSet.getBoolean("Gender"));
                entity.setImage(resultSet.getBytes("Image"));
                entity.setEmail(resultSet.getString("Email"));
                entity.setAddress(resultSet.getString("Address"));
                entity.setCountry(resultSet.getString("Country"));
                entity.setCreationDate(resultSet.getDate("CreationDate"));
                entity.setUserName(resultSet.getString("UserName"));
                entity.setPassword(resultSet.getString("Password"));
                entity.setActive(resultSet.getBoolean("Active"));
                entity.setRole(resultSet.getInt("Role"));
                entity.setComment(resultSet.getBoolean("Comment"));

                list.add(entity);
            }
            resultSet.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Account selectByUser(String keys) {
        List<Account> list = this.selectBySql(select_By_User_sql, keys);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public void insert_Register(Account entity) {
        Connect_Jdbc.update(insert_Register, entity.getUserName(), entity.getEmail(), entity.getPassword());
    }

    public void update_Register(Account entity) {
        Connect_Jdbc.update(update_Register, entity.getPassword(), entity.getUserName());
    }

    public Account selectByEmail(String keys) {
        List<Account> list = this.selectBySql(select_By_Email, keys);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    public Account selectByAppViewID(Integer keys) {
        List<Account> list = this.selectBySql(select_By_AppView, keys);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}

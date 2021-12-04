/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    private final String insert_Register = "INSERT Accounts (Username, Email, [Password], creationDate, Role, Active) VALUES (?, ?, ?, ?, ?, ?)";
    private final String select_By_AppView = "select * from Accounts where AccountId = (select Top 1 AccountId from ApplicationViews where ApplicationViewId =?)";
    private final String select_Email = "select Email from Accounts ";
    private final String select_UserEmail = "select Email from Accounts where Role = 2";
    private final String select_TableSendMail = "select AccountId, Name,UserName,Email from Accounts ";
    private final String select_Role = "select * from Accounts where role=?";
    private final String select_Active = "select * from Accounts where Active=?";
    private final String select_Cmt = "select * from Accounts where Comment=?";
    private final String select_ActRoleCmt = "select * from Accounts where Active=? and Role =? and Comment = ?";
    private final String update_QRcode = "update Accounts set QRcode=? where AccountId = ?";
    private final String update_infor = "update Accounts set Name = ?, BirthDay = ?, Gender = ?,Image = ?,  Email = ?, Address = ?,"
            + "Country = ? where AccountId = ?";
    private final String update_Password = "update Accounts set Password=? where AccountId = ?";
    private final String update_Password2 = "update Accounts set Password=? where UserName = ?";
    
    @Override
    public void insert(Account entity) {
        Connect_Jdbc.update(insert_sql, entity.getName(), entity.getBirthDay(), entity.isGender(),
                entity.getImage(), entity.getEmail(), entity.getAddress(), entity.getCountry(), entity.getCreationDate(),
                entity.getUsername(), entity.getPassword(), entity.isActive(), entity.getRole(), entity.isComment());
    }

    @Override
    public void update(Account entity) {
        Connect_Jdbc.update(update_sql, entity.getName(), entity.getBirthDay(), entity.isGender(), entity.getImage(),
                entity.getEmail(), entity.getAddress(), entity.getCountry(), entity.getCreationDate(), entity.getUsername(), entity.getPassword(),
                entity.isActive(), entity.getRole(), entity.isComment(), entity.getAccountId());
    }
     public void updateInfor(Account entity) {
        Connect_Jdbc.update(update_infor, entity.getName(), entity.getBirthDay(), entity.isGender(), entity.getImage(),
                entity.getEmail(), entity.getAddress(), entity.getCountry(), entity.getAccountId());
    }
      public void updatePass(Account entity) {
        Connect_Jdbc.update(update_Password, entity.getPassword(), entity.getAccountId());
    }
     public void updatePass2(Account entity) {
        Connect_Jdbc.update(update_Password2, entity.getPassword(), entity.getUsername());
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

    @Override
    public List<Account> selectByKeyWord(String keys) {
        return null ;
    }
    public List<Account> selectRoles(String keys) {
        return this.selectBySql(select_Role,keys);
    }
    public List<Account> selectActive(String keys) {
        return this.selectBySql(select_Active,keys);
    }
    public List<Account> selectComment(String keys) {
        return this.selectBySql(select_Cmt,keys);
    }
    public List<Account> selectActRole(String keys,String key2,String keys3) {
        return this.selectBySql(select_ActRoleCmt,keys,key2,keys3);
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
    
    public List<Account> selectEmail() {
        List<Account> list = new ArrayList<>();
        try {
            ResultSet resultSet = Connect_Jdbc.query(select_Email);
            while (resultSet.next()) {
                String Email = resultSet.getString("Email");
                Account account = new Account(Email);
                list.add(account);
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException e) {
        }
        return list;
    }
    public List<Account> selectUserEmail() {
        List<Account> list = new ArrayList<>();
        try {
            ResultSet resultSet = Connect_Jdbc.query(select_UserEmail);
            while (resultSet.next()) {
                String Email = resultSet.getString("Email");
                Account account = new Account(Email);
                list.add(account);
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException e) {
        }
        return list;
    }
    public List<Account> selectBySql(String sql, Object... args) {
        List<Account> list = new ArrayList<>();
        try {
            ResultSet resultSet = Connect_Jdbc.query(sql, args);
            while (resultSet.next()) {
                Account entity = new Account();
                entity.setAccountId(resultSet.getInt("AccountId"));
                entity.setName(resultSet.getString("Name"));
                entity.setBirthDay(resultSet.getDate("BirthDay"));
                entity.setGender(resultSet.getBoolean("Gender"));
                entity.setImage(resultSet.getBytes("Image"));
                entity.setEmail(resultSet.getString("Email"));
                entity.setAddress(resultSet.getString("Address"));
                entity.setCountry(resultSet.getString("Country"));
                entity.setCreationDate(resultSet.getDate("CreationDate"));
                entity.setUsername(resultSet.getString("UserName"));
                entity.setPassword(resultSet.getString("Password"));
                entity.setActive(resultSet.getBoolean("Active"));
                entity.setRole(resultSet.getInt("Role"));
                entity.setComment(resultSet.getBoolean("Comment"));
                entity.setQRcode(resultSet.getString("QRcode"));
                list.add(entity);
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<Account> selectMail() {
        List<Account> list = new ArrayList<>();
        try {
            ResultSet resultSet = Connect_Jdbc.query(select_TableSendMail);
            while (resultSet.next()) {
                int AccountId = resultSet.getInt("AccountId");
                String Name = resultSet.getString("Name");
                String Email = resultSet.getString("Email");
                String UserName = resultSet.getString("UserName");
                Account entity = new Account(AccountId,Name,Email,UserName);
                list.add(entity);
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public void updateQRcode(Account entity){
        Connect_Jdbc.update(update_QRcode, entity.getQRcode(),entity.getAccountId());
    }
    public Account selectByUser(String keys) {
        List<Account> list = this.selectBySql(select_By_User_sql, keys);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public void insert_Register(Account entity) {
        Connect_Jdbc.update(insert_Register, entity.getUsername(), entity.getEmail(), entity.getPassword(), entity.getCreationDate(), entity.getRole(), entity.isActive());
    }

    public void update_Register(Account entity) {
        Connect_Jdbc.update(update_Register, entity.getPassword(), entity.getUsername());
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

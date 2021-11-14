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
import java.sql.Blob;
import until.Connect_Jdbc;

/**
 *
 * @author leminhthanh
 */
public class AccountDAO extends DAO<Account, String> {

    private final String insert_sql = "insert into Accounts(Name, BirthDay, Gender, Image, Email, Address, Coutry, CreationDate, Username, Password, Active, Role, Comment) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String update_sql = "update Accounts set Name = ?, BirthDay = ?, Gender = ?, Image = ?, Email = ?, Address = ?, Coutry = ?, CreationDate = ?, Username = ?, Password = ?, Active = ?, Role = ?, Comment = ? where AccountId = ?";
    private final String delete_sql = "delete from Accounts where AccountId = ?";
    private final String select_all_sql = "select * from Accounts";
    private final String select_By_ID_sql = "select * from Accounts where Username = ?";
    private final String select_By_Name = "select * from Accounts where Name like ?";
    private final String insert_Register = "INSERT Accounts (Username, Email, [Password]) VALUES (?, ?, ?)";
    
    @Override
    public void insert(Account entity) {
        Connect_Jdbc.update(insert_sql, entity.getAccountId(), entity.getName(), entity.getBirthDay(), entity.isGender(), entity.getImage(), entity.getEmail(), entity.getAddress(), entity.getCountry(),
                entity.getCreationDate(), entity.isActive(), entity.getRole(), entity.isComment());
    }

    @Override
    public void update(Account entity) {
        Connect_Jdbc.update(update_sql, entity.getName(), entity.getBirthDay(), entity.isGender(), entity.getImage(), entity.getEmail(), entity.getAddress(), entity.getCountry(),
                entity.getCreationDate(), entity.isActive(), entity.getRole(), entity.isComment(), entity.getAccountId());
    }

    @Override
    public void delete(String key) {
        Connect_Jdbc.update(delete_sql, key);
    }

    @Override
    public List<Account> selectAll() {
        return this.selectBySql(select_all_sql);
    }

    @Override
    public Account selectByID(String keys) {
        List<Account> list = this.selectBySql(select_By_ID_sql, keys);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Account> selectByKeyWord(String keys) {
        return this.selectBySql(select_By_Name, "%" + keys + "%");

    }

    @Override
    public List<Account> selectBySql(String sql, Object... args) {
        List<Account> list = new ArrayList<Account>();
        try {
            ResultSet resultSet = Connect_Jdbc.query(sql, args);
            while (resultSet.next()) {
                Account account = new Account();

                account.setAccountId(resultSet.getInt("AccountID"));
                account.setName(resultSet.getString("Name"));
                account.setBirthDay(resultSet.getDate("BirthDay"));
                account.setGender(resultSet.getBoolean("Gender"));
                Blob blob = resultSet.getBlob("Image");
                if (blob != null) {
                    account.setImage(blob.getBytes(1, (int) blob.length()));
                }
                account.setEmail(resultSet.getString("Email"));
                account.setAddress(resultSet.getString("Address"));
                account.setCountry(resultSet.getString("Country"));
                account.setCreationDate(resultSet.getDate("CreationDate"));
                account.setUsername(resultSet.getString("Username"));
                account.setPassword(resultSet.getString("Password"));
                account.setActive(resultSet.getBoolean("Active"));
                account.setRole(resultSet.getInt("Role"));
                account.setComment(resultSet.getBoolean("Comment"));

                list.add(account);
            }
            resultSet.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
   public void insert_Register(Account entity) {
        Connect_Jdbc.update(insert_Register, entity.getUsername(), entity.getEmail(), entity.getPassword());
    }

}

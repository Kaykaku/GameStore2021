/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.NewsDAO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import model.News;
import until.Connect_Jdbc;
import until.ProcessImage;

/**
 *
 * @author Admin
 */
public class test {
    static  List<News> list;
    public static void main(String[] args) throws FileNotFoundException {
        list =new NewsDAO().selectAll();
        int i=0;
        for (News acc : list) {
//            File f= ProcessImage.toFile(acc.getImage());
//            InputStream in = new FileInputStream(f);
//            updateImage(in, acc.getAccountId());
//            i++;
//            System.out.println(i);
        }
    }
    public static void updateImage(InputStream inputStream, Integer id) {
        String update_sql = "update News set Image = ? where NewsId = ?";
        Connect_Jdbc.update(update_sql, inputStream,id);
    }
    
}

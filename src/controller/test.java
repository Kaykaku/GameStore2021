/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.AccountDAO;
import DAO.ApplicationDAO;
import DAO.NewsDAO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import model.Account;
import model.Application;
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
        int i=1;
        for (News e : list) {
            if(i==6) i=1;
            File f= new File("C:\\Users\\Admin\\Downloads\\"+i+".jpg");
            e.setImage(ProcessImage.toBytes(f));
            i++;
            new  NewsDAO().update(e);
        }
    }
    public static void updateImage(InputStream inputStream, Integer id) {
        String update_sql = "update Applications set AppImage = ? where ApplicationID = ?";
        Connect_Jdbc.update(update_sql, inputStream,id);
    }
    
}

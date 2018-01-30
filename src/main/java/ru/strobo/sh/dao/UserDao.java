/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.strobo.sh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.strobo.sh.config.AppDataSource;
import ru.strobo.sh.data.User;

/**
 *
 * @author k.baukov
 */
@Component
public class UserDao {
    
    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(UserDao.class);
    
    public static final String GET_USERS = "SELECT * FROM \"PUBLIC\".USERS;";
    public static final String GET_USER = "SELECT * FROM \"PUBLIC\".USERS WHERE login=? AND pass=?;";
    
    
    @Autowired
    AppDataSource ds;
    
    public List<User> getUsers() {
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        List<User> list = new ArrayList<User>();
        
        try {
            conn = ds.getConnection();
 
            statement = conn.prepareStatement(GET_USERS);
            
            rs = statement.executeQuery();
            
            while(rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("user_type"),
                        rs.getString("login"),
                        rs.getString("pass"),
                        rs.getString("active_flag"),
                        rs.getDate("last_visit")
                );
                
                list.add(user);
            }
           
        } catch (SQLException ex) {
            Logger.error("Error while get users: "+ex.getMessage());
        } finally {
            try {
                if(rs!=null) rs.close();
                if(statement!=null) statement.close();
                if(conn!=null) conn.close();
            } catch (SQLException ex) {
                Logger.error("Error while connection resource release: "+ex.getMessage());
            }
        }
        
        return list;
    }
    
    public User auth(String login, String pass) {
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        User user = null;
        
        try {
            conn = ds.getConnection();
 
            statement = conn.prepareStatement(GET_USER);
            
            statement.setString(1, login);
            statement.setString(2, pass);
            
            rs = statement.executeQuery();
            
            if(rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("user_type"),
                        rs.getString("login"),
                        rs.getString("pass"),
                        rs.getString("active_flag"),
                        rs.getDate("last_visit")
                );
            }
           
        } catch (SQLException ex) {
            Logger.error("Error while get user: "+ex.getMessage());
        } finally {
            try {
                if(rs!=null) rs.close();
                if(statement!=null) statement.close();
                if(conn!=null) conn.close();
            } catch (SQLException ex) {
                Logger.error("Error while connection resource release: "+ex.getMessage());
            }
        }
        
        return user;
    }
    
}

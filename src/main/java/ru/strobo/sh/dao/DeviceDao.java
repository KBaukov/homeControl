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
import java.util.logging.Level;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.strobo.sh.config.AppDataSource;
import ru.strobo.sh.data.Device;

/**
 *
 * @author k.baukov
 */
@Component
public class DeviceDao {
    
    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(DeviceDao.class);
    
    public static final String GET_DEVICES = "SELECT * FROM \"PUBLIC\".DEVICES;";
    public static final String EDIT_DEVICE = "UPDATE \"PUBLIC\".DEVICES SET type = ?, name = ?, ip = ?, active_flag = ?, description = ? WHERE login=?;";
    
    
    @Autowired
    AppDataSource ds;
    
    public List<Device> getDevices() {
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        List<Device> list = new ArrayList<Device>();
        
        try {
            conn = ds.getConnection();
 
            statement = conn.prepareStatement(GET_DEVICES);
            
            rs = statement.executeQuery();
            
            while(rs.next()) {
                Device device = new Device(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("name"),
                        rs.getString("ip"),
                        rs.getString("active_flag"),
                        rs.getString("description")
                );
                
                list.add(device);
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
    
    public boolean editDevice(int deviceID, String type, String name, String ip, String isActive, String descr) {
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        try {
            conn = ds.getConnection();
 
            statement = conn.prepareStatement(EDIT_DEVICE);
            statement.setString(1, type);
            statement.setString(2, name);
            statement.setString(3, ip);
            statement.setString(4, isActive);
            statement.setString(5, descr);
            statement.setInt(6, deviceID);
            
            statement.executeUpdate();
            
            conn.commit();
           
        } catch (SQLException ex) {
            Logger.error("Error while get users: "+ex.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                java.util.logging.Logger.getLogger(DeviceDao.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return false;
        } finally {
            try {
                if(rs!=null) rs.close();
                if(statement!=null) statement.close();
                if(conn!=null) conn.close();
            } catch (SQLException ex) {
                Logger.error("Error while connection resource release: "+ex.getMessage());
            }
        }
        
        return true;
    }
    
    
}

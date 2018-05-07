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
    
    public static final String GET_DEVICES = "SELECT * FROM \"PUBLIC\".DEVICES ORDER BY id;";
    public static final String EDIT_DEVICE = "UPDATE \"PUBLIC\".DEVICES SET type = ?, name = ?, ip = ?, active_flag = ?, description = ? WHERE id=?;";
    public static final String DEL_DEVICE = "DELETE FROM \"PUBLIC\".DEVICES WHERE id=?;";
    public static final String ADD_DEVICE = "INSERT INTO \"PUBLIC\".DEVICES (type, name, ip, active_flag, description, id) VALUES (?,?,?,?,?,?);";
    public static final String LAST_DEVICE_ID = "SELECT max(id) as id FROM \"PUBLIC\".DEVICES;";
    
    
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
        int lastId = 0;
        
        try {
            conn = ds.getConnection();
             
            statement = conn.prepareStatement(LAST_DEVICE_ID);
            rs = statement.executeQuery();
            
            while(rs.next()) {
                lastId = rs.getInt("id");
            }
            
            if(deviceID>lastId) {
                statement = conn.prepareStatement(ADD_DEVICE);
            } else {
                statement = conn.prepareStatement(EDIT_DEVICE);
            }
            statement.setString(1, type);
            statement.setString(2, name);
            statement.setString(3, ip);
            statement.setString(4, isActive);
            statement.setString(5, descr);
            statement.setInt(6, deviceID);
            
            statement.executeUpdate();
            
            conn.commit();
           
        } catch (SQLException ex) {
            Logger.error("Error while inser or update device: "+ex.getMessage());
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
    
    public boolean deleteDevice(int deviceID) {
        
        Connection conn = null;
        PreparedStatement statement = null;        
        try {
            conn = ds.getConnection();
           
            statement = conn.prepareStatement(DEL_DEVICE);

            statement.setInt(1, deviceID);
            
            statement.executeUpdate();
            
            conn.commit();
           
        } catch (SQLException ex) {
            Logger.error("Error while delete device: "+ex.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                java.util.logging.Logger.getLogger(DeviceDao.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return false;
        } finally {
            try {
                if(statement!=null) statement.close();
                if(conn!=null) conn.close();
            } catch (SQLException ex) {
                Logger.error("Error while connection resource release: "+ex.getMessage());
            }
        }
        
        return true;
    }
    
    
}

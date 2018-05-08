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
import ru.strobo.sh.data.Map;
import ru.strobo.sh.data.MapSensor;

/**
 *
 * @author k.baukov
 */
@Component
public class MapsDao {
    
    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(DeviceDao.class);
    
    public static final String GET_MAPS = "SELECT * FROM \"PUBLIC\".MAPS ORDER BY id;";
    public static final String EDIT_MAP = "UPDATE \"PUBLIC\".MAPS SET title = ?, pict = ?, w = ?, h = ?, description = ? WHERE id=?;";
    public static final String DEL_MAP = "DELETE FROM \"PUBLIC\".MAPS WHERE id=?;";
    public static final String ADD_MAP = "INSERT INTO \"PUBLIC\".MAPS (title, pict, w, h, description, id) VALUES (?,?,?,?,?,?);";
    public static final String LAST_MAPS_ID = "SELECT max(id) as id FROM \"PUBLIC\".MAPS;";
    
    public static final String GET_MAP_SENSORS = "SELECT * FROM \"PUBLIC\".MAP_SENSORS WHERE map_id = ? ORDER BY id;";    
    public static final String EDIT_SENSOR = "UPDATE \"PUBLIC\".MAP_SENSORS SET map_id = ?, device_id = ?, type = ?, pict = ?, xk = ?, yk = ?, description = ? WHERE id=?;";
    public static final String DEL_SENSOR = "DELETE FROM \"PUBLIC\".MAP_SENSORS WHERE id=?;";
    public static final String ADD_SENSOR = "INSERT INTO \"PUBLIC\".MAP_SENSORS (map_id, device_id, type, pict, xk, yk, description, id) VALUES (?,?,?,?,?,?,?,?);";
    public static final String LAST_SENSORS_ID = "SELECT max(id) as id FROM \"PUBLIC\".MAP_SENSORS;";
    
    
    
    @Autowired
    AppDataSource ds;
    
    public List<Map> getMaps() {
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        List<Map> list = new ArrayList<Map>();
        
        try {
            conn = ds.getConnection();
 
            statement = conn.prepareStatement(GET_MAPS);
            
            rs = statement.executeQuery();
            
            while(rs.next()) {
                Map map = new Map(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("pict"),
                    rs.getInt("w"),
                    rs.getInt("h"),
                    rs.getString("description")
                );
                
                list.add(map);
            }
           
        } catch (SQLException ex) {
            Logger.error("Error while get maps: "+ex.getMessage());
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
    
    public boolean editMap(int mapID, String title, String pict, Integer w, Integer h, String descr) {
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null; 
        int lastId = 0;
        
        try {
            conn = ds.getConnection();
             
            statement = conn.prepareStatement(LAST_MAPS_ID);
            rs = statement.executeQuery();
            
            while(rs.next()) {
                lastId = rs.getInt("id");
            }
            
            if(mapID>lastId) {
                statement = conn.prepareStatement(ADD_MAP);
                mapID = lastId+1;
            } else {
                statement = conn.prepareStatement(EDIT_MAP);
            }
            statement.setString(1, title);
            statement.setString(2, pict);
            statement.setInt(3, w);
            statement.setInt(4, h);
            statement.setString(5, descr);
            statement.setInt(6, mapID);
            
            statement.executeUpdate();
            
            conn.commit();
           
        } catch (SQLException ex) {
            Logger.error("Error while insert or update map: "+ex.getMessage());
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
    
    public boolean deleteMap(int mapID) {
        
        Connection conn = null;
        PreparedStatement statement = null;        
        try {
            conn = ds.getConnection();
           
            statement = conn.prepareStatement(DEL_MAP);

            statement.setInt(1, mapID);
            
            statement.executeUpdate();
            
            conn.commit();
           
        } catch (SQLException ex) {
            Logger.error("Error while delete map: "+ex.getMessage());
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
    
    public List<MapSensor> getSensors(int mapId) {
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        List<MapSensor> list = new ArrayList<MapSensor>();
        
        try {
            conn = ds.getConnection();
 
            statement = conn.prepareStatement(GET_MAP_SENSORS);
            statement.setInt(1, mapId);
            
            rs = statement.executeQuery();
            
            while(rs.next()) {
                MapSensor sensor = new MapSensor(
                    rs.getInt("id"),
                    rs.getInt("map_id"),
                    rs.getInt("device_id"),
                    rs.getString("type"),
                    rs.getString("pict"),
                    rs.getFloat("xk"),
                    rs.getFloat("yk"),
                    rs.getString("description")
                );
                
                list.add(sensor);
            }
           
        } catch (SQLException ex) {
            Logger.error("Error while get sensors: "+ex.getMessage());
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
    
    public int getSensorsLastId() {
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        int lastId = 0;
        
        try {
            conn = ds.getConnection();
 
            statement = conn.prepareStatement(LAST_SENSORS_ID);
            rs = statement.executeQuery();
            
            while(rs.next()) {
                lastId = rs.getInt("id");
            }
           
        } catch (SQLException ex) {
            Logger.error("Error while get sensors: "+ex.getMessage());
        } finally {
            try {
                if(rs!=null) rs.close();
                if(statement!=null) statement.close();
                if(conn!=null) conn.close();
            } catch (SQLException ex) {
                Logger.error("Error while connection resource release: "+ex.getMessage());
            }
        }
        
        return lastId;
    }
    
    public boolean editSensor(int id, int mapID, int devID, String type, String pict, Float xk, Float yk, String descr) {
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null; 
        int lastId = 0;
        
        try {
            conn = ds.getConnection();
             
            statement = conn.prepareStatement(LAST_SENSORS_ID);
            rs = statement.executeQuery();
            
            while(rs.next()) {
                lastId = rs.getInt("id");
            }
            
            if(id>lastId) {
                statement = conn.prepareStatement(ADD_SENSOR);
                id = lastId+1;
            } else {
                statement = conn.prepareStatement(EDIT_SENSOR);
            }
            
            statement.setInt(1, mapID);
            statement.setInt(2, devID);
            statement.setString(3, type);
            statement.setString(4, pict);
            statement.setFloat(5, xk);
            statement.setFloat(6, yk);
            statement.setString(7, descr);
            statement.setInt(8, id);
            
            statement.executeUpdate();
            
            conn.commit();
           
        } catch (SQLException ex) {
            Logger.error("Error while insert or update sensor: "+ex.getMessage());
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
    
    public boolean deleteSensor(int id) {
        
        Connection conn = null;
        PreparedStatement statement = null;        
        try {
            conn = ds.getConnection();
           
            statement = conn.prepareStatement(DEL_SENSOR);

            statement.setInt(1, id);
            
            statement.executeUpdate();
            
            conn.commit();
           
        } catch (SQLException ex) {
            Logger.error("Error while delete sensor: "+ex.getMessage());
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
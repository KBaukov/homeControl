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
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.strobo.sh.beans.KotelBean;
import ru.strobo.sh.config.AppDataSource;

/**
 *
 * @author k.baukov
 */
@Component
public class KotelDao {
    
    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(KotelDao.class);
    
    public static final String GET_SETINGS = "SELECT * FROM \"PUBLIC\".KOTEL;";
    public static final String SET_SETINGS = "UPDATE \"PUBLIC\".KOTEL SET dest_tp = ?, dest_to = ?, dest_tc = ?, dest_kw = ? WHERE 1;";
    
    @Autowired
    AppDataSource ds;
    
    @Autowired
    KotelBean kotel;
    
    public void getAllSetings() {
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        try {
            conn = ds.getConnection();
 
            statement = conn.prepareStatement(GET_SETINGS);
            
            rs = statement.executeQuery();
            
            while(rs.next()) {
                kotel.setDestTp(rs.getFloat("dest_tp"));
                kotel.setDestTo(rs.getFloat("dest_to"));
                kotel.setDestTc(rs.getFloat("dest_tc"));
                kotel.setDestKw(rs.getInt("dest_kw"));
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

    }
    
    public void setAllSetings() {
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        try {
            conn = ds.getConnection();
 
            statement = conn.prepareStatement(SET_SETINGS);
            statement.setFloat(1, kotel.getDestTp());
            statement.setFloat(2, kotel.getDestTo());
            statement.setFloat(3, kotel.getDestTc());
            statement.setInt(4, kotel.getDestKw());
            
            statement.executeUpdate();
           
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

    }
    
}

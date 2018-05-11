/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.strobo.sh.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.strobo.sh.dao.DeviceDao;
import ru.strobo.sh.data.Device;
import ru.strobo.sh.data.RoomData;
import ru.strobo.sh.ws.DeviceSessionsHandler;

/**
 *
 * @author k.baukov
 */
@Component
public class RoomDataBean {
    
    @Autowired
    DeviceDao dDao;
    
    @Autowired
    DeviceSessionsHandler sh;
    
    private Map<String, RoomData> roomData = new HashMap<String, RoomData>();
    private Map<Integer, String> roomDataMap = new HashMap<Integer, String>();
    
    @PostConstruct
    public void init() {        
        getDevices();
    }
    
    
    public RoomData getRoomData(int devId) {
        String devName = roomDataMap.get(devId);
        if(devName==null)
            return null;
        
        return roomData.get(devName);
    }
    
    private void getDevices() {
        List<Device> dd = dDao.getDevices();
        for(Device d : dd) {
            roomDataMap.put(d.getId(), d.getName());
            if(d.getType().equals("KotelController"))
                sh.setKotelControllerId(d.getName());
        }
    }
    
    public void setRoomsData(RoomData data) {
        roomData.put(data.getDeviceId(), data);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.strobo.sh.beans;

import java.util.HashMap;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.strobo.sh.dao.KotelDao;
import ru.strobo.sh.data.KotelData;
import ru.strobo.sh.data.RoomData;
import ru.strobo.sh.data.Device;
import java.util.Map;
import java.util.List;
import ru.strobo.sh.dao.DeviceDao;
import ru.strobo.sh.ws.DeviceSessionsHandler;

/**
 *
 * @author Strobo
 */

@Component
public class KotelBean {
    
    private float tp;
    private float to;
    private float pr;
    private int kw;
    
    private float destTp;
    private float destTo;
    private float destTc;
    private int destKw;
    private float destPr;
    
    private Map<String, RoomData> roomData = new HashMap<String, RoomData>();
    private Map<Integer, String> roomDataMap = new HashMap<Integer, String>();
    
//    private float t1;
//    private float t2;
//    private float t3;
//    private float h1;
//    private float h2;
//    private float h3;
    
    
    private int wait;
    
    private String controlCommand;
    
    @Autowired
    KotelDao kotelDao;
    
    @Autowired
    DeviceDao dDao;
    
    @Autowired
    DeviceSessionsHandler sh;

    @PostConstruct
    public void init() {
        
        kotelDao.getAllSetings();
        
        getDevices();
        
        this.tp = 34.55f;
        this.to = 59.15f;
        this.kw = 11;
        this.pr = 2.2f;
//        this.t1 = 20.00f;
//        this.t2 = 20.00f;
//        this.t3 = 20.00f;
//        
//        this.h1 = 20.00f;
//        this.h2 = 20.00f;
//        this.h3 = 20.00f;
        
        this.wait = 30000;
        
        this.controlCommand = "";
    }

    public float getTp() {
        return tp;
    }

    public void setTp(float tp) {
        this.tp = tp;
    }

    public float getTo() {
        return to;
    }

    public void setTo(float to) {
        this.to = to;
    }

    public int getKw() {
        return kw;
    }

    public void setKw(int kw) {
        this.kw = kw;
    }

    public float getPr() {
        return pr;
    }

    public void setPr(float pr) {
        this.pr = pr;
    }

    public float getT1() {
        RoomData rd = roomData.get(roomDataMap.get(1));                
        return rd.getT();
    }

    public float getT2() {
        RoomData rd = roomData.get(roomDataMap.get(2));                
        return rd.getT();
    }

    public float getT3() {
        RoomData rd = roomData.get(roomDataMap.get(3));                
        return rd.getT();
    }

    public float getH1() {
        RoomData rd = roomData.get(roomDataMap.get(1));                
        return rd.getH();
    }

    public float getH2() {
        RoomData rd = roomData.get(roomDataMap.get(2));                
        return rd.getH();
    }

    public float getH3() {
        RoomData rd = roomData.get(roomDataMap.get(3));                
        return rd.getH();
    }
    
    public float getDestTp() {
        return destTp;
    }

    public void setDestTp(float destTp) {
        this.destTp = destTp;
    }

    public float getDestTc() {
        return destTc;
    }

    public void setDestTc(float destTc) {
        this.destTc = destTc;
    }

    public float getDestTo() {
        return destTo;
    }

    public void setDestTo(float destTo) {
        this.destTo = destTo;
    }

    public int getDestKw() {
        return destKw;
    }

    public void setDestKw(int destKw) {
        this.destKw = destKw;
    }

    public float getDestPr() {
        return destPr;
    }

    public void setDestPr(float destPr) {
        this.destPr = destPr;
    }
    
    public String getControlCommand() {
        return controlCommand;
    }

    public void setControlCommand(String controlCommand) {
        this.controlCommand = controlCommand;
    }

    public int getWait() {
        return wait;
    }

    public void setWait(int wait) {
        this.wait = wait;
    }
    
    private void getDevices() {
        List<Device> dd = dDao.getDevices();
        for(Device d : dd) {
            roomDataMap.put(d.getId(), d.getName());
            if(d.getType().equals("KotelController"))
                sh.setKotelControllerId(d.getName());
        }
    }
    
    public String toJson() {
        return "";
    }
    
    public void setKotelMeshData(KotelData data) {
        
        tp = data.getTp();
        to = data.getTo();
        kw = data.getKw();
        pr = data.getPr();
        
    }
    
    public void setRoomsData(RoomData data) {
        
        roomData.put(data.getDeviceId(), data);
        
    }
}

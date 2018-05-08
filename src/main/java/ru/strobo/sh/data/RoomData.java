/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.strobo.sh.data;

/**
 *
 * @author Strobo
 */
public class RoomData {
    
    private String type;
    private String deviceId;
    private float t;
    private float h;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public float getT() {
        return t;
    }

    public void setT0(float t) {
        this.t = t;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n***** Room Data Details *****\n");
        sb.append("type="+type+"\n");
        sb.append("deviceId="+deviceId+"\n");
        sb.append("t="+t+"; "); sb.append("h="+h+"\n");
        sb.append("*****************************");

        return sb.toString();
    }
}

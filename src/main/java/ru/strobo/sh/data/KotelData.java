/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.strobo.sh.data;

import java.util.Date;

/**
 *
 * @author Strobo
 */
public class KotelData {
    
    private String type;
    private String deviceId;
    private float tp;
    private float to;
    private float pr;
    private int kw;
    private Date date = new Date();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    public float getPr() {
        return pr;
    }

    public void setPr(float pr) {
        this.pr = pr;
    }
    
    public int getKw() {
        return kw;
    }

    public void setKw(int kw) {
        this.kw = kw;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append("\n***** Kotel Data Details *****\n");
            sb.append("date="+date+"\n");
            sb.append("type="+type+"; ");
            sb.append("deviceId="+deviceId+"\n");
            sb.append("tp="+tp+"; ");
            sb.append("to="+to+"; ");
            sb.append("kw="+kw+"; ");
            sb.append("pr="+pr+"\n");
            sb.append("*****************************\n");

            return sb.toString();
    }
}

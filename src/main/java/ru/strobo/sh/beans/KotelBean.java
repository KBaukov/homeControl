/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.strobo.sh.beans;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.strobo.sh.dao.KotelDao;

/**
 *
 * @author Strobo
 */

@Component
public class KotelBean {
    
    private float tp;
    private float to;
    private int kw;
    private float t1;
    private float t2;
    private float t3;
    private float h1;
    private float h2;
    private float h3;
    private float destTp;
    private float destTo;
    private float destTc;
    private int destKw;
    
    private int wait;
    
    private String controlCommand;
    
    @Autowired
    KotelDao kotelDao;

    @PostConstruct
    public void init() {
        
        kotelDao.getAllSetings();
        
        this.tp = 34.55f;
        this.to = 59.15f;
        this.kw = 11;
        this.t1 = 20.00f;
        this.t2 = 20.00f;
        this.t3 = 20.00f;
        
        this.h1 = 20.00f;
        this.h2 = 20.00f;
        this.h3 = 20.00f;
        
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

    
    public float getT1() {
        return t1;
    }

    public void setT1(float t1) {
        this.t1 = t1;
    }

    public float getT2() {
        return t2;
    }

    public void setT2(float t2) {
        this.t2 = t2;
    }

    public float getT3() {
        return t3;
    }

    public void setT3(float t3) {
        this.t3 = t3;
    }

    public float getH1() {
        return h1;
    }

    public void setH1(float h1) {
        this.h1 = h1;
    }

    public float getH2() {
        return h2;
    }

    public void setH2(float h2) {
        this.h2 = h2;
    }

    public float getH3() {
        return h3;
    }

    public void setH3(float h3) {
        this.h3 = h3;
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
    
    public String toJson() {
        return "";
    }
    
}

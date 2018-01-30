/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.strobo.sh.data;

/**
 *
 * @author k.baukov
 */
public class Device {
    private Integer id;
    private String type;
    private String name;
    private String ip;
    private String activeFlag;
    private String description;

    public Device(Integer id, String type, String name, String ip, String activeFlag, String description) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.ip = ip;
        this.activeFlag = activeFlag;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }    
    
     public String toJson() {
        return "{"
               + "id:" + id 
               + ",type:\"" + type + "\""
               + ",name:\"" + name  + "\""
               + ",ip:\"" + ip  + "\""
               + ",active_flag:\"" + activeFlag  + "\""
               + ",description:\"" + description  + "\""
        + "}"; 
    }
    
}

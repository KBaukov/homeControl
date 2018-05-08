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
public class MapSensor {
    private Integer id;
    private Integer mapId;
    private Integer devId;
    private String pict;
    private String type;
    private float xk;
    private float yk;
    private String description;

    public MapSensor(Integer id, Integer mapId, Integer devId, String pict, String type, float xk, float yk, String description) {
        this.id = id;
        this.mapId = mapId;
        this.devId = devId;
        this.pict = pict;
        this.type = type;
        this.xk = xk;
        this.yk = yk;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMapId() {
        return mapId;
    }

    public void setMapId(Integer mapId) {
        this.mapId = mapId;
    }

    public Integer getDevId() {
        return devId;
    }

    public void setDevId(Integer devId) {
        this.devId = devId;
    }

    public String getPict() {
        return pict;
    }

    public void setPict(String pict) {
        this.pict = pict;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getXk() {
        return xk;
    }

    public void setXk(float xk) {
        this.xk = xk;
    }

    public float getYk() {
        return yk;
    }

    public void setYk(float yk) {
        this.yk = yk;
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
               + ",map_id:\"" + mapId + "\""
               + ",dev_id:\"" + devId + "\""
               + ",pict:\"" + pict  + "\""
               + ",xk:\"" + xk  + "\""
               + ",yk:\"" + yk  + "\""
               + ",description:\"" + description  + "\""
        + "}"; 
    }
}

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
public class Map {
    private Integer id;
    private String title;
    private String pict;
    private Integer w;
    private Integer h;
    private String description;

    public Map(Integer id, String title, String pict, Integer w, Integer h, String description) {
        this.id = id;
        this.title = title;
        this.pict = pict;
        this.w = w;
        this.h = h;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPict() {
        return pict;
    }

    public void setPict(String pict) {
        this.pict = pict;
    }

    public Integer getW() {
        return w;
    }

    public void setW(Integer w) {
        this.w = w;
    }

    public Integer getH() {
        return h;
    }

    public void setH(Integer h) {
        this.h = h;
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
               + ",title:\"" + title + "\""
               + ",pict:\"" + pict  + "\""
               + ",w:\"" + w  + "\""
               + ",h:\"" + h  + "\""
               + ",description:\"" + description  + "\""
        + "}"; 
    }
    
}

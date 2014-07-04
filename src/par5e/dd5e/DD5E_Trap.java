/*
 * 
 * PAR5E Fantasy Grounds Ruleset Module Parser Tool
 * Developed by Zeus. Copyright 2014
 * 
 */

package par5e.dd5e;

import java.util.LinkedHashMap;

/**
 *
 * @author zeph
 */
public class DD5E_Trap extends DD5E_BaseClass {
    
    //Variables
    public String type;
    public String size;
    public String detect;
    public String eoc;
    public String eoo;
    public String cm;
    public Integer level;
    public Integer xp;
    public String token;
    
    //Constructors
    public DD5E_Trap(String aName) {
        name = aName;
        type = "";
        size = "";
        detect = "";
        eoc = "";
        eoo = "";
        cm = "";
        level = -1;
        xp = -1;
        text = "";
        token = "";
    }
    
    //Public methods
    public void setType(String aNew) {
        type = aNew;
    }
    public void setSize(String aNew) {
        size = aNew;
    }
    public void setDetect(String aNew) {
        detect = aNew;
    }
    public void setEOC(String aNew) {
        eoc = aNew;
    }
    public void setEOO(String aNew) {
        eoo = aNew;
    }
    public void setCM(String aNew) {
        cm = aNew;
    }
    public void setLevel(Integer aNew) {
        level = aNew;
    }
    public void setXP(Integer aNew) {
        xp = aNew;
    }
    public void setToken(String aNew) {
        token = aNew;
    }
    
    public String getType() {
        return type;
    }
    public String getSize() {
        return size;
    }
    public String getDetect() {
        return detect;
    }
    public String getEOC() {
        return eoc;
    }
    public String getEOO() {
        return eoo;
    }
    public String getCM() {
        return cm;
    }
    public Integer getLevel() {
        return level;
    }
    public Integer getXP() {
        return xp;
    }
    public String getToken() {
        return token;
    }
    
    @Override
    public boolean isComplete() {
        boolean complete = true;
        if (type.equals("") || size.equals("") || eoc.equals("") || eoo.equals("") || cm.equals("") || level.equals(-1) || xp.equals(-1)) {
            complete = false;
        } 
        return complete;
    }

    
    
}

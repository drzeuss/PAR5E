/*
 * 
 * PAR5E Fantasy Grounds Ruleset Module Parser Tool
 * Developed by Zeus. Copyright 2014
 * 
 */

package par5e.dd5e;

import java.io.Serializable;

/**
 *
 * @author zeph
 */
public class DD5E_ClassFeature extends DD5E_BaseClass implements Serializable {
    
    public String level;
    public String specialization;
    
    public DD5E_ClassFeature(String aNew) {
        name = aNew;
        text = "";
        level = "1";
        specialization = "";
    }
    
    public String getLevel() {
        return level;
    }
    public String getSpecialization() {
        return specialization;
    }
    public void setLevel(String aNew) {
        level = aNew;
    }
    public void setSpecialization(String aNew) {
        specialization = aNew;
    }
    @Override
    public boolean isComplete() {
        boolean complete = true;
        if (text.equals("")) {
            complete = false;
        } 
        return complete;
    }
}

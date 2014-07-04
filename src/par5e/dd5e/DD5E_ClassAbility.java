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
public class DD5E_ClassAbility extends DD5E_BaseClass implements Serializable {
    
    public String level;
    
    public DD5E_ClassAbility(String aNew) {
        name = aNew;
        text = "";
        level = "1";
    }
    
    public String getLevel() {
        return level;
    }
    public void setLevel(String aNew) {
        level = aNew;
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

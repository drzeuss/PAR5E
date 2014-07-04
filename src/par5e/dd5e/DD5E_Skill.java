/*
 * 
 * PAR5E Fantasy Grounds Ruleset Module Parser Tool
 * Developed by Zeus. Copyright 2014
 * 
 */

package par5e.dd5e;

/**
 *
 * @author zeph
 */
public class DD5E_Skill extends DD5E_BaseClass {
    //Variables
    public String stat;
    
    //Constructors
    public DD5E_Skill(String aName) {
        name = aName;
        text = "";
        stat = "";        
    }
    
    //Methods
    public void setStat(String sNew) {
        stat = sNew;
    }
    public String getStat() {
        return stat;
    }
    
    @Override
    public boolean isComplete() {
        boolean complete = true;
        if (stat.equals("") || text.equals("")) {
            complete = false;
        }
        return complete;
    }
}
    


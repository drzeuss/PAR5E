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
public class DD5E_SubRace extends DD5E_BaseClass {
    
    //Variables
    
    public LinkedHashMap<String,String> traits;
    
    //Constructors
    public DD5E_SubRace(String aNew) {
        name = aNew;
        text = "";
        traits = new LinkedHashMap<>();
    }
    
    //Methods
    public void setTraits(LinkedHashMap<String,String> aNew) {
        traits = aNew;
    }
    public void addTrait(String aName, String aText) {
        traits.put(aName, aText);
    }
    public LinkedHashMap<String,String> getTraits() {
        return traits;
    }
    
    @Override
    public boolean isComplete() {
        boolean complete = true;
        if (traits.isEmpty()) {
            complete = false;
        }
        return complete;
    }
}

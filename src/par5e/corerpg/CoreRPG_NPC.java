/*
 * 
 * PAR5E Fantasy Grounds Ruleset Module Parser Tool
 * Developed by Zeus. Copyright 2014
 * 
 */

package par5e.corerpg;

import java.util.LinkedHashMap;

/**
 *
 * @author zeph
 */
public class CoreRPG_NPC extends CoreRPG_BaseClass{
    
    //Variables
    public Integer space;
    public Integer reach;
    public String skills;
    public String languages;
    public String equipment;
    public String token;
    
    
    //Constructors
    public CoreRPG_NPC(String aName) {
        name = aName;
        text = "";
        space = -1;
        reach = -1;
        skills = "";
        languages = "";
        equipment = "";
        token = "";
        source = "";
    }
    
    //Public methods
    public void setSpace(Integer aNew) {
        space = aNew;
    }
    public void setReach(Integer aNew) {
        reach = aNew;
    }
    public void setSkills(String aNew) {
        skills = aNew;
    }
    public void setLanguages(String aNew) {
        languages = aNew;
    }
    public void setEquipment(String aNew) {
        equipment = aNew;
    }
    public void setToken(String aNew) {
        token = aNew;
    }

    public Integer getSpace() {
        return space;
    }
    public Integer getReach() {
        return reach;
    }
    public String getSkills() {
        return skills;
    }
    public String getLanguages() {
        return languages;
    }
    public String getEquipment() {
        return equipment;
    }
    public String getToken() {
        return token;
    }
    
    @Override
    public boolean isComplete() {
        boolean complete = true;
        if (space.equals(-1) || reach.equals(-1) || skills.equals("") || languages.equals("") || equipment.equals("")) {
            complete = false;
        } 
        return complete;
    }
}

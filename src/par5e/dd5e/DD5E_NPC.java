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
public class DD5E_NPC extends DD5E_BaseClass{
    
    //Variables
    public String type;
    public String size;
    public Integer ac;
    public String actext;
    public Integer hp;
    public String hd;
    public String speed;
    public String senses;
    public LinkedHashMap<String,Integer> abilities;
    public String alignment;
    public String languages;
    public LinkedHashMap<String,String> traits;
    public LinkedHashMap<String,String> reactions;
    public LinkedHashMap<String,String> actions;
    public Integer level;
    public Integer xp;
    public String token;
    
    //Constructors
    public DD5E_NPC(String aName) {
        name = aName;
        type = "";
        size = "";
        ac = -1;
        hp = -1;
        hd = "";
        speed = "";
        senses = "";
        abilities = new LinkedHashMap<>();
        alignment = "";
        languages = "";
        traits = new LinkedHashMap<>();
        reactions = new LinkedHashMap<>();
        actions = new LinkedHashMap<>();
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
    public void setAC(Integer aNew) {
        ac = aNew;
    }
    public void setACText(String aNew) {
        actext = aNew;
    }
    public void setHP(Integer aNew) {
        hp = aNew;
    }
    public void setHD(String aNew) {
        hd = aNew;
    }
    public void setSpeed(String aNew) {
        speed = aNew;
    }
    public void setSenses(String aNew) {
        senses = aNew;
    }
    public void setAbilities(LinkedHashMap<String,Integer> aMap) {
        abilities = aMap;
    }
    public void setAlignment(String aNew) {
        alignment = aNew;
    }
    public void setLanguages(String aNew) {
        languages = aNew;
    }
    public void setTraits(LinkedHashMap<String,String> aMap) {
        traits = aMap;
    }
    public void setActions(LinkedHashMap<String,String> aMap) {
        actions = aMap;
    }
    public void setReactions(LinkedHashMap<String,String> aMap) {
        reactions = aMap;
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
    public Integer getAC() {
        return ac;
    }
    public String getACText() {
        return actext;
    }
    public Integer getHP() {
        return hp;
    }
    public String getHD() {
        return hd;
    }
    public String getSpeed() {
        return speed;
    }
    public String getSenses() {
        return senses;
    }
    public LinkedHashMap<String,Integer> getAbilities() {
        return abilities;
    }
    public String getAlignment() {
        return alignment;
    }
    public String getLanguages() {
        return languages;
    }
    public LinkedHashMap<String,String> getTraits() {
        return traits;
    }
    public LinkedHashMap<String,String> getReactions() {
        return reactions;
    }
    public LinkedHashMap<String,String> getActions() {
        return actions;
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
        if (type.equals("") || size.equals("") || ac.equals(-1) || hp.equals(-1) || hd.equals("") || speed.equals("") ||  
                abilities.isEmpty() || alignment.equals("") || languages.equals("") || level.equals(-1) || xp.equals(-1)) {
            complete = false;
        } 
        return complete;
    }
}

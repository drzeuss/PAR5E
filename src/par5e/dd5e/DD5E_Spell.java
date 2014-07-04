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
public class DD5E_Spell extends DD5E_BaseClass {
    
    public Integer level;
    public String school;
    public String source;
    public String castingtime;
    public String components;
    public String descriptors;
    public String duration;
    public String range;
    
    public DD5E_Spell(String aNew) {
        name = aNew;
        text = "";
        level = -1;        
        school = "";
        source = "";
        castingtime = "";
        components = "";
        descriptors = "";
        duration = "";
        range = "";
    }
    
    public void setLevel(Integer aNew) {
        level = aNew;
    }
    public void setSchool(String aNew) {
        school = aNew;
    }
    public void setSource(String aNew) {
        source = aNew;
    }
    public void setComponents(String aNew) {
        components = aNew;
    }
    public void setCastingTime(String aNew) {
        castingtime = aNew;
    }
    public void setDescriptors(String aNew) {
        descriptors = aNew;
    }
    public void setDuration(String aNew) {
        duration = aNew;
    }
    public void setRange(String aNew) {
        range = aNew;
    }

    public Integer getLevel() {
        return level;
    }
    public String getSchool() {
        return school;
    }
    public String getSource() {
        return source;
    }
    public String getCastingTime() {
        return castingtime;
    }
    public String getComponents() {
        return components;
    }
    public String getDescriptors() {
        return descriptors;
    }
    public String getDuration() {
        return duration;
    }
    public String getRange() {
        return range;
    }
    
    @Override
    public boolean isComplete() {
        boolean complete = true;
        if (text.equals("") || level.equals(-1) || school.equals("") || source.equals("") || castingtime.equals("") || duration.equals("") || range.equals("")) {
           complete = false;
        }
        return complete;
    }
    
    
}

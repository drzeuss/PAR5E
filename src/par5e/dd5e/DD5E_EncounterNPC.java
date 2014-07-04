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
public class DD5E_EncounterNPC extends DD5E_BaseClass {
    //Variables
    public Integer number;
    public Integer level;

    //Constructors
    public DD5E_EncounterNPC() {
        name = "";
        number = -1;
        level = -1;
    }
    public DD5E_EncounterNPC(String aName) {
        name = aName;
        number = -1;
        level = -1;
    }
    public DD5E_EncounterNPC(String aName, Integer aNum, Integer aLevel) {
        name = aName;
        number = aNum;
        level = aLevel;
    }

    //Methods
    public void setNumber(Integer aNum) {
        number = aNum;
    }
    public void setLevel(Integer aNum) {
        level = aNum;
    }
    public Integer getNumber() {
        return number;
    }
    public Integer getLevel() {
            return level;
        }
}

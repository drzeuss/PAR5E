/*
 * 
 * PAR5E Fantasy Grounds Ruleset Module Parser Tool
 * Developed by Zeus. Copyright 2014
 * 
 */

package par5e.corerpg;

/**
 *
 * @author zeph
 */
public class CoreRPG_EncounterNPC extends CoreRPG_BaseClass {
    //Variables
    public Integer number;
    public Integer level;

    //Constructors
    public CoreRPG_EncounterNPC() {
        name = "";
        number = -1;
        level = -1;
    }
    public CoreRPG_EncounterNPC(String aName) {
        name = aName;
        number = -1;
        level = -1;
    }
    public CoreRPG_EncounterNPC(String aName, Integer aNum, Integer aLevel) {
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

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
public class DD5E_MagicItem extends DD5E_BaseClass {
    
    //Variables
    public String type;
    public String subtype;
    public String rarity;
    
    //Constructors
    public DD5E_MagicItem(String aName) {
        name = aName;
    }
    public DD5E_MagicItem(String aName, String aType, String aSubtype, String aRarity, String aText) {
        name = aName;
        type = aType;
        subtype = aSubtype;
        rarity = aRarity;
        text = aText;
    }
    
    //Methods
    public void setType(String aNew) {
        type = aNew;
    }
    public void setSubType(String aNew) {
        subtype = aNew;
    }
    public void setRarity(String aNew) {
        rarity = aNew;
    }

    public String getType() {
        return type;
    }
    public String getSubType() {
        return subtype;
    }
    public String getRarity() {
        return rarity;
    }

    @Override
    public boolean isComplete() {
        boolean complete = true;
        if (type.equals("") && subtype.equals("") && rarity.equals("") && text.equals("")) {
            complete = false;
        }
        return complete;
    }
}

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
public class CoreRPG_MagicItem extends CoreRPG_BaseClass {
    
    //Variables
    
    //Constructors
    public CoreRPG_MagicItem(String aName) {
        name = aName;
        text = "";
    }
    public CoreRPG_MagicItem(String aName, String aText) {
        name = aName;
        text = aText;
    }
    
    //Methods

    @Override
    public boolean isComplete() {
        boolean complete = true;
        if (text.equals("")) {
            complete = false;
        }
        return complete;
    }
}

/*
 * 
 * PAR5E Fantasy Grounds Ruleset Module Parser Tool
 * Developed by Zeus. Copyright 2014
 * 
 */


package par5e.corerpg;

import java.util.Arrays;

/**
 *
 * @author zeph
 */
public class CoreRPG_ImagePins extends CoreRPG_BaseClass {
    //Variables
    public CoreRPG_ImagePin[] shortcuts;
                
    //Constructors
    public CoreRPG_ImagePins() {
        
    }   
    public CoreRPG_ImagePins(String aName) {
        name = aName;
        text = "";
        shortcuts = new CoreRPG_ImagePin[0];
    }   
    
    public void addShortcut(String aName, Integer x, Integer y, String refClass, String refName) {
        shortcuts = Arrays.copyOf(shortcuts, shortcuts.length+1);
        CoreRPG_ImagePin pin = new CoreRPG_ImagePin(aName, x, y, refClass, refName);
        shortcuts[shortcuts.length-1] = pin;
    }
    public CoreRPG_ImagePin[] getShortCuts() {
        return shortcuts;
    }
    
    
}

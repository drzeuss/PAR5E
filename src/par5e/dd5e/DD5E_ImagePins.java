/*
 * 
 * PAR5E Fantasy Grounds Ruleset Module Parser Tool
 * Developed by Zeus. Copyright 2014
 * 
 */


package par5e.dd5e;

import java.util.Arrays;

/**
 *
 * @author zeph
 */
public class DD5E_ImagePins extends DD5E_BaseClass {
    //Variables
    public DD5E_ImagePin[] shortcuts;
                
    //Constructors
    public DD5E_ImagePins() {
        
    }   
    public DD5E_ImagePins(String aName) {
        name = aName;
        text = "";
        shortcuts = new DD5E_ImagePin[0];
    }   
    
    public void addShortcut(String aName, Integer x, Integer y, String refClass, String refName) {
        shortcuts = Arrays.copyOf(shortcuts, shortcuts.length+1);
        DD5E_ImagePin pin = new DD5E_ImagePin(aName, x, y, refClass, refName);
        shortcuts[shortcuts.length-1] = pin;
    }
    public DD5E_ImagePin[] getShortCuts() {
        return shortcuts;
    }
    
    
}

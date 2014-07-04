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
public class CoreRPG_Token extends CoreRPG_BaseClass {
    //ariables
    public String path;
    
    //Constructors
    public CoreRPG_Token() {
        
    }
    public CoreRPG_Token(String aName, String aPath) {
        name = aName;
        text = "";
        path = aPath;
    }
    
    //Methods
    public void setPath(String aPath) {
        path = aPath;
    }
    public String getPath() {
        return path;
    }
}

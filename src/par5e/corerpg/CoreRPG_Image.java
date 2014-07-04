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
public class CoreRPG_Image extends CoreRPG_BaseClass {
    
    //ariables
    public String path;
    
    //Constructors
    public CoreRPG_Image() {
        
    }
    public CoreRPG_Image(String aName, String aPath) {
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

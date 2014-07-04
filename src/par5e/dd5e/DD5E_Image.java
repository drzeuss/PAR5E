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
public class DD5E_Image extends DD5E_BaseClass {
    
    //ariables
    public String path;
    
    //Constructors
    public DD5E_Image() {
        
    }
    public DD5E_Image(String aName, String aPath) {
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

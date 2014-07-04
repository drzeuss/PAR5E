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
public class DD5E_ImagePin extends DD5E_BaseClass{
    //Variables
    public String referenceClass;
    public String referenceName;
    public Integer x;
    public Integer y;     
    
    //Constructors
    public DD5E_ImagePin() {

    }   
    public DD5E_ImagePin(String aName) {
        name = aName;
        text = "";
        x = 0;
        y = 0;
        referenceClass = "";
        referenceName = "";
    } 
    public DD5E_ImagePin(String aName, Integer xpos, Integer ypos, String refClass, String refName) {
        name = aName;
        text = "";
        x = xpos;
        y = ypos;
        referenceClass = refClass;
        referenceName = refName;
    }
    //Methods
    public void setReferenceClass(String aNew) {
        referenceClass = aNew;
    }
    public void setReferenceName(String aNew) {
        referenceName = aNew;
    }
    public void setX(Integer aNew) {
        x = aNew;
    }
    public void setY(Integer aNew) {
        y = aNew;
    }
    public String getReferenceClass() {
        return referenceClass;
    }
    public String getReferenceName() {
        return referenceName;
    }
    public Integer getX() {
        return x;
    }
    public Integer getY() {
        return y;
    }
    
}

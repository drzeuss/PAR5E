/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package par5e.dd5e;

/**
 *
 * @author zeph
 */
public class DD5E_Feat extends DD5E_BaseClass {
    
    //Variables
    public String prereq;
    
    //Constructors
    public DD5E_Feat(String aName) {
        name = aName;
        text = "";
        prereq = "";        
    }
    
    //Methods
    public void setPrereq(String sNew) {
        prereq = sNew;
    }
    
    public String getPrereq() {
        return prereq;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package par5e;

/**
 *
 * @author zeph
 */
public class ModuleLibraryEntry {
    
    public String eName;
    public String eClass;
    public String eRecordname;
        
    public ModuleLibraryEntry(String aName, String aClass, String aRecordname) {
        eName = aName;
        eClass = aClass;
        eRecordname = aRecordname;
    }    
    
    public String getNameField() {
        return eName;
    }
    public String getClassField() {
        return eClass;
    }
    public String getRecordnameField() {
        return eRecordname;
    }
}


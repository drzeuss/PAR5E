/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package par5e;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author zeph
 */
public class ModuleLibrary {
    
    public LinkedHashMap<String,ModuleLibraryEntry> library;
    
    public ModuleLibrary() {
        library = new LinkedHashMap<>();
    }

    public Set<Map.Entry<String, ModuleLibraryEntry>> getEntries() {
        return library.entrySet();
    }
    
    public void addEntry(String aName, ModuleLibraryEntry modE) {
        library.put(aName, modE);
    }
    
}



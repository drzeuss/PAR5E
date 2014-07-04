/*
 * 
 * PAR5E Fantasy Grounds Ruleset Module Parser Tool
 * Developed by Zeus. Copyright 2014
 * 
 */

package par5e.corerpg;

import java.util.LinkedHashMap;

/**
 *
 * @author zeph
 */
public class CoreRPG_ReferenceSubChapter extends CoreRPG_BaseClass {
    
    public LinkedHashMap<String, CoreRPG_ReferencePage> pages;
    
    public CoreRPG_ReferenceSubChapter() {
        name = "";
        pages = new LinkedHashMap<>();
    }
    public CoreRPG_ReferenceSubChapter(String aName) {
        name = aName;
        pages = new LinkedHashMap<>();
    }
    public void setPages(LinkedHashMap<String, CoreRPG_ReferencePage> aNew) {
        pages = aNew;
    }
    public void addPage(String aNew, CoreRPG_ReferencePage aPage) {
        pages.put(aNew, aPage);
    }
    
    public LinkedHashMap<String,CoreRPG_ReferencePage> getPages() {
        return pages;
    }

    @Override
    public boolean isComplete() {
        return pages.isEmpty();
    }
}

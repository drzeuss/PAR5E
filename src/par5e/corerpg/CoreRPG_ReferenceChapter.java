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
public class CoreRPG_ReferenceChapter extends CoreRPG_BaseClass {
    
    public LinkedHashMap<String, CoreRPG_ReferenceSubChapter> subchapters;
    
    public CoreRPG_ReferenceChapter() {
        name = "";
        text = "";
        subchapters = new LinkedHashMap<>();
    }
    public CoreRPG_ReferenceChapter(String aName) {
        name = aName;
        text = "";
        subchapters = new LinkedHashMap<>();
    }
    public void setSubChapters(LinkedHashMap<String, CoreRPG_ReferenceSubChapter> aNew) {
        subchapters = aNew;
    }
    public void addSubChapter(String aNew, CoreRPG_ReferenceSubChapter aSubChapter) {
        subchapters.put(aNew, aSubChapter);
    }
    
    public LinkedHashMap<String,CoreRPG_ReferenceSubChapter> getSubChapters() {
        return subchapters;
    }

    @Override
    public boolean isComplete() {
        return subchapters.isEmpty();
    }
    
    
}

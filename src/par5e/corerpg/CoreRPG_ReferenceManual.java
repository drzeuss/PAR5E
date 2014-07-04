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
public class CoreRPG_ReferenceManual extends CoreRPG_BaseClass {
    
    public LinkedHashMap<String,CoreRPG_ReferenceChapter> chapters;
    
    public CoreRPG_ReferenceManual() {
        name = "";
        text = "";
    }
    public CoreRPG_ReferenceManual(String aNew) {
        name = aNew;
        text = "";
        chapters = new LinkedHashMap<>();
    }
    
    public void setChapters(LinkedHashMap<String,CoreRPG_ReferenceChapter> aNew) {
        chapters = aNew;
    }
    public void addChapter(String aNew, CoreRPG_ReferenceChapter aChapter) {
        chapters.put(aNew, aChapter);
    }
    public LinkedHashMap<String,CoreRPG_ReferenceChapter> getChapters() {
        return chapters;
    }
    
    @Override
    public boolean isComplete() {
        return chapters.isEmpty();
    }
}

/*
 * 
 * PAR5E Fantasy Grounds Ruleset Module Parser Tool
 * Developed by Zeus. Copyright 2014
 * 
 */

package par5e.dd5e;

import java.util.LinkedHashMap;
import par5e.corerpg.CoreRPG_ReferenceChapter;

/**
 *
 * @author zeph
 */
public class DD5E_ReferenceChapter extends CoreRPG_ReferenceChapter {
    
    public DD5E_ReferenceChapter() {
        name = "";
        text = "";
        subchapters = new LinkedHashMap<>();
    }
    public DD5E_ReferenceChapter(String aName) {
        name = aName;
        text = "";
        subchapters = new LinkedHashMap<>();
    }
    
    
}

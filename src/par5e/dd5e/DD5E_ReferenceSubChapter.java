/*
 * 
 * PAR5E Fantasy Grounds Ruleset Module Parser Tool
 * Developed by Zeus. Copyright 2014
 * 
 */

package par5e.dd5e;

import java.util.LinkedHashMap;
import par5e.corerpg.CoreRPG_ReferenceSubChapter;

/**
 *
 * @author zeph
 */
public class DD5E_ReferenceSubChapter extends CoreRPG_ReferenceSubChapter {
    
    public DD5E_ReferenceSubChapter() {
        name = "";
        pages = new LinkedHashMap<>();
    }
    public DD5E_ReferenceSubChapter(String aName) {
        name = aName;
        pages = new LinkedHashMap<>();
    }
    
}
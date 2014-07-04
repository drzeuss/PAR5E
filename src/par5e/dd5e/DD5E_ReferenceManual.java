/*
 * 
 * PAR5E Fantasy Grounds Ruleset Module Parser Tool
 * Developed by Zeus. Copyright 2014
 * 
 */

package par5e.dd5e;

import java.util.LinkedHashMap;
import par5e.corerpg.CoreRPG_ReferenceManual;

/**
 *
 * @author zeph
 */
public class DD5E_ReferenceManual extends CoreRPG_ReferenceManual {
    
    public DD5E_ReferenceManual() {
        name = "";
        text = "";
    }
    public DD5E_ReferenceManual(String aNew) {
        name = aNew;
        text = "";
        chapters = new LinkedHashMap<>();
    }
    
}


/*
 * 
 * PAR5E Fantasy Grounds Ruleset Module Parser Tool
 * Developed by Zeus. Copyright 2014
 * 
 */

package par5e.dd5e;

import par5e.corerpg.CoreRPG_ReferencePage;

/**
 *
 * @author zeph
 */
public class DD5E_ReferencePage extends CoreRPG_ReferencePage {
   
   public DD5E_ReferencePage() {
       name = "";
       text = "";
       keywords = "";
   }
   public DD5E_ReferencePage(String aName) {
       name = aName;
       text = "";
       keywords = "";
   }
   public DD5E_ReferencePage(String aName, String aText) {
       name = aName;
       text = aText;
       keywords = "";
   }
   
}


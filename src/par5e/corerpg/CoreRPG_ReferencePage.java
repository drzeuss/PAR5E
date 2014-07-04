/*
 * 
 * PAR5E Fantasy Grounds Ruleset Module Parser Tool
 * Developed by Zeus. Copyright 2014
 * 
 */

package par5e.corerpg;

/**
 *
 * @author zeph
 */
public class CoreRPG_ReferencePage extends CoreRPG_BaseClass {
   
   public String keywords;
   
   public CoreRPG_ReferencePage() {
       name = "";
       text = "";
       keywords = "";
   }
   public CoreRPG_ReferencePage(String aName) {
       name = aName;
       text = "";
       keywords = "";
   }
   public CoreRPG_ReferencePage(String aName, String aText) {
       name = aName;
       text = aText;
       keywords = "";
   }
   
   public void setKeywords(String aNew) {
       keywords = aNew;
   }
   public String getKeywords() {
       return keywords;
   }
   
   @Override
   public boolean isComplete() {
       return keywords.equals("");
   }
}

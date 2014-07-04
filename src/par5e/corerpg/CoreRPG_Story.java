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
public class CoreRPG_Story extends CoreRPG_BaseClass {
    
    public String category;
    
    public CoreRPG_Story(String aNew) {
        name = aNew;
        category = "";
        text = "";
    }
    
    public void setCategory(String aNew) {
        category = aNew;
    }
    public String getCategory() {
        return category;
    }
    
    @Override
    public boolean isComplete() {
        boolean complete = true;
        if (text.equals("") || category.equals("")) {
            complete = false;
        }
        return complete;
    }
}

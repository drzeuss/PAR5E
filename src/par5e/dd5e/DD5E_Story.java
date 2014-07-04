/*
 * 
 * PAR5E Fantasy Grounds Ruleset Module Parser Tool
 * Developed by Zeus. Copyright 2014
 * 
 */

package par5e.dd5e;

/**
 *
 * @author zeph
 */
public class DD5E_Story extends DD5E_BaseClass {
    
    public String category;
    
    public DD5E_Story(String aNew) {
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

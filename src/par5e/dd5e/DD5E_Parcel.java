/*
 * 
 * PAR5E Fantasy Grounds Ruleset Module Parser Tool
 * Developed by Zeus. Copyright 2014
 * 
 */

package par5e.dd5e;

import java.util.LinkedHashMap;

/**
 *
 * @author zeph
 */
public class DD5E_Parcel extends DD5E_BaseClass {
    
    //Variables
    public String category;
    public LinkedHashMap<String, Integer> coins;
    public LinkedHashMap<String, Integer> items;
    
    //Constructors
    public DD5E_Parcel(String aString) {
        name = aString;
        category = "";
        text = "";
        coins = new LinkedHashMap<>();
        items = new LinkedHashMap<>();
    }
    
    //Methods
    public void setCategory(String aNew) {
        category = aNew;
    }
    public void setCoins(LinkedHashMap<String,Integer> aNew) {
        coins = aNew;
    }
    public void setItems(LinkedHashMap<String,Integer> aNew) {
        items = aNew;
    }
    public void addCoin(String aNew, Integer aCoin) {
        coins.put(aNew, aCoin);
    }
    public void addItem(String aNew, Integer aItem) {
        items.put(aNew, aItem);
    }
 
    public String getCategory() {
        return category;
    }
    public LinkedHashMap<String,Integer> getCoins() {
        return coins;
    }
    public LinkedHashMap<String,Integer> getItems() {
        return items;
    }

    public boolean isComplete() {
        boolean complete = true;
        
        if (category.equals("") || (coins.isEmpty() && items.isEmpty())) {
            complete = false;
        }
        return complete;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package par5e.dd5e;

/**
 *
 * @author zeph
 */
public class DD5E_Equipment extends DD5E_BaseClass {
    
    //Variables
   // String name;
   // String text;
    String type;
    String subtype;
    String cost;
    String damage;
    String properties;
    String dexbonus;
    String stealth;
    
    Integer weight;
    Integer ac;
    Integer speed;
    
    //Constructors
    public DD5E_Equipment() {
        name = "";
        text = "";
        type = "";
        subtype = "";
        cost = "";
        damage = "";
        properties = "";
        dexbonus = "";
        stealth = "";
        
        weight = -1;
        ac = -1;
        speed = -1;
    }
    
    public void setType(String sNew) {
        type = sNew;
    }
    public void setSubType(String sNew) {
        subtype = sNew;
    }
    public void setCost(String sNew) {
        cost = sNew;
    }
    public void setDamage(String sNew) {
        damage = sNew;
    }
    public void setProperties(String sNew) {
        properties = sNew;
    }
    public void setDexBonus(String sNew) {
        dexbonus = sNew;
    }
    public void setStealth(String sNew) {
        stealth = sNew;
    }
    public void setWeight(Integer sNew) {
        weight = sNew;
    }
    public void setAC(Integer sNew) {
        ac = sNew;
    }
    public void setSpeed(Integer sNew) {
        speed = sNew;
    }

    public String getType() {
        return type;
    }
    public String getSubType() {
        return subtype;
    }
    public String getCost() {
        return cost;
    }
    public String getDamage() {
        return damage;
    }
    public String getProperties() {
        return properties;
    }
    public String getDexBonus() {
        return dexbonus;
    }
    public String getStealth() {
        return stealth;
    }
    public Integer getWeight() {
        return weight;
    }
    public Integer getAC() {
        return ac;
    }
    public Integer getSpeed() {
        return speed;
    }
    
    @Override
    public boolean isComplete() {
        boolean complete = true;
        if (type.toLowerCase().contains("armor")) {
            if (subtype.equals("") || cost.equals("") || weight.equals(-1) || ac.equals(-1) || dexbonus.equals("") || speed.equals(-1) || stealth.equals("")) {
                complete = false;
            }
        } else if (type.toLowerCase().contains("weapon")) {
            if (subtype.equals("") || cost.equals("") || weight.equals(-1) || damage.equals("") ||  properties.equals("")) {
                complete = false;
            }
        } else if (type.toLowerCase().contains("adventuring gear")) {
            if (subtype.equals("") || cost.equals("") || weight.equals(-1)) {
                complete = false;
            }
        } else {
            complete = false;
        }
        
        return complete;
    }
}    

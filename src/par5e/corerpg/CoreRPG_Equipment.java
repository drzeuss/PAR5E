/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package par5e.corerpg;

/**
 *
 * @author zeph
 */
public class CoreRPG_Equipment extends CoreRPG_BaseClass {
    
    //Variables
    String cost;
    Integer weight;
    
    //Constructors
    public CoreRPG_Equipment() {
        name = "";
        text = "";
        cost = "";
        weight = -1;
    }
    
    public void setCost(String sNew) {
        cost = sNew;
    }
    public void setWeight(Integer sNew) {
        weight = sNew;
    }
    public String getCost() {
        return cost;
    }
    public Integer getWeight() {
        return weight;
    }
    
    @Override
    public boolean isComplete() {
        boolean complete = true;
        if (cost.equals("") || weight.equals(-1) ) {
            complete = false;
        }
        return complete;
    }
}    

package par5e.dd5e;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 *
 * @author zeph
 */
public class DD5E_Background extends DD5E_BaseClass {
    
    public LinkedHashMap<String,String> traits;
    public LinkedHashMap<String,String> proficiencies;
    public String languages;
    public String equipment;
    
    //Constructors
    public DD5E_Background(String aName) {
        name = aName;
        text = "";
        traits = new LinkedHashMap<>();
        proficiencies = new LinkedHashMap<>();
        languages = "";
        equipment = "";
    }
    
    //Public methods
    public void setTraits(LinkedHashMap<String,String> aMap) {
        traits = aMap;
    }
    public void setProficiencies(LinkedHashMap<String,String> aMap) {
        proficiencies = aMap;
    }
    public void setLanguages(String aString) {
        languages = aString;
    }
    public void setEquipment(String aString) {
        equipment = aString;
    }
    public LinkedHashMap<String,String> getTraits() {
        return traits;
    }
    public LinkedHashMap<String,String> getProficiencies() {
        return proficiencies;
    }
    public String getLanguages() {
        return languages;
    }
    public String getEquipment() {
        return equipment;
    }
    @Override
    public boolean isComplete() {
        boolean complete = true;
        if (text.equals("") || traits.isEmpty() || proficiencies.isEmpty() || languages.equals("") || equipment.equals("")) {
            complete = false;
        }
        
        return complete;
    }

}

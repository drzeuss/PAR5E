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
public class DD5E_Class extends DD5E_BaseClass {
    
    //Variables
    public LinkedHashMap<String,String> hp;
    public LinkedHashMap<String,String> proficiencies;
    public LinkedHashMap<String,DD5E_ClassFeature> features;
    public LinkedHashMap<String,DD5E_ClassAbility> abilities;
    public LinkedHashMap<String,String> equipment;
    public String[] backgrounds;
    
    //Constructors
    public DD5E_Class(String aName) {
        name = aName;
        text = "";
        hp = new LinkedHashMap<>();
        proficiencies = new LinkedHashMap<>();
        features = new LinkedHashMap<>();
        abilities = new LinkedHashMap<>();
        equipment = new LinkedHashMap<>();
        backgrounds = new String[0];
    }
    
    //Public methods
    public void setHP(LinkedHashMap<String,String> aMap) {
        hp = aMap;
    }
    public void setProficiencies(LinkedHashMap<String,String> aMap) {
        proficiencies = aMap;
    }
    public void setFeatures(LinkedHashMap<String,DD5E_ClassFeature> aMap) {
        features = aMap;
    }
    public void setAbilities(LinkedHashMap<String,DD5E_ClassAbility> aMap) {
        abilities = aMap;
    }
    public void setEquipment(LinkedHashMap<String,String> aMap) {
        equipment = aMap;
    }
    public void addEquipment(String aTag, String aValue) {
        equipment.put(aTag, aValue);
    }
    public void setBackgrounds(String[] aMap) {
        backgrounds = aMap;
    }
    public void addBackground(String aString) {
        String[] aTmp = Arrays.copyOf(backgrounds, backgrounds.length+1);
        aTmp[aTmp.length-1] = aString;
        backgrounds = aTmp;
    }
    public LinkedHashMap<String,String> getHP() {
        return hp;
    }
    public LinkedHashMap<String,String> getProficiencies() {
        return proficiencies;
    }
    public LinkedHashMap<String,DD5E_ClassFeature> getFeatures() {
        return features;
    }
    public LinkedHashMap<String,DD5E_ClassAbility> getAbilities() {
        return abilities;
    }
    public LinkedHashMap<String,String> getEquipment() {
        return equipment;
    }
    public String[] getBackgrounds() {
        return backgrounds;
    }
    @Override
    public boolean isComplete() {
        boolean complete = true;
        if (text.equals("") || hp.isEmpty() || proficiencies.isEmpty() || features.isEmpty() || abilities.isEmpty() || equipment.isEmpty() || backgrounds.length == 0) {
            complete = false;
        } 
        return complete;
    }
 }

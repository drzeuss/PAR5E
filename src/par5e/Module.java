/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package par5e;

import java.util.LinkedHashMap;

/**
 *
 * @author zeph
 */
public class Module {
    
    //Variables
    public String name;
    public String id;
    public String category;
    public String ruleset;
    public String path;
    public String thumbnailpath;
    public String temppath;
    public String outputpath;
    public LinkedHashMap dataitems;
    public LinkedHashMap dataitempaths;
    
    //Constructors
    public void Module() {
        name = "<<Module Name>>";
        id = "<<Module ID>>";
        category = "<<Module Category>>";
        ruleset = "CoreRPG";
        path = System.getProperty("user.home");
        thumbnailpath = System.getProperty("user.home");
        temppath = System.getProperty("user.home");
        outputpath = System.getProperty("user.home");
        dataitems = new LinkedHashMap();
        dataitempaths = new LinkedHashMap();
    }
    
    //Methods
    public void setName(String aName) {
        name = aName;
    }    
    public void setID(String aID) {
        id = aID;
    }
    public void setCategory(String aCategory) {
        category = aCategory;
    }
    public void setRuleset(String aRuleset) {
        ruleset = aRuleset;
    }
    public void setDataItems(LinkedHashMap aDataItems) {
        dataitems = aDataItems;
    }
    public void setDataItemPaths(LinkedHashMap aDataItemPaths) {
        dataitempaths = aDataItemPaths;
    }
    public void setPath(String aPath) {
        path = aPath;
    }
    public void setThumbnailPath(String aPath) {
        thumbnailpath = aPath;
    }
    public void setTempPath(String aPath) {
        temppath = aPath;
    }
    public void setOutputPath(String aPath) {
        outputpath = aPath;
    }

    public String getName() {
        return name;
    }
    public String getID() {
        return id;
    }
    public String getCategory() {
        return category;
    }
    public String getRuleset() {
        return ruleset;
    }
    public LinkedHashMap getDataItems() {
        return dataitems;
    }
    public LinkedHashMap getDataItemPaths() {
        return dataitempaths;
    }
    public String getPath() {
        return path;
    }
    public String getThumbnailPath() {
        return thumbnailpath;
    }
    public String getTempPath() {
        return temppath;
    }
    public String getOutputPath() {
        return outputpath;
    }
    
    @Override
    public String toString() {
        String aString = "Name: " + name + "\n";
        aString = aString + "ID: " + id + "\n";
        aString = aString + "Category: " + category + "\n";
        aString = aString + "Ruleset: " + ruleset + "\n";
        aString = aString + "Path: " + path + "\n";
        aString = aString + "Thumbnail: " + thumbnailpath + "\n";
        aString = aString + "Temp: " + temppath + "\n";
        aString = aString + "Output: " + outputpath + "\n";
        return aString;
    }
}

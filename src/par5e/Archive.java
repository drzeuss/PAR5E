/*
 * 
 * PAR5E Fantasy Grounds Ruleset Module Parser Tool
 * Developed by Zeus. Copyright 2014
 * 
 */

package par5e;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import java.util.Arrays;
import java.util.List;
import par5e.corerpg.CoreRPG_BaseClass;
import par5e.corerpg.CoreRPG_MagicItem;
import par5e.corerpg.CoreRPG_NPC;
import par5e.dd5e.DD5E_MagicItem;
import par5e.dd5e.DD5E_NPC;

/**
 *
 * @author zeph
 */
public class Archive {
    
    //Variables
    public String path;
    public String[] classes;
    public ObjectContainer objects;
    
    //Constructors
    public Archive() {
        path = "";
        classes = new String[0];
        objects = null;
    }
        
    //Methods
    public void setPath(String aNew) {
        path = aNew;
    }
    public void setClasses(String[] aNew) {
        classes = aNew;
    }
    public void setObjects(ObjectContainer aNew) {
        if (objects != null) {
            objects.close();
        }
        objects = aNew;
    }
    public void addClass(String aNew) {
        classes = Arrays.copyOf(classes, classes.length+1);
        classes[classes.length-1] = aNew;
    }
    
    public void open(String aPath) {
        close();
        path = aPath;
        objects = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), aPath);
    }
    public void close() {
        if (objects != null) {
            objects.close();
        }
    }
    
    public String getPath() {
        return path;
    }
    public String[] getClasses() {
        return classes;
    }
    public ObjectContainer getObjects() {
        return objects;
    }
    public List<?> getObjects(String rulesetName, String objType) {

        List<?> list;
        
        outerbreak:
        switch(rulesetName) {
            case "5E": {
                switch(objType) {
                    default: {
                        list = objects.query(new Predicate<DD5E_NPC>() {
                            @Override
                            public boolean match(DD5E_NPC obj) {
                                return !obj.getName().equals("   ");
                            }
                        }); 
                        break outerbreak;
                    }
                    case "Magic Items": {
                        list = objects.query(new Predicate<DD5E_MagicItem>() {
                            @Override
                            public boolean match(DD5E_MagicItem obj) {
                                return !obj.getName().equals("   ");
                            }
                        });  
                        break outerbreak;
                    }
                }
            }
            default: {
                switch(objType) {
                    default: {
                        list = objects.query(new Predicate<CoreRPG_NPC>() {
                            @Override
                            public boolean match(CoreRPG_NPC obj) {
                                return !obj.getName().equals("   ");
                            }
                        });  
                        break outerbreak;
                    }
                    case "Magic Items": {
                        list = objects.query(new Predicate<CoreRPG_MagicItem>() {
                            @Override
                            public boolean match(CoreRPG_MagicItem obj) {
                                return !obj.getName().equals("   ");
                            }
                        });  
                        break outerbreak;
                    }
                }
            }
        } 
        return list;
    }
    public List<?> getObject(String rulesetName, String objType, final String objName) {

        List<?> list;
        
        outerbreak:
        switch(rulesetName) {
            case "5E": {
                switch(objType) {
                    default: {
                        list = objects.query(new Predicate<DD5E_NPC>() {
                            @Override
                            public boolean match(DD5E_NPC obj) {
                                return obj.getName().equals(objName);
                            }
                        }); 
                        break outerbreak;
                    }
                    case "Magic Items": {
                        list = objects.query(new Predicate<DD5E_MagicItem>() {
                            @Override
                            public boolean match(DD5E_MagicItem obj) {
                                return obj.getName().equals(objName);
                            }
                        });  
                        break outerbreak;
                    }
                }
            }
            default: {
                switch(objType) {
                    default: {
                        list = objects.query(new Predicate<CoreRPG_NPC>() {
                            @Override
                            public boolean match(CoreRPG_NPC obj) {
                                return obj.getName().equals(objName);
                            }
                        });  
                        break outerbreak;
                    }
                    case "Magic Items": {
                        list = objects.query(new Predicate<CoreRPG_MagicItem>() {
                            @Override
                            public boolean match(CoreRPG_MagicItem obj) {
                                return obj.getName().equals(objName);
                            }
                        });  
                        break outerbreak;
                    }
                }
            }
        } 
        return list;
    }
    public boolean isEmpty() {
        List<?> list = objects.query(new Predicate<CoreRPG_BaseClass>() {
            @Override
            public boolean match(CoreRPG_BaseClass obj) {
                return !obj.getName().equals("   ");
            }
        });  
        return list.isEmpty();
    }
}

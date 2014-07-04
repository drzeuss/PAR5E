/*
 * 
 * PAR5E Fantasy Grounds Ruleset Module Parser Tool
 * Developed by Zeus. Copyright 2014
 * 
 */


package par5e.corerpg;

import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import java.io.File;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jdom2.Element;
import par5e.PAR5ERulesetLibrary;
import par5e.utils.RegEx;
import par5e.ModuleLibraryEntry;

/**
 *
 * @author zeph
 */
public class RulesetLibrary extends PAR5ERulesetLibrary {
    
    //Data Stores
    public LinkedHashMap<String,CoreRPG_Encounter> Encounters;
    public LinkedHashMap<String,CoreRPG_Equipment> Equipment;
    public LinkedHashMap<String,CoreRPG_Image> Images;
    public LinkedHashMap<String,CoreRPG_ImagePins> ImagePins;
    public LinkedHashMap<String,CoreRPG_MagicItem> MagicItems;
    public LinkedHashMap<String,CoreRPG_NPC> NPCs;
    public LinkedHashMap<String,CoreRPG_Parcel> Parcels;
    public CoreRPG_ReferenceManual ReferenceManual;
    public LinkedHashMap<String,CoreRPG_Story> Stories;
    public LinkedHashMap<String,CoreRPG_Table> Tables;
    public LinkedHashMap<String,CoreRPG_Token> Tokens;   

    //Indexes
    public LinkedHashMap<String,String[]> EncountersByCategory;
    public LinkedHashMap<String,String[]> EquipmentByType;
    public LinkedHashMap<String,String> EquipmentSubTypesByType;
    public LinkedHashMap<String,String[]> MagicItemsByType;
    public LinkedHashMap<String,String> MagicItemSubTypesByType;
    public LinkedHashMap<String,String[]> NPCsByLetter;
    public LinkedHashMap<Integer,String[]> NPCsByLevel;
    public LinkedHashMap<String,String[]> NPCsByType;
    public LinkedHashMap<String,String[]> ParcelsByCategory;
    public LinkedHashMap<String,String[]> StoriesByCategory;
    public LinkedHashMap<String,String[]> TablesByCategory;
    
    //Constructors
    public RulesetLibrary() {  
    
        //Initialise data stores and indexes
       //Initialise data stores and indexes
        Encounters = new LinkedHashMap<>();
        EncountersByCategory = new LinkedHashMap<>();
        Equipment = new LinkedHashMap<>();
        EquipmentByType = new LinkedHashMap<>();
        EquipmentSubTypesByType = new LinkedHashMap<>();
        Images = new LinkedHashMap<>();
        ImagePins = new LinkedHashMap<>();
        MagicItems = new LinkedHashMap<>();
        MagicItemsByType = new LinkedHashMap<>();
        MagicItemSubTypesByType = new LinkedHashMap<>();
        NPCs = new LinkedHashMap<>();
        NPCsByLetter = new LinkedHashMap<>();
        NPCsByLevel = new LinkedHashMap<>();
        NPCsByType = new LinkedHashMap<>();
        Parcels = new LinkedHashMap<>();
        ParcelsByCategory = new LinkedHashMap<>();
        Stories = new LinkedHashMap<>();
        StoriesByCategory = new LinkedHashMap<>();
        Tables = new LinkedHashMap<>();
        TablesByCategory = new LinkedHashMap<>();
        Tokens = new LinkedHashMap<>();
        
        initialiseBS();
        buildDataItems(); 
        setName("CoreRPG");
        setRuleset("CoreRPG");
    }
    
    //Public methods
    @Override
    public void parseData(String aDataItemName, String[] aSourceData, ObjectContainer objects) {
        switch(aDataItemName) {
            case "encounters": parseEncounterData(aSourceData); break;
            case "equipment": parseEquipmentData(aSourceData); break;
            case "imagepins": parseImagePinData(aSourceData); break;
            case "images": parseImageData(aSourceData); break;
            case "magicitems": parseMagicItemData(aSourceData, objects); break;
            case "npcs": parseNPCData(aSourceData, objects); break;
            case "parcels": parseParcelData(aSourceData); break;
            case "referencemanual": parseReferenceManualData(aSourceData); break;
            case "story": parseStoryData(aSourceData); break;
            case "tokens": parseTokenData(aSourceData); break;
            case "tables": parseTableData(aSourceData); break;
            default: { }; break; 
        }
    }
    @Override
    public void generateIndexes(String aDataItemName) {
         switch(aDataItemName) {
            case "equipment": generateEquipmentIndexes(aDataItemName);
            case "magicitems": generateMagicItemIndexes(aDataItemName);
            case "npcs": generateNPCIndexes(aDataItemName);
        }
    }   
    @Override
    public void generateModuleXML(Element E, ObjectContainer objects) {
        engine.debug(1, "->generateModuleXML()");
        // Loop through selected module data items and make campaign XML data        
        
        Iterator aIterator = module.getDataItems().entrySet().iterator();
        while (aIterator.hasNext()) {
            Map.Entry entry = (Map.Entry) aIterator.next();
            String diName = (String)entry.getKey();
            BitSet diOutputMode = (BitSet)entry.getValue();
            boolean importfromarchive = false;
            
            if (diName.equals("npcs") || diName.equals("magicitems")) {
                if (!isArchiveEmpty(objects)) {
                    importfromarchive = true;
                }    
            }
            
            if (diOutputMode.get(0) == true || importfromarchive) {    
                switch(diName) {
                    case "encounters": generateEncounterXML(E); break;
                    case "equipment": generateItemXML(E); break;
                    case "images": generateImageXML(E, false); break;
                    case "npcs": generatePersonalitiesXML(E); break; 
                    case "parcels": generateParcelXML(E); break;
                    case "story": generateStoryXML(E); break;
                    case "tables": generateTableXML(E); break;
                    default: break;
                }
                con.printMsg("Make  : " + diName + " (campaign)");
                con.printMsgResult("Completed", info);
            }
        }
        
        Element refTag = generateReferenceTag(E);
        
        // Loop through selected module data items and make reference XML data
        aIterator = module.getDataItems().entrySet().iterator();
        while (aIterator.hasNext()) {
            Map.Entry entry = (Map.Entry) aIterator.next();
            String diName = (String)entry.getKey();
            BitSet diOutputMode = (BitSet)entry.getValue();
            boolean importfromarchive = false;
            
            if (diName.equals("npcs") || diName.equals("magicitems")) {
                if (!isArchiveEmpty(objects)) {
                    importfromarchive = true;
                }    
            }
            
            if (diOutputMode.get(1) == true || importfromarchive) {
                switch(diName) {
                    case "equipment": generateEquipmentXML(refTag, null); generateEquipmentXMLLists(refTag); break;
                    case "images": generateImageXML(refTag, true); break;
                    case "magicitems": generateMagicItemXML(refTag, true); generateMagicItemXMLLists(refTag); break;
                    case "npcs": generateNPCXML(refTag, true); generateNPCXMLLists(refTag); break;
                    case "referencemanual": generateReferenceManualXML(refTag); generateReferenceManualIndex(refTag); break;
                    default: break;
                }
                con.printMsg("Make  : " + diName + " (reference)");
                con.printMsgResult("Completed", info);
            }        
        }
        
        // Loop through selected module data items and make database XML data
        aIterator = module.getDataItems().entrySet().iterator();
        while (aIterator.hasNext()) {
            Map.Entry entry = (Map.Entry) aIterator.next();
            String diName = (String)entry.getKey();
            BitSet diOutputMode = (BitSet)entry.getValue();
            if (diOutputMode.get(2) == true) {
                
                //To be completed in a future release
                con.printMsg("Make  : " + diName + " (database)");
                con.printMsgResult("Completed", info);
            }
        }       
    }
    @Override
    public void generateArchive(ObjectContainer objects) {
        // Loop through selected module data items and make database XML data
        Iterator aIterator = module.getDataItems().entrySet().iterator();
        while (aIterator.hasNext()) {
            Map.Entry entry = (Map.Entry) aIterator.next();
            String diName = (String)entry.getKey();
            BitSet diOutputMode = (BitSet)entry.getValue();
            if (diOutputMode.get(2) == true) {
                switch(diName) {
                    case "npcs": generateNPCArchive(objects); break;
                    case "magicitems": generateMagicItemArchive(objects); break;
                    default: {}; break;
                }
                //To be completed in a future release
                con.printMsg("Make  : " + diName + " (archive)");
                con.printMsgResult("Completed", info);
            }
        } 
    }
    @Override
    public boolean isArchiveEmpty(ObjectContainer objects) {
        boolean empty = false;
        List<CoreRPG_NPC> npcs = objects.query(new Predicate<CoreRPG_NPC>() {
                @Override
                public boolean match(CoreRPG_NPC obj) {
                return !obj.getName().equals("   ");
            }
            }); 
        List<CoreRPG_MagicItem> mis = objects.query(new Predicate<CoreRPG_MagicItem>() {
            @Override
            public boolean match(CoreRPG_MagicItem obj) {
            return !obj.getName().equals("   ");
        }
        }); 
        if (mis.isEmpty() || npcs.isEmpty()) {
            empty = true;
        }
        return empty;
    }
    @Override
    public String[] getZLinkData(String aString) {
	engine.debug(3, "->getZLinkData()");
        String[] link = null;
        if (!aString.isEmpty()) {
            RegEx regex = new RegEx("(.+)\\;(.+)\\;(.*)", aString);          
            if (regex.find()) {
                link = getLinkData(regex.group(1), regex.group(2), regex.group(3));
                return link;
            } else {
                regex = new RegEx("(.+)\\;(.*)", aString);
                if (regex.find()){ 
                    link = getLinkData(regex.group(1), regex.group(2), "");
                }    
                return link;
            }
        }
        return null;
    }
    @Override
    public String[] getLinkData(String sType, String sName, String sTitle) {
	engine.debug(3, "->getLinkData()");
        String Class = "", Classname = "", Recordname = "", Sanename = "", Refname = "", Dbnode = "";
        String[] Linkdata = new String[3];
	RegEx regex;
	if (!sType.isEmpty() && !sName.isEmpty()) {
		Sanename = sName;
		Sanename = sanatise(Sanename);
		
		if (!sTitle.isEmpty()) {
                    Refname = sTitle;
                    Refname = sanatise(Refname);
		}
		
		// Reference List Objects
                regex = new RegEx("(.*)list", sType);
		if (regex.find() ) {
			String type = regex.group();
			switch(type) {
                            case "adventuringgear": case "armor": case "weapon": {
                                Class = "reference_" + type + "tablelist";
                                Dbnode = type + "lists";
                                Sanename = type + "_table";
                                Recordname = "reference" + module.getID() + "." + Dbnode + "." + Sanename + "@" + module.getName();
                            }
                            case "background": case "feat": case "npc": case "skill": case "speciality": case "trap": {
                                Class = "reference_" + type + "list";
                                Dbnode = type + "lists";
                                Sanename = "byLetter";
                                Recordname = "reference" + module.getID() + "." + Dbnode + "." + Sanename + "@" + module.getName();
                            }
                            case "ability": case "feature": case "class": case "spell": {
                                Class = "reference_" + type + "list";
                                Dbnode = type + "lists";
                                if (type.equals("spell")) {
                                    Sanename = "spells";
                                } else {
                                    Sanename = "byclass";
                                }
                                Recordname = "reference" + module.getID() + "." + Dbnode + "." + Sanename + "@" + module.getName();
                            }   
                            case "magicitem": {
                                Class = "reference_magicitemlist";
                                Dbnode = "magicitemlist";
                                Sanename = "bytype";
                                Recordname = "reference" + module.getID() + "." + Dbnode + "." + Sanename + "@" + module.getName();
                            }
                            default: {
                                Class = "reference_" + type + "list";
                                Dbnode = type + "lists";
                                Sanename = "byletter";
                                Recordname = "reference" + module.getID() + "." + Dbnode + "." + Sanename + "@" + module.getName();
                            } 
     			}
                }
	 	// DD5E_Background Trait
                regex = new RegEx("(.*)_backgroundtrait", sType);
		if (regex.find() ) {
                    Class = "reference_backgroundtrait";
                    Classname = sanatise(trimTxt(regex.group(1)));
                    Recordname = "reference" + module.getID() + ".backgrounddata." + Classname + ".traits." + Sanename + "@" + module.getName();
                }
                // Class Hit Points
                regex = new RegEx("(.*)_classhp", sType);
		if (regex.find() ) {
                    Class = "reference_classhp";
                    Classname = sanatise(trimTxt(regex.group(1)));
                    Recordname = "reference" + module.getID() + ".classdata." + Classname + ".hp." + Sanename + "@" + module.getName();
                }
		//Class Proficiencies
                regex = new RegEx("(.*)_classproficiency", sType);
		if (regex.find() ) {
                    Class = "reference_classproficiency";
                    Classname = sanatise(trimTxt(regex.group(1)));
                    Recordname = "reference" + module.getID() + ".classdata." + Classname + ".proficiencies." + Sanename + "@" + module.getName();
                }
		//Class Ability
		regex = new RegEx("(.*)_ability", sType);
		if (regex.find() ) {
                    Class = "reference_classability";
                    Classname = sanatise(trimTxt(regex.group(1)));
                    Recordname = "reference" + module.getID() + ".classdata." + Classname + ".abilities." + Sanename + "@" + module.getName();
                }
                //Class Feature
		regex = new RegEx("(.*)_feature", sType);
		if (regex.find() ) {
                    Class = "reference_classfeature";
                    Classname = sanatise(trimTxt(regex.group(1)));
                    Recordname = "reference" + module.getID() + ".classdata." + Classname + ".features." + Sanename + "@" + module.getName();
                }
                //Racial Trait
		regex = new RegEx("(.*)_trait", sType);
		if (regex.find() ) {
                    Class = "reference_racialtrait";
                    Classname = sanatise(trimTxt(regex.group(1)));
                    Recordname = "reference" + module.getID() + ".racedata." + Classname + ".traits." + Sanename + "@" + module.getName();
                }
                // Subrace
		regex = new RegEx("(.*)_subracetrait$", sType);
		if (regex.find() ) {
                    Class = "reference_subracialtrait";
                    Classname = sanatise(trimTxt(regex.group(1)));
                    Recordname = "reference" + module.getID() + ".racedata." + Classname + ".subraces." + Refname + ".traits." + Sanename + "@" + module.getName();
                }
                regex = new RegEx("(.*)_subrace$", sType);
		if (regex.find() ) {
                    Class = "reference_subrace";
                    Classname = sanatise(trimTxt(regex.group(1)));
                    Recordname = "reference" + module.getID() + ".racedata." + Classname + ".subraces." + Sanename + "@" + module.getName();
                }
                
                //Adventure Class Objects
                switch(sType) {
                    case "encounter": case "item": case "image": case "npc": case "parcel": case "table": case "story": {
                        switch (sType) {
                            case "encounter":
                                Sanename = "bat_" + Sanename;
                                Dbnode = sType;
                                Class = "battle";
                                break;
                            case "item":
                                Dbnode = "item";
                                Class = "item";
                                break;
                            case "npc":
                                Dbnode = "npc";
                                Class = "npc";
                                break;
                            case "story":
                                Sanename = "enc_" + Sanename;
                                Dbnode = "encounter";
                                Class = "encounter";
                                break;
                            case "table":
                                Sanename = "tab_" + Sanename;
                                Dbnode = sType + "s";
                                Class = sType;
                                break;
                            case "parcel":
                                Sanename = "par_" + Sanename;
                                Dbnode = sType + "s";
                                Class = sType;
                                break;
                            default:
                                Class = sType;
                                Dbnode = sType;
                                break;
                        }
                        Recordname = Dbnode + "." + Sanename + "@" + module.getName();
                    }
                    default: {
                        regex = new RegEx("reference(.*)", sType);
                        if (regex.find()) {
                            Class = "reference_" +  regex.group(1);
                        } else {
                            Class = "reference_" + sType;
                        }
                    }        
		} 
        }     
        Linkdata[0] = Class;
        Linkdata[1] = Recordname;
        Linkdata[2] = sName;
        return Linkdata;
    }
    
    //Private methods
    private void buildDataItems() {

        LinkedHashMap<String, BitSet> data = new LinkedHashMap<>();
            
        data.put("encounters", cbs);
        data.put("equipment", crbs);
        data.put("imagepins", crbs);
        data.put("images", crbs);
        data.put("magicitems", cdbs);
        data.put("npcs", cdbs);
        data.put("parcels", cbs);
        data.put("referencemanual", rbs);
        data.put("story", cbs);
        data.put("tables", cbs);
        data.put("tokens", crbs);
       
        initDataItems(data);
    }
    private String padID(Integer aNum) {
        String id;
        
        if (aNum < 10) {
            id = "0" + aNum.toString();
        } else {
            id = aNum.toString();
        }
        return id;        
    }
    
    private void parseEncounterData(String[] aSourceData) {
        engine.debug(1, "->parseEncounterData()");
        //Variables
        String sName = "", sCategory = "";
	CoreRPG_Encounter encounter = null;
        
        //Loop through source content by line
        for (String sLine : aSourceData) {
            
            //Check for empty line and skip
            RegEx emptyline = new RegEx("\\A$",sLine);
            RegEx whitespaceline = new RegEx("\\A\\s+$",sLine);
            if (emptyline.find() || whitespaceline.find()) {
                engine.debug(2,"Empty Line");
                continue;
            }
            
            //New Category
            RegEx regex = new RegEx("\\A\\#\\@\\;(.*)",sLine);
            if (regex.find()) {
                sCategory = trimTxt(regex.group(1));
                engine.debug(2,"Category - " + sCategory);
                continue;
            }
            
            //New Encounter
            regex = new RegEx("\\A\\#\\#\\;(.*)",sLine);
            if (regex.find()) {
                if (encounter != null && encounter.getName().equals(sName)) {                    
                    if (encounter.isComplete()) {
                        con.printMsg("Parse : encounter ");
                        con.printMsgResult(sName, normal);
                        engine.debug(2,"Parse Completed - " + sName);
                    } else {    
                        con.printMsg("Parse : encounter ");
                        con.printMsgResult(sName, warning);
                        engine.debug(2,"Parse Incomplete - " + sName);
                    }       
                    Encounters.put(sName, encounter);
                    
                    //Add encounter to index of encounters by category
                    String[] encs;
                    if (EncountersByCategory.get(sCategory) != null ) {
                        encs = Arrays.copyOf(EncountersByCategory.get(sCategory), EncountersByCategory.get(sCategory).length+1);
                        encs[encs.length-1] = sName;
                    } else {
                        encs = new String[1];
                        encs[0] = sName;
                    }
                    EncountersByCategory.put(sCategory, encs);
                }
                
                sName = trimTxt(regex.group(1));
                encounter = new CoreRPG_Encounter(sName);
                encounter.setCategory(sCategory);
                engine.debug(2,"Encounter - " + sName);
                continue;  
            }
            
            //Encounter NPC
            regex = new RegEx("\\A(\\d+)\\s*([a-zA-Z\\(\\)\\s\\,]+)\\s*Level\\s*(\\d+)\\s*",sLine);
            if (regex.find() && encounter != null) {
                Integer mNumber = Integer.parseInt(trimTxt(regex.group(1)));
                String mName = trimTxt(regex.group(2));
                Integer mLevel = Integer.parseInt(trimTxt(regex.group(3)));
                encounter.addNPCEntry(mName, mNumber, mLevel);
                engine.debug(2,"NPC Line");
            }     
        }
        //Store final Encounter
        if (encounter != null && encounter.getName().equals(sName)) {                    
            if (encounter.isComplete()) {
                con.printMsg("Parse : encounter ");
                con.printMsgResult(sName, normal);
                engine.debug(2,"Parse Completed - " + sName);
            } else {    
                con.printMsg("Parse : encounter ");
                con.printMsgResult(sName, warning);
                engine.debug(2,"Parse Incomplete - " + sName);
            }       
            Encounters.put(sName, encounter);
            //Add encounter to index of encounters by category
            String[] encs;
                if (EncountersByCategory.get(sCategory) != null ) {
                    encs = Arrays.copyOf(EncountersByCategory.get(sCategory), EncountersByCategory.get(sCategory).length+1);
                    encs[encs.length-1] = sName;
                } else {
                    encs = new String[1];
                    encs[0] = sName;
                }
            EncountersByCategory.put(sCategory, encs);
        }
    } 
    private void parseEquipmentData(String[] aSourceData) {
        engine.debug(1, "->parseEquipmentData()");
        
        //Variables
        boolean bTableBlock = false, bTableHeaderRow = false, bDescriptionBlock = false;
	String sName = "", sCategory = "", sType = "", sSubType = "", sText = "";
        CoreRPG_Equipment equipment = null;
        
        //Loop through each line of source data
        for (String sLine : aSourceData) {
            //Check for empty line and skip
            RegEx emptyline = new RegEx("\\A$",sLine);
            RegEx whitespaceline = new RegEx("\\A\\s+$",sLine);
            if (emptyline.find() || whitespaceline.find()) {
                engine.debug(2,"Empty Line");
                continue;
            }
            
            //Check for Table Header row and skip
            RegEx regex = new RegEx("\\A\\#th\\;(.*)", sLine);
            if (regex.find() ) {
                engine.debug(2,"Table Header");
                continue;
            }
            
            //Table Row
            regex = new RegEx("\\A.*\\;.*\\;.*", sLine);
            if (regex.find() ) {
                String[] row = sLine.split(";");
                Integer numfields = row.length;
  
                if (numfields > 2) {
                    if (equipment != null && equipment.getName().equals(sName)) {
                        if (equipment.isComplete()) {
                            con.printMsg("Parse : equipment");
                            con.printMsgResult(equipment.getName(), normal);
                            engine.debug(2,"Parse Completed - " + equipment.getName());
                        } else {
                            con.printMsg("Parse : equipment");
                            con.printMsgResult(equipment.getName(), warning);
                            engine.debug(2,"Parse Incomplete - " + equipment.getName());
                        }
                        Equipment.put(equipment.getName(), equipment);
                    }
                
                    equipment = new CoreRPG_Equipment();
                    RegEx r = new RegEx("\\A([\\w\\s\\’\\'\\-\\,]+).*", row[0]);
                    RegEx rn = new RegEx("(.*)\\s\\(.*\\)", row[0]);
                    
                    if (r.find()) {
                        if (rn.find()) {
                           sName = trimTxt(rn.group(1)); 
                        } else {
                           sName = trimTxt(r.group(1));
                        }
                        equipment.setName(sName);
                    }
                    equipment.setCost(trimTxt(row[1]));

                    if (numfields == 3 ) {
                        
                        r = new RegEx("\\A(\\d+).*", row[2]); 
                        if (r.find()) {
                            Integer i = 0;
                            try {
                                i = Integer.parseInt(trimTxt(r.group(1)));
                            } catch(IllegalStateException e) { }
                            equipment.setWeight(i);
                            
                        } else {
                            equipment.setWeight(0);
                        }

                    } 
                continue;
                }
            }
            
            //Table Row
            regex = new RegEx("\\A(.*)\\:(.*)", sLine);
            if (regex.find() ) {
                String eName = trimTxt(regex.group(1));
                if (Equipment.containsKey(eName)) {
                    String text = makeFormattedText(regex.group(2));
                    CoreRPG_Equipment equip = Equipment.get(eName);
                    equip.setText(text);
                    Equipment.put(eName, equip);
                }
            }    
        }
        
        if (equipment != null && equipment.getName().equals(sName)) {
            if (equipment.isComplete()) {
                con.printMsg("Parse : equipment");
                con.printMsgResult(equipment.getName(), normal);
                engine.debug(2,"Parse Completed - " + equipment.getName());
            } else {
                con.printMsg("Parse : equipment");
                con.printMsgResult(equipment.getName(), warning);
                engine.debug(2,"Parse Incomplete - " + equipment.getName());
            }
            Equipment.put(equipment.getName(), equipment);
        }
    }
    private void parseMagicItemData(String[] aSourceData, ObjectContainer objects) {
        engine.debug(1, "->parseMagicItemData()");
        
        String sName, sType, sSubType = "", sRarity, sText;
        boolean bNewMagicItem = true;
        CoreRPG_MagicItem magicitem = null;
        
        for (String sLine : aSourceData) {
            //Empty or blank lines, skip
            RegEx emptyline = new RegEx("\\A$",sLine);
            RegEx whitespaceline = new RegEx("\\A\\s+$",sLine);
            if (emptyline.find() || whitespaceline.find()) {              
                engine.debug(2,"Empty Line");
                bNewMagicItem = true;
                continue;
            } 
            
            //New Magic Item
            if (bNewMagicItem) {
                
                if (magicitem != null && magicitem.isComplete()) {
                    MagicItems.put(magicitem.getName(), magicitem);
                    con.printMsg("Parse : magic item");
                    con.printMsgResult(magicitem.getName(), normal);
                    engine.debug(2,"Parse Completed" + magicitem.getName());
                } else if (magicitem != null ) {
                    con.printMsg("Parse : magic item");
                    con.printMsgResult(magicitem.getName(), warning);
                    engine.debug(2,"Parse Incomplete - " + magicitem.getName());                   
                } 
                
                bNewMagicItem = false;
                sName = trimTxt(sLine);
                magicitem = new CoreRPG_MagicItem(sName);
                engine.debug(2,"Magic Item - " + sName);
                continue;
            }
            
            if (magicitem != null) {
               magicitem.setText(magicitem.getText() + makeFormattedText(sLine));
            }   
        }
        
        if (magicitem != null && magicitem.isComplete()) {
            MagicItems.put(magicitem.getName(), magicitem);
            con.printMsg("Parse : magic item");
            con.printMsgResult(magicitem.getName(), normal);
            engine.debug(2,"Parse Completed" + magicitem.getName());
        } else if (magicitem != null ) {
            con.printMsg("Parse : magic item");
            con.printMsgResult(magicitem.getName(), warning);
            engine.debug(2,"Parse Incomplete - " + magicitem.getName());                   
        } 
        
        List<CoreRPG_MagicItem> mis = objects.query(new Predicate<CoreRPG_MagicItem>() {
            @Override
            public boolean match(CoreRPG_MagicItem obj) {
                return !obj.getName().equals("   ");
            }
        }); 
        if (!mis.isEmpty()) {
            for (CoreRPG_MagicItem mi : mis) {
                if (!MagicItems.containsKey(mi.getName())) {
                    MagicItems.put(mi.getName(), mi);
                    con.printMsg("Import: magic item");
                    con.printMsgResult(mi.getName(), normal);
                    engine.debug(2,"Archive Import Completed - " + mi.getName());
                }
            }
        }
    }
    private void parseImageData(String[] aSourceData) {
        engine.debug(1, "->parseImageData()");
        
        File file;
        CoreRPG_Image image;
        
        for (String imageFilepath : aSourceData) {
           file = new File(imageFilepath);
           if (file.exists()) {
               image = new CoreRPG_Image(file.getName(), file.getPath());
               Images.put(file.getName(), image);
               con.printMsg("Parse : image");
               con.printMsgResult(file.getName(), normal);
           } else {
               con.printMsg("Parse : image");
               con.printMsgResult(file.getName(), warning);
           }
            
        }
        
    }
    private void parseImagePinData(String[] aSourceData) {
        engine.debug(1, "->parseImagePinData()");
        
        //Loop through each line of source data
        for (String sLine : aSourceData) {
            RegEx emptyline = new RegEx("\\A$",sLine);
            RegEx whitespaceline = new RegEx("\\A\\s+$",sLine);
            if (emptyline.find() || whitespaceline.find()) {
                engine.debug(2,"Empty Line");
                continue;
            }
            RegEx regex = new RegEx("\\A(.*)\\;.*;", sLine);
            if (regex.find() ) {
                String[] pins = getPinData(sLine);       
                if (pins != null) {
                    CoreRPG_ImagePin pin = new CoreRPG_ImagePin(pins[0], Integer.parseInt(pins[1]), Integer.parseInt(pins[2]), pins[3], pins[4]);
                    
                    if (ImagePins.containsKey(pins[0])) {   
                        ImagePins.get(pins[0]).addShortcut(pins[0], Integer.parseInt(pins[1]), Integer.parseInt(pins[2]), pins[3], pins[4]);
                    } else {
                        CoreRPG_ImagePins ipins = new CoreRPG_ImagePins(pins[0]);
                        ipins.addShortcut(pins[0], Integer.parseInt(pins[1]), Integer.parseInt(pins[2]), pins[3], pins[4]);
                        ImagePins.put(pins[0], ipins);
                    }                 
                    con.printMsg("Parse - imagepin");
                    con.printMsgResult(pins[0], normal);
                } else {
                    con.printMsg("Parse - imagepin");
                    con.printMsgResult(regex.group(1), warning);
                }
                
            }
        }
    }
    private void parseNPCData(String[] aSourceData, ObjectContainer objects) {
        engine.debug(1, "->parseNPCData()");
        
        //Variables
        String sName, sSpace, sReach, sSkills, sLanguages, sEquipment, sText;
        boolean bNewNPC = true;
        CoreRPG_NPC npc = null;
        
        //Loop through content line by line
        for (String sLine : aSourceData) {
            //Empty or blank lines, skip
            RegEx emptyline = new RegEx("\\A$",sLine);
            RegEx whitespaceline = new RegEx("\\A\\s+$",sLine);
            if (emptyline.find() || whitespaceline.find()) {              
                engine.debug(2,"Empty Line");
                bNewNPC = true;
                continue;
            } 
            
            //New NPC
            if (bNewNPC) {
                if (npc != null && npc.isComplete()) {
                    NPCs.put(npc.getName(), npc);
                    con.printMsg("Parse : npc");
                    con.printMsgResult(npc.getName(), normal);
                    engine.debug(2,"Parse Completed" + npc.getName());
                } else if (npc != null ) {
                    con.printMsg("Parse : npc");
                    con.printMsgResult(npc.getName(), warning);
                    engine.debug(2,"Parse Incomplete - " + npc.getName());                   
                } 
                
                bNewNPC = false;
                sName = trimTxt(sLine);
                npc = new CoreRPG_NPC(sName);
                engine.debug(2,"NPC - " + sName);
                continue;
            }
            
            //Space
            RegEx regex = new RegEx("\\ASpace\\s(\\d+)", sLine);
            if (regex.find()) {
                sSpace = trimTxt(regex.group(1));
                if (npc != null) {
                    npc.setSpace(Integer.parseInt(sSpace));
                    engine.debug(2,"Space - " + sSpace);
                }
                continue;
            }
            
            //Reach
            regex = new RegEx("\\AReach\\s(\\d+)", sLine);
            if (regex.find()) {
                sReach = trimTxt(regex.group(1));
                if (npc != null) {
                    npc.setReach(Integer.parseInt(sReach));
                    engine.debug(2,"Reach - " + sReach);
                }
                continue;
            }
            
            //Skills
            regex = new RegEx("\\ASkills\\s(.*)$", sLine);
            if (regex.find()) {
                sSkills = trimTxt(regex.group(1));
                if (npc != null) {
                    npc.setSkills(sSkills);
                    engine.debug(2,"Skills - " + sSkills);
                }
                continue;
            }
            
            //Languages
            regex = new RegEx("\\ALanguages\\s(.*)", sLine);
            if (regex.find()) {
                sLanguages = trimTxt(regex.group(1));
                if (npc != null) {
                    npc.setLanguages(sLanguages);
                    engine.debug(2,"Languages " + sLanguages);
                }
                continue;
            }
            
            //Equipment
            regex = new RegEx("\\AEquipment\\s(.*)", sLine);
            if (regex.find()) {
                sEquipment = trimTxt(regex.group(1));
                if (npc != null) {
                    npc.setEquipment(sEquipment);
                    engine.debug(2,"Equipment " + sEquipment);
                }
                continue;
            }

            //Notes
            regex = new RegEx("\\ANotes", sLine);
            if (regex.find()) {
                continue;
            }
            
            if (npc != null) {
                npc.setText(npc.getText() + makeFormattedText(sLine));
            }
        }  
        
        if (npc != null && npc.isComplete()) {
            NPCs.put(npc.getName(), npc);
            con.printMsg("Parse : npc");
            con.printMsgResult(npc.getName(), normal);
            engine.debug(2,"Parse Completed" + npc.getName());
        } else if (npc != null ) {
            con.printMsg("Parse : npc");
            con.printMsgResult(npc.getName(), warning);
            engine.debug(2,"Parse Incomplete - " + npc.getName());                   
        } 
        
        // Loop through tmp Archive objects and add to npc and magicitem linked hash maps
        List<CoreRPG_NPC> npcs = objects.query(new Predicate<CoreRPG_NPC>() {
            @Override
            public boolean match(CoreRPG_NPC obj) {
                return !obj.getName().equals("   ");
            }
        }); 
        if (!npcs.isEmpty()) {
            for (CoreRPG_NPC obj : npcs) {
                if (!NPCs.containsKey(obj.getName())) {
                    NPCs.put(obj.getName(), obj);
                    con.printMsg("Import: npc");
                    con.printMsgResult(obj.getName(), normal);
                    engine.debug(2,"Archive Import Completed - " + obj.getName());
                }
            }
        }
    }
    private void parseParcelData(String[] aSourceData) {
        engine.debug(1, "->parseParcelData()");
        
        String sName, sCategory = "", sCoinName, sItemName;
        Integer dCoinNum, dItemNum;
        CoreRPG_Parcel parcel = null;
        
        for (String sLine : aSourceData) {
            //Empty or blank lines, skip
            RegEx emptyline = new RegEx("\\A$",sLine);
            RegEx whitespaceline = new RegEx("\\A\\s+$",sLine);
            if (emptyline.find() || whitespaceline.find()) {              
                engine.debug(2,"Empty Line");
                continue;
            } 
            
            //New Category
            RegEx regex = new RegEx("\\A\\#\\@\\;(.*)",sLine);
            if (regex.find()) {
                sCategory = trimTxt(regex.group(1));
                engine.debug(2,"Category - " + sCategory);
                continue;
            }

            //New Parcel
            regex = new RegEx("\\A\\#\\#\\;(.*)",sLine);
            if (regex.find()) {
                
                if (parcel != null && parcel.isComplete()) {
                    Parcels.put(parcel.getName(), parcel);
                    con.printMsg("Parse : parcel");
                    con.printMsgResult(parcel.getName(), normal);
                    engine.debug(2,"Parse Completed - " + parcel.getName());
                } else if (parcel != null ) {
                    con.printMsg("Parse : parcel");
                    con.printMsgResult(parcel.getName(), warning);
                    engine.debug(2,"Parse Incomplete - " + parcel.getName());                   
                } 
                
                sName = trimTxt(regex.group(1));
                parcel = new CoreRPG_Parcel(sName);
                parcel.setCategory(sCategory);
                engine.debug(2,"Parcel - " + sName);
                continue;
            }
            
            //New Coin
            regex = new RegEx("\\Acoin\\;(\\d+);(.*)",sLine);
            if (regex.find()) {
                dCoinNum = Integer.parseInt(trimTxt(regex.group(1)));
                sCoinName = trimTxt(regex.group(2));
                if (parcel != null) {
                    parcel.addCoin(sCoinName, dCoinNum);
                    engine.debug(2,"Coin - " + dCoinNum.toString() + " " + sCoinName);
                }
                continue;
            }

            //New Item
            regex = new RegEx("\\Aitem\\;(\\d+);(.*)",sLine);
            if (regex.find()) {
                dItemNum = Integer.parseInt(trimTxt(regex.group(1)));
                sItemName = trimTxt(regex.group(2));
                if (parcel != null) {
                    parcel.addItem(sItemName, dItemNum);
                    engine.debug(2,"Item - " + dItemNum.toString() + " " + sItemName);
                }
            }
        }
        
        if (parcel != null && parcel.isComplete()) {
            Parcels.put(parcel.getName(), parcel);
            con.printMsg("Parse : parcel");
            con.printMsgResult(parcel.getName(), normal);
            engine.debug(2,"Parse Completed - " + parcel.getName());
        } else if (parcel != null ) {
            con.printMsg("Parse : parcel");
            con.printMsgResult(parcel.getName(), warning);
            engine.debug(2,"Parse Incomplete - " + parcel.getName());                   
        }   
    }
    private void parseReferenceManualData(String[] aSourceData) {
        engine.debug(1, "->parseReferenceManualData()");
        
        String sChapterName = "", sSubChapterName = "", sPageName = "", sText;
        CoreRPG_ReferencePage page;
        CoreRPG_ReferenceSubChapter subchapter = null;
        CoreRPG_ReferenceChapter chapter = null;
        ReferenceManual = new CoreRPG_ReferenceManual(module.getName());
        
        //engine.progressBar.setString("Please Wait ...");
        
        for (String sLine : aSourceData) {
            //Empty or blank lines, skip
            RegEx emptyline = new RegEx("\\A$",sLine);
            RegEx whitespaceline = new RegEx("\\A\\s+$",sLine);
            if (emptyline.find() || whitespaceline.find()) {              
                engine.debug(2,"Empty Line");
                continue;
            } 
            
            //New Chapter
            RegEx regex = new RegEx("\\A\\#\\@\\;(.*)",sLine);
            if (regex.find()) {

                sChapterName = trimTxt(regex.group(1));
                chapter = new CoreRPG_ReferenceChapter(sChapterName);
                ReferenceManual.addChapter(sChapterName, chapter);
                
                engine.debug(2,"Chapter - " + sChapterName);
                con.printMsg("Parse - reference chapter");
                con.printMsgResult(sChapterName, normal);
                continue;
            } 
            
            //New SubChapter
            regex = new RegEx("\\A\\#\\!\\;(.*)",sLine);
            if (regex.find()) {

                sSubChapterName = trimTxt(regex.group(1));
                subchapter = new CoreRPG_ReferenceSubChapter(sSubChapterName);
                chapter.addSubChapter(sSubChapterName, subchapter);
                
                engine.debug(2,"SubChapter - " + sSubChapterName);
                con.printMsg("Parse - reference subchapter");
                con.printMsgResult(sSubChapterName, normal);
                continue;
            } 
            
            //New Page
            regex = new RegEx("\\A\\#\\#\\;(.*)",sLine);
            if (regex.find()) {                
                
                sPageName = trimTxt(regex.group(1));
                page = new CoreRPG_ReferencePage(sPageName);
                subchapter.addPage(sPageName, page);
                
                engine.debug(2,"Page - " + sPageName);
                con.printMsg("Parse - reference page");
                con.printMsgResult(sPageName, normal);
                continue;
            } 
            
            sText = trimTxt(sLine);
            ReferenceManual.getChapters().get(sChapterName).getSubChapters().get(sSubChapterName).getPages().get(sPageName).setText(
                    ReferenceManual.getChapters().get(sChapterName).getSubChapters().get(sSubChapterName).getPages().get(sPageName).getText() +
                    makeFormattedText(sLine)        
            );

            String cleanTxt = cleanTxt(sLine);

            ReferenceManual.getChapters().get(sChapterName).getSubChapters().get(sSubChapterName).getPages().get(sPageName).setKeywords( 
                    ReferenceManual.getChapters().get(sChapterName).getSubChapters().get(sSubChapterName).getPages().get(sPageName).getKeywords() +
                    getKeyWords(cleanTxt)
            );
        }
        //engine.progressBar.setString("");
    }
    private void parseStoryData(String[] aSourceData) {
        engine.debug(1, "->parseStoryData()");
        
        String sName = "", sCategory = "";
        CoreRPG_Story story = null;
        
        for (String sLine : aSourceData) {
            //Check for empty line and skip
            RegEx emptyline = new RegEx("\\A$",sLine);
            RegEx whitespaceline = new RegEx("\\A\\s+$",sLine);
            if (emptyline.find() || whitespaceline.find()) {
                engine.debug(2,"Empty Line");
                continue;
            }
            
            //New Category
            RegEx regex = new RegEx("\\A\\#\\@\\;(.*)",sLine);
            if (regex.find()) {
                sCategory = trimTxt(regex.group(1));
                engine.debug(2,"Category - " + sCategory);
                continue;
            }
            
            //New Story
            regex = new RegEx("\\A\\#\\#\\;(.*)",sLine);
            if (regex.find()) {
                if (story != null && story.getName().equals(sName)) {                    
                    if (story.isComplete()) {
                        con.printMsg("Parse : story ");
                        con.printMsgResult(sName, normal);
                        engine.debug(2,"Parse Completed - " + sName);
                    } else {    
                        con.printMsg("Parse : story ");
                        con.printMsgResult(sName, warning);
                        engine.debug(2,"Parse Incomplete - " + sName);
                    }       
                    Stories.put(sName, story);
                    
                    //Add story to index of stories by category
                    String[] stories;
                    if (StoriesByCategory.get(sCategory) != null ) {
                        stories = Arrays.copyOf(StoriesByCategory.get(sCategory), StoriesByCategory.get(sCategory).length+1);
                        stories[stories.length-1] = sName;
                    } else {
                        stories = new String[1];
                        stories[0] = sName;
                    }
                    StoriesByCategory.put(sCategory, stories);
                }
                
                sName = trimTxt(regex.group(1));
                story = new CoreRPG_Story(sName);
                story.setCategory(sCategory);
                engine.debug(2,"Story - " + sName);
                continue;  
            }
            
            if (story != null) {
                story.setText(story.getText() + makeFormattedText(sLine));
            }
        }
        
        if (story != null && story.getName().equals(sName)) {                    
            if (story.isComplete()) {
                con.printMsg("Parse : story ");
                con.printMsgResult(sName, normal);
                engine.debug(2,"Parse Completed - " + sName);
            } else {    
                con.printMsg("Parse : story ");
                con.printMsgResult(sName, warning);
                engine.debug(2,"Parse Incomplete - " + sName);
            }       
            Stories.put(sName, story);

            //Add story to index of stories by category
            String[] stories;
            if (StoriesByCategory.get(sCategory) != null ) {
                stories = Arrays.copyOf(StoriesByCategory.get(sCategory), StoriesByCategory.get(sCategory).length+1);
                stories[stories.length-1] = sName;
            } else {
                stories = new String[1];
                stories[0] = sName;
            }
            StoriesByCategory.put(sCategory, stories);
        }
    } 
    private void parseTableData(String[] aSourceData) {
        engine.debug(1, "->parseTableData()");
        
        String sName = "", sCategory = "", sDescription;
        CoreRPG_Table table = null;
        
        for (String sLine : aSourceData) {
            //Check for empty line and skip
            RegEx emptyline = new RegEx("\\A$",sLine);
            RegEx whitespaceline = new RegEx("\\A\\s+$",sLine);
            if (emptyline.find() || whitespaceline.find()) {
                engine.debug(2,"Empty Line");
                continue;
            }
            
            //New Category
            RegEx regex = new RegEx("\\A\\#\\@\\;(.*)",sLine);
            if (regex.find()) {
                sCategory = trimTxt(regex.group(1));
                engine.debug(2,"Category - " + sCategory);
                continue;
            }
            
            //New Table
            regex = new RegEx("\\A\\#\\#\\;(.*)",sLine);
            if (regex.find()) {
                if (table != null && table.getName().equals(sName)) {                    
                    if (table.isComplete()) {
                        con.printMsg("Parse : table ");
                        con.printMsgResult(sName, normal);
                        engine.debug(2,"Parse Completed - " + sName);
                    } else {    
                        con.printMsg("Parse : table ");
                        con.printMsgResult(sName, warning);
                        engine.debug(2,"Parse Incomplete - " + sName);
                    }       
                    Tables.put(sName, table);
                    
                    //Add table to index of stories by category
                    String[] tables;
                    if (TablesByCategory.get(sCategory) != null ) {
                        tables = Arrays.copyOf(TablesByCategory.get(sCategory), TablesByCategory.get(sCategory).length+1);
                        tables[tables.length-1] = sName;
                    } else {
                        tables = new String[1];
                        tables[0] = sName;
                    }
                    TablesByCategory.put(sCategory, tables);
                }
                
                sName = trimTxt(regex.group(1));
                table = new CoreRPG_Table(sName);
                table.setCategory(sCategory);
                engine.debug(2,"Table - " + sName);
                continue;  
            }
            
            //New Table Description
            regex = new RegEx("\\A\\#\\!\\;(.*)",sLine);
            if (regex.find()) {
                sDescription = trimTxt(regex.group(1));
                if (table != null) {
                    table.setDescription(sDescription);
                }
                engine.debug(2,"Table Description");
                continue;
            }
            
            //Table Columns
            regex = new RegEx("\\Acolumn;(.*)",sLine);
            if (regex.find()) {
                String rawheaders = trimTxt(regex.group(1));
                String[] headers = rawheaders.split(";");
                if (table != null) {
                    for (String header : headers) {
                        table.addColumn(header);
                    }
                }
                engine.debug(2,"Table Headers");
                continue;
            }
            
            //Table Rows
            regex = new RegEx("\\Arow;(.*)",sLine);
            if (regex.find()) {
                if (table != null) {
                    String rawrow = trimTxt(regex.group(1));
                    String[] row = rawrow.split(";");
                    Integer fRange = Integer.parseInt(row[0]);
                    Integer tRange = Integer.parseInt(row[1]);

                    String[] results = new String[row.length-2];
                    for (Integer i=0;i<results.length;i++) {
                        results[i] = row[i+2];
                    }

                    CoreRPG_TableRow res = new CoreRPG_TableRow(fRange, tRange, results);
                    table.addDataRow(res);
                    
                }
                engine.debug(2,"Table Row");
                continue;
            }
            
            
            if (table != null) {
                table.setText(table.getText() + makeFormattedText(sLine));
                engine.debug(2,"Table Notes");
            }
        }
        
        if (table != null && table.getName().equals(sName)) {                    
            if (table.isComplete()) {
                con.printMsg("Parse : table ");
                con.printMsgResult(sName, normal);
                engine.debug(2,"Parse Completed - " + sName);
            } else {    
                con.printMsg("Parse : table ");
                con.printMsgResult(sName, warning);
                engine.debug(2,"Parse Incomplete - " + sName);
            }       
            Tables.put(sName, table);

            //Add table to index of stories by category
            String[] tables;
            if (TablesByCategory.get(sCategory) != null ) {
                tables = Arrays.copyOf(TablesByCategory.get(sCategory), TablesByCategory.get(sCategory).length+1);
                tables[tables.length-1] = sName;
            } else {
                tables = new String[1];
                tables[0] = sName;
            }
            TablesByCategory.put(sCategory, tables);
        }
        
    }
    private void parseTokenData(String[] aSourceData) {
        engine.debug(1, "->parseTokenData()");
        
        File file;
        CoreRPG_Token token;
        
        for (String tokenFilepath : aSourceData) {
           file = new File(tokenFilepath);
           if (file.exists()) {
               token = new CoreRPG_Token(file.getName(), file.getPath());
               Tokens.put(file.getName(), token);
               con.printMsg("Parse : token");
               con.printMsgResult(file.getName(), normal);
           } else {
               con.printMsg("Parse : token");
               con.printMsgResult(file.getName(), warning);
           }
            
        }
        
        //Update NPCs
        for (Map.Entry entry : NPCs.entrySet()) {
            String npcKey = (String) entry.getKey();
            CoreRPG_NPC npc = (CoreRPG_NPC) entry.getValue();
            String sanatisedName  = sanatise(npcKey);
            
            if (npc.getToken().equals("")) {
                con.printMsg("Parse : auto linked npc token");
                if (Tokens.containsKey(sanatisedName + ".png")) {
                    npc.setToken(Tokens.get(sanatisedName+".png").getName());
                    con.printMsgResult(npcKey+"->"+sanatisedName+".png", normal);
                } else if (Tokens.containsKey(npcKey.substring(0, 1) + ".png")) {
                    npc.setToken(Tokens.get(npcKey.substring(0, 1) + ".png").getName());
                    con.printMsgResult(npcKey+"->"+npcKey.substring(0, 1) + ".png", normal);
                } else {
                    con.printMsgResult(npcKey, warning);
                }
            }
        }

    }
    
    //Private Reference index generation methods
    private void generateEquipmentIndexes(String aSourceData) {
        engine.debug(1, "->generateEquipmentIndexes()");
    }
    private void generateMagicItemIndexes(String aSourceData) {
        engine.debug(1, "->generateMagicItemIndexes()");
    }
    private void generateNPCIndexes(String aSourceData) {
        engine.debug(1, "->generateNPCIndexes()");
    }
    
    //Private XML generation methods
    private void generateEncounterXML(Element E) {
        engine.debug(1, "->generateEncounterXML()");
        
        Element battle = new Element("battle");
        for (Map.Entry entry : EncountersByCategory.entrySet()) {
            String key = (String) entry.getKey();
            String[] val = (String[]) entry.getValue();
            Element eCat = createElement("category");
            eCat.setAttribute("name", key);
            eCat.setAttribute("mergeid", module.getID());
            eCat.setAttribute("baseicon", "2");
            eCat.setAttribute("decalicon", "1");
            
            battle.addContent(eCat);
            
            for (String encname : val) {
                CoreRPG_Encounter enc = Encounters.get(encname);
                
                if (enc != null) {
                    String sanatisedName = sanatise(enc.getName());
                    
                    Element e = createElement("bat_" + sanatisedName);
                    eCat.addContent(e);
                    
                    Element eLocked = createElement("locked", "0", "number");
                    e.addContent(eLocked);
                    Element eName = createElement("name", enc.getName(), "string");
                    e.addContent(eName);
                    
                    Element eNPC = createElement("npclist");
                    Integer count = 0;
                    for (Map.Entry ent : enc.getNPCs().entrySet()) {
                        String k = (String) ent.getKey();
                        CoreRPG_EncounterNPC v = (CoreRPG_EncounterNPC) ent.getValue();
                        
                        count++;
                        Element nid = createElement(makeID(count));
                        eNPC.addContent(nid);
                        Element ncount = createElement("count", v.getNumber().toString(), "number");
                        Element nname = createElement("name", v.getName(), "string");
                        nid.addContent(nname);
                        nid.addContent(ncount);

                        Element nlink = createElement("link", "", "windowreference");
                        nid.addContent(nlink);
                        
                        Element lclass = createElement("class", "reference_npc");
                        String lsanatisedName = sanatise(v.getName());
                        Element lrecord = createElement("recordname", "reference" + module.getID() + ".npcdata." + lsanatisedName + "@" + module.getName());
                        nlink.addContent(lclass);
                        nlink.addContent(lrecord);   
                    }  
                    e.addContent(eNPC);
                }   
            }   
        }
        E.addContent(battle);
    } 
    private void generateItemXML(Element E) {
        engine.debug(1, "->generateItemXML()");
    
        //Equipment
        Element item = new Element("item");
        Element iCat = createElement("category");
        iCat.setAttribute("name", "Items - " + module.getName());
        iCat.setAttribute("mergeid", module.getID());
        iCat.setAttribute("baseicon", "2");
        iCat.setAttribute("decalicon", "1");
        item.addContent(iCat);
        generateEquipmentXML(iCat, "items");     
        
        //Magic Items
        iCat = createElement("category");
        iCat.setAttribute("name", "Magic Items - " + module.getName());
        iCat.setAttribute("mergeid", module.getID());
        iCat.setAttribute("baseicon", "2");
        iCat.setAttribute("decalicon", "1");
        item.addContent(iCat);
        generateMagicItemXML(iCat, false);

        E.addContent(item);
    }
    private void generateEquipmentXML(Element E, String sType) {
        engine.debug(1, "->generateEquipmentXML()");      
        
        Element equipment;
        String type;
        if (sType != null) {
            equipment = E;
        } else {
            equipment = createElement("equipmentdata");
            E.addContent(equipment);
        }
         
        for (Map.Entry entry : Equipment.entrySet()) {
            String key = (String) entry.getKey();
            CoreRPG_Equipment val = (CoreRPG_Equipment) entry.getValue();
            String sanatisedName = sanatise(key);
            
            Element i = createElement(sanatisedName);
            equipment.addContent(i);
            
            Element iName = createElement("name", val.getName(), "string");
            Element iID = createElement("isidentified", "0", "number");
            Element iLocked = createElement("locked", "0", "number");
            Element iCost = createElement("cost", val.getCost(), "string");
            Element iWeight = createElement("weight", val.getWeight().toString(), "number");
            Element iNotes = createElement("notes", val.getText().toString(), "formattedtext");
            
            i.addContent(iName);
            i.addContent(iID);
            i.addContent(iLocked);
            i.addContent(iCost);
            i.addContent(iWeight);
            i.addContent(iNotes);
        }    
    }
    private void generateMagicItemXML(Element E, boolean reference) {
        engine.debug(1, "->generateMagicItemXML()");
        Element magicitemdata;
        
        if (!reference) {
           magicitemdata = E;
        } else {
           magicitemdata = new Element("magicitemdata"); 
           E.addContent(magicitemdata);
        }
        
        for (Map.Entry entry : MagicItems.entrySet()) {
            String key = (String) entry.getKey();
            CoreRPG_MagicItem val = (CoreRPG_MagicItem) entry.getValue();
            String sanatisedName = sanatise(key);
            
            Element mi = createElement(sanatisedName);
            magicitemdata.addContent(mi);
            
            Element iID = createElement("isidentified", "0", "number");
            Element iLocked = createElement("locked", "0", "number");
            Element iName = createElement("name", val.getName(), "string");
            Element iDesc = createElement("notes", val.getText(), "formattedtext");
            
            mi.addContent(iName);
            mi.addContent(iID);
            mi.addContent(iLocked);
            mi.addContent(iDesc);
        }

        
    }
    private void generateImageXML(Element E, boolean reference) {
        engine.debug(1, "->generateImageXML()");
        Element image, node;
        
        if (reference) {
            image = createElement("imagedata");
            node = image;
        } else {
            image = createElement("image");
            Element iCat = createElement("category");
            iCat.setAttribute("name", module.getName());
            iCat.setAttribute("mergeid", module.getID());
            iCat.setAttribute("baseicon", "2");
            iCat.setAttribute("decalicon", "1");
            image.addContent(iCat);
            node = iCat;
        } 
        
        for (Map.Entry entry : Images.entrySet()) {
            String key = (String) entry.getKey();
            CoreRPG_Image val = (CoreRPG_Image) entry.getValue();
            String sanatisedName = sanatise(key);
            Element iName = createElement("img_" + sanatisedName);
            Element iImage = createElement("image", "", "image");
            String n = key.replace(".png", "");
            n = n.replace(".jpg", "");
            Element iN = createElement("name", n, "string");
            
            node.addContent(iName);
            iName.addContent(iN);
            iName.addContent(iImage);

            RegEx regex = new RegEx("(.*\\.[png|jpg]+)", key);
            if (regex.find()) {
                Element iBitmap = createElement("bitmap", "images/" + regex.group(1), "string");
                iImage.addContent(iBitmap);
                
                String namenoext = regex.group(1);
                namenoext = namenoext.replace(".jpg", "");
                namenoext = namenoext.replace(".png", "");
                
                if (ImagePins != null && ImagePins.containsKey(namenoext)) {
                    Element iShortcuts = createElement("shortcuts");
                    iImage.addContent(iShortcuts);
                    
                    CoreRPG_ImagePin[] pins = ImagePins.get(namenoext).getShortCuts();
                    for (CoreRPG_ImagePin pin : pins) {
                        Element iShortcut = createElement("shortcut");
                        iShortcuts.addContent(iShortcut);
                        Element xpos = createElement("x", pin.getX().toString());
                        Element ypos = createElement("y", pin.getY().toString());
                        Element refclass = createElement("class", pin.getReferenceClass());
                        Element refname = createElement("recordname", pin.getReferenceName());
                        iShortcut.addContent(xpos);
                        iShortcut.addContent(ypos);
                        iShortcut.addContent(refclass);
                        iShortcut.addContent(refname);   
                    }
                }
            }
        }
        E.addContent(image);
    }
    private void generatePersonalitiesXML(Element E) {
        engine.debug(1, "->generatePersonalitiesXML()");
        Element npc = createElement("npc");
        
        BitSet bs = (BitSet) module.getDataItems().get("npcs");
        if (bs.get(0) || bs.get(1)) {
            //NPCs
            Element iCat = createElement("category");
            iCat.setAttribute("name", "NPCs - " + module.getName());
            iCat.setAttribute("mergeid", module.getID());
            iCat.setAttribute("baseicon", "2");
            iCat.setAttribute("decalicon", "1");
            npc.addContent(iCat);
            generateNPCXML(iCat, false);
        }
        
        E.addContent(npc);
    }
    private void generateNPCXML(Element E, boolean reference) {
        engine.debug(1, "->generateNPCXML()");
        Element node;
        
        if (reference) {
            node = createElement("npcdata");
            E.addContent(node);
        } else {
            node = E;
        }
        
        for (Map.Entry entry : NPCs.entrySet()) {
            String key = (String) entry.getKey();
            CoreRPG_NPC val = (CoreRPG_NPC) entry.getValue();
            String sanatisedName = sanatise(key);
            
            Element npc = createElement(sanatisedName);
            node.addContent(npc);
            
            Element nName = createElement("name", val.getName(), "string");
            Element nSpace = createElement("space", val.getSpace().toString(), "number");
            Element nReach = createElement("reach", val.getReach().toString(), "number");
            Element nSkills = createElement("skills", val.getSkills(), "string");
            Element nLanguages = createElement("languages", val.getLanguages(), "string");
            Element nEquipment = createElement("items", val.getEquipment(), "string");
            Element nNotes = createElement("notes", val.getText(), "formattedtext");
            npc.addContent(nName);
            npc.addContent(nSpace);
            npc.addContent(nReach);
            npc.addContent(nSkills);
            npc.addContent(nLanguages);
            npc.addContent(nEquipment);
            npc.addContent(nNotes);
            
            Element nLocked = createElement("locked", "0", "number");
            npc.addContent(nLocked);
            
            if (!val.getToken().equals("")) {
                Element nToken = createElement("token", "tokens" + slash + module.getName() + slash + val.getToken() + "@" + module.getName(), "token");
                npc.addContent(nToken);
            }
            
        }
    }
    private void generateParcelXML(Element E) {
        engine.debug(1, "->generateParcelXML()");
        Integer count = 0;
        Element parcel = createElement("treasureparcels");
        Element iCat = createElement("category");
        iCat.setAttribute("name", "Parcels - " + module.getName());
        iCat.setAttribute("mergeid", module.getID());
        iCat.setAttribute("baseicon", "2");
        iCat.setAttribute("decalicon", "1");
        parcel.addContent(iCat);
        
        for (Map.Entry entry : Parcels.entrySet()) {
            String key = (String) entry.getKey();
            CoreRPG_Parcel val = (CoreRPG_Parcel) entry.getValue();
            count++;
            
            Element id = createElement(makeID(count));
            iCat.addContent(id);
            
            Element pName = createElement("name", val.getName(), "string");
            Element pLocked = createElement("locked", "1", "number");
            id.addContent(pName);
            id.addContent(pLocked);

            Integer ecount = 0;
            if (!val.getCoins().isEmpty()) {
                Element list = createElement("coinlist");
                id.addContent(list);
                
                for (Map.Entry e : val.getCoins().entrySet()) {
                    String ekey = (String) e.getKey();
                    Integer eval = (Integer) e.getValue();
                    ecount++;
                    Element eid = createElement(makeID(ecount));
                    list.addContent(eid);
                    
                    Element amount = createElement("amount", eval.toString(), "number");
                    Element desc = createElement("description", ekey, "string");
                    eid.addContent(amount);
                    eid.addContent(desc);
                }
            }
            
            ecount = 0;
            if (!val.getItems().isEmpty()) {
                Element list = createElement("itemlist");
                id.addContent(list);
                
                for (Map.Entry e : val.getItems().entrySet()) {
                    String ekey = (String) e.getKey();
                    Integer eval = (Integer) e.getValue();
                    ecount++;
                    Element eid = createElement(makeID(ecount));
                    list.addContent(eid);
                    
                    Element amount = createElement("count", eval.toString(), "number");
                    Element iName = createElement("name", ekey, "string");
                    eid.addContent(iName);
                    eid.addContent(amount);
                    
                    if (Equipment.containsKey(ekey)) {
                        CoreRPG_Equipment item = Equipment.get(ekey);
                        
                        Element iCost = createElement("cost", item.getCost(), "string");
                        Element iWeight = createElement("weight", item.getWeight().toString(), "number");

                        eid.addContent(iCost);
                        eid.addContent(iWeight);
                        
                        
                    } else if (MagicItems.containsKey(ekey)) {
                        CoreRPG_MagicItem item = MagicItems.get(ekey);
                        
                        Element iDesc = createElement("description", item.getText(), "formattedtext");

                        eid.addContent(iDesc);    
                    }
                }
            }
        }
        E.addContent(parcel);
    }
    private void generateReferenceManualXML(Element E) {
        engine.debug(1, "->generateReferenceManualXML()");
        
        Integer chapterCount = -1, subchapterCount, pageCount;
        Element refmanualdata = createElement("refmanualdata");
        
        for (Map.Entry e1 : ReferenceManual.getChapters().entrySet()) {
            String chapterKey = (String) e1.getKey();
            CoreRPG_ReferenceChapter chapter = (CoreRPG_ReferenceChapter) e1.getValue();
            chapterCount++;
            subchapterCount = -1;
            for (Map.Entry e2 : chapter.getSubChapters().entrySet()) {
                String subchapterKey = (String) e2.getKey();
                CoreRPG_ReferenceSubChapter subchapter = (CoreRPG_ReferenceSubChapter) e2.getValue();
                subchapterCount++;
                pageCount = -1;
                for (Map.Entry e3 : subchapter.getPages().entrySet()) {
                    String pageKey = (String) e3.getKey();
                    CoreRPG_ReferencePage page = (CoreRPG_ReferencePage) e3.getValue();
                    pageCount++;
                    String chapTag = padID(chapterCount);
                    String subchapTag = padID(subchapterCount);
                    String pageTag = padID(pageCount);
                    Element id = createElement("refpage_" + chapTag + subchapTag + pageTag + sanatise(pageKey));
                    refmanualdata.addContent(id);
                    
                    Element iName = createElement("name", pageKey, "string");
                    Element iGroup = createElement("group", chapterKey, "string");
                    Element iSubGroup = createElement("subgroup", subchapterKey, "string");
                    Element iText = createElement("text", page.getText(), "string");
                    
                    id.addContent(iName);
                    id.addContent(iGroup);
                    id.addContent(iSubGroup);
                    id.addContent(iText);
                }
            }
        }
        E.addContent(refmanualdata);
    }
    private void generateStoryXML(Element E) {
        engine.debug(1, "->generateStoryXML()");
        
        Element encounter = new Element("encounter");
        for (Map.Entry entry : StoriesByCategory.entrySet()) {
            String key = (String) entry.getKey();
            String[] val = (String[]) entry.getValue();
            Element eCat = createElement("category");
            eCat.setAttribute("name", key);
            eCat.setAttribute("mergeid", module.getID());
            eCat.setAttribute("baseicon", "2");
            eCat.setAttribute("decalicon", "1");
            
            encounter.addContent(eCat);
            
            for (String encname : val) {
                CoreRPG_Story enc = Stories.get(encname);
                
                if (enc != null) {
                    String sanatisedName = sanatise(enc.getName());
                    
                    Element e = createElement("enc_" + sanatisedName);
                    eCat.addContent(e);
                    
                    Element eName = createElement("name", enc.getName(), "string");
                    Element eText = createElement("text", enc.getText(), "formattedtext");
                    
                    e.addContent(eName);
                    e.addContent(eText);
                }   
            }   
        }
        E.addContent(encounter);
    }
    private void generateTableXML(Element E) {
        engine.debug(1, "->generateTableXML()");
        
        Element tables = new Element("tables");
        for (Map.Entry entry : TablesByCategory.entrySet()) {
            String key = (String) entry.getKey();
            String[] val = (String[]) entry.getValue();
            Element tCat = createElement("category");
            tCat.setAttribute("name", key);
            tCat.setAttribute("mergeid", module.getID());
            tCat.setAttribute("baseicon", "2");
            tCat.setAttribute("decalicon", "1");
            
            tables.addContent(tCat);
            
            for (String tablename : val) {
                CoreRPG_Table table = Tables.get(tablename);
                
                if (table != null) {
                    String sanatisedName = sanatise(table.getName());
                    
                    Element t = createElement("tab_" + sanatisedName);
                    tCat.addContent(t);
                    
                    Element tName = createElement("name", table.getName(), "string");
                    Element tDesc = createElement("description", table.getDescription(), "string");
                    Element tNotes = createElement("notes", table.getText(), "formattedtext");
                    
                    t.addContent(tName);
                    t.addContent(tDesc);
                    t.addContent(tNotes);
                    
                    int count = 1;
                    for (String tcolHeader : table.getColumnHeaders()) {
                        Element tlabelCol = createElement("labelcol"+count, tcolHeader, "string");
                        t.addContent(tlabelCol);
                        count++;
                    }
                    
                    Element tresultCols = createElement("resultcols", table.getResultColumns().toString(), "string");
                    t.addContent(tresultCols);
                    
                    Element tRows = createElement("tablerows");
                    t.addContent(tRows);
                    
                    count = 1;
                    for (CoreRPG_TableRow tableRow : table.getTableRows()) {
                        Element id = createElement(makeID(count));
                        tRows.addContent(id);
                        
                        Element fromRange = createElement("fromrange", tableRow.from.toString(), "number");
                        Element toRange = createElement("torange", tableRow.to.toString(), "number");
                        Element results = createElement("results");
                        
                        id.addContent(fromRange);
                        id.addContent(toRange);
                        id.addContent(results);
                        
                        int rescount = 1;
                        for (String result : tableRow.getResults()) {
                            Element rid = createElement(makeID(rescount));
                            results.addContent(rid);
                            
                            Element res = createElement("result", result, "string");
                            rid.addContent(res);
                                    
                            rescount++;
                        }
                        
                        count++;
                    }
                }   
            }   
        }
        E.addContent(tables);
        
    }
    private Element generateReferenceTag(Element E) {
        engine.debug(1, "->generateReferenceTag()");
        Element eName = new Element("reference" + module.getID());
        eName.setAttribute("static", "true");
        E.addContent(eName);
        return eName;       
    }
    
    //Private Reference list generation methods
    private void generateEquipmentXMLLists(Element E) {
        engine.debug(1, "->generateEquipmentXMLLists()");
    }
    private void generateMagicItemXMLLists(Element E) {
        engine.debug(1, "->generateMagicItemXMLLists()");
    }
    private void generateNPCXMLLists(Element E) {
        engine.debug(1, "->generateNPCXMLLists()");
    }
    private void generateReferenceManualIndex(Element E) {
        engine.debug(1, "->generateReferenceManualIndex()");    
        
        Integer chapterCount = -1, subchapterCount, pageCount;
        Element refmanualindex = createElement("refmanualindex");
        Element chapters = createElement("chapters");
        refmanualindex.addContent(chapters);
         
        for (Map.Entry e1 : ReferenceManual.getChapters().entrySet()) {
            String chapterKey = (String) e1.getKey();
            CoreRPG_ReferenceChapter chapterVal = (CoreRPG_ReferenceChapter) e1.getValue();
            chapterCount++;
            subchapterCount = -1;
            
            Element chapter = createElement("chapter_" + padID(chapterCount));
            chapters.addContent(chapter);
            
            Element chapName = createElement("name", chapterKey, "string");
            chapter.addContent(chapName);
            
            Element subchapters = createElement("subchapters");
            chapter.addContent(subchapters);
            
            for (Map.Entry e2 : chapterVal.getSubChapters().entrySet()) {
                String subchapterKey = (String) e2.getKey();
                CoreRPG_ReferenceSubChapter subchapterVal = (CoreRPG_ReferenceSubChapter) e2.getValue();
                subchapterCount++;
                pageCount = -1;
                
                Element subchapter = createElement("subchapter_" + padID(subchapterCount));
                subchapters.addContent(subchapter);

                Element subchapName = createElement("name", subchapterKey, "string");
                subchapter.addContent(subchapName);

                Element refpages = createElement("refpages");
                subchapter.addContent(refpages);
                
                for (Map.Entry e3 : subchapterVal.getPages().entrySet()) {
                    String pageKey = (String) e3.getKey();
                    CoreRPG_ReferencePage pageVal = (CoreRPG_ReferencePage) e3.getValue();
                    String sanatisedPageKey = sanatise(pageKey);
                    pageCount++;
                    
                    Element refpage = createElement("refpage_" + padID(pageCount));
                    refpages.addContent(refpage);
                   
                    Element iListLink = createElement("listlink", "", "windowreference");
                    refpage.addContent(iListLink);
                    
                    Element iClass = createElement("class", "reference_manualtextwide");
                    iListLink.addContent(iClass);

                    Element iRecordname = createElement("recordname", "reference" + module.getID() + ".refmanualdata.refpage_" + padID(chapterCount) + padID(subchapterCount) + padID(pageCount) + sanatisedPageKey + "@" + module.getName());
                    iListLink.addContent(iRecordname);

                    Element iLdesc = createElement("description");
                    iListLink.addContent(iLdesc);
                    Element iLfield = createElement("field", "name");
                    iLdesc.addContent(iLfield);
                    
                    Element iName = createElement("name", pageKey, "string");
                    Element iKeywords = createElement("keywords", pageVal.getKeywords() , "string");
                    
                    
                    refpage.addContent(iName);
                    refpage.addContent(iKeywords);
                }
            }
        }
        E.addContent(refmanualindex);
        
        ModuleLibraryEntry libEntry = new ModuleLibraryEntry("Reference Manual", "reference_manual", "reference" + module.getID() + ".refmanualindex");
        library.addEntry("Reference Manual", libEntry);  
    }    
    
    //Private Archive generation routines
    private void generateNPCArchive(ObjectContainer objects) {
        for (Map.Entry entry : NPCs.entrySet()) {
            final String key = (String) entry.getKey();
            CoreRPG_NPC val = (CoreRPG_NPC) entry.getValue();
            List<CoreRPG_NPC> npcs = objects.query(new Predicate<CoreRPG_NPC>() {
                @Override
                public boolean match(CoreRPG_NPC obj) {
                    return obj.getName().equals(key);
                }
            }); 
            if (npcs.isEmpty()) {
                objects.store(val);
            }
        }
    }
    private void generateMagicItemArchive(ObjectContainer objects) {
        for (Map.Entry entry : MagicItems.entrySet()) {
            final String key = (String) entry.getKey();
            CoreRPG_MagicItem val = (CoreRPG_MagicItem) entry.getValue();
            List<CoreRPG_MagicItem> mis = objects.query(new Predicate<CoreRPG_MagicItem>() {
                @Override
                public boolean match(CoreRPG_MagicItem obj) {
                    return obj.getName().equals(key);
                }
            }); 
            if (mis.isEmpty()) {
                objects.store(val);
            }
        }
    }

    
}

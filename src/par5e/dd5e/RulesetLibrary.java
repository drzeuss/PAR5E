/*
 * 
 * PAR5E Fantasy Grounds Ruleset Module Parser Tool
 * Developed by Zeus. Copyright 2014
 * 
 */


package par5e.dd5e;

import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import java.io.File;
import java.util.ArrayList;
import par5e.utils.RegEx;
import par5e.ModuleLibraryEntry;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jdom2.Element;


/**
 *
 * @author zeph
 */
public class RulesetLibrary extends par5e.corerpg.RulesetLibrary {
    
    //Data Stores
    public LinkedHashMap<String,DD5E_Background> Backgrounds;
    public LinkedHashMap<String,DD5E_Class> Classes;
    public LinkedHashMap<String,DD5E_Encounter> Encounters;
    public LinkedHashMap<String,DD5E_Equipment> Equipment;
    public LinkedHashMap<String,DD5E_Feat> Feats;
    public LinkedHashMap<String,DD5E_Image> Images;
    public LinkedHashMap<String,DD5E_ImagePins> ImagePins;
    public LinkedHashMap<String,DD5E_MagicItem> MagicItems;
    public LinkedHashMap<String,DD5E_NPC> NPCs;
    public LinkedHashMap<String,DD5E_Parcel> Parcels;
    public LinkedHashMap<String,DD5E_Race> Races;
    public DD5E_ReferenceManual ReferenceManual;
    public LinkedHashMap<String,DD5E_Skill> Skills;
    public LinkedHashMap<String,DD5E_Spell> Spells;
    public LinkedHashMap<String,DD5E_Story> Stories;
    public LinkedHashMap<String,DD5E_Table> Tables;
    public LinkedHashMap<String,DD5E_Token> Tokens;
//    public LinkedHashMap<String,DD5E_Trap> Traps;
    
    //Indexes
    public LinkedHashMap<String,String[]> BackgroundsByLetter;
    public LinkedHashMap<String,String[]> ClassesByLetter;
    public LinkedHashMap<String,String[]> EncountersByCategory;
    public LinkedHashMap<String,String[]> EquipmentByType;
    public LinkedHashMap<String,String> EquipmentSubTypesByType;
    public LinkedHashMap<String,String[]> MagicItemsByType;
    public LinkedHashMap<String,String> MagicItemSubTypesByType;
    public LinkedHashMap<String,String[]> FeatsByLetter;
    public LinkedHashMap<String,String[]> NPCsByLetter;
    public LinkedHashMap<Integer,String[]> NPCsByLevel;
    public LinkedHashMap<String,String[]> NPCsByType;
    public LinkedHashMap<String,String[]> ParcelsByCategory;
    public LinkedHashMap<String,String[]> RacesByLetter;
    public LinkedHashMap<String,String[]> SkillsByLetter;
    public LinkedHashMap<String,String[]> SpellsByClass;
    public LinkedHashMap<String,String[]> SpellLevelsByClass;
    public LinkedHashMap<String,String[]> StoriesByCategory;
    public LinkedHashMap<String,String[]> TablesByCategory;
//    public LinkedHashMap<String,String[]> TrapsByLetter;
//    public LinkedHashMap<Integer,String[]> TrapsByLevel;
//    public LinkedHashMap<String,String[]> TrapsByType;
    
    public List<String> sortedSpells;
    public List<String> sortedSpellLevels;
    
    private boolean campaignpersonalities = false;
    
    public RulesetLibrary() {  
        //Initialise data stores and indexes
        Backgrounds = new LinkedHashMap<>();
        BackgroundsByLetter = new LinkedHashMap<>();
        Classes = new LinkedHashMap<>();
        ClassesByLetter = new LinkedHashMap<>();
        Encounters = new LinkedHashMap<>();
        EncountersByCategory = new LinkedHashMap<>();
        Equipment = new LinkedHashMap<>();
        EquipmentByType = new LinkedHashMap<>();
        EquipmentSubTypesByType = new LinkedHashMap<>();
        Feats = new LinkedHashMap<>();
        FeatsByLetter = new LinkedHashMap<>();
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
        Races = new LinkedHashMap<>();
        RacesByLetter = new LinkedHashMap<>();
        Skills = new LinkedHashMap<>();
        SkillsByLetter = new LinkedHashMap<>();
        Spells = new LinkedHashMap<>();
        SpellsByClass = new LinkedHashMap<>();
        SpellLevelsByClass = new LinkedHashMap<>();
        Stories = new LinkedHashMap<>();
        StoriesByCategory = new LinkedHashMap<>();
        Tables = new LinkedHashMap<>();
        TablesByCategory = new LinkedHashMap<>();
        Tokens = new LinkedHashMap<>();
  //      Traps = new LinkedHashMap<>();
  //      TrapsByLetter = new LinkedHashMap<>();
  //      TrapsByLevel = new LinkedHashMap<>();
  //      TrapsByType = new LinkedHashMap<>();
        
        initialiseBS();
        buildDataItems();
        setName("5E");
        setRuleset("5E");

    }
    
    //Public methods
    @Override
    public void parseData(String aDataItemName, String[] aSourceData, ObjectContainer objects) {
        engine.debug(1, "->parseData()");
        switch(aDataItemName) {
            case "backgrounds": parseBackgroundData(aSourceData); break;
            case "class": parseClassData(aSourceData); break;
            case "encounters": parseEncounterData(aSourceData); break;
            case "equipment": parseEquipmentData(aSourceData); break;
            case "feats": parseFeatData(aSourceData); break;
            case "imagepins": parseImagePinData(aSourceData); break;
            case "images": parseImageData(aSourceData); break;
            case "magicitems": parseMagicItemData(aSourceData, objects); break;
            case "npcs": parseNPCData(aSourceData, objects); break;
            case "parcels": parseParcelData(aSourceData); break;
            case "races": parseRaceData(aSourceData); break;
            case "referencemanual": parseReferenceManualData(aSourceData); break;
            case "skills": parseSkillData(aSourceData); break;
            case "spells": parseSpellData(aSourceData); break;
            case "story": parseStoryData(aSourceData); break;
            case "tokens": parseTokenData(aSourceData); break;
            case "tables": parseTableData(aSourceData); break;
       //     case "traps": parseTrapData(aSourceData); break;
            default: { }; break;    
              
        }
    }
    @Override
    public void generateIndexes(String aDataItemName) {
         engine.debug(1, "->parsegenerateReferenceLists()");
         switch(aDataItemName) {
            case "backgrounds": generateBackgroundIndexes(aDataItemName); break;
            case "class": generateClassIndexes(aDataItemName); break;
            case "equipment": generateEquipmentIndexes(aDataItemName); break;
            case "feats": generateFeatIndexes(aDataItemName); break;
            case "magicitems": generateMagicItemIndexes(aDataItemName); break;
            case "npcs": generateNPCIndexes(aDataItemName); break;
            case "races": generateRaceIndexes(aDataItemName); break;
            case "skills": generateSkillIndexes(aDataItemName); break;
            case "spells": generateSpellIndexes(aDataItemName); break;
        //    case "traps": generateTrapIndexes(aDataItemName); break;
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
                    case "equipment": generateItemXML(E, true, false); break;
                    case "images": generateImageXML(E, false); break;
                    case "npcs": generatePersonalitiesXML(E); break; 
                    case "magicitems": generateItemXML(E, false, true); break;
                    case "parcels": generateParcelXML(E); break;
                    case "story": generateStoryXML(E); break;
                    case "tables": generateTableXML(E); break;
                    default: {}; break;
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
                    case "backgrounds": generateBackgroundXML(refTag); generateBackgroundXMLLists(refTag); break;
                    case "class": generateClassXML(refTag); generateClassXMLLists(refTag); break;
                    case "equipment": generateEquipmentXML(refTag, null); generateEquipmentXMLLists(refTag); break;
                    case "feats": generateFeatXML(refTag); generateFeatXMLLists(refTag); break;
                    case "images": generateImageXML(refTag, true); break;
                    case "magicitems": generateMagicItemXML(refTag, true); generateMagicItemXMLLists(refTag); break;
                    case "npcs": generateNPCXML(refTag, true); generateNPCXMLLists(refTag); break;
                    case "races": generateRaceXML(refTag); generateRaceXMLLists(refTag); break;
                    case "referencemanual": generateReferenceManualXML(refTag); generateReferenceManualIndex(refTag); break;
                    case "skills": generateSkillXML(refTag); generateSkillXMLLists(refTag); break;
                    case "spells": generateSpellXML(refTag); generateSpellXMLLists(refTag); break;
            //        case "traps": generateTrapXML(refTag, true); generateTrapXMLLists(refTag); break;
                    default: {}; break;
                }
                con.printMsg("Make  : " + diName + " (reference)");
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
        List<DD5E_NPC> npcs = objects.query(new Predicate<DD5E_NPC>() {
                @Override
                public boolean match(DD5E_NPC obj) {
                return !obj.getName().equals("   ");
            }
            }); 
        List<DD5E_MagicItem> mis = objects.query(new Predicate<DD5E_MagicItem>() {
            @Override
            public boolean match(DD5E_MagicItem obj) {
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
        String[] Linkdata = new String[4];
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
//                    default: {
  //                      regex = new RegEx("reference(.*)", sType);
    //                    if (regex.find()) {
      //                      Class = "reference_" +  regex.group(1);
        //                } else {
          //                  Class = "reference_" + sType;
            //            }
              //      }        
		} 
        }     
        Linkdata[0] = Class;
        Linkdata[1] = Recordname;
        Linkdata[2] = sName;
        
        if (!sTitle.isEmpty()) {
            Linkdata[3] = sTitle;
        } else {
            Linkdata[3] = sName;
        }
        
        return Linkdata;
    }
    
    public static<T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
        List<T> list = new ArrayList<>(c);
        java.util.Collections.sort(list);
        return list;
    }

    //Private methods
    private void buildDataItems() {
        LinkedHashMap<String, BitSet> data = new LinkedHashMap<>();
        
        data.put("backgrounds", rbs);
        data.put("class", rbs);
        data.put("encounters", cbs);
        data.put("equipment", crbs);
        data.put("feats", rbs);
        data.put("images", crbs);
        data.put("imagepins", crbs);
        data.put("magicitems", crdbs);
        data.put("npcs", crdbs);
        data.put("parcels", cbs);
        data.put("races", rbs);
        data.put("referencemanual", rbs);
        data.put("skills", rbs);
        data.put("spells", rbs);
        data.put("story", cbs);
        data.put("tables", cbs);
     //   data.put("traps", rbs); 
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
    
    //Private parse methods
    private void parseBackgroundData(String[] aSourceData) {
        engine.debug(1, "->parseBackgroundData()");
        
        //Variables
	String sName = "", sTraitName = "", sProficiencyName = "", sProficiencyText, sTLinks = "";
	boolean bDescriptionBlock = false, bTraitBlock = false, bProficiencyBlock = false, bLanguagesBlock = false, bEquipmentBlock = false;
        DD5E_Background background = null;
        
        for (String line : aSourceData) {
            
            String sLine = line;
            //System.out.println(line);
            //Empty or blank lines, skip
            RegEx emptyline = new RegEx("\\A$",sLine);
            RegEx whitespaceline = new RegEx("\\A\\s+$",sLine);
            if (emptyline.find() || whitespaceline.find()) {              
                engine.debug(2,"Empty Line");
                continue;
            } 
            
            //New background
            RegEx regex = new RegEx("\\A\\#\\#\\;(.*)",sLine);
            if (regex.find()) {
                if (background != null && background.getName().equals(sName) ) {
                    sTLinks = sTLinks + makeFormattedText("#zle;");
                    
                    background.setText(background.getText() + makeFormattedText("#h;Traits"));
                    background.setText(background.getText() + sTLinks);
                    if (!background.getProficiencies().isEmpty()) {
                        background.setText(background.getText() + makeFormattedText("#h;Proficiencies"));
                        for (Map.Entry entry : background.getProficiencies().entrySet()) {
                            String key = (String)entry.getKey();
                            String val = (String)entry.getValue();  
                            
                            background.setText(background.getText() + makeFormattedText("#bp;" + key + ": " + val));
                        }
                    }
                    
                    if (background.languages != null) {
                        background.setText(background.getText() + makeFormattedText("#h;Languages"));
                        background.setText(background.getText() + makeFormattedText(background.getLanguages()));
                    }
                    
                    if (!background.getEquipment().equals("")) {
                        background.setText(background.getText() + makeFormattedText("#h;Equipment"));
                        background.setText(background.getText() + makeFormattedText(background.getEquipment()));
                    }
                    
                    if (background.isComplete()) {
                        con.printMsg("Parse : background ");
                        con.printMsgResult(background.getName(), normal);
                        engine.debug(2,"Parse Completed - " + background.getName());
                    } else {    
                        con.printMsg("Parse : background ");
                        con.printMsgResult(background.getName(), warning);
                        engine.debug(2,"Parse Incomplete - " + background.getName());
                    }      
                    Backgrounds.put(background.getName(), background);
                }
                
                sName = trimTxt(regex.group(1));
                background = new DD5E_Background(sName);
                bDescriptionBlock = true;
                bProficiencyBlock = false;
                bTraitBlock = false;
                bLanguagesBlock = false;
                bEquipmentBlock = false;
                sTLinks = makeFormattedText("#zls;");
                engine.debug(2, "Background " + sName);
                continue;        
            }
            //Traits
            regex = new RegEx("\\ATrait\\s[\\-|\"]\\s(.*)$",sLine);
            if (regex.find()) {               
                sTraitName = trimTxt(regex.group(1));
                bDescriptionBlock = false;
                bProficiencyBlock = false;
                bTraitBlock = true;
                bLanguagesBlock = false;
                bEquipmentBlock = false;
                if (background != null) {
                    LinkedHashMap<String,String> traits;
                    if (background.traits != null ){
                        traits = background.getTraits();
                        traits.put(sTraitName, "");
                    } else {
                        traits = new LinkedHashMap<>();
                        traits.put(sTraitName, "");
                    }
                    background.setTraits(traits);
                }
                sTLinks = sTLinks + makeFormattedText("#zl;" + sName.toLowerCase() + "_backgroundtrait;" + sTraitName); 
                engine.debug(2, "Background Trait - " + sTraitName);
                continue;
            }
            //Proficiencies
            regex = new RegEx("\\AProficiencies\\s*$",sLine);
            if (regex.find()) {
                bDescriptionBlock = false;
                bProficiencyBlock = true;
                bTraitBlock = false;
                bLanguagesBlock = false;
                bEquipmentBlock = false;
                engine.debug(2,"Proficiencies Block");
                continue;
            }
            //Languages
            regex = new RegEx("\\ALanguages.*",sLine);
            if (regex.find()) {
                bDescriptionBlock = false;
                bProficiencyBlock = false;
                bTraitBlock = false;
                bLanguagesBlock = true;
                bEquipmentBlock = false;
                engine.debug(2,"Languages Block");
                continue;
            }
            //Equipment
            regex = new RegEx("\\AEquipment\\s*$",sLine);
            if (regex.find()) {
                bDescriptionBlock = false;
                bProficiencyBlock = false;
                bTraitBlock = false;
                bLanguagesBlock = false;
                bEquipmentBlock = true;
                engine.debug(2,"Equipment Block");
                continue;
            }
            
            //Process Line
            if (bDescriptionBlock && background != null) {
                background.setText(background.getText() + makeFormattedText(sLine));
                engine.debug(2,"Background Description Line");
            } else if (bTraitBlock && background != null && background.getTraits().containsKey(sTraitName)) {
                String text = background.getTraits().get(sTraitName).toString();
                text = text + makeFormattedText(sLine);
                background.getTraits().put(sTraitName, text);
                engine.debug(2,"Background Trait Description Line");
            } else if (bProficiencyBlock && background != null) {
                regex = new RegEx("\\A(.*)\\:(.*)$", sLine);
                if (regex.find()) {
                    sProficiencyName = trimTxt(regex.group(1));
                    sProficiencyText = trimTxt(regex.group(2));
                    LinkedHashMap<String,String> proficiencies;
                    
                    if (background.proficiencies != null ){
                        proficiencies = background.getProficiencies();
                        proficiencies.put(sProficiencyName, sProficiencyText);
                    } else {
                        proficiencies = new LinkedHashMap<>();
                        proficiencies.put(sProficiencyName, sProficiencyText);
                    }
                    background.setProficiencies(proficiencies);
                    engine.debug(2, "Background Proficiency - " + sProficiencyName);
                } else if (background.getProficiencies().containsKey(sProficiencyName)) {
                    String text = background.getProficiencies().get(sProficiencyName).toString();
                    text = text + sLine;
                    background.getProficiencies().put(sProficiencyName, text);
                    engine.debug(2,"Background Proficiency Description Line");
                }
            } else if (bLanguagesBlock && background != null) {
                background.setLanguages(trimTxt(sLine));
                engine.debug(2,"Background Language Line");
            } else if (bEquipmentBlock && background != null) {
                background.setEquipment(trimTxt(sLine));
                engine.debug(2,"Background Equipment Line");
            }
        }
        sTLinks = sTLinks + makeFormattedText("#zle;"); 
        if (background != null) {
            background.setText(background.getText() + makeFormattedText("#h;Traits"));
            background.setText(background.getText() + sTLinks);
            if (!background.getProficiencies().isEmpty()) {
                background.setText(background.getText() + makeFormattedText("#h;Proficiencies"));
                for (Map.Entry entry : background.getProficiencies().entrySet()) {
                    String key = (String)entry.getKey();
                    String val = (String)entry.getValue();
                    
                    background.setText(background.getText() + makeFormattedText("#bp;" + key + ": " + val));
                }
            }
            
            if (!background.getLanguages().isEmpty()) {
                background.setText(background.getText() + makeFormattedText("#h;Languages"));	
                background.setText(background.getText() + makeFormattedText(background.getLanguages()));
            }
            
            if (!background.getEquipment().equals("")) {
                background.setText(background.getText() + makeFormattedText("#h;Equipment"));
                background.setText(background.getText() + makeFormattedText(background.getEquipment()));
            }
            Backgrounds.put(background.getName(), background);
            engine.debug(2,"Parse Complete - " + background.getName());
         }       
    } 
    private void parseClassData(String[] aSourceData) {
        engine.debug(1, "->parseClassData()");
        
        //Variables
        String sName = "", sFName = "", sAName = "", sProficiencyName = "", sHPName = "", sAbilitiesName = "", sAFName = "";
	String sText = "", sProficiencyText = "", sHPText = "", sHPLinks = "", sPLinks = "", sFLinks = "", sALinks = "", sAFLinks = "", sAText;
	boolean bDescriptionBlock = false;
	boolean bHPBlock = false;
	boolean bProficienciesBlock = false;
	boolean bFeaturesBlock = false;
	boolean bAbilitiesBlock = false;
        boolean bAbilitiesFeaturesBlock = false;
	boolean bAbilitiesHeaderBlock = false;
        DD5E_Class pcclass = null;
        
        //Loop through each line
        for (String sLine : aSourceData) {
            
            //Check for empty line and skip
            RegEx emptyline = new RegEx("\\A$",sLine);
            RegEx whitespaceline = new RegEx("\\A\\s+$",sLine);
            if (emptyline.find() || whitespaceline.find()) {
                engine.debug(2,"Empty Line");
                continue;
            }
            
            //New Class
            RegEx regex = new RegEx("\\A\\#\\#\\;(.*)",sLine);
            if (regex.find()) {
                if (pcclass != null && pcclass.getName().equals(sName)) {
                    sHPLinks += makeFormattedText("#zle;");
                    sPLinks += makeFormattedText("#zle;");
                    sFLinks += makeFormattedText("#zle;");
                    sALinks += makeFormattedText("#zle;");
                    
                    pcclass.setText(pcclass.getText() + "<h>Hit Points</h>");
                    pcclass.setText(pcclass.getText() + sHPLinks);
                    pcclass.setText(pcclass.getText() + "<h>Proficiencies</h>");
                    pcclass.setText(pcclass.getText() + sPLinks);
                    pcclass.setText(pcclass.getText() + "<h>Features</h>");
                    pcclass.setText(pcclass.getText() + sFLinks);
                    pcclass.setText(pcclass.getText() + "<h>" + sAbilitiesName + "</h>");
                    pcclass.setText(pcclass.getText() + sALinks);
                    
                    if (pcclass.getAbilities().containsKey(sAName)) {
                        sAFLinks += makeFormattedText("#zle;");
                        pcclass.getAbilities().get(sAName).setText(pcclass.getAbilities().get(sAName).getText() + "<h>Features</h>");
                        pcclass.getAbilities().get(sAName).setText(pcclass.getAbilities().get(sAName).getText() + sAFLinks);
                    }
                    
                    if (pcclass.isComplete()) {
                        con.printMsg("Parse : class ");
                        con.printMsgResult(pcclass.getName(), normal);
                        engine.debug(2,"Parse Completed - " + pcclass.getName());
                    } else {    
                        con.printMsg("Parse : class ");
                        con.printMsgResult(pcclass.getName(), warning);
                        engine.debug(2,"Parse Incomplete - " + pcclass.getName());
                    }                         
                    Classes.put(pcclass.getName(), pcclass);
                }
                
                sName = trimTxt(regex.group(1));
                pcclass = new DD5E_Class(sName);
                bDescriptionBlock = true;
                bHPBlock = false;
                bProficienciesBlock = false;
                bFeaturesBlock = false;
                bAbilitiesBlock = false;
                bAbilitiesHeaderBlock = false;
                bAbilitiesFeaturesBlock = false;
                sHPLinks = makeFormattedText("#zls;");
                sPLinks = makeFormattedText("#zls;");
                sFLinks = makeFormattedText("#zls;");
                sALinks = makeFormattedText("#zls;");
                engine.debug(2, "Class - " + sName);
                continue;
            }
            
            //Class Hit Points
            regex = new RegEx("\\AHit\\s+Points\\s*$",sLine);
            if (regex.find()) {
                bDescriptionBlock = false;
                bHPBlock = true;
                bProficienciesBlock = false;
                bFeaturesBlock = false;
                bAbilitiesBlock = false;
                bAbilitiesHeaderBlock = false;
                bAbilitiesFeaturesBlock = false;
                engine.debug(2,"Class Hit Points Block");
                continue;
            }
            
            //Class Proficiencies
            regex = new RegEx("\\AProficiencies\\s*$",sLine);
            if (regex.find()) {
                bDescriptionBlock = false;
                bHPBlock = false;
                bProficienciesBlock = true;
                bFeaturesBlock = false;
                bAbilitiesBlock = false;
                bAbilitiesHeaderBlock = false; 
                bAbilitiesFeaturesBlock = false;
                engine.debug(2,"Class Proficiencies Block");
                continue;
            }
            
            //Class Features
            regex = new RegEx("\\A\\#fe\\;(.*)",sLine);
            if (regex.find() && pcclass != null) {
                
                RegEx r = new RegEx("(.*)\\;(.*)", regex.group(1));
                if (r.find()) {
                    Integer count = 1;
                    String[] levels = r.group(2).split(",");
                    
                    for (String level : levels) {
                        String cnt;
                        if (count == 1 ) {
                            cnt = "";
                        } else {
                            cnt = count.toString();
                        }
                            
                        sFName = trimTxt(r.group(1)) + cnt;
                        sFLinks += makeFormattedText("#zl;" + sName.toLowerCase() + "_feature;" + sFName + ";" + trimTxt(r.group(1)));
                        count++;
                        
                        LinkedHashMap<String,DD5E_ClassFeature> features;
                        if (pcclass.getFeatures() != null) {
                            features = pcclass.getFeatures();
                        } else {
                            features = new LinkedHashMap<>();
                        }  

                        DD5E_ClassFeature cf = new DD5E_ClassFeature(sFName);
                        cf.setLevel(level);
                        features.put(sFName, cf);
                        pcclass.setFeatures(features);
                    }
               } else {
                    sFName = trimTxt(regex.group(1));
                    sFLinks += makeFormattedText("#zl;" + sName.toLowerCase() + "_feature;" + sFName );

                    LinkedHashMap<String,DD5E_ClassFeature> features;
                    if (pcclass.getFeatures() != null) {
                        features = pcclass.getFeatures();
                    } else {
                        features = new LinkedHashMap<>();
                    }  

                    DD5E_ClassFeature cf = new DD5E_ClassFeature(sFName);
                    features.put(sFName, cf);
                    pcclass.setFeatures(features);
                }
               
                bDescriptionBlock = false;
                bHPBlock = false;
                bProficienciesBlock = false;
                bFeaturesBlock = true;
                bAbilitiesBlock = false;
                bAbilitiesHeaderBlock = false;  
                bAbilitiesFeaturesBlock = false;
                engine.debug(2,"Feature - " + sFName);
                continue;
            }
            
            //Class Abilities Header
            regex = new RegEx("\\A\\#abh\\;(.*)",sLine);
            if (regex.find()) {
                sAbilitiesName = trimTxt(regex.group(1));
                bDescriptionBlock = false;
                bHPBlock = false;
                bProficienciesBlock = false;
                bFeaturesBlock = false;
                bAbilitiesBlock = false;
                bAbilitiesHeaderBlock = true;
                bAbilitiesFeaturesBlock = false;
                sAText = "";
                engine.debug(2,"Ability Header - " + sAbilitiesName);
                continue;
            }
            
            //Class Abilities 
            regex = new RegEx("\\A\\#ab\\;(.*)",sLine);
            if (regex.find() && pcclass != null) {
                
                if (pcclass.getAbilities().containsKey(sAName)) {
                    sAFLinks += makeFormattedText("#zle;");
                    pcclass.getAbilities().get(sAName).setText(pcclass.getAbilities().get(sAName).getText() + "<h>Features</h>");
                    pcclass.getAbilities().get(sAName).setText(pcclass.getAbilities().get(sAName).getText() + sAFLinks);
                }
                
                sAName = trimTxt(regex.group(1));
                sAName = sAName.replace(":", " -");
                sALinks += makeFormattedText("#zl;" + sName.toLowerCase() + "_ability;" + sAName );  
                
                sAFLinks = makeFormattedText("#zls;");
                
                LinkedHashMap<String,DD5E_ClassAbility> abilities; 
                if (pcclass.getAbilities() != null) {
                    abilities = pcclass.getAbilities();
                } else {
                    abilities = new LinkedHashMap<>();
                }
                DD5E_ClassAbility ca = new DD5E_ClassAbility(sAName);
                abilities.put(sAName, ca);
                pcclass.setAbilities(abilities);
        
                bDescriptionBlock = false;
                bHPBlock = false;
                bProficienciesBlock = false;
                bFeaturesBlock = false;
                bAbilitiesBlock = true;
                bAbilitiesHeaderBlock = false;
                bAbilitiesFeaturesBlock = false;
                engine.debug(2,"Ability - " + sAName);
                continue;
            }
            
            //Class Ability Features
            regex = new RegEx("\\A\\#abf\\;(.*)",sLine);
            if (regex.find() && pcclass != null) {
                RegEx r = new RegEx("(.*)\\;(.*)", regex.group(1));
                if (r.find()) {
                    Integer count = 1;
                    String[] levels = r.group(2).split(",");
                
                     for (String level : levels) {
                        String cnt;
                        if (count == 1 ) {
                            cnt = "";
                        } else {
                            cnt = count.toString();
                        }
                        
                        sAFName = trimTxt(r.group(1)) + cnt;
                        sAFLinks += makeFormattedText("#zl;" + sName.toLowerCase() + "_feature;" + sAFName + ";" + trimTxt(r.group(1)));
                        count++;
                        
                        LinkedHashMap<String,DD5E_ClassFeature> features;
                        if (pcclass.getFeatures() != null) {
                            features = pcclass.getFeatures();
                        } else {
                            features = new LinkedHashMap<>();
                        }   
                        DD5E_ClassFeature cf = new DD5E_ClassFeature(sAFName);
                        cf.setSpecialization(sAName);
                        cf.setLevel(level);
                        features.put(sAFName, cf);
                        pcclass.setFeatures(features);
                    }
                    
                } else {
                    
                    sAFName = trimTxt(regex.group(1));
                    sAFLinks += makeFormattedText("#zl;" + sName.toLowerCase() + "_feature;" + sAFName );
                
                    LinkedHashMap<String,DD5E_ClassFeature> features;
                    if (pcclass.getFeatures() != null) {
                        features = pcclass.getFeatures();
                    } else {
                        features = new LinkedHashMap<>();
                    }    
                    DD5E_ClassFeature cf = new DD5E_ClassFeature(sAFName);
                    cf.setSpecialization(sAName);
                    features.put(sAFName, cf);
                    pcclass.setFeatures(features);
                }
                
                bDescriptionBlock = false;
                bHPBlock = false;
                bProficienciesBlock = false;
                bFeaturesBlock = false;
                bAbilitiesBlock = false;
                bAbilitiesHeaderBlock = false;
                bAbilitiesFeaturesBlock = true;
                engine.debug(2,"Ability Feature - " + sFName);
                continue;
            }
            
            
            //Class Description
            if (bDescriptionBlock && pcclass != null ) {
                pcclass.setText(pcclass.getText() + makeFormattedText(sLine));
                engine.debug(2,"Class Description Line");
                
                //Backgrounds
                regex = new RegEx("\\ABackground\\:\\s+(.*)", sLine);
                if (regex.find()) {
                    if (regex.group(1).contains(",")) {
                        String[] backgrounds = regex.group(1).split(",");
                        for (String b : backgrounds) {
                            pcclass.addBackground(b);
                        }
                    } else {
                        pcclass.addBackground(regex.group(1));
                    }
                }
                
                //Equipment
                regex = new RegEx("\\AEquipment\\s\\-\\s(.*)\\:(.*)", sLine);
                if (regex.find()) {
                    String tag = trimTxt(regex.group(1));
                    String val = trimTxt(regex.group(2));
                    pcclass.addEquipment(tag, val);
                }
                
                //Equipment
                regex = new RegEx("\\AEquipment\\:\\s(.*)", sLine);
                if (regex.find()) {                      
                    pcclass.addEquipment("Standard", trimTxt(regex.group(1)));        
                }
                
                
            } else if (bHPBlock && pcclass != null) {
                //Class HP Block
                regex = new RegEx("\\A(.+):(.+)$", sLine);
                if (regex.find()) {
                    sHPName = trimTxt(regex.group(1));
                    sHPText = trimTxt(regex.group(2));
                    sHPLinks += makeFormattedText("#zl;" + sName.toLowerCase() + "_classhp;" + sHPName );              
                
                    LinkedHashMap<String,String> hp;
                    if (pcclass.getHP() != null) {
                        hp = pcclass.getHP();
                    } else {
                        hp = new LinkedHashMap<>();
                    }    
                    hp.put(sHPName, makeFormattedText(sHPText));
                    pcclass.setHP(hp);
                } else if (pcclass.getHP().containsKey(sHPName)) {
                    String text = pcclass.getHP().get(sHPName).toString();
                    text = text + makeFormattedText(sHPText);
                    pcclass.getHP().put(sHPName, text);
                }
                engine.debug(2,"Class HP Line");
            } else if (bProficienciesBlock && pcclass != null) {
                //Class Proficiencies Block
                regex = new RegEx("\\A(.+):(.+)$", sLine);
                if (regex.find()) {
                    sProficiencyName = trimTxt(regex.group(1));
                    sProficiencyText = trimTxt(regex.group(2));
                    sPLinks += makeFormattedText("#zl;" + sName.toLowerCase() + "_classproficiency;" + sProficiencyName );              
                
                    LinkedHashMap<String,String> proficiencies;
                    if (pcclass.getProficiencies() != null) {
                        proficiencies = pcclass.getProficiencies();
                    } else {
                        proficiencies = new LinkedHashMap<>();
                    }    
                    String text = makeFormattedText(sProficiencyText);
                    proficiencies.put(sProficiencyName, text);
                    pcclass.setProficiencies(proficiencies);
                } else if (pcclass.getProficiencies().containsKey(sProficiencyName)) {
                    String text = pcclass.getProficiencies().get(sProficiencyName).toString();
                    text = text + makeFormattedText(sProficiencyText);
                    pcclass.getProficiencies().put(sProficiencyName, text);
                }
                engine.debug(2,"Class Proficency Line");
            } else if (bFeaturesBlock && pcclass != null) {
                //Class Features Block
                if (pcclass.getFeatures().containsKey(sFName)) {
                   DD5E_ClassFeature cf = pcclass.getFeatures().get(sFName);
                   cf.setText(cf.getText() + makeFormattedText(sLine));
                   regex = new RegEx("(\\d+)[s|n|r|t][t|d|h]", sLine);
                   if (regex.find()) {
                      cf.setLevel(regex.group(1));
                   }
                   pcclass.getFeatures().put(sFName, cf); 
                }
                engine.debug(2,"Class Feature Line");
            } else if (bAbilitiesHeaderBlock && pcclass != null) {
                //Class Abilities Hearder Block
                sAText = makeFormattedText(sLine);
            } else if (bAbilitiesBlock && pcclass != null) {
                //Class Abilities
                if (pcclass.getAbilities().containsKey(sAName)) {
                   DD5E_ClassAbility ca = pcclass.getAbilities().get(sAName);
                   ca.setText(ca.getText() + makeFormattedText(sLine));
                   regex = new RegEx("(\\d+)[s|n|r|t][t|d|h]", sLine);
                   if (regex.find()) {
                       ca.setLevel(regex.group(1));
                   }
                   pcclass.getAbilities().put(sAName, ca); 
                }
                engine.debug(2,"Class Abilities Line");
            } else if (bAbilitiesFeaturesBlock && pcclass != null) {
                //Class Abilities Features Block
                if (pcclass.getFeatures().containsKey(sAFName)) {
                   DD5E_ClassFeature cf = pcclass.getFeatures().get(sAFName);
                   cf.setText(cf.getText() + makeFormattedText(sLine));
                   regex = new RegEx("(\\d+)[s|n|r|t][t|d|h]", sLine);
                   if (regex.find()) {
                       cf.setLevel(regex.group(1));
                   }
                   pcclass.getFeatures().put(sAFName, cf); 
                }
                engine.debug(2,"Class Ability Feature Line");
            }  
        }
        
        //Close and store the final Class
        sHPLinks += makeFormattedText("#zle;");
        sPLinks += makeFormattedText("#zle;");
        sFLinks += makeFormattedText("#zle;");
        sALinks += makeFormattedText("#zle;");

        if (pcclass != null) {
            pcclass.setText(pcclass.getText() + "<h>Hit Points</h>");
            pcclass.setText(pcclass.getText() + sHPLinks);
            pcclass.setText(pcclass.getText() + "<h>Proficiencies</h>");
            pcclass.setText(pcclass.getText() + sPLinks);
            pcclass.setText(pcclass.getText() + "<h>Features</h>");
            pcclass.setText(pcclass.getText() + sFLinks);
            pcclass.setText(pcclass.getText() + "<h>Abilities</h>");
            pcclass.setText(pcclass.getText() + sALinks);
            
            if (pcclass.getAbilities().containsKey(sAName)) {
                sAFLinks += makeFormattedText("#zle;");
                pcclass.getAbilities().get(sAName).setText(pcclass.getAbilities().get(sAName).getText() + "<h>Features</h>");
                pcclass.getAbilities().get(sAName).setText(pcclass.getAbilities().get(sAName).getText() + sAFLinks);
            }

            if (pcclass.isComplete()) {
                con.printMsg("Parse : class ");
                con.printMsgResult(pcclass.getName(), normal);
                engine.debug(2,"Parse Completed - " + pcclass.getName());
            } else {    
                con.printMsg("Parse : class ");
                con.printMsgResult(pcclass.getName(), warning);
                engine.debug(2,"Parse Incomplete - " + pcclass.getName());
            }      
            Classes.put(pcclass.getName(), pcclass);
        }
    } 
    private void parseEncounterData(String[] aSourceData) {
        engine.debug(1, "->parseEncounterData()");
        //Variables
        String sName = "", sCategory = "";
	DD5E_Encounter encounter = null;
        
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
                encounter = new DD5E_Encounter(sName);
                encounter.setCategory(sCategory);
                engine.debug(2,"Encounter - " + sName);
                continue;  
            }
            
            //Encounter Level/XP 
            regex = new RegEx("\\ALevel\\s*(\\d+)\\s*XP\\s*(\\d+)",sLine);
            if (regex.find() && encounter != null) {
                Integer level = Integer.parseInt(trimTxt(regex.group(1)));
                encounter.setLevel(level);
                Integer xp = Integer.parseInt(trimTxt(regex.group(2)));
                encounter.setXP(xp);
                engine.debug(2,"Level/XP Line");
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
        DD5E_Equipment equipment = null;
        
        //Loop through each line of source data
        for (String sLine : aSourceData) {
            //Check for empty line and skip
            RegEx emptyline = new RegEx("\\A$",sLine);
            RegEx whitespaceline = new RegEx("\\A\\s+$",sLine);
            if (emptyline.find() || whitespaceline.find()) {
                engine.debug(2,"Empty Line");
                continue;
            }
            
            //New Equipment Type
            RegEx regex = new RegEx("\\#\\@\\;(.*)$", sLine);
            if (regex.find() ) {
                sType = trimTxt(regex.group(1));
                sSubType = "";
                engine.debug(2,"Type" + sType);
                continue;
            }
            
            //Check for Table Header row and skip
            regex = new RegEx("\\A\\#th\\;(.*)", sLine);
            if (regex.find() ) {
                engine.debug(2,"Table Header");
                continue;
            }
            
            //Equipment Subtype
            regex = new RegEx("\\A\\#st\\;(.*)", sLine);
            if (regex.find() ) {
                sSubType = regex.group(1);
                engine.debug(2,"Subtype - " + sSubType);
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
                            String niceName = equipment.getType().toLowerCase();
                            con.printMsg("Parse : equipment (" + niceName + ")");
                            con.printMsgResult(equipment.getName(), normal);
                            engine.debug(2,"Parse Completed - " + equipment.getName());
                        } else {
                            String niceName = makeProperCase(equipment.getType());
                            con.printMsg("Parse : equipment (" + niceName + ")");
                            con.printMsgResult(equipment.getName(), warning);
                            engine.debug(2,"Parse Incomplete - " + equipment.getName());
                        }
                        Equipment.put(equipment.getName(), equipment);
                    }
                
                    equipment = new DD5E_Equipment();
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
                    equipment.setType(sType);
                    equipment.setSubType(sSubType);
                    equipment.setCost(trimTxt(row[1]));

                    if (numfields == 3 && sType.toLowerCase().equals("adventuring gear")) {
                        
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

                    } else if (numfields == 6 && sType.toLowerCase().equals("armor")) {
                        r = new RegEx("\\A\\+?(\\d+).*", row[2]); 
                        if (r.find()) {
                            Integer i = 0;
                            try {
                                i = Integer.parseInt(trimTxt(r.group(1)));
                            } catch(IllegalStateException e) { }
                            equipment.setAC(i);
                        } else {
                            equipment.setAC(0);
                        }
                        
                        r = new RegEx("\\+\\sDex\\smodifier(.*)", row[2]); 
                        String dexbonus = "-";
                        if (r.find()) {
                            dexbonus = "Yes " + r.group(1);
                        }
                        dexbonus = trimTxt(dexbonus);
                        equipment.setDexBonus(dexbonus);
                        
                        r = new RegEx("\\A([\\–\\-]?\\d).*", row[3]);
                        if (r.find()) {
                            String s = r.group(1);
                            
                            if (s.contains("–")) {
                                s = s.replace("–", "-");
                            }
                            
                            Integer i = 0;
                            try {
                                i = Integer.parseInt(trimTxt(s));
                            } catch(IllegalStateException | NumberFormatException e ) { } 
                            equipment.setSpeed(i);
                        } else {
                            equipment.setSpeed(0);
                        }
                        equipment.setStealth(trimTxt(row[4]));
                        
                        r = new RegEx("\\A(\\d+).*", row[5]); 
                        if (r.find()) {
                            Integer i = 0;
                            try {
                                i = Integer.parseInt(trimTxt(r.group(1)));
                            } catch(IllegalStateException e) { }
                            equipment.setWeight(i);
                        } else {
                            equipment.setWeight(0);
                        }                  
                    } else if (numfields == 5 && sType.toLowerCase().equals("weapon")) {
                        equipment.setDamage(trimTxt(row[2]));
                        r = new RegEx("\\A(\\d+).*", row[3]); 
                        if (r.find()) {
                            Integer i = 0;
                            try {
                                i = Integer.parseInt(trimTxt(r.group(1)));
                            } catch(IllegalStateException e) { }
                            equipment.setWeight(i);
                        } else {
                            equipment.setWeight(0);
                        }
                        equipment.setProperties(trimTxt(row[4]));
                    }       
                } 
                continue;
            }
            
            //Equipment Description Row
            regex = new RegEx("\\A(.*)\\:(.*)", sLine);
            if (regex.find() ) {
                String eName = trimTxt(regex.group(1));
                if (Equipment.containsKey(eName)) {
                    String text = makeFormattedText(regex.group(2));
                    DD5E_Equipment equip = Equipment.get(eName);
                    equip.setText(text);
                    Equipment.put(eName, equip);
                }
            
            }    
        }
        
        if (equipment != null && equipment.getName().equals(sName)) {
            if (equipment.isComplete()) {
                String niceName = makeProperCase(equipment.getType());
                con.printMsg("Parse : equipment (" + niceName + ")");
                con.printMsgResult(equipment.getName(), normal);
                engine.debug(2,"Parse Completed - " + equipment.getName());
            } else {
                String niceName = makeProperCase(equipment.getType());
                con.printMsg("Parse : equipment (" + niceName + ")");
                con.printMsgResult(equipment.getName(), warning);
                engine.debug(2,"Parse Incomplete - " + equipment.getName());
            }
            Equipment.put(equipment.getName(), equipment);
        }   
    }
    private void parseFeatData(String[] aSourceData) {
        engine.debug(1, "->parseFeatData()");
        
        String sName, sText;
        DD5E_Feat feat = null;
        
        //Loop through each line of source data
        for (String sLine : aSourceData) {
            //Check for empty line and skip
            RegEx emptyline = new RegEx("\\A$",sLine);
            RegEx whitespaceline = new RegEx("\\A\\s+$",sLine);
            if (emptyline.find() || whitespaceline.find()) {
                engine.debug(2,"Empty Line");
                continue;
            }
            
            //New Equipment Type
            RegEx regex = new RegEx("\\#\\#\\;(.*)$", sLine);
            if (regex.find() ) {
                
                if (feat != null ) {
                    if (feat.isComplete()) {
                        con.printMsg("Parse : feat");
                        con.printMsgResult(feat.getName(), normal);
                        engine.debug(2, "Parse Completed - " + feat.getName());
                    } else {
                        con.printMsg("Parse : feat");
                        con.printMsgResult(feat.getName(), warning);
                        engine.debug(2, "Parse Incomplete - " + feat.getName());
                    }
                    Feats.put(feat.getName(), feat);
                }
                
                sName = trimTxt(regex.group(1));
                feat = new DD5E_Feat(sName);
                continue;
            } 
            
            regex = new RegEx("\\APrerequisite\\:(.*)", sLine);
            if (regex.find() ) {
                sText = trimTxt(regex.group(1));
                String ftext = makeFormattedText("#ip;" + sLine);
                if (feat != null) {
                    feat.setText(ftext);
                    feat.setPrereq(sText);
                }
                continue;
            }
            
            if (feat != null) {
                feat.setText(feat.getText() + makeFormattedText(sLine));
            }
            
        }    
    } 
    private void parseImageData(String[] aSourceData) {
        engine.debug(1, "->parseImageData()");
        
        File file;
        DD5E_Image image;
        
        for (String imageFilepath : aSourceData) {
           file = new File(imageFilepath);
           if (file.exists()) {
               image = new DD5E_Image(file.getName(), file.getPath());
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
                    DD5E_ImagePin pin = new DD5E_ImagePin(pins[0], Integer.parseInt(pins[1]), Integer.parseInt(pins[2]), pins[3], pins[4]);
                    
                    if (ImagePins.containsKey(pins[0])) {   
                        ImagePins.get(pins[0]).addShortcut(pins[0], Integer.parseInt(pins[1]), Integer.parseInt(pins[2]), pins[3], pins[4]);
                    } else {
                        DD5E_ImagePins ipins = new DD5E_ImagePins(pins[0]);
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
    private void parseMagicItemData(String[] aSourceData, ObjectContainer objects) {
        engine.debug(1, "->parseMagicItemData()");
        
        String sName, sType, sSubType = "", sRarity, sText;
        boolean bNewMagicItem = true;
        DD5E_MagicItem magicitem = null;
        
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
                magicitem = new DD5E_MagicItem(sName);
                magicitem.setSource(module.getName());
                engine.debug(2,"Magic Item - " + sName);
                continue;
            }
            
            //New Equipment Type
            RegEx regex = new RegEx("\\A(Common|Uncommon|Rare|Very\\srare|Legendary|Artifact)\\s(magic \\w+|[\\w\\s]+)", sLine);
            if (regex.find() ) {
                sRarity = trimTxt(regex.group(1));
                sType = trimTxt(regex.group(2));
                sType = sType.replace("magic ", "");
                sType = makeProperCase(sType);
                
                RegEx r = new RegEx("\\(([\\w\\s]+)\\)\\s?$", sType);
                if (r.find()) {
                    sSubType = trimTxt(r.group(1));
                }
                if (magicitem != null) {
                    magicitem.setRarity(sRarity);
                    magicitem.setType(sType);
                    magicitem.setSubType(sSubType);
                }
                engine.debug(2,"Rarity - " + sRarity);
                engine.debug(2,"Type - " + sType);
                engine.debug(2,"SubType - " + sSubType);
            } else {
                if (magicitem != null) {
                    magicitem.setText(magicitem.getText() + makeFormattedText(sLine));
                }
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
        
        List<DD5E_MagicItem> mis = objects.query(new Predicate<DD5E_MagicItem>() {
            @Override
            public boolean match(DD5E_MagicItem obj) {
                return !obj.getName().equals("   ");
            }
        }); 
        if (!mis.isEmpty()) {
            for (DD5E_MagicItem mi : mis) {
                if (!MagicItems.containsKey(mi.getName())) {
                    MagicItems.put(mi.getName(), mi);
                    con.printMsg("Import: magic item");
                    con.printMsgResult(mi.getName(), normal);
                    engine.debug(2,"Archive Import Completed - " + mi.getName());
                }
            }
        }
        
    }
    private void parseNPCData(String[] aSourceData, ObjectContainer objects) {
        engine.debug(1, "->parseNPCData()");
        
        //Variables
        String sName, sType, sSize, sAC, sACText, sHP, sHD, sSpeed, sSenses, sStat, sScore, sAlignment, sLanguages, sItemName = "", sItemText, sLevel, sXP;
        boolean bNewNPC = true;
        boolean bTraitsBlock = false, bReactionsBlock = false, bActionsBlock = false, bEncBuildBlock = false;
        DD5E_NPC npc = null;
        
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
                npc = new DD5E_NPC(sName);
                npc.setSource(module.getName());
                engine.debug(2,"NPC - " + sName);
                continue;
            }
            
            //Size and Type
            RegEx regex = new RegEx("\\A(Tiny|Small|Medium|Large|Huge|Gargantuan)\\s+(Aberration|Beast|Celestial|Construct|Dragon|Elemental|Fey|Fiend|Giant|Humanoid|Monstrosity|Ooze|Plant|Undead)(.*)", sLine);
            if (regex.find()) {
                sSize = trimTxt(regex.group(1));
                sType = trimTxt(regex.group(2) + regex.group(3));
                if (npc != null) {
                    npc.setSize(sSize);
                    npc.setType(sType);
                    engine.debug(2,"Size - " + sSize);
                    engine.debug(2,"Type - " + sType);
                }
                continue;
            }
            
            //Armor Class
            regex = new RegEx("\\AArmor\\sClass\\s(\\d*)(.*)", sLine);
            if (regex.find()) {
                sAC = trimTxt(regex.group(1));
                sACText = trimTxt(regex.group(2));
                if (npc != null) {
                    npc.setAC(Integer.parseInt(sAC));
                    npc.setACText(sACText);
                    engine.debug(2,"AC - " + sAC);
                    engine.debug(2,"AC Text - " + sACText);
                }
                continue;
            }
            
            //Hit Points and Hit Dice
            regex = new RegEx("\\AHit\\sPoints\\s(\\d*)\\s?(\\(.*\\))(.*)", sLine);
            if (regex.find()) {
                sHP = trimTxt(regex.group(1));
                sHD = trimTxt(regex.group(2));
                if (npc != null) {
                    npc.setHP(Integer.parseInt(sHP));
                    npc.setHD(sHD);
                    engine.debug(2,"HP - " + sHP);
                    engine.debug(2,"HD - " + sHD);
                }
                continue;
            }
            
            //Speed
            regex = new RegEx("\\ASpeed\\s(\\d+\\sft\\..*)", sLine);
            if (regex.find()) {
                sSpeed = trimTxt(regex.group(1));
                if (npc != null) {
                    npc.setSpeed(sSpeed);
                    engine.debug(2,"Speed " + sSpeed);
                }
                continue;
            }
            
            //Senses
            regex = new RegEx("\\ASenses(.*)", sLine);
            if (regex.find()) {
                sSenses = trimTxt(regex.group(1));
                if (npc != null) {
                    npc.setSenses(sSenses);
                    engine.debug(2,"Senses " + sSenses);
                }
                continue;
            }
            
            //Stats
            regex = new RegEx("\\A([Str|Int]+)\\s(\\d+)\\s\\(([-|+]\\d+)\\).*", sLine);
            if (regex.find()) {
                String[] stats = sLine.split("\\)");
                for (String stat : stats) {
                    RegEx r = new RegEx("(\\w+)\\s(\\d+)", stat);
                    if (r.find()) {
                        sStat = trimTxt(r.group(1));
                        if (sStat.startsWith("t")) {
                            sStat = sStat.substring(1, sStat.length());
                        }
                        
                        sScore = trimTxt(r.group(2));
                        if (npc != null) {
                            npc.getAbilities().put(sStat, Integer.parseInt(sScore));
                            engine.debug(2, sStat + " " + sScore);
                        }
                    }
                }
                continue;
            }
            
            //Alignment
            regex = new RegEx("\\AAlignment\\s+(.*)", sLine);
            if (regex.find()) {
                sAlignment = trimTxt(regex.group(1));
                if (npc != null) {
                    npc.setAlignment(sAlignment);
                    engine.debug(2,"Alignment " + sAlignment);
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
            
            //Traits
            regex = new RegEx("\\ATRAITS\\s*$", sLine);
            if (regex.find()) {
                bTraitsBlock = true;
                bReactionsBlock = false;
                bActionsBlock = false;
                bEncBuildBlock = false;
                continue;
            }

            //Reactions
            regex = new RegEx("\\AREACTIONS\\s*$", sLine);
            if (regex.find()) {
                bTraitsBlock = false;
                bReactionsBlock = true;
                bActionsBlock = false;
                bEncBuildBlock = false;
                continue;
            }

            //Actions
            regex = new RegEx("\\AACTIONS\\s*$", sLine);
            if (regex.find()) {
                bTraitsBlock = false;
                bReactionsBlock = false;
                bActionsBlock = true;
                bEncBuildBlock = false;
                continue;
            }
            
            //Encounter Building
            regex = new RegEx("\\AENCOUNTER BUILDING.*$", sLine);
            if (regex.find()) {
                bTraitsBlock = false;
                bReactionsBlock = false;
                bActionsBlock = false;
                bEncBuildBlock = true;
                continue;
            }
            
            if (bTraitsBlock) {
                RegEx r = new RegEx("\\A([\\w\\s\\-\\(\\)\\/]+)\\:([\\w\\s\\.\\,\\-\\n\\+\\(\\)\\;\\/]+)", sLine);
                if (r.find()) {
                  sItemName = trimTxt(r.group(1));
                  sItemText = trimTxt(r.group(2));
                  if (npc != null) {
                    npc.getTraits().put(sItemName, sItemText);
                    engine.debug(2,"Trait " + sItemName);
                  }
                } else if (!sLine.contains(":") && npc != null) {
                  String itemtext = npc.getTraits().get(sItemName);
                  npc.getTraits().put(sItemName, itemtext + sLine);
                }
                continue;
            }
            
            if (bReactionsBlock) {
                RegEx r = new RegEx("\\A([\\w\\s\\-\\(\\)\\/]+)\\:(.*)", sLine);
                if (r.find()) {
                    sItemName = trimTxt(r.group(1));
                    sItemText = trimTxt(r.group(2));
                    if (npc != null) {
                        if (!sItemName.equals("Hit") || !sItemName.equals("Miss") || !sItemName.equals("Failed\\sSave") || !sItemName.equals("Sucessful\\sSave") || !sItemName.equals("Special")) {
                           npc.getReactions().put(sItemName, sItemText);
                           engine.debug(2,"Reaction " + sItemName);
                        } 
                        if (sItemName.equals("Hit") || sItemName.equals("Miss") || sItemName.equals("Failed\\sSave") || sItemName.equals("Sucessful\\sSave") || sItemName.equals("Special")) {
                            String itemtext = npc.getReactions().get(sItemName);
                            npc.getReactions().put(sItemName, itemtext + " " + sLine);   
                        } 
                    }
                } else {
                    if (npc != null) {
                        String itemtext = npc.getReactions().get(sItemName);
                        npc.getReactions().put(sItemName, itemtext + sLine);    
                    }
                }
                continue;
            }
            
            if (bActionsBlock) {
                RegEx r = new RegEx("\\A([\\w\\s\\-\\(\\)\\/]+)\\:(.*)", sLine);
                if (r.find()) {
                    sItemName = trimTxt(r.group(1));
                    sItemText = trimTxt(r.group(2));
                    if (npc != null) {
                        if (!sItemName.equals("Hit") || !sItemName.equals("Miss") || !sItemName.equals("Failed\\sSave") || !sItemName.equals("Sucessful\\sSave") || !sItemName.equals("Special")) {
                           npc.getActions().put(sItemName, sItemText);
                           engine.debug(2,"Action " + sItemName);
                        } 
                        if (sItemName.equals("Hit") || sItemName.equals("Miss") || sItemName.equals("Failed\\sSave") || sItemName.equals("Sucessful\\sSave") || sItemName.equals("Special")) {
                            String itemtext = npc.getActions().get(sItemName);
                            npc.getActions().put(sItemName, itemtext + sLine);   
                        } 
                    }
                } else {
                    if (npc != null) {
                        String itemtext = npc.getActions().get(sItemName);
                        npc.getActions().put(sItemName, itemtext + " " + sLine);    
                    }
                }
                continue;
            }
            
            if (bEncBuildBlock) {
                RegEx r = new RegEx("\\ALevel\\s+(\\d+).*XP\\s+([\\d\\,]+)", sLine);
                if (r.find()) {
                    sLevel = trimTxt(r.group(1));
                    sXP = trimTxt(r.group(2));
                    sXP = sXP.replace(",", "");
                    if (npc != null) {
                        npc.setLevel(Integer.parseInt(sLevel));
                        npc.setXP(Integer.parseInt(sXP));
                        engine.debug(2,"Level " + sLevel + " XP " + sXP);
                    }
                }
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
        List<DD5E_NPC> npcs = objects.query(new Predicate<DD5E_NPC>() {
            @Override
            public boolean match(DD5E_NPC obj) {
                return !obj.getName().equals("   ");
            }
        }); 
        if (!npcs.isEmpty()) {
            for (DD5E_NPC obj : npcs) {
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
        DD5E_Parcel parcel = null;
        
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
                parcel = new DD5E_Parcel(sName);
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
    private void parseRaceData(String[] aSourceData) {
        engine.debug(1, "->parseRaceData()");
        
        String sName = "", sTraitName = "", sTraitText, sSubRaceName = "", sSubRaceTraitName = "", sSubRaceTraitText, sText;
        String sTLinks = "", sSRLinks = "", sSRTLinks = "";
        boolean bDescriptionBlock = false, bTraitsBlock = false, bSubRaceBlock = false, bSubRaceTraitsBlock = false;
        DD5E_Race race = null;
        DD5E_SubRace subrace = null;
        
        for (String sLine : aSourceData) {
            //Empty or blank lines, skip
            RegEx emptyline = new RegEx("\\A$",sLine);
            RegEx whitespaceline = new RegEx("\\A\\s+$",sLine);
            if (emptyline.find() || whitespaceline.find()) {              
                engine.debug(2,"Empty Line");
                continue;
            } 
            
            //New Race
            RegEx regex = new RegEx("\\A\\#\\#\\;(.*)",sLine);
            if (regex.find()) {
                
                if (subrace != null && race != null ) {
                    sSRTLinks += makeFormattedText("#zle;");
                    subrace.setText(subrace.getText() + sSRTLinks);

                    if (subrace.isComplete()) {
                        con.printMsg("Parse : subrace");
                        con.printMsgResult(subrace.getName(), normal);
                        engine.debug(2,"Parse Completed - " + subrace.getName());
                    } else {
                        con.printMsg("Parse : subrace");
                        con.printMsgResult(subrace.getName(), warning);
                        engine.debug(2,"Parse Incomplete - " + subrace.getName());                   
                    }
                    race.addSubRace(sSubRaceName, subrace);
                    subrace = null;
                }
                
                
                if (race != null ) {
                    sTLinks = sTLinks + makeFormattedText("#zle;");
                    sSRLinks = sSRLinks + makeFormattedText("#zle;");
                    race.setText(race.getText() + sTLinks);
                    race.getTraits().put("Subrace", race.getTraits().get("Subrace") + sSRLinks);

                    if (race.isComplete()) {
                        con.printMsg("Parse : race");
                        con.printMsgResult(race.getName(), normal);
                        engine.debug(2,"Parse Completed - " + race.getName());
                    } else {
                        con.printMsg("Parse : race");
                        con.printMsgResult(race.getName(), warning);
                        engine.debug(2,"Parse Incomplete - " + race.getName());                   
                    }
                    Races.put(race.getName(), race);
                }
                sName = trimTxt(regex.group(1));
                race = new DD5E_Race(sName);
                bDescriptionBlock = true;
                bTraitsBlock = false;
                bSubRaceBlock = false;
                bSubRaceTraitsBlock = false;
                sTLinks = "";
                sSRLinks = "";
                sSRTLinks = "";
                engine.debug(2,"Race - " + sName);
                continue;
            }
            
            //New Race Traits Block
            regex = new RegEx("\\A\\#h\\;?Traits$",sLine);
            if (regex.find()) {
                bDescriptionBlock = false;
                bTraitsBlock = true;
                bSubRaceBlock = false;
                bSubRaceTraitsBlock = false;
                if (race != null) {
                    race.setText(race.getText() + " " + makeFormattedText(sLine));
                }
                sTLinks = makeFormattedText("#zls;");
                sSRLinks = makeFormattedText("#zls;");
                engine.debug(2,"Traits Block");
                continue;
            }
            
            //New SubRace Block
            regex = new RegEx("\\A\\#s\\;(.*)$",sLine);
            if (regex.find()) {
                                    
                if (subrace != null && race != null ) {
                    sSRTLinks += makeFormattedText("#zle;");
                    subrace.setText(subrace.getText() + sSRTLinks);

                    if (subrace.isComplete()) {
                        con.printMsg("Parse : subrace");
                        con.printMsgResult(subrace.getName(), normal);
                        engine.debug(2,"Parse Completed - " + subrace.getName());
                    } else {
                        con.printMsg("Parse : subrace");
                        con.printMsgResult(subrace.getName(), warning);
                        engine.debug(2,"Parse Incomplete - " + subrace.getName());                   
                    }
                    race.addSubRace(sSubRaceName, subrace);   
                }
                
                bDescriptionBlock = false;
                bTraitsBlock = false;
                bSubRaceBlock = true;
                bSubRaceTraitsBlock = false;
                sSubRaceName = trimTxt(regex.group(1));
                subrace = new DD5E_SubRace(sSubRaceName);
                
                sSRTLinks = makeFormattedText("#h;Traits");
                sSRTLinks += makeFormattedText("#zls;");
                sSRLinks += makeFormattedText("#zl;" + sName.toLowerCase() + "_subrace;" + sSubRaceName);
                engine.debug(2,"SubRace - " + sSubRaceName);
                continue;
            }
            
            //Description Block
            if (bDescriptionBlock && race != null) {
                race.setText(race.getText() + makeFormattedText(sLine));
                engine.debug(2,"Description Text ");
                continue;
            }
            
            //Traits Block
            if (bTraitsBlock && race != null) {
                regex = new RegEx("([\\w\\s\\-]+)\\:(.+)$", sLine);
                if (regex.find()) {
                    sTraitName = trimTxt(regex.group(1));
                    sTraitText = trimTxt(regex.group(2));
                    race.addTrait(sTraitName, sTraitText);
                    sTLinks += makeFormattedText("#zl;" + sName.toLowerCase() + "_trait;" + sTraitName); 
                    engine.debug(2,"Trait - " + sTraitName);
                } else if (race.getTraits().containsKey(sTraitName)) {
                    race.getTraits().put(sTraitName, race.getTraits().get(sTraitName) + makeFormattedText(sLine));
                    engine.debug(2,"Trait Text");
                } else {
                    race.setText(race.getText() + makeFormattedText(sLine));   
                }
                continue;
            }
            
            //SubRace Block
            if (bSubRaceBlock && race != null && subrace != null) {
                regex = new RegEx("([\\w\\s\\-]+)\\:(.+)$", sLine);
                if (regex.find()) {
                    sSubRaceTraitName = trimTxt(regex.group(1));
                    sSubRaceTraitText = trimTxt(regex.group(2));
                    subrace.getTraits().put(sSubRaceTraitName,sSubRaceTraitText);
                    sSRTLinks += makeFormattedText("#zl;" + sName.toLowerCase() + "_subracetrait;" + sSubRaceTraitName + ";" + sSubRaceName);
                    engine.debug(2,"SubRace Trait - " + sSubRaceTraitName);
                } else if (subrace.getTraits().containsKey(sSubRaceTraitName)) {
                    subrace.getTraits().put(sSubRaceTraitName, subrace.getTraits().get(sSubRaceTraitName) + makeFormattedText(sLine));  
                } else {
                    subrace.setText(subrace.getText() + makeFormattedText(sLine));   
                }
            }
        }
        
        if (race != null ) {
            sTLinks = sTLinks + makeFormattedText("#zle;");
            sSRLinks = sSRLinks + makeFormattedText("#zle;");
            race.setText(race.getText() + sTLinks + sSRLinks);
            
            if (race.isComplete()) {
                con.printMsg("Parse : race");
                con.printMsgResult(race.getName(), normal);
                engine.debug(2,"Parse Completed - " + race.getName());
            } else {
                con.printMsg("Parse : race");
                con.printMsgResult(race.getName(), warning);
                engine.debug(2,"Parse Incomplete - " + race.getName());                   
            }
            Races.put(race.getName(), race);
        }
    } 
    private void parseReferenceManualData(String[] aSourceData) {
        engine.debug(1, "->parseReferenceManualData()");
        
        String sChapterName = "", sSubChapterName = "", sPageName = "", sText;
        DD5E_ReferencePage page;
        DD5E_ReferenceSubChapter subchapter = null;
        DD5E_ReferenceChapter chapter = null;
        ReferenceManual = new DD5E_ReferenceManual(module.getName());
        
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
                chapter = new DD5E_ReferenceChapter(sChapterName);
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
                subchapter = new DD5E_ReferenceSubChapter(sSubChapterName);
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
                page = new DD5E_ReferencePage(sPageName);
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
    private void parseSkillData(String[] aSourceData) {
        engine.debug(1, "->parseSkillData()");
        
        String sName = "", sStat = "";
        DD5E_Skill skill = null;
        
        for (String sLine : aSourceData) {
            //Empty or blank lines, skip
            RegEx emptyline = new RegEx("\\A$",sLine);
            RegEx whitespaceline = new RegEx("\\A\\s+$",sLine);
            if (emptyline.find() || whitespaceline.find()) {              
                engine.debug(2,"Empty Line");
                continue;
            } 
            
            //New Stat
            RegEx regex = new RegEx("\\A\\#\\@\\;(.*)",sLine);
            if (regex.find()) {
               sStat = trimTxt(regex.group(1));
               engine.debug(2,"Stat - " + sStat);
               continue;
            } 
            
            //New SKill
            regex = new RegEx("\\A\\#\\#\\;(.*)",sLine);
            if (regex.find()) {
                
                if (skill != null) {
                    if (skill.isComplete()) {
                        con.printMsg("Parse - skill");
                        con.printMsgResult(sName, normal);
                        engine.debug(2,"Skill - " + skill.getName());
                    } else {
                        con.printMsg("Parse - skill");
                        con.printMsgResult(sName, warning);
                        engine.debug(2,"Skill - " + skill.getName());
                    }
                    Skills.put(skill.getName(), skill);
                }
                
                sName = trimTxt(regex.group(1));
                skill = new DD5E_Skill(sName);
                skill.setStat(sStat);
                continue;
            }
            
            if (skill != null) {
                skill.setText(skill.getText() + makeFormattedText(sLine));
            }
        }
  
        if (skill != null) {
            if (skill.isComplete()) {
                con.printMsg("Parse - skill");
                con.printMsgResult(sName, normal);
                engine.debug(2,"Skill - " + skill.getName());
            } else {
                con.printMsg("Parse - skill");
                con.printMsgResult(sName, warning);
                engine.debug(2,"Skill - " + skill.getName());
            }
            Skills.put(skill.getName(), skill);
        }
    } 
    private void parseSpellData(String[] aSourceData) {
        engine.debug(1, "->parseSpellData()");
        
        String sName = "", sClass = "", sLevel, sText, sSchool, sCastingTime, sDuration, sComponents, sRange, sDescriptors;
        boolean bIndexBlock = true, bSpellDataBlock = false, bNewSpell = false;
        DD5E_Spell spell;
        
        for (String sLine : aSourceData) {
            //Empty or blank lines, skip
            RegEx regex1 = new RegEx("\\A$",sLine);
            RegEx regex2 = new RegEx("\\A\\s+$",sLine);
            if (regex1.find() || regex2.find()) {              
                engine.debug(2,"Empty Line");
                bNewSpell = true;
                continue;
            } 
            
            //Spell Index Block
            regex1 = new RegEx("\\A\\#\\@\\;.*",sLine);
            if (regex1.find()) {
               bIndexBlock = true;
               bSpellDataBlock = false;
               engine.debug(2,"Spell Index Block");
               continue;
            } 
            
            //Spell Data Block
            regex1 = new RegEx("\\A\\#\\#\\;.*",sLine);
            if (regex1.find()) {
               sName = ""; 
               bIndexBlock = false;
               bSpellDataBlock = true;
               bNewSpell = true;
               engine.debug(2,"Spell Data Block");
               continue;
            } 
            
            //Spell index
            if (bIndexBlock) {
                regex1 = new RegEx("(.*)\\sSpells",sLine);
                regex2 = new RegEx("Level\\s\\d+\\sSpells",sLine);
                if (regex1.find() && !regex2.find()) {
                    sClass = trimTxt(regex1.group(1));
                    engine.debug(2,"Spell Index Class - " + sClass);
                    continue;
                }
                
                regex1 = new RegEx("Level\\s\\d+\\sSpells",sLine);
                regex2 = new RegEx("Cantrip",sLine);
                if (bIndexBlock && (!regex1.find() && !regex2.find())) {
                    sName = trimTxt(sLine);
                    if (Spells.containsKey(sName)) {
                        Spells.get(sName).setSource(Spells.get(sName).getSource() + ", " + sClass);
                    } else {
                        Spells.put(sName, new DD5E_Spell(sName));
                        Spells.get(sName).setSource(sClass);
                    }
                    engine.debug(2,"Spell Index Spell - " + sName);
                    continue;
                }  
            }
            
            //Spell Data
            if (bSpellDataBlock) {
                
                if (bNewSpell) {                   
                    if (!sName.equals("")) {
                        con.printMsg("Parse - spell");
                        if (Spells.containsKey(sName) && Spells.get(sName).isComplete()) {
                            con.printMsgResult(sName, normal);
                        } else {
                            con.printMsgResult(sName, warning);                       
                        }
                    }
                    sName = trimTxt(sLine);
                    bNewSpell = false;
                    engine.debug(2,"Spell - " + sName);
                    continue;
                }
                
                //Spell Data - Level, School and Descriptors (if any)
                regex1 = new RegEx("\\A(\\d+)(st|nd|rd|th).*level\\s(\\w+)\\s?(.*)", sLine);
                if (regex1.find()) {
                    sLevel = trimTxt(regex1.group(1));
                    sSchool = trimTxt(regex1.group(3));
                
                    if (Spells.containsKey(sName)) {
                        Spells.get(sName).setLevel(Integer.parseInt(sLevel));
                        Spells.get(sName).setSchool(makeProperCase(sSchool));
                        engine.debug(2,"Level - " + sLevel);
                        engine.debug(2,"School - " + makeProperCase(sSchool));
                        
                        regex2 = new RegEx("\\(([\\w|\\s]+)\\)", regex1.group(4));
                        if (regex2.find()) {
                            sDescriptors = trimTxt(regex2.group(1));
                            Spells.get(sName).setDescriptors(makeProperCase(sDescriptors));
                            engine.debug(2,"Descriptor - " + makeProperCase(sDescriptors));
                        }
                    }
                    continue;
                }        
                        
                //Spell Data - Cantrip (Level 0) and School
                regex1 = new RegEx("\\A([\\w]+)\\s[c|C]antrip", sLine);
                if (regex1.find()) {
                    sSchool = trimTxt(regex1.group(1));
                    if (Spells.containsKey(sName)) {
                        Spells.get(sName).setLevel(0);
                        Spells.get(sName).setSchool(makeProperCase(sSchool));
                        engine.debug(2,"Level - Cantrip");
                        engine.debug(2,"School - " + makeProperCase(sSchool));
                    }
                    continue;
                }
                
                //Spell Data - Casting time
                regex1 = new RegEx("\\ACasting Time\\:\\s(.*)", sLine);
                if (regex1.find()) {
                    sCastingTime = trimTxt(regex1.group(1));
                    if (Spells.containsKey(sName)) {
                        Spells.get(sName).setCastingTime(sCastingTime);
                        engine.debug(2,"Casting Time - " + sCastingTime);
                    }
                    continue;
                }
                
                //Spell Data - Range
                regex1 = new RegEx("\\ARange\\:\\s(.*)", sLine);
                if (regex1.find()) {
                    sRange = trimTxt(regex1.group(1));
                    if (Spells.containsKey(sName)) {
                        Spells.get(sName).setRange(sRange);
                        engine.debug(2,"Range - " + sRange);
                    }
                    continue;
                }
                
                //Spell Data - Duration
                regex1 = new RegEx("\\ADuration\\:\\s(.*)", sLine);
                if (regex1.find()) {
                    sDuration = trimTxt(regex1.group(1));
                    if (Spells.containsKey(sName)) {
                        Spells.get(sName).setDuration(sDuration);
                        engine.debug(2,"Duration - " + sDuration);
                    }
                    continue;
                }
                
                //Spell Data - Components
                regex1 = new RegEx("\\AMaterial\\sComponents\\:\\s(.*)", sLine);
                if (regex1.find()) {
                    sComponents = trimTxt(regex1.group(1));
                    if (Spells.containsKey(sName)) {
                        Spells.get(sName).setComponents(sComponents);
                        engine.debug(2,"Components - " + sComponents);
                    }
                    continue;
                }
                
                if (Spells.containsKey(sName)) {
                    Spells.get(sName).setText(Spells.get(sName).getText() + makeFormattedText(sLine));
                }
            }
        } 
        
        Collection<String> unsorted = Spells.keySet();
        sortedSpells = asSortedList(unsorted);
        
    }
    private void parseStoryData(String[] aSourceData) {
        engine.debug(1, "->parseStoryData()");
        
        String sName = "", sCategory = "";
        DD5E_Story story = null;
        
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
                story = new DD5E_Story(sName);
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
        DD5E_Table table = null;
        
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
                table = new DD5E_Table(sName);
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

                    DD5E_TableRow res = new DD5E_TableRow(fRange, tRange, results);
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
        DD5E_Token token;
        
        for (String tokenFilepath : aSourceData) {
           file = new File(tokenFilepath);
           if (file.exists()) {
               token = new DD5E_Token(file.getName(), file.getPath());
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
            DD5E_NPC npc = (DD5E_NPC) entry.getValue();
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
        
        /*
        //Update Traps
        for (Map.Entry entry : Traps.entrySet()) {
            String trapKey = (String) entry.getKey();
            DD5E_Trap trap = (DD5E_Trap) entry.getValue();
            String sanatisedName  = sanatise(trapKey);
            
            if (trap.getToken().equals("")) {
                con.printMsg("Parse : auto linked trap token");
                if (Tokens.containsKey(sanatisedName + ".png")) {
                    trap.setToken(Tokens.get(sanatisedName+".png").getName());
                    con.printMsgResult(trapKey+"->"+sanatisedName+".png", normal);
                } else if (Tokens.containsKey(trapKey.substring(0, 1) + ".png")) {
                    trap.setToken(Tokens.get(trapKey.substring(0, 1) + ".png").getName());
                    con.printMsgResult(trapKey+"->"+trapKey.substring(0, 1) + ".png", normal);
                } else {
                    con.printMsgResult(trapKey, warning);
                }
            }
        } 
        */
    }
       
    //Private Reference list generation methods
    private void generateBackgroundIndexes(String aSourceData) {
        engine.debug(1, "->generateBackgroundIndexes()");
        LinkedHashMap<String,String> BackgroundLetterSeen = new LinkedHashMap<>();
        
        for (Map.Entry entry : Backgrounds.entrySet()) {
            String key = (String) entry.getKey();
            DD5E_Background val = (DD5E_Background) entry.getValue();
            String firstchar = key.substring(0, 1).toUpperCase();
            if (!BackgroundLetterSeen.containsKey(firstchar)) { 
                String[] bgrounds = new String[1];
                bgrounds[0] = key;
                BackgroundsByLetter.put(firstchar, bgrounds);
                BackgroundLetterSeen.put(firstchar, "");
            } else {
                String[] bgrounds = BackgroundsByLetter.get(firstchar);
                bgrounds = Arrays.copyOf(bgrounds, bgrounds.length+1); 
                bgrounds[bgrounds.length-1] = key;
                BackgroundsByLetter.put(firstchar, bgrounds);
            }
            engine.debug(2, "Index: Found " + key + ", adding to " + firstchar);
        }
    }
    private void generateClassIndexes(String aSourceData) {
        engine.debug(1, "->generateClassIndexes()");
        LinkedHashMap<String,String> ClassLetterSeen = new LinkedHashMap<>();
        
        for (Map.Entry entry : Classes.entrySet()) {
            String key = (String) entry.getKey();
            DD5E_Class val = (DD5E_Class) entry.getValue();
            String firstchar = key.substring(0, 1).toUpperCase();
            if (!ClassLetterSeen.containsKey(firstchar)) { 
                String[] sClasses = new String[1];
                sClasses[0] = key;
                ClassesByLetter.put(firstchar, sClasses);
                ClassLetterSeen.put(firstchar, "");
            } else {
                String[] sClasses = ClassesByLetter.get(firstchar);
                sClasses = Arrays.copyOf(sClasses, sClasses.length+1); 
                sClasses[sClasses.length-1] = key;
                ClassesByLetter.put(firstchar, sClasses);
            }
            engine.debug(2, "Index: Found " + key + ", adding to " + firstchar);
        }
    }
    private void generateEquipmentIndexes(String aSourceData) {
        engine.debug(1, "->generateEquipmentIndexes()");
        
        //Add equipment to index of equipment by type
        for (Map.Entry entry : Equipment.entrySet()) {
            String key = (String) entry.getKey();
            DD5E_Equipment val = (DD5E_Equipment) entry.getValue();
        
            String[] equip;
            if (EquipmentByType.get(val.getType()) != null ) {
                equip = Arrays.copyOf(EquipmentByType.get(val.getType()), EquipmentByType.get(val.getType()).length+1);
                equip[equip.length-1] = key;
            } else {
                equip = new String[1];
                equip[0] = key;
            } 
            EquipmentByType.put(val.getType(), equip);
        }
        
        //Add subtypes to index of equipment types
        for (Map.Entry e1 : EquipmentByType.entrySet()) {
            String typeKey = (String) e1.getKey();
            for (Map.Entry e2 : Equipment.entrySet()) {
                String equipKey = (String) e2.getKey();
                DD5E_Equipment equipVal = (DD5E_Equipment) e2.getValue();
                if (equipVal.getType().toLowerCase().equals(typeKey.toLowerCase())) {
                    String equipSubKey = equipVal.getSubType();
                    EquipmentSubTypesByType.put(equipSubKey, typeKey);
                }
            }
        }
        
        
    }
    private void generateFeatIndexes(String aSourceData) {
        engine.debug(1, "->generateFeatIndexes()");
        
        LinkedHashMap<String,String> FeatLetterSeen = new LinkedHashMap<>();
        
        for (Map.Entry entry : Feats.entrySet()) {
            String key = (String) entry.getKey();
            DD5E_Feat val = (DD5E_Feat) entry.getValue();
            String firstchar = key.substring(0, 1).toUpperCase();
            if (!FeatLetterSeen.containsKey(firstchar)) { 
                String[] sFeats = new String[1];
                sFeats[0] = key;
                FeatsByLetter.put(firstchar, sFeats);
                FeatLetterSeen.put(firstchar, "");
            } else {
                String[] sFeats = FeatsByLetter.get(firstchar);
                sFeats = Arrays.copyOf(sFeats, sFeats.length+1); 
                sFeats[sFeats.length-1] = key;
                FeatsByLetter.put(firstchar, sFeats);
            }
            engine.debug(2, "Index: Found " + key + ", adding to " + firstchar);
        }
    }
    private void generateMagicItemIndexes(String aSourceData) {
        engine.debug(1, "->generateMagicItemIndexes()");
        
        //Add magic items to index of magic items by type
        for (Map.Entry entry : MagicItems.entrySet()) {
            String key = (String) entry.getKey();
            DD5E_MagicItem val = (DD5E_MagicItem) entry.getValue();
        
            String[] magicitems;
            if (MagicItemsByType.get(val.getType()) != null ) {
                magicitems = Arrays.copyOf(MagicItemsByType.get(val.getType()), MagicItemsByType.get(val.getType()).length+1);
                magicitems[magicitems.length-1] = key;
            } else {
                magicitems = new String[1];
                magicitems[0] = key;
            } 
            MagicItemsByType.put(val.getType(), magicitems);
        }
        
        //Add subtypes to index of magic items types
        for (Map.Entry e1 : MagicItemsByType.entrySet()) {
            String typeKey = (String) e1.getKey();
            for (Map.Entry e2 : MagicItems.entrySet()) {
                String miKey = (String) e2.getKey();
                DD5E_MagicItem miVal = (DD5E_MagicItem) e2.getValue();
                if (miVal.getType().toLowerCase().equals(typeKey.toLowerCase())) {
                    String miSubKey = miVal.getSubType();
                    MagicItemSubTypesByType.put(miSubKey, typeKey);
                }
            }
        }
    }
    private void generateNPCIndexes(String aSourceData) {
        engine.debug(1, "->generateNPCIndexes()");
        LinkedHashMap<String,String> NPCLetterSeen = new LinkedHashMap<>();
        LinkedHashMap<Integer,String> NPCLevelSeen = new LinkedHashMap<>();
        
        //Add npcs to index of npcs by letter
        for (Map.Entry entry : NPCs.entrySet()) {
            String key = (String) entry.getKey();
            DD5E_NPC val = (DD5E_NPC) entry.getValue();
            String firstchar = key.substring(0, 1).toUpperCase();
            if (!NPCLetterSeen.containsKey(firstchar)) { 
                String[] npcs = new String[1];
                npcs[0] = key;
                NPCsByLetter.put(firstchar, npcs);
                NPCLetterSeen.put(firstchar, "");
            } else {
                String[] npcs = NPCsByLetter.get(firstchar);
                npcs = Arrays.copyOf(npcs, npcs.length+1); 
                npcs[npcs.length-1] = key;
                NPCsByLetter.put(firstchar, npcs);
            }
            engine.debug(2, "Index: Found " + key + ", adding to " + firstchar);
        }
        
        //Add npcs to index of npcs by level
        for (Map.Entry entry : NPCs.entrySet()) {
            String key = (String) entry.getKey();
            DD5E_NPC val = (DD5E_NPC) entry.getValue();
            Integer level = val.getLevel();
            if (!NPCLevelSeen.containsKey(level)) { 
                String[] npcs = new String[1];
                npcs[0] = key;
                NPCsByLevel.put(level, npcs);
                NPCLevelSeen.put(level, "");
            } else {
                String[] npcs = NPCsByLevel.get(level);
                npcs = Arrays.copyOf(npcs, npcs.length+1); 
                npcs[npcs.length-1] = key;
                NPCsByLevel.put(level, npcs);
            }
            engine.debug(2, "Index: Found " + key + ", adding to " + level);
        }
        
        //Add npcs to index of npcs by type
        for (Map.Entry entry : NPCs.entrySet()) {
            String key = (String) entry.getKey();
            DD5E_NPC val = (DD5E_NPC) entry.getValue();
        
            String[] npcs;
            if (NPCsByType.get(val.getType()) != null ) {
                npcs = Arrays.copyOf(NPCsByType.get(val.getType()), NPCsByType.get(val.getType()).length+1);
                npcs[npcs.length-1] = key;
            } else {
                npcs = new String[1];
                npcs[0] = key;
            } 
            NPCsByType.put(val.getType(), npcs);
        }
    }
    private void generateRaceIndexes(String aSourceData) {
        engine.debug(1, "->generateRaceIndexes()");
        LinkedHashMap<String,String> RaceLetterSeen = new LinkedHashMap<>();
        
        //Add npcs to index of npcs by letter
        for (Map.Entry entry : Races.entrySet()) {
            String key = (String) entry.getKey();
            DD5E_Race val = (DD5E_Race) entry.getValue();
            String firstchar = key.substring(0, 1).toUpperCase();
            if (!RaceLetterSeen.containsKey(firstchar)) { 
                String[] races = new String[1];
                races[0] = key;
                RacesByLetter.put(firstchar, races);
                RaceLetterSeen.put(firstchar, "");
            } else {
                String[] races = RacesByLetter.get(firstchar);
                races = Arrays.copyOf(races, races.length+1); 
                races[races.length-1] = key;
                RacesByLetter.put(firstchar, races);
            }
            engine.debug(2, "Index: Found " + key + ", adding to " + firstchar);
        }
        
    }
    private void generateSkillIndexes(String aSourceData) {
        engine.debug(1, "->generateSkillIndexes()");
        
        LinkedHashMap<String,String> SkillLetterSeen = new LinkedHashMap<>();
        
        for (Map.Entry entry : Skills.entrySet()) {
            String key = (String) entry.getKey();
            DD5E_Skill val = (DD5E_Skill) entry.getValue();
            String firstchar = key.substring(0, 1).toUpperCase();
            if (!SkillLetterSeen.containsKey(firstchar)) { 
                String[] skills = new String[1];
                skills[0] = key;
                SkillsByLetter.put(firstchar, skills);
                SkillLetterSeen.put(firstchar, "");
            } else {
                String[] skills = SkillsByLetter.get(firstchar);
                skills = Arrays.copyOf(skills, skills.length+1); 
                skills[skills.length-1] = key;
                SkillsByLetter.put(firstchar, skills);
            }
            engine.debug(2, "Index: Found " + key + ", adding to " + firstchar);
        }
        
    }
    private void generateSpellIndexes(String aSourceData) {
        engine.debug(1, "->generateSpellIndexes()");
        
        //Add spells to index of spell classes
        for (Map.Entry entry : Spells.entrySet()) {
            String key = (String) entry.getKey();
            DD5E_Spell val = (DD5E_Spell) entry.getValue();
        
            String[] spells;
            
            String source = val.getSource();
            if (source.contains(",")) {
                source = val.getSource().split(",")[0];
            }
            
            if (SpellsByClass.get(val.getSource()) != null ) {
                spells = Arrays.copyOf(SpellsByClass.get(source), SpellsByClass.get(source).length+1);
                spells[spells.length-1] = key;
            } else {
                spells = new String[1];
                spells[0] = key;
            } 
            SpellsByClass.put(source, spells);
        }
        
        //Add Levels to index of spell classes
        for (Map.Entry e1 : SpellsByClass.entrySet()) {
            String classKey = (String) e1.getKey();
            for (Map.Entry e2 : Spells.entrySet()) {
                String sKey = (String) e2.getKey();
                DD5E_Spell sVal = (DD5E_Spell) e2.getValue();
                String[] levels;
                if (sVal.getSource().toLowerCase().contains(classKey.toLowerCase())) {
                    String sLevelKey = sVal.getLevel().toString();
                    if (SpellLevelsByClass.get(classKey) != null ) {
                        if (!Arrays.asList(SpellLevelsByClass.get(classKey)).contains(sLevelKey)) {
                            levels = Arrays.copyOf(SpellLevelsByClass.get(classKey), SpellLevelsByClass.get(classKey).length+1);
                            levels[levels.length-1] = sLevelKey;
                            SpellLevelsByClass.put(classKey, levels);
                        } 
                    } else {
                        levels = new String[1];
                        levels[0] = sLevelKey;
                        SpellLevelsByClass.put(classKey, levels);
                    } 
                    
                }
            }
        }
    }
    
    //Private generate XML data methods
    private void generateBackgroundXML(Element E) {
        engine.debug(1, "->generateBackgroundXML()");
        
        Element backgrounddata = new Element("backgrounddata");
        for (Map.Entry entry : Backgrounds.entrySet()) {
            String key = (String) entry.getKey();
            DD5E_Background val = (DD5E_Background) entry.getValue();
            String sanatisedName = sanatise(key);

            Element b = createElement(sanatisedName);
            Element bName = createElement("name", key, "string");
            Element bText = createElement("text", val.getText(), "formattedtext");           
            Element bTraits = createElement("traits");
            
            for (Map.Entry e : val.getTraits().entrySet())  {
                String k = (String) e.getKey();
                String v = (String) e.getValue();
                String sName = sanatise(k);
                
                Element t = createElement(sName);
                Element tName = createElement("name", k, "string");
                Element tText = createElement("text", v.toString(), "formattedtext");             
                t.addContent(tName);
                t.addContent(tText);
                bTraits.addContent(t);
            }
            
            b.addContent(bName);
            b.addContent(bText);
            b.addContent(bTraits);
            
            for (Map.Entry e : val.getProficiencies().entrySet())  {
                String k = (String) e.getKey();
                String v = (String) e.getValue();
                String sName = sanatise(k);

                Element t = createElement(sName, v.toString(), "string");
                b.addContent(t);
            }
            
            Element bLanguages = createElement("languages", val.getLanguages(), "string");
            b.addContent(bLanguages);

            if (!val.getEquipment().equals("")) {
                Element bEquipment = createElement("equipment", val.getEquipment(), "string");
                b.addContent(bEquipment);
            }
            backgrounddata.addContent(b);
        }
        E.addContent(backgrounddata);
    } 
    private void generateClassXML(Element E) {
        engine.debug(1, "->generateClassXML()");
        
        Element classdata = new Element("classdata");
        
        for (Map.Entry entry : Classes.entrySet()) {
            String key = (String) entry.getKey();
            DD5E_Class val = (DD5E_Class) entry.getValue();
            String sanatisedName = sanatise(key);
                    
            Element c = createElement(sanatisedName);
            Element cName = createElement("name", key, "string");
            Element cText = createElement("text", val.getText(), "formattedtext");              

            c.addContent(cName);
            c.addContent(cText);

            Element cHP = createElement("hp");
            for (Map.Entry e : val.getHP().entrySet())  {
                String k = (String) e.getKey();
                String v = (String) e.getValue();
                String sName = sanatise(k);

                Element h = createElement(sName);
                Element hName = createElement("name", k, "string");
                Element hText = createElement("text", v.toString(), "formattedtext");             
                h.addContent(hName);
                h.addContent(hText);
                cHP.addContent(h);
            }
            c.addContent(cHP);
            

            Element cProf = createElement("proficiencies");
            for (Map.Entry e : val.getProficiencies().entrySet())  {
                String k = (String) e.getKey();
                String v = (String) e.getValue();
                String sName = sanatise(k);

                Element p = createElement(sName);
                Element pName = createElement("name", k, "string");
                Element pText = createElement("text", v.toString(), "formattedtext");             
                p.addContent(pName);
                p.addContent(pText);
                cProf.addContent(p);
            }
            c.addContent(cProf);

            Element cFeat = createElement("features");
            for (Map.Entry e : val.getFeatures().entrySet())  {
                String k = (String) e.getKey();
                DD5E_ClassFeature v = (DD5E_ClassFeature) e.getValue();
                String sName = sanatise(k);

                Element f = createElement(sName);
                
                String tmpName = k.replaceAll("\\d+", "");
                
                Element fName = createElement("name", tmpName, "string");
                Element fLevel = createElement("level", v.getLevel(), "number");             
                Element fText = createElement("text", v.getText(), "formattedtext");             
                f.addContent(fName);
                f.addContent(fLevel);
                f.addContent(fText);
                
                if (!v.getSpecialization().equals("") ) {
                    Element fSpecialization = createElement("specialization", v.getSpecialization(), "string");
                    f.addContent(fSpecialization);
                }
                
                cFeat.addContent(f);
            }
            c.addContent(cFeat);
            
            Element cAbil = createElement("abilities");
            for (Map.Entry e : val.getAbilities().entrySet())  {
                String k = (String) e.getKey();
                DD5E_ClassAbility v = (DD5E_ClassAbility) e.getValue();
                String sName = sanatise(k);

                Element a = createElement(sName);
                Element aName = createElement("name", k, "string");
                Element aLevel = createElement("level", v.getLevel(), "number");             
                Element aText = createElement("text", v.getText(), "formattedtext");             
                a.addContent(aName);
                a.addContent(aLevel);
                a.addContent(aText);
                cAbil.addContent(a);
            }
            c.addContent(cAbil);
            
            if (!val.getEquipment().isEmpty()) {
                Element eEquip = createElement("equipment");
                c.addContent(eEquip);
                for (Map.Entry ent : val.getEquipment().entrySet()) {
                    String e = (String) ent.getKey();
                    String v = (String) ent.getValue();
                    String sanatisedKey = sanatise(e);
                    
                    Element eID = createElement(sanatisedKey);
                    eEquip.addContent(eID);
                    
                    Element eGroup = createElement("group", e, "string");
                    Element eItem = createElement("item", v, "string");
                    eID.addContent(eGroup);
                    eID.addContent(eItem);
                }
            }
            
            if (val.getBackgrounds().length != 0) {
                String bground = "";
                for (String b : val.getBackgrounds()) {
                    bground+= b + ", ";
                }
                bground = bground.substring(0, bground.length()-2);
                
                Element eBackgrounds = createElement("backgrounds", bground, "string");
                c.addContent(eBackgrounds);
            }
            
            classdata.addContent(c);
        }
        E.addContent(classdata);
        
        
    } 
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
                DD5E_Encounter enc = Encounters.get(encname);
                
                if (enc != null) {
                    String sanatisedName = sanatise(enc.getName());
                    
                    Element e = createElement("bat_" + sanatisedName);
                    eCat.addContent(e);
                    
                    Element eName = createElement("name", enc.getName(), "string");
                    Element eLevel = createElement("level", enc.getLevel().toString(), "number");
                    Element eXP = createElement("exp", enc.getXP().toString(), "number");
                    
                    e.addContent(eName);
                    e.addContent(eLevel);
                    e.addContent(eXP);
                    
                    Element eNPC = createElement("npclist");
                    Integer count = 0;
                    for (Map.Entry ent : enc.getNPCs().entrySet()) {
                        String k = (String) ent.getKey();
                        DD5E_EncounterNPC v = (DD5E_EncounterNPC) ent.getValue();
                        
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
    private void generateItemXML(Element E, boolean includeEquipment, boolean includeMagicItems) {
        engine.debug(1, "->generateItemXML()");
        
        //Equipment
        if (includeEquipment) {
            Element item = new Element("item");
            for (Map.Entry entry : EquipmentByType.entrySet()) {
                String key = (String) entry.getKey();
                Element iCat = createElement("category");
                String properKey = makeProperCase(key);        
                iCat.setAttribute("name", properKey + " - " + module.getName());
                iCat.setAttribute("mergeid", module.getID());
                iCat.setAttribute("baseicon", "2");
                iCat.setAttribute("decalicon", "1");
                item.addContent(iCat);
                generateEquipmentXML(iCat, key);     
            }
            E.addContent(item);
        }
        
        if (includeMagicItems) {
            //Magic Items
            Element item;
            if (E.getChild("item") != null) {
                item = E.getChild("item");
            } else {
                item = new Element("item");
                E.addContent(item);
            }
            Element iCat = createElement("category");
            iCat.setAttribute("name", "Magic Items - " + module.getName());
            iCat.setAttribute("mergeid", module.getID());
            iCat.setAttribute("baseicon", "2");
            iCat.setAttribute("decalicon", "1");
            item.addContent(iCat);
            generateMagicItemXML(iCat, false);
            
        }
        
       
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
            DD5E_Equipment val = (DD5E_Equipment) entry.getValue();
            String sanatisedName = sanatise(key);
            
            if (sType != null && !val.getType().toLowerCase().equals(sType.toLowerCase())) {
                continue;
            } 
                
            Element i = createElement(sanatisedName);
            equipment.addContent(i);
            
            Element iName = createElement("name", val.getName(), "string");
            Element iType = createElement("type", makeProperCase(val.getType()), "string");
            Element iSubType = createElement("subtype", val.getSubType(), "string");
            Element iCost = createElement("cost", val.getCost(), "string");
            Element iWeight = createElement("weight", val.getWeight().toString(), "number");
            
            i.addContent(iName);
            i.addContent(iType);
            i.addContent(iSubType);
            i.addContent(iCost);
            i.addContent(iWeight);
            
            if (val.getType().toLowerCase().contains("armor")) {
                Element iAC = createElement("ac", val.getAC().toString(), "number");
                Element iDexBonus = createElement("dexbonus", val.getDexBonus(), "string");
                Element iSpeed = createElement("speed", val.getSpeed().toString(), "number");
                Element iStealth = createElement("stealth", val.getStealth(), "string");
                i.addContent(iAC);
                i.addContent(iDexBonus);
                i.addContent(iSpeed);
                i.addContent(iStealth);
            } else if (val.getType().toLowerCase().contains("weapon")) {
                Element iDamage = createElement("damage", val.getDamage(), "string");
                Element iProperties = createElement("properties", val.getProperties(), "string");
                i.addContent(iDamage);
                i.addContent(iProperties);
            }
            
            if (!val.getText().equals("")) {
                Element iText = createElement("text", val.getText(), "formattedtext");
                i.addContent(iText);
            }
        }    
            
        
    }
    private void generateFeatXML(Element E) {
        engine.debug(1, "->generateFeatXML()");
        
        Element featdata = new Element("featdata");
        for (Map.Entry entry : Feats.entrySet()) {
            String key = (String) entry.getKey();
            DD5E_Feat val = (DD5E_Feat) entry.getValue();
            String sanatisedName = sanatise(key);
            
            Element f = createElement(sanatisedName);         
            Element fName = createElement("name", key, "string");
            Element fText = createElement("text", val.getText(), "formattedtext"); 
            
            f.addContent(fName);
            f.addContent(fText);
            
            if (!val.getPrereq().equals("")) {
                Element fPrereq = createElement("prerequisite", val.getPrereq(), "string");
                f.addContent(fPrereq);
            }
            
            featdata.addContent(f); 
        }
        E.addContent(featdata);
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
            DD5E_Image val = (DD5E_Image) entry.getValue();
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
                    
                    DD5E_ImagePin[] pins = ImagePins.get(namenoext).getShortCuts();
                    for (DD5E_ImagePin pin : pins) {
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
            DD5E_MagicItem val = (DD5E_MagicItem) entry.getValue();
            String sanatisedName = sanatise(key);
            
            Element mi = createElement(sanatisedName);
            magicitemdata.addContent(mi);
            
            Element iName = createElement("name", val.getName(), "string");
            Element iType = createElement("type", makeProperCase(val.getType()), "string");
            Element iSubType = createElement("subtype", val.getSubType(), "string");
            Element iRarity = createElement("rarity", val.getRarity(), "string");
            Element iDesc = createElement("description", val.getText(), "formattedtext");
            
            mi.addContent(iName);
            mi.addContent(iType);
            mi.addContent(iSubType);
            mi.addContent(iRarity);
            mi.addContent(iDesc);
        }

        
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
        /*
        bs = (BitSet) module.getDataItems().get("traps");
        if (bs.get(0) || bs.get(1)) {
            //Traps
            Element iCat = createElement("category");
            iCat.setAttribute("name", "Traps - " + module.getName());
            iCat.setAttribute("mergeid", module.getID());
            iCat.setAttribute("baseicon", "2");
            iCat.setAttribute("decalicon", "1");
            npc.addContent(iCat);
            generateTrapXML(iCat, false);
        }
        */
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
            DD5E_NPC val = (DD5E_NPC) entry.getValue();
            String sanatisedName = sanatise(key);
            
            Element npc = createElement(sanatisedName);
            node.addContent(npc);
            
            Element nName = createElement("name", val.getName(), "string");
            Element nType = createElement("type", val.getType(), "string");
            Element nSize = createElement("size", val.getSize(), "string");
            Element nAC = createElement("ac", val.getAC().toString(), "number");
            Element nHP = createElement("hp", val.getHP().toString(), "number");
            Element nHD = createElement("hd", val.getHD(), "string");
            Element nSpeed = createElement("speed", val.getSpeed(), "string");
            Element nSenses = createElement("senses", val.getSenses(), "string");
            npc.addContent(nName);
            npc.addContent(nType);
            npc.addContent(nSize);
            npc.addContent(nAC);
            npc.addContent(nHP);
            npc.addContent(nHD);
            npc.addContent(nSpeed);
            npc.addContent(nSenses);
            
            Element nAbilities = createElement("abilities");
            npc.addContent(nAbilities);
            
            for (Map.Entry e : val.getAbilities().entrySet()) {
                String k = (String) e.getKey();
                Integer v = (Integer) e.getValue();
                Element nStat = null;
                
                switch(k.toLowerCase()) {
                    case "str": nStat = createElement("strength"); break;
                    case "dex": nStat = createElement("dexterity"); break;
                    case "con": nStat = createElement("constitution"); break;
                    case "int": nStat = createElement("intelligence"); break;
                    case "wis": nStat = createElement("wisdom"); break;
                    case "cha": nStat = createElement("charisma"); break;
                }
                
                if (nStat != null) { 
                    nAbilities.addContent(nStat);
                }
                
                Integer mod = (v-10)/2;
                String smod = "";
                String sstat;
                
                if (mod > 0) {
                    smod = "+";
                } 
                        
                Element nScore = createElement("score", v.toString(), "number");
                Element nMod = createElement("modifier", smod + mod.toString(), "string");
                nStat.addContent(nScore);
                nStat.addContent(nMod);
            }
            
            Element nAlign = createElement("alignment", val.getAlignment(), "string");
            Element nLang = createElement("languages", val.getLanguages(), "string");
            npc.addContent(nAlign);
            npc.addContent(nLang);
                        
            if (!val.getTraits().isEmpty()) {
                Element nTraits = createElement("traits");
                npc.addContent(nTraits);
                Integer count = 0;
                for (Map.Entry e : val.getTraits().entrySet()) {
                    String k = (String) e.getKey();
                    String v = (String) e.getValue();
                    count++;
                    Element id = createElement(makeID(count)); 
                    nTraits.addContent(id);
                    
                    Element tName = createElement("name", k, "string");
                    Element tText = createElement("desc", v, "string");
                    id.addContent(tName);
                    id.addContent(tText);
                } 
            }
            
            if (!val.getReactions().isEmpty()) {
                Element nReactions = createElement("reactions");
                npc.addContent(nReactions);
                Integer count = 0;
                for (Map.Entry e : val.getReactions().entrySet()) {
                    String k = (String) e.getKey();
                    String v = (String) e.getValue();
                    count++;
                    Element id = createElement(makeID(count)); 
                    nReactions.addContent(id);
                    
                    Element tName = createElement("name", k, "string");
                    Element tText = createElement("desc", v, "string");
                    id.addContent(tName);
                    id.addContent(tText);
                } 
            }
            
            if (!val.getActions().isEmpty()) {
                Element nActions = createElement("actions");
                npc.addContent(nActions);
                Integer count = 0;
                for (Map.Entry e : val.getActions().entrySet()) {
                    String k = (String) e.getKey();
                    String v = (String) e.getValue();
                    count++;
                    Element id = createElement(makeID(count)); 
                    nActions.addContent(id);
                    
                    Element tName = createElement("name", k, "string");
                    Element tText = createElement("desc", v, "string");
                    id.addContent(tName);
                    id.addContent(tText);
                } 
            }
            
            if (!val.getToken().equals("")) {
                Element nToken = createElement("token", "tokens" + slash + module.getName() + slash + val.getToken() + "@" + module.getName(), "token");
                npc.addContent(nToken);
            }
            
            Element nLevel = createElement("level", val.getLevel().toString(), "number");
            Element nXP = createElement("xp", val.getXP().toString(), "number");
            npc.addContent(nLevel);
            npc.addContent(nXP);
            
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
            DD5E_Parcel val = (DD5E_Parcel) entry.getValue();
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
                        DD5E_Equipment item = Equipment.get(ekey);
                        
                        Element iType = createElement("type", makeProperCase(item.getType()), "string");
                        Element iSubType = createElement("subtype", item.getSubType(), "string");
                        Element iCost = createElement("cost", item.getCost(), "string");
                        Element iWeight = createElement("weight", item.getWeight().toString(), "number");

                        eid.addContent(iType);
                        eid.addContent(iSubType);
                        eid.addContent(iCost);
                        eid.addContent(iWeight);
                        
                        if (item.getType().toLowerCase().contains("armor")) {
                            Element iAC = createElement("ac", item.getAC().toString(), "number");
                            Element iDexBonus = createElement("dexbonus", item.getDexBonus(), "string");
                            Element iSpeed = createElement("speed", item.getSpeed().toString(), "number");
                            Element iStealth = createElement("stealth", item.getStealth(), "string");
                            eid.addContent(iAC);
                            eid.addContent(iDexBonus);
                            eid.addContent(iSpeed);
                            eid.addContent(iStealth);
                        } else if (item.getType().toLowerCase().contains("weapon")) {
                            Element iDamage = createElement("damage", item.getDamage(), "string");
                            Element iProperties = createElement("properties", item.getProperties(), "string");
                            eid.addContent(iDamage);
                            eid.addContent(iProperties);
                        }
                    } else if (MagicItems.containsKey(ekey)) {
                        DD5E_MagicItem item = MagicItems.get(ekey);
                        
                        Element iType = createElement("type", makeProperCase(item.getType()), "string");
                        Element iSubType = createElement("subtype", item.getSubType(), "string");
                        Element iRarity = createElement("cost", item.getRarity(), "string");
                        Element iDesc = createElement("description", item.getText(), "formattedtext");

                        eid.addContent(iType);
                        eid.addContent(iSubType);
                        eid.addContent(iRarity);
                        eid.addContent(iDesc);    
                    }
                }
            }
        }
        E.addContent(parcel);
    }
    private void generateRaceXML(Element E) {
        engine.debug(1, "->generateRaceXML()");
        
        Element racedata = createElement("racedata");
        
        for (Map.Entry entry : Races.entrySet()) {
            String key = (String) entry.getKey();
            DD5E_Race val = (DD5E_Race) entry.getValue();
            String sanatisedName = sanatise(key);
            
            Element race = createElement(sanatisedName);
            racedata.addContent(race);
            
            Element rName = createElement("name", key, "string");
            Element rText = createElement("text", val.getText(), "formattedtext");
            race.addContent(rName);
            race.addContent(rText);
            
            if (!val.getTraits().isEmpty()) {
                Element rTraits = createElement("traits");
                race.addContent(rTraits);
                
                for (Map.Entry e : val.getTraits().entrySet()) {
                    String k = (String) e.getKey();
                    String v = (String) e.getValue();
                    String sKey = sanatise(k);
                    Element eID = createElement(sKey);
                    rTraits.addContent(eID);
                    
                    Element eName = createElement("name", k, "string");
                    Element eText = createElement("text", v, "formattedtext");
                    eID.addContent(eName);
                    eID.addContent(eText);
                }
            }
            
            if (!val.getSubRaces().isEmpty()) {
                Element rSubRaces = createElement("subraces");
                race.addContent(rSubRaces);
                
                for (Map.Entry e : val.getSubRaces().entrySet()) {
                    String k = (String) e.getKey();
                    DD5E_SubRace v = (DD5E_SubRace) e.getValue();
                    String sKey = sanatise(k);
                    Element eID = createElement(sKey);
                    rSubRaces.addContent(eID);
                    
                    Element eName = createElement("name", k, "string");
                    Element eText = createElement("text", v.getText(), "formattedtext");
                    eID.addContent(eName);
                    eID.addContent(eText);
                    
                    if (!v.getTraits().isEmpty()) {
                        Element rTraits = createElement("traits");
                        eID.addContent(rTraits);

                        for (Map.Entry se : v.getTraits().entrySet()) {
                            String sek = (String) se.getKey();
                            String sev = (String) se.getValue();
                            String sK = sanatise(sek);
                            Element seID = createElement(sK);
                            rTraits.addContent(seID);

                            Element eN = createElement("name", sek, "string");
                            Element eT = createElement("text", sev, "formattedtext");
                            seID.addContent(eN);
                            seID.addContent(eT);
                        }
                    }
                }
            }
        }
        E.addContent(racedata);
    } 
    private void generateReferenceManualXML(Element E) {
        engine.debug(1, "->generateReferenceManualXML()");
        
        Integer chapterCount = -1, subchapterCount, pageCount;
        Element refmanualdata = createElement("refmanualdata");
        
        for (Map.Entry e1 : ReferenceManual.getChapters().entrySet()) {
            String chapterKey = (String) e1.getKey();
            DD5E_ReferenceChapter chapter = (DD5E_ReferenceChapter) e1.getValue();
            chapterCount++;
            subchapterCount = -1;
            for (Map.Entry e2 : chapter.getSubChapters().entrySet()) {
                String subchapterKey = (String) e2.getKey();
                DD5E_ReferenceSubChapter subchapter = (DD5E_ReferenceSubChapter) e2.getValue();
                subchapterCount++;
                pageCount = -1;
                for (Map.Entry e3 : subchapter.getPages().entrySet()) {
                    String pageKey = (String) e3.getKey();
                    DD5E_ReferencePage page = (DD5E_ReferencePage) e3.getValue();
                    pageCount++;
                    String chapTag = padID(chapterCount);
                    String subchapTag = padID(subchapterCount);
                    String pageTag = padID(pageCount);
                    Element id = createElement("refpage_" + chapTag + subchapTag + pageTag + sanatise(pageKey));
                    refmanualdata.addContent(id);
                    
                    Element iName = createElement("name", pageKey, "string");
                    Element iGroup = createElement("group", chapterKey, "string");
                    Element iSubGroup = createElement("subgroup", subchapterKey, "string");
                    Element iText = createElement("text", page.getText(), "formattedtext");
                    
                    id.addContent(iName);
                    id.addContent(iGroup);
                    id.addContent(iSubGroup);
                    id.addContent(iText);
                }
            }
        }
        E.addContent(refmanualdata);
    }
    private void generateSkillXML(Element E) {
        engine.debug(1, "->generateSkillXML()");
        
        Element skilldata = createElement("skilldata");
        for (Map.Entry entry : Skills.entrySet()) {
            String key = (String) entry.getKey();
            DD5E_Skill val = (DD5E_Skill) entry.getValue();
            String sanatisedName = sanatise(key);
            
            Element skill = createElement(sanatisedName);
            skilldata.addContent(skill);
            
            Element sName = createElement("name", key, "string");
            Element sStat = createElement("stat", val.getStat(), "string");
            Element sText = createElement("text", val.getText(), "formattedtext");
            
            skill.addContent(sName);
            skill.addContent(sStat);
            skill.addContent(sText);
        }
        
        E.addContent(skilldata);
    } 
    private void generateSpellXML(Element E) {
        engine.debug(1, "->generateSpellXML()");
        
        Element spelldata = createElement("spelldata");
        
        for (String Spell : sortedSpells) {
            if (Spells.containsKey(Spell)) {
                String spellKey = Spells.get(Spell).getName();
                DD5E_Spell spell = Spells.get(Spell);
                String sanatisedName = sanatise(spellKey);

                Element s = createElement(sanatisedName);
                spelldata.addContent(s);

                Element sName = createElement("name", spellKey, "string");
                Element sDesc = createElement("description", spell.getText(), "formattedtext");
                Element sLevel = createElement("level", spell.getLevel().toString(), "number");
                Element sSchool = createElement("school", spell.getSchool(), "string");
                Element sSource = createElement("source", spell.getSource(), "string");
                Element sCTime = createElement("castingtime", spell.getCastingTime(), "string");
                Element sRange = createElement("range", spell.getRange(), "string");
                Element sDuration = createElement("duration", spell.getDuration(), "string");

                s.addContent(sName);
                s.addContent(sDesc);
                s.addContent(sLevel);
                s.addContent(sSchool);
                s.addContent(sSource);
                s.addContent(sCTime);
                s.addContent(sRange);
                s.addContent(sDuration);

                if (!spell.getDescriptors().equals("")) {
                    Element sComponents = createElement("components", spell.getComponents(), "string");
                    s.addContent(sComponents);
                }
                if (!spell.getComponents().equals("")) {
                    Element sDescriptors = createElement("descriptors", spell.getDescriptors(), "string");
                    s.addContent(sDescriptors);
                }   
            }
        }
        E.addContent(spelldata);
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
                DD5E_Story enc = Stories.get(encname);
                
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
                DD5E_Table table = Tables.get(tablename);
                
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
                    for (DD5E_TableRow tableRow : table.getTableRows()) {
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
    
    //Private generate windowlist XML data methods
    private void generateBackgroundXMLLists(Element E) {
        engine.debug(1, "->generateBackgroundXMLLists()");
        
        Element backgroundlists = createElement("backgroundlists");
        Element byletter = createElement("byletter");
        backgroundlists.addContent(byletter);
        Element description = createElement("description", "Backgrounds", "string");
        byletter.addContent(description);
        Element groups = createElement("groups");
        byletter.addContent(groups);
        
        for (Map.Entry  entry : BackgroundsByLetter.entrySet()) {
            String key = (String) entry.getKey();
            String[] val = (String[]) entry.getValue();
            
            Element type = createElement("typeletter" + key);
            groups.addContent(type);
            
            Element desc = createElement("description", key, "string");
            type.addContent(desc);
            
            Element index = createElement("index");
            type.addContent(index);
            
            for (String backgroundName : val) {
                Element bName = createElement(sanatise(backgroundName));
                index.addContent(bName);
                
                Element bLink = createElement("link", "", "windowreference");
                bName.addContent(bLink);
                
                Element linkClass = createElement("class", "reference_background");
                bLink.addContent(linkClass);
                
                Element linkRecordname = createElement("recordname", "reference" + module.getID() + ".backgrounddata." + sanatise(backgroundName) + "@" + module.getName());
                bLink.addContent(linkRecordname);
                
                Element ldesc = createElement("description");
                bLink.addContent(ldesc);
                Element lfield = createElement("field", "name");
                ldesc.addContent(lfield);
                
                Element source = createElement("source", "", "string");
                bName.addContent(source);
            }  
        } 
        E.addContent(backgroundlists);
        
        ModuleLibraryEntry libEntry = new ModuleLibraryEntry("Backgrounds", "reference_colindex", "reference" + module.getID() + ".backgroundlists.byletter");
        library.addEntry("Backgrounds", libEntry);
        
        
    }
    private void generateClassXMLLists(Element E) {
        engine.debug(1, "->generateClassXMLLists()");
        
        Element classlists = createElement("classlists");
        Element byletter = createElement("byletter");
        classlists.addContent(byletter);
        Element description = createElement("description", "Classes", "string");
        byletter.addContent(description);
        Element groups = createElement("groups");
        byletter.addContent(groups);
        
        for (Map.Entry  entry : ClassesByLetter.entrySet()) {
            String key = (String) entry.getKey();
            String[] val = (String[]) entry.getValue();
            
            Element type = createElement("typeletter" + key);
            groups.addContent(type);
            
            Element desc = createElement("description", key, "string");
            type.addContent(desc);
            
            Element index = createElement("index");
            type.addContent(index);
            
            for (String className : val) {
                Element cName = createElement(sanatise(className));
                index.addContent(cName);
                
                Element cLink = createElement("link", "", "windowreference");
                cName.addContent(cLink);
                
                Element linkClass = createElement("class", "reference_class");
                cLink.addContent(linkClass);
                
                Element linkRecordname = createElement("recordname", "reference" + module.getID() + ".classdata." + sanatise(className) + "@" + module.getName());
                cLink.addContent(linkRecordname);
                
                Element ldesc = createElement("description");
                cLink.addContent(ldesc);
                Element lfield = createElement("field", "name");
                ldesc.addContent(lfield);
                
                Element source = createElement("source", "", "string");
                cName.addContent(source);
            }  
        } 
        E.addContent(classlists);
        
        ModuleLibraryEntry libEntry = new ModuleLibraryEntry("Classes", "reference_colindex", "reference" + module.getID() + ".classlists.byletter");
        library.addEntry("Classes", libEntry);  
    }
    private void generateEquipmentXMLLists(Element E) {
        engine.debug(1, "->generateEquipmentXMLLists()");
        
        Element equipmentlists = createElement("equipmentlists");
        Element equipment = createElement("equipment");
        equipmentlists.addContent(equipment);
        Element eName = createElement("name", "Equipment", "string");
        equipment.addContent(eName);
        Element eIndex = createElement("index");
        equipment.addContent(eIndex);
        
        //Equipment Index
        Integer count = 0;
        for (Map.Entry entry : EquipmentByType.entrySet()) {
            String key = (String) entry.getKey();
            String sanatisedKey = sanatise(key);
            count++;
            
            Element eID = createElement(makeID(count));
            eIndex.addContent(eID);
            
            Element elistlink = createElement("listlink", "", "windowreference");
            eID.addContent(elistlink);
            
            Element lName = createElement("name", key, "string");
            eID.addContent(lName);
            
            
            Element linkClass = createElement("class", "reference_" + sanatisedKey + "table");
            elistlink.addContent(linkClass);

            Element linkRecordname = createElement("recordname", "reference" + module.getID() + ".equipmentlists." + sanatise(key) + "table@" + module.getName());
            elistlink.addContent(linkRecordname);
        }    
         
        for (Map.Entry entry : EquipmentByType.entrySet()) {
            String key = (String) entry.getKey();
            String sanatisedKey = sanatise(key);
            String properKey = makeProperCase(key);
        
            Element list = createElement(sanatisedKey + "table");
            equipmentlists.addContent(list);
            
            Element ldesc = createElement("description", properKey + " Table", "string");
            Element lgroups = createElement("groups");
            list.addContent(ldesc);
            list.addContent(lgroups);
            
            Integer subtypecount = 0;
            for (Map.Entry e1 : EquipmentSubTypesByType.entrySet()) {
                String subtype = (String) e1.getKey();
                String type = (String) e1.getValue();
                String properSubType = makeProperCase(subtype);
                
                String sectionNumber = "";
                if (subtypecount < 10) {
                    sectionNumber = "00" + subtypecount;
                } else if (subtypecount < 100) {
                    sectionNumber = "0" + subtypecount;
                } else if (subtypecount < 1000) {
                    sectionNumber = "" + subtypecount; 
                }
                
                if (type.toLowerCase().equals(key.toLowerCase())) {
                    
                    Element section = createElement("section" + sectionNumber);
                    lgroups.addContent(section);
                    
                    Element sdesc = createElement("description", properSubType, "string");
                    section.addContent(sdesc);
                    
                    Element sequipment = createElement("equipment");
                    section.addContent(sequipment);
                    
                    for (Map.Entry e2 : Equipment.entrySet()) {
                        String k = (String) e2.getKey();
                        DD5E_Equipment v = (DD5E_Equipment) e2.getValue();
                        String sanatisedName = sanatise(k);
                        
                        if (v.getType().toLowerCase().equals(key.toLowerCase()) && v.getSubType().toLowerCase().equals(subtype.toLowerCase())) {
                            Element seequip = createElement(sanatisedName);
                            sequipment.addContent(seequip);
                            
                            String linkClass = "equipment";
                       
                            Element link = createElement("link", "", "windowreference");
                            seequip.addContent(link);
                            
                            Element iName = createElement("name", v.getName(), "string");
                            Element iCost = createElement("cost", v.getCost(), "string");
                            Element iWeight = createElement("weight", v.getWeight().toString(), "number");

                            seequip.addContent(iName);
                            seequip.addContent(iCost);
                            seequip.addContent(iWeight);

                            if (v.getType().toLowerCase().contains("armor")) {
                                Element iAC = createElement("ac", v.getAC().toString(), "string");
                                Element iDexBonus = createElement("dexbonus", v.getDexBonus(), "string");
                                Element iSpeed = createElement("speed", v.getSpeed().toString(), "string");
                                Element iStealth = createElement("stealth", v.getStealth(), "string");
                                seequip.addContent(iAC);
                                seequip.addContent(iDexBonus);
                                seequip.addContent(iSpeed);
                                seequip.addContent(iStealth);
                                linkClass = "armor";
                            } else if (v.getType().toLowerCase().contains("weapon")) {
                                Element iDamage = createElement("damage", v.getDamage(), "string");
                                Element iProperties = createElement("properties", v.getProperties(), "string");
                                seequip.addContent(iDamage);
                                seequip.addContent(iProperties);
                                linkClass = "weapon";
                            } 

                            Element lclass = createElement("class", "reference_" + linkClass);
                            Element lrecord = createElement("recordname", "reference" + module.getID() + ".equipmentdata." + sanatisedName + "@" + module.getName());
                            link.addContent(lclass);
                            link.addContent(lrecord);
                        }
                    }
                    
                    subtypecount++;
                }
                
            }
        }
        
        E.addContent(equipmentlists);
        
        ModuleLibraryEntry libEntry = new ModuleLibraryEntry("Equipment", "reference_index", "reference" + module.getID() + ".equipmentlists.equipment");
        library.addEntry("Equipment", libEntry);  
    }
    private void generateFeatXMLLists(Element E) {
        engine.debug(1, "->generateFeatXMLLists()");
        
        Element lists = createElement("featlists");
        Element byletter = createElement("byletter");
        lists.addContent(byletter);
        Element description = createElement("description", "Feats", "string");
        byletter.addContent(description);
        Element groups = createElement("groups");
        byletter.addContent(groups);
        
        for (Map.Entry  entry : FeatsByLetter.entrySet()) {
            String key = (String) entry.getKey();
            String[] val = (String[]) entry.getValue();
            
            Element type = createElement("letter" + key);
            groups.addContent(type);
            
            Element desc = createElement("description", key, "string");
            type.addContent(desc);
            
            Element index = createElement("feats");
            type.addContent(index);
            
            for (String itemName : val) {
                Element nodeName = createElement(sanatise(itemName));
                index.addContent(nodeName);
                
                Element iLink = createElement("link", "", "windowreference");
                nodeName.addContent(iLink);
                
                Element linkClass = createElement("class", "reference_feat");
                iLink.addContent(linkClass);
                
                Element linkRecordname = createElement("recordname", "reference" + module.getID() + ".featdata." + sanatise(itemName) + "@" + module.getName());
                iLink.addContent(linkRecordname);
                
                Element ldesc = createElement("description");
                iLink.addContent(ldesc);
                Element lfield = createElement("field", "name");
                ldesc.addContent(lfield);
                
                Element iName = createElement("name", itemName, "string");
                nodeName.addContent(iName);
                
                Element source = createElement("source", "", "string");
                nodeName.addContent(source);
            }  
        } 
        E.addContent(lists);
        
        ModuleLibraryEntry libEntry = new ModuleLibraryEntry("Feats", "reference_featlist", "reference" + module.getID() + ".featlists.byletter");
        library.addEntry("Feats", libEntry);
        
        
    }
    private void generateMagicItemXMLLists(Element E) {
        engine.debug(1, "->generateMagicItemXMLLists()");
        
        Element magicitemlists = createElement("magicitemlists");
        Element bytype = createElement("bytype");
        magicitemlists.addContent(bytype);
        Element miDescription = createElement("description", "Magic Items", "string");
        bytype.addContent(miDescription);
        Element miGroups = createElement("groups");
        bytype.addContent(miGroups);
        
        for (Map.Entry entry : MagicItemsByType.entrySet()) {
            String key = (String) entry.getKey();
            String sanatisedKey = sanatise(key);
            String properKey = makeProperCase(key);
        
            Element list = createElement("type" + sanatisedKey);
            miGroups.addContent(list);
            
            Element ldesc = createElement("description", properKey, "string");
            Element lindex = createElement("index");
            list.addContent(ldesc);
            list.addContent(lindex);
            
            for (Map.Entry e2 : MagicItems.entrySet()) {
                String k = (String) e2.getKey();
                DD5E_MagicItem v = (DD5E_MagicItem) e2.getValue();
                String sanatisedName = sanatise(k);
                
                if (v.getType().toLowerCase().equals(key.toLowerCase())) {
                    Element mi = createElement(sanatisedName);
                    lindex.addContent(mi);
                    
                    Element link = createElement("link", "", "windowreference");
                    mi.addContent(link);
                    
                    Element lclass = createElement("class", "reference_magicitem");
                    Element lrecord = createElement("recordname", "reference" + module.getID() + ".magicitemdata." + sanatisedName + "@" + module.getName());
                    Element ldescription = createElement("description");
                    link.addContent(lclass);
                    link.addContent(lrecord);
                    link.addContent(ldescription);
                    
                    Element dfield = createElement("field", "name");
                    ldescription.addContent(dfield);
                    
                    Element source = createElement("source", v.getType());
                    mi.addContent(source);
                }
            }
        }
        E.addContent(magicitemlists);
        ModuleLibraryEntry libEntry = new ModuleLibraryEntry("Magic Items", "reference_colindex", "reference" + module.getID() + ".magicitemlists.bytype");
        library.addEntry("Magic Items", libEntry);
    }
    private void generateNPCXMLLists(Element E) {
        engine.debug(1, "->generateNPCXMLLists()");
        
        Element npclists = createElement("npclists");
        Element npcs = createElement("npcs");
        npclists.addContent(npcs);
        Element eName = createElement("name", "NPCs", "string");
        npcs.addContent(eName);
        Element eIndex = createElement("index");
        npcs.addContent(eIndex);
        
        //npc index list
        Element id = createElement("id-00001");    
        eIndex.addContent(id);
        Element nName = createElement("name", "NPCs - Alphabetical Index", "string");
        id.addContent(nName);
        Element link = createElement("listlink", "", "windowreference");
        id.addContent(link);
        Element lClass = createElement("class", "reference_colindex");
        link.addContent(lClass);
        Element lRecordname = createElement("recordname", "reference" + module.getID() + ".npclists.byletter@" + module.getName());
        link.addContent(lRecordname);
        
        id = createElement("id-00002");    
        eIndex.addContent(id);
        nName = createElement("name", "NPCs - Level Index", "string");
        id.addContent(nName);
        link = createElement("listlink", "", "windowreference");
        id.addContent(link);
        lClass = createElement("class", "reference_colindex");
        link.addContent(lClass);
        lRecordname = createElement("recordname", "reference" + module.getID() + ".npclists.bylevel@" + module.getName());
        link.addContent(lRecordname);
        
        id = createElement("id-00003");    
        eIndex.addContent(id);
        nName = createElement("name", "NPCs - Class Index", "string");
        id.addContent(nName);
        link = createElement("listlink", "", "windowreference");
        id.addContent(link);
        lClass = createElement("class", "reference_colindex");
        link.addContent(lClass);
        lRecordname = createElement("recordname", "reference" + module.getID() + ".npclists.bytype@" + module.getName());
        link.addContent(lRecordname);

        //byletter list
        Element byletter = createElement("byletter");
        npclists.addContent(byletter);
        Element description = createElement("description", "NPCs", "string");
        byletter.addContent(description);
        Element groups = createElement("groups");
        byletter.addContent(groups);
        
        for (Map.Entry entry : NPCsByLetter.entrySet()) {
            String key = (String) entry.getKey();
            String[] val = (String[]) entry.getValue();
            
            Element type = createElement("typeletter" + key);
            groups.addContent(type);
            
            Element desc = createElement("description", makeProperCase(key), "string");
            type.addContent(desc);
            
            Element index = createElement("index");
            type.addContent(index);
            
            for (String className : val) {
                Element cName = createElement(sanatise(className));
                index.addContent(cName);
                
                Element cLink = createElement("link", "", "windowreference");
                cName.addContent(cLink);
                
                lClass = createElement("class", "reference_npc");
                cLink.addContent(lClass);
                
                lRecordname = createElement("recordname", "reference" + module.getID() + ".npcdata." + sanatise(className) + "@" + module.getName());
                cLink.addContent(lRecordname);
                
                Element ldesc = createElement("description");
                cLink.addContent(ldesc);
                Element lfield = createElement("field", "name");
                ldesc.addContent(lfield);
                
                Element source = createElement("source", "", "number");
                cName.addContent(source);
            }  
        } 
        
        //bylevel list
        Element bylevel = createElement("bylevel");
        npclists.addContent(bylevel);
        Element ldescription = createElement("description", "NPCs", "string");
        bylevel.addContent(ldescription);
        Element lgroups = createElement("groups");
        bylevel.addContent(lgroups);
        
        for (Map.Entry entry : NPCsByLevel.entrySet()) {
            Integer key = (Integer) entry.getKey();
            String[] val = (String[]) entry.getValue();
            Element type;
            if ( key < 10) {
                type = createElement("level0" + key.toString());
            } else {
                type = createElement("level" + key.toString());
            }
            
            lgroups.addContent(type);
            
            Element desc = createElement("description", key.toString(), "string");
            type.addContent(desc);
            
            Element index = createElement("index");
            type.addContent(index);
            
            for (String className : val) {
                Element cName = createElement(sanatise(className));
                index.addContent(cName);
                
                Element cLink = createElement("link", "", "windowreference");
                cName.addContent(cLink);
                
                lClass = createElement("class", "reference_npc");
                cLink.addContent(lClass);
                
                lRecordname = createElement("recordname", "reference" + module.getID() + ".npcdata." + sanatise(className) + "@" + module.getName());
                cLink.addContent(lRecordname);
                
                Element ldesc = createElement("description");
                cLink.addContent(ldesc);
                Element lfield = createElement("field", "name");
                ldesc.addContent(lfield);
                
                Element source = createElement("source", "", "number");
                cName.addContent(source);
            }  
        }
        
        //bytype list
        Element bytype = createElement("bytype");
        npclists.addContent(bytype);
        Element tdescription = createElement("description", "NPCs", "string");
        bytype.addContent(tdescription);
        Element tgroups = createElement("groups");
        bytype.addContent(tgroups);
        for (Map.Entry entry : NPCsByType.entrySet()) {
            String key = (String) entry.getKey();
            String sanatisedKey = sanatise(key);
            String properKey = makeProperCase(key);
        
            Element list = createElement("type" + sanatisedKey);
            tgroups.addContent(list);
            
            Element ldesc = createElement("description", properKey, "string");
            Element lindex = createElement("index");
            list.addContent(ldesc);
            list.addContent(lindex);
            
            for (Map.Entry e2 : NPCs.entrySet()) {
                String k = (String) e2.getKey();
                DD5E_NPC v = (DD5E_NPC) e2.getValue();
                String sanatisedName = sanatise(k);
                
                if (v.getType().toLowerCase().equals(key.toLowerCase())) {
                    Element npc = createElement(sanatisedName);
                    lindex.addContent(npc);
                    
                    link = createElement("link", "", "windowreference");
                    npc.addContent(link);
                    
                    Element lclass = createElement("class", "reference_npc");
                    Element lrecord = createElement("recordname", "reference" + module.getID() + ".npcdata." + sanatisedName + "@" + module.getName());
                    ldescription = createElement("description");
                    link.addContent(lclass);
                    link.addContent(lrecord);
                    link.addContent(ldescription);
                    
                    Element dfield = createElement("field", "name");
                    ldescription.addContent(dfield);
                    
                    Element source = createElement("source", v.getType());
                    npc.addContent(source);
                }
            }
        }
        E.addContent(npclists);
        ModuleLibraryEntry libEntry = new ModuleLibraryEntry("NPCs", "reference_index", "reference" + module.getID() + ".npclists.npcs");
        library.addEntry("NPCs", libEntry);
    }
    private void generateRaceXMLLists(Element E) {
        engine.debug(1, "->generateRaceXMLLists()");
        
        Element racelists = createElement("racelists");
        Element byletter = createElement("byletter");
        racelists.addContent(byletter);
        Element description = createElement("description", "Races", "string");
        byletter.addContent(description);
        Element groups = createElement("groups");
        byletter.addContent(groups);
        
        for (Map.Entry  entry : RacesByLetter.entrySet()) {
            String key = (String) entry.getKey();
            String[] val = (String[]) entry.getValue();
            
            Element type = createElement("typeletter" + key);
            groups.addContent(type);
            
            Element desc = createElement("description", key, "string");
            type.addContent(desc);
            
            Element index = createElement("index");
            type.addContent(index);
            
            for (String className : val) {
                Element cName = createElement(sanatise(className));
                index.addContent(cName);
                
                Element cLink = createElement("link", "", "windowreference");
                cName.addContent(cLink);
                
                Element linkClass = createElement("class", "reference_race");
                cLink.addContent(linkClass);
                
                Element linkRecordname = createElement("recordname", "reference" + module.getID() + ".racedata." + sanatise(className) + "@" + module.getName());
                cLink.addContent(linkRecordname);
                
                Element ldesc = createElement("description");
                cLink.addContent(ldesc);
                Element lfield = createElement("field", "name");
                ldesc.addContent(lfield);
                
                Element source = createElement("source", "", "string");
                cName.addContent(source);
            }  
        } 
        E.addContent(racelists);
        
        ModuleLibraryEntry libEntry = new ModuleLibraryEntry("Races", "reference_colindex", "reference" + module.getID() + ".racelists.byletter");
        library.addEntry("Races", libEntry);  
    }  
    private void generateReferenceManualIndex(Element E) {
        engine.debug(1, "->generateReferenceManualIndex()");    
        
        Integer chapterCount = -1, subchapterCount, pageCount;
        Element refmanualindex = createElement("refmanualindex");
        Element chapters = createElement("chapters");
        refmanualindex.addContent(chapters);
         
        for (Map.Entry e1 : ReferenceManual.getChapters().entrySet()) {
            String chapterKey = (String) e1.getKey();
            DD5E_ReferenceChapter chapterVal = (DD5E_ReferenceChapter) e1.getValue();
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
                DD5E_ReferenceSubChapter subchapterVal = (DD5E_ReferenceSubChapter) e2.getValue();
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
                    DD5E_ReferencePage pageVal = (DD5E_ReferencePage) e3.getValue();
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
    private void generateSkillXMLLists(Element E) {
        engine.debug(1, "->generateSkillXMLLists()");
        
        Element skilllists = createElement("skilllists");
        Element byletter = createElement("byletter");
        skilllists.addContent(byletter);
        Element description = createElement("description", "Skills", "string");
        byletter.addContent(description);
        Element groups = createElement("groups");
        byletter.addContent(groups);
        
        for (Map.Entry  entry : SkillsByLetter.entrySet()) {
            String key = (String) entry.getKey();
            String[] val = (String[]) entry.getValue();
            
            Element type = createElement("typeletter" + key);
            groups.addContent(type);
            
            Element desc = createElement("description", key, "string");
            type.addContent(desc);
            
            Element index = createElement("index");
            type.addContent(index);
            
            for (String className : val) {
                Element cName = createElement(sanatise(className));
                index.addContent(cName);
                
                Element cLink = createElement("link", "", "windowreference");
                cName.addContent(cLink);
                
                Element linkClass = createElement("class", "reference_skill");
                cLink.addContent(linkClass);
                
                Element linkRecordname = createElement("recordname", "reference" + module.getID() + ".skilldata." + sanatise(className) + "@" + module.getName());
                cLink.addContent(linkRecordname);
                
                Element ldesc = createElement("description");
                cLink.addContent(ldesc);
                Element lfield = createElement("field", "name");
                ldesc.addContent(lfield);
                
                Element source = createElement("source", "", "string");
                cName.addContent(source);
            }  
        } 
        E.addContent(skilllists);
        
        ModuleLibraryEntry libEntry = new ModuleLibraryEntry("Skills", "reference_colindex", "reference" + module.getID() + ".skilllists.byletter");
        library.addEntry("Skills", libEntry);  
    }  
    private void generateSpellXMLLists(Element E) {
        engine.debug(1, "->generateSpellXMLLists()");
        
        Element spelllists = createElement("spelllists");
        Element spells = createElement("spells");
        spelllists.addContent(spells);
        Element eName = createElement("name", "Spells", "string");
        spells.addContent(eName);
        Element eIndex = createElement("index");
        spells.addContent(eIndex);
        
        //Spells Index
        Integer count = 0;
        for (Map.Entry entry : SpellsByClass.entrySet()) {
            String key = (String) entry.getKey();
            count++;
            
            Element eID = createElement(makeID(count));
            eIndex.addContent(eID);
            
            Element elistlink = createElement("listlink", "", "windowreference");
            eID.addContent(elistlink);
            
            Element lName = createElement("name", key, "string");
            eID.addContent(lName);
            
            
            Element linkClass = createElement("class", "reference_colindex");
            elistlink.addContent(linkClass);

            Element linkRecordname = createElement("recordname", "reference" + module.getID() + ".spelllists." + sanatise(key) + "@" + module.getName());
            elistlink.addContent(linkRecordname);
        }    
         
        for (Map.Entry entry : SpellsByClass.entrySet()) {
            String key = (String) entry.getKey();
            String sanatisedKey = sanatise(key);
            String properKey = makeProperCase(key);
        
            Element list = createElement(sanatisedKey);
            spelllists.addContent(list);
            
            Element ldesc = createElement("description", properKey + " Spells", "string");
            Element lgroups = createElement("groups");
            list.addContent(ldesc);
            list.addContent(lgroups);
            
            for (Map.Entry e1 : SpellLevelsByClass.entrySet()) {
                String spellclass = (String) e1.getKey();
                String[] levels = (String[]) e1.getValue();
                String properSubType = makeProperCase(spellclass);
                
                if (key.toLowerCase().equals(spellclass.toLowerCase())) {
                
                    for (String level : levels) {
                        Element l = createElement("level" + level);
                        lgroups.addContent(l);

                        String title = "Level " + level + " Spells";
                        if (level.equals("0")) {
                            title = "Cantrips";
                        }
                        Element ldescription = createElement("description", title, "string");
                        l.addContent(ldescription);

                        Element lindex = createElement("index");
                        l.addContent(lindex);

                        for (Map.Entry e2 : Spells.entrySet()) {
                            String k = (String) e2.getKey();
                            DD5E_Spell v = (DD5E_Spell) e2.getValue();
                            String sanatisedName = sanatise(k);

                            if (v.getSource().toLowerCase().contains(spellclass.toLowerCase()) && v.getLevel().toString().toLowerCase().equals(level.toLowerCase())) {
                                Element s = createElement(sanatisedName);
                                lindex.addContent(s);

                                Element link = createElement("link", "", "windowreference");
                                s.addContent(link);

                                Element lclass = createElement("class", "reference_spell");
                                Element lrecord = createElement("recordname", "reference" + module.getID() + ".spelldata." + sanatisedName + "@" + module.getName());
                                link.addContent(lclass);
                                link.addContent(lrecord);

                                Element ldesc1 = createElement("description");
                                link.addContent(ldesc1);
                                Element ldescname = createElement("field", "name");
                                ldesc1.addContent(ldescname);

                                Element source = createElement("source", "Class " + properKey);
                                s.addContent(source);
                            }
                        }
                    } 
                }
            }
        }
        E.addContent(spelllists);
        
        ModuleLibraryEntry libEntry = new ModuleLibraryEntry("Spells", "reference_index", "reference" + module.getID() + ".spelllists.spells");
        library.addEntry("Spells", libEntry);  
    }
    
    //Private Archive generation routines
    private void generateNPCArchive(ObjectContainer objects) {
        
        for (Map.Entry entry : NPCs.entrySet()) {
            final String key = (String) entry.getKey();
            DD5E_NPC val = (DD5E_NPC) entry.getValue();
            List<DD5E_NPC> npcs = objects.query(new Predicate<DD5E_NPC>() {
                @Override
                public boolean match(DD5E_NPC obj) {
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
            DD5E_MagicItem val = (DD5E_MagicItem) entry.getValue();
            List<DD5E_MagicItem> mis = objects.query(new Predicate<DD5E_MagicItem>() {
                @Override
                public boolean match(DD5E_MagicItem obj) {
                    return obj.getName().equals(key);
                }
            }); 
            if (mis.isEmpty()) {
                objects.store(val);
            }
        }
    }

   
}

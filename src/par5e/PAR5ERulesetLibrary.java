/*
 * 
 * PAR5E Fantasy Grounds Ruleset Module Parser Tool
 * Developed by Zeus. Copyright 2014
 * 
 */


package par5e;

import com.db4o.ObjectContainer;
import par5e.utils.RegEx;
import java.util.BitSet;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jdom2.Element;
import par5e.utils.OSValidator;

/**
 *
 * @author Zeus
 */
public class PAR5ERulesetLibrary {
    //Variables
    
    public String name;
    public String ruleset;
    public LinkedHashMap dataItems;
    public ConsoleWriter con;
    public PAR5EEngine engine;
    public Module module;
    public ModuleLibrary library;
    
    public final BitSet cbs = new BitSet(3);
    public final BitSet rbs = new BitSet(3);
    public final BitSet dbs = new BitSet(3);
    
    public final BitSet cdbs = new BitSet(3);
    public final BitSet rdbs = new BitSet(3);
    
    public final BitSet crbs = new BitSet(3);
    public final BitSet crdbs = new BitSet(3);
    
    
    
    public final String normal = "normal";
    public final String info = "info";
    public final String warning = "warning";
    public final String standard = "standard";
    
    public String slash = "/";
    
    private final OSValidator os;
    
    //Constructor
    public PAR5ERulesetLibrary() {
        dataItems = new LinkedHashMap();
        library = new ModuleLibrary();
        os = new OSValidator();
        
        if (os.isWindows()) {
            slash = "\\";
        }
    }
 
    //Basic Methods
    public String getName() {
        return name;
    }   
    public void setName(String newname) {
        name = newname;
    }
    public String getRuleset() {
        return ruleset;
    } 
    public void setRuleset(String newruleset) {
        ruleset = newruleset;
    }
    public void setConsole(ConsoleWriter aCon) {
        con = aCon;
    }
    public void setEngine(PAR5EEngine aPE) {
        engine = aPE;
    }
    public void setModule(Module aModule) {
        module = aModule;
    }

    //Data Item Routines
    public void initDataItems(LinkedHashMap dItems) {
        dataItems = dItems;    
    }    
    public LinkedHashMap getDataItems() {
        return dataItems;    
    }  
    
    public void initialiseBS() {
        cbs.set(0, true);
        cbs.set(1, false);
        cbs.set(2, false);
        
        rbs.set(0, false);
        rbs.set(1, true);
        rbs.set(2, false);

        dbs.set(0, false);
        dbs.set(1, false);
        dbs.set(2, true);
        
        cdbs.set(0, true);
        cdbs.set(1, false);
        cdbs.set(2, true);
        
        rdbs.set(0, false);
        rdbs.set(1, true);
        rdbs.set(2, true);
        
        crbs.set(0, true);
        crbs.set(1, true);
        crbs.set(2, false);

        crdbs.set(0, true);
        crdbs.set(1, true);
        crdbs.set(2, true);
    }
    
    // FormattedText Helper Routines
    public String makeFormattedText(String aString) {
	if (!aString.isEmpty()) {
		String sText;
		String sData = trimTxt(aString);
		Integer dNumTableCols;
                RegEx regex;
		
		//Parse string data and return appropriate formattedtext 
		regex = new RegEx("\\A\\#h\\;(.*)", sData);
                if (regex.find()) {
                    engine.debug(3, "Header");
                    return "<h>" + regex.group(1) + "</h>";
		}
                regex = new RegEx("\\A\\#p\\;(.*)", sData);
                if (regex.find()) {
                    engine.debug(3, "Paragraph");
                    return "<p>" + regex.group(1) + "</p>";
		}
                regex = new RegEx("\\A\\#b\\;(.*)", sData);
                if (regex.find()) {
                    engine.debug(3, "Bold Paragraph");
                    return "<p><b>" + regex.group(1) + "</b></p>";
		}
                regex = new RegEx("\\A\\#i\\;(.*)", sData);
                if (regex.find()) {
                    engine.debug(3, "Italic Paragraph");
                    return "<p><i>" + regex.group(1) + "</i></p>";
		}
                regex = new RegEx("\\A\\#bp\\;([\\w\\s\\-\\'\\\"\\,\\d\\(\\)]+)[\\.|\\:]{1}(.+)", sData);
                if (regex.find()) {
                    engine.debug(3, "Sentence Bold");
                    return "<p><b>" + regex.group(1) + "</b>" + regex.group(2) + "</p>";
		}
                regex = new RegEx("\\A\\#ip\\;([\\w\\s\\-\\'\\\"\\,\\d\\(\\)]+)[\\.|\\:]{1}(.+)", sData);
                if (regex.find()) {
                    engine.debug(3, "Sentence Italic");
                    return "<p><i>" + regex.group(1) + "</i>" + regex.group(2) + "</p>";
		}
                regex = new RegEx("\\A\\#ts\\;(.*)", sData);
                if (regex.find()) {
                    engine.debug(3, "Table Start");
                    sText = startFTTable();
                    return sText;
		}
                regex = new RegEx("\\A\\#th\\;(.*)", sData);
                if (regex.find()) {
                    engine.debug(3, "Table Header");
                    dNumTableCols = getFieldCount(regex.group(1));
                    sText = addFTTableDataRow(regex.group(1), dNumTableCols, true);
                    return sText;
		}
        	regex = new RegEx("\\A\\#tr\\;(.*)", sData);
                if (regex.find()) {
                    engine.debug(3, "Table Row");
                    dNumTableCols = getFieldCount(regex.group(1));
                    sText = addFTTableDataRow(regex.group(1), dNumTableCols, false);
                    return sText;
		}		
		regex = new RegEx("\\A\\#te\\;(.*)", sData);
                if (regex.find()) {
                    engine.debug(3, "Table End");
                    sText = endFTTable();
                    return sText;
		}
		regex = new RegEx("\\A\\#ls\\;(.*)", sData);
                if (regex.find()) {
                    engine.debug(3, "List Start");
                    sText = startFTList();
                    return sText;
		}
		regex = new RegEx("\\A\\#li\\;(.*)", sData);
                if (regex.find()) {
                    engine.debug(3, "List Row");
                    sText = addFTListDataRow(regex.group(1));
                    return sText;
		}
		regex = new RegEx("\\A\\#le\\;(.*)", sData);
                if (regex.find()) {
                    engine.debug(3, "List End");
                    sText = endFTList();
                    return sText;
		}regex = new RegEx("\\A\\#zfs\\;(.*)", sData);
                if (regex.find()) {
                    engine.debug(3, "Frame Start");
                    sText = startFTFrameText();
                    return sText;
		}
		regex = new RegEx("\\A\\#zfi\\;(.*)", sData);
                if (regex.find()) {
                    engine.debug(3, "Frame Speaker");
                    sText = addFTFrameSpeaker(regex.group(1));
                    return sText;
		}
		regex = new RegEx("\\A\\#zft\\;(.*)", sData);
                if (regex.find()) {
                    engine.debug(3, "Frame Text");
                    sText = regex.group(1);
                    return sText;
		}
		regex = new RegEx("\\A\\#zfe\\;(.*)", sData);
                if (regex.find()) {
                    engine.debug(3, "Frame End");
                    sText = endFTFrameText();
                    return sText;
		}
		regex = new RegEx("\\A\\#zls\\;(.*)", sData);
                if (regex.find()) {
                    engine.debug(3, "LinkList Start");
                    sText = startFTLink();
                    return sText;
		}
		regex = new RegEx("\\A\\#zl\\;(.*)", sData);
                if (regex.find()) {
                    engine.debug(3, "Link");
                    sText = regex.group(1);
                    String[] data = getZLinkData(sText);
                    if (data.length == 4) {
                        sText = addFTLink(data[0], data[1], data[3]);
                    } else {
                        sText = addFTLink(data[0], data[1], makeProperCase(data[2]));
                    }
                    return sText;
		}
		regex = new RegEx("\\A\\#zle\\;(.*)", sData);
                if (regex.find()) {
                    engine.debug(3, "LinkList End");
                    sText = endFTLink();
                    return sText;
		}
		regex = new RegEx("\\A(.*)\\:(.*)$", sData);
                if (regex.find()) {
                    engine.debug(3, "Labelled Text");
                    sText = "<p><b>" + regex.group(1) + "</b>: " + regex.group(2) + "</p>";
                    return sText;
		}
                engine.debug(3, "Paragraph");
		return "<p>" + sData + "</p>";
        }
        return null;
    }
    public String startFTList() {
        return "<list>";
    }
    public String addFTListDataRow(String row) {
        return "<li>" + row + "</li>";
    }
    public String endFTList() {
        return "</list>";
    }
    public String startFTTable() {
        return "<table>";
    }
    public String addFTTableDataRow(String row, int fields, boolean headerflag) {
        String[] splitrow = row.split(";");
        int fieldcount = 0;
        String aString;

        if (headerflag) {
            aString = "<tr decoration=\"underline\">";
        } else {
            aString = "<tr>";
        }

        for (String field : splitrow) {               
            if (field.matches("\\@.*\\@")) {
                if (headerflag) {
                    field = field.replace("@", "-");
                    aString = aString + "<td colspan=\"" + (fields - fieldcount) + "\"><b>" + field + "</b></td>";
                } else {
                    aString = aString + "<td colspan=\"" + (fields - fieldcount) + "\">" + field + "</td>";
                }
            } else { 
                if (headerflag || row.matches("\\w+\\sLevel\\;1\\;2\\;3\\;4\\;5\\;6\\;7\\;8\\;9\\;")) {
                    aString = aString + "<td><b>" + field + "</b></td>";
                }  else {
                    aString = aString + "<td>" + field + "</td>";
                }	
            }
            fieldcount++;
        }
        aString = aString + "</tr>";
        return aString;
    }
    public String endFTTable() {
        return "</table>";
    }
    public int getFieldCount(String row) {
        String[] fields = row.split(";");
        return fields.length;
    }
    public String startFTLink() {
        return "<listlink>";
    }
    public String addFTLink(String classname, String recordname, String title) {
        String link = "";

        if (classname.length() > 0 && recordname.length() > 0 && title.length() > 0) {
            link = "<link class=\"" + classname + "\" recordname=\"" + recordname + "\">" + title + "</link>";
        }
        return link;
    }
    public String endFTLink() {
        return "</listlink>";
    }
    public String startFTFrameText() {
        return "<frame>";
    }
    public String addFTFrameSpeaker(String aString) {
        String sSpeaker = "";
        if (aString.length() > 0) {
           sSpeaker = "<frameid>" + aString + "</frameid>";
        }
        return sSpeaker;
    }
    public String endFTFrameText() {
        return "</frame>";
    }
    public String getKeyWords(String aString) {
        String sNewString = "";
        String[] skeyWords = aString.split(" ");
        
        for (String skeyWord : skeyWords) {
            skeyWord = skeyWord.replace("\\#.+\\;", "");
            skeyWord = skeyWord.replace("\\,\\.\\-\\_\\(\\)\"\\&\\%\\$\\!\\*\\+\\?", "");
            sNewString = sNewString + skeyWord + " ";
        }
        return sNewString;
    }
    
    // Misc helpers
    public String[] getPinData(String aString) {
        String[] pindata;
        RegEx regex = new RegEx(".*\\;.*", aString);
        if (regex.find()) {
            pindata = aString.split(";");
            if (pindata.length != 5) {
                return null;
            } else {
                switch(pindata[3]) {
                    case "story": 
                        pindata[3] = "encounter";
                        pindata[4] = "encounter.enc_" + sanatise(pindata[4]);
                        break;
                    case "encounter": 
                        pindata[3] = "battle"; 
                        pindata[4] = "battle.bat_" + sanatise(pindata[4]);
                        break;    
                }
                
                return pindata;
            }
        }
        return null;
    } 
    
    // String Helper Routines
    public String stripTxt(String aString) {
        aString = aString.replace("\n", "");
        
        trimTxt(aString);
        return aString;
    }
    public String trimTxt(String aString) {
        aString = aString.replace("\\^\\s+", "");
        aString = aString.replace("\\s+$", "");
        aString = aString.replace("\\^\\t+", "");
        aString = aString.replace("\\t+$", "");
        aString = aString.trim();
        return aString;
    }
    public String cleanTxt(String aString) {
        String cleanString = aString;
        cleanString = cleanString.replaceAll("#[\\w]+;", "");
        cleanString = cleanString.replaceAll("[^\\w\\s]", "");
        return cleanString;
    }
    public String makeProperCase(String aString) {
        
        String inputString = aString.toLowerCase();
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : inputString.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }
    public String sanatise(String aString) {
	//Sanatise text to create legal XML tag
	//Remove all whitespace, converts to lowercase and replaces punctuation with _ char
 	String sanatisedstring = aString;
        
        sanatisedstring = sanatisedstring.replaceAll("\\s", "");
        sanatisedstring = sanatisedstring.replaceAll("[\\.\\,\\-\\(\\)\\:\\'\\’\\/\\?\\+]", "_");
        sanatisedstring = sanatisedstring.replaceAll("Â—", "");
        sanatisedstring = sanatisedstring.toLowerCase();
        	
	return sanatisedstring;
    }
    public String makeID(Integer i) {
        String sID = "id-";
        
        if (i < 10) {
            sID = sID + "0000" + i;
        } else if (i < 100) {
            sID = sID + "000" + i;
        } else if (i < 1000) {
            sID = sID + "00" + i;
        } else if (i < 10000) {
            sID = sID + "0" + i;
        } else {
            sID = sID + i;
        }
        return sID;
    }
    
    //XML Helper Routines
    public Element createElement(String aName) {
        Element element = new Element(aName);
        return element;
    }
    public Element createElement(String aName, String aText) {
        Element element = new Element(aName);
        element.setText(aText);
        return element;
    }
    public Element createElement(String aName, String aText, String aType) {
        Element element = new Element(aName);
        element.setAttribute("type", aType);
        element.setText(aText);       
        return element;
    }
    
    //Core methods
    public void parseData(String aDataItemName, String[] aSourceData, ObjectContainer objects) {
       switch(aDataItemName) {
          
       }
    }
    public void generateIndexes(String aDataItemName) {
       switch(aDataItemName) {
         
       }
    } 
    public void generateModuleXML(Element E, ObjectContainer objects) {
        
    }
    public void generateModuleXMLLibraryEntries(Element E) {
        engine.debug(1, "->generateModuleXMLLibraryEntries()");
        
        Element lib = createElement("library");
        Element libname = createElement("libn" + sanatise(module.getName()) + module.getID());
        lib.addContent(libname);
        Element modname = createElement("name", module.getName() + " Reference Library", "string");
        libname.addContent(modname);
        Element catname = createElement("categoryname", module.getCategory(), "string");
        libname.addContent(catname);
        Element entries = createElement("entries");
        libname.addContent(entries);
        
        Integer index = 0;
        for (Map.Entry e : library.getEntries()) {
            String key = (String) e.getKey();
            ModuleLibraryEntry val = (ModuleLibraryEntry) e.getValue();
            
            index++;
            Element id = createElement(makeID(index));
            entries.addContent(id);
            
            Element librarylink = createElement("librarylink", "", "windowreference");
            id.addContent(librarylink);
            
            Element lclass = createElement("class", val.getClassField());
            librarylink.addContent(lclass);
            
            Element lrecordname = createElement("recordname", val.getRecordnameField());
            librarylink.addContent(lrecordname);
            
            Element iname = createElement("name", val.getNameField(), "string");
            id.addContent(iname);
            
        }
        
        E.addContent(lib);
    }
    public void generateArchive(ObjectContainer objects) {
        
    }
    public boolean isArchiveEmpty(ObjectContainer objects) {
        return true;
    }
    public String[] getZLinkData(String aString) {
	String[] link;
        if (!aString.isEmpty()) {
            RegEx regex = new RegEx("(.+)\\;(.+)\\;(.*)", aString);          
            if (regex.find()){
                link = getLinkData(regex.group(1), regex.group(2), regex.group(3));
            } else {
                regex = new RegEx("(.+)\\;(.*)", aString);
                link = getLinkData(regex.group(1), regex.group(2), null);
            }    
            return link;
        }
        return null;
    }   
    public String[] getLinkData(String sType, String sName, String sTitle) {
        String Class = "", Classname = "", Recordname = "", Sanename = "", Refname = "", Dbnode = "";
        String[] Linkdata = new String[3];
	RegEx regex;
	if (!sType.isEmpty() && !sName.isEmpty()) {
           
        }
        
        Linkdata[0]=Class;
        Linkdata[1]=Recordname;
        Linkdata[2]=sName;
        return Linkdata;
    }
    
}

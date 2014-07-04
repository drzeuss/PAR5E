/*
 * 
 * PAR5E Fantasy Grounds Ruleset Module Parser Tool
 * Developed by Zeus. Copyright 2014
 * 
 */


package par5e;

import com.db4o.ObjectContainer;
import par5e.utils.*;
import par5e.utils.XMLViewer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import javax.swing.text.BadLocationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author zeph
 */
public class PAR5EEngine extends SwingWorker<Integer,Object> {
    
    public PAR5ERulesetLibrary rulesetLibrary;
    public LinkedHashMap dataItems;
    public Module module;
    public JTextPane output;
    public Integer debuglevel;
    public JProgressBar progressBar;
    public ObjectContainer archive, tmparch;
    public boolean archimport;
    public boolean running; 
    
    private final String info = "info";
    private final String normal = "normal";
    private final String warning = "warning";
    private final String standard = "standard";
    private final String slash;
    
    private JLabel labelStatus;
    private JButton buttonParse;
    private JTabbedPane panelMain;
    private ConsoleWriter console; 
    private final OSValidator os;
    private final Properties packageBuild = new Properties();
    private SAXParseException exception;
    
    //Constructors
    public PAR5EEngine() { 
        
        os = new OSValidator();
        if (os.isWindows()) {
            slash = "\\";
        } else {
            slash = "/";
        }
        rulesetLibrary = new par5e.corerpg.RulesetLibrary();
        dataItems = rulesetLibrary.getDataItems(); 
        module = null;
        output = null;
        debuglevel = 0;
        progressBar = null;
        archimport = false;
        running = false;
    }

    //Public Configuration Methods
    public void initialise() {      
       dataItems = rulesetLibrary.getDataItems(); 
    }
    public void setRulesetLibrary(String aRuleSetName) throws ClassNotFoundException {
        switch(aRuleSetName) {
            case "5E":
                rulesetLibrary = new par5e.dd5e.RulesetLibrary();
                break; 
            default: 
                rulesetLibrary = new par5e.corerpg.RulesetLibrary();
                break;
        }    
        
        rulesetLibrary.setConsole(console);
        rulesetLibrary.setEngine(this);
        
        initialise();
    }
    public void setModule(Module aModule) {
        module = aModule;
    }
    public void setOutput(JTextPane aControl) {
        output = aControl;
        console = new ConsoleWriter(output);
    }
    public void setProgressBarComp(JProgressBar jb) {
        progressBar = jb;
    }
    public void setStatusLabelComp(JLabel jl) {
        labelStatus = jl;
    }
    public void setParseButtonComp(JButton jb) {
        buttonParse = jb;
    }
    public void setPanelComp(JTabbedPane jp) {
        panelMain = jp;
    }
    public void setDebugLevel(Integer level) {
        debuglevel = level;
    }   
    public void setArchive(ObjectContainer objects) {
        archive = objects;
    }   
    public void setTmpArchive(ObjectContainer objects) {
        tmparch = objects;
    }   
    public void setArchiveImport(boolean i) {
        archimport = i;
    }
    public PAR5ERulesetLibrary getRulesetLibrary() {
        return rulesetLibrary;
    }
    public Module getModule() {
        return module;
    }
    public ObjectContainer getArchive() {
        return archive;
    }
    public ObjectContainer getTmpArchive() {
        return tmparch;
    }
    
    //Debug methods
    public void debug(Integer level, String msg) {
	//Depending on debugging level, print the passed message
        if (level <= debuglevel ) {
            System.out.println(msg);
        }
    }
    public LinkedHashMap getDataItems() {
        return rulesetLibrary.getDataItems();
    }
    public boolean isRunning() {
        return running;
    }
    
    // Private Methods
    public boolean isValidDataPath(String aPath) {
        File f = new File(aPath);
	return f.exists();
    }
    private Integer makeFolders() {
       // System.out.println(module.getTempPath());
        if (module.getTempPath() == null || module.getTempPath().equals("<<Temp Path>>") ) {
         //   System.out.println("Temp Path not set");
            return 0;
        } else {
        
            File f = new File(module.getTempPath());
            if ( f.exists() && f.isDirectory() ) {
                File imagefolder = new File(module.getTempPath() + slash + "images");
                File tokensfolder = new File(module.getTempPath() + slash + "tokens" + slash + module.getName());
                if (imagefolder.mkdir() && tokensfolder.mkdirs()) {
           //         System.out.println("Folders created.");
                    return 1;
                } else {
             //       System.out.println("Folders exist");
                    return 2;
                }
            } else {
               //  System.out.println("Temp Path does not exist");
                return 0;
            }
        }
    }   
    private void failed() {
        buttonParse.setEnabled(true);
        labelStatus.setText("Error");
    }
    private void copyImageFiles(File source, File destination) throws FileNotFoundException, IOException {       
       if (source.exists()) {
           if (source.isDirectory()) {
                FilenameFilter imgFilter = new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        boolean acc = false;

                        if (name.contains(".png")) {
                            acc = true;
                        }
                        if (name.contains(".jpg")) {
                            acc = true;
                        }
                        if (name.contains(".jpeg")) {
                            acc = true;
                        }

                        return acc;

                    }
                };  
                String[] files = source.list(imgFilter);

                for (String f : files) {
                    File srcFile = new File(source + slash + f);
                    File dstFile = new File(destination.getPath() + slash + f);

                    if (!srcFile.exists()) {
                        return;
                    }
                    Files.copy(srcFile.toPath(), dstFile.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES, LinkOption.NOFOLLOW_LINKS );
                }
           } else {
                File srcFile = source;
                File dstFile = new File(destination.getPath() + slash + "thumbnail.png");
                if (!srcFile.exists()) {
                    return;
                }
                Files.copy(srcFile.toPath(), dstFile.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES, LinkOption.NOFOLLOW_LINKS );
           }
        } 
    }
    private void zipFiles(File source, String aZIPPath) throws FileNotFoundException, IOException {
        List<File> fileList = new ArrayList<>(1);
        getAllFiles(source, fileList);      
        
        ZIPArchive zipA = new ZIPArchive();
        zipA.compressFiles(fileList, aZIPPath);   
    }
    private void getAllFiles(File source, List<File> fileList) {
        File[] files = source.listFiles();
        for (File file : files) {
            if (!file.getName().contains(".DS_Store")) {
                fileList.add(file);
            }        
        }
    }
    private boolean validateXML(String aPath)  {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setNamespaceAware(true);
            
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            builder.setErrorHandler(new SimpleErrorHandler());
            // the "parse" method also validates XML, will throw an exception if not well formed
            Document document = builder.parse(new InputSource(aPath));
            return true;
        } catch ( SAXParseException  ex) {
            if (ex.getClass().equals(SAXParseException.class)) {
                exception = ex;
            } 
            openXMLViewer(module.getTempPath() + slash + "db.xml", exception);
            return false;
        } catch (IOException | ParserConfigurationException | SAXException ex) {
            return false;            
        }
        
    }
    private void tidyXML(String aPath) {
        TransformerFactory factory = TransformerFactory.newInstance();
        try {
            
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            
            StreamSource source = new StreamSource(aPath);
            StreamResult result = new StreamResult(aPath + "_tidy.xml");
            
            transformer.transform(source, result);
        
            File outputFile = new File(aPath);
            File tidyoutputFile = new File(aPath + "_tidy.xml");
                        
            outputFile.delete();
            outputFile = new File(aPath);
            
            tidyoutputFile.renameTo(outputFile);
            tidyoutputFile.delete();
        } catch (TransformerException ex) {
            
        }
    }
    private void openXMLViewer(String aPath, SAXParseException ex) {
        try {
            String[] sourceData;
            String data = "";
            SourceFile sFile = new SourceFile(aPath);
            String msg = "";
            Integer row = 0;
            Integer col = 0;
            
            if (ex != null) {
                col = ex.getColumnNumber();
                row = ex.getLineNumber();
                msg = "  Malformed XML! db.xml:" + row + ":" + col + ": " + ex.getMessage() + "\n  Check module source files for correct markup and syntax.";
            } 
            Integer pos;
            
            sourceData = sFile.readFile();
            for (String sLine : sourceData){
                data += sLine + "\n";
            }
            
            XMLViewer xmlv = new XMLViewer(data, 0, msg);
            pos = xmlv.getTextArea().getLineStartOffset(row-1);
            xmlv.getTextArea().setCaretPosition(pos+col);
                    
        } catch (IOException | BadLocationException ex1) {
            
        }

    }
    
    //Public Threaded Routines
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public Integer doInBackground() throws InterruptedException {
        running = true;
        int progress = 0;
        
        rulesetLibrary.setModule(module);
        debug(1,"->rulesetLib (" + rulesetLibrary.getName() + ")");
        
        try {
            packageBuild.load(getClass().getResourceAsStream("build.properties"));
        } catch (IOException ex) {
            Logger.getLogger(PAR5EEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        labelStatus.setText("Processing");

        // Info
        console.printMsg("Info  : engine build");
        console.printMsgResult(packageBuild.getProperty("build").toString(), info);
        console.printMsg("Info  : ruleset library");
        console.printMsgResult(rulesetLibrary.getName(), info);
        console.printMsg("Info  : module");
        console.printMsgResult(module.getName(), info);
        console.printMsg("Info  : ruleset");
        console.printMsgResult(module.getRuleset(), info);
        console.printMsg("Info  : folder structure");
        
        //Call the process method to update the GUI progress bar
        progress++;
        publish(progress);
        
        //Create module folder structure
        int result = makeFolders();
        switch(result) {
            case 0: {
                console.printMsgResult("mkdir() Failed", warning);
                failed();
                cancel(true);
                running = false;
                return 0;
            } 
            case 1: console.printMsgResult("Created", info); break;
            case 2: console.printMsgResult("Already Exists", warning); break;
        }
        
        progress++;
        publish(progress);
        
        //Begin         
        //Loop through all module data items and parse source data
        Iterator itDataItems = module.getDataItems().entrySet().iterator();
        while (itDataItems.hasNext()) {
            Map.Entry entry = (Map.Entry) itDataItems.next();
            String diName = (String)entry.getKey();
            BitSet diOutputMode = (BitSet)entry.getValue();
            boolean isDataItemFolderType = false;
            boolean import_fromarchive = false;
            SourceFile dataItemSourceFile;
            BitSet mask = new BitSet(3);
            mask.set(0, true);
            mask.set(1, true);
            mask.set(2, true);
            String[] sourceData = null;
            
            //Check to see if the source is a file or folder of files
            if (diName.equals("images") || diName.equals("tokens")) {
               isDataItemFolderType = true;
            }
            
            //If output of data item has been selected
            if ( diOutputMode.intersects(mask) ){
                //Check for valid data item source path and open/read file
                if (isValidDataPath(module.getPath() + slash + module.getDataItemPaths().get(diName).toString()) ) {
                    //If data item is a single file
                    if (!isDataItemFolderType) {
                        dataItemSourceFile = new SourceFile(module.getPath() + slash + module.getDataItemPaths().get(diName).toString());
                        //Open and read the data item source file
                        try {
                            sourceData = dataItemSourceFile.readFile();
                        } catch (IOException ex) {
                            Logger.getLogger(PAR5EEngine.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        //If data item is a folder of files i.e images or tokens
                        File[] files = new File( module.getPath() + slash + module.getDataItemPaths().get(diName).toString()).listFiles();  
                        for (File file : files) {
                            if (file.isFile()) {
                                if (sourceData != null) {
                                    if (file.getName().contains(".png") || file.getName().contains(".jpg")) {
                                        sourceData = Arrays.copyOf(sourceData, sourceData.length+1); 
                                        sourceData[sourceData.length-1] = file.getPath();
                                    }
                                } else {
                                    if (file.getName().contains(".png") || file.getName().contains(".jpg")) {
                                        sourceData = new String[1];
                                        sourceData[0] = file.getPath();
                                    }
                                } 
                            }
                        }
                    }
                    
                    //Parse the data 
                    rulesetLibrary.parseData(diName, sourceData, tmparch);
                    console.printMsg("Parse : " + diName);
                    console.printMsgResult("Completed", info);

                    //Generate Window Reference Lists
                    if ((diOutputMode.get(0) || diOutputMode.get(1)) && !isDataItemFolderType) {
                        rulesetLibrary.generateIndexes(diName);
                        console.printMsg("Make  : " + diName + " windowlists");
                        console.printMsgResult("Completed", info);
                    }
                        
                } else if (diName.equals("npcs") || diName.equals("magicitems")) {
                    //Check to see if we have NPC or MItems to import from the Archive
                    if (archimport) {
                        //Parse the data 
                        String[] empty = new String[0];
                        rulesetLibrary.parseData(diName, empty, tmparch);
                        console.printMsg("Parse : " + diName);
                        console.printMsgResult("Completed", info);

                        //Generate Window Reference Lists
                        if ((diOutputMode.get(0) || diOutputMode.get(1))) {
                            rulesetLibrary.generateIndexes(diName);
                            console.printMsg("Make  : " + diName + " windowlists");
                            console.printMsgResult("Completed", info);
                        }    
                    }
                } else {
                    console.printMsg("Parse : " + diName);
                    console.printMsgResult("Invalid Path", warning);
                }
            }

            //pause thread and then update the progress
            Thread.sleep(50);
            progress = progress + (100/dataItems.size());
            //Call the process method to update the GUI
            publish(progress);
        }  
        
        //Create XML document instance
        ModuleXML modXML = new ModuleXML();
        rulesetLibrary.generateModuleXML(modXML.getRoot(), tmparch);           
        console.printMsg("Make  : module xml data");
        console.printMsgResult("Completed", info); 
        
        //Call the process method to update the GUI progress bar
        progress++;
        publish(progress);
        
        //Create Archive of parsed objects
        rulesetLibrary.generateArchive(archive);           
        console.printMsg("Make  : archive data");
        console.printMsgResult("Completed", info); 
        
        //Call the process method to update the GUI progress bar
        progress++;
        publish(progress);
        
        //Make library entries to module XML
        //Generate RefRulesetLibraryLibrary Entries
        rulesetLibrary.generateModuleXMLLibraryEntries(modXML.getRoot());
        console.printMsg("Make  : module xml library entries");
        console.printMsgResult("Completed", info); 
        
        //Call the process method to update the GUI progress bar
        progress++;
        publish(progress);
        
        //Check for sane encoding        
        modXML.outputXML(module.getTempPath() + slash + "db.xml"); 
        CleanXMLFile cleanXMLFile = new CleanXMLFile(module.getTempPath() + slash + "db.xml");
        cleanXMLFile.openFile();
        cleanXMLFile.cleanXML();
        cleanXMLFile.closeFile();
        //Tidy up XML output
        tidyXML(module.getTempPath() + slash + "db.xml");
        
        debug(1, "->writeXML");
        console.printMsg("Write : module xml data");
        console.printMsgResult("Completed", info);
        
        //Check for well formed XML  
        if (validateXML(module.getTempPath() + slash + "db.xml")) {
            console.printMsg("Write : module xml syntax");
            console.printMsgResult("Well Formed", info);
        } else {
            console.printMsg("Write : module xml syntax");
            console.printMsgResult("Mal Formed", warning);
            running = false;
            return 0;
        }
                
        //Make module definition XML 
        ModuleXML modDefinition = new ModuleXML();
        modDefinition.writeDef(module.getName(), "PAR5E " + packageBuild.getProperty("build"), module.getRuleset());
        modDefinition.outputXML(module.getTempPath() + slash + "definition.xml");
        debug(1, "->writeDefXML");
        console.printMsg("Write : module xml definition");
        console.printMsgResult("Completed", info); 
        
        //Call the process method to update the GUI progress bar
        progress++;
        publish(progress);
        
        //Copy images to module temp folder
        File sourcePath = new File(module.getPath() + slash + module.getDataItemPaths().get("images").toString());
        File targetPath = new File(module.getTempPath() + slash + "images");
        try {
            copyImageFiles(sourcePath, targetPath);
            debug(1, "->copyImages");        
            console.printMsg("Write : copying module images");
            console.printMsgResult("Completed", info); 
        } catch (IOException ex) {
            console.printMsg("Write : copying module images");
            console.printMsgResult("Failed", warning);
            running = false;
            return 0;
        } 
        
        //Call the process method to update the GUI progress bar
        progress++;
        publish(progress);
        
        //Copy tokens to module temp folder
        sourcePath = new File(module.getPath() + slash + module.getDataItemPaths().get("tokens").toString());
        targetPath = new File(module.getTempPath() + slash + "tokens" + slash + module.getName().toString());
        try {
            copyImageFiles(sourcePath, targetPath);
            debug(1, "->copyTokens"); 
            console.printMsg("Write : copying module tokens");
            console.printMsgResult("Completed", info);
        } catch (IOException ex) {
            console.printMsg("Write : copying module tokens");
            console.printMsgResult("Failed", warning);
            running = false;
            return 0;
        } 
        
        //Call the process method to update the GUI progress bar
        progress++;
        publish(progress);
        
        //Copy thumbnail to module temp folder
        sourcePath = new File(module.getThumbnailPath());
        targetPath = new File(module.getTempPath());
        try {
            copyImageFiles(sourcePath, targetPath);
            debug(1, "->copyThumbnail"); 
            console.printMsg("Write : copying module thumbnail");
            console.printMsgResult("Completed", info);
        } catch (IOException ex) {
            console.printMsg("Write : copying module thumbnail");
            console.printMsgResult("Failed", warning);
            running = false;
            return 0;
        } 
        
        
        //Call the process method to update the GUI progress bar
        progress++;
        publish(progress);
        
        //Bake module files into zip archive file
        sourcePath = new File(module.getTempPath());
        targetPath = new File(module.getOutputPath() + slash + module.getName() + ".mod");
        try {
            zipFiles(sourcePath, targetPath.getPath());
            debug(1, "->zipFile"); 
            console.printMsg("Cook  : module archive");
            console.printMsgResult("Completed", info);
        } catch (IOException ex) {
            console.printMsg("Cook  : module archive");
            console.printMsgResult("Failed", warning);
            running = false;
            return 0;
        }        
        
        //Call the process method to update the GUI progress bar
        progress++;
        publish(progress);
        
        //Build Complete
        console.printMsg("Build : module");
        console.printMsgResult("Completed", info);
        console.println("");
        
        //Call the process method to update the GUI
        progress = 100;
        publish(progress);
        
        running = false;
        return 1;
    }
    @Override
    protected void process(List chunks) {
        for (Object chunk : chunks) {
            progressBar.setValue((Integer)chunk);
        }
    }  
    @Override
    protected void done() {
        buttonParse.setEnabled(true);
        labelStatus.setText("Completed");
        running = false;
    } 
}

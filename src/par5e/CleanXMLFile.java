/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package par5e;

import par5e.utils.RegEx;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author zeph
 */
public class CleanXMLFile {
    
        public String path;
        public String[] textData;
        public Integer lineCount;
        
    public CleanXMLFile(String aPath) {
        path = aPath;
    }
    
    public void openFile() {
        FileReader fReader = null;     
        try {
            fReader = new FileReader(path);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CleanXMLFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (BufferedReader textReader = new BufferedReader(fReader)) {
            lineCount = getLineCount();
            textData = new String[lineCount];
            for (int i=0; i < lineCount; i++) {
                textData[i] = textReader.readLine();    
            }   
            textReader.close();
        } catch (IOException ex) {
            Logger.getLogger(CleanXMLFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cleanXML() {
        String[] cleantextData = new String[lineCount];
            for (int i=0; i < lineCount; i++) {
                cleantextData[i] = textData[i];

                RegEx regex = new RegEx("&lt;",cleantextData[i]);
                if (regex.find()) {
                    cleantextData[i] = regex.replaceAll("\\<");
                }
                regex = new RegEx("&gt;",cleantextData[i]);
                if (regex.find()) {
                    cleantextData[i] = regex.replaceAll("\\>");
                }
                regex = new RegEx("null",cleantextData[i]);
                if (regex.find()) {
                    cleantextData[i] = regex.replaceAll("");
                }
//                regex = new RegEx("&#x2019;",cleantextData[i]);
//                if (regex.find()) {
//                    cleantextData[i] = regex.replaceAll("'");
//                }
//                regex = new RegEx("&#x2010;",cleantextData[i]);
//                if (regex.find()) {
//                    cleantextData[i] = regex.replaceAll("-");
//                }
//                regex = new RegEx("&#x2013;",cleantextData[i]);
//                if (regex.find()) {
//                    cleantextData[i] = regex.replaceAll("-");
//                }  
//                regex = new RegEx("&#x2014;",cleantextData[i]);
//                if (regex.find()) {
//                    cleantextData[i] = regex.replaceAll("-");
//                }
//                regex = new RegEx("&#x201c;",cleantextData[i]);
//                if (regex.find()) {
//                    cleantextData[i] = regex.replaceAll("\"");
//                }
//                regex = new RegEx("&#x201d;",cleantextData[i]);
//                if (regex.find()) {
//                    cleantextData[i] = regex.replaceAll("\"");
//                }
     }
            textData = cleantextData;
    }
    
    public void closeFile() {
        FileWriter fWriter = null;     
        try {
            fWriter = new FileWriter(path);
        } catch (IOException ex) {
            Logger.getLogger(CleanXMLFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (BufferedWriter textWriter = new BufferedWriter(fWriter)) {
            for (int i=0; i < lineCount; i++) {
                textWriter.write(textData[i] + "\n");
            }
            textWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(CleanXMLFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    public int getLineCount() throws IOException {
        FileReader fReader = new FileReader(path);
        try (BufferedReader textReader = new BufferedReader(fReader)) {
            String aLine;
            int numLines = 0;
            
            while (( aLine = textReader.readLine()) != null) {
                numLines++;
            }
            textReader.close();
            return numLines;
        }
    }
    
}


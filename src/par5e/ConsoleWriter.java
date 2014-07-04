/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package par5e;

import par5e.utils.OSValidator;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author zeph
 */
public class ConsoleWriter {
    //The JTextArea to wichh the output stream will be redirected.
    public JTextPane textArea;
    public StyledDocument console;
    
    public SimpleAttributeSet standard = new SimpleAttributeSet();
    public SimpleAttributeSet normal = new SimpleAttributeSet();
    public SimpleAttributeSet info = new SimpleAttributeSet();
    public SimpleAttributeSet warning = new SimpleAttributeSet();
    
    private final Color STD = new Color(0,0,0);
    private final Color NORM = new Color(40,128,29);
    private final Color INFO = new Color(62,109,173);
    private final Color WARN = new Color(166,66,66);
    
    private final OSValidator os = new OSValidator();
    
    public ConsoleWriter(JTextPane area) {
	super();
	String osFont = "Courier New";
        textArea = area;   
        console = textArea.getStyledDocument();   
        
        if (os.isMac()) {
            osFont = "Consolas";
        }
        
        StyleConstants.setFontFamily(standard, osFont);
        StyleConstants.setBackground(standard, Color.WHITE);
        StyleConstants.setForeground(standard, STD);
        StyleConstants.setBold(standard, false);
        
        StyleConstants.setFontFamily(info, osFont);
        StyleConstants.setBackground(info, Color.WHITE);
        StyleConstants.setForeground(info, INFO);
        StyleConstants.setBold(info, true);
        
        StyleConstants.setFontFamily(normal, osFont);
        StyleConstants.setBackground(normal, Color.WHITE);
        StyleConstants.setForeground(normal, NORM);
        StyleConstants.setBold(normal, true);
        
        StyleConstants.setFontFamily(warning, osFont);
        StyleConstants.setBackground(warning, Color.WHITE);
        StyleConstants.setForeground(warning, WARN);
        StyleConstants.setBold(warning, true);

       
        
    }

    public void println(String string) {
        try {
            console.insertString(console.getLength(), string+"\n", null);
            textArea.setCaretPosition(console.getLength());
        } catch (BadLocationException ex) {
            Logger.getLogger(ConsoleWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void print(String string, SimpleAttributeSet aSet) {
        try {
            console.insertString(console.getLength(), string, aSet);
            textArea.setCaretPosition(console.getLength());
        } catch (BadLocationException ex) {
            Logger.getLogger(ConsoleWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void printMsg(String aString) {
        int leng = 100;
        
      //  if (os.isMac()) {
      //      leng = 68;
      //  }
        
        aString += "  ";
        for (int i = aString.length(); i <= leng; i++) {
            aString += ".";
        }
        aString += "  ";
        print("\n  " + aString, standard);
    }
    public void printMsgResult(String string, String type) {
        
        String res = "";
        
        int len = string.length();
        int curoffset = textArea.getDocument().getEndPosition().getOffset();
        int newoffset = curoffset - (len + 1);
        AttributeSet sAtt;
        
        switch (type) {
            case "normal":
                sAtt = normal;
                break;
            case "info":
                sAtt = info;
                break;
            case "warning":
                sAtt = warning;
                break;
            default:
                sAtt = standard;
                break;
        }
        
        try {
            int savedoffset = newoffset;
            textArea.getDocument().insertString(newoffset, " [", standard);
            newoffset += 2;
            textArea.getDocument().insertString(newoffset, string, sAtt);
            newoffset += string.length();
            textArea.getDocument().insertString(newoffset, "]", standard);
            newoffset += 1;
                       
            textArea.getDocument().remove(newoffset, string.length()-1);
        
        } catch (BadLocationException ex) {
        }
    } 
    public static int getRow(int pos, JTextPane editor) {
        int caretPosition = editor.getCaretPosition();
        Element root = editor.getDocument().getDefaultRootElement();

        return root.getElementIndex( caretPosition ) + 1;
    }
    public static int getCol(int pos, JTextComponent editor) {
        int caretPosition = editor.getCaretPosition();
        Element root = editor.getDocument().getDefaultRootElement();
        int line = root.getElementIndex( caretPosition );
        int lineStart = root.getElement( line ).getStartOffset();

        return caretPosition - lineStart + 1;
    }
}

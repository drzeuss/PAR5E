/*
 * 
 * PAR5E Fantasy Grounds Ruleset Module Parser Tool
 * Developed by Zeus. Copyright 2014
 * 
 */


package par5e.utils;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 * A simple example showing how to use RSyntaxTextArea to add Java syntax
 * highlighting to a Swing application.<p>
 * 
 * This example uses RSyntaxTextArea 2.0.1.<p>
 * 
 * Project Home: http://fifesoft.com/rsyntaxtextarea<br>
 * Downloads: https://sourceforge.net/projects/rsyntaxtextarea
 */
public class XMLViewer extends JFrame {

   private static final long serialVersionUID = 1L;

   private JPanel cp;
   private JTextPane statusText;
   private RSyntaxTextArea textArea;
   private RTextScrollPane sp;
   private JTextPane tp;
   private String osFont = "Courier New";
   private final OSValidator os = new OSValidator();
           
   public XMLViewer(String aContent, Integer pos, String msg) {
       initComponents(aContent, pos, msg);
   }
   
   private void initComponents(String aContent, Integer pos, String msg) {

        JInternalFrame internalFrame = new JInternalFrame(); 

        if (os.isMac()) {
            osFont = "Consolas";
        }
        textArea = new RSyntaxTextArea(50, 50);
        
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
        textArea.setCodeFoldingEnabled(true);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        
        textArea.setAntiAliasingEnabled(true);
        textArea.setFont(new java.awt.Font(osFont, 0, 13));
        sp = new RTextScrollPane(textArea);
        sp.setFoldIndicatorEnabled(true);
        statusText = new JTextPane();
        statusText.setFont(new java.awt.Font(osFont, 0, 11));
        statusText.setBorder(null);
        statusText.setBackground(getBackground());
        
        internalFrame.setVisible(true);

        GroupLayout ifLayout = new GroupLayout(internalFrame.getContentPane());
        internalFrame.getContentPane().setLayout(ifLayout);
        ifLayout.setHorizontalGroup(ifLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        ifLayout.setVerticalGroup(ifLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
    
        setTitle("PAR5E XML Browser");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1000, 750));
        getContentPane().setLayout(new java.awt.GridLayout(2, 1));
      
        //setContentPane(cp);
        textArea.setText(aContent);
        textArea.setCaretPosition(pos);
        getContentPane().add(sp);
        
        if (!msg.equals("")) {
            statusText.setText(msg);
        }
        getContentPane().add(statusText);
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(sp, GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE)
            .addComponent(statusText, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(sp, GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusText, GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
   }
   
   
   public RSyntaxTextArea getTextArea() {
       return textArea;
   }
   
}
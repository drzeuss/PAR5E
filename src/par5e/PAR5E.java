/*
 * 
 * PAR5E Fantasy Grounds Ruleset Module Parser Tool
 * Developed by Zeus. Copyright 2014
 * 
 */


package par5e;

import com.apple.eawt.AboutHandler;
import com.apple.eawt.AppEvent;
import par5e.utils.OSValidator;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import org.apache.commons.lang3.ArrayUtils;
import par5e.corerpg.*;

/**
 *
 * @author Zeus
 */
public final class PAR5E extends javax.swing.JFrame {

    //Variables
    Module module;
    Properties rulesetLibraries = new Properties();
    Properties packageBuild = new Properties();
    PAR5EEngine parserEngine;
    HashMap dataItems = new HashMap();
    JScrollPane scrollpanelDataItems;
    JTable tableDataItems, tableArchiveData, tableModuleData;
    GroupLayout panelConfigLayout;
    static String[] aArgs;
    OSValidator os;
    String slash;
    Archive archive, tmparch;
    boolean init = true;
    
    Integer debug = 0;

    /**
     * Creates new form PAR5E
     * @throws java.io.IOException
     */
    public PAR5E() throws IOException {
        //Check OS type and set slash accordingly
        os = new OSValidator();
        if (os.isWindows()) {
            slash = "\\";
        } else {
            slash = "/";
        }
        
        parserEngine = new PAR5EEngine();
        
        // Init GUI Components
        initComponents();

        //Set initial archive path
        textfieldArchivePath.setText(System.getProperty("user.dir") + slash + "archive.db4o");
        archive = new Archive();
        archive.open(textfieldArchivePath.getText());
        
        tmparch = new Archive();
        tmparch.open(System.getProperty("user.dir") + slash + "tmparch.db4o");
        
        // Create JTable components
        scrollpanelDataItems = new JScrollPane();
        tableDataItems = new JTable();
        
        tableArchiveData = new JTable();
        tableModuleData = new JTable();
        
        tableArchiveData.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tableModuleData.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // Set-up JTable Layout
        updateDataItemTableHeaders();
        panelConfigLayout = new GroupLayout(panelConfig);
        updatePanelConfigLayout();
        initDataItemsTable();
        
        // Init Ruleset Libraries 
        initRulesetLibraries();
        
        // Init Archives
        initRulesetArchive();
        initTmpArchive();
        
        //Set-up Archive JTable Layout
        updateArchiveTableHeaders();
        initArchiveTable();
        
        //Set-up Archive JTable Layout
        updateModuleTableHeaders();
        initModuleTable();
        
        // Get Data Items for selected ruleset and populate JTable
        dataItems = parserEngine.getDataItems();
        initDataItems();
        
        // Get Archive Data for selected ruleset and data view and populate Archive JTable
        updateArchiveTableData(comboArchiveData.getSelectedItem().toString());
        
        //Set GUI Tooltips
        initComponentToolTips();
        
        // Patch Java's buggy MANIFEST.MF functionality so that we can include a reference to versioning;
        packageBuild.load(getClass().getResourceAsStream("build.properties"));
        // Update the footer label with version and build info
        labelVersion.setText(packageBuild.getProperty("build").toString());
        setLocationRelativeTo(null);
        
        //Initialise New Module
        newModule();

        
        //Set Icon for App
        setIconImage(new ImageIcon(getClass().getResource("resources/icons/PAR5E256.png")).getImage());
        
        if (os.isMac()) {
            com.apple.eawt.Application app = com.apple.eawt.Application.getApplication();
            app.setDockIconImage(new ImageIcon(getClass().getResource("resources/icons/PAR5E512.png")).getImage());
            app.setAboutHandler(new AboutHandler() {
                @Override
                public void handleAbout(AppEvent.AboutEvent ae) {
                    AboutDialog aDialog = new AboutDialog(null, true);
                    aDialog.setText(packageBuild.getProperty("build").toString());
                    
                    aDialog.setVisible(true);   
                }
            });
        } 
        
        //Check for config file in program arguements and if present load and start parse action.
        if (aArgs.length > 0 ) {
            if (aArgs[0].contains("-c")) {
                if (!aArgs[1].isEmpty()) {
                    File f = new File(aArgs[1]);
                    if (f.exists()) {
                       loadConfigurationFile(aArgs[1]);
                       parseData();
                    }
                }
            }
        }
        
        pack();
        init = false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        toolbarMain = new javax.swing.JToolBar();
        buttonNew = new javax.swing.JButton();
        buttonLoad = new javax.swing.JButton();
        buttonSave = new javax.swing.JButton();
        toolbarAction = new javax.swing.JToolBar();
        buttonParse = new javax.swing.JButton();
        toolbarQuit = new javax.swing.JToolBar();
        buttonQuit = new javax.swing.JButton();
        tabpaneMain = new javax.swing.JTabbedPane();
        panelConfig = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        textfieldModuleName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textfieldModuleID = new javax.swing.JTextField();
        textfieldModuleCategory = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        spinnerDebug = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        comboRuleset = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        textfieldModulePath = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        textfieldTempPath = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        textfieldModuleOutputPath = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        textfieldThumbnailPath = new javax.swing.JTextField();
        buttonModuleSourcePath = new javax.swing.JButton();
        buttonThumbnailPath = new javax.swing.JButton();
        buttonTempPath = new javax.swing.JButton();
        buttonModuleOutputPath = new javax.swing.JButton();
        panelArchive = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        textfieldArchivePath = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        buttonArchivePath = new javax.swing.JButton();
        scrollpanelArchive = new javax.swing.JScrollPane();
        jPanel6 = new javax.swing.JPanel();
        scrollpanelModule = new javax.swing.JScrollPane();
        jPanel8 = new javax.swing.JPanel();
        comboArchiveBulkAction = new javax.swing.JComboBox();
        buttonArchiveBulkActionApply = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        comboArchiveData = new javax.swing.JComboBox();
        jPanel9 = new javax.swing.JPanel();
        comboModuleBulkAction = new javax.swing.JComboBox();
        buttonModuleBulkActionApply = new javax.swing.JButton();
        comboDataForModule = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        panelConsole = new javax.swing.JPanel();
        scrollpanelConsole = new javax.swing.JScrollPane();
        textpaneConsole = new javax.swing.JTextPane();
        progressbarStatus = new javax.swing.JProgressBar();
        labelStatus = new javax.swing.JLabel();
        labelVersion = new javax.swing.JLabel();
        menubarMain = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuitemNew = new javax.swing.JMenuItem();
        menuitemLoad = new javax.swing.JMenuItem();
        menuitemSave = new javax.swing.JMenuItem();
        menuitemQuit = new javax.swing.JMenuItem();
        menuAction = new javax.swing.JMenu();
        menuitemParse = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PAR5E");
        setIconImage(Toolkit.getDefaultToolkit().getImage(PAR5E.class.getResource("resources/icons/PAR5E16.png")));
        setMinimumSize(new java.awt.Dimension(810, 830));
        setName("PAR5Emain"); // NOI18N
        setPreferredSize(new java.awt.Dimension(810, 830));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        toolbarMain.setBorder(null);
        toolbarMain.setFloatable(false);
        toolbarMain.setRollover(true);
        toolbarMain.setBorderPainted(false);

        buttonNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/par5e/resources/buttons/Document2.png"))); // NOI18N
        buttonNew.setText("New");
        buttonNew.setActionCommand("buttonNew");
        buttonNew.setBorderPainted(false);
        buttonNew.setFocusable(false);
        buttonNew.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonNew.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbarMain.add(buttonNew);

        buttonLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/par5e/resources/buttons/Folder3.png"))); // NOI18N
        buttonLoad.setText("Load");
        buttonLoad.setActionCommand("buttonLoad");
        buttonLoad.setBorderPainted(false);
        buttonLoad.setFocusable(false);
        buttonLoad.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonLoad.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLoadActionPerformed(evt);
            }
        });
        toolbarMain.add(buttonLoad);

        buttonSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/par5e/resources/buttons/Save.png"))); // NOI18N
        buttonSave.setText("Save");
        buttonSave.setActionCommand("buttonSave");
        buttonSave.setBorderPainted(false);
        buttonSave.setFocusable(false);
        buttonSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveActionPerformed(evt);
            }
        });
        toolbarMain.add(buttonSave);

        toolbarAction.setBorder(null);
        toolbarAction.setFloatable(false);
        toolbarAction.setRollover(true);
        toolbarAction.setBorderPainted(false);

        buttonParse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/par5e/resources/buttons/Player Play.png"))); // NOI18N
        buttonParse.setText("Parse");
        buttonParse.setToolTipText("");
        buttonParse.setActionCommand("buttonParse");
        buttonParse.setBorderPainted(false);
        buttonParse.setFocusable(false);
        buttonParse.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonParse.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonParse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonParseActionPerformed(evt);
            }
        });
        toolbarAction.add(buttonParse);

        toolbarQuit.setBorder(null);
        toolbarQuit.setFloatable(false);
        toolbarQuit.setRollover(true);
        toolbarQuit.setBorderPainted(false);

        buttonQuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/par5e/resources/buttons/Standby.png"))); // NOI18N
        buttonQuit.setText("Quit");
        buttonQuit.setActionCommand("buttonQuit");
        buttonQuit.setBorderPainted(false);
        buttonQuit.setDefaultCapable(false);
        buttonQuit.setFocusable(false);
        buttonQuit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonQuit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonQuitActionPerformed(evt);
            }
        });
        toolbarQuit.add(buttonQuit);

        tabpaneMain.setBackground(java.awt.SystemColor.control);
        tabpaneMain.setAutoscrolls(true);
        tabpaneMain.setFont(new java.awt.Font("Lucida Grande", 1, 11)); // NOI18N
        tabpaneMain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabpaneMainMouseClicked(evt);
            }
        });

        panelConfig.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Module Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 11))); // NOI18N

        textfieldModuleName.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        textfieldModuleName.setText("<<Module Name>>");
        textfieldModuleName.setToolTipText("");
        textfieldModuleName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textfieldModuleNameActionPerformed(evt);
            }
        });
        textfieldModuleName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textfieldModuleNameFocusLost(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 11)); // NOI18N
        jLabel2.setLabelFor(textfieldModuleName);
        jLabel2.setText("Module Name");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 11)); // NOI18N
        jLabel3.setLabelFor(textfieldModuleID);
        jLabel3.setText("Module ID");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        textfieldModuleID.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        textfieldModuleID.setText("<<Module ID>>");
        textfieldModuleID.setToolTipText("");
        textfieldModuleID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textfieldModuleIDActionPerformed(evt);
            }
        });
        textfieldModuleID.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textfieldModuleIDFocusLost(evt);
            }
        });

        textfieldModuleCategory.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        textfieldModuleCategory.setText("<<Module Category>>");
        textfieldModuleCategory.setToolTipText("");
        textfieldModuleCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textfieldModuleCategoryActionPerformed(evt);
            }
        });
        textfieldModuleCategory.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textfieldModuleCategoryFocusLost(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 11)); // NOI18N
        jLabel4.setLabelFor(textfieldModuleCategory);
        jLabel4.setText("Module Category");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textfieldModuleCategory, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                    .addComponent(textfieldModuleID)
                    .addComponent(textfieldModuleName))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textfieldModuleName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textfieldModuleCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textfieldModuleID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PAR5E Configuration", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 11))); // NOI18N
        jPanel2.setMaximumSize(new java.awt.Dimension(100, 32767));
        jPanel2.setPreferredSize(new java.awt.Dimension(100, 121));
        jPanel2.setSize(new java.awt.Dimension(100, 121));

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 1, 11)); // NOI18N
        jLabel5.setLabelFor(spinnerDebug);
        jLabel5.setText("Debug Level");

        spinnerDebug.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        spinnerDebug.setModel(new javax.swing.SpinnerNumberModel(0, 0, 3, 1));
        spinnerDebug.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerDebugStateChanged(evt);
            }
        });
        spinnerDebug.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                spinnerDebugFocusLost(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 1, 11)); // NOI18N
        jLabel6.setLabelFor(comboRuleset);
        jLabel6.setText("Ruleset");
        jLabel6.setToolTipText("");

        comboRuleset.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        comboRuleset.setFocusTraversalKeysEnabled(false);
        comboRuleset.setFocusable(false);
        comboRuleset.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboRulesetItemStateChanged(evt);
            }
        });
        comboRuleset.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                comboRulesetFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(187, 187, 187)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerDebug, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboRuleset, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinnerDebug, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboRuleset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(14, 14, 14))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Path Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 11))); // NOI18N
        jPanel3.setPreferredSize(new java.awt.Dimension(780, 86));

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 1, 11)); // NOI18N
        jLabel7.setLabelFor(textfieldModulePath);
        jLabel7.setText("Module Source Path");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        textfieldModulePath.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        textfieldModulePath.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textfieldModulePath.setText("<<Module Path>>");
        textfieldModulePath.setToolTipText("");
        textfieldModulePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textfieldModulePathActionPerformed(evt);
            }
        });
        textfieldModulePath.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textfieldModulePathFocusLost(evt);
            }
        });
        textfieldModulePath.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                textfieldModulePathInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });

        jLabel9.setFont(new java.awt.Font("Lucida Grande", 1, 11)); // NOI18N
        jLabel9.setLabelFor(textfieldTempPath);
        jLabel9.setText("Temp Path");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        textfieldTempPath.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        textfieldTempPath.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textfieldTempPath.setText("<<Temp Path>>");
        textfieldTempPath.setToolTipText("");
        textfieldTempPath.setPreferredSize(new java.awt.Dimension(115, 26));
        textfieldTempPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textfieldTempPathActionPerformed(evt);
            }
        });
        textfieldTempPath.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textfieldTempPathFocusLost(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Lucida Grande", 1, 11)); // NOI18N
        jLabel10.setLabelFor(textfieldModuleOutputPath);
        jLabel10.setText("Output Folder");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        textfieldModuleOutputPath.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        textfieldModuleOutputPath.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textfieldModuleOutputPath.setText("<<FG Modules Path>>");
        textfieldModuleOutputPath.setToolTipText("");
        textfieldModuleOutputPath.setPreferredSize(new java.awt.Dimension(115, 26));
        textfieldModuleOutputPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textfieldModuleOutputPathActionPerformed(evt);
            }
        });
        textfieldModuleOutputPath.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textfieldModuleOutputPathFocusLost(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Lucida Grande", 1, 11)); // NOI18N
        jLabel8.setLabelFor(textfieldThumbnailPath);
        jLabel8.setText("Thumbnail Path");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        textfieldThumbnailPath.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        textfieldThumbnailPath.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textfieldThumbnailPath.setText("<<Thumbnail Path>>");
        textfieldThumbnailPath.setToolTipText("");
        textfieldThumbnailPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textfieldThumbnailPathActionPerformed(evt);
            }
        });
        textfieldThumbnailPath.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textfieldThumbnailPathFocusLost(evt);
            }
        });

        buttonModuleSourcePath.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        buttonModuleSourcePath.setIcon(new javax.swing.ImageIcon(getClass().getResource("/par5e/resources/buttons/Folder3.png"))); // NOI18N
        buttonModuleSourcePath.setActionCommand("buttonModuleSourceLoad");
        buttonModuleSourcePath.setBorderPainted(false);
        buttonModuleSourcePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonModuleSourcePathActionPerformed(evt);
            }
        });

        buttonThumbnailPath.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        buttonThumbnailPath.setIcon(new javax.swing.ImageIcon(getClass().getResource("/par5e/resources/buttons/Folder3.png"))); // NOI18N
        buttonThumbnailPath.setActionCommand("buttonModuleThumbnailLoad");
        buttonThumbnailPath.setBorderPainted(false);
        buttonThumbnailPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonThumbnailPathActionPerformed(evt);
            }
        });

        buttonTempPath.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        buttonTempPath.setIcon(new javax.swing.ImageIcon(getClass().getResource("/par5e/resources/buttons/Folder3.png"))); // NOI18N
        buttonTempPath.setActionCommand("buttonModuleTempLoad");
        buttonTempPath.setBorderPainted(false);
        buttonTempPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTempPathActionPerformed(evt);
            }
        });

        buttonModuleOutputPath.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        buttonModuleOutputPath.setIcon(new javax.swing.ImageIcon(getClass().getResource("/par5e/resources/buttons/Folder3.png"))); // NOI18N
        buttonModuleOutputPath.setActionCommand("buttonModuleOutputPath");
        buttonModuleOutputPath.setBorderPainted(false);
        buttonModuleOutputPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonModuleOutputPathActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textfieldModulePath, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textfieldThumbnailPath, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonThumbnailPath, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(buttonModuleSourcePath, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textfieldTempPath, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textfieldModuleOutputPath, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonModuleOutputPath, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonTempPath, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(textfieldModulePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(buttonModuleSourcePath, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonTempPath, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(textfieldThumbnailPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8)
                                .addComponent(jLabel10))
                            .addComponent(buttonThumbnailPath, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonModuleOutputPath, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(textfieldTempPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textfieldModuleOutputPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelConfigLayout = new javax.swing.GroupLayout(panelConfig);
        panelConfig.setLayout(panelConfigLayout);
        panelConfigLayout.setHorizontalGroup(
            panelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelConfigLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
            .addGroup(panelConfigLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        panelConfigLayout.setVerticalGroup(
            panelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigLayout.createSequentialGroup()
                .addGroup(panelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(428, Short.MAX_VALUE))
        );

        tabpaneMain.addTab("Configuration", panelConfig);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Path Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 11))); // NOI18N
        jPanel4.setName(""); // NOI18N

        textfieldArchivePath.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        textfieldArchivePath.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textfieldArchivePath.setToolTipText("");
        textfieldArchivePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textfieldArchivePathActionPerformed(evt);
            }
        });
        textfieldArchivePath.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textfieldArchivePathFocusLost(evt);
            }
        });
        textfieldArchivePath.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                textfieldArchivePathInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });

        jLabel11.setFont(new java.awt.Font("Lucida Grande", 1, 11)); // NOI18N
        jLabel11.setLabelFor(textfieldModulePath);
        jLabel11.setText("Archive Source Path");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        buttonArchivePath.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        buttonArchivePath.setIcon(new javax.swing.ImageIcon(getClass().getResource("/par5e/resources/buttons/Folder3.png"))); // NOI18N
        buttonArchivePath.setActionCommand("buttonModuleThumbnailLoad");
        buttonArchivePath.setBorderPainted(false);
        buttonArchivePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonArchivePathActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textfieldArchivePath, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonArchivePath, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(380, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(textfieldArchivePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(buttonArchivePath, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        scrollpanelArchive.setBackground(java.awt.SystemColor.window);
        scrollpanelArchive.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Archive Data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 11))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 172, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        scrollpanelModule.setBackground(java.awt.SystemColor.window);
        scrollpanelModule.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data to include in Module", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 11))); // NOI18N

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Actions", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 11))); // NOI18N
        jPanel8.setPreferredSize(new java.awt.Dimension(336, 96));

        comboArchiveBulkAction.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        comboArchiveBulkAction.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Copy", "Delete" }));
        comboArchiveBulkAction.setFocusTraversalKeysEnabled(false);
        comboArchiveBulkAction.setFocusable(false);

        buttonArchiveBulkActionApply.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        buttonArchiveBulkActionApply.setText("Apply");
        buttonArchiveBulkActionApply.setFocusPainted(false);
        buttonArchiveBulkActionApply.setFocusTraversalKeysEnabled(false);
        buttonArchiveBulkActionApply.setFocusable(false);
        buttonArchiveBulkActionApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonArchiveBulkActionApplyActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Lucida Grande", 1, 11)); // NOI18N
        jLabel13.setLabelFor(textfieldModulePath);
        jLabel13.setText("Data");
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        comboArchiveData.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        comboArchiveData.setFocusTraversalKeysEnabled(false);
        comboArchiveData.setFocusable(false);
        comboArchiveData.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboArchiveDataItemStateChanged(evt);
            }
        });
        comboArchiveData.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                comboArchiveDataFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                        .addComponent(comboArchiveBulkAction, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonArchiveBulkActionApply, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboArchiveData, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(168, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboArchiveData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboArchiveBulkAction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonArchiveBulkActionApply))
                .addContainerGap())
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Actions", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 11))); // NOI18N
        jPanel9.setPreferredSize(new java.awt.Dimension(336, 63));

        comboModuleBulkAction.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        comboModuleBulkAction.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Delete" }));
        comboModuleBulkAction.setFocusTraversalKeysEnabled(false);
        comboModuleBulkAction.setFocusable(false);

        buttonModuleBulkActionApply.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        buttonModuleBulkActionApply.setText("Apply");
        buttonModuleBulkActionApply.setFocusPainted(false);
        buttonModuleBulkActionApply.setFocusTraversalKeysEnabled(false);
        buttonModuleBulkActionApply.setFocusable(false);
        buttonModuleBulkActionApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonModuleBulkActionApplyActionPerformed(evt);
            }
        });

        comboDataForModule.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        comboDataForModule.setFocusTraversalKeysEnabled(false);
        comboDataForModule.setFocusable(false);
        comboDataForModule.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboDataForModuleItemStateChanged(evt);
            }
        });
        comboDataForModule.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                comboDataForModuleFocusLost(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Lucida Grande", 1, 11)); // NOI18N
        jLabel14.setLabelFor(textfieldModulePath);
        jLabel14.setText("Data");
        jLabel14.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(comboModuleBulkAction, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonModuleBulkActionApply)
                .addGap(0, 170, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboDataForModule, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboDataForModule, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboModuleBulkAction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonModuleBulkActionApply))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelArchiveLayout = new javax.swing.GroupLayout(panelArchive);
        panelArchive.setLayout(panelArchiveLayout);
        panelArchiveLayout.setHorizontalGroup(
            panelArchiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelArchiveLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(panelArchiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelArchiveLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(618, 618, 618))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelArchiveLayout.createSequentialGroup()
                        .addGroup(panelArchiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelArchiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelArchiveLayout.createSequentialGroup()
                                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelArchiveLayout.createSequentialGroup()
                                    .addComponent(scrollpanelArchive, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(scrollpanelModule, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())))
        );
        panelArchiveLayout.setVerticalGroup(
            panelArchiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelArchiveLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelArchiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelArchiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollpanelModule, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scrollpanelArchive, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(271, 271, 271))
        );

        jPanel4.getAccessibleContext().setAccessibleDescription("");

        tabpaneMain.addTab("Archive", panelArchive);

        textpaneConsole.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                textpaneConsoleCaretUpdate(evt);
            }
        });
        scrollpanelConsole.setViewportView(textpaneConsole);

        javax.swing.GroupLayout panelConsoleLayout = new javax.swing.GroupLayout(panelConsole);
        panelConsole.setLayout(panelConsoleLayout);
        panelConsoleLayout.setHorizontalGroup(
            panelConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollpanelConsole, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE)
        );
        panelConsoleLayout.setVerticalGroup(
            panelConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsoleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollpanelConsole, javax.swing.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE))
        );

        tabpaneMain.addTab("Console", panelConsole);

        progressbarStatus.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        progressbarStatus.setString("");

        labelStatus.setFont(new java.awt.Font("Lucida Grande", 1, 11)); // NOI18N
        labelStatus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelStatus.setLabelFor(progressbarStatus);
        labelStatus.setText("Ready");
        labelStatus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        labelStatus.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                labelStatusPropertyChange(evt);
            }
        });

        labelVersion.setFont(new java.awt.Font("Lucida Grande", 1, 11)); // NOI18N
        labelVersion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelVersion.setText("v");
        labelVersion.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        menubarMain.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N

        menuFile.setText("File");
        menuFile.setFont(new java.awt.Font("Lucida Grande", 1, 11)); // NOI18N

        menuitemNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.ALT_MASK));
        menuitemNew.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        menuitemNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/par5e/resources/buttons/Document2.png"))); // NOI18N
        menuitemNew.setText("New");
        menuitemNew.setRolloverEnabled(true);
        menuFile.add(menuitemNew);

        menuitemLoad.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.ALT_MASK));
        menuitemLoad.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        menuitemLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/par5e/resources/buttons/Folder3.png"))); // NOI18N
        menuitemLoad.setText("Load");
        menuitemLoad.setActionCommand("menuitemLoad");
        menuitemLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemLoadActionPerformed(evt);
            }
        });
        menuFile.add(menuitemLoad);

        menuitemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK));
        menuitemSave.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        menuitemSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/par5e/resources/buttons/Save.png"))); // NOI18N
        menuitemSave.setText("Save");
        menuitemSave.setActionCommand("menuitemSave");
        menuitemSave.setOpaque(false);
        menuFile.add(menuitemSave);

        menuitemQuit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.ALT_MASK));
        menuitemQuit.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        menuitemQuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/par5e/resources/buttons/Standby.png"))); // NOI18N
        menuitemQuit.setText("Quit");
        menuitemQuit.setActionCommand("menuitemQuit");
        menuitemQuit.setOpaque(false);
        menuitemQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemQuitActionPerformed(evt);
            }
        });
        menuFile.add(menuitemQuit);

        menubarMain.add(menuFile);

        menuAction.setText("Action");
        menuAction.setFont(new java.awt.Font("Lucida Grande", 1, 11)); // NOI18N

        menuitemParse.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK));
        menuitemParse.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        menuitemParse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/par5e/resources/buttons/Player Play.png"))); // NOI18N
        menuitemParse.setText("Parse");
        menuAction.add(menuitemParse);

        menubarMain.add(menuAction);

        setJMenuBar(menubarMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabpaneMain, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toolbarMain, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(toolbarAction, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addComponent(toolbarQuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(labelVersion, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressbarStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(toolbarMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(toolbarQuit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(toolbarAction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabpaneMain, javax.swing.GroupLayout.PREFERRED_SIZE, 684, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressbarStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelVersion)))
                .addContainerGap())
        );

        tabpaneMain.getAccessibleContext().setAccessibleName("Configuration");
        tabpaneMain.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
         
    private void textfieldModuleOutputPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textfieldModuleOutputPathActionPerformed
        updateOutputPath();
    }//GEN-LAST:event_textfieldModuleOutputPathActionPerformed

    private void textfieldTempPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textfieldTempPathActionPerformed
        updateTempPath();
    }//GEN-LAST:event_textfieldTempPathActionPerformed

    private void textfieldThumbnailPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textfieldThumbnailPathActionPerformed
        updateThumbnailPath();
    }//GEN-LAST:event_textfieldThumbnailPathActionPerformed

    private void textfieldModulePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textfieldModulePathActionPerformed
        updateModulePath();
    }//GEN-LAST:event_textfieldModulePathActionPerformed

    private void textfieldModuleCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textfieldModuleCategoryActionPerformed
        updateModuleCategory();
    }//GEN-LAST:event_textfieldModuleCategoryActionPerformed

    private void textfieldModuleIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textfieldModuleIDActionPerformed
        updateModuleID();
    }//GEN-LAST:event_textfieldModuleIDActionPerformed

    private void menuitemLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemLoadActionPerformed
        if (! parserEngine.isRunning()) {
            loadConfiguration();
        }
    }//GEN-LAST:event_menuitemLoadActionPerformed

    private void buttonLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLoadActionPerformed
        if (! parserEngine.isRunning()) {
            loadConfiguration();
        }
    }//GEN-LAST:event_buttonLoadActionPerformed
       
    private void textfieldModuleNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textfieldModuleNameActionPerformed
        updateModuleName();    
    }//GEN-LAST:event_textfieldModuleNameActionPerformed

    private void spinnerDebugStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerDebugStateChanged
        updateDebugLevel();
    }//GEN-LAST:event_spinnerDebugStateChanged

    private void buttonQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonQuitActionPerformed
        closeWindow();
    }//GEN-LAST:event_buttonQuitActionPerformed

    private void menuitemQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemQuitActionPerformed
        closeWindow();
    }//GEN-LAST:event_menuitemQuitActionPerformed

    private void buttonModuleSourcePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonModuleSourcePathActionPerformed
        try {      
            String FilePath = loadFolderDialog();
            if (FilePath != null) {
                textfieldModulePath.setText(FilePath);
                updateModulePath();
            }
        } catch (IOException ex) {
            Logger.getLogger(PAR5E.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonModuleSourcePathActionPerformed

    private void buttonThumbnailPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonThumbnailPathActionPerformed
        try {      
            String FilePath = loadFileDialog(2);
            if (FilePath != null) {
                textfieldThumbnailPath.setText(FilePath);
                updateThumbnailPath();
            }
        } catch (IOException ex) {
            Logger.getLogger(PAR5E.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonThumbnailPathActionPerformed

    private void buttonTempPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTempPathActionPerformed
        try {      
            String FilePath = loadFolderDialog();
            if (FilePath != null) {
                textfieldTempPath.setText(FilePath);
                updateTempPath();
            }
        } catch (IOException ex) {
            Logger.getLogger(PAR5E.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonTempPathActionPerformed

    private void buttonModuleOutputPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonModuleOutputPathActionPerformed
        try {      
            String FilePath = loadFolderDialog();
            if (FilePath != null) {
                textfieldModuleOutputPath.setText(FilePath);
                updateOutputPath();
            }
        } catch (IOException ex) {
            Logger.getLogger(PAR5E.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonModuleOutputPathActionPerformed

    private void textfieldModuleNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textfieldModuleNameFocusLost
        updateModuleName(); 
    }//GEN-LAST:event_textfieldModuleNameFocusLost

    private void textfieldModuleIDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textfieldModuleIDFocusLost
        updateModuleID();
    }//GEN-LAST:event_textfieldModuleIDFocusLost

    private void textfieldModuleCategoryFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textfieldModuleCategoryFocusLost
        updateModuleCategory();
    }//GEN-LAST:event_textfieldModuleCategoryFocusLost

    private void spinnerDebugFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_spinnerDebugFocusLost
        updateDebugLevel();
    }//GEN-LAST:event_spinnerDebugFocusLost

    private void textfieldModulePathInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_textfieldModulePathInputMethodTextChanged

    }//GEN-LAST:event_textfieldModulePathInputMethodTextChanged

    private void textfieldModulePathFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textfieldModulePathFocusLost
        updateModulePath();
    }//GEN-LAST:event_textfieldModulePathFocusLost

    private void textfieldThumbnailPathFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textfieldThumbnailPathFocusLost
        updateThumbnailPath();
    }//GEN-LAST:event_textfieldThumbnailPathFocusLost

    private void textfieldTempPathFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textfieldTempPathFocusLost
        updateTempPath();
    }//GEN-LAST:event_textfieldTempPathFocusLost

    private void textfieldModuleOutputPathFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textfieldModuleOutputPathFocusLost
        updateOutputPath();
    }//GEN-LAST:event_textfieldModuleOutputPathFocusLost

    private void comboRulesetItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboRulesetItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            updateRulesetLibrary();
        }
    }//GEN-LAST:event_comboRulesetItemStateChanged

    private void comboRulesetFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_comboRulesetFocusLost
        updateRulesetLibrary();
    }//GEN-LAST:event_comboRulesetFocusLost

    private void buttonParseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonParseActionPerformed
        parseData();
    }//GEN-LAST:event_buttonParseActionPerformed

    private void tabpaneMainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabpaneMainMouseClicked
        if (! parserEngine.isRunning() ) {
            tabpaneMain.setEnabledAt(tabpaneMain.indexOfTab("Configuration"), true);
        }
    }//GEN-LAST:event_tabpaneMainMouseClicked

    private void labelStatusPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_labelStatusPropertyChange
        if (parserEngine != null && parserEngine.isDone()) {
            SwingUtilities.invokeLater( new Runnable(){
            @Override
                public void run(){
                    try {
                        Thread.sleep(500);
                        labelStatus.setText("Ready");
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PAR5E.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
         }
    }//GEN-LAST:event_labelStatusPropertyChange

    private void buttonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveActionPerformed
         if (! parserEngine.isRunning()) {
            saveConfiguration();
        }
    }//GEN-LAST:event_buttonSaveActionPerformed

    private void textpaneConsoleCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_textpaneConsoleCaretUpdate
        
    }//GEN-LAST:event_textpaneConsoleCaretUpdate

    private void textfieldArchivePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textfieldArchivePathActionPerformed
        updateArchivePath();
    }//GEN-LAST:event_textfieldArchivePathActionPerformed

    private void textfieldArchivePathFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textfieldArchivePathFocusLost
        updateArchivePath();
    }//GEN-LAST:event_textfieldArchivePathFocusLost

    private void textfieldArchivePathInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_textfieldArchivePathInputMethodTextChanged
        updateArchivePath();
    }//GEN-LAST:event_textfieldArchivePathInputMethodTextChanged

    private void buttonArchivePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonArchivePathActionPerformed
        try {      
            String FilePath = loadFileDialog(2);
            if (FilePath != null) {
                textfieldArchivePath.setText(FilePath);
                updateArchivePath();
            }
        } catch (IOException ex) {
            Logger.getLogger(PAR5E.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonArchivePathActionPerformed

    private void comboArchiveDataItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboArchiveDataItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            updateArchiveTableData(comboArchiveData.getSelectedItem().toString());
        }
    }//GEN-LAST:event_comboArchiveDataItemStateChanged

    private void comboArchiveDataFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_comboArchiveDataFocusLost
        updateArchiveTableData(comboArchiveData.getSelectedItem().toString());
    }//GEN-LAST:event_comboArchiveDataFocusLost

    private void buttonArchiveBulkActionApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonArchiveBulkActionApplyActionPerformed
 
        DefaultTableModel tadm = (DefaultTableModel) tableArchiveData.getModel();
        DefaultTableModel tmdm = (DefaultTableModel) tableModuleData.getModel();
        int[] selected_rows = tableArchiveData.getSelectedRows();
        
        switch (comboArchiveBulkAction.getSelectedItem().toString()) {
            case "Copy": {
                for (int row : selected_rows) {
                    String name = tadm.getValueAt(row,0).toString();
                    String source = tadm.getValueAt(row,1).toString();
                    comboDataForModule.setSelectedItem(comboArchiveData.getSelectedItem());
                    tmdm.addRow(new Object []{name, source});
                    List<?> objects = archive.getObject(comboRuleset.getSelectedItem().toString(), comboArchiveData.getSelectedItem().toString(), name);
                    for (Object obj : objects) {                
                        tmparch.getObjects().store(obj);
                    }
                }   
                break;
            }
            case "Delete": {
                ArrayUtils.reverse(selected_rows);
                for (int row : selected_rows) {
                    String name = tadm.getValueAt(row,0).toString();
                    String source = tadm.getValueAt(row,1).toString();
                    tadm.removeRow(tableArchiveData.convertRowIndexToModel(row));
                    List<?> objects = archive.getObject(comboRuleset.getSelectedItem().toString(), comboArchiveData.getSelectedItem().toString(), name);
                    for (Object obj : objects) {                
                        archive.getObjects().delete(obj);
                    }
                }   
                tableArchiveData.clearSelection();
                break;
            }
        }
         
    }//GEN-LAST:event_buttonArchiveBulkActionApplyActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        closeWindow();
    }//GEN-LAST:event_formWindowClosing

    private void comboDataForModuleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboDataForModuleItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            updateModuleTableData(comboDataForModule.getSelectedItem().toString());
        }
    }//GEN-LAST:event_comboDataForModuleItemStateChanged

    private void comboDataForModuleFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_comboDataForModuleFocusLost
        updateModuleTableData(comboDataForModule.getSelectedItem().toString());
    }//GEN-LAST:event_comboDataForModuleFocusLost

    private void buttonModuleBulkActionApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonModuleBulkActionApplyActionPerformed
        DefaultTableModel tmdm = (DefaultTableModel) tableModuleData.getModel();
        int[] selected_rows = tableModuleData.getSelectedRows();
        
        switch (comboModuleBulkAction.getSelectedItem().toString()) {
            case "Delete": {
                ArrayUtils.reverse(selected_rows);
                for (int row : selected_rows) {
                    String name = tmdm.getValueAt(row,0).toString();
                    String source = tmdm.getValueAt(row,1).toString();
                    tmdm.removeRow(tableModuleData.convertRowIndexToModel(row));
                    List<?> objects = tmparch.getObject(comboRuleset.getSelectedItem().toString(), comboDataForModule.getSelectedItem().toString(), name);
                    for (Object obj : objects) {                
                        tmparch.getObjects().delete(obj);
                    }
                }   
                tableModuleData.clearSelection();
                break;
            }
        }
    }//GEN-LAST:event_buttonModuleBulkActionApplyActionPerformed

    //Field Update Handlers
    private void updateDebugLevel() {
        debug = (Integer) spinnerDebug.getValue();
    }
    private void updateRulesetLibrary() {
        try {
            updateModuleRuleset();   
            parserEngine.setRulesetLibrary(comboRuleset.getSelectedItem().toString());
            dataItems = parserEngine.getDataItems();
            initDataItems();
            initRulesetArchive();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PAR5E.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void updateModuleRuleset() {
        module.setRuleset(comboRuleset.getSelectedItem().toString());   
    }
    private void updateModuleName() {
        module.setName(textfieldModuleName.getText());
    }
    private void updateModuleID() {
        module.setID(textfieldModuleID.getText());
    }
    private void updateModuleCategory() {
        module.setCategory(textfieldModuleCategory.getText());
    }
    private void updateModulePath() {
        module.setPath(textfieldModulePath.getText());
    }
    private void updateModuleDataItems() {
        
        LinkedHashMap<String, BitSet> moduleDataItems = new LinkedHashMap<>();
        LinkedHashMap<String, String> moduleDataItemPaths = new LinkedHashMap<>();
        int rowCount = tableDataItems.getRowCount();
        boolean campaign;
        boolean reference;
        boolean database;
        
        for (int i=0; i <= rowCount-1;i++) {
            BitSet bs = new BitSet(3);  
            campaign = false;
            reference = false;
            database = false;
            
            if (tableDataItems.getValueAt(i, 2) != null && tableDataItems.getValueAt(i, 2).toString().equals("true")) {
              campaign = true;
            }
            if (tableDataItems.getValueAt(i, 3) != null && tableDataItems.getValueAt(i, 3).toString().equals("true")) {
              reference = true;
            }
            if (tableDataItems.getValueAt(i, 4) != null && tableDataItems.getValueAt(i, 4).toString().equals("true")) {
              database = true;
            }
            
            bs.set(0, campaign);
            bs.set(1, reference);
            bs.set(2, database);
            
            moduleDataItems.put(tableDataItems.getValueAt(i, 0).toString(), bs);
            moduleDataItemPaths.put(tableDataItems.getValueAt(i, 0).toString(), tableDataItems.getValueAt(i, 1).toString());
 
        }
        module.setDataItems(moduleDataItems);
        module.setDataItemPaths(moduleDataItemPaths);
    }  
    private void updateThumbnailPath() {
        module.setThumbnailPath(textfieldThumbnailPath.getText());
    }
    private void updateTempPath() {
        module.setTempPath(textfieldTempPath.getText());
    }
    private void updateOutputPath() {
        module.setOutputPath(textfieldModuleOutputPath.getText());
    }
    private void updateArchivePath() {
        if (!init) {
            archive.open(textfieldArchivePath.getText());
        }
    }
    private void loadConfiguration() {
        try { 
            String FilePath = loadFileDialog(0);
            if (FilePath != null) {
                loadConfigurationFile(FilePath);
            }
        } catch (IOException ex) {
            Logger.getLogger(PAR5E.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void saveConfiguration() {
        try { 
            String FilePath = saveFileDialog();
            if (FilePath != null) {
                saveConfigurationFile(FilePath);
            }
        } catch (IOException ex) {
            Logger.getLogger(PAR5E.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void newModule() {
        module = new Module();
    }
    private void parseData() {
        
        parserEngine = new PAR5EEngine(); 
        parserEngine.setArchive(archive.getObjects());
        parserEngine.setTmpArchive(tmparch.getObjects());
        parserEngine.setOutput(textpaneConsole);
        parserEngine.setProgressBarComp(progressbarStatus);
        parserEngine.setStatusLabelComp(labelStatus);
        parserEngine.setParseButtonComp(buttonParse);
        parserEngine.setPanelComp(tabpaneMain);
        parserEngine.setDebugLevel(debug);
        parserEngine.setModule(module);
        
        if (!tmparch.isEmpty()) {
           parserEngine.setArchiveImport(true); 
        }
        
        if (! parserEngine.isRunning() ) {
            progressbarStatus.setStringPainted(true);
            
            updateModuleDataItems();
            tabpaneMain.setSelectedIndex(tabpaneMain.indexOfTab("Console"));
            tabpaneMain.setEnabledAt(tabpaneMain.indexOfTab("Configuration"), false);
            buttonParse.setEnabled(false);
            
            try {
                parserEngine.setRulesetLibrary(comboRuleset.getSelectedItem().toString());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PAR5E.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            parserEngine.initialise();     
            parserEngine.execute();

        }   
    }    
    
    //Helper Routines
    private String saveFileDialog() throws IOException {
        JFileChooser chooser = new JFileChooser();
        String userHome = System.getProperty("user.home");
        
        if (module.getPath() != null ) {
            chooser.setCurrentDirectory(new java.io.File(module.getPath()));
        } else {
            chooser.setCurrentDirectory(new java.io.File(userHome));
        }
        chooser.setDialogTitle("Save Configuration File");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Properties file", new String[] {"properties", "prop"});
        chooser.setFileFilter(filter);
        chooser.addChoosableFileFilter(filter);
        
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            return file.getCanonicalPath();
        } else {
            return null;
        }                
    }  
    private String loadFileDialog(Integer mode) throws IOException {
        JFileChooser chooser = new JFileChooser();
        String userHome = System.getProperty("user.home");
        FileNameExtensionFilter filter = null; 
        
        if (module.getPath() != null ) {
            chooser.setCurrentDirectory(new java.io.File(module.getPath()));
        } else {
            chooser.setCurrentDirectory(new java.io.File(userHome));
        }
        chooser.setDialogTitle("Open Configuration File");
       
        switch(mode){
            case 0: filter = new FileNameExtensionFilter("Properties file", new String[] {"properties", "prop"});
                break;
            case 1: filter = new FileNameExtensionFilter("Text file", new String[] {"text", "txt"});
                break;
            case 2: filter = new FileNameExtensionFilter("PNG file", new String[] {"png"});
                break;
        }
        
        if (filter != null) {
            chooser.setFileFilter(filter);
            chooser.addChoosableFileFilter(filter);
        }
        
       // chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //chooser.setAcceptAllFileFilterUsed(false);
        
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            return file.getCanonicalPath();
        } else {
            return null;
        }                
    }  
    private String loadFolderDialog() throws IOException {
        JFileChooser chooser = new JFileChooser();
        String userHome = System.getProperty("user.home");
        
        if (module.getPath() != null ) {
            chooser.setCurrentDirectory(new java.io.File(module.getPath()));
        } else {
            chooser.setCurrentDirectory(new java.io.File(userHome));
        }
        
        chooser.setDialogTitle("Select Folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            return file.getCanonicalPath();
        } else {
            return null;
        }                
    }
    private void loadConfigurationFile(String FilePath) {
        Properties config = new Properties();
        InputStream in = null;
        File f = new File(FilePath);
        DefaultTableModel tModel;
        
        try {
            in = new FileInputStream(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PAR5E.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                config.load(in);
            } catch (IOException ex) {
                Logger.getLogger(PAR5E.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        textfieldModuleName.setText(config.getProperty("name"));
        updateModuleName();
        textfieldModuleID.setText(config.getProperty("id"));
        updateModuleID();
        textfieldModuleCategory.setText(config.getProperty("category"));
        updateModuleCategory();
        spinnerDebug.setValue(Integer.parseInt(config.getProperty("debug")));
        updateDebugLevel();
        comboRuleset.setSelectedItem(config.getProperty("ruleset"));
        updateRulesetLibrary();
        
        textfieldModulePath.setText(config.getProperty("path"));
        updateModulePath();
        textfieldThumbnailPath.setText(config.getProperty("thumbnailpath"));
        updateThumbnailPath();
        textfieldTempPath.setText(config.getProperty("temppath"));
        updateTempPath();
        textfieldModuleOutputPath.setText(config.getProperty("outputpath"));
        updateOutputPath();
        
        String[] dItemPaths = config.getProperty("dataitempaths").split(",");
        String[] dItemOutput = config.getProperty("dataitemoutput").split(";");
        
        tModel = (DefaultTableModel)tableDataItems.getModel();
        Integer tableRows = tModel.getRowCount();
        
        for (Integer i=0; i<tableRows;i++) {
            String dataItem = tModel.getValueAt(i,0).toString();
            String dataPath = dItemPaths[i].toString();
            String dataOutput = dItemOutput[i].toString();
            
            tModel.setValueAt(dataPath, i, 1);
            switch(dataOutput) {                    
                case "0,0,1": {
                    tModel.setValueAt(true, i, 4);
                    break;
                }                            
                case "0,1,0": {
                    tModel.setValueAt(true, i, 3);
                    break;
                }  
                case "0,1,1": {
                    tModel.setValueAt(true, i, 3);
                    tModel.setValueAt(true, i, 4);
                    break;
                }
                case "1,0,0": {
                    tModel.setValueAt(true, i, 2);
                    break;
                } 
                case "1,1,0": {
                    tModel.setValueAt(true, i, 2);
                    tModel.setValueAt(true, i, 3);
                    break;
                }
                case "1,1,1": {
                    tModel.setValueAt(true, i, 2);
                    tModel.setValueAt(true, i, 3);
                    tModel.setValueAt(true, i, 4);
                    break;
                } 
            }
        }
        updateModuleDataItems();
        
    }
    
    @SuppressWarnings("null")
    private void saveConfigurationFile(String FilePath) {
        Properties config = new Properties();
        OutputStream out = null;
        File f = new File(FilePath);
        String dItems = "";
        String dItemPaths = "";
        String dItemOutput = "";       
       
        config.setProperty("name", module.getName());
        config.setProperty("id", module.getID());
        config.setProperty("category", module.getCategory());
        config.setProperty("ruleset", comboRuleset.getSelectedItem().toString());
        config.setProperty("path", module.getPath());
        config.setProperty("thumbnailpath", module.getThumbnailPath());
        config.setProperty("temppath", module.getTempPath());
        config.setProperty("outputpath", module.getOutputPath());
        updateModuleDataItems();
        
        Iterator itDataItems = module.getDataItems().entrySet().iterator();
        // Loop through all module data items and parse source data
        while (itDataItems.hasNext()) {
            Map.Entry entry = (Map.Entry) itDataItems.next();
            String diName = (String)entry.getKey();
            BitSet diOutputMode = (BitSet)entry.getValue();
            
            Integer tableRow = getRowID(diName);
            String outputcampaign = "0";
            String outputreference = "0";
            String outputdatabase = "0";
            
            if (tableRow != -1) {
                String aTmpString;
                DefaultTableModel tModel = (DefaultTableModel)tableDataItems.getModel();

                if (tModel.getValueAt(tableRow, 2) != null && tModel.getValueAt(tableRow,2).toString().equals("true")) {
                    outputcampaign = "1";
                }
                if (tModel.getValueAt(tableRow, 3) != null && tModel.getValueAt(tableRow,3).toString().equals("true")) {
                    outputreference = "1";
                }
                if (tModel.getValueAt(tableRow, 4) != null && tModel.getValueAt(tableRow,4).toString().equals("true")) {
                    outputdatabase = "1";
                }

                dItems = dItems + diName + ",";
                dItemOutput = dItemOutput + outputcampaign + "," + outputreference + "," + outputdatabase + ";";
                
                if (tModel.getValueAt(tableRow, 1) != null ) {
                    dItemPaths = dItemPaths + tModel.getValueAt(tableRow, 1) + ",";
                }
            }
        }
        
        config.setProperty("dataitems", dItems);
        config.setProperty("dataitemoutput", dItemOutput);
        config.setProperty("dataitempaths", dItemPaths);
        
        config.setProperty("debug", debug.toString());

        try {           
           out = new FileOutputStream(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PAR5E.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                config.store(out, "PAR5E Module " + packageBuild.getProperty("build").toString());
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(PAR5E.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    @SuppressWarnings("unchecked")
    private void initRulesetLibraries() throws IOException {
        
        rulesetLibraries.load(getClass().getResourceAsStream("libraries.properties"));
        final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        String[] rulesets = rulesetLibraries.getProperty("rulesets").split(",");
        for (String ruleset : rulesets) {
            model.addElement(ruleset);
        }
        
        comboRuleset.setModel(model);
        String selectedRulesetLib = comboRuleset.getSelectedItem().toString();
    }  
    @SuppressWarnings("unchecked")
    private void initRulesetArchive() {
        final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        String[] archiveclasses = {"NPCs", "Magic Items"};
        for (String cls : archiveclasses) {
            model.addElement(cls);
        }
        comboArchiveData.setModel(model);
        comboArchiveData.setSelectedIndex(0);
    }
    @SuppressWarnings("unchecked")
    private void initTmpArchive() {
        final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        String[] archiveclasses = {"NPCs", "Magic Items"};
        for (String cls : archiveclasses) {
            model.addElement(cls);
        }
        comboDataForModule.setModel(model);
        comboDataForModule.setSelectedIndex(0);
    }
    
    private void initComponentToolTips() {
        
        textfieldModuleName.setToolTipText("Enter a name name for the Module (note: the name must not contain non alpha-numeric characters).");
        textfieldModuleCategory.setToolTipText("Enter a category name for the module (e.g. Core Rules or Supplement).");
        textfieldModuleID.setToolTipText("Enter a short unique identifier for the module (e.g. TM1).");
        comboRuleset.setToolTipText("Select a ruleset (note: selecting a ruleset resets data items and output settings).");
        spinnerDebug.setToolTipText("Set the debug level of the PAR5E engine (default 1). Higher values increase output verbosity to standard out.");
        textfieldModulePath.setToolTipText("Enter the top level path of the module (note: data item source files and folders will be sourced relatively to this path).");
        textfieldThumbnailPath.setToolTipText("Enter the path to a suitably sized thumbnail.png (e.g. 100x100px) for use as the modules thumbnail image.");
        textfieldTempPath.setToolTipText("Enter a path to a temporary folder that will be used to host the parsed data output files prior to archiving in a \nFantasyGrounds .mod module file. (note: ensure the folder is empty before starting a parse action).");
        textfieldModuleOutputPath.setToolTipText("Enter the final path of the created module file. (note: set to the FantasyGrounds app data/modules folder \nif you want the module to be available in FantasyGrounds).");
        tabpaneMain.setToolTipTextAt(0, "Select to change configuration of the PAR5E engine and Module.");
        tabpaneMain.setToolTipTextAt(1, "Select to view output of the PAR5E engine, parsing and module creation process.");
        tableDataItems.setToolTipText("Set data item paths (note: all paths a relative to module path). Select data output options Campaign (Cmp), Reference Library (Ref) and/or database (Db).");       
        
        buttonNew.setToolTipText("Select to reset and clear configuration.");
        buttonLoad.setToolTipText("Select to load a new configuration file.");
        buttonSave.setToolTipText("Select to save configuration to a file.");
        buttonParse.setToolTipText("Select to start the parse and module creation process.");
        buttonQuit.setToolTipText("Select to Exit the program.");
        
        menuitemNew.setToolTipText("Select to reset and clear configuration.");
        menuitemLoad.setToolTipText("Select to load a new configuration file.");
        menuitemSave.setToolTipText("Select to save configuration to a file.");
        menuitemParse.setToolTipText("Select to start the parse and module creation process.");
        menuitemQuit.setToolTipText("Select to Exit the program.");
        
    }
    private void updatePanelConfigLayout() {
        panelConfig.setLayout(panelConfigLayout);
        
        panelConfigLayout.setHorizontalGroup(
            panelConfigLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigLayout.createSequentialGroup()
                .addGroup(panelConfigLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(GroupLayout.Alignment.LEADING, panelConfigLayout.createSequentialGroup()
                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(scrollpanelDataItems, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 785, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelConfigLayout.setVerticalGroup(
            panelConfigLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollpanelDataItems, GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                .addContainerGap())
        );
        
        scrollpanelDataItems.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ruleset Data Items", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Lucida Grande", 1, 11))); 
        scrollpanelDataItems.setBackground(SystemColor.window);
    } 
    private void updateDataItemTableHeaders() {
         try {
           tableDataItems.setDefaultRenderer (Class.forName("java.lang.Boolean"), new CheckBoxCellRenderer());
           JTableHeader header = tableDataItems.getTableHeader();
           header.setDefaultRenderer(new HeaderCellRenderer(tableDataItems));
         } catch (ClassNotFoundException ex) {
           Logger.getLogger(PAR5E.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    private void updateArchiveTableHeaders() {
        JTableHeader header = tableArchiveData.getTableHeader();
        header.setDefaultRenderer(new ArchiveHeaderCellRenderer(tableArchiveData));
    }
    private void updateModuleTableHeaders() {
        JTableHeader header = tableModuleData.getTableHeader();
        header.setDefaultRenderer(new ArchiveHeaderCellRenderer(tableModuleData));
    }
    private void initDataItemsTable() {
        
        //Define the table data model
        tableDataItems.setModel(new DefaultTableModel( new Object [][] { },
            new String [] {"Data Item", "Relative Source File/Folder Path", "Cmp", "Ref", "Arch", "" }) {
            Class[] types = new Class [] {String.class, String.class, Boolean.class, Boolean.class, Boolean.class, BitSet.class};

            @Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            
            @Override
            public boolean isCellEditable(int row, int col) {
                return enableDataItemCell(row, col);
            }   
        });
        
        //Set table attributes and settings
        tableDataItems.getColumnModel().getColumn(0).setCellRenderer(new NonEditCellRenderer());
        tableDataItems.setFont(new java.awt.Font("Lucida Grande", 0, 11));  
        tableDataItems.setFillsViewportHeight(true);
        tableDataItems.setMaximumSize(new Dimension(500, 96));
        tableDataItems.setShowGrid(true);
        tableDataItems.setShowVerticalLines(false);
        tableDataItems.getTableHeader().setResizingAllowed(false);
        tableDataItems.getTableHeader().setReorderingAllowed(false);
        scrollpanelDataItems.setViewportView(tableDataItems);
        
        tableDataItems.getTableHeader().setFont(new Font("Lucinda Grande", Font.BOLD, 11));
        
        if (tableDataItems.getColumnModel().getColumnCount() > 0) {
            tableDataItems.getColumnModel().getColumn(0).setResizable(false);
            tableDataItems.getColumnModel().getColumn(0).setPreferredWidth(100);
              
            tableDataItems.getColumnModel().getColumn(1).setResizable(false);
            tableDataItems.getColumnModel().getColumn(1).setPreferredWidth(380);
            tableDataItems.getColumnModel().getColumn(2).setResizable(false);
            tableDataItems.getColumnModel().getColumn(2).setPreferredWidth(10);
            tableDataItems.getColumnModel().getColumn(3).setResizable(false);
            tableDataItems.getColumnModel().getColumn(3).setPreferredWidth(10);
            tableDataItems.getColumnModel().getColumn(4).setResizable(false);
            tableDataItems.getColumnModel().getColumn(4).setPreferredWidth(10);             

            TableColumn tc = tableDataItems.getColumnModel().getColumn(5);
            tableDataItems.getColumnModel().removeColumn(tc);
        }
    } 
    private void initArchiveTable() {
        //Define the table data model
        tableArchiveData.setModel(new DefaultTableModel( new Object [][] { },
            new String [] { "Name", "Source" } ) {
            Class[] types = new Class [] {String.class, String.class};
            
            @Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            } 
            
            });
        
        //Set table attributes and settings
        tableArchiveData.getColumnModel().getColumn(0).setCellRenderer(new ArchiveNonEditCellRenderer());
        tableArchiveData.getColumnModel().getColumn(1).setCellRenderer(new ArchiveNonEditCellRenderer());
        
        tableArchiveData.setFont(new java.awt.Font("Lucida Grande", 0, 11));  
        tableArchiveData.setFillsViewportHeight(true);
        tableArchiveData.setShowGrid(true);
        tableArchiveData.setShowVerticalLines(false);
        tableArchiveData.getTableHeader().setResizingAllowed(false);
                
        scrollpanelArchive.setViewportView(tableArchiveData);
        
        tableArchiveData.getTableHeader().setFont(new Font("Lucinda Grande", Font.BOLD, 11));
        
        if (tableArchiveData.getColumnModel().getColumnCount() > 0) {
            tableArchiveData.getColumnModel().getColumn(0).setResizable(false);
            tableArchiveData.getColumnModel().getColumn(0).setPreferredWidth(200);
            tableArchiveData.getColumnModel().getColumn(1).setResizable(false);
            tableArchiveData.getColumnModel().getColumn(1).setPreferredWidth(150);
        }
    }
    private void initModuleTable() {
        //Define the table data model
        tableModuleData.setModel(new DefaultTableModel( new Object [][] { },
            new String [] { "Name", "Source"} ) {
            Class[] types = new Class [] {String.class, String.class};
            
            @Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            } 
            
            });
        
        //Set table attributes and settings
        tableModuleData.getColumnModel().getColumn(0).setCellRenderer(new ArchiveNonEditCellRenderer());
        tableModuleData.getColumnModel().getColumn(1).setCellRenderer(new ArchiveNonEditCellRenderer());
        
        tableModuleData.setFont(new java.awt.Font("Lucida Grande", 0, 11));  
        tableModuleData.setFillsViewportHeight(true);
        tableModuleData.setShowGrid(true);
        tableModuleData.setShowVerticalLines(false);
        tableModuleData.getTableHeader().setResizingAllowed(false);
                
        tableModuleData.getTableHeader().setReorderingAllowed(true);
        scrollpanelModule.setViewportView(tableModuleData);
        
        tableModuleData.getTableHeader().setFont(new Font("Lucinda Grande", Font.BOLD, 11));
        
        if (tableModuleData.getColumnModel().getColumnCount() > 0) {
            tableModuleData.getColumnModel().getColumn(0).setResizable(false);
            tableModuleData.getColumnModel().getColumn(0).setPreferredWidth(200);
            tableModuleData.getColumnModel().getColumn(1).setResizable(false);
            tableModuleData.getColumnModel().getColumn(1).setPreferredWidth(150);
         }
    }
    private void initDataItems() {      
        DefaultTableModel tModel = (DefaultTableModel) tableDataItems.getModel();
        Iterator it = dataItems.entrySet().iterator();
       
        if (tModel.getRowCount() > 0 ) {
            tModel.setRowCount(0);
        }
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String)entry.getKey();
            BitSet val = (BitSet)entry.getValue();
            if (key.equals("images") || key.equals("tokens")) {
                tModel.addRow(new Object []{key, "input" + slash + key, null, null, null, val});   
            } else {
                tModel.addRow(new Object []{key, "input" + slash + key + ".txt", null, null, null, val});   
            }   
        } 
        
        updateArchiveTableData(comboArchiveData.getSelectedItem().toString());
    }
    private void updateArchiveTableData(String type) {
        DefaultTableModel tModel = (DefaultTableModel) tableArchiveData.getModel();
        List<?> objects = archive.getObjects(comboRuleset.getSelectedItem().toString(), type);
        
        if (tModel.getRowCount() > 0 ) {
            tModel.setRowCount(0);
        }

        for (Object obj : objects) {
            CoreRPG_BaseClass object = (CoreRPG_BaseClass) obj; 
            tModel.addRow(new Object []{object.getName(), object.getSource()});   
        }
    }
    private void updateModuleTableData(String type) {
        DefaultTableModel tModel = (DefaultTableModel) tableModuleData.getModel();
        List<?> objects = tmparch.getObjects(comboRuleset.getSelectedItem().toString(), type);
        
        if (tModel.getRowCount() > 0 ) {
            tModel.setRowCount(0);
        }

        for (Object obj : objects) {
            CoreRPG_BaseClass object = (CoreRPG_BaseClass) obj; 
            tModel.addRow(new Object []{object.getName(), object.getSource()});   
        }
    }
    
    private boolean enableDataItemCell(int row, int col) {
        DefaultTableModel tModel = (DefaultTableModel) tableDataItems.getModel();
        String dataitemName = tableDataItems.getValueAt(row, 0).toString();
        BitSet bs = (BitSet)dataItems.get(dataitemName);

        if (col == 0) {
            return false;
        } else if (col == 1) {
            return true;
        } else if (col > 1 && col < 5) {
            int map = col - 2;
            return bs.get(map);
        } else {
            return false;
        }  
    }      
    private void disableControls() {
        // Tab Frame Controls        
        Component[] components = this.getComponents();
        for (Component component : components) {
            component.setEnabled(false);
        }
    }
    private void enableControls() {
                // Tab Frame Controls        
        Component[] components = this.getComponents();
        for (Component component : components) {
            component.setEnabled(true);
        }
    }
    private Integer getRowID(String aDataItemName) {
        
        Integer rowCount = tableDataItems.getRowCount();
        
        for (Integer i=0; i <= rowCount-1; i++) {
            if ( tableDataItems.getValueAt(i,0).toString().equals(aDataItemName) ) {
                return i;
            }
        }
        return -1;
        
    }

    private void closeWindow() {
        int confirm = JOptionPane.showOptionDialog(null,
                "Are You Sure to Close this Application?",
                "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (confirm == JOptionPane.YES_OPTION) {
            archive.close();
            tmparch.close();
            File tArchive = new File(System.getProperty("user.dir") + slash + "tmparch.db4o");
            tArchive.delete();
            System.exit(0);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            OUTER:
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                switch (info.getName()) {
                    case "Windows":
                        UIManager.setLookAndFeel(info.getClassName());
                        break OUTER;
                    case "Mac OS X":
                        UIManager.setLookAndFeel(info.getClassName());
                        break OUTER;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PAR5E.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        aArgs = args;
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new PAR5E().setVisible(true);
                    
                } catch (IOException ex) {
                    Logger.getLogger(PAR5E.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonArchiveBulkActionApply;
    private javax.swing.JButton buttonArchivePath;
    private javax.swing.JButton buttonLoad;
    private javax.swing.JButton buttonModuleBulkActionApply;
    private javax.swing.JButton buttonModuleOutputPath;
    private javax.swing.JButton buttonModuleSourcePath;
    private javax.swing.JButton buttonNew;
    private javax.swing.JButton buttonParse;
    private javax.swing.JButton buttonQuit;
    private javax.swing.JButton buttonSave;
    private javax.swing.JButton buttonTempPath;
    private javax.swing.JButton buttonThumbnailPath;
    private javax.swing.JComboBox comboArchiveBulkAction;
    private javax.swing.JComboBox comboArchiveData;
    private javax.swing.JComboBox comboDataForModule;
    private javax.swing.JComboBox comboModuleBulkAction;
    private javax.swing.JComboBox comboRuleset;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JLabel labelVersion;
    private javax.swing.JMenu menuAction;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuBar menubarMain;
    private javax.swing.JMenuItem menuitemLoad;
    private javax.swing.JMenuItem menuitemNew;
    private javax.swing.JMenuItem menuitemParse;
    private javax.swing.JMenuItem menuitemQuit;
    private javax.swing.JMenuItem menuitemSave;
    private javax.swing.JPanel panelArchive;
    private javax.swing.JPanel panelConfig;
    private javax.swing.JPanel panelConsole;
    private javax.swing.JProgressBar progressbarStatus;
    private javax.swing.JScrollPane scrollpanelArchive;
    private javax.swing.JScrollPane scrollpanelConsole;
    private javax.swing.JScrollPane scrollpanelModule;
    private javax.swing.JSpinner spinnerDebug;
    private javax.swing.JTabbedPane tabpaneMain;
    private javax.swing.JTextField textfieldArchivePath;
    private javax.swing.JTextField textfieldModuleCategory;
    private javax.swing.JTextField textfieldModuleID;
    private javax.swing.JTextField textfieldModuleName;
    private javax.swing.JTextField textfieldModuleOutputPath;
    private javax.swing.JTextField textfieldModulePath;
    private javax.swing.JTextField textfieldTempPath;
    private javax.swing.JTextField textfieldThumbnailPath;
    private javax.swing.JTextPane textpaneConsole;
    private javax.swing.JToolBar toolbarAction;
    private javax.swing.JToolBar toolbarMain;
    private javax.swing.JToolBar toolbarQuit;
    // End of variables declaration//GEN-END:variables

}

/*
 * PowerReaderUI.java
 *
 * Created on November 24, 2007, 7:09 PM
 */

package powerreader;

// Import J3D Stuff
import com.sun.j3d.utils.universe.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.*;
import javax.swing.JColorChooser;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;
import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.WindowConstants;
import javax.vecmath.Point3d;
import speech.Speech;
import util.HierarchyObject;
import util.RawTextParser;
import util.TextObject3d;
import util.WordHashMap;
/**
 *
 * @author  cleung
 */
public class PowerReaderUI extends javax.swing.JFrame {
    
    // Manually added variables
    private Canvas3D m_canvas;
    private Background m_background;
    
    private BranchGroup m_sceneRoot;
    private TransformGroup rootTransformGroup;
    private HierarchyObject m_hierarchyRoot = null;
    private RawTextParser rawTextParser;
    private Pick pick;
    
    // Config panel
    private ConfigUI m_configPanel;
    
    /** Creates new form PowerReaderUI */
    public PowerReaderUI() {
        initComponents();
        
        // Link the configuration manager and the slider on this UI
        ConfigurationManager.setZoomSlider(m_slider_zoomLevel);
        // Initialize config panel
        m_configPanel = new ConfigUI();
        m_configPanel.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        
        // Set default button colors
        m_button_bgColor.setBackground(ConfigurationManager.DEFAULT_BG_COLOR);
        m_button_fgColor.setBackground(ConfigurationManager.DEFAULT_FG_COLOR);
        m_button_hlColor.setBackground(ConfigurationManager.DEFAULT_HL_COLOR);
        
        TextObject3d.setBaseColor(new Color3f(ConfigurationManager.DEFAULT_FG_COLOR));
        TextObject3d.setHighlightColor(new Color3f(ConfigurationManager.DEFAULT_HL_COLOR));
        
        // Now initialize the 3D Canvas
        create3dCanvas();
    }
    
    private void create3dCanvas() {
        
        // Set up the canvas and scene
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        m_canvas = new Canvas3D(config);
        SimpleUniverse simpleU = new SimpleUniverse(m_canvas);
        
        createSceneGraph();
        simpleU.getViewingPlatform().setNominalViewingTransform();
        simpleU.addBranchGraph(m_sceneRoot);
        simpleU.getViewer().getView().setBackClipDistance(100);
        m_panel_textArea.setLayout( new BorderLayout() );
        m_panel_textArea.setOpaque( false );
        m_panel_textArea.add("Center", m_canvas);
        
        // Create picker
        pick = new Pick(m_canvas,m_sceneRoot,rootTransformGroup);
    }
    
    private void createSceneGraph() {
        // Create the root of the branch graph
        m_sceneRoot = new BranchGroup();
        m_sceneRoot.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        m_sceneRoot.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        
        // Set the background color
        startPowerReader("default.txt");
        Speech.speak("Hello and welcome to Power Reader, by Christopher Leung, Rubaiz Virk, and Zhan Shi.  To begin, please click the Open File button to your right.");
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        m_panel_textArea = new javax.swing.JPanel();
        m_panel_controlArea = new javax.swing.JPanel();
        m_slider_readSpeed = new javax.swing.JSlider();
        m_label_readSpeed = new javax.swing.JLabel();
        m_button_play = new javax.swing.JButton();
        m_buton_stop = new javax.swing.JButton();
        m_slider_lod = new javax.swing.JSlider();
        m_label_lod = new javax.swing.JLabel();
        m_slider_zoomLevel = new javax.swing.JSlider();
        m_label_zoomLevel = new javax.swing.JLabel();
        m_button_bgColor = new javax.swing.JButton();
        m_label_hlColor = new javax.swing.JLabel();
        m_button_hlColor = new javax.swing.JButton();
        m_checkBox_showImages = new javax.swing.JCheckBox();
        m_checkBox_wordsGrow = new javax.swing.JCheckBox();
        m_checkBox_speechEnabled = new javax.swing.JCheckBox();
        m_button_fgColor = new javax.swing.JButton();
        m_label_bgColor = new javax.swing.JLabel();
        m_label_fgColor = new javax.swing.JLabel();
        m_button_open = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        m_checkBox_speechEnabled1 = new javax.swing.JCheckBox();
        m_label_lof = new javax.swing.JLabel();
        m_slider_lof = new javax.swing.JSlider();
        m_slider_imageSize = new javax.swing.JSlider();
        m_label_lod1 = new javax.swing.JLabel();
        m_label_readSpeed1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Power Reader Alpha");
        //Test
        org.jdesktop.layout.GroupLayout m_panel_textAreaLayout = new org.jdesktop.layout.GroupLayout(m_panel_textArea);
        m_panel_textArea.setLayout(m_panel_textAreaLayout);
        m_panel_textAreaLayout.setHorizontalGroup(
            m_panel_textAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 520, Short.MAX_VALUE)
        );
        m_panel_textAreaLayout.setVerticalGroup(
            m_panel_textAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 713, Short.MAX_VALUE)
        );

        m_panel_controlArea.setFocusCycleRoot(true);
        m_slider_readSpeed.setMajorTickSpacing(58);
        m_slider_readSpeed.setMaximum(3000);
        m_slider_readSpeed.setMinimum(100);
        m_slider_readSpeed.setPaintTicks(true);
        m_slider_readSpeed.setValue(1550);
        m_slider_readSpeed.setName("Read Speed");
        m_slider_readSpeed.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                m_slider_readSpeedStateChanged(evt);
            }
        });

        m_label_readSpeed.setFont(new java.awt.Font("Tahoma", 1, 11));
        m_label_readSpeed.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        m_label_readSpeed.setText(" Read Speed");

        m_button_play.setFont(new java.awt.Font("Tahoma", 1, 11));
        m_button_play.setIcon(new javax.swing.ImageIcon(getClass().getResource("/powerreader/resources/player_play.png")));
        m_button_play.setText("Play");
        m_button_play.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_button_playActionPerformed(evt);
            }
        });

        m_buton_stop.setFont(new java.awt.Font("Tahoma", 1, 11));
        m_buton_stop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/powerreader/resources/player_stop.png")));
        m_buton_stop.setText("Stop");
        m_buton_stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_buton_stopActionPerformed(evt);
            }
        });

        m_slider_lod.setMajorTickSpacing(1);
        m_slider_lod.setMaximum(3);
        m_slider_lod.setPaintTicks(true);
        m_slider_lod.setSnapToTicks(true);
        m_slider_lod.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                m_slider_lodStateChanged(evt);
            }
        });

        m_label_lod.setFont(new java.awt.Font("Tahoma", 1, 11));
        m_label_lod.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        m_label_lod.setText("Level of Detail");

        m_slider_zoomLevel.setMajorTickSpacing(2);
        m_slider_zoomLevel.setMaximum(1);
        m_slider_zoomLevel.setMinimum(-100);
        m_slider_zoomLevel.setPaintTicks(true);
        m_slider_zoomLevel.setValue(-25);
        m_slider_zoomLevel.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                m_slider_zoomLevelStateChanged(evt);
            }
        });

        m_label_zoomLevel.setFont(new java.awt.Font("Tahoma", 1, 11));
        m_label_zoomLevel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        m_label_zoomLevel.setText("Zoom Level");

        m_button_bgColor.setBackground(new java.awt.Color(255, 153, 0));
        m_button_bgColor.setText("Background Color");
        m_button_bgColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_button_bgColorActionPerformed(evt);
            }
        });

        m_label_hlColor.setFont(new java.awt.Font("Tahoma", 1, 11));
        m_label_hlColor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_label_hlColor.setText("Highlight Color");

        m_button_hlColor.setBackground(new java.awt.Color(255, 0, 0));
        m_button_hlColor.setText("Highlight Color");
        m_button_hlColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_button_hlColorActionPerformed(evt);
            }
        });

        m_checkBox_showImages.setFont(new java.awt.Font("Tahoma", 1, 11));
        m_checkBox_showImages.setText("Show images");
        m_checkBox_showImages.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        m_checkBox_showImages.setMargin(new java.awt.Insets(0, 0, 0, 0));
        m_checkBox_showImages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_checkBox_showImagesActionPerformed(evt);
            }
        });

        m_checkBox_wordsGrow.setFont(new java.awt.Font("Tahoma", 1, 11));
        m_checkBox_wordsGrow.setText("Words grow as they are read");
        m_checkBox_wordsGrow.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        m_checkBox_wordsGrow.setMargin(new java.awt.Insets(0, 0, 0, 0));
        m_checkBox_wordsGrow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_checkBox_wordsGrowActionPerformed(evt);
            }
        });

        m_checkBox_speechEnabled.setFont(new java.awt.Font("Tahoma", 1, 11));
        m_checkBox_speechEnabled.setSelected(true);
        m_checkBox_speechEnabled.setText("Audible speech enabled");
        m_checkBox_speechEnabled.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        m_checkBox_speechEnabled.setMargin(new java.awt.Insets(0, 0, 0, 0));
        m_checkBox_speechEnabled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_checkBox_speechEnabledActionPerformed(evt);
            }
        });

        m_button_fgColor.setBackground(new java.awt.Color(0, 0, 204));
        m_button_fgColor.setText("Foreground Color");
        m_button_fgColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_button_fgColorActionPerformed(evt);
            }
        });

        m_label_bgColor.setFont(new java.awt.Font("Tahoma", 1, 11));
        m_label_bgColor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_label_bgColor.setText("Background Color");

        m_label_fgColor.setFont(new java.awt.Font("Tahoma", 1, 11));
        m_label_fgColor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_label_fgColor.setText("Foreground Color");

        m_button_open.setFont(new java.awt.Font("Tahoma", 1, 11));
        m_button_open.setIcon(new javax.swing.ImageIcon(getClass().getResource("/powerreader/resources/open.png")));
        m_button_open.setText("Open File");
        m_button_open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_button_openActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/powerreader/resources/configure.png")));
        jButton1.setText("Options");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        m_checkBox_speechEnabled1.setFont(new java.awt.Font("Tahoma", 1, 11));
        m_checkBox_speechEnabled1.setText("Follow words");
        m_checkBox_speechEnabled1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        m_checkBox_speechEnabled1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        m_checkBox_speechEnabled1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_checkBox_speechEnabled1ActionPerformed(evt);
            }
        });

        m_label_lof.setFont(new java.awt.Font("Tahoma", 1, 11));
        m_label_lof.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        m_label_lof.setText("Level of Focus");

        m_slider_lof.setMajorTickSpacing(1);
        m_slider_lof.setMaximum(3);
        m_slider_lof.setPaintTicks(true);
        m_slider_lof.setSnapToTicks(true);
        m_slider_lof.setValue(0);
        m_slider_lof.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                m_slider_lofStateChanged(evt);
            }
        });

        m_slider_imageSize.setMajorTickSpacing(1);
        m_slider_imageSize.setMaximum(5);
        m_slider_imageSize.setMinimum(1);
        m_slider_imageSize.setPaintTicks(true);
        m_slider_imageSize.setSnapToTicks(true);
        m_slider_imageSize.setValue(3);
        m_slider_imageSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                m_slider_imageSizeStateChanged(evt);
            }
        });

        m_label_lod1.setFont(new java.awt.Font("Tahoma", 1, 11));
        m_label_lod1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        m_label_lod1.setText(" Image Size (width)");

        m_label_readSpeed1.setFont(new java.awt.Font("Tahoma", 1, 11));
        m_label_readSpeed1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_label_readSpeed1.setText("(-)     <---    --->      (+)");

        org.jdesktop.layout.GroupLayout m_panel_controlAreaLayout = new org.jdesktop.layout.GroupLayout(m_panel_controlArea);
        m_panel_controlArea.setLayout(m_panel_controlAreaLayout);
        m_panel_controlAreaLayout.setHorizontalGroup(
            m_panel_controlAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(m_panel_controlAreaLayout.createSequentialGroup()
                .addContainerGap()
                .add(m_panel_controlAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jButton1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, m_button_open, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .add(m_panel_controlAreaLayout.createSequentialGroup()
                        .add(m_button_play, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(m_buton_stop, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 129, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(m_panel_controlAreaLayout.createSequentialGroup()
                        .add(m_label_lof, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(m_slider_lof, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, m_panel_controlAreaLayout.createSequentialGroup()
                        .add(m_panel_controlAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(m_label_lod1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, m_label_lod, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(m_panel_controlAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, m_slider_imageSize, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, m_slider_lod, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, m_panel_controlAreaLayout.createSequentialGroup()
                        .add(m_panel_controlAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(m_label_zoomLevel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                            .add(m_label_readSpeed, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(m_panel_controlAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, m_slider_readSpeed, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, m_slider_zoomLevel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                            .add(m_label_readSpeed1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)))
                    .add(m_button_bgColor, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .add(m_label_hlColor, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .add(m_button_hlColor, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, m_button_fgColor, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .add(m_label_bgColor, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .add(m_label_fgColor, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .add(m_checkBox_speechEnabled)
                    .add(m_checkBox_wordsGrow)
                    .add(m_checkBox_showImages)
                    .add(m_checkBox_speechEnabled1))
                .addContainerGap())
        );
        m_panel_controlAreaLayout.setVerticalGroup(
            m_panel_controlAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(m_panel_controlAreaLayout.createSequentialGroup()
                .addContainerGap()
                .add(jButton1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_button_open)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_panel_controlAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(m_buton_stop)
                    .add(m_button_play))
                .add(10, 10, 10)
                .add(m_label_readSpeed1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_panel_controlAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(m_panel_controlAreaLayout.createSequentialGroup()
                        .add(m_slider_readSpeed, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                    .add(m_label_readSpeed, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .add(m_panel_controlAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(m_panel_controlAreaLayout.createSequentialGroup()
                        .add(21, 21, 21)
                        .add(m_label_zoomLevel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                    .add(m_panel_controlAreaLayout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(m_slider_zoomLevel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(m_panel_controlAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(m_panel_controlAreaLayout.createSequentialGroup()
                        .add(23, 23, 23)
                        .add(m_label_lof, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, m_panel_controlAreaLayout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(m_slider_lof, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(16, 16, 16)
                .add(m_panel_controlAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(m_panel_controlAreaLayout.createSequentialGroup()
                        .add(m_label_lod, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                        .add(18, 18, 18))
                    .add(m_panel_controlAreaLayout.createSequentialGroup()
                        .add(m_slider_lod, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_panel_controlAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(m_label_lod1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(m_slider_imageSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, Short.MAX_VALUE))
                .add(20, 20, 20)
                .add(m_label_fgColor)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_button_fgColor)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_label_bgColor)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_button_bgColor)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_label_hlColor)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_button_hlColor)
                .add(25, 25, 25)
                .add(m_checkBox_speechEnabled1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_checkBox_showImages)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_checkBox_wordsGrow)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_checkBox_speechEnabled)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(m_panel_textArea, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_panel_controlArea, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, m_panel_textArea, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(m_panel_controlArea, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void m_slider_imageSizeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_m_slider_imageSizeStateChanged
        ConfigurationManager.setImageScale(m_slider_imageSize.getValue());
    }//GEN-LAST:event_m_slider_imageSizeStateChanged
    
    private void m_slider_lodStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_m_slider_lodStateChanged
        
        // If LOD is 'lower' than level of focus (i.e. detail level is word when focus level is sentence)
        if(m_slider_lod.getValue() < m_slider_lof.getValue()) {
            // Force the level of detail equal to the level of focus
            m_slider_lod.setValue(m_slider_lof.getValue());
        }
        ConfigurationManager.setDetailLevel(3 - m_slider_lod.getValue());
        
        // Refresh the scene
        Player.disableRenderExcept(Player.getFocusOn());
    }//GEN-LAST:event_m_slider_lodStateChanged
    
    private void m_slider_lofStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_m_slider_lofStateChanged
        // Make sure sliders don't pass each other
        if(m_slider_lod.getValue() < m_slider_lof.getValue()) {
            m_slider_lof.setValue(m_slider_lod.getValue());
        }
        int currentFocusLevel = (3 - m_slider_lof.getValue());
        ConfigurationManager.setFocusLevel(currentFocusLevel);
        HierarchyObject currentObj = Player.getFocusOn();
        
        Player.restart(m_hierarchyRoot,ConfigurationManager.getFocusLevel());
        
        // Set the focus at the correct level now
        if(currentFocusLevel < currentObj.getLevel()) {
            Player.setFocusOn(currentObj.getParent(currentFocusLevel));
        } else if (currentFocusLevel > currentObj.getLevel()) {
            Player.setFocusOn((HierarchyObject)currentObj.getChildren().get(0));
        } else {
            Player.setFocusOn(currentObj);
        }
        
    }//GEN-LAST:event_m_slider_lofStateChanged
    
    private void m_checkBox_showImagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_checkBox_showImagesActionPerformed
        ConfigurationManager.toggleShowImages();
    }//GEN-LAST:event_m_checkBox_showImagesActionPerformed
    
    private void m_checkBox_speechEnabled1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_checkBox_speechEnabled1ActionPerformed
        ConfigurationManager.toggleFollowFocus();
    }//GEN-LAST:event_m_checkBox_speechEnabled1ActionPerformed
    
    private void m_slider_zoomLevelStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_m_slider_zoomLevelStateChanged
        
        ConfigurationManager.current_z = m_slider_zoomLevel.getValue();
        ConfigurationManager.refreshTranslate();
    }//GEN-LAST:event_m_slider_zoomLevelStateChanged
    
    private void m_checkBox_wordsGrowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_checkBox_wordsGrowActionPerformed
        ConfigurationManager.toggleWordsGrow();
    }//GEN-LAST:event_m_checkBox_wordsGrowActionPerformed
    
    private void m_checkBox_speechEnabledActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_checkBox_speechEnabledActionPerformed
        ConfigurationManager.toggleAudibleSpeech();
    }//GEN-LAST:event_m_checkBox_speechEnabledActionPerformed
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        m_configPanel.setVisible(true);
        
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private void m_slider_readSpeedStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_m_slider_readSpeedStateChanged
        Player.setSleepDelay(3100-m_slider_readSpeed.getValue());
    }//GEN-LAST:event_m_slider_readSpeedStateChanged
    
    private void m_buton_stopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_buton_stopActionPerformed
        Player.pause();
    }//GEN-LAST:event_m_buton_stopActionPerformed
    
    private void m_button_openActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_button_openActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(this);
        if (fc.getSelectedFile() != null) {
            startPowerReader(fc.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_m_button_openActionPerformed
    
    private void startPowerReader(String file) {
        rawTextParser = new RawTextParser(file);
        
        // Create a new background from scratc
        BoundingSphere boundingSphere =
                new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        m_background = new Background();
        m_background.setApplicationBounds(boundingSphere);
        m_background.setColor(new Color3f(m_button_bgColor.getBackground()));
        m_background.setCapability(Background.ALLOW_COLOR_WRITE);
        
        //reset the scene and memmory
        if(m_hierarchyRoot != null) {
            // Remove the scene from the root node
            m_sceneRoot.removeChild(m_hierarchyRoot.getBranchGroup());
        }
        TextObject3d.resetLocation();
        WordHashMap.getInstance().clearMap();
        
        // Parse
        rawTextParser.parse();
        
        // Returns the document level hierarchy object
        m_hierarchyRoot = rawTextParser.getHierarchyRoot();
        
        // Set the transform group that we use for translating the entire scene
        Transform3D translation = new Transform3D();
        translation.setTranslation(new Vector3f(ConfigurationManager.DEFAULT_X,ConfigurationManager.DEFAULT_Y,ConfigurationManager.DEFAULT_Z));
        ConfigurationManager.setMainTransformGroup(m_hierarchyRoot.getTransformGroup());
        ConfigurationManager.getMainTransformGroup().setTransform(translation);
        ConfigurationManager.getMainTransformGroup().addChild(m_background);
        
        // Reset the slider zoom as well
        
        ConfigurationManager.current_x = ConfigurationManager.DEFAULT_X;
        ConfigurationManager.current_y = ConfigurationManager.DEFAULT_Y;
        ConfigurationManager.current_z = ConfigurationManager.DEFAULT_Z;
        
        m_slider_zoomLevel.setValue((int)ConfigurationManager.current_z);
        
        m_sceneRoot.addChild(m_hierarchyRoot.getBranchGroup());
        
        Player.restart(m_hierarchyRoot,ConfigurationManager.getFocusLevel());
    }
    
    private void m_button_hlColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_button_hlColorActionPerformed
        Color c = m_button_hlColor.getBackground();
        // Bring up color selector
        c = JColorChooser.showDialog(((Component)evt.getSource()).getParent(), "Highlight Color", c);
        
        if(c != null) {
            // Assign result to the button
            m_button_hlColor.setBackground(c);
            
            // Assign to text object
            TextObject3d.setHighlightColor(new Color3f(c));
            
            // Recolor only the focused item
            Player.getFocusOn().color(true);
        }
    }//GEN-LAST:event_m_button_hlColorActionPerformed
    
    private void m_button_bgColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_button_bgColorActionPerformed
        Color c = m_button_bgColor.getBackground();
        // Bring up color selector
        c = JColorChooser.showDialog(((Component)evt.getSource()).getParent(), "Choose Background Color", c);
        
        if(c != null) {
            // Assign result to the button
            m_button_bgColor.setBackground(c);
            
            // Assign result to background
            m_background.setColor(new Color3f(c));
        }
    }//GEN-LAST:event_m_button_bgColorActionPerformed
    
    private void m_button_fgColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_button_fgColorActionPerformed
        
        Color c = m_button_fgColor.getBackground();
        // Bring up color selector
        c = JColorChooser.showDialog(((Component)evt.getSource()).getParent(), "Choose Foreground Color", c);
        
        if (c != null) {
            
            // Assign result to the button
            m_button_fgColor.setBackground(c);
            
            // Assign result to foreground
            TextObject3d.setBaseColor(new Color3f(c));
            
            // Color all the foreground
            m_hierarchyRoot.color(false);
            
            // Rehighlight the focused
            Player.getFocusOn().color(true);
        }
    }//GEN-LAST:event_m_button_fgColorActionPerformed
    
    private void m_button_playActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_button_playActionPerformed
        Player.play();
    }//GEN-LAST:event_m_button_playActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton m_buton_stop;
    private javax.swing.JButton m_button_bgColor;
    private javax.swing.JButton m_button_fgColor;
    private javax.swing.JButton m_button_hlColor;
    private javax.swing.JButton m_button_open;
    private javax.swing.JButton m_button_play;
    private javax.swing.JCheckBox m_checkBox_showImages;
    private javax.swing.JCheckBox m_checkBox_speechEnabled;
    private javax.swing.JCheckBox m_checkBox_speechEnabled1;
    private javax.swing.JCheckBox m_checkBox_wordsGrow;
    private javax.swing.JLabel m_label_bgColor;
    private javax.swing.JLabel m_label_fgColor;
    private javax.swing.JLabel m_label_hlColor;
    private javax.swing.JLabel m_label_lod;
    private javax.swing.JLabel m_label_lod1;
    private javax.swing.JLabel m_label_lof;
    private javax.swing.JLabel m_label_readSpeed;
    private javax.swing.JLabel m_label_readSpeed1;
    private javax.swing.JLabel m_label_zoomLevel;
    private javax.swing.JPanel m_panel_controlArea;
    private javax.swing.JPanel m_panel_textArea;
    private javax.swing.JSlider m_slider_imageSize;
    private javax.swing.JSlider m_slider_lod;
    private javax.swing.JSlider m_slider_lof;
    private javax.swing.JSlider m_slider_readSpeed;
    private javax.swing.JSlider m_slider_zoomLevel;
    // End of variables declaration//GEN-END:variables
    
    
}

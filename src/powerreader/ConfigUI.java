/*
 * ConfigUI.java
 *
 * Created on December 4, 2007, 2:11 PM
 */

package powerreader;

/**
 *
 * @author  Christopher Leung
 */
public class ConfigUI extends javax.swing.JFrame {
    
    /** Creates new form ConfigUI */
    public ConfigUI() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        m_combo_dictionaryLookup = new javax.swing.JComboBox();
        m_combo_imageLookup = new javax.swing.JComboBox();
        m_combo_fontFace = new javax.swing.JComboBox();
        m_combo_voicePersonality = new javax.swing.JComboBox();
        m_combo_leftClick = new javax.swing.JComboBox();
        m_combo_middleClick = new javax.swing.JComboBox();
        m_combo_rightClick = new javax.swing.JComboBox();
        m_okButton = new javax.swing.JButton();
        m_cancelButton = new javax.swing.JButton();
        m_checkBox_mouseWheel = new javax.swing.JCheckBox();
        m_label_dictionarySource = new javax.swing.JLabel();
        m_label_imageSource = new javax.swing.JLabel();
        m_label_fontFace = new javax.swing.JLabel();
        m_label_voicePersonality = new javax.swing.JLabel();
        m_label_leftClick = new javax.swing.JLabel();
        m_label_middleClick = new javax.swing.JLabel();
        m_label_rightClick = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Power Reader Options");
        m_combo_dictionaryLookup.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Wiktionary", "WordNet Dictionary" }));

        m_combo_imageLookup.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Flickr", "Google Images", "Yahoo Images" }));
        m_combo_imageLookup.setSelectedIndex(1);

        m_combo_fontFace.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tahoma" }));

        m_combo_voicePersonality.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kevin" }));
        m_combo_voicePersonality.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_combo_voicePersonalityActionPerformed(evt);
            }
        });

        m_combo_leftClick.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Focus On Item" }));

        m_combo_middleClick.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Read Dictionary Definition" }));

        m_combo_rightClick.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Drag and Scroll" }));

        m_okButton.setText("OK");
        m_okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_okButtonActionPerformed(evt);
            }
        });

        m_cancelButton.setText("Cancel");
        m_cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_cancelButtonActionPerformed(evt);
            }
        });

        m_checkBox_mouseWheel.setSelected(true);
        m_checkBox_mouseWheel.setText("Enable Mouse Wheel Zoom");
        m_checkBox_mouseWheel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        m_checkBox_mouseWheel.setMargin(new java.awt.Insets(0, 0, 0, 0));

        m_label_dictionarySource.setFont(new java.awt.Font("Tahoma", 1, 11));
        m_label_dictionarySource.setText("Dictionary Source");

        m_label_imageSource.setFont(new java.awt.Font("Tahoma", 1, 11));
        m_label_imageSource.setText("Image Source");

        m_label_fontFace.setFont(new java.awt.Font("Tahoma", 1, 11));
        m_label_fontFace.setText("Font Face");

        m_label_voicePersonality.setFont(new java.awt.Font("Tahoma", 1, 11));
        m_label_voicePersonality.setText("Voice Personality");

        m_label_leftClick.setFont(new java.awt.Font("Tahoma", 1, 11));
        m_label_leftClick.setText("Left Click");

        m_label_middleClick.setFont(new java.awt.Font("Tahoma", 1, 11));
        m_label_middleClick.setText("Middle Click");

        m_label_rightClick.setFont(new java.awt.Font("Tahoma", 1, 11));
        m_label_rightClick.setText("Right Click");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(m_label_fontFace)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                        .addComponent(m_combo_fontFace, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(m_label_imageSource)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addComponent(m_combo_imageLookup, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(m_label_leftClick)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                        .addComponent(m_combo_leftClick, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(m_label_middleClick)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addComponent(m_combo_middleClick, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(m_label_rightClick)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(m_checkBox_mouseWheel)
                            .addComponent(m_combo_rightClick, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(m_label_voicePersonality)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(m_combo_voicePersonality, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(m_okButton, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(m_label_dictionarySource)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addComponent(m_combo_dictionaryLookup, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_label_dictionarySource)
                    .addComponent(m_combo_dictionaryLookup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_combo_imageLookup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_label_imageSource))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_combo_fontFace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_label_fontFace, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_combo_voicePersonality, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_label_voicePersonality))
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_label_leftClick)
                    .addComponent(m_combo_leftClick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_label_middleClick)
                    .addComponent(m_combo_middleClick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_combo_rightClick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_label_rightClick))
                .addGap(14, 14, 14)
                .addComponent(m_checkBox_mouseWheel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_cancelButton)
                    .addComponent(m_okButton))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void m_okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_okButtonActionPerformed
        ConfigurationManager.setDictionary(m_combo_dictionaryLookup.getSelectedIndex());
        this.setVisible(false);
    }//GEN-LAST:event_m_okButtonActionPerformed

    private void m_cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_cancelButtonActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_m_cancelButtonActionPerformed

    private void m_combo_voicePersonalityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_combo_voicePersonalityActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_m_combo_voicePersonalityActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConfigUI().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton m_cancelButton;
    private javax.swing.JCheckBox m_checkBox_mouseWheel;
    private javax.swing.JComboBox m_combo_dictionaryLookup;
    private javax.swing.JComboBox m_combo_fontFace;
    private javax.swing.JComboBox m_combo_imageLookup;
    private javax.swing.JComboBox m_combo_leftClick;
    private javax.swing.JComboBox m_combo_middleClick;
    private javax.swing.JComboBox m_combo_rightClick;
    private javax.swing.JComboBox m_combo_voicePersonality;
    private javax.swing.JLabel m_label_dictionarySource;
    private javax.swing.JLabel m_label_fontFace;
    private javax.swing.JLabel m_label_imageSource;
    private javax.swing.JLabel m_label_leftClick;
    private javax.swing.JLabel m_label_middleClick;
    private javax.swing.JLabel m_label_rightClick;
    private javax.swing.JLabel m_label_voicePersonality;
    private javax.swing.JButton m_okButton;
    // End of variables declaration//GEN-END:variables
    
}

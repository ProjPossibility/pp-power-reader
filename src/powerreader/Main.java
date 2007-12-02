/*
 * Main.java
 *
 * Created on November 24, 2007, 7:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package powerreader;
import speech.*;
import misc.*;

/**
 *
 * @author cleung
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PowerReaderUI().setVisible(true);
            }
        });
        
        Speech.speak("Hello and welcome to Power Reader.  This is a test of the free text to speech synthesizer.");
    }
    
}

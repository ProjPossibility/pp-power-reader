package speech;

import java.util.*;

import com.sun.speech.freetts.*;
import com.sun.speech.freetts.en.*;

import misc.*;

public class Speech extends Thread {
    static private Speech m_instance = null;
    
    private Voice m_voice = null;
    private String m_msg = null;
    private Object m_ready = null;
    
    static {
        m_instance = new Speech();
    }
    
    private Speech() {
        Voice[] voices = VoiceManager.getInstance().getVoices();
        m_voice = VoiceManager.getInstance().getVoice("kevin16");
        if (m_voice == null) {
            ErrorDialog.show("Could not load FreeTTS voice");
            System.exit(1);
        }
        m_voice.allocate();
        m_ready = new Object();
        start();
    }
    
    public void run() {
        String curMsg = null;
        for (;;) {
            synchronized(m_ready) {
                while(m_msg == null) {
                    try {
                        m_ready.wait();
                    } catch (InterruptedException e) {
                        ErrorDialog.show(e);
                        System.exit(1);
                    }
                }
                curMsg = m_msg;
                m_msg = null;
            }
            m_voice.speak(curMsg);
        }
    }
    
    public void setMessage(String msg) {
        synchronized(m_ready) {
            m_voice.getAudioPlayer().cancel();
            m_msg = msg;
            m_ready.notify();
        }
    }
    
    static public void setSpeed(float speed) {
        m_instance.m_voice.setRate(speed);
    }
    
    static public void speak(String msg) {
        m_instance.setMessage(msg);
    }
    
    static public void cancel() {
        m_instance.m_voice.getAudioPlayer().cancel();
    }
}

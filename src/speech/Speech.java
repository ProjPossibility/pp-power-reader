/*
     This file is part of Power Reader.

    Power Reader is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Power Reader is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Power Reader.  If not, see <http://www.gnu.org/licenses/>. 
    
    This software was developed by members of Project:Possibility, a software 
    collaboration for the disabled.
    
    For more information, visit http://www.projectpossibility.org 
 */

package speech;


import com.sun.speech.freetts.*;

import misc.*;
import util.HierarchyObject;

public class Speech extends Thread {
    static private Speech m_instance = null;
    
    private Voice m_voice = null;
    private String m_msg = null;
    private Object m_ready = null;
    private Object m_sync = null;
    private boolean m_mute = false;
    
    private HierarchyObject m_curObj = null;
    
    
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
        m_sync = new Object();
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
            if(!m_mute) {
                synchronized(m_sync) {
                    m_voice.speak(curMsg);
                    m_sync.notify();
                }
            }
        }
    }
    
    public void setMessage(String msg) {
        synchronized(m_ready) {
            m_voice.getAudioPlayer().cancel();
            m_msg = msg;
            m_ready.notify();
        }
    }
    
    public void setObj(HierarchyObject obj) {
        synchronized(m_ready) {
            m_voice.getAudioPlayer().cancel();
            m_msg = obj.getValue();
            m_ready.notify();
        }
    }
    static public void setSpeed(float speed) {
        m_instance.m_voice.setRate(speed);
    }
    
    static public void speak(String msg) {
        m_instance.setMessage(msg);
    }

    static public void speakObj(HierarchyObject obj) {
        m_instance.setObj(obj);
    }
    
    static public void cancel() {
        m_instance.m_voice.getAudioPlayer().cancel();
    }
    static public void mute() {
        m_instance.cancel();
        m_instance.m_mute = true;
    }
    static public void unmute() {
        m_instance.m_mute = false;
    }
    static public Object getSync() {
        return m_instance.m_sync;
    }
}

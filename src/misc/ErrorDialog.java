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
package misc;

import java.awt.*;
import javax.swing.*;

public class ErrorDialog extends JDialog {
	
	static public void show(String s) {
		new ErrorDialog(null, s);
	}
	
	static public void show(Exception e) {
		new ErrorDialog(e);
	}
	
	static public void show(Exception e, String s) {
		new ErrorDialog(e, s);
	}
	
	public ErrorDialog(Exception e) {
		//super(NTFrame.getFrame());
		init(e, null);
	}
	
	public ErrorDialog(Exception e, String msg) {
		//super(NTFrame.getFrame());
		init(e, msg);
	}
	
	private void init(Exception e, String msg) {
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setModal(true);
		setResizable(true);
		
		if (msg != null) {
			add(new JLabel(msg));
			Log.getInstance().logMessage(getClass(), msg);
		}
		
		if (e != null) {
			add(new JLabel(e.getMessage()));
		
			Log.getInstance().logError(getClass(), msg, e);
		
			StackTraceElement[] trace = e.getStackTrace();
			for (int i = 0; i < ((trace.length < 10) ? trace.length : 10); ++i) {
				String line = trace[i].getFileName() + ":" 
						+ trace[i].getClassName() + ":" 
						+ trace[i].getMethodName() + ":" 
						+ trace[i].getLineNumber();
				add(new JLabel(line));
			}
		}
		
		pack();
		setBounds(getX(), getY(), getWidth() + 15, getHeight() + 15);
		setLocationByPlatform(true);
		setVisible(true);
	}
        
}

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

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

import java.text.DateFormat;
import java.util.Date;
import java.io.OutputStream;
import java.io.PrintStream;

public class Log {
	
	static private Log m_instance = new Log();
	
	private OutputStream m_outputStream = null;
	private PrintStream m_printStream = null;
	
	private Log() {
	}
	
	static public Log getInstance() {
		return m_instance;
	}
	
	public void logMessage(Class objClass, String message) {
		println(getTimeString() + objClass.getName() + ": " + message);
	}
	
	public void logError(Class objClass, String message) {
		logError(objClass, message, null);
	}
	
	public void logError(Class objClass, String message, Exception e) {
		println(getTimeString() + objClass.getName() + ": " + "ERROR: " + message);
		if (e != null) {
			print(e);
		}
	}
	
	public void setOutputStream(OutputStream outputStream) {
		m_outputStream = outputStream;
		m_printStream = new PrintStream(m_outputStream);
	}
	
	public void print(String txt) {
		if (m_printStream == null) {
			return;
		}
		m_printStream.print(txt);
	}
	
	public void print(char c) {
		if (m_printStream == null) {
			return;
		}
		m_printStream.print(c);
	}
	
	public void print(byte b) {
		if (m_printStream == null) {
			return;
		}
		m_printStream.print(b);
	}
	
	public void print(int i) {
		if (m_printStream == null) {
			return;
		}
		m_printStream.print(i);
	}
	
	public void println(String txt) {
		if (m_printStream == null) {
			return;
		}
		m_printStream.println(txt);
	}
	
	public void println(char c) {
		if (m_printStream == null) {
			return;
		}
		m_printStream.println(c);
	}
	
	public void println(byte b) {
		if (m_printStream == null) {
			return;
		}
		m_printStream.println(b);
	}
	
	void println(int i) {
		if (m_printStream == null) {
			return;
		}
		m_printStream.println(i);
	}
	
	public void print(Exception e) {
		if (m_printStream == null) {
			return;
		}
		e.printStackTrace(m_printStream);
	}
	
	static private String getTimeString() {
		return (DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(new Date()) + ": ");
	}
	
}

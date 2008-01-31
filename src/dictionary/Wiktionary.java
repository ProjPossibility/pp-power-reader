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

package dictionary;
import java.io.*;
import java.net.*;
import java.util.regex.*;

/**
 *
 * @author zhanshi
 */
public class Wiktionary extends DictionaryLookup {
    
    /** Creates a new instance of Wiktionary */
    public Wiktionary() {
        dictionaryName = "Wiktionary";
    }
    
    public String getDefinition(String text) {
        String def = "";
        try {
            // Create an URL instance
            URL url = new URL("http://en.wiktionary.org/wiki/" + sanitize(text));
            URLConnection connection = url.openConnection();
            DataInputStream in = new DataInputStream(connection.getInputStream());
            String line;
            Pattern defPattern = Pattern.compile("<li>(.+)</li>");
            Boolean inDef = false;
            Boolean inDefItem = false;
            while ((line = in.readLine()) != null) {
                if (line.indexOf("<ol>") >= 0) {
                    inDef = true;
                }
                if (inDef) {
                    if (line.indexOf("<li>") >= 0) {
                        inDefItem = true;
                    }
                    if (inDefItem) {
                        def += line.replaceAll("\\<.*?>","");
                    }
                    if (line.indexOf("</li>") >= 0) {
                        if (inDefItem) {
                            def += ".\n";
                        }
                        inDefItem = false;
                    }
                }
                if (line.indexOf("</ol>") >= 0) {
                    inDef = false;
                }
            }
            in.close();
        } catch (Exception e) {}
        return def;
    }
}

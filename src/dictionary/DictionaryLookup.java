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

import java.util.regex.*;

/**
 *
 * @author zhanshi
 */
public abstract class DictionaryLookup {
    
    protected static String dictionaryName;
            
    /** Creates a new instance of DictionaryLookup */
    public DictionaryLookup() {
    }
    public abstract String getDefinition(String text);
    
    public String sanitize(String text) {
        return text.replaceAll("^[^\\w]*", "").replaceAll("[^\\w]*$", "");
    }
    
    static public String getName() {
        return dictionaryName;
    }

}

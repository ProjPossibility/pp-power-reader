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

/*
 * HashMap.java
 *
 * Created on December 4, 2007, 5:03 AM
 *
 *A singelton class that contains a hash table mapping TextObject3d to HierarchyObject
 */

package util;

import java.util.Hashtable;

/**
 *
 * @author Rubaiz Singh Virk
 */
public class WordHashMap {
    
    private static WordHashMap instance = null;
    private static Hashtable wordMap = new Hashtable();
            
    protected WordHashMap(){
    }
    
    public static WordHashMap getInstance() {
      if(instance == null) {
         instance = new WordHashMap();
      }
      return instance;
    }
    
    //instert new key value pairs
    public void addHash(TextObject3d tObj, HierarchyObject hObj){
        wordMap.put(tObj,hObj);
    }
    
    // returns the mapping
    public HierarchyObject getHirarchyObject(TextObject3d tObj){
        HierarchyObject rVal=null;
        if (tObj!=null){
            if (wordMap.containsKey(tObj)){
                rVal = (HierarchyObject) wordMap.get(tObj);
            }
        }
        return rVal;
    }
    
    public void clearMap(){
        wordMap.clear();
    }
}
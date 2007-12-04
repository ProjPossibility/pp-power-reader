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
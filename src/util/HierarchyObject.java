/*
 * HierarchyObject.java
 *
 * Created on December 3, 2007, 4:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package util;

import java.util.ArrayList;
import javax.media.j3d.Node;

/**
 *
 * @author Christopher Leung
 */
public class HierarchyObject {
    
    /**
     * Defines document level identifier as integer.
     */
    public static int LEVEL_DOCUMENT = 0;
    /**
     * Defines paragraph level identifier as integer.
     */
    public static int LEVEL_PARAGRAPH  = 1;
    /**
     * Defines sentence level identifier as integer.
     */
    public static int LEVEL_SENTENCE  = 2;
    /**
     * Defines phrase level identifier as integer.
     */
    public static int LEVEL_PHRASE = 3;
    /**
     * Defines word level identifier as integer.
     */
    public static int LEVEL_WORD = 4;
    
    // Referece to scene graph node
    private Node m_node;
    
    // Value of this object (i.e. word, or phrase, or sentence)
    private String m_value;
    
    // "Level" of this object
    private int m_level;
    
    // Objects parents ordered list
    private ArrayList m_parents;
    
    // The type of object as text (i.e. "Word", "Sentence")
    private String m_type;
    
    // Ordered list of children
    private ArrayList m_children;
    
    /**
     * Creates a new instance of HierarchyObject
     * @param newLevel Level of the hierarchy object
     * @param newType Tpe of the hierarchy as string
     * @param newNode Reference to the scene graph node
     */
    public HierarchyObject(int newLevel, String newType, Node newNode) {
        m_level = newLevel;
        m_type = newType;
        m_node = newNode;
    }

    /**
     * Get type of Hierarchy object as string (i.e. "Word", "Sentence")
     */
    public String getType() {
        return m_type;
    }
    
    public HierarchyObject getParent() {
        // If we're at the highest level, return null'
        if(m_level == 0) {
            return null;
        }
        else {
            return (HierarchyObject) m_parents.get(m_level-1);
        }
    }

    public HierarchyObject getParent(int type) {
        // Will throw an exception of we've requested something out of bounds
        return (HierarchyObject) m_parents.get(type);
    }

    public void setParents(ArrayList parents) {
        // Copy the arraylist into this object
        m_parents = (ArrayList)parents.clone();
    }
    
    public void addChild(HierarchyObject child) {
        m_children.add(child);
    }
    
    public ArrayList getChildren () {
        return m_children;
    }
}

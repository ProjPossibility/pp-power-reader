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
import java.util.Iterator;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;

/**
 *
 * @author Christopher Leung
 */
public class HierarchyObject {
    
    // Referece to scene graph node
    private BranchGroup m_branchGroup;
    private TransformGroup m_transformGroup;
    
    // Value of this object (i.e. word, or sentence, or paragraph)
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
    public HierarchyObject(int newLevel, String newType) {
        m_level = newLevel;
        m_type = newType;
        m_children = new ArrayList();
        m_parents = new ArrayList();
        m_value = null;
        m_branchGroup = new BranchGroup();
        m_transformGroup = new TransformGroup();
        m_branchGroup.addChild(m_transformGroup);
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
        } else {
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
        // Add the child to the arraylist
        m_children.add(child);
        
        // Now also make this item a child in the scene graph
        m_transformGroup.addChild(child.getBranchGroup());
    }
    
    public void addSceneNode(TransformGroup transformGroup) {
        m_transformGroup = transformGroup;
        m_branchGroup.addChild(m_transformGroup);
    }
    
    public TransformGroup getTransformGroup() {
        return m_transformGroup;
    }
    
    public BranchGroup getBranchGroup() {
        return m_branchGroup;
    }
    
    public ArrayList getChildren() {
        return m_children;
    }
    
    public ArrayList getAllChildrenOfLevel(int level) {
        return getAllChildrenOfLevelRecursive(level,this);
    }
    
    private static ArrayList getAllChildrenOfLevelRecursive(int level, HierarchyObject toParse) {
        HierarchyObject treeNode;
        ArrayList children = new ArrayList();
        ArrayList result;
        if(toParse.getLevel() == level) {
            children.add(toParse);
        } else {
            // Get all the children
            Iterator childrenIt =  toParse.getChildren().iterator();
            
            while(childrenIt.hasNext()) {
                treeNode = (HierarchyObject) childrenIt.next();
                result = getAllChildrenOfLevelRecursive(level,treeNode);
                children = joinArrayList(children,result);
            }
        }
        return (children);
    }
    
    private static ArrayList joinArrayList(ArrayList list1, ArrayList list2) {
        Iterator it1 = list1.iterator();
        Iterator it2 = list2.iterator();
        ArrayList result = new ArrayList();
        while(it1.hasNext()) {
            result.add(it1.next());
        }
        while(it2.hasNext()) {
            result.add(it2.next());
        }
        return result;
    }
    
    public int getLevel() {
        return m_level;
    }
    
    public boolean hasChildren() {
        if (m_children.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public void setValue(String value) {
        m_value = value;
    }
    
    public String getValue() {
        return m_value;
    }
    
    public void color (boolean highLight) {
        ArrayList objectsToColor = this.getAllChildrenOfLevel(RawTextParser.LEVEL_WORD_ID);
        Iterator it = objectsToColor.iterator();
        HierarchyObject objectToColor;
        TextObject3d objectToHighlight;
        if(highLight) {
            while(it.hasNext()) {
                objectToColor = (HierarchyObject)it.next();
                objectToHighlight = (TextObject3d)(objectToColor.getTransformGroup());
                objectToHighlight.highLight();
            }
        } else {
            while(it.hasNext()) {
                objectToColor = (HierarchyObject)it.next();
                objectToHighlight = (TextObject3d)(objectToColor.getTransformGroup());
                objectToHighlight.unHighLight();
            }
        }
    }
}

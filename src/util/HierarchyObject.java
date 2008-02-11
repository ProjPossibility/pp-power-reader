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
    
    // Stores whether this object is being rendered or not
    private boolean m_render;
    
    /**
     * Creates a new instance of HierarchyObject
     * @param newLevel Level of the hierarchy object
     * @param newType Tpe of the hierarchy as string
     * @param newNode Reference to the scene graph node
     */
    public HierarchyObject(int newLevel, String newType) {
        m_render = true;
        m_level = newLevel;
        m_type = newType;
        m_children = new ArrayList();
        m_parents = new ArrayList();
        m_value = null;
        m_branchGroup = new BranchGroup();
        m_branchGroup.setCapability(BranchGroup.ALLOW_DETACH);
        m_branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        m_branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        m_branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        
        
        m_transformGroup = new TransformGroup();
        m_transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        m_transformGroup.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        m_transformGroup.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        
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
    
    public void color(boolean highLight) {
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
    
    public void disableRender() {
        if(m_render == true) {
            this.getParent().getTransformGroup().removeChild(m_branchGroup);
            m_render = false;
        }
    }
    
    public void enabledRender() {
        if(this.getParent() != null && m_render == false) {
            this.getParent().getTransformGroup().addChild(m_branchGroup);
            m_render = true;
        }
    }
}

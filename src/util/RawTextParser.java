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
 * RawTextParser.java
 *
 * Created on December 3, 2007, 4:55 PM
 *
 * NOTE - The scene graph that is created is structured as follows
 *  Level 0 - Document - BranchGroup, TransformGroup is null by default
 *  Level 1 - Paragraph - BranchGroup, TransformGroup is null by default
 *  Level 2 - Sentence - Branchgroup, TransformGroup is null by default
 *  Level 3 - Word - Branchgroup , with TransformGroup attached to it
 *            (the 3d Word itself)
 */

package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.media.j3d.BranchGroup;
import powerreader.ConfigurationManager;

/**
 *
 * @author Christopher Leung
 */
public class RawTextParser {
    public static String LEVEL_DOCUMENT_STR = "Document";
    public static String LEVEL_PARAGRAPH_STR = "Paragraph";
    public static String LEVEL_SENTENCE_STR = "Sentence";
    public static String LEVEL_WORD_STR = "Word";
    
    public static int LEVEL_DOCUMENT_ID = 0;
    public static int LEVEL_PARAGRAPH_ID  = 1;
    public static int LEVEL_SENTENCE_ID  = 2;
    public static int LEVEL_WORD_ID = 3;
    
    private BranchGroup m_rootNode = null;
    private HierarchyObject m_hierarchyRoot;
    private String m_fileName;
    
    /** Creates a new instance of RawTextParser */
    public RawTextParser(String filename) {
        m_fileName = filename;
        ConfigurationManager.setCurrentFileName(filename);
    }
    
    public BranchGroup getRootNode() {
        return m_rootNode;
    }
    
    public HierarchyObject getHierarchyRoot() {
        return m_hierarchyRoot;
    }
    public void parse() {
        WordHashMap map = WordHashMap.getInstance();
        
        BranchGroup node = new BranchGroup();
        
        node.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        node.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        node.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        node.setCapability(BranchGroup.ALLOW_DETACH);
        node.setCapability(BranchGroup.ENABLE_PICK_REPORTING);
        
        BufferedReader input = null;
        String line = null;
        StringTokenizer wordTok = null;
        String token = null;
        String sentenceText = new String();
        String paragraphText = new String();
        String documentText = new String();
        String senToken = null;
        
        HierarchyObject docuObj = new HierarchyObject(LEVEL_DOCUMENT_ID, LEVEL_DOCUMENT_STR);
        HierarchyObject paraObj = new HierarchyObject(LEVEL_PARAGRAPH_ID, LEVEL_PARAGRAPH_STR);
        HierarchyObject sentObj = new HierarchyObject(LEVEL_SENTENCE_ID, LEVEL_SENTENCE_STR);
        HierarchyObject wordObj = new HierarchyObject(LEVEL_WORD_ID, LEVEL_WORD_STR);
        
        ArrayList currentParagraphParent = new ArrayList();
        ArrayList currentSentenceParents = new ArrayList();
        ArrayList currentWordParents = new ArrayList();
        
        // Add current document as parent to paragraphs
        currentParagraphParent.add(docuObj);
        
        // Add current document, paragraph as parents to sentence
        currentSentenceParents.add(docuObj);
        currentSentenceParents.add(paraObj);
        
        // Add current document, paragraph, and sentence as parents to word
        currentWordParents.add(docuObj);
        currentWordParents.add(paraObj);
        currentWordParents.add(sentObj);
        
        
        try {
            input = new BufferedReader( new FileReader(m_fileName) );
            
            while (( line = input.readLine()) != null){
                // Paragraph break
                if(line.equals("")) {
                    // Signify paragraph found
                    //System.out.println("< NEW PARAGRAPH >");
                    
                    // Tell the rendering component to reset text position for new paragraph
                    TextObject3d.startNewParagraph();
                    
                    // Add the text value of the paragraph to the object
                    paraObj.setValue(paragraphText);
                    
                    paraObj.setParents(currentParagraphParent);
                    
                    // Add the paragraph to the document
                    documentText = documentText.concat(paragraphText);
                    
                    // Clear the paragraph string
                    paragraphText = new String();
                    
                    // Add the paragraph object to list of paragraphs
                    docuObj.addChild(paraObj);
                    
                    // Create a new container paragraph
                    paraObj = new HierarchyObject(LEVEL_PARAGRAPH_ID, LEVEL_PARAGRAPH_STR);
                    
                    // Set new sentence and word parents
                    currentSentenceParents = (ArrayList) currentSentenceParents.clone();
                    currentSentenceParents.set(LEVEL_PARAGRAPH_ID,paraObj);
                    currentWordParents = (ArrayList) currentWordParents.clone();
                    currentWordParents.set(LEVEL_PARAGRAPH_ID,paraObj);
                    
                    // Make sure our sentence is cleared out
                    sentenceText = new String();
                    sentObj = new HierarchyObject(LEVEL_SENTENCE_ID,LEVEL_SENTENCE_STR);
                    sentObj.setParents(currentSentenceParents);
                    currentWordParents.set(LEVEL_SENTENCE_ID,sentObj);
                    
                } else {
                    // Tokenize the entire sequence by spaces
                    wordTok = new StringTokenizer(line, " ", false);
                    // While we have words
                    while(wordTok.hasMoreTokens()) {
                        // Get the token
                        token = wordTok.nextToken();
                        
                        // Append to sentence string
                        sentenceText = sentenceText.concat(" " + token);
                        
                        // Create new object for the token
                        wordObj = new HierarchyObject(LEVEL_WORD_ID,LEVEL_WORD_STR);
                        
                        // Set value of the word object
                        wordObj.setValue(token);
                        
                        // Create 3d object and add it to the scene graph and hashMap
                        TextObject3d textObj = new TextObject3d(token);
                        wordObj.addSceneNode(textObj);
                        map.addHash(textObj,wordObj);
                        
                        // Add the parents to the word
                        wordObj.setParents(currentWordParents);
                        
                        // Add word object to the sentence object
                        sentObj.addChild(wordObj);
                        
                        // If we are also at the end of a sentence
                        if(token.contains(".") || token.contains("!") || token.contains("?")) {
                            //System.out.println("Sentence Found: " + sentenceText);
                            paragraphText = paragraphText.concat(sentenceText);
                            
                            // Add the sentence parents before adding the sentence to the paragraph
                            sentObj.setParents(currentSentenceParents);
                             
                            // Add the sentence object to list of sentence objects
                            paraObj.addChild(sentObj);
                            
                            // Add the value of sentence to the sentence object
                            sentObj.setValue(sentenceText);
                            
                            // Create new containers
                            sentenceText = new String();
                            sentObj = new HierarchyObject(LEVEL_SENTENCE_ID,LEVEL_SENTENCE_STR);
                            currentWordParents.set(LEVEL_SENTENCE_ID,sentObj);
                        }
                    }
                }
            }
            
            // Add any more paragraphs to the document
            paraObj.setValue(paragraphText);
            
            
            // Set the paragraph's parents
            paraObj.setParents(currentParagraphParent);
            
            // Add the paragraph object to list of paragraphs
            docuObj.addChild(paraObj);
            
            // Complete the document text
            documentText = documentText.concat(paragraphText);
            docuObj.setValue(documentText);
            
            // Set parser values
            m_hierarchyRoot = docuObj;
            m_rootNode = docuObj.getBranchGroup();
            
            // Save the root in the configuration manager
           ConfigurationManager.currentRoot = m_hierarchyRoot;
            
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}

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

import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.media.j3d.BranchGroup;

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
    
    private TreebankLanguagePack tlp;
    private BranchGroup m_rootNode = null;
    private HierarchyObject m_hierarchyRoot;
    private String m_fileName;
    
    /** Creates a new instance of RawTextParser */
    public RawTextParser(String filename) {
        m_fileName = filename;
        tlp = new PennTreebankLanguagePack();
    }
    
    public BranchGroup getRootNode() {
        return m_rootNode;
    }
    
    public HierarchyObject getHierarchyRoot() {
        return m_hierarchyRoot;
    }
    public void parse() {
        BranchGroup node = new BranchGroup();
        
        node.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        node.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        node.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        node.setCapability(BranchGroup.ALLOW_DETACH);
        
        BufferedReader input = null;
        String line = null;
        StringTokenizer wordTok = null;
        String token = null;
        String sentenceText = new String();
        String paragraphText = new String();
        String senToken = null;
        
        HierarchyObject docuObj = new HierarchyObject(LEVEL_DOCUMENT_ID, LEVEL_DOCUMENT_STR);
        HierarchyObject paraObj = new HierarchyObject(LEVEL_PARAGRAPH_ID, LEVEL_PARAGRAPH_STR);
        HierarchyObject sentObj = new HierarchyObject(LEVEL_SENTENCE_ID, LEVEL_SENTENCE_STR);
        HierarchyObject wordObj = new HierarchyObject(LEVEL_WORD_ID, LEVEL_WORD_STR);
        
        ArrayList currentSentenceParents = new ArrayList();
        ArrayList currentWordParents = new ArrayList();
        
        currentSentenceParents.add(docuObj);
        currentWordParents.add(docuObj);
        
        try {
            input = new BufferedReader( new FileReader(m_fileName) );            
            
            while (( line = input.readLine()) != null){
                // Paragraph break
                if(line.equals("")) {
                    // Signify paragraph found
                    System.out.println("< NEW PARAGRAPH >");
                    
                    // Add the text value of the paragraph to the object
                    paraObj.setValue(paragraphText);
                    
                    // Add the paragraph object to list of paragraphs
                    docuObj.addChild(paraObj);
                    
                    // Create a new container paragraph
                    paraObj = new HierarchyObject(LEVEL_PARAGRAPH_ID, LEVEL_PARAGRAPH_STR);
                    
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

                        // Create 3d object and add it to the scene graph
                        wordObj.setTransformGroup(new TextObject3d(token));

                        // Add word object to the sentence object
                        sentObj.addChild(wordObj);                            

                        // If we are also at the end of a sentence
                        if(token.contains(".") || token.contains("!") || token.contains("?")) {
                            System.out.println("Sentence Found: " + sentenceText);
                            paragraphText = paragraphText.concat(sentenceText);
                            
                            // Add the sentence object to list of sentence objects
                            paraObj.addChild(sentObj);

                            // Add the value of sentence to the sentence object
                            sentObj.setValue(sentenceText);
                            
                            // Create new containers
                            sentenceText = new String();                            
                            sentObj = new HierarchyObject(LEVEL_SENTENCE_ID,LEVEL_SENTENCE_STR);
                        }
                    }
                }
            }
            
            // Add any more paragraphs to the document
            paraObj.setValue(paragraphText);
            
            // Add the paragraph object to list of paragraphs      
            docuObj.addChild(paraObj);
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}

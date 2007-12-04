/*
 * RawTextParser.java
 *
 * Created on December 3, 2007, 4:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package util;

import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;

/**
 *
 * @author Christopher Leung
 */
public class RawTextParser {
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
        StringTokenizer sentenceTok = null;
        String token = null;
        String sentence = new String();
        String paragraph = new String();
        String senToken = null;
        
        HierarchyObject dObj = new HierarchyObject(HierarchyObject.LEVEL_DOCUMENT_ID,HierarchyObject.LEVEL_DOCUMENT_STR,null);
        HierarchyObject pObj = new HierarchyObject(HierarchyObject.LEVEL_PARAGRAPH_ID,HierarchyObject.LEVEL_PARAGRAPH_STR,null);
        HierarchyObject sObj = new HierarchyObject(HierarchyObject.LEVEL_SENTENCE_ID,HierarchyObject.LEVEL_SENTENCE_STR,null);
        HierarchyObject wObj = new HierarchyObject(HierarchyObject.LEVEL_WORD_ID,HierarchyObject.LEVEL_WORD_STR,null);
        
        try {
            input = new BufferedReader( new FileReader(m_fileName) );

            while (( line = input.readLine()) != null){
                // Paragraph break
                if(line.equals("")) {
                    // Signify paragraph found
                    System.out.println("< NEW PARAGRAPH >");
                    
                    // Add the paragraph object to list of paragraphs
                }
                else {
                    // Tokenize for sentence breaks
                    sentenceTok = new StringTokenizer(line, ".!?", true);
                    
                    // While we have sentences
                    while(sentenceTok.hasMoreTokens()) {
                        
                        // Get all the individual words
                        senToken = sentenceTok.nextToken();
                        wordTok = new StringTokenizer(senToken," ", false);
                        
                        // While we have words or characters
                        while(wordTok.hasMoreTokens()) {
                            
                            // Get the token
                            token = wordTok.nextToken();
                            
                            // If we are at the end of a sentence
                            if(token.contains(".") || token.contains("!") || token.contains("?")) {
                                System.out.println("Sentence Found: " + sentence + token);
                                paragraph = paragraph.concat(sentence);
                                sentence = new String();
                                
                                // Add the sentence object to list of sentence objects
                            }
                            // Else append the word to the current sentence
                            else {
                                
                                // Append to sentence string
                                sentence = sentence.concat(" " + token);
                                
                                // Create object for the token
                                
                                // Add the token to the list of sentence objects
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
    
    private BranchGroup renderParagraph(String para){
        BranchGroup node = new BranchGroup();
        
        node.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        node.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        node.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        node.setCapability(BranchGroup.ALLOW_DETACH);
        
        int i;
        String[] sentences= para.split("\\.+");
        for (i=0; i<sentences.length; i++) {
            node.addChild(renderSentence(sentences[i]+"."));
        }
        return node;
    }
    
    private BranchGroup renderSentence(String sentence){
        BranchGroup node = new BranchGroup();
        
        node.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        node.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        node.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        node.setCapability(BranchGroup.ALLOW_DETACH);
        
        int i;
        String[] words= sentence.split("\\s+");
        for (i=0; i<words.length; i++) {
            node.addChild(renderWord(words[i]));
        }
        return node;
    }
    
    private BranchGroup renderWord(String word) {
        BranchGroup node = new BranchGroup();
        
        node.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        node.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        node.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        node.setCapability(BranchGroup.ALLOW_DETACH);
        
        node.addChild(new TextObject3d(word));
        
        return node;
    }
    
}

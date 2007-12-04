/*
 * RawTextParser.java
 *
 * Created on December 3, 2007, 4:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package util;

import edu.stanford.nlp.ling.BasicDocument;
import edu.stanford.nlp.ling.Document;
import edu.stanford.nlp.process.Processor;
import edu.stanford.nlp.process.StripTagsProcessor;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;
import javax.swing.JOptionPane;

/**
 *
 * @author Christopher Leung
 */
public class RawTextParser {
    private TreebankLanguagePack tlp;
    private Node m_rootNode;
    private HierarchyObject m_hierarchyRoot;
    private String m_fileName;
    
    /** Creates a new instance of RawTextParser */
    public RawTextParser(String filename) {
        m_fileName = filename;
        tlp = new PennTreebankLanguagePack();
    }
    
    public Node getRootNode() {
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

        File file = new File(m_fileName);
        
        String urlOrFile = m_fileName;
        // if file can't be found locally, try prepending http:// and looking on web
        if (!file.exists() && m_fileName.indexOf("://") == -1) {
            urlOrFile = "http://" + m_fileName;
        }
        // else prepend file:// to handle local html file urls
        else if (m_fileName.indexOf("://") == -1) {
            urlOrFile = "file://" + m_fileName;
        }
        
        // load the document
        Document doc = null;
        try {
            if (urlOrFile.startsWith("http://") || urlOrFile.endsWith(".htm") || urlOrFile.endsWith(".html")) {
                // strip tags from html documents
                Document docPre = new BasicDocument().init(new URL(urlOrFile));
                Processor noTags = new StripTagsProcessor();
                doc = noTags.processDocument(docPre);
            } else {
                doc = new BasicDocument(tlp.getTokenizerFactory()).init(new InputStreamReader(new FileInputStream(m_fileName), tlp.getEncoding()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Could not load file " + m_fileName + "\n" + e, null, JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        // load the document into string
        StringBuilder docStr = new StringBuilder();
        for (Iterator it = doc.iterator(); it.hasNext(); ) {
            if (docStr.length() > 0) {
                docStr.append(' ');
            }
            docStr.append(it.next().toString());
        }
        
        // parse the string
        StringTokenizer strtok = new StringTokenizer(docStr.toString(), ".!?", true);
        while(strtok.hasMoreTokens()) {
            String sentence = strtok.nextToken();
            if (strtok.hasMoreTokens()) {
                sentence += strtok.nextToken();
            }
            System.out.println(sentence);
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

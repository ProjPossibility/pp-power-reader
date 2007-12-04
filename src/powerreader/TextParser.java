/*
 * TextParser.java
 *
 * Created on December 2, 2007, 10:05 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package powerreader;

// Import file loader and parser stuff
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.Processor;
import edu.stanford.nlp.process.StripTagsProcessor;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.WordSegmentingTokenizer;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import java.net.*;
import java.io.CharArrayReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author zhanshi
 */
public class TextParser {
    private static TreebankLanguagePack tlp;
    private LexicalizedParser parser;
    private ArrayList parseTree;
    
    /** Creates a new instance of TextParser */
    public TextParser() {
        // Initialize file loader and parser
        tlp = new PennTreebankLanguagePack();
        // parser = new LexicalizedParser("englishPCFG.ser.gz");
        // replace this with full path to parser?
        parser = new LexicalizedParser("D:/Class/588/Project/stanford-parser-2007-08-19/englishPCFG.ser.gz");
        parseTree = new ArrayList();
    }
    
    public ArrayList loadFile(String filename) {
        if (filename == null) {
            return null;
        }
        
        File file = new File(filename);
        
        String urlOrFile = filename;
        // if file can't be found locally, try prepending http:// and looking on web
        if (!file.exists() && filename.indexOf("://") == -1) {
            urlOrFile = "http://" + filename;
        }
        // else prepend file:// to handle local html file urls
        else if (filename.indexOf("://") == -1) {
            urlOrFile = "file://" + filename;
        }
        
        // load the document
        Document doc;
        try {
            if (urlOrFile.startsWith("http://") || urlOrFile.endsWith(".htm") || urlOrFile.endsWith(".html")) {
                // strip tags from html documents
                Document docPre = new BasicDocument().init(new URL(urlOrFile));
                Processor noTags = new StripTagsProcessor();
                doc = noTags.processDocument(docPre);
            } else {
                doc = new BasicDocument(tlp.getTokenizerFactory()).init(new InputStreamReader(new FileInputStream(filename), tlp.getEncoding()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Could not load file " + filename + "\n" + e, null, JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
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
            Tokenizer<? extends HasWord> toke = tlp.getTokenizerFactory().getTokenizer(new CharArrayReader(sentence.toCharArray()));
            List<? extends HasWord> wordList = toke.tokenize();
            try {
                if (parser.parse(wordList)) {
                    Tree t = parser.getBestPCFGParse().children()[0];
                    parseTree.add(t);
                    for (int j = 0; j < t.children().length; j++) {
                        System.out.println(t.children()[j].label().value());
                    }
                }
            } catch (Exception e) {
                // sentence might have been too long - just give up and add it
                
            }
        }
        return parseTree;
    }
    
    /**
     * Worker thread for parsing.  Since parsing can be slow, it might
     * be a better idea to use a separate thread rather than block.
     */
    private class ParseThread extends Thread {
        
        List<? extends HasWord> sentence;
        
        public ParseThread(List<? extends HasWord> sentence) {
            this.sentence = sentence;
        }
        
        public void run() {
            boolean successful;
            try {
                successful = parser.parse(sentence);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Could not parse selected sentence\n(sentence probably too long)", null, JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (successful) {
                // display the best parse
                Tree tree = parser.getBestParse();
            } else {
                JOptionPane.showMessageDialog(null, "Could not parse selected sentence", null, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
}

/*
 * GoogleImageFetcher.java
 *
 * Created on December 2, 2007, 8:31 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package image;

import java.io.*;
import java.net.*;
import java.util.regex.*;

/**
 *
 * @author zhanshi
 */
public class GoogleImageFetcher extends ImageFetcher {
    
    /** Creates a new instance of GoogleImageFetcher */
    public GoogleImageFetcher() {
        imageFetcherName = "Google Images";
    }
    
    public String getImageURL(String text) {
        String imgURL = "";
        try {
            Socket s = new Socket("images.google.com",80);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            bw.write("GET /images?q=" + text + " HTTP/1.0\r\n");
            bw.write("User-Agent: Mozilla/1.0 (compatible; NCSA Mosaic; Atari 800-ST)\r\n");
            bw.write("Connection: close\r\n\r\n");
            bw.flush();
            
            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader buffer = new BufferedReader(in);
            String line;
            
            Pattern imgPattern = Pattern.compile("imgurl=([^&]+)&imgrefurl");
            while ((line = buffer.readLine()) != null) {
                Matcher match = imgPattern.matcher(line);
                if (match.find()) {
                    imgURL = match.group(1);
                    break;
                }
            }
            in.close();
        } catch (Exception e) {}
        return imgURL;
    }
}

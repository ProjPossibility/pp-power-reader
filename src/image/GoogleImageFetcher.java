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
            bw.write("GET /images?q=" + URLEncoder.encode(text) + " HTTP/1.0\r\n");
            bw.write("User-Agent: Mozilla/1.0 (compatible; NCSA Mosaic; Atari 800-ST)\r\n");
            bw.write("Connection: close\r\n\r\n");
            bw.flush();
            
            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader buffer = new BufferedReader(in);
            String line;
            
//            Pattern imgPattern = Pattern.compile("imgurl=([^&]+)&imgrefurl");
            Pattern imgPattern = Pattern.compile("(http://\\w+\\.google\\.com/images\\?[^\\s]+)");
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

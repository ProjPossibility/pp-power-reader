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
public class YahooImageFetcher extends ImageFetcher {
    
    /** Creates a new instance of YahooImageFetcher */
    public YahooImageFetcher() {
        imageFetcherName = "Yahoo Images";
    }
    
    public String getImageURL(String text) {
        String imgURL = "";
        try {
            // Create an URL instance
            URL url = new URL("http://images.search.yahoo.com/search/images?p=" + URLEncoder.encode(text));
            URLConnection connection = url.openConnection();
            DataInputStream in = new DataInputStream(connection.getInputStream());
            String line;
            Pattern imgPattern = Pattern.compile("isrc=\"([^\"]*)\"");
            while ((line = in.readLine()) != null) {
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

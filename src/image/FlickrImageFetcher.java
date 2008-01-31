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
import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.RequestContext;
import com.aetrion.flickr.photos.*;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @author zhanshi
 */
public class FlickrImageFetcher extends ImageFetcher {
    Flickr f;
    REST rest;
    RequestContext rc;
    
    /** Creates a new instance of FlickrImageFetcher */
    public FlickrImageFetcher() {
        String secret = "3e5f0f1ddeb11a0d";
        try {
        rest = new REST();
        rest.setHost("www.flickr.com");
        f = new Flickr("59336ed1a2cef1d9a8ceba4d8fc7dde5", rest);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        imageFetcherName = "Flickr";
    }

    public String getImageURL(String text) {
        PhotosInterface pi = f.getPhotosInterface();
        SearchParameters sp = new SearchParameters();
        sp.setText(text);
        try {
            PhotoList pl = pi.search(sp, 1, 1);
            if (!pl.isEmpty()) {
                return ((Photo) pl.get(0)).getThumbnailUrl();
                //return ((Photo) pl.get(0)).getUrl();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }
}

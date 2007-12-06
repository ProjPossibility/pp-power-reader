/*
 * FlickrImageFetcher.java
 *
 * Created on December 2, 2007, 12:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
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

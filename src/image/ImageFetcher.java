/*
 * ImageFetcher.java
 *
 * Created on December 2, 2007, 8:29 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package image;
import java.net.*;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import javax.imageio.*;

/**
 *
 * @author zhanshi
 */
public abstract class ImageFetcher {
    
    protected String imageFetcherName;
    protected Hashtable imageCache;
    
    public ImageFetcher() {
        imageCache = new Hashtable();
    }
    
    public abstract String getImageURL(String text);

    public BufferedImage getImage(String text) {
        if (imageCache.containsKey(text)) {
            return (BufferedImage) imageCache.get(text);
        }
        BufferedImage img;
        String imgURL = getImageURL(URLEncoder.encode(text));
        System.out.println("Fetching image from " + imgURL);
        try {
            URL url = new URL(imgURL);
            img = ImageIO.read(url);
            // save the image for future use
            imageCache.put(text, img);
            return img;
        } catch (Exception e) {
            System.out.println("Unable to retrieve image from " + imgURL);
        }
        return null;
    }
    public String getName() {
        return imageFetcherName;
    }
}

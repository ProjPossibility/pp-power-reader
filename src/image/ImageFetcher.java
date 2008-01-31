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
        String imgURL = getImageURL(text);
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

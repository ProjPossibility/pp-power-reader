/*
 * ImageFetcher.java
 *
 * Created on December 2, 2007, 8:29 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package powerreader;
import java.net.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;

/**
 *
 * @author zhanshi
 */
public abstract class ImageFetcher {
    public abstract String getImageURL(String text);
    public BufferedImage getImage(String imgURL) {
        BufferedImage img;
        try {
            URL url = new URL(imgURL);
            img = ImageIO.read(url);
            return img;
        } catch (Exception e) {
            System.out.println("Unable to retrieve image from " + imgURL);
        }
        return null;
    }
}

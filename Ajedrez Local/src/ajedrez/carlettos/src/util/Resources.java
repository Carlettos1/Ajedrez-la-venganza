package ajedrez.carlettos.src.util;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public class Resources {
    public final static String ROOT = "/ajedrez/carlettos/assets/";
    
    public final static Image getImage(String source){
        URL url = ROOT.getClass().getResource(ROOT + source);
        ImageIcon icon = new ImageIcon(url);
        return icon.getImage();
    }
}
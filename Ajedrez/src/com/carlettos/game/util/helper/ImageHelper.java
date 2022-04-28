package com.carlettos.game.util.helper;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.TreeMap;

import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlettos
 */
public class ImageHelper {
    public static final String PATH_PATTERN = FileHelper.TEXTURES_FOLDER + "%s.png";
    private static final Map<String, BufferedImage> IMAGES = new TreeMap<>();
    private static final Map<String, BufferedImage> IMAGES_WHITE = new TreeMap<>();
    
    public static final synchronized void clearTextures() {
        IMAGES.clear();
        IMAGES_WHITE.clear();
    }
    
    public static final synchronized BufferedImage getImage(String textureName) {
        if (IMAGES.containsKey(textureName)) {
            return IMAGES.get(textureName);
        }
        var img = FileHelper.getImageFromFile(PATH_PATTERN.formatted(textureName));
        IMAGES.put(textureName, img);
        return img;
    }
    
    public static final synchronized BufferedImage getImage(Piece piece) {
        var name = piece.getResourceLocation().getTextureName();
        var color = piece.getColor();
        var img = getImage(name);
        if (color == Color.BLACK) {
            return img;
        } else {
            if (IMAGES_WHITE.containsKey(name)) {
                return IMAGES_WHITE.get(name);
            }
            img = replaceColors(img, 
                    new Color[]{Color.BLACK, Color.WHITE}, 
                    new Color[]{Color.WHITE, Color.BLACK});
            IMAGES_WHITE.put(name, img);
            return img;
        }
    }
    
    public static final BufferedImage replaceColors(BufferedImage image, Color[] from, Color[] to) {
        if (from.length != to.length) {
            LogHelper.LOG.warning("Colors to replace are of different lenghts");
            return image;
        }
        var copy = getEmptyImage(image);
        
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                boolean modified = false;
                checkColors: for (int i = 0; i < from.length; i++) {
                    if (image.getRGB(x, y) == from[i].getAWT().getRGB()) {
                        copy.setRGB(x, y, to[i].getAWT().getRGB());
                        modified = true;
                        break checkColors;
                    }
                }
                if (!modified) {
                    copy.setRGB(x, y, image.getRGB(x, y));
                }
            }
        }
        return copy;
    }
    
    public static final BufferedImage getEmptyImage(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }
    
    public static final BufferedImage getEmptyImage(BufferedImage image) {
        return getEmptyImage(image.getWidth(), image.getHeight());
    }
}

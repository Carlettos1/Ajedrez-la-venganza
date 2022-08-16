package com.carlettos.game.util.helper;

import java.awt.image.BufferedImage;

import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlettos
 */
public class ImageHelper {
    public static final BufferedImage replaceColors(BufferedImage image, Color[] from, Color[] to) {
        if (from.length != to.length) {
            LogHelper.LOG.warning("Colors to replace are of different lenghts");
            return image;
        }
        var copy = getEmptyImage(image);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                boolean modified = false;
                for (int i = 0; i < from.length; i++) {
                    if (image.getRGB(x, y) == from[i].getAWT().getRGB()) {
                        copy.setRGB(x, y, to[i].getAWT().getRGB());
                        modified = true;
                        break;
                    }
                }
                if (!modified) {
                    copy.setRGB(x, y, image.getRGB(x, y));
                }
            }
        }
        return copy;
    }

    public static final BufferedImage getEmptyImage(BufferedImage image) {
        return new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
    }
}

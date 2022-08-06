package com.carlettos.game.util.resource;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.helper.FileHelper;
import com.carlettos.game.util.helper.ImageHelper;

public record ImageResource(String subFolder, String name) implements IImageable {

    private static final Pattern PATTERN_VALID_FOLDER = Pattern.compile("([a-z0-9_\\\\])");
    private static final String PATH_FORMATTER = FileHelper.TEXTURES_FOLDER + "%s.png";
    private static final Map<String, BufferedImage> IMAGES = new TreeMap<>();

    public ImageResource {
        TranslateResource.requireStandard(name);
        if (!subFolder.replaceAll(PATTERN_VALID_FOLDER.pattern(), "").isEmpty()) {
            throw new IllegalArgumentException("Non [a-z0-9_\\] character in folder " + subFolder);
        }
    }

    public ImageResource(String name) {
        this("", name);
    }

    /**
     * Works as an Unique ID for every ImageResource
     */
    public String getSubPath() {
        return subFolder + "\\" + name;
    }

    @Override
    public BufferedImage getImage() {
        if (IMAGES.containsKey(this.getSubPath())) { return IMAGES.get(this.getSubPath()); }
        var img = FileHelper.getImageFromFile(PATH_FORMATTER.formatted(this.getSubPath()));
        IMAGES.put(this.getSubPath(), img);
        return img;
    }

    public BufferedImage getImage(Color color) {
        var img = this.getImage();

        // by default the images are black, so no changes necessary.
        // and gray is the default color.
        if (color == Color.BLACK || color == Color.GRAY) { return img; }

        String newKey = this.getSubPath() + "_" + color.toString().toLowerCase();
        if (IMAGES.containsKey(newKey)) { return IMAGES.get(newKey); }
        Color[] from = { Color.BLACK, color };
        Color[] to = { color, Color.BLACK };
        img = ImageHelper.replaceColors(img, from, to);
        IMAGES.put(newKey, img);
        return img;
    }

    public static void updateResurces() {
        IMAGES.clear();
    }
}

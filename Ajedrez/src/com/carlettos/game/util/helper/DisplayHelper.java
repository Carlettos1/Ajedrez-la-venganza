package com.carlettos.game.util.helper;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;

import com.carlettos.game.util.Point;

/**
 * It provides methods of help to other classes.
 * 
 * @author Carlos
 */
public class DisplayHelper {
    public static final Font FONT_6 = new Font(Font.SANS_SERIF, Font.PLAIN, 6);
    public static final Font FONT_8 = new Font(Font.SANS_SERIF, Font.PLAIN, 8);
    public static final Font FONT_10 = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
    public static final Font FONT_12 = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    
    private DisplayHelper() {}

    public static Component getComponentAt(Component component, Point point) {
        return getComponentAt(component, point.x, point.y);
    }
    
    public static Component getLastComponentAt(Component component, int x, int y) {
        var comp = component;
        while(comp instanceof Container && ((Container)comp).getComponents().length != 0) {
            var loc = getAbsoluteLocation(comp);
            comp = getComponentAt(comp, x - loc.x, y - loc.y);
        }
        return comp;
    }

    public static Component getComponentAt(Component component, int x, int y) {
        if (!(component instanceof Container)) {
            return component;
        }
        Container container = (Container) component;
        for (Component comp : container.getComponents()) {
            if ("null.glassPane".equals(comp.getName())) {
                continue;
            }
            if (comp.contains(x - comp.getX(), y - comp.getY())) {
                return comp;
            }
        }
        return null;
    }

    public static Point getAbsoluteLocation(Component component) {
        int x = component.getX();
        int y = component.getY(); 
        
        Container container = component.getParent();
        if (container == null) {
            return new Point(x, y);
        }
        while (container.getParent() != null) {
            x += container.getX();
            y += container.getY();
            container = container.getParent();
        }
        return new Point(x + container.getX(), y + container.getY());
    }
}

package com.carlettos.game.core.helper;

import com.carlettos.game.core.Point;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;

/**
 * It provides methods of help to other classes.
 * 
 * @author Carlos
 */
public class DisplayHelper {
    private static Font FONT;
    
    public static Font getFont() {
        if (FONT == null) {
            FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
        }
        return FONT;
    }

    public static Component getComponentAt(Component component, Point point) {
        return getComponentAt(component, point.x, point.y);
    }
    
    public static Component getLastComponentAt(Component component, int x, int y) {
        var comp = component;
        while(comp instanceof Container) {
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
            if (comp instanceof Container) {
                if (((Container) (comp)).getComponents().length == 0) {
                    continue;
                }
            }
            if (comp.contains(x - comp.getX(), y - comp.getY())) {
                return comp;
            }
        }
        return null;
    }

    public static Point getAbsoluteLocation(Component component) {
        int x = component.getX(), y = component.getY();
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

package com.carlettos.game.input;

import com.carlettos.game.core.Point;
import com.carlettos.game.visual.BoardDisplay;
import java.awt.Component;
import java.awt.Container;

/**
 * It provides methods of help to other classes.
 * 
 * @author Carlos
 */
public class DisplayHelper {

    /**
     * It gets the last parent of the component and cast it to BoardDisplay.
     * 
     * @param component
     * 
     * @return BoardDisplay of the component.
     */
    public static BoardDisplay getBoardDisplay(Component component){
        Container lastParent = component.getParent();
        while (lastParent.getParent() != null) {
            lastParent = lastParent.getParent();
        }
        return (BoardDisplay) lastParent;
    }

    public static Component getComponentAt(Component component, Point point) {
        return getComponentAt(component, point.x, point.y);
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
        while (container.getParent() != null) {
            x += container.getX();
            y += container.getY();
            container = container.getParent();
        }
        return new Point(x + container.getX(), y + container.getY());
    }
}

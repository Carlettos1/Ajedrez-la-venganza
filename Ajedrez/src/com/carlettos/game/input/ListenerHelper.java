package com.carlettos.game.input;

import com.carlettos.game.visual.BoardDisplay;
import java.awt.Component;
import java.awt.Container;

/**
 *
 * @author Carlos
 */
public class ListenerHelper {
    public static BoardDisplay getTableroVisual(Component component){
        Container lastParent = component.getParent();
        while (lastParent.getParent() != null) {
            lastParent = lastParent.getParent();
        }
        return (BoardDisplay) lastParent;
    }
}

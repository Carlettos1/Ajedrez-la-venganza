package com.carlettos.game.display.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Carlettos
 */
public class ClickDeckButton implements ActionListener {
    private static final ClickDeckButton LISTENER = new ClickDeckButton();
    
    private ClickDeckButton() {
    }
    
    public static ClickDeckButton get() {
        return LISTENER;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}

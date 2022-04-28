package com.carlettos.game.display.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.carlettos.game.display.board.BoardDisplay;

/**
 *
 * @author Carlettos
 */
public class ClickTurnButton implements ActionListener {
    private static final ClickTurnButton LISTENER = new ClickTurnButton();

    private ClickTurnButton() {
    }
    
    public static ClickTurnButton get(){
        return LISTENER;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        var board = BoardDisplay.getInstance();
        board.getClockDisplay().getClock().endTurn();
        board.offAll();
        board.repaint();
    }
}

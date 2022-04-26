package com.carlettos.game.display.listeners;

import com.carlettos.game.display.board.BoardDisplay;
import com.carlettos.game.display.info.InfoDisplay;
import com.carlettos.game.gameplay.ability.InfoManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Carlettos
 */
public class ClickAbilityButton implements ActionListener {
    private static final ClickAbilityButton LISTENER = new ClickAbilityButton();
    
    private ClickAbilityButton() {
    }
    
    public static ClickAbilityButton get() {
        return LISTENER;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (MousePiece.get().selected == null) {
            return;
        }
        var escaque = MousePiece.get().selected.getEscaque();
        var board = BoardDisplay.getInstance();
        
        if (!escaque.getPiece().getAbility().commonCanUse(board.getBoard(), escaque.getPiece())) {
            return;
        }
        
        var values = escaque.getPiece().getAbility().getPossibleValues(board.getBoard(), escaque.getPos());
        //board, escaque
        int i = InfoDisplay.showOptions(board.getBoard(), escaque); //TODO: ahorrar código
        if(i == -1){
            return;
        }

        var valor = values[i];
        var info = InfoManager.getInfo(valor); //todo: quitar canUse de la carta, y usar can de piece.
        var ar = escaque.getPiece().getAbility().canUse(board.getBoard(), escaque.getPiece(), escaque.getPos(), info);

        if (ar.isPositive()) {
            escaque.getPiece().getAbility().use(board.getBoard(),
                    escaque.getPiece(),
                    escaque.getPos(),
                    info);
            board.getBoard().movement();
            MousePiece.get().selected = null;
            board.offAll();
            board.repaint();
        }
    }
}
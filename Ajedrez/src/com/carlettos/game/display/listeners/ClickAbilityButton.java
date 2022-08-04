package com.carlettos.game.display.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.carlettos.game.display.board.BoardDisplay;
import com.carlettos.game.display.info.InfoDisplay;

/**
 *
 * @author Carlettos
 */
public class ClickAbilityButton implements ActionListener {
    private static final ClickAbilityButton LISTENER = new ClickAbilityButton();

    private ClickAbilityButton() {}

    public static ClickAbilityButton get() {
        return LISTENER;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (MousePiece.get().getSelected() == null) { return; }
        var escaque = MousePiece.get().getSelected().getEscaque();
        var board = BoardDisplay.getInstance();

        if (!escaque.getPiece().getAbility().commonCanUse(board.getBoard(), escaque.getPiece())) { return; }

        var values = escaque.getPiece().getAbility().getValues(board.getBoard(), escaque.getPos());
        // board, escaque TODO: ahorrar código
        int i = InfoDisplay.showOptions(board.getBoard(), escaque);
        if (i == -1) { return; }

        // todo: quitar canUse de la carta, y usar can de piece.
        var valor = values[i];
        var info = valor.toInfo();
        var ar = escaque.getPiece().getAbility().canUse(board.getBoard(), escaque.getPiece(), escaque.getPos(), info);

        if (ar.isPositive()) {
            escaque.getPiece().getAbility().use(board.getBoard(), escaque.getPiece(), escaque.getPos(), info);
            board.getBoard().movement();
            MousePiece.get().setSelected(null);
            board.offAll();
            board.repaint();
        }
    }
}

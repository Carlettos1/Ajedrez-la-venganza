package com.carlettos.game.board.piece.pattern;

import com.carlettos.game.board.property.Color;

/**
 * 
 * @author Carlettos
 */
public interface PatternPawn extends Pattern {

    /**
     * Returns the color of the pawn. //TODO: cambiar por dirección de tablero
     * @return the color of the pawn.
     */
    public Color getColor();
}

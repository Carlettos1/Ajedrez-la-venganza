package com.carlettos.game.gameplay.pattern;

import com.carlettos.game.util.enums.Color;

/**
 * 
 * @author Carlettos
 */
public interface PatternPawn extends Pattern {

    /**
     * Returns the color of the pawn. //TODO: cambiar por dirección de tablero
     * 
     * @return the color of the pawn.
     */
    public Color getColor();
}

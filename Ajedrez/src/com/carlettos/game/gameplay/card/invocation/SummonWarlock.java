package com.carlettos.game.gameplay.card.invocation;

import com.carlettos.game.gameplay.card.SummonPiece;
import com.carlettos.game.gameplay.piece.starting.Warlock;

/**
 *
 * @author Carlettos
 */
public class SummonWarlock extends SummonPiece<Warlock> {

    public SummonWarlock() {
        super("Invocar Brujo", 
                "Invoca un brujo en la ubicación elegida al final del turno.",
                5, c -> new Warlock(c));
    }
}

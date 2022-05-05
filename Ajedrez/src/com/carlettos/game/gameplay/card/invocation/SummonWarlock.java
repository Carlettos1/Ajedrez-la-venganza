package com.carlettos.game.gameplay.card.invocation;

import com.carlettos.game.gameplay.card.SummonPiece;
import com.carlettos.game.gameplay.piece.starting.Warlock;

/**
 *
 * @author Carlettos
 */
public class SummonWarlock extends SummonPiece<Warlock> {

    public SummonWarlock() {
        super("warlock", 5, Warlock::new);
    }
}

package com.carlettos.game.gameplay.card.invocation;

import com.carlettos.game.gameplay.card.SummonPiece;
import com.carlettos.game.gameplay.piece.classic.Knight;

/**
 *
 * @author Carlos
 */
public class SummonKnight extends SummonPiece<Knight> {

    public SummonKnight() {
        super("knight", 2, Knight::new);
    }
}

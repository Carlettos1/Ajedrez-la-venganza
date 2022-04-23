package com.carlettos.game.board.card.invocation;

import com.carlettos.game.board.card.SummonPiece;
import com.carlettos.game.board.piece.classic.Knight;

/**
 * 
 * @author Carlos
 */
public class SummonKnight extends SummonPiece<Knight> {

    public SummonKnight() {
        super("Invocar Caballo", 
                "Invoca un caballo en la ubicación elegida al final del turno.",
                2, c -> new Knight(c));
    }
}

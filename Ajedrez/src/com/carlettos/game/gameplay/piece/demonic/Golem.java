package com.carlettos.game.gameplay.piece.demonic;

import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.gameplay.piece.type.PieceType;
import com.carlettos.game.util.enums.Color;

public class Golem extends SimplePiece {

    public Golem(Color color) {
        super("golem", ability, color, Patterns.ARCHER_MOVE_PATTERN, PieceType.DEMONIC, PieceType.IMMUNE, PieceType.STRUCTURE, PieceType.IMPENETRABLE);
    }
}

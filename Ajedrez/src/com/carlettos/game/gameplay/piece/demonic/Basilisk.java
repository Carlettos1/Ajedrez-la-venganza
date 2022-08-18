package com.carlettos.game.gameplay.piece.demonic;

import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.gameplay.piece.type.PieceType;
import com.carlettos.game.util.enums.Color;

public class Basilisk extends SimplePiece {

    public Basilisk(Color color) {
        super("basilisk", ability, color, Patterns.BISHOP_PATTERN, PieceType.DEMONIC, PieceType.IMMUNE);
    }
}

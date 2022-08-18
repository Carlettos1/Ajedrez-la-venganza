package com.carlettos.game.gameplay.piece.demonic;

import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.gameplay.piece.type.PieceType;
import com.carlettos.game.util.enums.Color;

public class Gargoyle extends SimplePiece {

    public Gargoyle(Color color) {
        super("gargoyle", ability, color, Patterns.GARGOYLE_PATTERN, PieceType.DEMONIC);
    }
}

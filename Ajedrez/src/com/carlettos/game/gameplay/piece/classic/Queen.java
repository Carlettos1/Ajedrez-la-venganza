package com.carlettos.game.gameplay.piece.classic;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.enums.Color;

public class Queen extends SimplePiece {
    public Queen(Color color) {
        super("queen", Abilities.QUEEN_ABILITY, color, Patterns.QUEEN_PATTERN, IPieceType.BIOLOGIC, IPieceType.HEROIC);
    }
}

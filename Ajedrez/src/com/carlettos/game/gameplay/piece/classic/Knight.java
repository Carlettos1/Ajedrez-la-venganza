package com.carlettos.game.gameplay.piece.classic;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.pattern.classic.PatternKnight;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.enums.Color;

public class Knight extends SimplePiece<PatternKnight> {
    public Knight(Color color) {
        super("knight", Abilities.KNIGHT_ABILITY, color, Patterns.KNIGHT_PATTERN, IPieceType.BIOLOGIC,
                IPieceType.TRANSPORTABLE);
    }
}

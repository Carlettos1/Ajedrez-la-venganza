package com.carlettos.game.gameplay.piece.classic;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.pattern.classic.PatternQueen;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.PieceType;

public class Queen extends SimplePiece<PatternQueen> {
    public Queen(Color color) {
        super("queen", Abilities.QUEEN_ABILITY, color, Patterns.QUEEN_PATTERN, PieceType.BIOLOGIC, PieceType.HEROIC);
    }
}

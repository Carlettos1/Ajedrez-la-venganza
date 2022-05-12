package com.carlettos.game.gameplay.piece.classic;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.classic.PatternKnight;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.PieceType;

public class Knight extends SimplePiece<PatternKnight> {
    public Knight(Color color) {
        super("knight", Abilities.ABILITY_KNIGHT, color, new PatternKnight(){}, PieceType.BIOLOGIC, PieceType.TRANSPORTABLE);
    }
}

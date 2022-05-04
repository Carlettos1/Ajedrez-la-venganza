package com.carlettos.game.gameplay.piece.classic;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.classic.PatternRook;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.PieceType;

public class Rook extends SimplePiece<PatternRook> {
    public Rook(Color color) {
        super("rook", "r", Abilities.ABILITY_ROOK, color, new PatternRook(){}, PieceType.STRUCTURE);
    }
}

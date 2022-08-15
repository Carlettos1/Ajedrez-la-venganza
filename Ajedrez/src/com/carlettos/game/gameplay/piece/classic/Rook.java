package com.carlettos.game.gameplay.piece.classic;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.pattern.classic.PatternRook;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Color;

public class Rook extends SimplePiece<PatternRook> {
    public Rook(Color color) {
        super("rook", Abilities.ROOK_ABILITY, color, Patterns.ROOK_PATTERN, IPieceType.STRUCTURE);
    }
}

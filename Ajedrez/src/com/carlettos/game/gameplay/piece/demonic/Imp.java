package com.carlettos.game.gameplay.piece.demonic;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Color;

public class Imp extends Piece implements IMove {
    public Imp(Color color) {
        super("imp", Abilities.IMP_ABILITY, color, IPieceType.DEMONIC, IPieceType.BIOLOGIC);
    }

    @Override
    public Pattern getMovePattern(AbstractBoard board, Point start) {
        return Patterns.KNIGHT_PATTERN;
    }
}

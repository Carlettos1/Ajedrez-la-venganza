package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.pattern.action.IAttack;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlettos
 */
public class Ballista extends Piece implements IMove, IAttack {
    public Ballista(Color color) {
        super("ballista", Abilities.NO_ABILITY, color, IPieceType.STRUCTURE);
    }

    @Override
    public Pattern getAttackPattern(AbstractBoard board, Point start) {
        return Patterns.BALLISTA_ATTACK_PATTERN;
    }

    @Override
    public Pattern getMovePattern(AbstractBoard board, Point start) {
        return Patterns.STRUCTURE_MOVE_PATTERN;
    }
}

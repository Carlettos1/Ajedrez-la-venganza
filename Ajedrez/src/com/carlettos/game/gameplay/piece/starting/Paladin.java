package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.pattern.action.ITake;
import com.carlettos.game.gameplay.pattern.classic.PatternQueen;
import com.carlettos.game.gameplay.piece.Empty;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.PieceType;

/**
 *
 * @author Carlettos
 */
public class Paladin extends Piece implements IMove<PatternQueen>, ITake<PatternQueen> {
    protected final PatternQueen pattern;

    public Paladin(Color color) { //TODO: Habilidad
        super("paladin", "PA", Empty.NO_ABILITY, color, PieceType.HEROIC, PieceType.IMMUNE);
        pattern = new PatternQueen(){};
    }

    @Override
    public ActionResult can(Action action, AbstractBoard board, Point start, Info info) {
        return switch(action){
            case MOVE -> this.canMove(board, start, info, pattern);
            case TAKE -> this.canTake(board, start, info, pattern);
            default -> ActionResult.FAIL;
        };
    }
}

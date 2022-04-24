package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.pattern.starting.PatternMagicianMove;
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
public class Magician extends Piece implements IMove<PatternMagicianMove> {
    protected final PatternMagicianMove movePattern;
    public Magician(Color color) { //TODO: hacer lo de la habilidad
        super("magician", "HE", Empty.NO_ABILITY, color, PieceType.BIOLOGIC, PieceType.HEROIC, PieceType.IMMUNE, PieceType.TRANSPORTABLE);
        movePattern = new PatternMagicianMove() {};
    }

    @Override
    public ActionResult can(Action action, AbstractBoard board, Point start, Info info) {
        return switch(action) {
            case MOVE -> this.canMove(board, start, info, this.movePattern);
            default -> ActionResult.FAIL;
        };
    }
}

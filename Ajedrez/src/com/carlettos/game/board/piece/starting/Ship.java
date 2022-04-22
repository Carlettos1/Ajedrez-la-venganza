package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.pattern.action.IMove;
import com.carlettos.game.board.piece.pattern.action.ITake;
import com.carlettos.game.board.piece.pattern.classic.PatternKing;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.board.property.PieceType;
import com.carlettos.game.board.piece.pattern.starting.PatternMagicianMove;
import com.carlettos.game.board.property.ability.Info;
import com.carlettos.game.board.property.ability.info.InfoNone;
import com.carlettos.game.board.property.ability.InfoUse.AbilityNone;

/**
 *
 * @author Carlettos
 */
public class Ship extends Piece implements IMove<PatternMagicianMove>, ITake<PatternKing> {
    public static final Ability<Piece, String, InfoNone> ABILITY_SHIP = new AbilityShip<>();
    protected final PatternMagicianMove movePattern;
    protected final PatternKing takePattern;

    public Ship(Color color) {
        super("ship", "N", ABILITY_SHIP, color, PieceType.STRUCTURE);
        this.movePattern = new PatternMagicianMove() {};
        this.takePattern = new PatternKing() {};
    }

    @Override
    public ActionResult can(Action action, AbstractBoard board, Point start, Info info) {
        return switch(action){
            case MOVE -> this.canMove(board, start, info, movePattern);
            case TAKE -> this.canTake(board, start, info, takePattern);
            case ABILITY -> this.getAbility().canUse(board, this, start, info);
            default -> ActionResult.FAIL;
        };
    }

    public static class AbilityShip<P extends Piece> extends Ability<P, String, InfoNone> implements AbilityNone {

        public AbilityShip() {
            super("Ataque en area", 
                    "Ataca a las 6 casillas adyacentes",
                    12, 0, 
                    "ninguno");
        }

        @Override
        public ActionResult canUse(AbstractBoard board, P piece, Point start, InfoNone info) {
            return ActionResult.fromBoolean(this.commonCanUse(board, piece));
        }

        @Override
        public void use(AbstractBoard board, P piece, Point start, InfoNone info) {
            Point[] puntos = new Point[]{start.add(1, 1), start.add(1, 0), start.add(1, -1),
                                       start.add(-1, 1), start.add(-1, 0), start.add(-1, -1)};
            for (Point point : puntos) {
                if(!board.isOutOfBorder(point)){
                    board.removePiece(point);
                }
            }
            this.commonUse(board, piece);
        }
    }
}

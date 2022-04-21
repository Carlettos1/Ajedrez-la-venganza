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
import com.carlettos.game.board.property.ability.InfoNone;
import com.carlettos.game.board.property.ability.InfoGetter.AbilityNone;

/**
 *
 * @author Carlettos
 */
public class Ship extends Piece implements IMove<PatternMagicianMove>, ITake<PatternKing> {
    public static final Ability<Piece, String, InfoNone> HABILIDAD_NAVE = new HabilidadNave<>();
    protected final PatternMagicianMove patronMover;
    protected final PatternKing patronComer;

    public Ship(Color color) {
        super("Nave", "N", HABILIDAD_NAVE, color, PieceType.STRUCTURE);
        this.patronMover = new PatternMagicianMove() {};
        this.patronComer = new PatternKing() {};
    }

    @Override
    public ActionResult can(Action accion, AbstractBoard tablero, Point inicio, Info info) {
        return switch(accion){
            case MOVE -> this.canMove(tablero, inicio, info, patronMover);
            case TAKE -> this.canTake(tablero, inicio, info, patronComer);
            case ABILITY -> this.getAbility().canUse(tablero, this, inicio, info);
            default -> ActionResult.FAIL;
        };
    }

    public static class HabilidadNave<P extends Piece> extends Ability<P, String, InfoNone> implements AbilityNone{

        public HabilidadNave() {
            super("Ataque en area", 
                    "Ataca a las 6 casillas adyacentes",
                    12, 0, 
                    "ninguno");
        }

        @Override
        public ActionResult canUse(AbstractBoard tablero, P pieza, Point inicio, InfoNone info) {
            return ActionResult.fromBoolean(this.commonCanUse(tablero, pieza));
        }

        @Override
        public void use(AbstractBoard tablero, P pieza, Point inicio, InfoNone info) {
            Point[] puntos = new Point[]{inicio.add(1, 1), inicio.add(1, 0), inicio.add(1, -1),
                                       inicio.add(-1, 1), inicio.add(-1, 0), inicio.add(-1, -1)};
            for (Point punto : puntos) { //TODO: quitar try
                try {
                    tablero.removePiece(punto);
                } catch (Exception e) {
                }
            }
            this.commonUse(tablero, pieza);
        }
    }
}

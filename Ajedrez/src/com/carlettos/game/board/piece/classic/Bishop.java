package com.carlettos.game.board.piece.classic;

import com.carlettos.game.board.piece.SimplePiece;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Direction;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.board.property.PieceType;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.classic.PatternBishop;
import com.carlettos.game.board.property.ability.info.InfoDirection;
import java.util.ArrayList;
import java.util.List;
import com.carlettos.game.board.property.ability.InfoUse.AbilityDirection;

/**
 *
 * @author Carlos
 */
public class Bishop extends SimplePiece<PatternBishop> {

    public static final Ability<Bishop, Direction, InfoDirection> ABILITY_BISHOP = new AbilityBishop<>();

    public Bishop(Color color) {
        super("bishop", "A", ABILITY_BISHOP, color, new PatternBishop(){}, PieceType.BIOLOGIC, PieceType.TRANSPORTABLE);
    }

    public static class AbilityBishop<P extends Piece> extends Ability<P, Direction, InfoDirection> implements AbilityDirection {
        public AbilityBishop() {
            super("Cambio de Color",
                    "El alfil cambia de color, pudiendo moverse una casilla en cualquiera de las 4 direcciones cardinales.",
                    2,
                    0,
                    "La dirección cardinal hacia dónde se mueve el alfil (NESW).");
        }

        @Override
        public ActionResult canUse(AbstractBoard board, P piece, Point start, InfoDirection info) {
            if (!super.commonCanUse(board, piece)) {
                return ActionResult.FAIL;
            }
            
            boolean can;
            
            if(info.isAxis(Direction.Axis.NS)){
                if(board.isOutOfBorder(start.add(0, info.getSign()))){
                    can = false;
                } else {
                    can = !board.getEscaque(start.add(0, info.getSign())).hasPiece();
                }
            } else {
                if(board.isOutOfBorder(start.add(info.getSign(), 0))){
                    can = false;
                } else {
                    can = !board.getEscaque(start.add(info.getSign(), 0)).hasPiece();
                }
            }
            return ActionResult.fromBoolean(can);
        }

        @Override
        public void use(AbstractBoard board, P piece, Point start, InfoDirection info) {
            if(info.isAxis(Direction.Axis.NS)){
                board.getEscaque(start.add(0, info.getSign())).setPiece(piece);
            } else {
                board.getEscaque(start.add(info.getSign(), 0)).setPiece(piece);
            }
            board.getEscaque(start).removePiece();
            this.commonUse(board, piece);
        }

        @Override
        public Direction[] getPossibleValues(AbstractBoard board, Point start) {
            List<Direction> values = new ArrayList<>(4);
            for (Direction direction : Direction.values()) {
                boolean can;
            
                if(direction.isAxis(Direction.Axis.NS)){
                    if(board.isOutOfBorder(start.add(0, direction.getSign()))){
                        can = false;
                    } else {
                        can = !board.getEscaque(start.add(0, direction.getSign())).hasPiece();
                    }
                } else {
                    if(board.isOutOfBorder(start.add(direction.getSign(), 0))){
                        can = false;
                    } else {
                        can = !board.getEscaque(start.add(direction.getSign(), 0)).hasPiece();
                    }
                }
                if(can){
                    values.add(direction);
                }
            }
            return values.toArray(Direction[]::new);
        }
    }
}

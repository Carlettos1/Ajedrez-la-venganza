package com.carlettos.game.gameplay.piece.classic;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.InfoUse.AbilityDirection;
import com.carlettos.game.gameplay.ability.info.InfoDirection;
import com.carlettos.game.gameplay.pattern.classic.PatternBishop;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.Direction;
import com.carlettos.game.util.enums.PieceType;

/**
 *
 * @author Carlos
 */
public class Bishop extends SimplePiece<PatternBishop> {

    public static final Ability<Bishop, Direction, InfoDirection> ABILITY_BISHOP = new AbilityBishop<>();

    public Bishop(Color color) {
        super("bishop", "b", ABILITY_BISHOP, color, new PatternBishop(){}, PieceType.BIOLOGIC, PieceType.TRANSPORTABLE);
    }

    public static class AbilityBishop<P extends Piece> extends Ability<P, Direction, InfoDirection> implements AbilityDirection {
        public AbilityBishop() {
            super("Cambio de Color",
                    "El alfil cambia de color, pudiendo moverse una casilla en cualquiera de las 4 direcciones cardinales.",
                    2,
                    0);
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

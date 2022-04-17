package com.carlettos.game.board.piece.classic;

import com.carlettos.game.board.piece.SimplePiece;
import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Direction;
import com.carlettos.game.core.Tuple;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.board.property.PieceType;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.classic.PatternBishop;
import com.carlettos.game.board.property.ability.InfoNESW;
import com.carlettos.game.board.property.ability.InfoGetter.HabilidadNESW;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class Bishop extends SimplePiece<PatternBishop> {

    public static final Ability<Bishop, Direction, InfoNESW> HABILIDAD_ALFIL = new HabilidadAlfil<>();

    public Bishop(Color color) {
        super("Alfil", "A", HABILIDAD_ALFIL, color, new PatternBishop(){}, PieceType.BIOLOGICA, PieceType.TRANSPORTABLE);
    }

    public static class HabilidadAlfil<P extends Piece> extends Ability<P, Direction, InfoNESW> implements HabilidadNESW {
        public HabilidadAlfil() {
            super("Cambio de Color",
                    "El alfil cambia de color, pudiendo moverse una casilla en cualquiera de las 4 direcciones cardinales.",
                    2,
                    0,
                    "La dirección cardinal hacia dónde se mueve el alfil (NESW).");
        }

        @Override
        public ActionResult canUse(AbstractBoard tablero, P pieza, Point inicio, InfoNESW info) {
            if (!super.commonCanUse(tablero, pieza)) {
                return ActionResult.FAIL;
            }
            
            boolean verificacion;
            
            if(info.isAxis(Direction.Axis.NS)){
                if(tablero.isOutOfBorder(inicio.add(0, info.getSign()))){
                    verificacion = false;
                } else {
                    verificacion = !tablero.getEscaque(inicio.add(0, info.getSign())).hasPiece();
                }
            } else {
                if(tablero.isOutOfBorder(inicio.add(info.getSign(), 0))){
                    verificacion = false;
                } else {
                    verificacion = !tablero.getEscaque(inicio.add(info.getSign(), 0)).hasPiece();
                }
            }
            return ActionResult.fromBoolean(verificacion);
        }

        @Override
        public void use(AbstractBoard tablero, P pieza, Point inicio, InfoNESW info) {
            if(info.isAxis(Direction.Axis.NS)){
                tablero.getEscaque(inicio.add(0, info.getSign())).setPiece(pieza);
            } else {
                tablero.getEscaque(inicio.add(info.getSign(), 0)).setPiece(pieza);
            }
            tablero.getEscaque(inicio).quitarPieza();
            this.commonUse(tablero, pieza);
        }

        @Override
        public Direction[] getAllValoresPosibles(AbstractBoard tablero, Point inicio) {
            List<Direction> valores = new ArrayList<>(4);
            for (Direction direction : Direction.values()) {
                boolean verificacion;
            
                if(direction.isAxis(Direction.Axis.NS)){
                    if(tablero.isOutOfBorder(inicio.add(0, direction.getSign()))){
                        verificacion = false;
                    } else {
                        verificacion = !tablero.getEscaque(inicio.add(0, direction.getSign())).hasPiece();
                    }
                } else {
                    if(tablero.isOutOfBorder(inicio.add(direction.getSign(), 0))){
                        verificacion = false;
                    } else {
                        verificacion = !tablero.getEscaque(inicio.add(direction.getSign(), 0)).hasPiece();
                    }
                }
                if(verificacion){
                    valores.add(direction);
                }
            }
            return valores.toArray(Direction[]::new);
        }
    }
}

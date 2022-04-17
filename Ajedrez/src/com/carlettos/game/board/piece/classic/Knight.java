package com.carlettos.game.board.piece.classic;

import com.carlettos.game.board.piece.SimplePiece;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.board.property.PieceType;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.classic.PatternKnight;
import com.carlettos.game.board.property.ability.InfoNone;
import com.carlettos.game.board.property.ability.InfoGetter.HabilidadSinInfo;

public class Knight extends SimplePiece<PatternKnight> {

    public static final Ability<Knight, String, InfoNone> HABILIDAD_CABALLO = new HabilidadCaballo<>();

    public Knight(Color color) {
        super("Caballo", "C", HABILIDAD_CABALLO, color, new PatternKnight(){}, PieceType.BIOLOGICA, PieceType.TRANSPORTABLE);
    }

    public static class HabilidadCaballo<P extends Piece> extends Ability<P, String, InfoNone> implements HabilidadSinInfo {

        public HabilidadCaballo() {
            super("Bajar Jinetes",
            "Invoca 2 peones, uno a cada lado del caballo (EW). Ambas casillas deben estar vacías",
            10,
            1,
            "No requiere información adicional");
        }

        @Override
        public ActionResult canUse(AbstractBoard tablero, P pieza, Point inicio, InfoNone info) {
            if (!this.commonCanUse(tablero, pieza)) {
                return ActionResult.FAIL;
            }

            Point p1 = new Point(inicio.x + 1, inicio.y);
            Point p2 = new Point(inicio.x - 1, inicio.y);

            if (tablero.getEscaque(p1).hasPiece() || tablero.getEscaque(p2).hasPiece()) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        }

        @Override
        public void use(AbstractBoard tablero, P pieza, Point inicio, InfoNone info) {
            Point p1 = new Point(inicio.x + 1, inicio.y);
            Point p2 = new Point(inicio.x - 1, inicio.y);
            tablero.getEscaque(p1).setPiece(new Pawn(pieza.getColor()));
            tablero.getEscaque(p2).setPiece(new Pawn(pieza.getColor()));
            this.commonUse(tablero, pieza);
        }
    }
}

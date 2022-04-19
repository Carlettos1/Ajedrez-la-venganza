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
import com.carlettos.game.board.piece.pattern.classic.PatternQueen;
import com.carlettos.game.board.property.ability.InfoPoint;
import com.carlettos.game.board.property.ability.InfoGetter.HabilidadPoint;

public class Queen extends SimplePiece<PatternQueen> {

    public static final Ability<Queen, Point, InfoPoint> HABILIDAD_REINA = new HabilidadReina<>();

    public Queen(Color color) {
        super("Reina", "R", HABILIDAD_REINA, color, new PatternQueen(){}, PieceType.BIOLOGICA, PieceType.HEROICA);
    }

    public static class HabilidadReina<P extends Piece> extends Ability<P, Point, InfoPoint> implements HabilidadPoint {

        public HabilidadReina() {
            super("Movimiento Caballístico.",
                    "Permite a la reina moverse como caballo, comiendo cualquier pieza en la que caiga, incluida piezas aliadas.",
                    5,
                    0,
                    "\"dx dy\", literalmente");
        }

        @Override
        public ActionResult canUse(AbstractBoard tablero, Piece pieza, Point inicio, InfoPoint info) {
            if (!this.commonCanUse(tablero, pieza)) {
                return ActionResult.FAIL;
            }

            if(!new PatternKnight() {}.match(tablero, inicio, info.getValor())){
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        }

        @Override
        public void use(AbstractBoard tablero, Piece pieza, Point inicio, InfoPoint info) {
            tablero.getEscaque(info.getValor()).setPiece(pieza);
            tablero.removePiece(inicio);
            this.commonUse(tablero, pieza);
        }

        @Override
        public Point[] getAllValoresPosibles(AbstractBoard tablero, Point inicio) { //todo: que no salga del mapa
            Point[] valores = new Point[8];
            valores[0] = (new Point(-2, -1));
            valores[1] = (new Point(-2, 1));
            valores[2] = (new Point(2, -1));
            valores[3] = (new Point(2, 1));
            valores[4] = (new Point(-1, -2));
            valores[5] = (new Point(-1, 2));
            valores[6] = (new Point(1, -2));
            valores[7] = (new Point(1, 2));
            return valores;
        }
    }
}

package com.carlettos.game.board.piece.classic;

import com.carlettos.game.board.piece.SimplePiece;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.board.property.PieceType;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.classic.PatternKing;
import com.carlettos.game.board.property.ability.InfoPoint;
import com.carlettos.game.board.property.ability.InfoGetter.HabilidadPoint;
import java.util.ArrayList;
import java.util.List;

public class King extends SimplePiece<PatternKing> {

    //TODO: que no se muera después de comer o moverse
    protected boolean seHaTeletransportado;
    public static final Ability<King, Point, InfoPoint> HABILIDAD_REY = new HabilidadRey<>();

    public King(Color color) {
        super("Rey", "RE", HABILIDAD_REY, color, new PatternKing(){}, PieceType.BIOLOGICA, PieceType.INMUNE, PieceType.HEROICA);
        this.seHaTeletransportado = false;
    }
    
    public static class HabilidadRey<P extends King> extends Ability<P, Point, InfoPoint> implements HabilidadPoint {

        public HabilidadRey() {
            super("Teletransportación",
                    "Se teletransporta a cualquier casilla en un rango de 5",
                    0,
                    2,
                    "Debe ser algo de la forma \"dx dy\", con espacio incluido");
        }

        @Override
        public ActionResult canUse(AbstractBoard tablero, P pieza, Point inicio, InfoPoint info) {
            if (pieza.seHaTeletransportado) {
                return ActionResult.FAIL;
            }

            return ActionResult.fromBoolean(info.getValor().getDistanceTo(inicio) <= 5);
        }

        @Override
        public void use(AbstractBoard tablero, P pieza, Point inicio, InfoPoint info) {
            pieza.seHaTeletransportado = true;
            tablero.getEscaque(info.getValor()).setPiece(pieza);
            tablero.getEscaque(inicio).removePiece();
        }

        @Override
        public Point[] getAllValoresPosibles(AbstractBoard tablero, Point inicio) {
            List<Point> valores = new ArrayList<>();
            for (int x = 0; x < tablero.columns; x++) {
                for (int y = 0; y < tablero.rows; y++) {
                    if(new Point(x, y).getDistanceTo(inicio) <= 5 && !tablero.getEscaque(x, y).hasPiece()){
                        valores.add(new Point(x, y));
                    }
                }
            }
            return valores.toArray(Point[]::new);
        }
    }
}

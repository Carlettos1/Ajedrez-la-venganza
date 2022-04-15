package com.carlettos.game.board.piece.classic;

import com.carlettos.game.board.piece.PiezaSimple;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.board.piece.Pieza;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Habilidad;
import com.carlettos.game.board.property.Tipo;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.classic.PatronCaballo;
import com.carlettos.game.board.property.ability.InfoNinguna;
import com.carlettos.game.board.property.ability.InfoGetter.HabilidadSinInfo;

public class Caballo extends PiezaSimple<PatronCaballo> {

    public static final Habilidad<Caballo, String, InfoNinguna> HABILIDAD_CABALLO = new HabilidadCaballo<>();

    public Caballo(Color color) {
        super("Caballo", "C", HABILIDAD_CABALLO, color, new PatronCaballo(){}, Tipo.BIOLOGICA, Tipo.TRANSPORTABLE);
    }

    public static class HabilidadCaballo<P extends Pieza> extends Habilidad<P, String, InfoNinguna> implements HabilidadSinInfo {

        public HabilidadCaballo() {
            super("Bajar Jinetes",
            "Invoca 2 peones, uno a cada lado del caballo (EW). Ambas casillas deben estar vacías",
            10,
            1,
            "No requiere información adicional");
        }

        @Override
        public ActionResult canUsar(AbstractBoard tablero, P pieza, Point inicio, InfoNinguna info) {
            if (!this.commonCanUsar(tablero, pieza)) {
                return ActionResult.FAIL;
            }

            Point p1 = new Point(inicio.x + 1, inicio.y);
            Point p2 = new Point(inicio.x - 1, inicio.y);

            if (tablero.getEscaque(p1).hasPieza() || tablero.getEscaque(p2).hasPieza()) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        }

        @Override
        public void usar(AbstractBoard tablero, P pieza, Point inicio, InfoNinguna info) {
            Point p1 = new Point(inicio.x + 1, inicio.y);
            Point p2 = new Point(inicio.x - 1, inicio.y);
            tablero.getEscaque(p1).setPieza(new Peon(pieza.getColor()));
            tablero.getEscaque(p2).setPieza(new Peon(pieza.getColor()));
            this.commonUsar(tablero, pieza);
        }
    }
}

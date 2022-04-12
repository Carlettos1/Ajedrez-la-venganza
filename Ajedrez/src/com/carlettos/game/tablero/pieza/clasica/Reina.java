package com.carlettos.game.tablero.pieza.clasica;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.habilidad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.AbstractTablero;
import com.carlettos.game.tablero.pieza.patron.clasico.PatronCaballo;
import com.carlettos.game.tablero.pieza.patron.clasico.PatronReina;
import com.carlettos.game.tablero.propiedad.habilidad.InfoPoint;
import com.carlettos.game.tablero.propiedad.habilidad.InfoGetter.HabilidadPoint;

public class Reina extends PiezaSimple<PatronReina> {

    public static final Habilidad<Reina, Point, InfoPoint> HABILIDAD_REINA = new HabilidadReina<>();

    public Reina(Color color) {
        super("Reina", "R", HABILIDAD_REINA, color, new PatronReina(){}, Tipo.BIOLOGICA, Tipo.HEROICA);
    }

    public static class HabilidadReina<P extends Pieza> extends Habilidad<P, Point, InfoPoint> implements HabilidadPoint {

        public HabilidadReina() {
            super("Movimiento Caballístico.",
                    "Permite a la reina moverse como caballo, comiendo cualquier pieza en la que caiga, incluida piezas aliadas.",
                    5,
                    0,
                    "\"dx dy\", literalmente");
        }

        @Override
        public ActionResult canUsar(AbstractTablero tablero, Pieza pieza, Point inicio, InfoPoint info) {
            if (!this.commonCanUsar(tablero, pieza)) {
                return ActionResult.FAIL;
            }

            if(!new PatronCaballo() {}.checkPatron(tablero, inicio, info.getValor())){
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        }

        @Override
        public void usar(AbstractTablero tablero, Pieza pieza, Point inicio, InfoPoint info) {
            tablero.getEscaque(info.getValor()).setPieza(pieza);
            tablero.quitarPieza(inicio);
            this.commonUsar(tablero, pieza);
        }

        @Override
        public Point[] getAllValoresPosibles(AbstractTablero tablero, Point inicio) { //todo: que no salga del mapa
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

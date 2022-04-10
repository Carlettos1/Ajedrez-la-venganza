package com.carlettos.game.tablero.pieza.clasica;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.habilidad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.pieza.patron.clasico.PatronCaballo;

public class Caballo extends PiezaSimple<PatronCaballo> {

    public static final Habilidad<Caballo> HABILIDAD_CABALLO = new HabilidadCaballo<>();

    public Caballo(Color color) {
        super("Caballo", "C", HABILIDAD_CABALLO, color, new PatronCaballo(){}, Tipo.BIOLOGICA, Tipo.TRANSPORTABLE);
    }

    public static class HabilidadCaballo<P extends Pieza> extends Habilidad<P> {

        public HabilidadCaballo() {
            super("Bajar Jinetes",
            "Invoca 2 peones, uno a cada lado del caballo (EW). Ambas casillas deben estar vacías",
            10,
            1,
            "No requiere información adicional");
        }

        @Override
        public ActionResult canUsar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            if (pieza.getCdActual() > 0) {
                return ActionResult.FAIL;
            }

            if (pieza.seHaMovidoEsteTurno()) {
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
        public void usar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            Point p1 = new Point(inicio.x + 1, inicio.y);
            Point p2 = new Point(inicio.x - 1, inicio.y);
            tablero.getEscaque(p1).setPieza(new Peon(pieza.getColor()));
            tablero.getEscaque(p2).setPieza(new Peon(pieza.getColor()));
            //TODO: cambiar cd y maná
        }
    }
}

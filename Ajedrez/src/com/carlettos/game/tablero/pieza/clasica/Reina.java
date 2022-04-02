package com.carlettos.game.tablero.pieza.clasica;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.pieza.patron.clasico.PatronReina;

public class Reina extends PiezaSimple<PatronReina> {

    public static final Habilidad<Reina> HABILIDAD_REINA = new HabilidadReina<>();

    public Reina(Color color) {
        super("Reina", "R", HABILIDAD_REINA, color, new PatronReina(){}, Tipo.BIOLOGICA, Tipo.HEROICA);
    }

    public static class HabilidadReina<P extends Pieza> extends Habilidad<P> {

        public HabilidadReina() {
            super("Movimiento Caballístico.",
                    "Permite a la reina moverse como caballo, comiendo cualquier pieza en la que caiga, incluida piezas aliadas.",
                    5,
                    0,
                    "Un número en x y otro en y para que se mueva, ej: -1 2, con el espacio incluido.");
        }

        @Override
        public ActionResult canUsar(Tablero tablero, Pieza pieza, Point inicio, Point final_, String informacionExtra) {
            if (pieza.getCdActual() > 0) {
                return ActionResult.FAIL;
            }

            if (pieza.seHaMovidoEsteTurno()) {
                return ActionResult.FAIL;
            }

            if (!(informacionExtra.equals("1 2")
                    || informacionExtra.equals("2 1")
                    || informacionExtra.equals("-1 2")
                    || informacionExtra.equals("-2 1")
                    || informacionExtra.equals("-1 -2")
                    || informacionExtra.equals("-2 -1")
                    || informacionExtra.equals("1 -2")
                    || informacionExtra.equals("2 -1"))) {
                return ActionResult.FAIL;
            }

            int xInicio = tablero.getEscaque(inicio).getLocalizacion().x;
            int yInicio = tablero.getEscaque(inicio).getLocalizacion().y;
            int dx = 0;
            int dy = 0;

            switch (informacionExtra) {
                case "1 2" -> {
                    dx = 1;
                    dy = 2;
                }
                case "2 1" -> {
                    dx = 2;
                    dy = 1;
                }
                case "-1 2" -> {
                    dx = -1;
                    dy = 2;
                }
                case "-2 1" -> {
                    dx = -2;
                    dy = 1;
                }
                case "-1 -2" -> {
                    dx = -1;
                    dy = -2;
                }
                case "-2 -1" -> {
                    dx = -2;
                    dy = -1;
                }
                case "1 -2" -> {
                    dx = 1;
                    dy = -2;
                }
                case "2 -1" -> {
                    dx = 2;
                    dy = -1;
                }
            }

            if (xInicio + dx < tablero.columnas && xInicio + dx >= 0) {
                if (yInicio + dy < tablero.filas && yInicio + dy >= 0) {
                    return ActionResult.PASS;
                }
            }
            return ActionResult.FAIL;
        }

        @Override
        public void usar(Tablero tablero, Pieza pieza, Point inicio, Point final_, String informacionExtra) {
            int dx = 0;
            int dy = 0;

            switch (informacionExtra) {
                case "1 2" -> {
                    dx = 1;
                    dy = 2;
                }
                case "2 1" -> {
                    dx = 2;
                    dy = 1;
                }
                case "-1 2" -> {
                    dx = -1;
                    dy = 2;
                }
                case "-2 1" -> {
                    dx = -2;
                    dy = 1;
                }
                case "-1 -2" -> {
                    dx = -1;
                    dy = -2;
                }
                case "-2 -1" -> {
                    dx = -2;
                    dy = -1;
                }
                case "1 -2" -> {
                    dx = 1;
                    dy = -2;
                }
                case "2 -1" -> {
                    dx = 2;
                    dy = -1;
                }
            }

            tablero.getEscaque(inicio.add(dx, dy)).setPieza(pieza);
            tablero.quitarEntidad(inicio);
            //TODO: cambiar cd y maná
        }
    }
}

package com.carlettos.game.tablero.pieza.clasica;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.Escaque;

public class Reina extends PiezaClasica {

    public static final Habilidad<Reina> HABILIDAD_REINA = new HabilidadReina<Reina>();

    public Reina(Color color) {
        super("Reina", "R", HABILIDAD_REINA, color, Tipo.BIOLOGICA);
    }

    @Override
    public ActionResult canMover(Tablero tablero, Point inicio, Point final_) {
        if (tablero.getEscaque(final_).hasPieza()) {
            return ActionResult.FAIL;
        }

        if (this.seHaMovidoEsteTurno()) {
            return ActionResult.FAIL;
        }

        boolean mueveComoAlfil = false;

        Escaque escaqueInicio = tablero.getEscaque(inicio);
        Escaque escaqueFinal = tablero.getEscaque(final_);

        int xInicio = escaqueInicio.getLocalizacion().x;
        int xFinal = escaqueFinal.getLocalizacion().x;
        int yInicio = escaqueInicio.getLocalizacion().y;
        int yFinal = escaqueFinal.getLocalizacion().y;

        int deltaX = xFinal - xInicio;
        int deltaY = yFinal - yInicio;

        int direccionX = 0;
        int direccionY = yFinal > yInicio ? 1 : -1;

        if (xFinal != xInicio) { //se mueve en x
            if (yFinal != yInicio) { //se mueve en y
                if (Math.abs(deltaX) != Math.abs(deltaY)) {
                    return ActionResult.FAIL;
                } else {
                    mueveComoAlfil = true;
                }
            } else {
                direccionX = xFinal > xInicio ? 1 : -1;
                direccionY = 0;
            }
        }

        if (mueveComoAlfil) {

            int signoX = deltaX > 0 ? 1 : -1;
            int signoY = deltaY > 0 ? 1 : -1;

            for (int casilla = 1; casilla <= Math.abs(deltaX); casilla++) {
                if (tablero.getEscaque(xInicio + casilla * signoX, yInicio + casilla * signoY).hasPieza()) {
                    return ActionResult.FAIL;
                }
            }
            return ActionResult.PASS;
        } else {

            boolean isMovimientoVertical = direccionY != 0;

            for (int casilla = 1; casilla <= (isMovimientoVertical ? Math.abs(yInicio - yFinal) : Math.abs(xInicio - xFinal)); casilla++) {
                if (tablero.getEscaque(xInicio + casilla * direccionX, yInicio + (casilla * direccionY)).hasPieza()) {
                    return ActionResult.FAIL;
                }
            }
            return ActionResult.PASS;
        }
    }

    @Override
    public ActionResult canComer(Tablero tablero, Point inicio, Point final_) {

        if (this.getColor().equals(tablero.getEscaque(final_).getColorControlador())) {
            return ActionResult.FAIL;
        }
        if (tablero.getEscaque(final_).hasPieza()) {
            return ActionResult.FAIL;
        }

        if (seHaMovidoEsteTurno()) {
            return ActionResult.FAIL;
        }

        boolean mueveComoAlfil = false;

        Escaque escaqueInicio = tablero.getEscaque(inicio);
        Escaque escaqueFinal = tablero.getEscaque(final_);

        int xInicio = escaqueInicio.getLocalizacion().x;
        int xFinal = escaqueFinal.getLocalizacion().x;
        int yInicio = escaqueInicio.getLocalizacion().y;
        int yFinal = escaqueFinal.getLocalizacion().y;

        int deltaX = xFinal - xInicio;
        int deltaY = yFinal - yInicio;

        int direccionX = 0;
        int direccionY = yFinal > yInicio ? 1 : -1;

        if (xFinal != xInicio) { //se mueve en x
            if (yFinal != yInicio) { //se mueve en y
                if (Math.abs(deltaX) != Math.abs(deltaY)) {
                    return ActionResult.FAIL;
                } else {
                    mueveComoAlfil = true;
                }
            } else {
                direccionX = xFinal > xInicio ? 1 : -1;
                direccionY = 0;
            }
        }

        if (mueveComoAlfil) {

            int signoX = deltaX > 0 ? 1 : -1;
            int signoY = deltaY > 0 ? 1 : -1;

            for (int casilla = 1; casilla < Math.abs(deltaX); casilla++) {
                if (tablero.getEscaque(xInicio + casilla * signoX, yInicio + casilla * signoY).hasPieza()) {
                    return ActionResult.FAIL;
                }
            }
            return ActionResult.PASS;
        } else {

            boolean isMovimientoVertical = direccionY != 0;

            for (int casilla = 1; casilla < (isMovimientoVertical ? Math.abs(yInicio - yFinal) : Math.abs(xInicio - xFinal)); casilla++) {
                if (tablero.getEscaque(xInicio + casilla * direccionX, yInicio + (casilla * direccionY)).hasPieza()) {
                    return ActionResult.FAIL;
                }
            }
            return ActionResult.PASS;
        }
    }

    //TODO: habilidad reina
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
                case "1 2":
                    dx = 1;
                    dy = 2;
                    break;
                case "2 1":
                    dx = 2;
                    dy = 1;
                    break;
                case "-1 2":
                    dx = -1;
                    dy = 2;
                    break;
                case "-2 1":
                    dx = -2;
                    dy = 1;
                    break;
                case "-1 -2":
                    dx = -1;
                    dy = -2;
                    break;
                case "-2 -1":
                    dx = -2;
                    dy = -1;
                    break;
                case "1 -2":
                    dx = 1;
                    dy = -2;
                    break;
                case "2 -1":
                    dx = 2;
                    dy = -1;
                    break;
            }
            dy = -dy; //para que tenga sentido con el visual

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
                case "1 2":
                    dx = 1;
                    dy = 2;
                    break;
                case "2 1":
                    dx = 2;
                    dy = 1;
                    break;
                case "-1 2":
                    dx = -1;
                    dy = 2;
                    break;
                case "-2 1":
                    dx = -2;
                    dy = 1;
                    break;
                case "-1 -2":
                    dx = -1;
                    dy = -2;
                    break;
                case "-2 -1":
                    dx = -2;
                    dy = -1;
                    break;
                case "1 -2":
                    dx = 1;
                    dy = -2;
                    break;
                case "2 -1":
                    dx = 2;
                    dy = -1;
                    break;
            }
            dy = -dy; //para que tenga sentido con el visual

            tablero.getEscaque(inicio.add(dx, dy)).setPieza(pieza);
            tablero.quitarEntidad(inicio);
            //TODO: cambiar cd y maná
        }
    }
}

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
        super("Reina", "R", HABILIDAD_REINA, color, Tipo.BIOLOGICA, Tipo.HEROICA);
    }

    @Override
    public ActionResult canMover(Tablero tablero, Point inicio, Point final_) {
        if (tablero.getEscaque(final_).hasPieza()) {
            return ActionResult.FAIL;
        }

        if (this.seHaMovidoEsteTurno()) {
            return ActionResult.FAIL;
        }

        boolean isAlfil = true;

        int deltaX = final_.x - inicio.x;
        int deltaY = final_.y - inicio.y;

        if (Math.abs(deltaY) != Math.abs(deltaX)) {
            isAlfil = false;
        }

        if (isAlfil) {
            int signoX = deltaX > 0 ? 1 : -1;
            int signoY = deltaY > 0 ? 1 : -1;

            for (int escaque = 1; escaque < Math.abs(deltaX); escaque++) {
                if (tablero.getEscaque(inicio.x + escaque * signoX,
                        inicio.y + escaque * signoY).hasPieza()) {
                    return ActionResult.FAIL;
                }
            }
            return ActionResult.PASS;
        } else {
            if (final_.x != inicio.x && final_.y != inicio.y) {
                return ActionResult.FAIL;
            }

            if (final_.x != inicio.x) { //se mueve el x
                int direccion = final_.x > inicio.x ? 1 : -1;
                for (int puntero = 1; puntero < Math.abs(final_.x - inicio.x); puntero++) {
                    if (tablero.getEscaque(inicio.x + puntero * direccion, inicio.y).hasPieza()) {
                        return ActionResult.FAIL;
                    }
                }
            } else if (final_.y != inicio.y) { //se mueve en y
                int direccion = final_.y > inicio.y ? 1 : -1;
                for (int puntero = 1; puntero < Math.abs(final_.y - inicio.y); puntero++) {
                    if (tablero.getEscaque(inicio.x, inicio.y + puntero * direccion).hasPieza()) {
                        return ActionResult.FAIL;
                    }
                }
            }
            return ActionResult.PASS;
        }
    }

    @Override
    public ActionResult canComer(Tablero tablero, Point inicio, Point final_) {
        if (!tablero.getEscaque(final_).hasPieza()) {
            return ActionResult.FAIL;
        }

        if (tablero.getEscaque(final_).getPieza().getColor().equals(getColor())) {
            return ActionResult.FAIL;
        }

        if (seHaMovidoEsteTurno()) {
            return ActionResult.FAIL;
        }

        boolean isAlfil = true;

        int deltaX = final_.x - inicio.x;
        int deltaY = final_.y - inicio.y;

        if (Math.abs(deltaY) != Math.abs(deltaX)) {
            isAlfil = false;
        }

        if (isAlfil) {
            int signoX = deltaX > 0 ? 1 : -1;
            int signoY = deltaY > 0 ? 1 : -1;

            for (int escaque = 1; escaque < Math.abs(deltaX); escaque++) {
                if (tablero.getEscaque(inicio.x + escaque * signoX,
                        inicio.y + escaque * signoY).hasPieza()) {
                    return ActionResult.FAIL;
                }
            }
            return ActionResult.PASS;
        } else {
            if (final_.x != inicio.x && final_.y != inicio.y) {
                return ActionResult.FAIL;
            }

            if (final_.x != inicio.x) { //se mueve el x
                int direccion = final_.x > inicio.x ? 1 : -1;
                for (int puntero = 1; puntero < Math.abs(final_.x - inicio.x); puntero++) {
                    if (tablero.getEscaque(inicio.x + puntero * direccion, inicio.y).hasPieza()) {
                        return ActionResult.FAIL;
                    }
                }
            } else if (final_.y != inicio.y) { //se mueve en y
                int direccion = final_.y > inicio.y ? 1 : -1;
                for (int puntero = 1; puntero < Math.abs(final_.y - inicio.y); puntero++) {
                    if (tablero.getEscaque(inicio.x, inicio.y + puntero * direccion).hasPieza()) {
                        return ActionResult.FAIL;
                    }
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

            tablero.getEscaque(inicio.add(dx, dy)).setPieza(pieza);
            tablero.quitarEntidad(inicio);
            //TODO: cambiar cd y maná
        }
    }
}

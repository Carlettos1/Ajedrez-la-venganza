package com.carlettos.game.tablero.pieza.clasica;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Par;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import com.carlettos.game.core.Point;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class Alfil extends PiezaClasica {

    public static final Habilidad<Alfil> HABILIDAD_ALFIL = new HabilidadAlfil<>();

    public Alfil(Color color) {
        super("Alfil", "A", HABILIDAD_ALFIL, color, Tipo.BIOLOGICA, Tipo.TRANSPORTABLE);
    }

    @Override
    protected ActionResult canMover(Tablero tablero, Point inicio, Point final_) {
        if (tablero.getEscaque(final_).hasPieza()) {
            return ActionResult.FAIL;
        }

        if (seHaMovidoEsteTurno()) {
            return ActionResult.FAIL;
        }

        int deltaX = final_.x - inicio.x;
        int deltaY = final_.y - inicio.y;

        if (Math.abs(deltaY) != Math.abs(deltaX)) {
            return ActionResult.FAIL;
        }

        int signoX = deltaX > 0 ? 1 : -1;
        int signoY = deltaY > 0 ? 1 : -1;

        for (int escaque = 1; escaque < Math.abs(deltaX); escaque++) {
            if (tablero.getEscaque(inicio.x + escaque * signoX,
                    inicio.y + escaque * signoY).hasPieza()) {
                return ActionResult.FAIL;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    protected ActionResult canComer(Tablero tablero, Point inicio, Point final_) {
        if (!tablero.getEscaque(final_).hasPieza()) {
            return ActionResult.FAIL;
        }

        if (tablero.getEscaque(final_).getPieza().getColor().equals(
                this.getColor())) {
            return ActionResult.FAIL;
        }

        if (seHaMovidoEsteTurno()) {
            return ActionResult.FAIL;
        }

        int deltaX = final_.x - inicio.x;
        int deltaY = final_.y - inicio.y;

        if (Math.abs(deltaY) != Math.abs(deltaX)) {
            return ActionResult.FAIL;
        }

        int signoX = deltaX > 0 ? 1 : -1;
        int signoY = deltaY > 0 ? 1 : -1;

        for (int escaque = 1; escaque < Math.abs(deltaX); escaque++) {
            if (tablero.getEscaque(inicio.x + escaque * signoX,
                    inicio.y + escaque * signoY).hasPieza()) {
                return ActionResult.FAIL;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public List<Par<Point, Accion>> allAcciones(Tablero tablero, Point seleccionado) {
        //myc viene de mover y comer
        List<Par<Point, Accion>> myc = super.allAcciones(tablero, seleccionado);
        if (this.getHabilidad().canUsar(tablero, this, seleccionado, seleccionado, "N").isPositive()) {
            myc.add(new Par<>(new Point(seleccionado.x, seleccionado.y + 1), Accion.HABILIDAD));
        }
        if (this.getHabilidad().canUsar(tablero, this, seleccionado, seleccionado, "E").isPositive()) {
            myc.add(new Par<>(new Point(seleccionado.x + 1, seleccionado.y), Accion.HABILIDAD));
        }
        if (this.getHabilidad().canUsar(tablero, this, seleccionado, seleccionado, "S").isPositive()) {
            myc.add(new Par<>(new Point(seleccionado.x, seleccionado.y - 1), Accion.HABILIDAD));
        }
        if (this.getHabilidad().canUsar(tablero, this, seleccionado, seleccionado, "W").isPositive()) {
            myc.add(new Par<>(new Point(seleccionado.x - 1, seleccionado.y), Accion.HABILIDAD));
        }
        return myc;
    }

    public static class HabilidadAlfil<P extends Pieza> extends Habilidad<P> {
        public HabilidadAlfil() {
            super("Cambio de Color",
                    "El alfil cambia de color, pudiendo moverse una casilla en cualquiera de las 4 direcciones cardinales.",
                    2,
                    0,
                    "La dirección cardinal hacia dónde se mueve el alfil (NESW).");
        }

        @Override
        public ActionResult canUsar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            if (pieza.getCdActual() > 0) {
                return ActionResult.FAIL;
            }

            if (pieza.seHaMovidoEsteTurno()) {
                return ActionResult.FAIL;
            }

            if (informacionExtra == null) {
                return ActionResult.FAIL;
            }

            boolean verificacion;

            switch (informacionExtra) {
                case "N":
                    verificacion = !tablero.getEscaque(inicio.x, inicio.y + 1).hasPieza();
                    break;
                case "E":
                    verificacion = !tablero.getEscaque(inicio.x + 1, inicio.y).hasPieza();
                    break;
                case "S":
                    verificacion = !tablero.getEscaque(inicio.x, inicio.y - 1).hasPieza();
                    break;
                case "W":
                    verificacion = !tablero.getEscaque(inicio.x - 1, inicio.y).hasPieza();
                    break;
                default:
                    return ActionResult.FAIL;
            }
            return verificacion ? ActionResult.PASS : ActionResult.FAIL;
        }

        @Override
        public void usar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            switch (informacionExtra) {
                case "N":
                    tablero.getEscaque(inicio.x, inicio.y + 1).setPieza(pieza);
                    tablero.getEscaque(inicio).quitarPieza();
                    break;
                case "E":
                    tablero.getEscaque(inicio.x + 1, inicio.y).setPieza(pieza);
                    tablero.getEscaque(inicio).quitarPieza();
                    break;
                case "S":
                    tablero.getEscaque(inicio.x, inicio.y - 1).setPieza(pieza);
                    tablero.getEscaque(inicio).quitarPieza();
                    break;
                case "W":
                    tablero.getEscaque(inicio.x - 1, inicio.y).setPieza(pieza);
                    tablero.getEscaque(inicio).quitarPieza();
                    break;
            }
            //TODO: cambiar cd
        }
    }
}

package com.carlettos.game.tablero.pieza.clasica;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import java.awt.Point;

public class Rey extends PiezaClasica {

    protected boolean seHaTeletransportado;
    public static final Habilidad<Rey> HABILIDAD_REY = new HabilidadRey<>();

    public Rey(Color color) {
        super("Rey", "RE", HABILIDAD_REY, color, Tipo.BIOLOGICA, Tipo.INMUNE);
        this.seHaTeletransportado = false;
    }

    @Override
    public ActionResult canMover(Tablero tablero, Point inicio, Point final_) {
        if (tablero.getEscaque(final_).hasPieza()) {
            return ActionResult.FAIL;
        }

        if (seHaMovidoEsteTurno()) {
            return ActionResult.FAIL;
        }

        if (Math.abs(inicio.x - final_.x) > 1) {
            return ActionResult.FAIL;
        }
        if (Math.abs(inicio.y - final_.y) > 1) {
            return ActionResult.FAIL;
        }

        //TODO: que nada se lo pueda comer ¿? y verificar jaques
        return ActionResult.PASS;
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

        if (Math.abs(inicio.x - final_.x) > 1) {
            return ActionResult.FAIL;
        }
        if (Math.abs(inicio.y - final_.y) > 1) {
            return ActionResult.FAIL;
        }

        //TODO: que nada se lo pueda comer ¿? y verificar jaques
        return ActionResult.PASS;
    }

    public static class HabilidadRey<P extends Rey> extends Habilidad<P> {

        public HabilidadRey() {
            super("Teletransportación",
                    "Se teletransporta a cualquier casilla en un rango de 5",
                    0,
                    2,
                    "Es solo de un solo uso y la casilla debe estar vacía.");
        }

        @Override
        public ActionResult canUsar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            if (pieza.seHaTeletransportado) {
                return ActionResult.FAIL;
            }

            String[] info = informacionExtra.split(" ");
            if (info.length != 2) {
                return ActionResult.FAIL;
            }
            try {
                int deltaX = Integer.parseInt(info[0]);
                int deltaY = Integer.parseInt(info[1]);

                if (Math.abs(deltaX) > 5 || Math.abs(deltaY) > 5) {
                    return ActionResult.FAIL;
                }

                if (inicio.x + deltaX >= tablero.columnas
                        || inicio.x + deltaX < 0
                        || inicio.y + deltaY >= tablero.filas
                        || inicio.y + deltaY < 0) {
                    return ActionResult.FAIL;
                }

                if (tablero.getEscaque(inicio.x + deltaX, inicio.y + deltaY).hasPieza()) {
                    return ActionResult.FAIL;
                }

                return ActionResult.PASS;

            } catch (NumberFormatException e) {
                System.out.println(e);
                return ActionResult.FAIL;
            }
        }

        @Override
        public void usar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            pieza.seHaTeletransportado = true;
            int deltaX = Integer.parseInt(informacionExtra.split(" ")[0]);
            int deltaY = Integer.parseInt(informacionExtra.split(" ")[1]);

            tablero.getEscaque(inicio.x + deltaX, inicio.y + deltaY).setPieza(pieza);
            tablero.getEscaque(inicio).quitarPieza();
        }
    }
}

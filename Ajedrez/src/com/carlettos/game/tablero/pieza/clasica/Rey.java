package com.carlettos.game.tablero.pieza.clasica;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.pieza.patron.clasico.PatronRey;

public class Rey extends PiezaSimple<PatronRey> {

    //TODO: que no se muera después de comer o moverse
    protected boolean seHaTeletransportado;
    public static final Habilidad<Rey> HABILIDAD_REY = new HabilidadRey<>();

    public Rey(Color color) {
        super("Rey", "RE", HABILIDAD_REY, color, new PatronRey(){}, Tipo.BIOLOGICA, Tipo.INMUNE, Tipo.HEROICA);
        this.seHaTeletransportado = false;
    }
    
    public static class HabilidadRey<P extends Rey> extends Habilidad<P> {

        public HabilidadRey() {
            super("Teletransportación",
                    "Se teletransporta a cualquier casilla en un rango de 5",
                    0,
                    2,
                    "Debe ser algo de la forma \"dx dy\", con espacio incluido");
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

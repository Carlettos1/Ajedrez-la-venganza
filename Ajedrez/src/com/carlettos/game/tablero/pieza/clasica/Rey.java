package com.carlettos.game.tablero.pieza.clasica;

import com.carlettos.game.tablero.pieza.PiezaSimple;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.habilidad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.AbstractTablero;
import com.carlettos.game.tablero.pieza.patron.clasico.PatronRey;
import com.carlettos.game.tablero.propiedad.habilidad.InfoPoint;
import com.carlettos.game.tablero.propiedad.habilidad.InfoGetter.HabilidadPoint;
import java.util.ArrayList;
import java.util.List;

public class Rey extends PiezaSimple<PatronRey> {

    //TODO: que no se muera después de comer o moverse
    protected boolean seHaTeletransportado;
    public static final Habilidad<Rey, Point, InfoPoint> HABILIDAD_REY = new HabilidadRey<>();

    public Rey(Color color) {
        super("Rey", "RE", HABILIDAD_REY, color, new PatronRey(){}, Tipo.BIOLOGICA, Tipo.INMUNE, Tipo.HEROICA);
        this.seHaTeletransportado = false;
    }
    
    public static class HabilidadRey<P extends Rey> extends Habilidad<P, Point, InfoPoint> implements HabilidadPoint {

        public HabilidadRey() {
            super("Teletransportación",
                    "Se teletransporta a cualquier casilla en un rango de 5",
                    0,
                    2,
                    "Debe ser algo de la forma \"dx dy\", con espacio incluido");
        }

        @Override
        public ActionResult canUsar(AbstractTablero tablero, P pieza, Point inicio, InfoPoint info) {
            if (pieza.seHaTeletransportado) {
                return ActionResult.FAIL;
            }

            return ActionResult.fromBoolean(info.getValor().getDistanceTo(inicio) <= 5);
        }

        @Override
        public void usar(AbstractTablero tablero, P pieza, Point inicio, InfoPoint info) {
            pieza.seHaTeletransportado = true;
            tablero.getEscaque(info.getValor()).setPieza(pieza);
            tablero.getEscaque(inicio).quitarPieza();
        }

        @Override
        public Point[] getAllValoresPosibles(AbstractTablero tablero, Point inicio) {
            List<Point> valores = new ArrayList<>();
            for (int x = 0; x < tablero.columnas; x++) {
                for (int y = 0; y < tablero.filas; y++) {
                    if(new Point(x, y).getDistanceTo(inicio) <= 5 && !tablero.getEscaque(x, y).hasPieza()){
                        valores.add(new Point(x, y));
                    }
                }
            }
            return valores.toArray(Point[]::new);
        }
    }
}

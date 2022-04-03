package com.carlettos.game.tablero.pieza.nueva;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;

import com.carlettos.game.tablero.pieza.patron.accion.IMover;
import com.carlettos.game.tablero.pieza.patron.accion.IComer;
import com.carlettos.game.tablero.pieza.patron.clasico.PatronRey;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronMoverHechicero;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;

/**
 *
 * @author Carlettos
 */
public class Nave extends Pieza implements IMover<PatronMoverHechicero>, IComer<PatronRey> {
    public static final Habilidad<Pieza> HABILIDAD_NAVE = new HabilidadNave<>();
    protected final PatronMoverHechicero patronMover;
    protected final PatronRey patronComer;

    public Nave(Color color) {
        super("Nave", "N", HABILIDAD_NAVE, color, Tipo.ESTRUCTURA);
        this.patronMover = new PatronMoverHechicero() {};
        this.patronComer = new PatronRey() {};
    }

    @Override
    public ActionResult can(Accion accion, Tablero tablero, Point inicio, Point final_) {
        return switch(accion){
            case MOVER -> this.canMover(tablero, inicio, final_, patronMover);
            case COMER -> this.canComer(tablero, inicio, final_, patronComer);
            default -> ActionResult.FAIL;
        };
    }

    public static class HabilidadNave<P extends Pieza> extends Habilidad<P>{

        public HabilidadNave() {
            super("Ataque en area", 
                    "Ataca a las 6 casillas adyacentes",
                    12, 0, 
                    "ninguno");
        }

        @Override
        public ActionResult canUsar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            return ActionResult.PASS;
        }

        @Override
        public void usar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            Point[] puntos = new Point[]{inicio.add(1, 1), inicio.add(1, 0), inicio.add(1, -1),
                                       inicio.add(-1, 1), inicio.add(-1, 0), inicio.add(-1, -1)};
            for (Point punto : puntos) {
                try {
                    tablero.quitarPieza(punto);
                } catch (Exception e) {
                }
            }
        }
    }
}

package com.carlettos.game.tablero.pieza.nueva;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.AbstractTablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.pieza.patron.accion.IMover;
import com.carlettos.game.tablero.pieza.patron.accion.IComer;
import com.carlettos.game.tablero.pieza.patron.clasico.PatronRey;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.habilidad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronHechiceroMover;
import com.carlettos.game.tablero.propiedad.habilidad.InfoNinguna;
import com.carlettos.game.tablero.propiedad.habilidad.InfoGetter.HabilidadSinInfo;

/**
 *
 * @author Carlettos
 */
public class Nave extends Pieza implements IMover<PatronHechiceroMover>, IComer<PatronRey> {
    public static final Habilidad<Pieza, String, InfoNinguna> HABILIDAD_NAVE = new HabilidadNave<>();
    protected final PatronHechiceroMover patronMover;
    protected final PatronRey patronComer;

    public Nave(Color color) {
        super("Nave", "N", HABILIDAD_NAVE, color, Tipo.ESTRUCTURA);
        this.patronMover = new PatronHechiceroMover() {};
        this.patronComer = new PatronRey() {};
    }

    @Override
    public ActionResult can(Accion accion, AbstractTablero tablero, Point inicio, Point final_) {
        return switch(accion){
            case MOVER -> this.canMover(tablero, inicio, final_, patronMover);
            case COMER -> this.canComer(tablero, inicio, final_, patronComer);
            default -> ActionResult.FAIL;
        };
    }

    public static class HabilidadNave<P extends Pieza> extends Habilidad<P, String, InfoNinguna> implements HabilidadSinInfo{

        public HabilidadNave() {
            super("Ataque en area", 
                    "Ataca a las 6 casillas adyacentes",
                    12, 0, 
                    "ninguno");
        }

        @Override
        public ActionResult canUsar(AbstractTablero tablero, P pieza, Point inicio, InfoNinguna info) {
            return ActionResult.fromBoolean(this.commonCanUsar(tablero, pieza));
        }

        @Override
        public void usar(AbstractTablero tablero, P pieza, Point inicio, InfoNinguna info) {
            Point[] puntos = new Point[]{inicio.add(1, 1), inicio.add(1, 0), inicio.add(1, -1),
                                       inicio.add(-1, 1), inicio.add(-1, 0), inicio.add(-1, -1)};
            for (Point punto : puntos) { //TODO: quitar try
                try {
                    tablero.quitarPieza(punto);
                } catch (Exception e) {
                }
            }
            this.commonUsar(tablero, pieza);
        }
    }
}

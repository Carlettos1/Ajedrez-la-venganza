package com.carlettos.game.tablero.pieza.nueva;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Direction;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.AbstractTablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.pieza.patron.accion.IMover;
import com.carlettos.game.tablero.pieza.patron.accion.IComer;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronHechiceroMover;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronSanguijuelaComer;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.habilidad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import com.carlettos.game.tablero.propiedad.habilidad.InfoNESW;
import com.carlettos.game.tablero.propiedad.habilidad.InfoGetter.HabilidadNESW;
import java.util.function.Function;

/**
 *
 * @author Carlettos
 */
public class Constructor extends Pieza implements IMover<PatronHechiceroMover>, IComer<PatronSanguijuelaComer> {
    public static final Habilidad<Constructor, Direction, InfoNESW> HABILIDAD_CONSTRUCTOR = new HabilidadConstructor<>();
    protected final PatronHechiceroMover patronMover;
    protected final PatronSanguijuelaComer patronComer;

    public Constructor(Color color) {
        super("Constructor", "CO", HABILIDAD_CONSTRUCTOR, color, Tipo.BIOLOGICA, Tipo.TRANSPORTABLE);
        this.patronMover = new PatronHechiceroMover() {};
        this.patronComer = new PatronSanguijuelaComer() {};
    }

    @Override
    public ActionResult can(Accion accion, AbstractTablero tablero, Point inicio, Point final_) {
        return switch(accion){
            case MOVER -> this.canMover(tablero, inicio, final_, patronMover);
            case COMER -> this.canComer(tablero, inicio, final_, patronComer);
            default -> ActionResult.FAIL;
        };
    }
    
    public static class HabilidadConstructor<P extends Pieza> extends Habilidad<P, Direction, InfoNESW> implements HabilidadNESW{
        protected final Function<Color, Muro> creator = Muro::new;

        public HabilidadConstructor() {
            super("Construir Barrera", 
                    "Construye 3 muros en la dirección indicada. No reemplaza piezas.", 
                    10, 
                    0, 
                    "Dirección (NESW).");
        }

        @Override
        public ActionResult canUsar(AbstractTablero tablero, P pieza, Point inicio, InfoNESW info) {
            return ActionResult.fromBoolean(this.commonCanUsar(tablero, pieza));
        }

        @Override
        public void usar(AbstractTablero tablero, P pieza, Point inicio, InfoNESW info) {
            Point p1 = new Point(-1, -1);
            Point p2 = new Point(-1, -1);
            Point p3 = new Point(-1, -1);
            
            switch(info.getValor()){
                case N -> {
                    p1 = inicio.add(1, 1);
                    p2 = inicio.add(0, 1);
                    p3 = inicio.add(-1, 1);
                }
                case S -> {
                    p1 = inicio.add(1, -1);
                    p2 = inicio.add(0, -1);
                    p3 = inicio.add(-1, -1);
                }
                case E -> {
                    p1 = inicio.add(1, 1);
                    p2 = inicio.add(1, 0);
                    p3 = inicio.add(1, -1);
                }
                case W -> {
                    p1 = inicio.add(-1, 1);
                    p2 = inicio.add(-1, 0);
                    p3 = inicio.add(-1, -1);
                }
            }
            if(!tablero.isOutOfBorder(p1)){
                tablero.getEscaque(p1).setPiezaIfEmpty(creator.apply(pieza.getColor()));
            }
            if(!tablero.isOutOfBorder(p2)){
                tablero.getEscaque(p2).setPiezaIfEmpty(creator.apply(pieza.getColor()));
            }
            if(!tablero.isOutOfBorder(p3)){
                tablero.getEscaque(p3).setPiezaIfEmpty(creator.apply(pieza.getColor()));
            }
            this.commonUsar(tablero, pieza);
        }
    }
}

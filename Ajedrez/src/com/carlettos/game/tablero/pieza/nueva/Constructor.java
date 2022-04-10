package com.carlettos.game.tablero.pieza.nueva;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;

import com.carlettos.game.tablero.pieza.patron.accion.IMover;
import com.carlettos.game.tablero.pieza.patron.accion.IComer;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronHechiceroMover;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronSanguijuelaComer;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.habilidad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import java.util.function.Function;

/**
 *
 * @author Carlettos
 */
public class Constructor extends Pieza implements IMover<PatronHechiceroMover>, IComer<PatronSanguijuelaComer> {
    public static final Habilidad<Constructor> HABILIDAD_CONSTRUCTOR = new HabilidadConstructor<>();
    protected final PatronHechiceroMover patronMover;
    protected final PatronSanguijuelaComer patronComer;

    public Constructor(Color color) {
        super("Constructor", "CO", HABILIDAD_CONSTRUCTOR, color, Tipo.BIOLOGICA, Tipo.TRANSPORTABLE);
        this.patronMover = new PatronHechiceroMover() {};
        this.patronComer = new PatronSanguijuelaComer() {};
    }

    @Override
    public ActionResult can(Accion accion, Tablero tablero, Point inicio, Point final_) {
        return switch(accion){
            case MOVER -> this.canMover(tablero, inicio, final_, patronMover);
            case COMER -> this.canComer(tablero, inicio, final_, patronComer);
            default -> ActionResult.FAIL;
        };
    }
    
    public static class HabilidadConstructor<P extends Pieza> extends Habilidad<P>{
        protected final Function<Color, Muro> creator = Muro::new;

        public HabilidadConstructor() {
            super("Construir Barrera", 
                    "Construye 3 muros en la dirección indicada. No reemplaza piezas.", 
                    10, 
                    0, 
                    "Dirección (NESW).");
        }

        @Override
        public ActionResult canUsar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            return switch(informacionExtra){
                case "N", "S", "W", "E" -> ActionResult.PASS;
                default -> ActionResult.FAIL;
            };
        }

        @Override
        public void usar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            switch(informacionExtra){
                case "N" -> {
                    tablero.getEscaque(inicio.add(1, 1)).setPiezaIfEmpty(creator.apply(pieza.getColor()));
                    tablero.getEscaque(inicio.add(0, 1)).setPiezaIfEmpty(creator.apply(pieza.getColor()));
                    tablero.getEscaque(inicio.add(-1, 1)).setPiezaIfEmpty(creator.apply(pieza.getColor()));}
                case "S" -> {
                    tablero.getEscaque(inicio.add(1, -1)).setPiezaIfEmpty(creator.apply(pieza.getColor()));
                    tablero.getEscaque(inicio.add(0, -1)).setPiezaIfEmpty(creator.apply(pieza.getColor()));
                    tablero.getEscaque(inicio.add(-1, -1)).setPiezaIfEmpty(creator.apply(pieza.getColor()));}
                case "E" -> {
                    tablero.getEscaque(inicio.add(1, 1)).setPiezaIfEmpty(creator.apply(pieza.getColor()));
                    tablero.getEscaque(inicio.add(1, 0)).setPiezaIfEmpty(creator.apply(pieza.getColor()));
                    tablero.getEscaque(inicio.add(1, -1)).setPiezaIfEmpty(creator.apply(pieza.getColor()));}
                case "W" -> {
                    tablero.getEscaque(inicio.add(-1, 1)).setPiezaIfEmpty(creator.apply(pieza.getColor()));
                    tablero.getEscaque(inicio.add(-1, 0)).setPiezaIfEmpty(creator.apply(pieza.getColor()));
                    tablero.getEscaque(inicio.add(-1, -1)).setPiezaIfEmpty(creator.apply(pieza.getColor()));}
            }
            //TODO: cambiar cd y mana
        }
    }
}

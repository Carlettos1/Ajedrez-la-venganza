package com.carlettos.game.tablero.pieza.nueva;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Evento;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.pieza.patron.Patron;
import com.carlettos.game.tablero.pieza.patron.accion.IComer;
import com.carlettos.game.tablero.pieza.patron.accion.IMover;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronCañonAtacar;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronEstructuraMover;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronHechiceroMover;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.habilidad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;

/**
 *
 * @author Carlettos
 */
public class TorreTesla extends Pieza implements IMover<PatronHechiceroMover>, IComer<PatronEstructuraMover>{
    public final static Habilidad<TorreTesla> HABILIDAD_TORRE_TESLA = new HabilidadTorreTesla<>();
    protected final PatronHechiceroMover patronMover;
    protected final PatronEstructuraMover patronComer;

    public TorreTesla(Color color) {
        super("Torre Tesla", "TT", HABILIDAD_TORRE_TESLA, color, Tipo.ESTRUCTURA);
        this.patronMover = new PatronHechiceroMover() {};
        this.patronComer = new PatronEstructuraMover() {};
    }

    @Override
    public ActionResult can(Accion accion, Tablero tablero, Point inicio, Point final_) {
        return switch(accion){
            case MOVER -> this.canMover(tablero, inicio, final_, patronMover);
            case COMER -> this.canComer(tablero, inicio, final_, patronComer);
            default -> ActionResult.FAIL;
        };
    }
    
    public static class HabilidadTorreTesla<P extends Pieza> extends Habilidad<P>{
        protected final Patron patronHabilidad = new PatronCañonAtacar() {};
        
        public HabilidadTorreTesla() {
            super("PEM", 
                    "Emite un PEM que desactiva todas las estructuras", 
                    20, 
                    1, 
                    "ninguno");
        }

        @Override
        public ActionResult canUsar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            return ActionResult.PASS; //todo: cambiar mana y cd
        }

        @Override
        public void usar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            //TODO: usar el reloj para dar 1 turno de tiempo
            tablero.getReloj().addEventos(Evento.Builder.start(tablero).with(2, this.getNombre(), inicio)
                    .build((turnos1, nombre1, punto1, tablero1) -> {
                        tablero1.getEscaquesMatchPatron(patronHabilidad, inicio).stream()
                                .filter(escaque -> escaque.getPieza().isTipo(Tipo.ESTRUCTURA))
                                .forEach(escaque -> escaque.getPieza().cambiarCD(10)); //TODO: que desactive de verdad
                    }));
        }
    }
}

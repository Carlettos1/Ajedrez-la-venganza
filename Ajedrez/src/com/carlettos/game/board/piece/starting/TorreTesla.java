package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Evento;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.pattern.Patron;
import com.carlettos.game.board.piece.pattern.action.IComer;
import com.carlettos.game.board.piece.pattern.action.IMover;
import com.carlettos.game.board.piece.pattern.starting.PatronCañonAtacar;
import com.carlettos.game.board.piece.pattern.starting.PatronEstructuraMover;
import com.carlettos.game.board.piece.pattern.starting.PatronHechiceroMover;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.board.property.Tipo;
import com.carlettos.game.board.property.ability.InfoNinguna;
import com.carlettos.game.board.property.ability.InfoGetter.HabilidadSinInfo;

/**
 *
 * @author Carlettos
 */
public class TorreTesla extends Piece implements IMover<PatronHechiceroMover>, IComer<PatronEstructuraMover> {
    public final static Ability<TorreTesla, String, InfoNinguna> HABILIDAD_TORRE_TESLA = new HabilidadTorreTesla<>();
    protected final PatronHechiceroMover patronMover;
    protected final PatronEstructuraMover patronComer;

    public TorreTesla(Color color) {
        super("Torre Tesla", "TT", HABILIDAD_TORRE_TESLA, color, Tipo.ESTRUCTURA);
        this.patronMover = new PatronHechiceroMover() {};
        this.patronComer = new PatronEstructuraMover() {};
    }

    @Override
    public ActionResult can(Accion accion, AbstractBoard tablero, Point inicio, Point final_) {
        return switch(accion){
            case MOVER -> this.canMover(tablero, inicio, final_, patronMover);
            case COMER -> this.canComer(tablero, inicio, final_, patronComer);
            default -> ActionResult.FAIL;
        };
    }
    
    public static class HabilidadTorreTesla<P extends Piece> extends Ability<P, String, InfoNinguna> implements HabilidadSinInfo{
        protected final Patron patronHabilidad = new PatronCañonAtacar() {};
        
        public HabilidadTorreTesla() {
            super("PEM", 
                    "Emite un PEM que desactiva todas las estructuras", 
                    20, 
                    1, 
                    "ninguno");
        }

        @Override
        public ActionResult canUsar(AbstractBoard tablero, P pieza, Point inicio, InfoNinguna info) {
            return ActionResult.fromBoolean(this.commonCanUsar(tablero, pieza) && tablero instanceof Board);
        }

        @Override
        public void usar(AbstractBoard tablero, P pieza, Point inicio, InfoNinguna info) {
            if(tablero instanceof Board t){
                t.getClock().addEventos(Evento.Builder.start(t).with(2, this.getNombre(), inicio)
                        .build((turnos1, nombre1, punto1, tablero1) -> {
                            tablero1.getEscaquesMatchPatron(patronHabilidad, inicio).stream()
                                    .filter(escaque -> escaque.getPieza().isTipo(Tipo.ESTRUCTURA))
                                    .forEach(escaque -> escaque.getPieza().cambiarCD(10)); //TODO: que desactive de verdad
                        }));
            } else {
                throw new IllegalArgumentException("Tablero no es instanceof Tablero");
            }
            this.commonUsar(tablero, pieza);
        }
    }
}

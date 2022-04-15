package com.carlettos.game.board.piece.classic;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Par;
import com.carlettos.game.board.piece.AbstractPeon;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Habilidad;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Pieza;
import com.carlettos.game.board.piece.starting.Ariete;
import com.carlettos.game.board.piece.starting.Arquero;
import com.carlettos.game.board.piece.starting.Ballesta;
import com.carlettos.game.board.piece.starting.Brujo;
import com.carlettos.game.board.piece.starting.Catapulta;
import com.carlettos.game.board.piece.starting.Cañon;
import com.carlettos.game.board.piece.starting.Constructor;
import com.carlettos.game.board.piece.starting.Defensor;
import com.carlettos.game.board.piece.starting.Nave;
import com.carlettos.game.board.piece.starting.PeonLoco;
import com.carlettos.game.board.piece.starting.SuperPeon;
import com.carlettos.game.board.piece.starting.TorreTesla;
import com.carlettos.game.board.piece.pattern.classic.PatronPeonComer;
import com.carlettos.game.board.piece.pattern.classic.PatronPeonMover;
import com.carlettos.game.board.property.ability.InfoPieza;
import com.carlettos.game.board.property.ability.InfoGetter.HabilidadPieza;
import java.util.List;

/**
 * Pieza fundamental del ajedrez, solo que levemente transformada. Se mueve 2
 * escaques hacia al frente y come, en su escaque más próximo, en sus dos
 * diagonales frontales. Su habilidad es la de coronar, no come al paso.
 *
 * @author Carlos
 */
public class Peon extends AbstractPeon<PatronPeonMover, PatronPeonComer> {

    /**
     * la habilidad default del peón, de utilidad por si necesita usarse en
     * otras piezas.
     */
    public static final Habilidad<Peon, Pieza, InfoPieza> HABILIDAD_PEON = new HabilidadPeon<>();

    public Peon(Color color) {
        super(()->color, ()->color, "Peón", "P", HABILIDAD_PEON, color);
    }

    public static class HabilidadPeon<P extends Pieza> extends Habilidad<P, Pieza, InfoPieza> implements HabilidadPieza {

        public HabilidadPeon() {
            super("Coronar",
                    "Al estar en la última fila, puede transformarse en cualquier pieza de las que se permita.",
                    0,
                    0,
                    "El nombre de la pieza a transformar. Ejemplo: Dama.");
        }

        @Override
        public ActionResult canUsar(AbstractBoard tablero, P pieza, Point inicio, InfoPieza info) {
            if(!this.commonCanUsar(tablero, pieza)){
                return ActionResult.FAIL;
            }
            if (pieza.getColor().equals(Color.BLANCO)) {
                if (inicio.y + 1 == tablero.filas) {
                    return ActionResult.PASS;
                } else {
                    return ActionResult.FAIL;
                }
            } else if (pieza.getColor().equals(Color.NEGRO)) {
                if (inicio.y == 0) {
                    return ActionResult.PASS;
                } else {
                    return ActionResult.FAIL;
                }
            }
            //todo: poder coronar con cualquier color
            System.err.println("INTENTANDO CORONAR CON OTRO COLOR");
            return ActionResult.FAIL;
        }

        @Override
        public void usar(AbstractBoard tablero, P pieza, Point inicio, InfoPieza info) {
            Pieza p = info.getValor();
            p.setColor(pieza.getColor());
            tablero.getEscaque(inicio).setPieza(p);
            p.setSeHaMovidoEsteTurno(true);
        }

        @Override
        public Pieza[] getAllValoresPosibles(AbstractBoard tablero, Point inicio) {
            return new Pieza[]{new Bishop(Color.DEFAULT),
                new Caballo(Color.DEFAULT),
                new Reina(Color.DEFAULT),
                new Torre(Color.DEFAULT),
                new Ariete(Color.DEFAULT),
                new Arquero(Color.DEFAULT),
                new Ballesta(Color.DEFAULT),
                new Brujo(Color.DEFAULT),
                new Catapulta(Color.DEFAULT),
                new Cañon(Color.DEFAULT),
                new Constructor(Color.DEFAULT),
                new Defensor(Color.DEFAULT),
                new Nave(Color.DEFAULT),
                new PeonLoco(Color.DEFAULT),
                new SuperPeon(Color.DEFAULT),
                new TorreTesla(Color.DEFAULT)};
        }
    }
}

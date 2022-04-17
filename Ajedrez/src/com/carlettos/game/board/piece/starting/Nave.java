package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.pattern.action.IMover;
import com.carlettos.game.board.piece.pattern.action.IComer;
import com.carlettos.game.board.piece.pattern.classic.PatronRey;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.board.property.Tipo;
import com.carlettos.game.board.piece.pattern.starting.PatronHechiceroMover;
import com.carlettos.game.board.property.ability.InfoNinguna;
import com.carlettos.game.board.property.ability.InfoGetter.HabilidadSinInfo;

/**
 *
 * @author Carlettos
 */
public class Nave extends Piece implements IMover<PatronHechiceroMover>, IComer<PatronRey> {
    public static final Ability<Piece, String, InfoNinguna> HABILIDAD_NAVE = new HabilidadNave<>();
    protected final PatronHechiceroMover patronMover;
    protected final PatronRey patronComer;

    public Nave(Color color) {
        super("Nave", "N", HABILIDAD_NAVE, color, Tipo.ESTRUCTURA);
        this.patronMover = new PatronHechiceroMover() {};
        this.patronComer = new PatronRey() {};
    }

    @Override
    public ActionResult can(Accion accion, AbstractBoard tablero, Point inicio, Point final_) {
        return switch(accion){
            case MOVER -> this.canMover(tablero, inicio, final_, patronMover);
            case COMER -> this.canComer(tablero, inicio, final_, patronComer);
            default -> ActionResult.FAIL;
        };
    }

    public static class HabilidadNave<P extends Piece> extends Ability<P, String, InfoNinguna> implements HabilidadSinInfo{

        public HabilidadNave() {
            super("Ataque en area", 
                    "Ataca a las 6 casillas adyacentes",
                    12, 0, 
                    "ninguno");
        }

        @Override
        public ActionResult canUsar(AbstractBoard tablero, P pieza, Point inicio, InfoNinguna info) {
            return ActionResult.fromBoolean(this.commonCanUsar(tablero, pieza));
        }

        @Override
        public void usar(AbstractBoard tablero, P pieza, Point inicio, InfoNinguna info) {
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

package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.Vacia;
import com.carlettos.game.board.piece.pattern.action.IMover;
import com.carlettos.game.board.piece.pattern.action.IComer;
import com.carlettos.game.board.piece.pattern.classic.PatronReina;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.Tipo;

/**
 *
 * @author Carlettos
 */
public class Paladin extends Piece implements IMover<PatronReina>, IComer<PatronReina> {
    protected final PatronReina patron;

    public Paladin(Color color) { //TODO: Habilidad
        super("Paladin", "PA", Vacia.NO_HABILIDAD, color, Tipo.HEROICA, Tipo.INMUNE);
        patron = new PatronReina(){};
    }

    @Override
    public ActionResult can(Accion accion, AbstractBoard tablero, Point inicio, Point final_) {
        return switch(accion){
            case MOVER -> this.canMover(tablero, inicio, final_, patron);
            case COMER -> this.canComer(tablero, inicio, final_, patron);
            default -> ActionResult.FAIL;
        };
    }
}

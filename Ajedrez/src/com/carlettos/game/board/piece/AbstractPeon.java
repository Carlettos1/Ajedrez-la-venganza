package com.carlettos.game.board.piece;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.PatronPeon;
import com.carlettos.game.board.piece.pattern.action.IMover;
import com.carlettos.game.board.piece.pattern.action.IComer;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.board.property.Tipo;

/**
 *
 * @author Carlettos <M extends PatronPeon, C extends PatronPeon> 
 */
public abstract class AbstractPeon<M extends PatronPeon, C extends PatronPeon> extends Piece implements IMover<M>, IComer<C> {
    protected final C patronComer;
    protected final M patronMover;

    public AbstractPeon(C patronComer, M patronMover, String nombre, String abreviacion, Ability habilidad, Color color) {
        super(nombre, abreviacion, habilidad, color, Tipo.BIOLOGICA, Tipo.TRANSPORTABLE);
        this.patronComer = patronComer;
        this.patronMover = patronMover;
    }

    @Override
    public ActionResult can(Accion accion, AbstractBoard tablero, Point inicio, Point final_) {
        return switch(accion){
            case MOVER -> this.canMover(tablero, inicio, final_, patronMover);
            case COMER -> this.canComer(tablero, inicio, final_, patronComer);
            default -> ActionResult.FAIL;
        };
    }
}

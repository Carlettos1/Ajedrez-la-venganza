package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.Vacia;
import com.carlettos.game.board.piece.pattern.action.IAtacar;
import com.carlettos.game.board.piece.pattern.action.IMover;
import com.carlettos.game.board.piece.pattern.starting.PatronCañonAtacar;
import com.carlettos.game.board.piece.pattern.starting.PatronEstructuraMover;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.Tipo;
import com.carlettos.game.board.property.ability.InfoGetter.HabilidadSinInfo;

/**
 *
 * @author Carlettos
 */
public class Cañon extends Piece implements IAtacar<PatronCañonAtacar>, IMover<PatronEstructuraMover>, HabilidadSinInfo {

    protected final PatronCañonAtacar patronAtacar;
    protected final PatronEstructuraMover patronMover;

    public Cañon(Color color) {
        super("Cañón", "CAÑ", Vacia.NO_HABILIDAD, color, Tipo.ESTRUCTURA);
        patronAtacar = new PatronCañonAtacar() {};
        patronMover = new PatronEstructuraMover() {};
    }

    @Override
    public ActionResult can(Accion accion, AbstractBoard tablero, Point inicio, Point final_) {
        return switch (accion) {
            case ATACAR -> this.canAtacar(tablero, inicio, final_, patronAtacar);
            case MOVER -> this.canMover(tablero, inicio, final_, patronMover);
            default -> ActionResult.FAIL;
        };
    }
}

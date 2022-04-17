package com.carlettos.game.board.piece;

import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.property.ability.InfoNone;
import com.carlettos.game.board.property.ability.InfoGetter.HabilidadSinInfo;

/**
 * Es la pieza que funciona como place-holder, no hace nada y es negra por
 * defecto, la única utilidad que tiene es evitar que un Escaque tenga una
 * referencia null.
 *
 * @author Carlos
 */
public class Empty extends Piece {

    public final static Ability NO_HABILIDAD = new NoHabilidad();

    public Empty() {
        super("Vacía", " ", NO_HABILIDAD, Color.DEFAULT);
    }

    @Override
    public ActionResult can(Action accion, AbstractBoard tablero, Point inicio, Point final_) {
        return ActionResult.FAIL;
    }

    public static class NoHabilidad extends Ability<Piece, String, InfoNone> implements HabilidadSinInfo {

        public NoHabilidad() {
            super("Habilidad nula", "No hace nada", 0, 0, "Ninguno");
        }

        @Override
        public ActionResult canUse(AbstractBoard tablero, Piece pieza, Point inicio, InfoNone info) {
            return ActionResult.FAIL;
        }

        @Override
        public void use(AbstractBoard tablero, Piece pieza, Point inicio, InfoNone info) {
        }
    }
}

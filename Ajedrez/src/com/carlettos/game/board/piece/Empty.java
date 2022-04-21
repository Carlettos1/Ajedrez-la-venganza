package com.carlettos.game.board.piece;

import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.property.ability.Info;
import com.carlettos.game.board.property.ability.info.InfoNone;
import com.carlettos.game.board.property.ability.InfoUse.AbilityNone;

/**
 *
 * @author Carlos
 */
public class Empty extends Piece {

    public final static Ability NO_ABILITY = new NoAbility();

    public Empty() {
        super("Vacía", " ", NO_ABILITY, Color.GRAY);
    }

    @Override
    public ActionResult can(Action action, AbstractBoard board, Point start, Info info) {
        return ActionResult.FAIL;
    }

    public static class NoAbility extends Ability<Piece, String, InfoNone> implements AbilityNone {

        public NoAbility() {
            super("Habilidad nula", "No hace nada", 0, 0, "Ninguno");
        }

        @Override
        public ActionResult canUse(AbstractBoard board, Piece piece, Point start, InfoNone info) {
            return ActionResult.FAIL;
        }

        @Override
        public void use(AbstractBoard board, Piece piece, Point start, InfoNone info) {
        }
    }
}

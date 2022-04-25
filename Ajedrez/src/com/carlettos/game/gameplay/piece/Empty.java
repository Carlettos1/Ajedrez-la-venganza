package com.carlettos.game.gameplay.piece;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.ability.InfoUse.AbilityNone;
import com.carlettos.game.gameplay.ability.info.InfoNone;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlos
 */
public class Empty extends Piece {

    public final static Ability NO_ABILITY = new NoAbility();

    public Empty() {
        super("empty", "non", NO_ABILITY, Color.GRAY);
    }

    @Override
    public ActionResult can(Action action, AbstractBoard board, Point start, Info info) {
        return ActionResult.FAIL;
    }

    public static class NoAbility extends Ability<Piece, String, InfoNone> implements AbilityNone {

        public NoAbility() {
            super("Habilidad nula", "No hace nada", 0, 0);
        }

        @Override
        public boolean commonCanUse(AbstractBoard board, Piece piece) {
            return false;
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

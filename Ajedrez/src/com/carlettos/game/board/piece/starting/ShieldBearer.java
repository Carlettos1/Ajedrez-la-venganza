package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.AbstractPawn;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.pattern.classic.PatternPawnTake;
import com.carlettos.game.board.piece.pattern.classic.PatternPawnMove;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.board.property.ability.info.InfoNone;
import com.carlettos.game.board.property.ability.InfoUse.AbilityNone;

/**
 *
 * @author Carlettos
 */
public class ShieldBearer extends AbstractPawn<PatternPawnMove, PatternPawnTake> {

    public static final Ability<Piece, String, InfoNone> ABILITY_SHIELD_BEARER = new AbilityShieldBearer<>();

    public ShieldBearer(Color color) {
        super(() -> color, () -> color, "shield_bearer", "D", ABILITY_SHIELD_BEARER, color);
    }
    
    public static class AbilityShieldBearer<P extends Piece> extends Ability<P, String, InfoNone> implements AbilityNone {
        public AbilityShieldBearer() {//TODO: repensar la habilidad
            super("Defender",
                    "Defiende de ataques de ballesta",
                    6,
                    0,
                    "ninguno");
        }

        @Override
        public ActionResult canUse(AbstractBoard board, P piece, Point start, InfoNone info) {
            return ActionResult.fromBoolean(this.commonCanUse(board, piece));
        }

        @Override
        public void use(AbstractBoard board, P piece, Point start, InfoNone info) {
            System.out.println("USAR HABILIDAD DEFENSOR");
        }
    }
}

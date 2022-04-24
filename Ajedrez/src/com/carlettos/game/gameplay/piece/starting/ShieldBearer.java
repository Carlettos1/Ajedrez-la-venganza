package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.InfoUse.AbilityNone;
import com.carlettos.game.gameplay.ability.info.InfoNone;
import com.carlettos.game.gameplay.pattern.classic.PatternPawnMove;
import com.carlettos.game.gameplay.pattern.classic.PatternPawnTake;
import com.carlettos.game.gameplay.piece.AbstractPawn;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;

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

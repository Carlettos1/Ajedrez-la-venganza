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
import com.carlettos.game.board.property.ability.InfoNone;
import com.carlettos.game.board.property.ability.InfoGetter.HabilidadSinInfo;

/**
 *
 * @author Carlettos
 */
public class ShieldBearer extends AbstractPawn<PatternPawnMove, PatternPawnTake> {

    public static final Ability<Piece, String, InfoNone> HABILIDAD_DEFENSOR = new HabilidadDefensor<>();

    public ShieldBearer(Color color) {
        super(() -> color, () -> color, "Defensor", "D", HABILIDAD_DEFENSOR, color);
    }
    
    public static class HabilidadDefensor<P extends Piece> extends Ability<P, String, InfoNone> implements HabilidadSinInfo {
        public HabilidadDefensor() {//TODO: repensar la habilidad
            super("Defender",
                    "Defiende de ataques de ballesta",
                    6,
                    0,
                    "ninguno");
        }

        @Override
        public ActionResult canUsar(AbstractBoard tablero, P pieza, Point inicio, InfoNone info) {
            return ActionResult.fromBoolean(this.commonCanUsar(tablero, pieza));
        }

        @Override
        public void usar(AbstractBoard tablero, P pieza, Point inicio, InfoNone info) {
            System.out.println("USAR HABILIDAD DEFENSOR");
        }
    }
}

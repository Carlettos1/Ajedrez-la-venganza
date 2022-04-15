package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.AbstractPeon;
import com.carlettos.game.board.piece.Pieza;
import com.carlettos.game.board.piece.pattern.classic.PatronPeonComer;
import com.carlettos.game.board.piece.pattern.classic.PatronPeonMover;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Habilidad;
import com.carlettos.game.board.property.ability.InfoNinguna;
import com.carlettos.game.board.property.ability.InfoGetter.HabilidadSinInfo;

/**
 *
 * @author Carlettos
 */
public class Defensor extends AbstractPeon<PatronPeonMover, PatronPeonComer> {

    public static final Habilidad<Pieza, String, InfoNinguna> HABILIDAD_DEFENSOR = new HabilidadDefensor<>();

    public Defensor(Color color) {
        super(() -> color, () -> color, "Defensor", "D", HABILIDAD_DEFENSOR, color);
    }
    
    public static class HabilidadDefensor<P extends Pieza> extends Habilidad<P, String, InfoNinguna> implements HabilidadSinInfo {
        public HabilidadDefensor() {//TODO: repensar la habilidad
            super("Defender",
                    "Defiende de ataques de ballesta",
                    6,
                    0,
                    "ninguno");
        }

        @Override
        public ActionResult canUsar(AbstractBoard tablero, P pieza, Point inicio, InfoNinguna info) {
            return ActionResult.fromBoolean(this.commonCanUsar(tablero, pieza));
        }

        @Override
        public void usar(AbstractBoard tablero, P pieza, Point inicio, InfoNinguna info) {
            System.out.println("USAR HABILIDAD DEFENSOR");
        }
    }
}

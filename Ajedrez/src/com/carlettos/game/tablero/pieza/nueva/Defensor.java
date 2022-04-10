package com.carlettos.game.tablero.pieza.nueva;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.AbstractPeon;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.pieza.patron.clasico.PatronPeonComer;
import com.carlettos.game.tablero.pieza.patron.clasico.PatronPeonMover;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.habilidad.Habilidad;

/**
 *
 * @author Carlettos
 */
public class Defensor extends AbstractPeon<PatronPeonMover, PatronPeonComer> {

    public static final Habilidad<Pieza> HABILIDAD_DEFENSOR = new HabilidadDefensor<>();

    public Defensor(Color color) {
        super(() -> color, () -> color, "Defensor", "D", HABILIDAD_DEFENSOR, color);
    }
    
    public static class HabilidadDefensor<P extends Pieza> extends Habilidad<P>{
        public HabilidadDefensor() {//TODO: repensar la habilidad //aplicar un tipo en area
            super("Defender",
                    "Defiende de ataques de ballesta",
                    6,
                    0,
                    "ninguno");
        }

        @Override
        public ActionResult canUsar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            throw new UnsupportedOperationException("Not supported yet."); //todo: implementar
        }

        @Override
        public void usar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            throw new UnsupportedOperationException("Not supported yet."); //todo: implementar
        }
    }
}

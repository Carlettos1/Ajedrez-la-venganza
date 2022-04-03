package com.carlettos.game.tablero.pieza.nueva;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.pieza.clasica.Peon;
import com.carlettos.game.tablero.pieza.patron.accion.IComer;
import com.carlettos.game.tablero.pieza.patron.accion.IMover;
import com.carlettos.game.tablero.pieza.patron.clasico.PatronPeonComer;
import com.carlettos.game.tablero.pieza.patron.clasico.PatronPeonMover;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;

/**
 *
 * @author Carlettos
 */
public class Defensor extends Pieza implements IComer<PatronPeonComer>, IMover<PatronPeonMover> {

    public static final Habilidad<Pieza> HABILIDAD_DEFENSOR = new HabilidadDefensor<>();
    protected final PatronPeonComer patronComer;
    protected final PatronPeonMover patronMover;

    public Defensor(Color color) {
        super("Defensor", "D", HABILIDAD_DEFENSOR, color, Tipo.BIOLOGICA, Tipo.TRANSPORTABLE);
        patronComer = (PatronPeonComer) () -> color;
        patronMover = (PatronPeonMover) () -> color;
    }

    @Override
    public ActionResult can(Accion accion, Tablero tablero, Point inicio, Point final_) {
        return switch (accion) {
            case MOVER -> this.canMover(tablero, inicio, final_, patronMover);
            case COMER -> this.canComer(tablero, inicio, final_, patronComer);
            default -> ActionResult.FAIL;
        };
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

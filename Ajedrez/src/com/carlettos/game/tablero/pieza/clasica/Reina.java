package com.carlettos.game.tablero.pieza.clasica;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import com.carlettos.game.core.Point;

public class Reina extends PiezaClasica {

    public static final Habilidad<Reina> HABILIDAD_REINA = new HabilidadReina<Reina>();

    public Reina(Color color) {
        super("Reina", "R", HABILIDAD_REINA, color, Tipo.BIOLOGICA);
    }

    @Override
    public ActionResult canMover(Tablero tablero, Point inicio, Point final_) {
        return ActionResult.FAIL; //todo: mover reina
    }

    @Override
    public ActionResult canComer(Tablero tablero, Point inicio, Point final_) {
        return ActionResult.FAIL; //todo: comer reina
    }

    //TODO: habilidad reina
    public static class HabilidadReina<P extends Pieza> extends Habilidad<P> {

        public HabilidadReina() {
            super("Why?",
                    "TODO XD",
                    0,
                    0,
                    "1");
        }

        @Override
        public ActionResult canUsar(Tablero tablero, Pieza pieza, Point inicio, Point final_, String informacionExtra) {
            return ActionResult.FAIL;
        }

        @Override
        public void usar(Tablero tablero, Pieza pieza, Point inicio, Point final_, String informacionExtra) {
        }
    }
}

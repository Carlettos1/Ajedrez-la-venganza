package ajedrez.carlettos.src.pieza.piezas.clasica;

import ajedrez.carlettos.src.pieza.base.Pieza;
import ajedrez.carlettos.src.pieza.propiedad.EnumTipoPieza;
import ajedrez.carlettos.src.pieza.propiedad.Habilidad;
import ajedrez.carlettos.src.tablero.TableroManager;
import ajedrez.carlettos.src.tablero.jugador.Color;
import ajedrez.carlettos.src.util.Par;
import java.awt.Point;

public class Reina extends Pieza {

    public static final Habilidad HABILIDAD_REINA = new Habilidad("Why?",
            "TODO XD",
            0,
            0,
            "1");

    public Reina(Color color) {
        super("Reina", "R", HABILIDAD_REINA, color, EnumTipoPieza.BIOLOGICA);
    }

    @Override
    public boolean canMover(TableroManager tablero, Point inicio, Point final_) {
        return false;
    }

    @Override
    public boolean canComer(TableroManager tablero, Point inicio, Point final_) {
        return false;
    }

    @Override
    public Par<Boolean, String> canUsarHabilidad(TableroManager tablero, Point inicio, Point final_, String informacionExtra) {
        return null;
    }

    @Override
    public void habilidad(TableroManager tablero, Point inicio, Point final_, String informacionExtra) {
        
    }
}

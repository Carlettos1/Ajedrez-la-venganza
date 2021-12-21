package com.carlettos.game.tablero.pieza.clasica;

import com.carlettos.game.core.Par;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import java.awt.Point;

public class Reina extends Pieza {

    public static final Habilidad HABILIDAD_REINA = new Habilidad("Why?",
            "TODO XD",
            0,
            0,
            "1");

    public Reina(Color color) {
        super("Reina", "R", HABILIDAD_REINA, color, Tipo.BIOLOGICA);
    }

    @Override
    public boolean canMover(Tablero tablero, Point inicio, Point final_) {
        return false;
    }

    @Override
    public boolean canComer(Tablero tablero, Point inicio, Point final_) {
        return false;
    }

    @Override
    public Par<Boolean, String> canUsarHabilidad(Tablero tablero, Point inicio, Point final_, String informacionExtra) {
        return null;
    }

    @Override
    public void habilidad(Tablero tablero, Point inicio, Point final_, String informacionExtra) {
        //todo: todo
    }
}

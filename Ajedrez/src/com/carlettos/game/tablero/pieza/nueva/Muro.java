package com.carlettos.game.tablero.pieza.nueva;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Par;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.pieza.Vacia;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import java.util.List;

/**
 * TODO: que se utilize
 * @author Carlettos
 */
public class Muro extends Pieza {
    public Muro(Color color) {
        super("Muro", "MU", Vacia.NO_HABILIDAD, color, Tipo.ESTRUCTURA);
    }

    @Override
    public ActionResult can(Accion accion, Tablero tablero, Point inicio, Point final_) {
        return ActionResult.FAIL;
    }

    @Override
    public List<Par<Point, Accion>> allAcciones(Tablero tablero, Point seleccionado) {
        return List.of();
    }
}

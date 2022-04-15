package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Par;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Pieza;
import com.carlettos.game.board.piece.Vacia;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.Tipo;
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
    public ActionResult can(Accion accion, AbstractBoard tablero, Point inicio, Point final_) {
        return ActionResult.FAIL;
    }

    @Override
    public List<Par<Point, Accion>> allAcciones(AbstractBoard tablero, Point seleccionado) {
        return List.of();
    }
}

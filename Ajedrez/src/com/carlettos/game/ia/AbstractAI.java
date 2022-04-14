package com.carlettos.game.ia;

import com.carlettos.game.tablero.jugador.Jugador;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.visual.TableroVisual;


/**
 *
 * @author Carlettos
 */
public abstract class AbstractAI implements AI {
    private final Tablero tablero;
    private final TableroVisual visual;
    private final Jugador jugador;

    public AbstractAI(TableroVisual visual, Jugador jugador) {
        this.tablero = visual.getTablero();
        this.visual = visual;
        this.jugador = jugador;
    }

    public TableroVisual getVisual() {
        return visual;
    }

    @Override
    public Tablero getTablero() {
        return tablero;
    }

    @Override
    public Jugador getJugador() {
        return jugador;
    }
}

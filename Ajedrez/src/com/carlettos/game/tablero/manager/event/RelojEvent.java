package com.carlettos.game.tablero.manager.event;

import com.carlettos.game.tablero.manager.Reloj;
import com.carlettos.game.tablero.propiedad.Color;
import java.util.EventObject;

/**
 *
 * @author Carlettos
 */
public class RelojEvent extends EventObject{
    private final Color color;
    public RelojEvent(Reloj source) {
        super(source);
        this.color = source.turnoDe().getColor();
    }

    public Color getColor() {
        return color;
    }

    @Override
    public Reloj getSource() {
        return (Reloj) source;
    }
}

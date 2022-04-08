package com.carlettos.game.tablero.pieza.nueva;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.pieza.Vacia;

import com.carlettos.game.tablero.pieza.patron.accion.IMover;
import com.carlettos.game.tablero.pieza.patron.accion.IAtacar;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronArqueroAtacar;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronArqueroMover;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;

/**
 *
 * @author Carlettos
 */
public class Arquero extends Pieza implements IMover<PatronArqueroMover>, IAtacar<PatronArqueroAtacar> {
    protected final PatronArqueroMover patronMover;
    protected final PatronArqueroAtacar patronAtacar;
    
    public Arquero(Color color) {
        super("Arquero", "ARQ", Vacia.NO_HABILIDAD, color, Tipo.BIOLOGICA, Tipo.TRANSPORTABLE);
        patronMover = new PatronArqueroMover() {};
        patronAtacar = new PatronArqueroAtacar() {};
    }

    @Override
    public ActionResult can(Accion accion, Tablero tablero, Point inicio, Point final_) {
        return switch(accion){ //TODO: que el ataque pueda fallar
            case MOVER -> this.canMover(tablero, inicio, final_, patronMover);
            case ATACAR -> this.canAtacar(tablero, inicio, final_, patronAtacar);
            default -> ActionResult.FAIL;
        };
    }
}

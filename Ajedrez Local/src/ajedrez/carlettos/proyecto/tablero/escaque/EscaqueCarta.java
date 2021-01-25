package ajedrez.carlettos.proyecto.tablero.escaque;

import ajedrez.carlettos.proyecto.carta.base.Carta;

/**
 * El escaque del "tablero de cartas", sirve como forma de tener cartas
 * ordenadas y un número máximo de cartas, pero en la práctica, es mejor y más
 * intuitivo usar otras formas de controlas las cartas.
 *
 * @author Carlos
 * @deprecated
 */
@Deprecated()
public class EscaqueCarta {

    private Carta carta;

    public EscaqueCarta(Carta carta) {
        this.carta = carta;
    }

    public Carta getCarta() {
        return carta;
    }

    public void setCarta(Carta carta) {
        this.carta = carta;
    }
}

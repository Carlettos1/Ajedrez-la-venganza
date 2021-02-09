package ajedrez.carlettos.src.tablero.jugador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * En el ajedrez normal hay 2 equipos, un equipo contiene a las blancas, y el
 * otro a las negras. En el ajedrez 2 vs 2 de 4 jugadores de chess.com hay 2
 * equipos y cada uno contiene a 2 colores.
 * <P>
 * Esta clase es la que es responsable de contener a los colores de cada equipo.
 *
 * @author Carlos
 *
 * @see Color
 */
public class Equipo {

    private final List<Color> colores;

    /**
     * Los coloresd el equipo deben ser proporcionados en este constructor, ya
     * que, luego no podrán ser añadidos ni tampoco quitados.
     *
     * @param color el color "líder" del equipo, realmente es simplemente para
     * que se deba colocar algo si o si en cualquier equipo.
     * @param colores el resto de colores del equipo, puede ir ninguno.
     */
    public Equipo(Color color, Color... colores) {
        this.colores = new ArrayList<>();
        this.colores.add(color);
        this.colores.addAll(Arrays.asList(colores));
    }

    /**
     * Los colores de un equipo no pueden cambiar en partida, por lo tanto, se
     * devuelve una lista inmutable.
     *
     * @return
     */
    public List<Color> getColores() {
        return Collections.unmodifiableList(colores);
    }
}

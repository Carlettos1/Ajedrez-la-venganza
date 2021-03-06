package ajedrez.carlettos.src.tablero.mano;

import ajedrez.carlettos.src.carta.base.Carta;
import ajedrez.carlettos.src.util.ActionResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Esta clase maneja las cartas de un jugador, aunque, podría, por ejemplo, en
 * un 2 vs 2 con mano compartida, usarse para manejar las cartas de 2 jugadores.
 *
 * @author Carlos
 */
public class ManoManager {

    private final List<Carta> cartas;

    public ManoManager(Carta... cartas) {
        this.cartas = new ArrayList<>();
        this.cartas.addAll(Arrays.asList(cartas));
    }

    public void addCarta(Carta... carta) {
        this.cartas.addAll(Arrays.asList(carta));
    }

    /**
     * Retorna todas las cartas.
     * @return todas las cartas que tiene la mano.
     */
    public List<Carta> getCartas() {
        return Collections.unmodifiableList(cartas);
    }

    /**
     * Remueve la carta que se indique, útil al momento de jugar una carta y
     * quitarla de la mano.
     *
     * @param carta la carta que debe borrarse de la mano.
     *
     * @return PASS si existe y se pudo hacer la operación, FAIL de otro modo.
     *
     * @see Carta
     * @see ActionResult
     */
    public ActionResult quitarCarta(Carta carta) {
        if (cartas.remove(carta)) {
            return ActionResult.PASS;
        } else {
            return ActionResult.FAIL;
        }
    }

    /**
     * Quita una carta de la ubicación dada, si se ingresa un número negativo
     * funcionará como python, haciendo que, por ejemplo, -1 acceda al último
     * elemnto.
     *
     * @param posicion la posición que se quiera borrar, puede ser negativo.
     *
     * @return FAIL si la posición ingresada es mayor o igual a la catidad de
     * cartas o si el módulo del valor negativo es mayor a la cantidad de
     * cartas. PASS en el caso que se pueda remover la carta en la posición
     * indicada.
     *
     * @see Carta
     * @see ActionResult
     */
    public ActionResult quitarCarta(int posicion) {
        if (posicion < cartas.size() && posicion >= 0) {
            cartas.remove(posicion);
            return ActionResult.PASS;
        } else if (posicion < 0) {
            int newPos = cartas.size() + posicion;
            if (newPos < cartas.size()) {
                cartas.remove(newPos);
                return ActionResult.PASS;
            }
        }
        return ActionResult.FAIL;
    }

    public Carta getCarta(int pos) {
        return cartas.get(pos);
    }

    public boolean hasCarta(Carta carta) {
        return cartas.stream().anyMatch((c) -> {
            return c.equals(carta);
        });
    }

    public int getCantidadDeCartas() {
        return cartas.size();
    }

    @Override
    public String toString() {
        return "ManoManager{" + "cartas=" + cartas + '}';
    }
}

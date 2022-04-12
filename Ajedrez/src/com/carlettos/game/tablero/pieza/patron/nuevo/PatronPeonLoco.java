package com.carlettos.game.tablero.pieza.patron.nuevo;

import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.manager.TableroAbstract;
import com.carlettos.game.tablero.pieza.patron.Patron;
import java.util.Random;

/**
 *
 * @author Carlettos
 */
public interface PatronPeonLoco extends Patron {

    /**
     * Patron estandar para que todos los peones locos utilizen el mismo dado.
     */
    public static final PatronPeonLoco PATRON_STANDAR = new PatronPeonLoco() {
        private final Random rng = new Random();
        private int turno = -1;
        private int randomNumber;
        @Override
        public int getRandomNumber(int turno) {
            if(this.turno != turno){
                this.turno = turno;
                this.randomNumber = rng.nextInt(8);
            }
            return randomNumber;
        }
    };

    @Override
    public default boolean checkPatron(TableroAbstract tablero, Point inicio, Point final_) {
        if(tablero instanceof Tablero t){
            return switch(this.getRandomNumber(t.getReloj().getTurno())){
                case 0 -> final_.equals(inicio.add(0, 1)) || final_.equals(inicio.add(0, 2));
                case 1 -> final_.equals(inicio.add(1, 1)) || final_.equals(inicio.add(2, 2));
                case 2 -> final_.equals(inicio.add(1, 0)) || final_.equals(inicio.add(2, 0));
                case 3 -> final_.equals(inicio.add(1, -1)) || final_.equals(inicio.add(2, -2));
                case 4 -> final_.equals(inicio.add(0, -1)) || final_.equals(inicio.add(0, -2));
                case 5 -> final_.equals(inicio.add(-1, -1)) || final_.equals(inicio.add(-2, -2));
                case 6 -> final_.equals(inicio.add(-1, 0)) || final_.equals(inicio.add(-2, 0));
                case 7 -> final_.equals(inicio.add(-1, 1)) || final_.equals(inicio.add(-2, 2));
                default -> throw new IllegalArgumentException("Numero random no esperado");
            };
        } else {
            throw new IllegalArgumentException("Tablero no es instanceof Tablero");
        }
    }
    
    /**
     * Da un número aleatorio entre 0 y 8,
     * en un mismo turno debe de dar el mismo número
     * 
     * @return un número entre 0 y 8.
     */
    int getRandomNumber(int turno);
}

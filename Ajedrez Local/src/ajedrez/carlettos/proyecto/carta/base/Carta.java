package ajedrez.carlettos.proyecto.carta.base;

import ajedrez.carlettos.proyecto.tablero.TableroManager;
import ajedrez.carlettos.proyecto.tablero.jugador.Jugador;
import java.util.Objects;

/**
 * Es la representación de una carta, posee nombre, descripción, entre otras
 * cosas, sabe cómo utilizarse y cuándo.
 * <p>
 * Todos los métodos deberían sobreescribirse para crear una carta en
 * específico, además de que una misma carta debe tener siempre el mismo nombre
 * y descripción, mientras que, tanto su color como coste de maná, pueden
 * cambiar por diversos efectos que ocurran en el juego.
 *
 * @author Carlos
 * 
 * @see Jugador
 */
public abstract class Carta {

    protected final String nombre;
    protected final String descripcion;
    protected int costeMana;

    public Carta(String nombre, String descripcion, int costeMana) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costeMana = costeMana;
    }

    public abstract boolean canUsarCarta(Jugador jugador, TableroManager tablero);

    public abstract void usarCarta(Jugador jugador, TableroManager tablero);
    
    /**
     *
     * @param mana cantidad de maná a sumar
     */
    public void cambiarCosteDeMana(int mana){
    
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCosteMana() {
        return costeMana;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.nombre);
        hash = 23 * hash + this.costeMana;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Carta other = (Carta) obj;
        if (this.costeMana != other.costeMana) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }
}

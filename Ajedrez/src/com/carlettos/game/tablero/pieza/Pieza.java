package com.carlettos.game.tablero.pieza;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Par;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.propiedad.Tipo;
import com.carlettos.game.core.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Es la unidad base de la parte del gameplay, todos los métodos deberían ser,
 * idealmente, sobreescritos para crear una pieza en específico.
 *
 * <p>
 * La pieza en cuestión sólo deberá ser responsable de SABER si se puede, o no,
 * ejecutar una acción, nunca ejecutarla, el único caso excepción debe ser el
 * méthodo habilidad, ya que ejecuta la habilidad.
 * </p>
 *
 * @author Carlos
 */
public abstract class Pieza {

    /**
     * Representa el estado de si se ha movido. Prácticamente sirve para no
     * mover a la pieza en el mismo turno 2 veces, debe volver a {@code false}
     * una vez haya terminado el turno.
     */
    protected boolean seHaMovidoEsteTurno;

    /**
     * Es el cooldown que tiene la habilidad de esta pieza, debería disminuir 1
     * por cada turno que pasa, aunque podría llegar a ser modificado por otras
     * piezas o cartas.
     */
    protected int cdActual;

    /**
     * El nombre de la pieza, por ejemplo, Peón, Caballo, Dama. Debe ser el
     * nombre completo, no una abreviación.
     */
    protected String nombre;

    /**
     * Parámetro de utilidad, es la abreviación de la pieza.
     */
    public final String abreviacion;

    /**
     * Es la habilidad que tiene la pieza.
     */
    protected Habilidad habilidad;

    /**
     * Es el color que tiene la piza, si es un color "ninguno" quiere decir que
     * es un place-holder.
     */
    protected Color color;

    /**
     * Los tipos que es la determinada pieza, es posible cambiarlos en pleno
     * juego por efectos de cartas o de otras piezas, pero son los tipos bases
     * con los que empieza la pieza.
     */
    protected final List<Tipo> tipos;

    
    /**
     * Constructor general de la pieza.
     *
     * @param nombre nombre de la pieza.
     * @param abreviacion abreviación de la pieza.
     * @param habilidad la habilidad que tiene la pieza.
     * @param tipos los tipos de la pieza.
     * @param color color de la pieza, blanco o negro en el ajedrez normal.
     */
    public <P extends Pieza> Pieza(String nombre, String abreviacion, Habilidad habilidad, Color color, Tipo... tipos) {
        this.seHaMovidoEsteTurno = false;
        this.cdActual = 0;
        this.nombre = nombre;
        this.abreviacion = abreviacion;
        this.habilidad = habilidad;
        this.color = color;
        this.tipos = Arrays.asList(tipos);
    }

    /**
     * Actualización a sistema de acciones, se debe contemplar si la pieza puede
     * comer, mover o atacar. La habilidad lo ve la habilidad de la pieza.
     *
     * @param accion Accion a ejecutar, no incluye habilidad.
     * @param tablero TableroManager del juego.
     * @param inicio Point de la pieza seleccionada.
     * @param final_ Point del Escaque hacia dónde se mueve.
     *
     * @return ActionResult.
     */
    public abstract ActionResult can(Accion accion, Tablero tablero, Point inicio, Point final_);
    
    /**
     * Este méthodo debe usarse solo en caso de urgencia, se sugiere
     * sobreescribirlo y que se efectúen cálculos más específicos a la pieza
     * correspondiente. Revisa Todos los Escaques del tablero y prueba si se
     * puede efectuar alguna de las acciones básicas, por lo que es
     * extremadamente ineficiente y revisa muchas casillas que, por obviedad, no
     * debería revisar.
     *
     * @param tablero TableroManager del juego.
     * @param seleccionado Point de la Pieza seleccionada.
     * @return Una lista con todos los puntos de acción básica posibles, junto a
     * la acción que corresponde.
     *
     * @see TableroManager
     * @see Pieza
     * @see Escaque
     */
    public List<Par<Point, Accion>> allAcciones(Tablero tablero, Point seleccionado) {
        List<Par<Point, Accion>> acciones = new ArrayList<>();
        for (int x = 0; x < tablero.columnas; x++) {
            for (int y = 0; y < tablero.filas; y++) {
                if (this.can(Accion.COMER, tablero, seleccionado, tablero.getEscaque(x, y).getLocalizacion()).isPositive()) {
                    acciones.add(new Par<>(tablero.getEscaque(x, y).getLocalizacion(), Accion.COMER));
                } if (this.can(Accion.MOVER, tablero, seleccionado, tablero.getEscaque(x, y).getLocalizacion()).isPositive()) {
                    acciones.add(new Par<>(tablero.getEscaque(x, y).getLocalizacion(), Accion.MOVER));
                } if (this.can(Accion.ATACAR, tablero, seleccionado, tablero.getEscaque(x, y).getLocalizacion()).isPositive()) {
                    acciones.add(new Par<>(tablero.getEscaque(x, y).getLocalizacion(), Accion.ATACAR));
                }
            }
        }
        return acciones;
    }
    
    public boolean isTipo(Tipo tipo){
        return this.tipos.contains(tipo);
    }

    /**
     * Suma al cd actual. Valor mínimo es 0.
     *
     * @param numeroDeTurnos Numero de turnos a sumar, puede ser negativo para
     * restar.
     */
    public void cambiarCD(int numeroDeTurnos) {
        if (cdActual + numeroDeTurnos < 0) {
            cdActual = 0;
        } else {
            cdActual += numeroDeTurnos;
        }
    }

    public void setSeHaMovidoEsteTurno(boolean seHaMovidoEsteTurno) {
        this.seHaMovidoEsteTurno = seHaMovidoEsteTurno;
    }

    public Color getColor() {
        return this.color;
    }

    public List<Tipo> getTipos() {
        return tipos;
    }

    public String getNombre() {
        return nombre;
    }

    /*FIXME: No se tiene en cuenta el <> de la habilidad, la cual es necesaria 
    para algunas habilidades*/
    public Habilidad getHabilidad() {
        return habilidad;
    }

    public int getCdActual() {
        return cdActual;
    }

    public boolean seHaMovidoEsteTurno() {
        return seHaMovidoEsteTurno;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.nombre);
        hash = 67 * hash + Objects.hash(this.color);
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
        if (!(obj instanceof Pieza)) {
            return false;
        }
        final Pieza other = (Pieza) obj;
        if (!this.nombre.equals(other.nombre)) {
            return false;
        }
        return other.color.equals(this.color);
    }
}
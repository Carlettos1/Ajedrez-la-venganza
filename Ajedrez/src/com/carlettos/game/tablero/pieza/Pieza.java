package com.carlettos.game.tablero.pieza;

import ajedrez.carlettos.src.util.Accion;
import ajedrez.carlettos.src.util.Par;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.propiedad.Tipo;
import java.awt.Point;
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
 * método habilidad, ya que ejecuta la habilidad.
 * </p>
 *
 * @author Carlos
 */
public abstract class Pieza {
    protected boolean seHaMovidoEsteTurno;
    protected int cdActual;
    protected String nombre;
    public final String abreviacion;
    protected Habilidad habilidad;
    protected Color color;
    protected final List<Tipo> tipos;

    public Pieza(String nombre, String abreviacion, Habilidad habilidad, Color color, Tipo... tipos) {
        this.seHaMovidoEsteTurno = false;
        this.cdActual = 0;
        this.nombre = nombre;
        this.abreviacion = abreviacion;
        this.habilidad = habilidad;
        this.color = color;
        this.tipos = Arrays.asList(tipos);
    }
    
    //TODO: mover, comer, habilidad y atacar
    public abstract boolean canMover(Tablero tablero, Point inicio, Point final_);
    public abstract boolean canComer(Tablero tablero, Point inicio, Point final_);
    public abstract Par<Boolean, String> canUsarHabilidad(Tablero tablero, Point inicio, Point final_, String informacionExtra);
    public abstract void habilidad(Tablero tablero, Point inicio, Point final_, String informacionExtra);
    
    public List<Par<Point, Accion>> allAcciones(Tablero tablero, Point seleccionado) {
        List<Par<Point, Accion>> acciones = new ArrayList<>();
        for (int x = 0; x < tablero.columnas; x++) {
            for (int y = 0; y < tablero.filas; y++) {
                if (this.canComer(tablero, seleccionado, tablero.getEscaque(x, y).getLocalizacion())) {
                    acciones.add(new Par<>(tablero.getEscaque(x, y).getLocalizacion(), Accion.COMER));
                } else if (this.canMover(tablero, seleccionado, tablero.getEscaque(x, y).getLocalizacion())) {
                    acciones.add(new Par<>(tablero.getEscaque(x, y).getLocalizacion(), Accion.MOVER));
                }
            }
        }
        return acciones;
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

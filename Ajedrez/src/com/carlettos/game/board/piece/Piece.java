package com.carlettos.game.board.piece;

import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Tuple;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.board.property.PieceType;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.property.ability.InfoPiece;
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
public abstract class Piece {

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
    protected Ability habilidad;

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
    protected final List<PieceType> tipos;

    
    /**
     * Constructor general de la pieza.
     *
     * @param nombre nombre de la pieza.
     * @param abreviacion abreviación de la pieza.
     * @param habilidad la habilidad que tiene la pieza.
     * @param tipos los tipos de la pieza.
     * @param color color de la pieza, blanco o negro en el ajedrez normal.
     */
    public Piece(String nombre, String abreviacion, Ability habilidad, Color color, PieceType... tipos) {
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
    public abstract ActionResult can(Action accion, AbstractBoard tablero, Point inicio, Point final_);
    
    public void postAccion(Action accion, AbstractBoard tablero, Point inicio, Point final_){
        this.setSeHaMovidoEsteTurno(true);
    }
    
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
     * la acción que corresponde. EXCLUYE A LA HABILIDAD.
     *
     * @see TableroManager
     * @see Piece
     * @see Escaque
     */
    public List<Tuple<Point, Action>> allAcciones(AbstractBoard tablero, Point seleccionado) {
        List<Tuple<Point, Action>> acciones = new ArrayList<>();
        for (int x = 0; x < tablero.columnas; x++) {
            for (int y = 0; y < tablero.filas; y++) {
                if (this.can(Action.COMER, tablero, seleccionado, tablero.getEscaque(x, y).getPos()).isPositive()) {
                    acciones.add(new Tuple<>(tablero.getEscaque(x, y).getPos(), Action.COMER));
                } if (this.can(Action.MOVER, tablero, seleccionado, tablero.getEscaque(x, y).getPos()).isPositive()) {
                    acciones.add(new Tuple<>(tablero.getEscaque(x, y).getPos(), Action.MOVER));
                } if (this.can(Action.ATACAR, tablero, seleccionado, tablero.getEscaque(x, y).getPos()).isPositive()) {
                    acciones.add(new Tuple<>(tablero.getEscaque(x, y).getPos(), Action.ATACAR));
                }
            }
        }
        return acciones;
    }
    
    public boolean isTipo(PieceType tipo){
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

    public void setColor(Color color) {
        this.color = color;
    }

    public List<PieceType> getTipos() {
        return tipos;
    }

    /**
     * Le quita el tipo especificado a la pieza, si es que lo tiene.
     * @param tipo tipo que se quiera eliminar.
     * @return PASS.
     */
    public ActionResult addTipo(PieceType tipo) {
        return ActionResult.fromBoolean(this.getTipos().add(tipo));
    }

    
    /**
     * Le quita el tipo especificado a la pieza, si es que lo tiene.
     * @param tipo tipo que se quiera eliminar.
     * @return PASS si se ha quitado el tipo, FAIL si no.
     */
    public ActionResult removeTipo(PieceType tipo) {
        return ActionResult.fromBoolean(this.getTipos().remove(tipo));
    }

    /**
     * Le agrega todos los tipos especificados a esta pieza.
     * @param tipos tipos que se quieran agregar.
     * @return PASS si se le han agregado todos los tipos a esta pieza, 
     * FAIL en otro caso.
     * @throws NullPointerException si hay algún null en el argumento.
     */
    public ActionResult addTipos(PieceType... tipos) {
        boolean success = true;
        for (PieceType tipo : tipos) {
            Objects.requireNonNull(tipo);
            success = Boolean.logicalAnd(success, this.getTipos().add(tipo));
        }
        return ActionResult.fromBoolean(success);
    }

    /**
     * Le quita todos los tipos especificados a esta pieza, si es que los tiene.
     * @param tipos tipos que se quieran eliminar.
     * @return PASS si la pieza tenía todos los tipos indicados y han sido 
     * eliminados, FAIL en otro caso.
     * @throws NullPointerException si hay algún null en el argumento.
     */
    public ActionResult removeTipos(PieceType... tipos) {
        boolean success = true;
        for (PieceType tipo : tipos) {
            Objects.requireNonNull(tipo);
            success = Boolean.logicalAnd(success, this.getTipos().remove(tipo));
        }
        return ActionResult.fromBoolean(success);
    }

    public String getNombre() {
        return nombre;
    }

    /*FIXME: No se tiene en cuenta el <> de la habilidad, la cual es necesaria 
    para algunas habilidades*/
    public Ability getHabilidad() {
        return habilidad;
    }

    public int getCdActual() {
        return cdActual;
    }

    public boolean seHaMovidoEsteTurno() {
        return seHaMovidoEsteTurno;
    }
    
    public InfoPiece toInfo(){
        return new InfoPiece(this);
    }

    @Override
    public String toString() {
        return abreviacion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.nombre);
        hash = 67 * hash + Objects.hash(this.color);
        return hash;
    }

    /**
     * Revisa si las piezas tienen el mismo nombre y son del mismo color. De la
     * misma que todos los peones blancos son iguales en el ajedrez normal.
     * Si el color de una pieza es gris, sólo se verificará su nombre;
     * @param obj la otra pieza
     * @return true si tienen el mismo nombre y color, false si no.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Piece)) {
            return false;
        }
        final Piece other = (Piece) obj;
        if (!this.nombre.equals(other.nombre)) {
            return false;
        }
        if(this.color == Color.GRIS || other.color == Color.GRIS){
            return true;
        }
        return other.color.equals(this.color);
    }
}
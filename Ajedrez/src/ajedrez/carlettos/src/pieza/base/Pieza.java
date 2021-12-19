package ajedrez.carlettos.src.pieza.base;

import ajedrez.carlettos.src.estructura.base.Estructura;
import ajedrez.carlettos.src.pieza.propiedad.EnumTipoPieza;
import ajedrez.carlettos.src.pieza.propiedad.Habilidad;
import ajedrez.carlettos.src.tablero.TableroManager;
import ajedrez.carlettos.src.tablero.escaque.Escaque;
import ajedrez.carlettos.src.tablero.jugador.Color;
import ajedrez.carlettos.src.util.Accion;
import ajedrez.carlettos.src.util.Par;
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
     * Es la habilidad que tiene la pieza, tiene toda la información
     * descriptiva, pero no el cómo se ejecuta, eso debe escribirse en el método
     * habilidad.
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
    protected final List<EnumTipoPieza> tipos;

    /**
     * Constructor de la estructura, cuando se extienda de la clase, el
     * constructor del hijo, idealmente, debe sólo incluir el isBlanca en los
     * parámetros, ya que el resto de variables deben ser constantes entre todos
     * los mismo tipos de piezas. No es obligatorio.
     *
     * @param nombre nombre de la pieza, por ejemplo, Peón.
     * @param abreviacion abreviación de la pieza, por ejemplo, P.
     * @param habilidad la habilidad que tiene la pieza, con su descripción,
     * cooldowns y costes de maná. La ejecución queda relegada al método
     * {@code pieza.habilidad();}.
     * @param tipos todos los tipos de los cuales es la pieza en cuestión. No es
     * inmutable, porque puede, por ejemplo, una carta, quitar el tipo de
     * transportable, o añadir el tipo demoníaco.
     * @param color color de la pieza, blanco o negro en el ajedrez normal.
     *
     * @see Habilidad
     * @see EnumTipoPieza
     * @see Estructura
     */
    public Pieza(String nombre, String abreviacion, Habilidad habilidad, Color color, EnumTipoPieza... tipos) {
        this.seHaMovidoEsteTurno = false;
        this.cdActual = 0;
        this.nombre = nombre;
        this.abreviacion = abreviacion;
        this.habilidad = habilidad;
        this.color = color;
        this.tipos = new ArrayList<>();
        this.tipos.addAll(Arrays.asList(tipos));
    }

    /**
     * Función específica para saber si puede mover o no, debe ser implementada
     * con mucho cuidado y de forma correcta para que consuma la menor cantidad
     * de recursos posibles y sea rápida, ya que, por experiencia propia, se
     * suele apretar todas las piezas del tablero para saber qué y qué no pueden
     * hacer, por lo que suele invocarse mucho este método. Debe implementarse
     * independientemente del método canComer, aunque puede ser el mismo código,
     * como por ejemplo, el caballo, pero, por temas de orden, debe revisar
     * hacia donde se mueve.
     * <p>
     * En todos los casos, canComer y canMover deben dar resultados
     * completamente distintos; si uno da true, el otro debe dar false. Esto es
     * importante porque comer y mover hacen reaccionar cartas completamente
     * diferentes, además, idealmente, deben marcarse de forma distinta en el
     * método allAcciones.
     *
     * @param tablero TableroManager del juego.
     * @param inicio Point de la pieza seleccionada.
     * @param final_ Point del Escaque hacia dónde se mueve.
     *
     * @return boolean true si puede mover, false si no.
     *
     * @see TableroManager
     * @see Pieza
     * @see Escaque
     * @see Habilidad
     */
    public abstract boolean canMover(TableroManager tablero, Point inicio, Point final_);

    /**
     * Función específica para saber si puede comer o no, debe ser implementada
     * con mucho cuidado y de forma correcta para que consuma la menor cantidad
     * de recursos posibles y sea rápida, ya que, por experiencia propia, se
     * suele apretar todas las piezas del tablero para saber qué y qué no pueden
     * hacer, por lo que suele invocarse mucho este método. Debe implementarse
     * independientemente del método canMover, aunque puede ser el mismo código,
     * como por ejemplo, el caballo, pero, por temas de orden, debe revisar lo
     * que come.
     * <p>
     * En todos los casos, canComer y canMover deben dar resultados
     * completamente distintos; si uno da true, el otro debe dar false. Esto es
     * importante porque comer y mover hacen reaccionar cartas completamente
     * diferentes, además, idealmente, deben marcarse de forma distinta en el
     * método allAcciones.
     *
     * @param tablero TableroManager del juego.
     * @param inicio Point de la pieza seleccionada.
     * @param final_ Point del Escaque hacia dónde va a comer.
     *
     * @return boolean true si puede comer, false si no.
     *
     * @see TableroManager
     * @see Pieza
     * @see Escaque
     * @see Habilidad
     */
    public abstract boolean canComer(TableroManager tablero, Point inicio, Point final_);

    /**
     * Verifica que los requisitos se cumplan, tanto como de cooldown y maná
     * como algunos más específicos como los que vienen con el String de
     * informacionExtra.
     *
     * @param tablero TableroManager del juego.
     * @param inicio Point de la pieza seleccionada, o, en casos particulares,
     * desde dónde se efectúa la acción.
     * @param final_ Point del Escaque hacia dónde se dirige la habilidad, suele
     * omitirse, ya que la información necesaria entra por otra vía.
     * @param informacionExtra String que contiene la información de la
     * habilidad, por ejemplo, hacia dónde se va a aplicar la habilidad de la
     * torre (NESW).
     *
     * @return Par, boolean true si se puede usar la habilidad, false de lo
     * contrario, y un String que contiene información adicional para poder
     * accionar de acuerdo a los diferentes fallos, o, en casos muy específicos,
     * éxitos.
     *
     * @see TableroManager
     * @see Pieza
     * @see Escaque
     * @see Habilidad
     * @see Par
     */
    public abstract Par<Boolean, String> canUsarHabilidad(TableroManager tablero, Point inicio, Point final_, String informacionExtra);

    /**
     * Efectúa la habilidad, por lo tanto, efectúa acciones relacionadas a la
     * habilidad. Se recomienda usar un enum con un parser para descifrar el
     * String de informacionExtra y poder trabajarlo de forma más simple. Pero
     * no es requisito.
     *
     * @param tablero TableroManager del juego.
     * @param inicio Point de la pieza seleccionada, o, en casos particulares,
     * desde dónde se efectúa la acción.
     * @param final_ Point del Escaque hacia dónde se dirige la habilidad, suele
     * omitirse, ya que la información necesaria entra por otra vía.
     * @param informacionExtra String que contiene la información de la
     * habilidad, por ejemplo, hacia dónde se va a aplicar la habilidad de la
     * torre (NESW).
     *
     * @see TableroManager
     * @see Pieza
     * @see Escaque
     * @see Habilidad
     */
    public abstract void habilidad(TableroManager tablero, Point inicio, Point final_, String informacionExtra);

    /**
     * Este método debe usarse solo en caso de urgencia, se sugiere
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
    public List<Par<Point, Accion>> allAcciones(TableroManager tablero, Point seleccionado) {
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

    public List<EnumTipoPieza> getTipos() {
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

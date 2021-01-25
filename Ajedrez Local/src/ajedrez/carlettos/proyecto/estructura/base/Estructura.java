package ajedrez.carlettos.proyecto.estructura.base;

import ajedrez.carlettos.proyecto.estructura.propiedad.EnumTipoEstructura;
import ajedrez.carlettos.proyecto.pieza.base.Pieza;
import ajedrez.carlettos.proyecto.pieza.propiedad.EnumTipoPieza;
import ajedrez.carlettos.proyecto.pieza.propiedad.Habilidad;
import ajedrez.carlettos.proyecto.tablero.TableroManager;
import ajedrez.carlettos.proyecto.tablero.jugador.Color;
import ajedrez.carlettos.proyecto.util.Par;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class Estructura {

    /**
     * Para saber si necesita magia o no, la cantidad de magia debe ser
     * especificada por cada implementación de estructura diferente.
     */
    protected final boolean requiereMagia;

    /**
     * Si se ha movido en el turno actual, pueden ocurrir eventos que modifiquen
     * este valor aún si la estructura no ha usado su habilidad, como también
     * pueden volver a darle otro movimiento en el mismo turno. Debe volver a
     * {@code false} una vez haya terminado el turno.
     */
    protected boolean seHaMovidoEsteTurno;

    /**
     * Cooldown que tiene la estructura para volver a lanzar la habilidad.
     */
    protected int cdActual;

    //TODO: permitir cualquier tipo de forma.
    /**
     * Dimensiones de la estructura, en números enteros de escaques que ocupa,
     * por ahora, solo soporta estructuras cuadradas.
     */
    protected final Par<Integer, Integer> dimensiones;

    /**
     * Nombre de la estructura, por ejemplo, Muro. Debe ser el nombre completo,
     * no una abreviación.
     */
    protected String nombre;

    /**
     * Habilidad de la estructura, tiene la información para mostrar, pero no
     * tiene efectos prácticos.
     */
    protected Habilidad habilidad;

    /**
     * Es el color de la estructura, por ejemplo, blanca o negra. Puede cambiar
     * en plena partida poe efectos de cartas u otros.
     */
    protected Color color;

    /**
     * Son los tipos de la estructura, por ejemplo, si es pequeña.
     */
    protected final List<EnumTipoEstructura> tipos;

    /**
     * Constructor de la estructura, cuando se extienda de la clase, el
     * constructor del hijo, idealmente, debe sólo incluir el color en los
     * parámetros, ya que el resto de variables deben ser constantes entre todos
     * los mismo tipos de piezas. No es obligatorio.
     *
     * @param requiereMagia true si necesita usar alguna fuente de magia, false
     * si no.
     * @param ancho la x en el plano cartesiano, el valor mínimo es 1.
     * @param alto la y en el plano cartesiano, el valor mínimo es 1.
     * @param nombre nombre de la pieza, por ejemplo, Peón.
     * @param habilidad la habilidad que tiene la estructura, con su
     * descripción, cooldowns y costes de maná. La ejecución queda relegada al
     * método {@code estructura.habilidad();}.
     * @param color es el color de la estructura, blanco o negro en el ajedrez
     * normal.
     * @param tipos todos los tipos de los cuales es la estructura. No es
     * inmutable, porque puede, por ejemplo, una carta añadirle el tipo
     * transportable.
     *
     * @see Habilidad
     * @see EnumTipoPieza
     * @see Pieza
     */
    public Estructura(boolean requiereMagia, int ancho, int alto, String nombre, Habilidad habilidad, Color color, EnumTipoEstructura... tipos) {
        this.requiereMagia = requiereMagia;
        this.seHaMovidoEsteTurno = false;
        this.cdActual = 0;
        if (ancho < 1) {
            throw new IllegalArgumentException("El ancho=" + ancho + " debe tener un valor mínimo de 1.");
        }
        if (alto < 1) {
            throw new IllegalArgumentException("El alto=" + alto + " debe tener un valor mínimo de 1.");
        }
        this.dimensiones = new Par<>(ancho, alto);
        this.nombre = nombre;
        this.habilidad = habilidad;
        this.color = color;
        this.tipos = new ArrayList<>();
        this.tipos.addAll(Arrays.asList(tipos));
    }

    /**
     * Constructor de la estructura, cuando se extienda de la clase, el
     * constructor del hijo, idealmente, debe sólo incluir el color en los
     * parámetros, ya que el resto de variables deben ser constantes entre todos
     * los mismo tipos de piezas. No es obligatorio.
     *
     * @param requiereMagia true si necesita usar alguna fuente de magia, false
     * si no.
     * @param dimensiones Par conteniendo x e y, como en el plano cartesiano, el
     * valor mínimo es 1 para cada dimensión.
     * @param nombre nombre de la pieza, por ejemplo, Peón.
     * @param habilidad la habilidad que tiene la estructura, con su
     * descripción, cooldowns y costes de maná. La ejecución queda relegada al
     * método {@code estructura.habilidad();}.
     * @param color color de la pieza, blanco o negro en el ajedrez normal.
     * @param tipos todos los tipos de los cuales es la estructura. No es
     * inmutable, porque puede, por ejemplo, una carta añadirle el tipo
     * transportable.
     *
     * @see Par
     * @see Habilidad
     * @see EnumTipoPieza
     * @see Pieza
     */
    public Estructura(boolean requiereMagia, Par<Integer, Integer> dimensiones, String nombre, Habilidad habilidad, Color color, EnumTipoEstructura... tipos) {
        this(requiereMagia, dimensiones.x, dimensiones.y, nombre, habilidad, color, tipos);
    }

    /**
     * Usa la habilidad de la estructura en cuestión, no afecta ni modifica el
     * maná o cd del jugador, lo cual debe hacerse aparte. Se recomienda usar un
     * enum con un parser para descifrar el String de informacionExtra y poder
     * trabajarlo de forma más simple. Pero no es requisito.
     *
     * @param tablero tablero de la pieza.
     * @param inicio ubicación desde dónde se lanza la habilidad.
     * @param final_ ubicación hacia dónde se lanza la habilidad.
     * @param informacionExtra información extra que debe ser pasada para
     * ejecutar la habilidad, por ejemplo, la torre tiene una de estas letras de
     * información extra NESW.
     *
     * @see TableroManager
     * @see Estructura
     * @see Escaque
     * @see Habilidad
     * @see Par
     */
    public abstract void habilidad(TableroManager tablero, Point inicio, Point final_, String informacionExtra);

    /**
     * verifica si puede usar la habilidad de la estructura en cuestión. Se
     * recomienda usar un enum con un parser para descifrar el String de
     * informacionExtra y poder trabajarlo de forma más simple. Pero no es
     * requisito.
     *
     * @param tablero tablero de la pieza.
     * @param inicio ubicación desde dónde se lanza la habilidad.
     * @param final_ ubicación hacia dónde se lanza la habilidad.
     * @param informacionExtra información extra que debe ser pasada para
     * ejecutar la habilidad, por ejemplo, la torre tiene una de estas letras de
     * información extra NESW.
     * @return un par de valores, con un boolean para saber si puede o no usar
     * la habilidad; y, con un String que lleva información extra para poder
     * detectar con presición qué es lo que no permite usar la habilidad.
     *
     * @see TableroManager
     * @see Estructura
     * @see Escaque
     * @see Habilidad
     * @see Par
     */
    public abstract Par<Boolean, String> canUsarHabilidad(TableroManager tablero, Point inicio, Point final_, String informacionExtra);

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

    public boolean requiereMagia() {
        return requiereMagia;
    }

    public Color getColor() {
        return color;
    }

    public boolean seHaMovidoEsteTurno() {
        return seHaMovidoEsteTurno;
    }

    public Par<Integer, Integer> getDimensiones() {
        return dimensiones;
    }

    public int getCdActual() {
        return cdActual;
    }

    public String getNombre() {
        return nombre;
    }

    public Habilidad getHabilidad() {
        return habilidad;
    }

    public List<EnumTipoEstructura> getTipos() {
        return tipos;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.nombre);
        hash = 13 * hash + Objects.hashCode(this.color);
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
        final Estructura other = (Estructura) obj;
        if (!this.color.equals(other.color)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }
}

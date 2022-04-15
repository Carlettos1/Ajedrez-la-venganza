package com.carlettos.game.board.manager;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.Escaque;
import com.carlettos.game.board.piece.Pieza;
import com.carlettos.game.board.piece.Vacia;
import com.carlettos.game.board.piece.classic.Peon;
import com.carlettos.game.board.piece.classic.Bishop;
import com.carlettos.game.board.piece.classic.Caballo;
import com.carlettos.game.board.piece.classic.Reina;
import com.carlettos.game.board.piece.classic.Rey;
import com.carlettos.game.board.piece.classic.Torre;
import com.carlettos.game.board.piece.starting.Ariete;
import com.carlettos.game.board.piece.starting.Arquero;
import com.carlettos.game.board.piece.starting.Ballesta;
import com.carlettos.game.board.piece.starting.Brujo;
import com.carlettos.game.board.piece.starting.Catapulta;
import com.carlettos.game.board.piece.starting.Cañon;
import com.carlettos.game.board.piece.starting.Constructor;
import com.carlettos.game.board.piece.starting.Defensor;
import com.carlettos.game.board.piece.starting.Hechicero;
import com.carlettos.game.board.piece.starting.Muro;
import com.carlettos.game.board.piece.starting.Nave;
import com.carlettos.game.board.piece.starting.Paladin;
import com.carlettos.game.board.piece.starting.PeonLoco;
import com.carlettos.game.board.piece.starting.SuperPeon;
import com.carlettos.game.board.piece.starting.TorreTesla;
import com.carlettos.game.board.piece.pattern.Patron;
import com.carlettos.game.board.property.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlettos
 */
public class AbstractBoard {

    //TODO: poder hacer tableros de cualquier tipo de formas.
    protected final Escaque[][] tableroAjedrez;

    /**
     * Cantidad de columnas del tablero. Está relacionada con el número x en el
     * plano cartesiano.
     */
    public final int columnas;

    /**
     * Cantidad de filas del tablero. Está relacionada con el número y en el
     * plano cartesiano.
     */
    public final int filas;

    public AbstractBoard(int columnas, int filas) {
        this.tableroAjedrez = new Escaque[filas][columnas];
        this.columnas = columnas;
        this.filas = filas;
        for (int y = 0; y < filas; y++) {
            for (int x = 0; x < columnas; x++) {
                tableroAjedrez[y][x] = new Escaque(new Point(x, y));
            }
        }
    }

    public void quitarPieza(int x, int y) {
        this.quitarPieza(new Point(x, y));
    }

    public void quitarPieza(Point punto) {
        this.getEscaque(punto).quitarPieza();
    }

    /**
     * Da el escaque de la posición especificada.
     *
     * @param punto punto del cuál se quiere sacar el escaque.
     *
     * @return el escaque en la posición solicidada.
     *
     * @throws IllegalArgumentException si alguna coordenada se sale de las
     * dimensiones del tablero.
     *
     * @see Escaque
     */
    public Escaque getEscaque(Point punto) {
        if (punto.x < 0) {
            throw new IllegalArgumentException("La coordenada x no puede ser negativa");
        }
        if (punto.y < 0) {
            throw new IllegalArgumentException("La coordenada y no puede ser negativa");
        }
        if (punto.x >= columnas) {
            throw new IllegalArgumentException("La coordenada x no puede ser mayor o igual que el número de columnas");
        }
        if (punto.y >= filas) {
            throw new IllegalArgumentException("La coordenada y no puede ser mayor o igual que el número de filas");
        }
        return tableroAjedrez[punto.y][punto.x];
    }
    
    public boolean isOutOfBorder(Point punto){
        if (punto.x < 0) {
            return true;
        }
        if (punto.y < 0) {
            return true;
        }
        if (punto.x >= columnas) {
            return true;        
        }
        return punto.y >= filas;
    }

    /**
     * Da el escaque ne la posición especificada.
     *
     * @param x coordenada x de la posición.
     * @param y coordenada y de la posición.
     *
     * @return el escaque en la posición solicidada.
     *
     * @throws IllegalArgumentException si alguna coordenada se sale de las
     * dimensiones del tablero.
     *
     * @see Escaque
     */
    public Escaque getEscaque(int x, int y) {
        return getEscaque(new Point(x, y));
    }

    public List<Escaque> getEscaquesCercanos(Escaque escaque) {
        List<Escaque> escaques = new ArrayList<>(filas * columnas);

        int x = escaque.getPos().x;
        int y = escaque.getPos().y;

        int tmp;
        if ((tmp = x + 1) < columnas) {
            escaques.add(this.getEscaque(tmp, y));
        }
        if ((tmp = x - 1) >= 0) {
            escaques.add(this.getEscaque(tmp, y));
        }
        if ((tmp = y + 1) < filas) {
            escaques.add(this.getEscaque(x, tmp));
        }
        if ((tmp = y - 1) >= 0) {
            escaques.add(this.getEscaque(x, tmp));
        }

        return escaques;
    }
    
    public List<Escaque> getEscaquesMatchPatron(Patron patron, Point inicio){
        List<Escaque> matches = new ArrayList<>(filas * columnas);
        for (Escaque[] escaques : tableroAjedrez) {
            for (Escaque escaque : escaques) {
                if(patron.checkPatron(this, inicio, escaque.getPos())){
                    matches.add(escaque);
                }
            }
        }
        return matches;
    }
    
    public int getValoracionTotal(Color positivo, Color negativo, Map<Class<? extends Pieza>, Integer> map){
        int valoracion = 0;
        for (Escaque[] escaques : tableroAjedrez) {
            for (Escaque escaque : escaques) {
                if (escaque.getColorControlador().equals(positivo) && map.containsKey(escaque.getPieza().getClass())) {
                    valoracion += map.get(escaque.getPieza().getClass());
                } else if(escaque.getColorControlador().equals(negativo) && map.containsKey(escaque.getPieza().getClass())) {
                    valoracion -= map.get(escaque.getPieza().getClass());
                }
            }
        }
        return valoracion;
    }
    
    public void copyContentTo(AbstractBoard other){
        if(this.columnas != other.columnas || this.filas != other.filas){
            throw new IllegalArgumentException("No se puede copiar contenido a un tablero de otras dimensiones");
        }
        for (Escaque[] escaques : this.tableroAjedrez) {
            for (Escaque escaque : escaques) {
                other.getEscaque(escaque.getPos()).setIsConstruible(escaque.isConstruible());
                other.getEscaque(escaque.getPos()).setIsFuenteDeMagia(escaque.isFuenteDeMagia());
                Pieza pieza = escaque.getPieza();
                Pieza p2 = (switch (pieza) {
                    case Peon piezaCopiada -> new Peon(piezaCopiada.getColor());
                    case Bishop piezaCopiada -> new Bishop(piezaCopiada.getColor());
                    case Caballo piezaCopiada -> new Caballo(piezaCopiada.getColor());
                    case Reina piezaCopiada -> new Reina(piezaCopiada.getColor());
                    case Rey piezaCopiada -> new Rey(piezaCopiada.getColor());
                    case Torre piezaCopiada -> new Torre(piezaCopiada.getColor());
                    case Ariete piezaCopiada -> new Ariete(piezaCopiada.getColor());
                    case Arquero piezaCopiada -> new Arquero(piezaCopiada.getColor());
                    case Ballesta piezaCopiada -> new Ballesta(piezaCopiada.getColor());
                    case Brujo piezaCopiada -> new Brujo(piezaCopiada.getColor());
                    case Catapulta piezaCopiada -> new Catapulta(piezaCopiada.getColor());
                    case Cañon piezaCopiada -> new Cañon(piezaCopiada.getColor());
                    case Constructor piezaCopiada -> new Constructor(piezaCopiada.getColor());
                    case Defensor piezaCopiada -> new Defensor(piezaCopiada.getColor());
                    case Hechicero piezaCopiada -> new Hechicero(piezaCopiada.getColor());
                    case Muro piezaCopiada -> new Muro(piezaCopiada.getColor());
                    case Nave piezaCopiada -> new Nave(piezaCopiada.getColor());
                    case Paladin piezaCopiada -> new Paladin(piezaCopiada.getColor());
                    case PeonLoco piezaCopiada -> new PeonLoco(piezaCopiada.getColor());
                    case SuperPeon piezaCopiada -> new SuperPeon(piezaCopiada.getColor());
                    case TorreTesla piezaCopiada -> new TorreTesla(piezaCopiada.getColor());
                    default -> new Vacia();
                });
                p2.setSeHaMovidoEsteTurno(pieza.seHaMovidoEsteTurno());
                p2.cambiarCD(pieza.getCdActual());
                other.getEscaque(escaque.getPos()).setPieza(p2);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(5 * columnas * filas);
        for (int y = filas - 1; y >= 0; y--) {
            for (int x = 0; x < columnas; x++) {
                str.append("[")
                        .append(getEscaque(x, y).getPieza().abreviacion)
                        .append("]");
            }
            str.append('\n');
        }
        return str.toString();
    }
}

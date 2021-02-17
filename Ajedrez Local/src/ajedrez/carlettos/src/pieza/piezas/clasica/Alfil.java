package ajedrez.carlettos.src.pieza.piezas.clasica;

import ajedrez.carlettos.src.pieza.base.Pieza;
import ajedrez.carlettos.src.pieza.propiedad.EnumTipoPieza;
import ajedrez.carlettos.src.pieza.propiedad.Habilidad;
import ajedrez.carlettos.src.tablero.TableroManager;
import ajedrez.carlettos.src.tablero.jugador.Color;
import ajedrez.carlettos.src.util.Accion;
import ajedrez.carlettos.src.util.Par;
import java.awt.Point;
import java.util.List;

public class Alfil extends Pieza {

    public static final Habilidad HABILIDAD_ALFIL = new Habilidad("Cambio de Color",
            "El alfil cambia de color, pudiendo moverse una casilla en cualquiera de las 4 direcciones cardinales.",
            2,
            0,
            "La dirección cardinal hacia dónde se mueve el alfil (NESW).");

    public Alfil(Color color) {
        super("Alfil", "A", HABILIDAD_ALFIL, color, EnumTipoPieza.BIOLOGICA, EnumTipoPieza.TRANSPORTABLE);
    }

    @Override
    public boolean canMover(TableroManager tablero, Point inicio, Point final_) {
        if (tablero.getEscaque(final_).hasPieza()) {
            return false;
        }

        if (seHaMovidoEsteTurno()) {
            return false;
        }

        int deltaX = final_.x - inicio.x;
        int deltaY = final_.y - inicio.y;

        if (Math.abs(deltaY) != Math.abs(deltaX)) {
            return false;
        }

        int signoX = deltaX > 0 ? 1 : -1;
        int signoY = deltaY > 0 ? 1 : -1;

        for (int escaque = 1; escaque < Math.abs(deltaX); escaque++) {
            if (tablero.getEscaque(inicio.x + escaque * signoX,
                    inicio.y + escaque * signoY).hasPieza()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canComer(TableroManager tablero, Point inicio, Point final_) {
        if (!tablero.getEscaque(final_).hasPieza()) {
            return false;
        }

        if (tablero.getEscaque(final_).getPieza().getColor().equals(
                this.getColor())) {
            return false;
        }

        if (seHaMovidoEsteTurno()) {
            return false;
        }

        int deltaX = final_.x - inicio.x;
        int deltaY = final_.y - inicio.y;

        if (Math.abs(deltaY) != Math.abs(deltaX)) {
            return false;
        }

        int signoX = deltaX > 0 ? 1 : -1;
        int signoY = deltaY > 0 ? 1 : -1;

        for (int escaque = 1; escaque < Math.abs(deltaX); escaque++) {
            if (tablero.getEscaque(inicio.x + escaque * signoX,
                    inicio.y + escaque * signoY).hasPieza()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Par<Boolean, String> canUsarHabilidad(TableroManager tablero, Point inicio, Point final_, String informacionExtra) {
        if (this.getCdActual() > 0) {
            return new Par(false, "Aún en CD");
        }

        if (this.seHaMovidoEsteTurno()) {
            return new Par(false, "Se ha movido este turno");
        }
        
        if (informacionExtra == null) {
            return new Par(false, "Null información extra");
        }

        boolean verificacion;
        String why;

        switch (informacionExtra) {
            case "N":
                verificacion = !tablero.getEscaque(inicio.x, inicio.y + 1).hasPieza();
                break;
            case "E":
                verificacion = !tablero.getEscaque(inicio.x + 1, inicio.y).hasPieza();
                break;
            case "S":
                verificacion = !tablero.getEscaque(inicio.x, inicio.y - 1).hasPieza();
                break;
            case "W":
                verificacion = !tablero.getEscaque(inicio.x - 1, inicio.y).hasPieza();
                break;
            default:
                return new Par(false, "Parámetros inválidos");
        }
        why = verificacion ? "No hay pieza" : "Hay pieza";
        return new Par(verificacion, why);
    }

    @Override
    public void habilidad(TableroManager tablero, Point inicio, Point final_, String informacionExtra) {
        switch (informacionExtra) {
            case "N":
                tablero.getEscaque(inicio.x, inicio.y + 1).setPieza(this);
                tablero.getEscaque(inicio).quitarPieza();
                break;
            case "E":
                tablero.getEscaque(inicio.x + 1, inicio.y).setPieza(this);
                tablero.getEscaque(inicio).quitarPieza();
                break;
            case "S":
                tablero.getEscaque(inicio.x, inicio.y - 1).setPieza(this);
                tablero.getEscaque(inicio).quitarPieza();
                break;
            case "W":
                tablero.getEscaque(inicio.x - 1, inicio.y).setPieza(this);
                tablero.getEscaque(inicio).quitarPieza();
                break;
        }
        //TODO: cambiar cd
    }

    @Override
    public List<Par<Point, Accion>> allAcciones(TableroManager tablero, Point seleccionado) {
        //myc viene de mover y comer
        List<Par<Point, Accion>> myc = super.allAcciones(tablero, seleccionado);
        if(this.canUsarHabilidad(tablero, seleccionado, seleccionado, "N").x){
            myc.add(new Par<>(new Point(seleccionado.x, seleccionado.y + 1), Accion.HABILIDAD));
        }
        if(this.canUsarHabilidad(tablero, seleccionado, seleccionado, "E").x){
            myc.add(new Par<>(new Point(seleccionado.x + 1, seleccionado.y), Accion.HABILIDAD));
        }
        if(this.canUsarHabilidad(tablero, seleccionado, seleccionado, "S").x){
            myc.add(new Par<>(new Point(seleccionado.x, seleccionado.y - 1), Accion.HABILIDAD));
        }
        if(this.canUsarHabilidad(tablero, seleccionado, seleccionado, "W").x){
            myc.add(new Par<>(new Point(seleccionado.x - 1, seleccionado.y), Accion.HABILIDAD));
        }
        return myc;
    }
}

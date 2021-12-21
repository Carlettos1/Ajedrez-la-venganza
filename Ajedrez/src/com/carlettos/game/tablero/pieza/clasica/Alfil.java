package com.carlettos.game.tablero.pieza.clasica;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.Par;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import java.awt.Point;
import java.util.List;

public class Alfil extends Pieza {
    public static final Habilidad HABILIDAD_ALFIL = new Habilidad("Cambio de Color",
            "El alfil cambia de color, pudiendo moverse una casilla en cualquiera de las 4 direcciones cardinales.",
            2,
            0,
            "La dirección cardinal hacia dónde se mueve el alfil (NESW).");

    public Alfil(Color color) {
        super("Alfil", "A", HABILIDAD_ALFIL, color, Tipo.BIOLOGICA, Tipo.TRANSPORTABLE);
    }

    @Override
    public boolean canMover(Tablero tablero, Point inicio, Point final_) {
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
    public boolean canComer(Tablero tablero, Point inicio, Point final_) {
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
    public Par<Boolean, String> canUsarHabilidad(Tablero tablero, Point inicio, Point final_, String informacionExtra) {
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
    public void habilidad(Tablero tablero, Point inicio, Point final_, String informacionExtra) {
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
    public List<Par<Point, Accion>> allAcciones(Tablero tablero, Point seleccionado) {
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

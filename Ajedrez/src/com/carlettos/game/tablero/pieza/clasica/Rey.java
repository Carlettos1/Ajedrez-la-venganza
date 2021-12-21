package com.carlettos.game.tablero.pieza.clasica;

import com.carlettos.game.core.Par;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import java.awt.Point;

public class Rey extends Pieza {
    
    private boolean seHaTeletransportado;
    public static final Habilidad HABILIDAD_REY = new Habilidad("Teletransportación",
            "Se teletransporta a cualquier casilla en un rango de 5",
            0,
            2,
            "Es solo de un solo uso y la casilla debe estar vacía.");
    
    public Rey(Color color) {
        super("Rey", "RE", HABILIDAD_REY, color, Tipo.BIOLOGICA, Tipo.INMUNE);
        this.seHaTeletransportado = false;
    }
    
    @Override
    public boolean canMover(Tablero tablero, Point inicio, Point final_) {
        if (tablero.getEscaque(final_).hasPieza()) {
            return false;
        }
        
        if (seHaMovidoEsteTurno()) {
            return false;
        }
        
        if (Math.abs(inicio.x - final_.x) > 1) {
            return false;
        }
        if (Math.abs(inicio.y - final_.y) > 1) {
            return false;
        }

        //TODO: que nada se lo pueda comer ¿? y verificar jaques
        return true;
    }
    
    @Override
    public boolean canComer(Tablero tablero, Point inicio, Point final_) {
        if (!tablero.getEscaque(final_).hasPieza()) {
            return false;
        }
        
        if (tablero.getEscaque(final_).getPieza().getColor().equals(getColor())) {
            return false;
        }
        
        if (seHaMovidoEsteTurno()) {
            return false;
        }
        
        if (Math.abs(inicio.x - final_.x) > 1) {
            return false;
        }
        if (Math.abs(inicio.y - final_.y) > 1) {
            return false;
        }

        //TODO: que nada se lo pueda comer ¿? y verificar jaques
        return true;
    }
    
    @Override
    public Par<Boolean, String> canUsarHabilidad(Tablero tablero, Point inicio, Point final_, String informacionExtra) {
        if (seHaTeletransportado) {
            return new Par(false, "Se ha teletransportado");
        }
        
        String[] info = informacionExtra.split(" ");
        if (info.length != 2) {
            return new Par(false, "Cantidad de información inválida");
        }
        try {
            int deltaX = Integer.parseInt(info[0]);
            int deltaY = Integer.parseInt(info[1]);
            
            if (Math.abs(deltaX) > 5 || Math.abs(deltaY) > 5) {
                return new Par(false, "No se puede mover más de 5 en cualquier dirección");
            }
            
            if (inicio.x + deltaX >= tablero.columnas
                    || inicio.x + deltaX < 0
                    || inicio.y + deltaY >= tablero.filas
                    || inicio.y + deltaY < 0) {
                return new Par(false, "No puede salirse de los límites del tablero");
            }
            
            if (tablero.getEscaque(inicio.x + deltaX, inicio.y + deltaY).hasPieza()) {
                return new Par(false, "Está ocupado");
            }
            
            return new Par(true, "Todo OK");
            
        } catch (NumberFormatException e) {
            System.out.println(e);
            return new Par(false, e.toString());
        }
    }
    
    @Override
    public void habilidad(Tablero tablero, Point inicio, Point final_, String informacionExtra) {
        seHaTeletransportado = true;
        int deltaX = Integer.parseInt(informacionExtra.split(" ")[0]);
        int deltaY = Integer.parseInt(informacionExtra.split(" ")[1]);
        
        tablero.getEscaque(inicio.x + deltaX, inicio.y + deltaY).setPieza(this);
        tablero.getEscaque(inicio).quitarPieza();
    }
}

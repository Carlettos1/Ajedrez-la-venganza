package com.carlettos.game.tablero.pieza.clasica;

import com.carlettos.game.core.Par;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import java.awt.Point;

public class Caballo extends Pieza {

    public static final Habilidad HABILIDAD_CABALLO = new Habilidad("Bajar Jinetes",
            "Invoca 2 peones, uno a cada lado del caballo (EW). Ambas casillas deben estar vacías",
            10,
            1,
            "No requiere información adicional");

    public Caballo(Color color) {
        super("Caballo", "C", HABILIDAD_CABALLO, color, Tipo.BIOLOGICA, Tipo.TRANSPORTABLE);
    }

    @Override
    public boolean canMover(Tablero tablero, Point inicio, Point final_) {
        if(tablero.getEscaque(final_).hasPieza()){
            return false;
        }
        
        if(seHaMovidoEsteTurno()){
            return false;
        }
        
        int deltaX = final_.x - inicio.x;
        int deltaY = final_.y - inicio.y;
        deltaX = Math.abs(deltaX);
        deltaY = Math.abs(deltaY);
        
        return (deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2);
    }

    @Override
    public boolean canComer(Tablero tablero, Point inicio, Point final_) {
        if(!tablero.getEscaque(final_).hasPieza()){
            return false;
        }
        if(tablero.getEscaque(final_).getPieza().getColor().equals(this.getColor())){
            return false;
        }
        if(seHaMovidoEsteTurno()){
            return false;
        }
        
        int deltaX = final_.x - inicio.x;
        int deltaY = final_.y - inicio.y;
        deltaX = Math.abs(deltaX);
        deltaY = Math.abs(deltaY);
        
        return (deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2);
    }

    @Override
    public Par<Boolean, String> canUsarHabilidad(Tablero tablero, Point inicio, Point final_, String informacionExtra) {
        if(this.getCdActual() > 0){
            return new Par(false, "Aún en CD");
        }

        if (this.seHaMovidoEsteTurno()) {
            return new Par(false, "Se ha movido este turno");
        }
        
        Point p1 = new Point(inicio.x + 1, inicio.y);
        Point p2 = new Point(inicio.x - 1, inicio.y);

        if(tablero.getEscaque(p1).hasPieza() || tablero.getEscaque(p2).hasPieza()){
            return new Par(false, "Espacios ocupados");
        }
        return new Par(true, "No hay piezas");
    }

    @Override
    public void habilidad(Tablero tablero, Point inicio, Point final_, String informacionExtra) {
        Point p1 = new Point(inicio.x + 1, inicio.y);
        Point p2 = new Point(inicio.x - 1, inicio.y);
        tablero.getEscaque(p1).setPieza(new Peon(this.getColor()));
        tablero.getEscaque(p2).setPieza(new Peon(this.getColor()));
        //TODO: cambiar cd y maná
    }
}

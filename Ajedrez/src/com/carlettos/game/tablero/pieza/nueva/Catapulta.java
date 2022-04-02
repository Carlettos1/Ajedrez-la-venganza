package com.carlettos.game.tablero.pieza.nueva;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.pieza.patron.accion.IMover;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronEstructuraMover;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import com.carlettos.game.util.MathHelper;

/**
 *
 * @author Ususario
 */
public class Catapulta extends Pieza implements IMover<PatronEstructuraMover> {

    public static final Habilidad<Catapulta> HABILIDAD_CATAPULTA = new HabilidadCatapulta<>();
    protected final PatronEstructuraMover patronMover;
    
    public Catapulta(Color color) {
        super("Catapulta", "CA", HABILIDAD_CATAPULTA, color, Tipo.ESTRUCTURA);
        patronMover = new PatronEstructuraMover() {};
    }
    
    @Override
    public ActionResult can(Accion accion, Tablero tablero, Point inicio, Point final_) {
        return switch(accion){
            case MOVER -> this.canMover(tablero, inicio, final_, patronMover);
            default -> ActionResult.FAIL;
        };    
    }
    
    public static class HabilidadCatapulta<P extends Pieza> extends Habilidad<P> {
        public HabilidadCatapulta() {
            super("Lanzar Pieza", 
                    "Lanza una pieza en una dirección.", 
                    5, 
                    0, """
                       (NESW) + un número, siguiendo la distribución:
                       7 8 9
                       4 C 6
                       1 2 3.
                       Ej: N 3.""");
        }

        @Override
        public ActionResult canUsar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            if (pieza.getCdActual() > 0) {
                return ActionResult.FAIL;
            }

            if (pieza.seHaMovidoEsteTurno()) {
                return ActionResult.FAIL;
            }

            if (informacionExtra == null) {
                return ActionResult.FAIL;
            }
            
            String[] info = informacionExtra.split(" ");
            if(info.length != 2){
                return ActionResult.FAIL;
            }
            switch (info[0]) {
                case "N", "S", "E", "W" -> {}
                default -> {return ActionResult.FAIL;}
            }
            int num;
            try {
                num = Integer.parseInt(info[1]);
            } catch (NumberFormatException e) {
                num = 0;
            }
            if(num >= 1 && num <= 9 && num != 5){
                return ActionResult.PASS;
            }
            return ActionResult.FAIL;
        }

        @Override
        public void usar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            String[] info = informacionExtra.split(" ");
            int num = Integer.parseInt(info[1]);
            Point posPieza;
            switch (num) {
                case 1 -> posPieza = new Point(inicio.x-1, inicio.y-1);
                case 2 -> posPieza = new Point(inicio.x, inicio.y-1);
                case 3 -> posPieza = new Point(inicio.x+1, inicio.y-1);
                case 4 -> posPieza = new Point(inicio.x-1, inicio.y);
                case 6 -> posPieza = new Point(inicio.x+1, inicio.y);
                case 7 -> posPieza = new Point(inicio.x-1, inicio.y+1);
                case 8 -> posPieza = new Point(inicio.x, inicio.y+1);
                case 9 -> posPieza = new Point(inicio.x+1, inicio.y+1);
                default -> throw new IllegalArgumentException("Lanzamiento de catapulta inválido");
            }
            int dirX = 0;
            int dirY = 0;
            switch (info[0]) {
                case "N" -> {dirX = 1;}
                case "S" -> {dirX = -1;}
                case "E" -> {dirY = 1;}
                case "W" -> {dirY = -1;}
            }
            if(dirX != 0){
                int x = dirX * 6 + inicio.x;
                x = MathHelper.clamp(0, tablero.columnas - 1, x);
                tablero.getEscaque(x, inicio.y).setPieza(tablero.getEscaque(posPieza).getPieza());
                tablero.quitarEntidad(posPieza);
            } else {
                int y = dirY * 6 + inicio.y;
                y = MathHelper.clamp(0, tablero.columnas - 1, y);
                tablero.getEscaque(inicio.x, y).setPieza(tablero.getEscaque(posPieza).getPieza());
                tablero.quitarEntidad(posPieza);
            }
        }
    }
}

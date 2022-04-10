package com.carlettos.game.tablero.pieza.nueva;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.pieza.patron.accion.IMover;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronEstructuraMover;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.habilidad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;

/**
 *
 * @author Carlettos
 */
public class Ariete extends Pieza implements IMover<PatronEstructuraMover> {
    
    public static final Habilidad<Ariete> HABILIDAD_ARIETE = new HabilidadAriete<>();
    protected final PatronEstructuraMover patronMover;
    
    public Ariete(Color color) {
        super("Ariete", "AR", HABILIDAD_ARIETE, color, Tipo.ESTRUCTURA);
        patronMover = new PatronEstructuraMover() {};
    }
    
    @Override
    public ActionResult can(Accion accion, Tablero tablero, Point inicio, Point final_) {
        return switch(accion){
            case MOVER -> this.canMover(tablero, inicio, final_, patronMover);
            default -> ActionResult.FAIL;
        };
    }
    
    public static class HabilidadAriete<P extends Pieza> extends Habilidad<P> {
        public HabilidadAriete() {
            super("Carga de Ariete", 
                    "El ariete carga en una dirección hasta alcanzar la primera "
                            + "pieza, luego procede a avanzar, comiendo lo que atraviese, "
                            + "dependiendo de cuanto haya cargado "
                            + "(avanza 1 escaque por cada 5 que carge, +1 de base).", 
                    4, 
                    0, 
                    "La dirección cardinal hacia dónde se mueve el alfil (NESW).");
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
            
            return switch (informacionExtra) {
                case "N", "E", "S", "W" -> ActionResult.PASS;
                default -> ActionResult.FAIL;
            };
        }

        @Override
        public void usar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            int dirX = 0;
            int dirY = 0;
            int carga = 0;
            switch (informacionExtra) {
                case "N" -> dirY = 1;
                case "E" -> dirX = 1;
                case "S" -> dirY = -1;
                case "W" -> dirX = -1;
            }
            if(dirX != 0) {
                for(int x = inicio.x + dirX;;x += dirX){
                    if(tablero.getEscaque(x, inicio.y).hasPieza()) {
                        for (int dx = 0; dx < carga/5; dx++) {
                            tablero.quitarPieza(x + dx, inicio.y);
                            tablero.getEscaque(x + dx, inicio.y).setPieza(pieza);
                        }
                        break;
                    } else {
                        carga++;
                    }
                }
            } else if (dirY != 0) {
                for(int y = inicio.y + dirY;;y += dirY){
                    if(tablero.getEscaque(inicio.x, y).hasPieza()) {
                        for (int dy = 0; dy < carga/5; dy++) {
                            tablero.quitarPieza(inicio.x, y + dy);
                            tablero.getEscaque(inicio.x, y + dy).setPieza(pieza);
                        }
                        break;
                    } else {
                        carga++;
                    }
                }
            }
            //TODO: cambiar cd
        }
    }
}

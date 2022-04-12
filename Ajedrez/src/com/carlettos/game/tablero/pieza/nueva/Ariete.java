package com.carlettos.game.tablero.pieza.nueva;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Direction;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.AbstractTablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.pieza.patron.accion.IMover;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronEstructuraMover;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.habilidad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import com.carlettos.game.tablero.propiedad.habilidad.InfoNESW;
import com.carlettos.game.tablero.propiedad.habilidad.InfoGetter.HabilidadNESW;

/**
 *
 * @author Carlettos
 */
public class Ariete extends Pieza implements IMover<PatronEstructuraMover> {
    
    public static final Habilidad<Ariete, Direction, InfoNESW> HABILIDAD_ARIETE = new HabilidadAriete<>();
    protected final PatronEstructuraMover patronMover;
    
    public Ariete(Color color) {
        super("Ariete", "AR", HABILIDAD_ARIETE, color, Tipo.ESTRUCTURA);
        patronMover = new PatronEstructuraMover() {};
    }
    
    @Override
    public ActionResult can(Accion accion, AbstractTablero tablero, Point inicio, Point final_) {
        return switch(accion){
            case MOVER -> this.canMover(tablero, inicio, final_, patronMover);
            default -> ActionResult.FAIL;
        };
    }
    
    public static class HabilidadAriete<P extends Pieza> extends Habilidad<P, Direction, InfoNESW> implements HabilidadNESW{
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
        public ActionResult canUsar(AbstractTablero tablero, P pieza, Point inicio, InfoNESW info) {
            if (!this.commonCanUsar(tablero, pieza)) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        }

        @Override
        public void usar(AbstractTablero tablero, P pieza, Point inicio, InfoNESW info) {
            int carga = 1;
            tablero.getEscaque(inicio).quitarPieza();
            if(info.isAxis(Direction.Axis.EW)) {
                for(int x = inicio.x + info.getSign();;x += info.getSign()){
                    if(tablero.isOutOfBorder(new Point(x, inicio.y))){
                        tablero.getEscaque(x - info.getSign(), inicio.y).setPieza(pieza);
                        break;
                    }
                    if(tablero.getEscaque(x, inicio.y).hasPieza()) {
                        for (int dx = 0; dx < carga/5 + 1; dx++) {
                            if(!tablero.isOutOfBorder(new Point(x + dx, inicio.y))){
                                tablero.getEscaque(x + dx, inicio.y).setPieza(pieza);
                                tablero.getEscaque(x + dx - info.getSign(), inicio.y).quitarPieza();
                            }
                        }
                        break;
                    } else {
                        carga++;
                    }
                }
            } else {
                for(int y = inicio.y + info.getSign();;y += info.getSign()){
                    if(tablero.isOutOfBorder(new Point(inicio.x, y))){
                        tablero.getEscaque(inicio.x, y - info.getSign()).setPieza(pieza);
                        break;
                    }
                    if(tablero.getEscaque(inicio.x, y).hasPieza()) {
                        for (int dy = 0; dy < carga/5 + 1; dy++) {
                            if(!tablero.isOutOfBorder(new Point(inicio.x, y + dy))){
                                tablero.getEscaque(inicio.x, y + dy).setPieza(pieza);
                                tablero.getEscaque(inicio.x, y + dy - info.getSign()).quitarPieza();
                            }
                        }
                        break;
                    } else {
                        carga++;
                    }
                }
            }
            this.commonUsar(tablero, pieza);
        }
    }
}

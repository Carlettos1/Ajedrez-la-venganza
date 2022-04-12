package com.carlettos.game.visual;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.Constantes;
import com.carlettos.game.tablero.Escaque;
import com.carlettos.game.tablero.propiedad.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * Es el escaque de un tablero de ajedrez pero extendiendo de JComponent para
 * poder utilizarlo de forma visual, por lo mismo, solo hace funciones de
 * display y se desentiende de la parte lógica de un escaque.
 *
 * @author Carlos
 */
public class EscaqueVisual extends Component {

    private final Escaque escaque;
    private final boolean isPar;
    private final List<Accion> acciones;

    /**
     * Crea el Escaque de una forma visual utilizando al Escaque en forma de
     * ayuda.
     *
     * @param escaque escaque que va a representar.
     */
    public EscaqueVisual(Escaque escaque) {
        this.acciones = new ArrayList<>();
        this.escaque = escaque;
        isPar = (escaque.getPos().x + escaque.getPos().y) % 2 == 0;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Constantes.TAMAÑO_CASILLA, Constantes.TAMAÑO_CASILLA);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor((isPar ? Constantes.COLOR2 : Constantes.COLOR1).getAWT());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor((!isPar ? Constantes.COLOR2 : Constantes.COLOR1).getAWT());
        //TODO: cambiar String por una imagen.
        g.drawString(escaque.getPieza().abreviacion, 2, 20);
        g.drawString(escaque.isEmpty() ? "" : escaque.getPieza().getColor().name(), 2, 30);

        double w = Constantes.PORCENTAJE * getWidth();
        double h = Constantes.PORCENTAJE * getHeight();
        double x = (getWidth() - w) / 2D;
        double y = (getHeight() - h) / 2D;
        switch (this.acciones.size()) {
            case 0:
                break;
            case 1:
                g.setColor(acciones.get(0).getColor().getAWT());
                g.fillOval((int) x, (int) y, (int) w, (int) h);
                break;
            case 2:
                g.setColor(acciones.get(0).getColor().getAWT());
                g.fillOval((int) (x / 2D), (int) (y / 2D),
                        (int) (w), (int) (h));
                
                g.setColor(acciones.get(1).getColor().getAWT());
                g.fillOval((int) (x * 1.5D), (int) (y * 1.5D),
                        (int) (w), (int) (h));
                break;
            case 3:
                w = Constantes.PORCENTAJE_PEQUEÑO * getWidth();
                h = Constantes.PORCENTAJE_PEQUEÑO * getHeight();
                x = (getWidth() - w) / 2D;
                y = (getHeight() - h) / 2D;
                
                g.setColor(acciones.get(0).getColor().getAWT());
                g.fillOval((int) (x), (int) (y / 2D),
                        (int) (w), (int) (h));
                
                g.setColor(acciones.get(1).getColor().getAWT());
                g.fillOval((int) (x * 1.5D), (int) (y * 1.5D),
                        (int) (w), (int) (h));
                
                g.setColor(acciones.get(2).getColor().getAWT());
                g.fillOval((int) (x / 2D), (int) (y * 1.5D),
                        (int) (w), (int) (h));
                break;
            case 4:
                w = Constantes.PORCENTAJE_PEQUEÑITO * getWidth();
                h = Constantes.PORCENTAJE_PEQUEÑITO * getHeight();
                x = (getWidth() - w) / 2D;
                y = (getHeight() - h) / 2D;
                
                g.setColor(acciones.get(0).getColor().getAWT());
                g.fillOval((int) (x / 3D), (int) (y / 3D),
                        (int) (w), (int) (h));
                
                g.setColor(acciones.get(1).getColor().getAWT());
                g.fillOval((int) (x * 5/3D), (int) (y / 3D),
                        (int) (w), (int) (h));
                
                g.setColor(acciones.get(2).getColor().getAWT());
                g.fillOval((int) (x / 3D), (int) (y * 5/3D),
                        (int) (w), (int) (h));
                
                g.setColor(acciones.get(3).getColor().getAWT());
                g.fillOval((int) (x * 5/3D), (int) (y * 5/3D),
                        (int) (w), (int) (h));
                break;
            default:
                System.err.println("Cantidad de acciones: " + this.acciones.size() + ", no manejada");
        }
    }

    /**
     * Méthodo para establecer las acciones del escaque.
     *
     * @param accion accion a añadir.
     *
     * @see Accion
     */
    public void setAccion(Accion accion) {
        this.acciones.add(accion);
        repaint();
    }

    public void removeAccion(Accion accion) {
        if (this.acciones.contains(accion)) {
            this.acciones.remove(accion);
            repaint();
        }
    }

    /**
     * Elimina todas las acciones.
     */
    public void offAccion() {
        this.acciones.clear();
        repaint();
    }

    public Escaque getEscaque() {
        return escaque;
    }
}

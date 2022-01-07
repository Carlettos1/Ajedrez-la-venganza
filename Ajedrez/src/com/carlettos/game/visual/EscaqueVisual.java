package com.carlettos.game.visual;

import com.carlettos.game.core.Constantes;
import com.carlettos.game.tablero.Escaque;
import com.carlettos.game.tablero.propiedad.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;


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
    private boolean hasAccion;
    private Color colorAccion;

    /**
     * Crea el Escaque de una forma visual utilizando al Escaque en forma de
     * ayuda.
     *
     * @param escaque escaque que va a representar.
     */
    public EscaqueVisual(Escaque escaque) {
        this.escaque = escaque;
        isPar = (escaque.getLocalizacion().x + escaque.getLocalizacion().y) % 2 == 0;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Constantes.TAMAÑO_CASILLA, Constantes.TAMAÑO_CASILLA);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor((isPar ? Constantes.COLOR2 : Constantes.COLOR1).getColor());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor((!isPar ? Constantes.COLOR2 : Constantes.COLOR1).getColor());
        //TODO: cambiar String por una imagen.
        g.drawString(escaque.getPieza().abreviacion, 2, 20);
        g.drawString(escaque.isEmpty() ? "" : escaque.getPieza().getColor().name(), 2, 30);
        if (hasAccion) {
            g.setColor(colorAccion.getColor());
            g.fillOval((int) (Constantes.PORCENTAJE * getWidth()),
                    (int) (Constantes.PORCENTAJE * getHeight()),
                    (int) (Constantes.PORCENTAJE * getWidth()),
                    (int) (Constantes.PORCENTAJE * getWidth()));
        }
    }

    /**
     * Méthodo para saber si el escaque tiene acción o no. No es el escaque en
     * donde está situada la pieza que hace la acción, sino que es el escaque en
     * dónde ocurrirá la acción. Sirve para que el escaque se pinte.
     *
     * @param hasAccion true si tiene acción, false si no.
     * @param colorAccion color de la acción. Utilizar el enum para eso.
     *
     * @see Accion
     */
    public void setHasAccion(boolean hasAccion, Color colorAccion) {
        this.hasAccion = hasAccion;
        this.colorAccion = colorAccion;
        repaint();
    }

    /**
     * Elimina el flag de acción para que no se pinte.
     */
    public void offAccion() {
        setHasAccion(false, null);
    }

    public Escaque getEscaque() {
        return escaque;
    }
}

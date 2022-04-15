package com.carlettos.game.visual;

import com.carlettos.game.core.Constants;
import com.carlettos.game.board.card.Card;
import com.carlettos.game.board.property.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author Carlos
 */
public class CartaVisual extends JComponent {
    private boolean seleccionado;
    private final Color color;
    private final Card carta;

    public CartaVisual(Color color, Card carta) {
        this.seleccionado = false;
        this.color = color;
        this.carta = carta;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Constants.TAMAÑO_CARTA_X, Constants.TAMAÑO_CARTA_Y);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(color.getAWT());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(color.getColorNegativo());
        g.drawString(carta.getNombre(), 2, 20);
        g.drawString(carta.getDescripcion(), 2, 50);
        //TODO que se vea bonito y mostrar más datos de la carta
    }

    public Card getCarta() {
        return carta;
    }

    public Color getColor() {
        return color;
    }
}

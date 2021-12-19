package ajedrez.carlettos.src.visual;

import ajedrez.carlettos.src.carta.base.Carta;
import ajedrez.carlettos.src.util.Constantes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.util.Arrays;
import javax.swing.JComponent;

public class EscaqueCartaVisual extends JComponent {
    private boolean seleccionado;
    private final Color color;
    private final Carta carta;

    public EscaqueCartaVisual(Color color, Carta carta) {
        this.seleccionado = false;
        this.color = color;
        this.carta = carta;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Constantes.TAMAÑO_CARTA_X, Constantes.TAMAÑO_CARTA_Y);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue()));
        g.drawString(carta.getNombre(), 2, 20);
        g.drawString(carta.getDescripcion(), 2, 50);
        //TODO que se vea bonito y mostrar más datos de la carta
    }

    public Carta getCarta() {
        return carta;
    }

    public Color getColor() {
        return color;
    }
}

package com.carlettos.game.display.board;

import com.carlettos.game.board.card.Card;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.core.helper.ConfigHelper;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author Carlos
 */
public class CardDisplay extends JComponent {
    private boolean selected;
    private final Color color;
    private final Card card;

    public CardDisplay(Color color, Card carta) {
        this.selected = false;
        this.color = color;
        this.card = carta;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(ConfigHelper.getInstance().getIntConfig("card_width"), ConfigHelper.getInstance().getIntConfig("card_height"));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(color.getAWT());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(color.getColorNegativo());
        g.drawString(card.getName(), 2, 20);
        g.drawString(card.getDescription(), 2, 50);
        //TODO que se vea bonito y mostrar más datos de la carta
    }

    public Card getCard() {
        return card;
    }

    public Color getColor() {
        return color;
    }
}

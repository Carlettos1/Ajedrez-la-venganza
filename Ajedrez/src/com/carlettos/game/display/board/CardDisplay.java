package com.carlettos.game.display.board;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.carlettos.game.display.listeners.MouseCard;
import com.carlettos.game.gameplay.card.Card;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.helper.ConfigHelper;
import com.carlettos.game.util.helper.DisplayHelper;

/**
 *
 * @author Carlos
 */
public class CardDisplay extends JPanel {
    private static final long serialVersionUID = 6871650869282376214L;
    protected boolean selected;
    protected final Color color;
    protected final transient Card card;
    protected JLabel name;
    protected JLabel desc;
    protected JLabel mana;

    public CardDisplay(Color color, Card carta, boolean hasListener) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setBackground(color.getAWT());

        this.selected = false;
        this.color = color;
        this.card = carta;

        this.name = new JLabel(card.getData().getName());
        this.desc = new JLabel("<html>" + card.getData().getDescription() + "</html>");
        this.mana = new JLabel(ConfigHelper.getManaSymbol().repeat(card.getData().manaCost()));

        this.name.setForeground(color.getNegativeColor());
        this.desc.setForeground(color.getNegativeColor());
        this.mana.setForeground(ConfigHelper.getColorMana().getAWT());

        this.name.setFont(DisplayHelper.FONT_12);
        this.desc.setFont(DisplayHelper.FONT_10);
        this.mana.setFont(DisplayHelper.FONT_6);

        this.name.setToolTipText(card.getData().getName());
        this.desc.setToolTipText(card.getData().getDescription());
        this.mana.setToolTipText("Cost:" + card.getData().manaCost());

        add(this.mana);
        add(this.name);
        add(Box.createVerticalGlue());
        add(this.desc);
        add(Box.createVerticalGlue());

        if (hasListener) {
            this.addMouseListener(MouseCard.get());
            mana.addMouseListener(MouseCard.get());
            name.addMouseListener(MouseCard.get());
            desc.addMouseListener(MouseCard.get());
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(ConfigHelper.getCardWidth(), ConfigHelper.getCardHeight());
    }

    public Card getCard() {
        return card;
    }

    public Color getColor() {
        return color;
    }
}

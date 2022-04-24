package com.carlettos.game.display.board;

import com.carlettos.game.board.card.Card;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.core.helper.ConfigHelper;
import com.carlettos.game.core.helper.DisplayHelper;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Carlos
 */
public class CardDisplay extends JPanel {
    protected boolean selected;
    protected final Color color;
    protected final Card card;
    protected JLabel name;
    protected JLabel desc;
    protected JLabel mana;

    public CardDisplay(Color color, Card carta) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setBackground(color.getAWT());
        
        this.selected = false;
        this.color = color;
        this.card = carta;
        
        this.name = new JLabel(card.getName());
        this.desc = new JLabel("<html>" + card.getDescription() + "</html>");
        this.mana = new JLabel(ConfigHelper.getStringConfig("mana_symbol").repeat(card.getCost()));
        
        this.name.setForeground(color.getNegativeColor());
        this.desc.setForeground(color.getNegativeColor());
        this.mana.setForeground(Color.values()[ConfigHelper.get().getInt("color_mana_id")].getAWT());
        
        this.name.setFont(DisplayHelper.getFont().deriveFont(12f));
        this.desc.setFont(DisplayHelper.getFont());
        this.mana.setFont(DisplayHelper.getFont().deriveFont(6f));
        
        this.name.setToolTipText(card.getName());
        this.desc.setToolTipText(card.getDescription());
        this.mana.setToolTipText("Cost:" + card.getCost());
        
        add(this.mana);
        add(this.name);
        add(Box.createVerticalGlue());
        add(this.desc);
        add(Box.createVerticalGlue());
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(ConfigHelper.get().getInt("card_width"), ConfigHelper.get().getInt("card_height"));
    }

    public Card getCard() {
        return card;
    }

    public Color getColor() {
        return color;
    }
}

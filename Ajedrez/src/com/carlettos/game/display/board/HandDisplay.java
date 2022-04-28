package com.carlettos.game.display.board;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ItemEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.carlettos.game.board.clock.Clock;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.helper.ConfigHelper;

/**
 *
 * @author Carlos
 */
public class HandDisplay extends JPanel {
	private static final long serialVersionUID = 6749397947510998299L;
	private final JPanel playersPanel;

    public HandDisplay(Clock clock) throws HeadlessException {
        super(new BorderLayout(0, 10));
        this.playersPanel = new JPanel(new CardLayout());
        for (Player player : clock.getPlayers()) {
            playersPanel.add(new PlayerPanel(player), player.toString());
        }
        JComboBox<Player> cb = new JComboBox<>(clock.getPlayers());
        cb.setEditable(false);
        cb.addItemListener((ItemEvent e) -> {
            CardLayout cl = (CardLayout) playersPanel.getLayout();
            cl.show(playersPanel, ((Player) e.getItem()).toString());
        });

        add(cb, BorderLayout.PAGE_START);
        add(playersPanel, BorderLayout.CENTER);
    }

    public void redo() {
        for (Component component : playersPanel.getComponents()) {
            if (component instanceof PlayerPanel pp) {
                pp.rehacer();
            }
        }
    }

    private static final class PlayerPanel extends JPanel {

		private static final long serialVersionUID = 4861537177427017355L;
		
		//todo: transient?
		private final transient Player player;

        public PlayerPanel(Player player) {
            super(new GridLayout(ConfigHelper.getCardsPerColumn(),
                    ConfigHelper.getCardsPerRow(), 10, 10));
            this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            this.player = player;
            rehacer();
        }

        public void rehacer() {
            removeAll();
            player.getHand().forEach(card -> add(new CardDisplay(player.getColor(), card)));
            revalidate();
            repaint();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(ConfigHelper.getCardsPerRow() * ConfigHelper.getCardWidth(),
                    ConfigHelper.getCardsPerColumn() * ConfigHelper.getCardHeight());
        }
    }
}

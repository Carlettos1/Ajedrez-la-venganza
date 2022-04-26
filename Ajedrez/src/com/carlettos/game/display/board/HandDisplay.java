package com.carlettos.game.display.board;

import com.carlettos.game.board.clock.Clock;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.helper.ConfigHelper;
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

/**
 *
 * @author Carlos
 */
public class HandDisplay extends JPanel {

    private final JPanel playersPanel;

    public HandDisplay(Clock clock) throws HeadlessException {
        super(new BorderLayout(0, 10));
        this.playersPanel = new JPanel(new CardLayout());
        for (Player player : clock.getPlayers()) {
            playersPanel.add(new PlayerPanel(player), player.toString());
        }
        JComboBox cb = new JComboBox(clock.getPlayers());
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
            if (component instanceof PlayerPanel playerPanel_) {
                playerPanel_.rehacer();
            }
        }
    }

    private static final class PlayerPanel extends JPanel {

        private final Player player;

        public PlayerPanel(Player player) {
            super(new GridLayout(ConfigHelper.getCardsPerColumn(),
                    ConfigHelper.getCardsPerRow(), 10, 10));
            this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            this.player = player;
            rehacer();
        }

        public void rehacer() {
            removeAll();
            player.getHand().forEach((carta) -> {
                add(new CardDisplay(player.getColor(), carta));
            });
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

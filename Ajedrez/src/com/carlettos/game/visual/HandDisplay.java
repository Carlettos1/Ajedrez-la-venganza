package com.carlettos.game.visual;

import com.carlettos.game.core.Constants;
import com.carlettos.game.input.MouseCard;
import com.carlettos.game.board.player.Player;
import com.carlettos.game.board.manager.clock.Clock;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ItemEvent;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 *
 * @author Carlos
 */
public class HandDisplay extends JPanel {

    private final JPanel panelesJugadores;

    public HandDisplay(Clock reloj) throws HeadlessException {
        super(new BorderLayout(0, 10));
        this.panelesJugadores = new JPanel(new CardLayout());
        for (Player player : reloj.getPlayers()) {
            panelesJugadores.add(new PanelJugador(player), player.toString());
        }
        JComboBox cb = new JComboBox(reloj.getPlayers());
        cb.setEditable(false);
        cb.addItemListener((ItemEvent e) -> {
            CardLayout cl = (CardLayout) panelesJugadores.getLayout();
            cl.show(panelesJugadores, ((Player) e.getItem()).toString());
        });

        add(cb, BorderLayout.PAGE_START);
        add(panelesJugadores, BorderLayout.CENTER);
    }

    public void rehacer() {
        for (Component component : panelesJugadores.getComponents()) {
            if (component instanceof PanelJugador panelJugador) {
                panelJugador.rehacer();
            }
        }
    }

    private static final class PanelJugador extends JPanel {

        private final Player jugador;

        public PanelJugador(Player jugador) {
            super(new GridLayout(Constants.CARTAS_Y, Constants.CARTAS_X, 10, 10));
            this.jugador = jugador;
            jugador.getHand().getCartas().forEach((carta) -> {
                CardDisplay ecv = new CardDisplay(jugador.getColor(), carta);
                ecv.addMouseListener(MouseCard.get());
                add(ecv);
            });
        }

        public void rehacer() {
            removeAll();
            jugador.getHand().getCartas().forEach((carta) -> {
                CardDisplay ecv = new CardDisplay(jugador.getColor(), carta);
                ecv.addMouseListener(MouseCard.get());
                add(ecv);
            });
            revalidate();
            repaint();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(Constants.CARTAS_X * Constants.TAMAÑO_CARTA_X,
                    Constants.CARTAS_Y * Constants.TAMAÑO_CARTA_Y);
        }
    }
}

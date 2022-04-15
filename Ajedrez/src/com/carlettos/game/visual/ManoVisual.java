package com.carlettos.game.visual;

import com.carlettos.game.core.Constants;
import com.carlettos.game.input.MouseCarta;
import com.carlettos.game.board.player.Jugador;
import com.carlettos.game.board.manager.Clock;
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
public class ManoVisual extends JPanel {

    private final Clock reloj; //TODO: no usar reloj
    private final JPanel panelesJugadores;

    public ManoVisual(Clock reloj) throws HeadlessException {
        super(new BorderLayout(0, 10));
        this.reloj = reloj;
        this.panelesJugadores = new JPanel(new CardLayout());
        reloj.getJugadores().forEach((jugador) -> {
            panelesJugadores.add(new PanelJugador(jugador), jugador.toString());
        });
        JComboBox cb = new JComboBox(reloj.getJugadores().toArray(new Jugador[0]));
        cb.setEditable(false);
        cb.addItemListener((ItemEvent e) -> {
            CardLayout cl = (CardLayout) panelesJugadores.getLayout();
            cl.show(panelesJugadores, ((Jugador) e.getItem()).toString());
        });

        add(cb, BorderLayout.PAGE_START);
        add(panelesJugadores, BorderLayout.CENTER);
    }

    public void rehacer() {
        for (Component component : panelesJugadores.getComponents()) {
            if (component instanceof PanelJugador) {
                PanelJugador panelJugador = (PanelJugador) component;
                panelJugador.rehacer();
            }
        }
    }

    private static final class PanelJugador extends JPanel {

        private final Jugador jugador;

        public PanelJugador(Jugador jugador) {
            super(new GridLayout(Constants.CARTAS_Y, Constants.CARTAS_X, 10, 10));
            this.jugador = jugador;
            jugador.getMano().getCartas().forEach((carta) -> {
                CartaVisual ecv = new CartaVisual(jugador.getColor(), carta);
                ecv.addMouseListener(MouseCarta.get());
                add(ecv);
            });
        }

        public void rehacer() {
            removeAll();
            jugador.getMano().getCartas().forEach((carta) -> {
                CartaVisual ecv = new CartaVisual(jugador.getColor(), carta);
                ecv.addMouseListener(MouseCarta.get());
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

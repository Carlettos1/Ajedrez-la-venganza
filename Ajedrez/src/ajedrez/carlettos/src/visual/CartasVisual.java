package ajedrez.carlettos.src.visual;

import ajedrez.carlettos.src.tablero.jugador.Jugador;
import ajedrez.carlettos.src.tablero.reloj.RelojManager;
import ajedrez.carlettos.src.util.Constantes;
import ajedrez.carlettos.src.util.MouseCarta;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class CartasVisual extends JPanel {

    private final RelojManager reloj;
    private final JPanel panelesJugadores;

    public CartasVisual(RelojManager reloj) throws HeadlessException {
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
            super(new GridLayout(Constantes.CARTAS_Y, Constantes.CARTAS_X, 10, 10));
            this.jugador = jugador;
            jugador.getMano().getCartas().forEach((carta) -> {
                EscaqueCartaVisual ecv = new EscaqueCartaVisual(jugador.getColor().getAwtColor(), carta);
                ecv.addMouseListener(MouseCarta.LISTENER);
                add(ecv);
            });
        }

        public void rehacer() {
            removeAll();
            jugador.getMano().getCartas().forEach((carta) -> {
                EscaqueCartaVisual ecv = new EscaqueCartaVisual(jugador.getColor().getAwtColor(), carta);
                ecv.addMouseListener(MouseCarta.LISTENER);
                add(ecv);
            });
            revalidate();
            repaint();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(Constantes.CARTAS_X * Constantes.TAMAÑO_CARTA_X,
                    Constantes.CARTAS_Y * Constantes.TAMAÑO_CARTA_Y);
        }
    }
}

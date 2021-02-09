package ajedrez.carlettos.src.visual;

import ajedrez.carlettos.src.tablero.reloj.Evento;
import ajedrez.carlettos.src.tablero.reloj.RelojManager;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RelojVisual extends JPanel {

    private final RelojManager reloj;
    private StringBuilder ordenJugadores;
    private StringBuilder ordenEventos;
    private StringBuilder turnoActual;
    private final JLabel jugadoresLabel;
    private final JLabel eventosLabel;
    private final JLabel turnoLabel;
    private final JButton boton;

    public RelojVisual(RelojManager reloj) {
        super(new BorderLayout());
        this.reloj = reloj;
        actualizarTextos();
        this.jugadoresLabel = new JLabel(ordenJugadores.toString());
        this.eventosLabel = new JLabel(ordenEventos.toString());
        this.turnoLabel = new JLabel(turnoActual.toString());
        this.boton = new JButton("Avanzar turno");
        setup();
    }

    protected void setup() {
        add(jugadoresLabel, BorderLayout.PAGE_START);
        add(eventosLabel, BorderLayout.CENTER);
        add(turnoLabel, BorderLayout.PAGE_END);
        boton.addActionListener((ActionEvent e) -> {
            reloj.terminarTurno();
            Container container = this;
            while (container.getParent() != null) {
                container = container.getParent();
            }
            container.repaint();
        });
        add(boton, BorderLayout.EAST);
    }

    public void actualizarTextos() {
        ordenJugadores = new StringBuilder("El orden de los jugadores es: ");
        reloj.getJugadores().forEach((jugador) -> {
            ordenJugadores.append(jugador.getColor()).append(", ");
        });
        ordenJugadores.delete(ordenJugadores.lastIndexOf(", "), ordenJugadores.length());
        ordenJugadores.append(".");

        ordenEventos = new StringBuilder("<html>Evento(s) más próximos: <br/>");
        List<Evento> eventos = reloj.getEventosOrdenados();
        for (Evento evento : Arrays.asList(eventos.stream().filter((evento) -> {
            return evento.turnos == eventos.get(0).turnos;
        }).toArray(Evento[]::new))) {
            ordenEventos.append(evento).append("<br/>");

        }
        ordenEventos.append("</html>");

        turnoActual = new StringBuilder("Es el turno del jugador ");
        turnoActual.append(reloj.turnoDe().getColor()).append('.');
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        actualizarTextos();
        jugadoresLabel.setText(ordenJugadores.toString());
        eventosLabel.setText(ordenEventos.toString());
        turnoLabel.setText(turnoActual.toString());
    }

    public RelojManager getReloj() {
        return reloj;
    }
}

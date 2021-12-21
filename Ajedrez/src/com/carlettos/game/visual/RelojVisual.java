package com.carlettos.game.visual;

import com.carlettos.game.core.Evento;
import com.carlettos.game.core.Par;
import com.carlettos.game.input.MousePieza;
import com.carlettos.game.tablero.Escaque;
import com.carlettos.game.tablero.manager.Reloj;
import com.carlettos.game.tablero.pieza.Vacia;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Carlos
 */
public class RelojVisual extends JPanel{

    private final Reloj reloj;
    private StringBuilder ordenJugadores;
    private StringBuilder ordenEventos;
    private StringBuilder turnoActual;
    private final JLabel jugadoresLabel;
    private final JLabel eventosLabel;
    private final JLabel turnoLabel;
    private final JButton turno;
    private final JButton habilidad;

    public RelojVisual(Reloj reloj) {
        super(new BorderLayout());
        this.reloj = reloj;
        actualizarTextos();
        this.jugadoresLabel = new JLabel(ordenJugadores.toString());
        this.eventosLabel = new JLabel(ordenEventos.toString());
        this.turnoLabel = new JLabel(turnoActual.toString());
        this.turno = new JButton("Avanzar turno");
        this.habilidad = new JButton("Usar Habilidad");
        setup();
    }

    protected void setup() {
        add(jugadoresLabel, BorderLayout.PAGE_START);
        add(eventosLabel, BorderLayout.CENTER);
        add(turnoLabel, BorderLayout.PAGE_END);
        turno.addActionListener((ActionEvent e) -> {
            reloj.terminarTurno();
            Container container = this;
            while (container.getParent() != null) {
                container = container.getParent();
            }
            container.repaint();
            if (container instanceof TableroVisual) {
                TableroVisual tv = (TableroVisual) container;
                tv.offAll();
            }
        });
        habilidad.addActionListener((e) -> {
            if (MousePieza.LISTENER.seleccionado == null) {
                return;
            }
            Container container = this;
            while (container.getParent() != null) {
                container = container.getParent();
            }
            if (container instanceof TableroVisual) {
                TableroVisual tv = (TableroVisual) container;
                System.out.println(tv);
                String infoHabilidad = JOptionPane.showInputDialog(this,
                        "Escribir datos de la habilidad.",
                        "Habilidad",
                        JOptionPane.QUESTION_MESSAGE);
                Escaque escaque = MousePieza.LISTENER.seleccionado.getEscaque();
                Par<Boolean, String> can = escaque.getPieza().canUsarHabilidad(tv.getTablero(),
                        escaque.getLocalizacion(),
                        escaque.getLocalizacion(),
                        infoHabilidad);
                if (can.x) {
                    escaque.getPieza().habilidad(tv.getTablero(),
                            escaque.getLocalizacion(),
                            escaque.getLocalizacion(),
                            infoHabilidad);
                    reloj.movimiento();
                    MousePieza.LISTENER.seleccionado = null;
                    tv.offAll();
                } else {
                    System.out.println(can.y);
                }
            }
        });
        JPanel temp = new JPanel(new BorderLayout(0, 10));
        temp.add(turno, BorderLayout.NORTH);
        temp.add(habilidad, BorderLayout.SOUTH);
        add(temp, BorderLayout.EAST);
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
        for (Evento evento : (eventos.stream().filter((evento) -> {
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

    public Reloj getReloj() {
        return reloj;
    }
}

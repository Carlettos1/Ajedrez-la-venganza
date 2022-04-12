package com.carlettos.game.visual;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Direction;
import com.carlettos.game.core.Evento;
import com.carlettos.game.core.Par;
import com.carlettos.game.core.Point;
import com.carlettos.game.input.MousePieza;
import com.carlettos.game.tablero.Escaque;
import com.carlettos.game.tablero.manager.Reloj;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.propiedad.habilidad.Info;
import com.carlettos.game.tablero.propiedad.habilidad.InfoInteger;
import com.carlettos.game.tablero.propiedad.habilidad.InfoNESW;
import com.carlettos.game.tablero.propiedad.habilidad.InfoNinguna;
import com.carlettos.game.tablero.propiedad.habilidad.InfoPieza;
import com.carlettos.game.tablero.propiedad.habilidad.InfoPoint;
import com.carlettos.game.tablero.propiedad.habilidad.InfoString;
import com.carlettos.game.visual.info.InfoVisual;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
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

    //TODO: listeners por favor
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
            if (MousePieza.get().seleccionado == null) {
                return;
            }
            Container container = this;
            while (container.getParent() != null) {
                container = container.getParent();
            }
            if (container instanceof TableroVisual tv) {
                Escaque escaque = MousePieza.get().seleccionado.getEscaque();
                Info infoHabilidad = escaque.getPieza().getHabilidad().getInfoHabilidad();
                
                Object[] valores = escaque.getPieza().getHabilidad().getAllValoresPosibles(tv.getTablero(), escaque.getPos());
                if (infoHabilidad instanceof InfoNinguna) {
                    valores = new String[]{"Usar"};
                }
                int i = InfoVisual.showOptions(tv.getTablero(), escaque.getPos(), valores);
                if(i == -1){
                    return;
                }
                
                Object valor = valores[i];
                Info infoUsada = new InfoNinguna();
                ActionResult ar;
                if (infoHabilidad instanceof InfoNinguna) {
                    ar = escaque.getPieza().getHabilidad().canUsar(tv.getTablero(), escaque.getPieza(), escaque.getPos(), infoUsada = new InfoNinguna());
                } else if(infoHabilidad instanceof InfoInteger){
                    ar = escaque.getPieza().getHabilidad().canUsar(tv.getTablero(), escaque.getPieza(), escaque.getPos(), infoUsada = new InfoInteger((Integer) valor));
                } else if(infoHabilidad instanceof InfoNESW){
                    ar = escaque.getPieza().getHabilidad().canUsar(tv.getTablero(), escaque.getPieza(), escaque.getPos(), infoUsada = new InfoNESW((Direction) valor));
                } else if(infoHabilidad instanceof InfoPieza){
                    ar = escaque.getPieza().getHabilidad().canUsar(tv.getTablero(), escaque.getPieza(), escaque.getPos(), infoUsada = new InfoPieza((Pieza) valor));
                } else if(infoHabilidad instanceof InfoPoint){
                    ar = escaque.getPieza().getHabilidad().canUsar(tv.getTablero(), escaque.getPieza(), escaque.getPos(), infoUsada = new InfoPoint((Point) valor));
                } else if(infoHabilidad instanceof InfoString){
                    ar = escaque.getPieza().getHabilidad().canUsar(tv.getTablero(), escaque.getPieza(), escaque.getPos(), infoUsada = new InfoString((String) valor));
                } else { //todo: compuesta
                    ar = ActionResult.FAIL;
                }
                if (ar.isPositive()) {
                    escaque.getPieza().getHabilidad().usar(tv.getTablero(),
                            escaque.getPieza(),
                            escaque.getPos(),
                            infoUsada);
                    reloj.movimiento();
                    MousePieza.get().seleccionado = null;
                    tv.offAll();
                }
            } else {
                System.err.println("tv no es TableroVisual");
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

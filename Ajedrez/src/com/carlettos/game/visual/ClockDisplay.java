package com.carlettos.game.visual;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Direction;
import com.carlettos.game.board.manager.clock.event.Event;
import com.carlettos.game.core.Tuple;
import com.carlettos.game.core.Point;
import com.carlettos.game.input.MousePiece;
import com.carlettos.game.board.Escaque;
import com.carlettos.game.board.manager.clock.Clock;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.player.Player;
import com.carlettos.game.board.property.ability.Info;
import com.carlettos.game.board.property.ability.InfoInteger;
import com.carlettos.game.board.property.ability.InfoDirection;
import com.carlettos.game.board.property.ability.InfoNone;
import com.carlettos.game.board.property.ability.InfoPiece;
import com.carlettos.game.board.property.ability.InfoPoint;
import com.carlettos.game.board.property.ability.InfoString;
import com.carlettos.game.visual.info.InfoDisplay;
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
public class ClockDisplay extends JPanel{

    private final Clock reloj;
    private StringBuilder ordenJugadores;
    private StringBuilder ordenEventos;
    private StringBuilder turnoActual;
    private final JLabel jugadoresLabel;
    private final JLabel eventosLabel;
    private final JLabel turnoLabel;
    private final JButton turno;
    private final JButton habilidad;

    public ClockDisplay(Clock reloj) {
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
            reloj.endTurn();
            Container container = this;
            while (container.getParent() != null) {
                container = container.getParent();
            }
            container.repaint();
            if (container instanceof BoardDisplay) {
                BoardDisplay tv = (BoardDisplay) container;
                tv.offAll();
            }
        });
        habilidad.addActionListener((e) -> {
            if (MousePiece.get().seleccionado == null) {
                return;
            }
            Container container = this;
            while (container.getParent() != null) {
                container = container.getParent();
            }
            if (container instanceof BoardDisplay tv) {
                Escaque escaque = MousePiece.get().seleccionado.getEscaque();
                Info infoHabilidad = escaque.getPiece().getAbility().getInfo();
                
                Object[] valores = escaque.getPiece().getAbility().getPossibleValues(tv.getBoard(), escaque.getPos());
                if (infoHabilidad instanceof InfoNone) {
                    valores = new String[]{"Usar"};
                }
                int i = InfoDisplay.showOptions(tv.getBoard(), escaque.getPos(), valores);
                if(i == -1){
                    return;
                }
                
                Object valor = valores[i];
                Info infoUsada = new InfoNone();
                ActionResult ar;
                if (infoHabilidad instanceof InfoNone) {
                    ar = escaque.getPiece().getAbility().canUse(tv.getBoard(), escaque.getPiece(), escaque.getPos(), infoUsada = new InfoNone());
                } else if(infoHabilidad instanceof InfoInteger){
                    ar = escaque.getPiece().getAbility().canUse(tv.getBoard(), escaque.getPiece(), escaque.getPos(), infoUsada = new InfoInteger((Integer) valor));
                } else if(infoHabilidad instanceof InfoDirection){
                    ar = escaque.getPiece().getAbility().canUse(tv.getBoard(), escaque.getPiece(), escaque.getPos(), infoUsada = new InfoDirection((Direction) valor));
                } else if(infoHabilidad instanceof InfoPiece){
                    ar = escaque.getPiece().getAbility().canUse(tv.getBoard(), escaque.getPiece(), escaque.getPos(), infoUsada = new InfoPiece((Piece) valor));
                } else if(infoHabilidad instanceof InfoPoint){
                    ar = escaque.getPiece().getAbility().canUse(tv.getBoard(), escaque.getPiece(), escaque.getPos(), infoUsada = new InfoPoint((Point) valor));
                } else if(infoHabilidad instanceof InfoString){
                    ar = escaque.getPiece().getAbility().canUse(tv.getBoard(), escaque.getPiece(), escaque.getPos(), infoUsada = new InfoString((String) valor));
                } else { //todo: compuesta
                    ar = ActionResult.FAIL;
                }
                if (ar.isPositive()) {
                    escaque.getPiece().getAbility().use(tv.getBoard(),
                            escaque.getPiece(),
                            escaque.getPos(),
                            infoUsada);
                    tv.getBoard().movement();
                    MousePiece.get().seleccionado = null;
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
        for (Player player : reloj.getPlayers()) {
            ordenJugadores.append(player.getColor()).append(", ");
            
        }
        ordenJugadores.delete(ordenJugadores.lastIndexOf(", "), ordenJugadores.length());
        ordenJugadores.append(".");

        ordenEventos = new StringBuilder("<html>Evento(s) más próximos: <br/>");
        List<Event> eventos = reloj.getEventosOrdenados();
        for (Event evento : (eventos.stream().filter((evento) -> {
            return evento.info.getTurns() == eventos.get(0).info.getTurns();
        }).toArray(Event[]::new))) {
            ordenEventos.append(evento).append("<br/>");

        }
        ordenEventos.append("</html>");

        turnoActual = new StringBuilder("Es el turno del jugador ");
        turnoActual.append(reloj.turnOf().getColor()).append('.');
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        actualizarTextos();
        jugadoresLabel.setText(ordenJugadores.toString());
        eventosLabel.setText(ordenEventos.toString());
        turnoLabel.setText(turnoActual.toString());
    }

    public Clock getClock() {
        return reloj;
    }
}

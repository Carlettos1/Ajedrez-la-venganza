package com.carlettos.game.visual;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.board.manager.clock.event.Event;
import com.carlettos.game.input.MousePiece;
import com.carlettos.game.board.Escaque;
import com.carlettos.game.board.manager.clock.Clock;
import com.carlettos.game.board.player.Player;
import com.carlettos.game.board.property.ability.Info;
import com.carlettos.game.board.property.ability.InfoManager;
import com.carlettos.game.board.property.ability.info.InfoNone;
import com.carlettos.game.visual.info.InfoDisplay;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Carlos
 */
public class ClockDisplay extends JPanel{

    private final Clock clock;
    private StringBuilder playerOrder;
    private StringBuilder eventsOrder;
    private StringBuilder turn;
    private final JLabel playerLabel;
    private final JLabel eventsLabel;
    private final JLabel turnLabel;
    private final JButton turnButton;
    private final JButton abilityButton;

    public ClockDisplay(Clock clock) {
        super(new BorderLayout());
        this.clock = clock;
        updateTexts();
        this.playerLabel = new JLabel(playerOrder.toString());
        this.eventsLabel = new JLabel(eventsOrder.toString());
        this.turnLabel = new JLabel(turn.toString());
        this.turnButton = new JButton("Avanzar turno");
        this.abilityButton = new JButton("Usar Habilidad");
        setup();
    }

    //TODO: listeners por favor
    protected void setup() {
        add(playerLabel, BorderLayout.PAGE_START);
        add(eventsLabel, BorderLayout.CENTER);
        add(turnLabel, BorderLayout.PAGE_END);
        turnButton.addActionListener((ActionEvent e) -> {
            clock.endTurn();
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
        abilityButton.addActionListener((e) -> {
            if (MousePiece.get().selected == null) {
                return;
            }
            Container container = this;
            while (container.getParent() != null) {
                container = container.getParent();
            }
            if (container instanceof BoardDisplay tv) {
                Escaque escaque = MousePiece.get().selected.getEscaque();
                
                Object[] values = escaque.getPiece().getAbility().getPossibleValues(tv.getBoard(), escaque.getPos());
                int i = InfoDisplay.showOptions(tv.getBoard(), escaque.getPos(), values);
                if(i == -1){
                    return;
                }
                
                Object valor = values[i];
                Info usedInfo = new InfoNone();
                ActionResult ar = escaque.getPiece().getAbility().canUse(tv.getBoard(), escaque.getPiece(), escaque.getPos(), usedInfo = InfoManager.getInfo(valor));
                
                if (ar.isPositive()) {
                    escaque.getPiece().getAbility().use(tv.getBoard(),
                            escaque.getPiece(),
                            escaque.getPos(),
                            usedInfo);
                    tv.getBoard().movement();
                    MousePiece.get().selected = null;
                    tv.offAll();
                }
            } else {
                System.err.println("tv no es TableroVisual");
            }
        });
        JPanel tmp = new JPanel(new BorderLayout(0, 10));
        tmp.add(turnButton, BorderLayout.NORTH);
        tmp.add(abilityButton, BorderLayout.SOUTH);
        add(tmp, BorderLayout.EAST);
    }

    public void updateTexts() {
        playerOrder = new StringBuilder("El orden de los jugadores es: ");
        for (Player player : clock.getPlayers()) {
            playerOrder.append(player.getColor()).append(", ");
            
        }
        playerOrder.delete(playerOrder.lastIndexOf(", "), playerOrder.length());
        playerOrder.append(".");

        eventsOrder = new StringBuilder("<html>Evento(s) más próximos: <br/>");
        List<Event> events = clock.getEventosOrdenados();
        for (Event event : (events.stream().filter((e) -> {
            return e.info.getTurns() == events.get(0).info.getTurns();
        }).toArray(Event[]::new))) {
            eventsOrder.append(event).append("<br/>");

        }
        eventsOrder.append("</html>");

        turn = new StringBuilder("Es el turno del jugador ");
        turn.append(clock.turnOf().getColor()).append('.');
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        updateTexts();
        playerLabel.setText(playerOrder.toString());
        eventsLabel.setText(eventsOrder.toString());
        turnLabel.setText(turn.toString());
    }

    public Clock getClock() {
        return clock;
    }
}

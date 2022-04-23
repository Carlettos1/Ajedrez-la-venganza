package com.carlettos.game.display.board;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.board.manager.clock.event.Event;
import com.carlettos.game.input.MousePiece;
import com.carlettos.game.board.Escaque;
import com.carlettos.game.board.manager.clock.Clock;
import com.carlettos.game.board.player.Player;
import com.carlettos.game.board.property.ability.Info;
import com.carlettos.game.board.property.ability.InfoManager;
import com.carlettos.game.board.property.ability.info.InfoNone;
import com.carlettos.game.display.info.InfoDisplay;
import com.carlettos.game.input.ClickAbilityButton;
import com.carlettos.game.input.ClickTurnButton;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.clock = clock;
        updateTexts();
        this.playerLabel = new JLabel(playerOrder.toString());
        this.eventsLabel = new JLabel(eventsOrder.toString());
        this.turnLabel = new JLabel(turn.toString());
        this.turnButton = new JButton("Avanzar turno");
        this.abilityButton = new JButton("Usar Habilidad");
        setup();
    }

    protected void setup() {
        JPanel labels = new JPanel();
        labels.setLayout(new BoxLayout(labels, BoxLayout.PAGE_AXIS));
        labels.add(Box.createVerticalGlue());
        labels.add(playerLabel);
        labels.add(Box.createVerticalGlue());
        labels.add(eventsLabel);
        labels.add(Box.createVerticalGlue());
        labels.add(turnLabel);
        labels.add(Box.createVerticalGlue());
        turnButton.addActionListener(ClickTurnButton.get());
        abilityButton.addActionListener(ClickAbilityButton.get());
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));
        buttons.add(Box.createVerticalGlue());
        buttons.add(turnButton);
        buttons.add(Box.createVerticalGlue());
        buttons.add(abilityButton);
        buttons.add(Box.createVerticalGlue());
        
        this.add(labels);
        this.add(Box.createHorizontalGlue());
        this.add(buttons);
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
        turn.append(clock.turnOf().getColor())
                .append("(Mana: ")
                .append(clock.turnOf().getMana())
                .append(", Movs: ")
                .append(clock.turnOf().getMaxMovements() - clock.getMovements())
                .append("/")
                .append(clock.turnOf().getMaxMovements())
                .append(").");
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

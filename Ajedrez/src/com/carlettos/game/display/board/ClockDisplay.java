package com.carlettos.game.display.board;

import com.carlettos.game.board.clock.Clock;
import com.carlettos.game.board.clock.event.Event;
import com.carlettos.game.display.listeners.ClickAbilityButton;
import com.carlettos.game.display.listeners.ClickTurnButton;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.helper.ConfigHelper;
import com.carlettos.game.util.helper.DisplayHelper;
import java.awt.Graphics;
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
    private StringBuilder eventsOrder;
    private StringBuilder turn;
    private String manaStr;
    private final JLabel eventsLabel;
    private final JLabel turnLabel;
    private final JLabel manaLabel;
    private final JButton turnButton;
    private final JButton abilityButton;

    public ClockDisplay(Clock clock) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.clock = clock;
        updateTexts();
        this.eventsLabel = new JLabel(eventsOrder.toString());
        this.turnLabel = new JLabel(turn.toString());
        this.manaLabel = new JLabel(manaStr);
        this.manaLabel.setFont(DisplayHelper.FONT_6);
        this.manaLabel.setForeground(Color.values()[ConfigHelper.get().getInt("color_mana_id")].getAWT());
        this.turnButton = new JButton("Avanzar turno");
        this.abilityButton = new JButton("Usar Habilidad");
        setup();
    }

    protected void setup() {
        JPanel labels = new JPanel();
        labels.setLayout(new BoxLayout(labels, BoxLayout.PAGE_AXIS));
        labels.add(eventsLabel);
        labels.add(Box.createVerticalGlue());
        labels.add(turnLabel);
        labels.add(manaLabel);
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
                .append("(Movs: ")
                .append(clock.turnOf().getMaxMovements() - clock.getMovements())
                .append("/")
                .append(clock.turnOf().getMaxMovements())
                .append(").");
        
        manaStr = ConfigHelper.getStringConfig("mana_symbol").repeat(clock.turnOf().getMana());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        updateTexts();
        eventsLabel.setText(eventsOrder.toString());
        turnLabel.setText(turn.toString());
        manaLabel.setText(manaStr);
        manaLabel.setToolTipText("Mana: " + clock.turnOf().getMana());
    }

    public Clock getClock() {
        return clock;
    }
}

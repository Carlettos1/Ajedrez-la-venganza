package com.carlettos.game.display.listeners;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.carlettos.game.board.deck.Deck;
import com.carlettos.game.board.deck.PlayerDeck;
import com.carlettos.game.display.board.BoardDisplay;
import com.carlettos.game.display.board.CardDisplay;
import com.carlettos.game.gameplay.card.Card;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.Tuple;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlettos
 */
public class ClickDeckButton implements ActionListener {
    private static final ClickDeckButton LISTENER = new ClickDeckButton();

    private ClickDeckButton() {}

    public static ClickDeckButton get() {
        return LISTENER;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var panel = new JPanel(new BorderLayout());
        var board = BoardDisplay.getInstance();
        var clock = board.getClockDisplay().getClock();
        
        var centralDeck = clock.getCentralDeck();
        var playerDecks = clock.getPlayerDecks();
        var boardCards = clock.getBoardCards();
        
        var cardPanel = new JPanel(new CardLayout());
        
        cardPanel.add(new DeckCentralPanel(centralDeck), "CentralDeck");
        cardPanel.add(new PlayerDeckPanel(playerDecks), "PlayerDecks");
        cardPanel.add(new BoardCardsPanel(boardCards), "BoardCards");
        
        //TODO: use ResourceLocation
        JComboBox<String> cb = new JComboBox<>(new String[]{"CentralDeck", "PlayerDecks", "BoardCards"});
        cb.setEditable(false);
        cb.addItemListener(itemEvent -> {
            CardLayout cl = (CardLayout) cardPanel.getLayout();
            cl.show(cardPanel, itemEvent.getItem().toString());
        });
        
        panel.add(cb, BorderLayout.WEST);
        panel.add(cardPanel, BorderLayout.CENTER);
        JOptionPane.showConfirmDialog(board, panel);
    }
    
    public static class BoardCardsPanel extends JPanel {
        private static final long serialVersionUID = 5747169458901321586L;
        private transient List<Tuple<Player, Card>> cards;
        
        public BoardCardsPanel(List<Tuple<Player, Card>> cards) {
            this.cards = cards;
            update();
        }

        private void update() {
            if (cards == null) { return; }
            removeAll();
            for (var tuple : cards) {
                add(new CardDisplay(tuple.x.getColor(), tuple.y, false));
            }
            revalidate();
        }
        
        @Override
        public void repaint() {
            update();
            super.repaint();
        }
    }

    public static class PlayerDeckPanel extends JPanel {
        private static final long serialVersionUID = -618796457914516688L;
        private transient List<PlayerDeck> decks;

        public PlayerDeckPanel(List<PlayerDeck> decks) {
            this.decks = decks;
            update();
        }

        private void update() {
            if (decks == null) { return; }
            removeAll();
            for (var deck : decks) {
                var p = new JPanel();
                for (var card : deck.getCards()) {
                    p.add(new CardDisplay(deck.getOwner().getColor(), card, false));
                }
                add(p);
            }
            revalidate();
        }

        @Override
        public void repaint() {
            update();
            super.repaint();
        }
    }

    public static class DeckCentralPanel extends JPanel {
        private static final long serialVersionUID = 3445825895294504631L;
        private transient Deck deck;

        public DeckCentralPanel(Deck deck) {
            super(new BorderLayout());
            this.deck = deck;
            update();
        }

        private void update() {
            if (deck == null) { return; }
            removeAll();
            
            var cardPanel = new JPanel(new CardLayout());
            for (var card : deck.getCards()) {
                cardPanel.add(new CardDisplay(Color.GRAY, card, false), card.toString());
            }
            
            var cb = new JComboBox<Card>(deck.getCards().toArray(Card[]::new));
            cb.setEditable(false);
            cb.addItemListener(item -> ((CardLayout) cardPanel.getLayout()).show(cardPanel, item.getItem().toString()));
            
            this.add(cb, BorderLayout.EAST);
            this.add(cardPanel, BorderLayout.CENTER);
            revalidate();
        }

        @Override
        public void repaint() {
            update();
            super.repaint();
        }
    }
}

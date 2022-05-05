package com.carlettos.game.display.listeners;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.carlettos.game.board.Deck;
import com.carlettos.game.board.PlayerDeck;
import com.carlettos.game.display.board.BoardDisplay;
import com.carlettos.game.display.board.CardDisplay;
import com.carlettos.game.gameplay.card.Card;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlettos
 */
public class ClickDeckButton implements ActionListener {
    private static final ClickDeckButton LISTENER = new ClickDeckButton();
    
    private ClickDeckButton() {
    }
    
    public static ClickDeckButton get() {
        return LISTENER;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	var panel = new JPanel(new CardLayout());
    	var board = BoardDisplay.getInstance();
    	var deck = board.getClockDisplay().getClock().getDeck();
    	var playerDecks = board.getClockDisplay().getClock().getPlayerDecks();
    	panel.add(new DeckPanel(deck));
    	panel.add(new PlayerDeckPanel(playerDecks));
        JOptionPane.showConfirmDialog(null, panel);
    }
    
    public static class PlayerDeckPanel extends JPanel {
    	private static final long serialVersionUID = -618796457914516688L;
		private transient List<PlayerDeck> decks;
		
		public PlayerDeckPanel(List<PlayerDeck> decks) {
			this.decks = decks;
			update();
		}
		
		private void update() {
			if(decks == null) {
				return;
			}
			removeAll();
			for (var deck : decks) {
				var p = new JPanel();
				for (var card : deck.getCards()) {
					p.add(new CardDisplay(deck.getOwner().getColor(), card));
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
    
    public static class DeckPanel extends JPanel {
    	private static final long serialVersionUID = 3445825895294504631L;
		private transient Deck deck;

		public DeckPanel(Deck deck) {
			super(new CardLayout());
			this.deck = deck;
			update();
		}
		
		private void update() {
			if(deck == null) {
				return;
			}
			removeAll();
			for (var card : deck.getCards()) {
				add(new CardDisplay(Color.GRAY, card));
			}
			var cb = new JComboBox<Card>(deck.getCards().toArray(Card[]::new));
			cb.setEditable(false);
			cb.addItemListener(item -> ((CardLayout) getLayout()).show(this, item.getItem().toString()));
			add(cb);
			revalidate();
		}
		
		@Override
		public void repaint() {
			update();
			super.repaint();
		}
    } 
}

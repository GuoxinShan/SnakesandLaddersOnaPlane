package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class SmartStrategy extends Strategy {
	
	private int  thinkingTime;
	
	public SmartStrategy(Oh_Heaven ohHeaven, int playerIndex, int thinkingTime) {
		super(ohHeaven, playerIndex);
	}

	@Override
	public Card selectLeadCard(Hand hand, AccessibleInfo info) {
		this.getOhHeaven().setStatus("Player " + this.getPlayerIndex() + " thinking...");
		Oh_Heaven.delay(this.thinkingTime);
		
		Card highest = null;
		highest = hand.get(0);
		for(int i=1; i< hand.getNumberOfCards(); i++) {
			Card card = hand.get(i);
			if(highest.getRankId()>card.getRankId()) {
				highest = card;
			}
		}
		
		return  highest;
	}

	@Override
	public Card selectCard(Hand hand, AccessibleInfo info) {
		this.getOhHeaven().setStatus("Player " + this.getPlayerIndex() + " thinking...");
		Oh_Heaven.delay(this.thinkingTime);

		Card highest = null;
		for(Card card : hand.getCardsWithSuit(info.getLead())) {
			if(highest==null) {
				highest = card;
			}else if(highest.getRankId() > card.getRankId()) {
				highest =card;
			}
		}
		if(highest!=null) {
			return highest;
		}
		
		for(Card card : hand.getCardsWithSuit(info.getTrumps())) {
			if(highest==null) {
				highest = card;
			}else if(highest.getRankId() > card.getRankId()) {
				highest =card;
			}
		}
		if(highest!=null) {
			return highest;
		}		
		return this.selectLeadCard(hand, info);
	}
}

package oh_heaven.game;

import java.util.List;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class LegalStrategy extends Strategy {
	
	private int  thinkingTime;
	
	public LegalStrategy(Oh_Heaven ohHeaven, int playerIndex, int thinkingTime) {
		super(ohHeaven, playerIndex);
	}

	@Override
	public Card selectLeadCard(Hand hand, AccessibleInfo info) {
		this.getOhHeaven().setStatus("Player " + this.getPlayerIndex() + " thinking...");
		Oh_Heaven.delay(this.thinkingTime);
		int x = Oh_Heaven.random.nextInt(hand.getNumberOfCards());
	    return hand.get(x);
	}

	@Override
	public Card selectCard(Hand hand, AccessibleInfo info) {
		
		if(hand.getNumberOfCardsWithSuit(info.getLead())>0) {
			List<Card> cards = hand.getCardsWithSuit(info.getLead());
			return  cards.get(Oh_Heaven.random.nextInt(cards.size()));
		}
		
		return this.selectLeadCard(hand, info);
	}


}

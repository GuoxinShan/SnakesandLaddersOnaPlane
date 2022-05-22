package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class PlayerStrategy extends Strategy {

public PlayerStrategy(Oh_Heaven ohHeaven, int playerIndex) {
		super(ohHeaven,  playerIndex);
	}

	@Override
	public Card selectLeadCard(Hand hand, AccessibleInfo info) {
		this.getOhHeaven().setStatus("Player "+ this.getPlayerIndex() +" double-click on card to lead.");
		return this.getOhHeaven().selectCardUser(this.getPlayerIndex());
	}
	
	@Override
	public Card selectCard(Hand hand, AccessibleInfo info) {
		return this.selectLeadCard(hand, info);
	}
}

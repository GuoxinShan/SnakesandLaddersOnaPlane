package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public abstract class Strategy {	
	private  Oh_Heaven  ohHeaven;
	private  int playerIndex;
	public  Strategy(Oh_Heaven ohHeaven, int playerIndex) {
		this.ohHeaven = ohHeaven;
		this.playerIndex =playerIndex;
	}

	public  abstract  Card  selectLeadCard(Hand hand, AccessibleInfo state); //for a lead player
	public  abstract  Card  selectCard(Hand hand, AccessibleInfo state);  
	
	protected  Oh_Heaven  getOhHeaven() {
		return this.ohHeaven;
	}
	
	public  int  getPlayerIndex() {
		return this.playerIndex;
	}
	

}

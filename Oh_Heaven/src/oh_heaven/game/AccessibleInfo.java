package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import oh_heaven.game.Oh_Heaven.Suit;

public class AccessibleInfo {
	
		Suit trumps;
		Suit lead;



		public AccessibleInfo(Suit trumps, Suit lead) {
			this.trumps = trumps;
			this.lead = lead;
	
		}

		public Suit getTrumps() {
			return trumps;
		}

		public Suit getLead() {
			return lead;
		}


}

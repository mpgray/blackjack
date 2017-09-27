package blackJack;

import java.util.ArrayList;
import java.util.List;
import blackJack.Card;
import blackJack.Card.Face;

public class Hand {

	public List<Card> cards = new ArrayList<Card>();
	private int ace = 0;
	public int total;
	
	//Lists cards in your hand
	public Hand(){
		
	}
	
	//Keeps track of total value in hand
	public void drawing (Card card){
		cards.add(card);
		
		//Counts number of aces in hand
		if (card.face == Face.ACE){
			ace++;
		}
		
		//Changes value of Ace to 1 if you go over 21
		if(total + card.value > 21 && ace > 0){
			total = total + (card.value - 10);
			ace--;
		}else{
			total = total + card.value;
		}
	}
	
	public int returnTotal(){
		return total;
	}
	
	public void resetGame(){
		cards.clear();
		total = 0;
		ace = 0;
	}
	
	
}

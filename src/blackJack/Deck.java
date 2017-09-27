package blackJack;

import blackJack.Card.Face;
import blackJack.Card.Suit;

public class Deck {
	
	private Card[] cards = new Card[52];
	
	//Constructor for deck
	public Deck(){
		shuffle();
	}
	
	//Shuffle the deck
	public void shuffle(){
		
		int counter = 0;
		
		for(Suit suit : Suit.values()){
			for(Face face : Face.values()){
			cards[counter++] = new Card(face, suit);
			}
		}
		
	}
	
	//Draw a random card from the deck
	public Card draw(){
		Card theCard = null;
		
		while(theCard == null){
			int deck = (int)(Math.random()*cards.length);
			theCard = cards[deck];
			cards[deck] = null;
			}
		
		return theCard;
		
	}
	
	
}

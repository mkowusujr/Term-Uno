import java.util.ArrayList;

public abstract class Player {
    protected ArrayList<Card> handCards;

    public Player() {
        handCards = new ArrayList<Card>();
    }

    public void receiveHand(ArrayList<Card> deltCards) {
        for (Card card : deltCards) {
            card.flipCard();
            handCards.add(card);
        }
    }

    public void receiveHand(Card card) {
        handCards.add(card);
    }

    public int getHandCount() {
        return handCards.size();
    }

    
    abstract Card playCard(Deck playingDeck);

    abstract String displayHand();

    abstract void gameOverAction();
}
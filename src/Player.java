import java.util.ArrayList;

public class Player { // TODO make this an abstract class
    private ArrayList<Card> handCards;

    // private int handCount;
    public Player() {
        handCards = new ArrayList<Card>();
        // handCount = handCards.size();
    }

    public void receiveHand(ArrayList<Card> deltCards) {
        for (Card card : deltCards) {
            card.flipCard();
            handCards.add(card);
            // handCount += 1;
        }
    }
    public void receiveHand(Card card) {
        
            handCards.add(card);
            // handCount += 1;
        
    }

    public int getHandCount() {

        return handCards.size();
    }

    private Card discardFromHand(String card) {
        Card played = null;
        for (int i = 0; i < handCards.size(); i++) {
            if (handCards.get(i).toString().equals(card)) {
                played = handCards.remove(i);
                break;
            }
        }
        return played;
    }

    public Card playCard(Deck playingDeck, String move) {
        // playCard method that returns card
        if (move.equals("draw")) {
            System.out.println("Drawing card from deck...");
            Card drawn = playingDeck.drawCard();
            drawn.flipCard();
            handCards.add(drawn);
            move = drawn.toString();
            System.out.println("You drew a " + drawn + " card");
        }

        Card card = discardFromHand(move);
        if (card == null)
            handCards.remove(handCards.size() - 1);
        return card;
    }
    

    public String displayHand() {
        String output = "";
        for (Card card : handCards) {
            output += card;
            output += " ";
        }
        return output;
    }
}
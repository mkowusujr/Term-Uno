import java.util.ArrayList;

/**
 * Player Abstract Class representing both human and computer players
 * 
 * @author Mathew Owusu Jr
 */
public abstract class Player {
    protected ArrayList<Card> handCards;

    /**
     * Class constructor
     * Assigns the Player Object a hand, an empty list of cards
     * the PLayer object plays with
     */
    public Player() {
        handCards = new ArrayList<Card>();
    }

    /**
     * Adds the list oof cards being distributed into the Player Object's hand 
     * @param dealtCards The cards being distributed
     */
    public void addToHand(ArrayList<Card> dealtCards) {
        for (Card card : dealtCards) {
            card.flipCard();
            handCards.add(card);
        }
    }

    /**
     * Adds the card into the Player Object's hand
     * @param card The card being distributed
     */
    public void addToHand(Card card) {
        handCards.add(card);
    }

    /**
     * Gets how many cards are in the Player Object's hand
     * @return An integer
     */
    public int getHandCount() {
        return handCards.size();
    }

    /**
     * How the Player Object will choose their next playing card
     * @param discardDeck The deck of cards being used to store the cards
     *      being discarded during the game
     * @return The card the Player is discarding from their hand
     */
    abstract Card playCard(Deck discardDeck);

    /**
     * How the Player Object will display their hand
     * @return The Player Objects hand as a string. If the Player is a Human
     *      Object the cards in their hand will be facing up. If the Player is
     *      A Computer Object the cards in their hand will being facing down
     */
    abstract String displayHand();

    /**
     * How the Player Object responds to a game over
     */
    abstract void gameOverAction();

    /**
     * Is the Player Object of the Human subclass
     * @return A boolean representing if the Player Object is a human (true)
     *      or not (false)
     */
    abstract boolean isHuman();
}
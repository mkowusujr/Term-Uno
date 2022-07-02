import java.util.ArrayList;
/**
 * A Class representing the Discard Deck
 * 
 * @author Mathew Owusu Jr
 */
public class DiscardDeck {
    /**
     * The deck cards are discarded into
     */
    private ArrayList<Card> discardDeck;

    /**
     * The Class Constructor.
     * Starts the discard pile
     */
    public DiscardDeck(PlayingDeck playingDeck) {
        discardDeck = new ArrayList<Card>();
        createDiscardPile(playingDeck);
    }
    
    /**
     * Helper function for creating the discard deck by continuously drawing
     * from the playing deck unk a valid card is placed on top. At the beginning
     * of a card the first card played must be a number from 0-9
     * @param playingDeck  The playing deck for the game.
     */
    private void createDiscardPile(PlayingDeck playingDeck) {
        discardDeck.add(playingDeck.drawCard());
        discardDeck.get(discardDeck.size() - 1).flipCard();
    
        if (getTopOfDiscardDeck().getValue() > 9) {
            createDiscardPile(playingDeck);
        }
    }

    /**
     * Gets the card at the top of the discard deck
     * @return The card at the top of the discard deck
     */
    public Card getTopOfDiscardDeck() {
        return discardDeck.get(discardDeck.size() - 1);
    }

    /**
     * Adds a card to the discard deck
     * @param card The card being added to the discard deck
     */
    public void addToDiscardCard(Card card) {
        discardDeck.add(card);
    }

    /**
     * Gets the entire discard deck
     * @return The list of cards making up the discard deck
     */
    public ArrayList<Card> getDiscardDeck() {
        return discardDeck;
    }

    /**
     * Removes the card at the top of the discardd deck
     */
    public void removeTopCard() {
        discardDeck.remove(discardDeck.size() - 1);
    }

    public int deckSize() {
        return discardDeck.size();
    }
}

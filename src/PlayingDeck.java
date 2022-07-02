import java.util.ArrayList;
import java.util.Collections;

/**
 * The Class representing the Playing Deck, also know as the draw pile
 * 
 * @author Mathew Owusu Jr
 */
public class PlayingDeck {
    /**
     * The deck cards are drew from
     */
    private ArrayList<Card> playingDeck;
    /**
     * The allowed colors a card can be in the deck
     */
    private final char[] colors = { 'r', 'g', 'b', 'y' };

    /**
     * The Class Constructor.
     * Creates and sets up the playing deck.
     */
    public PlayingDeck() {
        unboxDeck();
        Collections.shuffle(playingDeck);
    }

    /**
     * Simulates creating a a full deck of Uno Cards
     */
    private void unboxDeck() {
        playingDeck = new ArrayList<Card>();

        // Adding Zero Cards
        for (char color : colors) {
            playingDeck.add(new Card(color, 0));
        }

        // Adding 1-9 and skip, reverse, draw two cards
        for (int i = 1; i < 13; i++) {
            for (char color : colors) {
                playingDeck.add(new Card(color, i));
                playingDeck.add(new Card(color, i));
            }
        }

        // Add the wild colors
        for (int i = 0; i < 4; i++) {
            playingDeck.add(new Card('s', 13));
            playingDeck.add(new Card('s', 14));
        }
    }

    /**
     * Draws a card from the top of the playing deck
     * 
     * @return The card drawn
     */
    public Card drawCard() {
        return playingDeck.remove(playingDeck.size() - 1);
    }

    /**
     * Gets the current size of the playing Deck
     * 
     * @return An integer
     */
    public int deckSize() {
        return playingDeck.size();
    }

    /**
     * Gets the card at the top of the playing deck
     * 
     * @return The card at the top of the playing deck
     */
    public Card getTopOfDeck() {
        return playingDeck.get(playingDeck.size() - 1);
    }

    /**
     * Deals multiple cards from the playing deck
     * 
     * @param cardAmount The amount of cards being drawn from the playing deck
     * @return A list of the cards drawn
     */
    public ArrayList<Card> dealPlayingDeck(int cardAmount) {
        ArrayList<Card> drawnCards = new ArrayList<Card>();
        for (int i = 0; i < cardAmount; i++) {
            drawnCards.add(drawCard());
        }
        return drawnCards;
    }

    /**
     * Setups of the playing deck again using the cards from the discard deck
     * 
     * @param discardDeck The deck of cards being used to store the cards
     *      being discarded during the game
     */
    public void resetDeck(DiscardDeck discardDeck) {
        System.out.println("Emptying, adding discard deck back in");
        for (int i = discardDeck.deckSize(); i >= 0; i--){
            Card card = discardDeck.getTopOfDeck();
            card.flipCard();
            playingDeck.add(card);
            discardDeck.removeTopCard();
        }
        Collections.shuffle(playingDeck);
    }

    /**
     * Whether or not the playing deck is empty
     * @return true if playing deck is empty and false of it isn't
     */
    public boolean isEmpty() {
        if (playingDeck.size() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
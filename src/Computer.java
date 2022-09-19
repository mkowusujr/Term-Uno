import java.util.ArrayList;
import java.lang.Thread;

/**
 * A Class representing a computer player
 * 
 * @author Mathew Owusu Jr
 */
public class Computer extends Player {
    public Computer() {
        super();
    }

    /**
     * Introduces delay to the computer player's moves to make the game easier
     * to follow in the terminal
     */
    private void think() {
        try {
            int thinkTime = 0;

            // Random time in milliseconds in range of 500 ms to 2500 ms inclusive
            thinkTime = (int) Math.random() * (2500 + 1) + 500;
            Thread.sleep(thinkTime);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Creates a list of cards from hand that can be played on top of the discard deck
     * 
     * @param discardDeck The deck of cards being used to store the cards
     *      being discarded during the game
     * @return The list of cards in queue to be used for current turn
     */
    private ArrayList<Card> findPlayableCards(DiscardDeck discardDeck) {
        Card lastPlayedCard = discardDeck.getTopOfDeck();

        ArrayList<Card> playable = new ArrayList<Card>();
        think();
        for (Card card : cardsInHand) {
            if (card.equals(lastPlayedCard)) {
                playable.add(card);
            }
        }

        return playable;
    }

    /**
     * Draws from the playing deck and immediately puts the drawn card into the list
     * of cards to use for current move
     * 
     * @param playingDeck The deck of cards being drawn from
     * @param playableCards The list of cards in queue to be used for current turn
     */
    private void drawFromDeck(PlayingDeck playingDeck, ArrayList<Card> playableCards) {
        think();
            System.out.println("Had no playable card in hand, drawing from deck...");
            Card drawn = playingDeck.drawCard();
            drawn.flipCard();
            playableCards.add(drawn);
    }

    /**
     * Picks the first card in the list of playable cards
     * 
     * @param action A string containg the Computer Player's actions during it's turn
     * @param playableCards The deck of cards being drawn from
     * @param playingDeck The deck of cards being drawn from
     * @return The card chosen from the list of playable cards
     */
    private Card pickCard(String action, ArrayList<Card> playableCards, PlayingDeck playingDeck) {
        if (playableCards.size() == 0) {
            drawFromDeck(playingDeck, playableCards);
            action += "Drew a ";
        } 
        Card chosenCard = playableCards.get(0);
        if (action.equals("Drew a ")) {
            System.out.print(action + chosenCard + " card, ");
        }
        return chosenCard;
    }

    /**
     * Removes the card chosen from the playable cards list from the Computer Player's 
     * hand
     * 
     * @param chosenCard The card chosen from the list of playable cards 
     */
    private void discardFromHand(Card chosenCard) {
        for (Card card : cardsInHand) {
            if (chosenCard.toString().equals(card.toString())) {
                //System.out.println("Playing " + card);
                cardsInHand.remove(card);
                break;
            }
        }
    }

    /**
     * Changes the color of the card last played. Used in cases where a Computer
     * Player just played a Wild Card or a Plus Four Card.
     * The Computer changes the color to the first color in their hand
     * 
     * @param discardDeck The deck of cards being used to store the cards
     *      being discarded during the game
     */
    private void changeSpecialCardColor(DiscardDeck discardDeck){
        char color = 's';
            for (Card card : cardsInHand) {
                if (card.getColor() != 's')
                    color = cardsInHand.get(0).getColor();
            }
            Card lastPlayedCard = discardDeck.getTopOfDeck();

            think();
            System.out.print("Changing the color to ");
            switch (color) {
                case 'r':
                    System.out.println("\033[31;1mred\033[0m");
                    break;
                case 'b':
                    System.out.println("\033[34;1mblue\033[0m");
                    break;
                case 'g':
                    System.out.println("\033[32;1mgreen\033[0m");
                    break;
                case 'y':
                    System.out.println("\033[33;1myellow\033[0m");
                    break;
            }
            lastPlayedCard.changeColor(color);
    }

    /**
     * {@inheritDoc}}.
     * First the computer creates a list of cards they can play for their turn.
     * If they have no playable cards they draw from the playing deck and attempts
     * to play the drawn card.
     */
    @Override
    public Card makeMove(PlayingDeck playingDeck, DiscardDeck discardDeck) {
        ArrayList<Card> playableCards = findPlayableCards(discardDeck);
        String action = "";

        Card chosenCard = pickCard(action, playableCards, playingDeck);
        discardFromHand(chosenCard);

        think();
        if (canPlayCard(discardDeck, chosenCard)) {
            // cases where a color changing card is played
            if (discardDeck.getTopOfDeck().getValue() > 12) {
                changeSpecialCardColor(discardDeck);
            }
        } 
        else {
            System.out.println("But couldn't play the " + chosenCard + " card");
            chosenCard = null;
        }
        return chosenCard;
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public boolean isHuman() {
        return false;
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public String displayHand() {
        String output = "";
        for (Card card : cardsInHand) {
            card.flipCard();
            output += card;
            card.flipCard();
            output += " ";
        }
        return output;
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public void gameOverAction() {
        System.out.println("Good Game");
    }
}
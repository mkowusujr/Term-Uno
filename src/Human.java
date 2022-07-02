import java.util.Scanner;
import java.lang.Thread;

/**
 * The Class representing the human player
 * 
 * @author Mathew Owusu Jr
 */
public class Human extends Player {
    /**
     * A Scanner object that is shared with all instances of the Human Class
     */
    private static Scanner kb;

    /**
     * Class Constructor.
     * Initializes the scanner for user input
     */
    public Human() {
        super();
        kb = new Scanner(System.in);
    }

    /**
     * Introduces delay to make the game easier to follow in the terminal
     */
    private void movingTime() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Removes the card the user wants to discard from their 
     * 
     * @param card The card that the user plays to discard
     * @return The card being discard for the user's hand or null if the
     *      user doesn't have the card or typed the card incorrectly
     */
    private Card discardFromHand(String card) {
        Card played = null;

        // Find the card the user wants to play
        for (int i = 0; i < cardsInHand.size(); i++) {
            if (cardsInHand.get(i).displayCard().equals(card)) {
                played = cardsInHand.remove(i);
                break;
            }
        }
        return played;
    }

    /**
     * The user picks the next card they want to play.
     * If the user chooses to draw from the playing deck, this fuction 
     * with draw from the playing deck and make the user attempt play 
     * the card drawn. 
     * If the User chooses to not draw, but rather play a card in their hand,
     * this function will fetch the card from their hand and discard it if it
     * exists
     * 
     * @param playingDeck The playing deck for the game. The user can draw
     *      from it if they don't have a playable card
     * @param move The action the user wants to make. It can either be the 
     *      the name of the card they want to play, for example "g7", or
     *      it can be the string "draw" if they don't have a playable card
     * @return The card the user chooses to play, null is the card doesn't
     *      exist in the user's hand
     */
    private Card pickCard(PlayingDeck playingDeck, String move) {
        if (move.equals("draw")) {
            System.out.println("Drawing card from deck...");
            movingTime();
            Card drawn = playingDeck.drawCard();
            drawn.flipCard();
            addToHand(drawn);
            move = drawn.displayCard();
            System.out.print("You drew a " + drawn + " card, ");
        }

        Card card = discardFromHand(move);
        return card;
    }

    /**
     * Changes the color of the card last played. Used in cases where a user
     * just played a Wild Card or a Plus Four Card
     * 
     * @param discardDeck The deck of cards being used to store the cards
     *      being discarded during the game
     */
    private void changeSpecialCardColor(DiscardDeck discardDeck){
        System.out.println("What color would you like change it too");
        System.out.println("(r)ed, (b)lue, (g)reen, (y)ellow");
        String color = kb.nextLine();
        Card lastPlayedCard = discardDeck.getTopOfDeck();
        movingTime();
        System.out.print("Changing the color to ");
        switch (color.charAt(0)) {
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
     * {@inheritDoc}.
     * Prompts the user for what move they want to make
     * If the choose to play a card from their hand, first
     * the function checks if they have the card the user wants to play,
     * then it checks if the card the user wants to play is a valid move.
     * This function allows user multiple attempts to make a move if the 
     * checks fail.
     * This infinite loop ends once the user decided to draw from the playing deck 
     * instead. The loop also ends if the user makes a valid move.
     * If the user plays a card that can change its color the function will 
     * prompt the user for what they want to do next.
     */
    @Override
    public Card makeMove(PlayingDeck playingDeck, DiscardDeck discardDeck) {
        Card playedCard = null;
        String move = "";
        do {
            move = kb.nextLine();
            playedCard = this.pickCard(playingDeck, move);
            if (playedCard == null) {
                System.out.println("You don't have that card");
            } 
            else if (!playedCard.canPlayCard(discardDeck, this)) {
                System.out.println("You can't play that card");
            }
            else {
                movingTime();
                // If User plays a card that can change color
                if (discardDeck.getTopOfDeck().getValue() > 12) {
                    changeSpecialCardColor(discardDeck);
                }
                break;
            }

            // if the user
            if (!move.equals("draw"))
                System.out.println("Play a different Card");
            else
                return null;
        } while (!move.equals("draw"));
        return playedCard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isHuman() {
        return true;
    }

    /**
     * {@inheritDoc}.
     * Closes the scanner when the game is over
     */
    @Override
    public void gameOverAction() {
        kb.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String displayHand() {
        String output = "";
        for (Card card : cardsInHand) {
            output += card;
            output += " ";
        }
        return output;
    }
}
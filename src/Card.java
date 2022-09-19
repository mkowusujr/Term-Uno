/**
 * The Uno Card Class
 * @author Mathew Owusu Jr
 */
public class Card {
    private char color;
    private int value;
    private boolean faceUp;
    
    /**
     * Class Constructor for creating an uno card
     * 
     * @param color is the first letter of the card's color
     *      acceptable color values are 'r' for red,
     *      'b' for blue, 'g' for green, 'y' for yellow, and
     *      's' for wild cards
     * @param value a number representing what kind of card this is
     *      values 1-9 are uno cards 1-9
     *      value 10 represents a skip card, 11 is a reverse card,
     *      12 is a plus two card, 13 is a wild card,
     *      and 14 is a plus four card
     */
    public Card(char color, int value) {
        this.color = color;
        this.value = value;
        faceUp = false;
    }

    /**
     * Checks if the card color and type face is up
     * @return A boolean representing if the card's face is up(true) 
     *      or not(false)
     */
    public boolean isFaceUp() {
        return faceUp;
    }

    /**
     * Flips the card
     */
    public void flipCard() {
        faceUp = !faceUp;
    }

    /**
     * Gets the cards color
     * @return A char representing the card's color
     */
    public char getColor() {
        return color;
    }

    /**
     * Gets the cards value
     * 
     * @return A number representing what tyoe of card this is
     *      values 1-9 are uno cards 1-9
     *      value 10 represents a skip card, 11 is a reverse card,
     *      12 is a plus two card, 13 is a wild card,
     *      and 14 is a plus four card
     */
    public int getValue() {
        return value;
    }

    /**
     * Changes the card's current color, only works on cards with a 
     * value greater than 12. This means you can only change the color
     * of a wild card, which has a value of 13, and a plus four card,
     * which has a value of 14
     * 
     * @param color is the first letter of the card's color
     *      acceptable color values are 'r' for red,
     *      'b' for blue, 'g' for green, 'y' for yellow, and
     *      's' for wild cards
     */
    public void changeColor(char color) {
        if (value > 12) {
            this.color = color;
        }
    }

    /**
     * Changes the card's current color, only works on cards with a 
     * value greater than 12. This means you can only change the color
     * of a wild card, which has a value of 13, and a plus four card,
     * which has a value of 14. This function is for cases were the user types
     * in the colors full name
     * 
     * @param color is the first letter of the card's color
     *      acceptable color values are 'r' for red,
     *      'b' for blue, 'g' for green, 'y' for yellow, and
     *      's' for wild cards
     */
    public void changeColor(String color) {
        if (value > 12) {
            this.color = color.charAt(0);
        }
    }

    /**
     * Whether or not this card can be played on top of the discard deck
     * 
     * @param discardDeck The deck of cards being used to store the cards
     *      being discarded during the game
     * @param player the player playing the current card
     * @return A boolean representing whether or not this card matches the
     *      card on top of the discard deck
     */
    // public boolean canPlayCard(DiscardDeck discardDeck, Player player) {
    //     if (this.equals(discardDeck.getTopOfDeck())) {
    //         String pType;
    //         if (player.isHuman()) {
    //             pType = "You played";
    //         } else {
    //             pType = "Played";
    //         }
    //         System.out.printf("%s a %s card\n", pType, this);
    //         discardDeck.addToDeck(this);
    //         return true;
    //     } else {
    //         player.addToHand(this);
    //         return false;
    //     }
    // }

    /**
     * Checks if card has either the same color or card value 
     * as another card. If the other card is the special card
     * Wild Card or Plus Four Card, then it will pass the check
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Card) {
            Card cardPlayed = (Card) other;
            if (this.color == cardPlayed.color || this.color == 's') {
                return true;
            }
            return this.value == cardPlayed.value;
        }
        return false;
    }

    /**
     * Converts the card values to a string representing
     * it's card type
     * 
     * @return A string representing the card's current face
     */
    public String displayCard() {
        if (isFaceUp() == true) {
            switch(value) {
                case 10:
                    return color + "x";
                case 11:
                    return color + "<>";
                case 12:
                    return color + "+2";
                case 13:
                    return color + "w";
                case 14:
                    return color + "+4";
                default:
                    return color + String.valueOf(value);
            }
        } else {
            return "u";
        }
    }

    /**
     * Displays the card to the terminal. This function is responsible
     * for giving each card its respective color
     */
    @Override
    public String toString() {
        String output = "";
        if (faceUp == true) {
            switch (color) {
                case 'r':
                    output += "\033[31;1m"; // bold red
                    break;
                case 'g':
                    output += "\033[32;1m"; // bold green
                    break;
                case 'b':
                    output += "\033[34;1m"; // bold blue
                    break;
                case 'y':
                    output += "\033[33;1m"; // bold yellow
                    break;
                case 's':
                    output += "\033[37;1m"; // bold white
                    break;
            }
        }
        if (isFaceUp() == true) {  // prints the card face if its up
            switch(value) {
                case 10:
                    return output + color + "x" + "\033[0m";
                case 11:
                    return output + color + "<>" + "\033[0m";
                case 12:
                    return output + color + "+2" + "\033[0m";
                case 13:
                    return output + color + "w" + "\033[0m";
                case 14:
                    return output + color + "+4" + "\033[0m";
                default:
                    return output + color + String.valueOf(value) + "\033[0m";
            }
        } else {
            return "\033[37;1mu\033[0m";
        }
    }
}
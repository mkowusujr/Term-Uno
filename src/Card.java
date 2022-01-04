public class Card {
    private char color;
    private int value;
    private boolean faceUp;

    public Card(char color, int value) {
        this.color = color;
        this.value = value;
        faceUp = false;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void flipCard(Card card) {
        card.faceUp = !card.faceUp;
    }

    public void cardAffect(Card Card) {
        // TODO Switch class to do some thing to the deck
        // TODO Maybe add Deck Object, or Game Object idk rn
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Card) {
            Card lastPlayed = (Card) other;
            if (this.color == lastPlayed.color) {
                return true;
            }
            return this.value == lastPlayed.value;
        }
        return false;
    }

    @Override
    public String toString() {
        if (value == 10)
            return color + "x";
        else if (value == 11)
            return color + "<>";
        else if (value == 12)
            return color + "+2";
        else if (value == 13)
            return color + "w";
        else if (value == 14)
            return color + "+4";
        else
            return color + String.valueOf(value);
    }
}
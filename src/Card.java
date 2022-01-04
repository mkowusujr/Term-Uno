public class Card{
    private char color;
    private String value;
    private boolean faceUp;

    public Card(char color, String value){
        this.color = color;
        this.value = value;
        faceUp = false;
    }

    public boolean isFaceUp(){
        return faceUp;
    }

    public void flipCard(Card card){
        card.faceUp = !card.faceUp;
    }

    public void cardAffect(Card Card){
        // TODO Switch class to do some thing to the deck
        // TODO Maybe add Deck Object, or Game Object idk rn
    }

    public String toString(){
        return color + value;
    }
}
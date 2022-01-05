import java.util.*;

public class Deck{
    ArrayList<Card> playingDeck;
    ArrayList<Card> discardDeck;
    //private final int MAX_SIZE = 108;
    private final char[] colors = {'r', 'g', 'b', 'y'};

    private void unboxDeck(){
        playingDeck = new ArrayList<Card>();
        // Adding Zero Cards
        for (char color : colors) {
            playingDeck.add(new Card(color, 0));
        }
        
        // Adding 1-9 and skip, reverse, draw two cards
        for (int i = 1; i < 13; i++){
            for (char color : colors) {
                playingDeck.add(new Card(color, i));
                playingDeck.add(new Card(color, i));
            }
        }

        // Add the wild colors
        for (int i = 0; i < 4; i++){
            playingDeck.add(new Card('s', 13));
            playingDeck.add(new Card('s', 14));
        }
    }   

    public Deck(){
        unboxDeck();
        Collections.shuffle(playingDeck);
        discardDeck = new ArrayList<Card>();
    }

    public Card drawCard(){
        return playingDeck.remove(playingDeck.size() - 1);
    }

    public int deckSize(){
        return playingDeck.size();
    }

    public void playDeck(){
        discardDeck.add(drawCard());
        discardDeck.get(discardDeck.size() - 1).flipCard();

        if (getTopOfDiscardDeck().getValue() > 9){
            playDeck();
        }
    }
    
    public Card getTopOfDeck(){
        return playingDeck.get(playingDeck.size() - 1);
    }
    
    public Card getTopOfDiscardDeck(){
        return discardDeck.get(discardDeck.size() - 1);
    }
    
    public ArrayList<Card> dealPlayingDeck(int cardAmount){
        ArrayList<Card> hand = new ArrayList<Card>();
        for (int i = 0; i < cardAmount; i ++){
            hand.add(drawCard());
        }
        return hand;
    }

    public void addToDiscardCard(Card card){
        discardDeck.add(card);
    }

    private void resetDeck(){
        for (Card card : discardDeck) {
            card.flipCard();
            playingDeck.add(card);
        }
        Collections.shuffle(playingDeck);
    }

    public boolean isEmpty(){
        if (playingDeck.size() == 0){
            System.out.println("Emptying, adding discard deck back in");
            resetDeck();
            return true;
        }else{
            return false;
        }

    }
    
    @Override
    public String toString(){
        String output = "";
        output += getTopOfDeck().toString();
        output += "...";
        output += getTopOfDiscardDeck().toString();
        return output;
    }
}
import java.util.*;

public class Deck{
    // TODO
    ArrayList<Card> playingDeck;
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
    }

    public Card drawCard(){
        return playingDeck.remove(playingDeck.size() - 1);
    }

}
import java.util.ArrayList;

public class Computer extends Player {
    public Computer() {
        super();
    }

    @Override
    Card playCard(Deck playingDeck) {
        Card lastPlayed = playingDeck.getTopOfDiscardDeck();
        //Pick a card
        ArrayList<Card> playable = new ArrayList<Card>();
        // create a list of playable cards
        for (Card card : handCards) {
            if (card.equals(lastPlayed)){
                playable.add(card);
            }
        }
        if (playable.size() == 0){
            Card drawn = playingDeck.drawCard();
            drawn.flipCard();
            playable.add(drawn);
        }
        // choose the best card to play
        Card chosenCard = playable.get(0);
        System.out.println("Picked " +chosenCard);
        for (Card card : handCards) {
            if (chosenCard.toString().equals(card.toString())){
                System.out.println("Playing "+card);
                handCards.remove(card);
                break;
            }
        }
        if (chosenCard.canPlayCard(playingDeck, this)){

            //playingDeck.addToDiscardCard(chosenCard);
        }else{
            System.out.println("You cant play that");
            chosenCard = null;
        }
        return chosenCard;
    }

    @Override
    String displayHand() {
        String output = "";
        for (Card card : handCards) {
            card.flipCard();
            output += card;
            card.flipCard();
            output += " ";
        }
        return output;
    }

    @Override
    void gameOverAction() {
    }

}
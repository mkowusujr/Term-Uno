import java.util.ArrayList;

public class Player {
    private ArrayList<Card> handCards;
    //private int handCount;
    public Player() {
       handCards = new ArrayList<Card>();
       //handCount = handCards.size();
    }

    public void receiveHand(ArrayList<Card> deltCards){
        for (Card card : deltCards) {
            card.flipCard();
            handCards.add(card);
            //handCount += 1;
        }
    }

    public int getHandCount(){

        return handCards.size();
    }

    private Card discardFromHand(String card){
        Card played = null;
        for (int i = 0; i < handCards.size(); i++){
            if (handCards.get(i).toString().equals(card)){
                played = handCards.remove(i);
                break;
            }
        }
        return played;
    }

    public void playCard(Deck playingDeck, String played){
        Card card = discardFromHand(played);
        playingDeck.addToDiscardCard(card);
    }

    public String displayHand(){
        String output = "";
        for (Card card : handCards) {
            output += card;
            output += " ";
        }
        return output;
    }
}
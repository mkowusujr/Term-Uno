import java.util.Scanner;

public class Human extends Player {
    private static Scanner kb;

    public Human() {
        super();
        kb = new Scanner(System.in);
    }

    private Card discardFromHand(String card) {
        Card played = null;
        for (int i = 0; i < handCards.size(); i++) {
            if (handCards.get(i).toString().equals(card)) {
                played = handCards.remove(i);
                break;
            }
        }
        return played;
    }

    private Card pickCard(Deck playingDeck, String move) {
        // String move = kb.nextLine();
        if (move.equals("draw")) {
            System.out.println("Drawing card from deck...");
            Card drawn = playingDeck.drawCard();
            drawn.flipCard();
            handCards.add(drawn);
            move = drawn.toString();
            System.out.println("You drew a " + drawn + " card");
        }

        Card card = discardFromHand(move);
        if (card == null)
            handCards.remove(handCards.size() - 1);
        return card;
    }

    @Override
    Card playCard(Deck playingDeck) {
        Card playedCard = null;
        String playCard = "";
        do {
            playCard = kb.nextLine();
            playedCard = this.pickCard(playingDeck, playCard);
            if (playedCard != null && playedCard.canPlayCard(playingDeck, this)) {
                if (playingDeck.getTopOfDiscardDeck().getValue() > 12) {
                    System.out.println("What color would you like change it too");
                    System.out.println("(r)ed, (b)lue, (g)reen, (y)ellow");
                    String color = kb.nextLine();
                    Card discardTop = playingDeck.getTopOfDiscardDeck();
                    discardTop.changeColor(color);
                }
                break;
            } else if (playedCard == null) {
                System.out.println("You don't have that card");
            } else {
                System.out.println("You can't play that card");
            }
            if (!playCard.equals("draw"))
                System.out.println("Play a different Card");
            else
                return null;
        } while (!playCard.equals("draw"));
        return playedCard;
    }

    @Override
    void gameOverAction() {
        kb.close();
    }

    @Override
    public String displayHand() {
        String output = "";
        for (Card card : handCards) {
            output += card;
            output += " ";
        }
        return output;
    }
}
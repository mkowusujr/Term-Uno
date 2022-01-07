import java.util.ArrayList;
import java.lang.Thread;

public class Computer extends Player {
    public Computer() {
        super();
    }

    private void think() {
        try {
            int thinkTime = 0;
            thinkTime = (int) Math.random() * (5000 - 3000 + 1) + 3000;
            Thread.sleep(thinkTime);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    Card playCard(Deck playingDeck) {
        Card lastPlayed = playingDeck.getTopOfDiscardDeck();
        // Pick a card
        ArrayList<Card> playable = new ArrayList<Card>();
        // create a list of playable cards
        think();
        for (Card card : handCards) {
            if (card.equals(lastPlayed)) {
                playable.add(card);
            }
        }
        if (playable.size() == 0) {
            think();
            System.out.println("No playable card, drawing from deck...");
            Card drawn = playingDeck.drawCard();
            drawn.flipCard();
            playable.add(drawn);
        }
        // choose the best card to play
        Card chosenCard = playable.get(0);
        System.out.println("Picked " + chosenCard);
        for (Card card : handCards) {
            if (chosenCard.toString().equals(card.toString())) {
                System.out.println("Playing " + card);
                handCards.remove(card);
                break;
            }
        }
        think();
        if (chosenCard.canPlayCard(playingDeck, this)) {
            // cases where a color changing card is played
            if (playingDeck.getTopOfDiscardDeck().getValue() > 12) {
                char color = 's';
                for (Card card : handCards) {
                    if (card.getColor() != 's')
                        color = handCards.get(0).getColor();
                }
                Card discardTop = playingDeck.getTopOfDiscardDeck();
                think();
                System.out.print("Changing the color to ");
                switch (color) {
                    case 'r':
                        System.out.println("red");
                        break;
                    case 'b':
                        System.out.println("blue");
                        break;
                    case 'g':
                        System.out.println("green");
                        break;
                    case 'y':
                        System.out.println("yellow");
                        break;
                }
                discardTop.changeColor(color);
            }
            // playingDeck.addToDiscardCard(chosenCard);
        } else {
            System.out.println("Can't play that");
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
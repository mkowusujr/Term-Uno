import java.util.ArrayList;
import java.lang.Thread;

public class Computer extends Player {
    public Computer() {
        super();
    }

    private void think() {
        try {
            int thinkTime = 0;
            thinkTime = (int) Math.random() * (3000 - 500 + 1) + 500;
            Thread.sleep(thinkTime);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    Card playCard(PlayingDeck playingDeck) {
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
        String action = "";
        if (playable.size() == 0) {
            think();
            System.out.println("Had no playable card in hand, drawing from deck...");
            Card drawn = playingDeck.drawCard();
            drawn.flipCard();
            playable.add(drawn);
            action += "Drew a ";
        } else {
            // action += "Picked";
        }
        // choose the best card to play
        Card chosenCard = playable.get(0);
        if (action.equals("Drew a ")) {
            System.out.print(action + chosenCard + " card, ");
        }

        for (Card card : handCards) {
            if (chosenCard.toString().equals(card.toString())) {
                //System.out.println("Playing " + card);
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
                discardTop.changeColor(color);
            }
            // playingDeck.addToDiscardCard(chosenCard);
        } else {
            System.out.println("But couldn't play the " +chosenCard + " card");
            chosenCard = null;
        }
        return chosenCard;
    }

    @Override
    boolean isHuman() {
        return false;
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
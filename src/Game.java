import java.util.*;

public class Game {
    private Deck gameDeck;
    private Player[] players;
    private boolean clockwise;
    private int round;

    public Game(int numPlayers, int cardsAtStart) {
        gameDeck = new Deck();
        ArrayList<Card> deltCards;

        players = new Player[numPlayers];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player();
        }
        for (Player player : players) {
            deltCards = gameDeck.dealPlayingDeck(cardsAtStart);
            player.receiveHand(deltCards);
        }
        gameDeck.playDeck();
        clockwise = true;
        round += 1;
    }

    public void humanMove(Player player, Scanner kb) {
        String playCard = "";
        do {
            playCard = kb.nextLine();
            Card playedCard = player.playCard(gameDeck, playCard);
            if (playedCard.canPlayCard(gameDeck, player)) {
                if (gameDeck.getTopOfDiscardDeck().getValue() > 12) {
                    System.out.println("What color would you like change it too");
                    System.out.println("(r)ed, (b)lue, (g)reen, (y)ellow");
                    String color = kb.nextLine();
                    Card discardTop = gameDeck.getTopOfDiscardDeck();
                    discardTop.changeColor(color);
                }
                break;
            } else {
                System.out.println("You can't play that card");
            }
        } while (!playCard.equals("draw"));
    }

    /*
     * private void aiMove(){
     * // TODO write ai
     * }
     */

    private int nextPlayer(int lastPos) {
        int currentPos = lastPos;
        if (clockwise == true) { // go right
            if (lastPos + 1 == players.length) {
                currentPos = 0;
            } else {
                currentPos += 1;
            }
        } else { // go left
            if (lastPos - 1 < 0) {
                currentPos = players.length - 1;
            } else {
                currentPos -= 1;
            }
        }
        return currentPos;
    }

    private int whoGoesNext(int lastPos, boolean skip) {
        int currentPos = nextPlayer(lastPos);
        if (skip == true) {
            return whoGoesNext(currentPos, false);
        } else {
            return currentPos;
        }
    }

    public String playGame(int startingPlayer, Scanner kb) {

        boolean skip = false;
        int nextPos;
        Player player = players[startingPlayer];

        System.out.println(gameDeck);
        System.out.println("It is player number " + (startingPlayer + 1) + "'s turn!");
        System.out.println("Your current hand is: " + player.displayHand());

        // make player move
        humanMove(player, kb);
        Card cardPlayed = gameDeck.getTopOfDiscardDeck();
        int topCardVal = cardPlayed.getValue();
        if (topCardVal > 9) { // Special card played
            int nextPlayer;
            ArrayList<Card> deltCards;
            switch (topCardVal) {
                case 10: // skip card
                    skip = true;
                    break;
                case 11: // reverse card
                    clockwise = !clockwise;
                    break;
                case 12: // draw two card
                    nextPlayer = nextPlayer(startingPlayer);
                    deltCards = gameDeck.dealPlayingDeck(2);
                    players[nextPlayer].receiveHand(deltCards);
                    skip = true;
                    break;
                case 13: // wild card, handled in elsewhere
                    break;
                case 14: // draw 4 wild card
                    nextPlayer = nextPlayer(startingPlayer);
                    deltCards = gameDeck.dealPlayingDeck(4);
                    players[nextPlayer].receiveHand(deltCards);
                    skip = true;
                    break;
            }
        }

        if (player.getHandCount() == 1) {
            // TODO UNO method
            System.out.println("UNO!\n");
            nextPos = whoGoesNext(startingPlayer, skip);
            return playGame(nextPos, kb);
        } else if (player.getHandCount() == 0) {
            return ("player " + startingPlayer + " wins!");
        } else {
            nextPos = whoGoesNext(startingPlayer, skip);
            System.out.println("\n");
            return playGame(nextPos, kb);
        }
    }

    public int currentRound() {
        return round;
    }

    public static void main(String[] args) {
        Game newGame = new Game(2, 7);
        Scanner kb = new Scanner(System.in);
        System.out.println("Round " + newGame.currentRound());
        System.out.println(newGame.playGame(0, kb));
        kb.close();
    }
}
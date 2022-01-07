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
            players[i] = new Human();
        }
        for (Player player : players) {
            deltCards = gameDeck.dealPlayingDeck(cardsAtStart);
            player.receiveHand(deltCards);
        }
        gameDeck.playDeck();
        clockwise = true;
        round += 1;
    }

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

    public String playGame(int startingPlayer) {

        boolean skip = false;
        int nextPos;
        Player player = players[startingPlayer];

        System.out.println(gameDeck);
        System.out.println("It is player number " + (startingPlayer + 1) +
                "'s turn!");
        System.out.printf("Player %d current hand is: %s\n", (startingPlayer + 1),
                player.displayHand());
        System.out.println("Play a card");
        Card cardPlayed = player.playCard(gameDeck); // gameDeck.getTopOfDiscardDeck();
        if (cardPlayed != null) {
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
                        System.out.print("The direction got reverse, we are now playing ");
                        if (clockwise)
                            System.out.println("clockwise");
                        else
                            System.out.println("counter-clockwise");

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

        }

        if (player.getHandCount() == 1) {
            // TODO UNO method
            System.out.println("UNO!\n");
            nextPos = whoGoesNext(startingPlayer, skip);
            return playGame(nextPos);
        } else if (player.getHandCount() == 0) {
            player.gameOverAction();
            return ("player " + startingPlayer + " wins!");
        } else {
            nextPos = whoGoesNext(startingPlayer, skip);
            if (skip == true) {
                System.out.println("\n\nPlayer " + (nextPlayer(startingPlayer) + 1) +
                        " got skipped!");
            }
            System.out.println("\n");
            return playGame(nextPos);
        }
    }

    public int currentRound() {
        return round;
    }

    public static void main(String[] args) {
        Game newGame = new Game(2, 7);
        System.out.println("Round " + newGame.currentRound());
        System.out.println(newGame.playGame(0));
    }
}
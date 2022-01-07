import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Thread;

public class Game {
    private Deck gameDeck;
    private Player[] players;
    private boolean clockwise;

    public Game(int numPlayers, int cardsAtStart) {
        gameDeck = new Deck();
        ArrayList<Card> deltCards;

        players = new Player[numPlayers + 1];
        players[0] = new Human();
        for (int i = 1; i < players.length; i++) {
            players[i] = new Computer();
        }
        for (Player player : players) {
            deltCards = gameDeck.dealPlayingDeck(cardsAtStart);
            player.receiveHand(deltCards);
        }
        gameDeck.playDeck();
        clockwise = true;
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

    private void display(int startingPlayer) {
        Player player = players[startingPlayer];
        String p;
        if (player.isHuman()) {
            p = "Your";
        } else {
            p = "Player " + (startingPlayer + 1) + "'s";
        }
        // System.out.println(gameDeck);
        System.out.printf("It is %s Turn!\n", p);
        System.out.printf("%s Hand is: %s\n", p, player.displayHand());
        Card discardTop = gameDeck.getTopOfDiscardDeck();
        System.out.println("The top of the Discard Pile is a " + discardTop + " card");
        if (player.isHuman()) {
            System.out.print("Play a card... ");
        } else {
            System.out.println("Play a card...");
        }
    }

    public String playGame(int startingPlayer) {

        boolean skip = false;
        int nextPos;
        Player player = players[startingPlayer];
        display(startingPlayer);
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
            return ("player " + (startingPlayer + 1) + " wins!");
        } else {
            nextPos = whoGoesNext(startingPlayer, skip);
            if (skip == true) {
                System.out.println("\n\nPlayer " + (nextPlayer(startingPlayer) + 1) +
                        " got skipped!");
            }
            System.out.println("\n");
            // Wait a bit before moving on
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println(e);
            }
            return playGame(nextPos);
        }
    }

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.print("How many computer opponents do you want to play (1-7): ");
        int numOfPlayers = kb.nextInt();
        System.out.print("How cards do you want to start with: ");
        int numOfCards = kb.nextInt();
        System.out.println();
        Game newGame = new Game(numOfPlayers, numOfCards);
        System.out.println(newGame.playGame(0));
        kb.close();
    }
}
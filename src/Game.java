import java.util.*;

public class Game {
    private Player[] players;
    private Deck gameDeck;
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
        round += 1;
    }

    public void humanPlay(Player player, Scanner kb) {
        System.out.println(gameDeck);
        System.out.println("You current hand is: " + player.displayHand());
        System.out.println("Your Turn");
        while (true) {
                String playCard = kb.nextLine();
            if (player.playCard(gameDeck, playCard)) {
                System.out.println("\n");
                // continue;
                break;
            } else {
                System.out.println("You can't play that card");
                System.out.println(gameDeck);
            }
        }
    }

    public int currentRound() {
        return round;
    }

    public static void main(String[] args) {
        Game newGame = new Game(2, 7);
        Scanner kb = new Scanner(System.in);
        System.out.println("Round " + newGame.currentRound());
        while(true){
            newGame.humanPlay(newGame.players[0], kb);
            if (newGame.players[0].getHandCount() == 0)
                break;
        }
        kb.close();
    }
}
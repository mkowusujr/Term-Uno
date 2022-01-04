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

    /*
     * private boolean playedSpecial() {
     * if (gameDeck.getTopOfDiscardDeck().getValue() > 10) {
     * return true;
     * } else {
     * return false;
     * }
     * }
     * 
     * private void handleSpecials(Player player) {
     * int topCardValue = gameDeck.getTopOfDiscardDeck().getValue();
     * switch (topCardValue) {
     * case 10: // skip
     * 
     * break;
     * case 11: // reverse
     * break;
     * case 12: // draw 2
     * player.receiveHand( gameDeck.dealPlayingDeck(2) );
     * break;
     * case 13: // wild card
     * break;
     * case 14: // draw 4
     * player.receiveHand( gameDeck.dealPlayingDeck(4) );
     * break;
     * }
     * }
     * 
     * public void playRound() {
     * // humanPlay(players[0]);
     * for (int i = 0; i < players.length; i++) {
     * // player make move
     * if (playedSpecial() == true) {// {
     * if (i + 1 >= players.length) {
     * handleSpecials(players[0]);
     * } else {
     * handleSpecials(players[i++]);
     * }
     * }
     * }
     * // }else{
     * // humanPlay(players[1]);
     * // }
     * }
     */
    public void humanPlay(Player player) {
        Scanner kb = new Scanner(System.in);
        System.out.println(gameDeck);
        System.out.println("You current hand is: " + player.displayHand());
        System.out.println("Your Turn");
        while (true) {
            String playCard = kb.next();
            if (player.playCard(gameDeck, playCard)) {
                System.out.println("\n");
                // continue;
                break;
            } else {
                System.out.println("You can't play that card");
                System.out.println(gameDeck);
            }
        }
        kb.close();
    }

    public int currentRound() {
        return round;
    }

    public static void main(String[] args) {
        Game newGame = new Game(2, 7);

        System.out.println("Round " + newGame.currentRound());
        newGame.humanPlay(newGame.players[0]);
        /*
         * Player p1 = new Player();
         * p1.receiveHand( gameDeck.dealPlayingDeck(7) );
         * Player p2 = new Player();
         * p2.receiveHand( gameDeck.dealPlayingDeck(7) );
         * gameDeck.playDeck();
         * /*System.out.println("Player hand count " + p1.getHandCount());
         * System.out.println(gameDeck);
         * System.out.println("You current hand is: " + p1.displayHand());
         */

    }
}
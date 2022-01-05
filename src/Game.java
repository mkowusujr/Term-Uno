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
    
    // private void aiMove(){
    //     // TODO write ai
    // }
    
    private int nextPlayer(int lastPos){
        int currentPos = lastPos;
        if (clockwise == true){ // go right
            if (lastPos + 1 == players.length){
                currentPos = 0;
            }else{
                currentPos += 1;
            }
        }else{ // go left
            if(lastPos - 1 < 0){ 
                currentPos = players.length - 1;
            }else{
                currentPos -= 1;
            }
        }
        return currentPos;
    }

    private int whoGoesNext(int lastPos, boolean skip){
        int currentPos = nextPlayer(lastPos);
        if (skip == true){
            return whoGoesNext(currentPos, false);
        }else{
            return currentPos;
        }
    }

    public String playGame(int startingPlayer, Scanner kb){
        boolean skip = false;
        int nextPos;
        // make player move
        Player player = players[startingPlayer];
        humanMove(player, kb);
        Card cardPlayed = gameDeck.getTopOfDiscardDeck();
        int topCardVal = cardPlayed.getValue();
        if (topCardVal > 9) { // Special card played
            int nextPlayer;
            ArrayList<Card> deltCards;
            switch(topCardVal){
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
                case 13: // wild card
                    break;
                case 14: // draw 4 wild card
                    nextPlayer = nextPlayer(startingPlayer);
                    deltCards = gameDeck.dealPlayingDeck(4);
                    players[nextPlayer].receiveHand(deltCards);
                    skip = true;
                    break;
            }
        }
        if (player.getHandCount() == 1){
            // TODO UNO
            System.out.println("UNO!");
            nextPos = whoGoesNext(startingPlayer, skip);
            return playGame(nextPos, kb);
        }else if (player.getHandCount() == 0){
            return "player " + startingPlayer + " wins!";
        }else{
            nextPos = whoGoesNext(startingPlayer, skip);
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
        newGame.playGame(0, kb);
        kb.close();
    }
}
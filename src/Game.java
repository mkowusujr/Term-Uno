import java.util.*;
public class Game {
    public static void main(String[] args) {
        Deck gameDeck = new Deck();
        Player p1 = new Player();
        p1.receiveHand( gameDeck.dealPlayingDeck(7) );
        gameDeck.playDeck();
        System.out.println("Player hand count " + p1.getHandCount());
        System.out.println(gameDeck);
        System.out.println("You current hand is: " + p1.displayHand());

        Scanner kb = new Scanner(System.in);
        while(true){
            System.out.println(gameDeck);
            System.out.println("You current hand is: " + p1.displayHand());
            System.out.println("Play a card");
            String playCard = kb.next();
            if (p1.playCard(gameDeck, playCard)){
                continue;
                //break;
            }else{
                System.out.println("You can't play that card");
            }
        }

        //kb.close();
    }
}
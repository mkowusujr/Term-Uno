public class Game {
    public static void main(String[] args) {
        // Testing Card Class
        Card[] deck = {
                new Card('r', 1),
                new Card('r', 2),
                new Card('b', 1),
                new Card('y', 12)
        };
        for (Card card : deck) {
            System.out.print(card + "  ");
        }
        System.out.print("\n");

        System.out.print("Are r4 and y4 equal?");
        Card red = new Card('r', 1);
        Card blue = new Card('b', 1);
        System.out.println("Are r4 and y4 equal? " + red.equals(blue));
        Card green = new Card('g', 4);
        Card greendos = new Card('g', 7);
        System.out.println("Are g4 and g7 equal? " + green.equals(greendos));

        // Check for False
        red = new Card('r', 5);
        blue = new Card('b', 4);
        System.out.println("Are r5 and y4 equal? " + red.equals(blue));

        
        Deck gameDeck = new Deck();
        gameDeck.playDeck();
        System.out.println(gameDeck.toString());
        System.out.println("Drawing Seven Cards");
        for (int i = 0; i < 7; i++)
            System.out.print(gameDeck.drawCard() + " ");
        System.out.print("\n");
    }
}
public class Game {
    public static void main(String[] args) {
        // Testing Card Class
        Card[] deck = {
                new Card('r', "1"),
                new Card('r', "2"),
                new Card('b', "1"),
                new Card('y', "+2")
        };
        for (Card card : deck) {
            System.out.print(card + "  ");
        }
        System.out.print("\n");

        System.out.print("Are r4 and y4 equal?");
        Card red = new Card('r', "4");
        Card blue = new Card('b', "4");
        System.out.println("Are r4 and y4 equal? " + red.equals(blue));
        Card green = new Card('g', "4");
        Card greendos = new Card('g', "7");
        System.out.println("Are g4 and g7 equal? " + green.equals(greendos));

        // Check for False
        red = new Card('r', "5");
        blue = new Card('b', "4");
        System.out.println("Are r5 and y4 equal? " + red.equals(blue));

    }
}
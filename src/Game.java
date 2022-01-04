public class Game{
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
    }
}
public class Computer extends Player {
    public Computer() {
        super();
    }

    @Override
    Card playCard(Deck playingDeck) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    String displayHand() {
        // TODO Auto-generated method stub
        String output = "";
        for (Card card : handCards) {
            output += "u";
            output += " ";
        }
        return output;
    }

    @Override
    void gameOverAction() {
        // TODO Auto-generated method stub

    }

}
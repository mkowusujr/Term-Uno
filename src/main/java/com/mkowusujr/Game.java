package com.mkowusujr;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Thread;

/**
 * The class that handles the creating and running the Uno Game
 * 
 * @author Mathew Owusu Jr
 */
public class Game {
    private PlayingDeck playingDeck;
    private DiscardDeck discardDeck;
    private Player[] players;
    private boolean clockwise;
    private boolean isGameOver;

    /**
     * The Class Constructor.
     * Sets up the Uno game. Creates a new playing deck. Creates the list of
     * players.
     * The first player will always be a human, the rest will be computer players.
     * The amount of cards at the start of the game will be dealt outto all players.
     * Lastly a discard deck will be created
     * 
     * @param numOfPlayers      Number of players at the start of the game
     * @param numOfCardsAtStart Number of cards each player starts with
     */
    public Game(int numOfPlayers, int numOfCardsAtStart) {
        playingDeck = new PlayingDeck();
        createListOfPlayers(numOfPlayers);
        dealPlayingDeckToPlayers(numOfCardsAtStart);
        discardDeck = new DiscardDeck(playingDeck);
        clockwise = true;
    }

    /**
     * Creates the players of the game. The first player is a human, the rest are
     * Computer
     * Players.
     * 
     * @param numOfPlayers Number of players at the start of the game
     */
    private void createListOfPlayers(int numOfPlayers) {
        players = new Player[numOfPlayers + 1];
        players[0] = new Human();
        for (int i = 1; i < players.length; i++) {
            players[i] = new Computer();
        }
    }

    /**
     * Distributes the amount of cards specified at the start of the game to all the
     * Players of the game
     * 
     * @param numOfCardsAtStart Number of cards each player starts with
     */
    private void dealPlayingDeckToPlayers(int numOfCardsAtStart) {
        System.out.println("Dealing out playing deck...\n");
        ArrayList<Card> dealtCards;
        for (Player player : players) {
            dealtCards = playingDeck.dealPlayingDeck(numOfCardsAtStart);
            player.addToHand(dealtCards);
        }
    }

    /**
     * Finds the next player in line to go using the last player's postion in the
     * list,
     * and whether or not the game is being played clockwise or counter clockwise
     * 
     * @param lastPosition The position of the last player that went
     * @return The position of the next player to go
     */
    private int findNextPlayer(int lastPosition) {
        int currentPos = lastPosition;
        if (clockwise == true) { // go right
            if (lastPosition + 1 == players.length) {
                currentPos = 0;
            } else {
                currentPos += 1;
            }
        } else { // go left
            if (lastPosition - 1 < 0) {
                currentPos = players.length - 1;
            } else {
                currentPos -= 1;
            }
        }
        return currentPos;
    }

    /**
     * Determines which player will go next.
     * If the next player got skipped by a card played, whoGoesNext is called again
     * with the skip flag set to false to find out which player gets to go instead
     * 
     * @param lastPosition The position of the last player to go
     * @param skip         Whether or not the next player should be skipped
     * @return The positon of the next player to get a turn
     */
    private int whoGoesNext(int lastPosition, boolean skip) {
        int nextToGo = findNextPlayer(lastPosition);
        if (skip == true) {
            System.out.println("\n\nPlayer " + (nextToGo + 1) + " got skipped!");
            return whoGoesNext(nextToGo, false);
        } else {
            return nextToGo;
        }
    }

    /**
     * The message logged to the Terminal at the start of a round
     * 
     * @param currentPlayerPosition The array index of the current Player in the
     *                              list of
     *                              Players
     */
    private void logStartOfTurn(int currentPlayerPosition) {
        Player player = players[currentPlayerPosition];

        String playerString;
        playerString = player.isHuman() ? "Your" : "Player " + (currentPlayerPosition + 1) + "'s";
        System.out.printf("It is %s Turn!\n", playerString);
        System.out.printf("The cards in %s hand are: %s\n", playerString.toLowerCase(), player.displayHand());

        Card lastPlayedCard = discardDeck.getTopOfDeck();
        System.out.println("The top of the Discard Pile is a " + lastPlayedCard + " card");

        if (player.isHuman()) {
            System.out.print("Play a card: ");
        } else {
            System.out.println("Playing a card...");
        }
    }

    /**
     * Handles the last card played
     * 
     * @param cardPlayed            The last card played
     * @param skip                  Whether or not the next player in line to play
     *                              will get skipped
     * @param currentPlayerPosition The position of the player that played the card
     *                              on top.
     * @return The boolean value of the skip flag
     */
    private boolean handlePlayedCard(Card cardPlayed, boolean skip, int currentPlayerPosition, Player player) {
        int topCardVal = cardPlayed.getValue();
        if (topCardVal > 9) { // Special card played
            skip = handleSpecialCard(topCardVal, skip, currentPlayerPosition);
        }

        switch (player.getHandCount()) {
            case 1: // if a player has one card, prints UNO! to terminal
                System.out.println("UNO!\n");
                int nextPosition = whoGoesNext(currentPlayerPosition, skip);
                playGame(nextPosition);
                break;
            case 0: // if a player runs out of cards, the game ends
                player.gameOverAction();
                System.out.println("player " + (currentPlayerPosition + 1) + " wins!");
                isGameOver = true;
                break;
        }

        return skip;
    }

    /**
     * Handles the affects of specail cards
     * 
     * @param topCardVal            The card value of the card on the top of the
     *                              discard eck
     * @param skip                  Whether or not the next player in line to play
     *                              will get skipped
     * @param currentPlayerPosition The position of the player that played the card
     *                              on top.
     * @return the boolean value of the skip flag
     */
    private boolean handleSpecialCard(int topCardVal, boolean skip, int currentPlayerPosition) {
        int nextPlayer;
        ArrayList<Card> dealtCards;
        switch (topCardVal) {
            case 10: // skip card
                skip = true;
                break;
            case 11: // reverse card
                clockwise = !clockwise;
                System.out.print("The direction got reversed, we are now playing in ");
                if (clockwise)
                    System.out.println("clockwise");
                else
                    System.out.println("counter-clockwise");

                break;
            case 12: // draw two card
                nextPlayer = findNextPlayer(currentPlayerPosition);
                dealtCards = playingDeck.dealPlayingDeck(2);
                players[nextPlayer].addToHand(dealtCards);
                skip = true;
                break;
            case 13: // wild card, handled by Player Objects
                break;
            case 14: // draw 4 wild card
                nextPlayer = findNextPlayer(currentPlayerPosition);
                dealtCards = playingDeck.dealPlayingDeck(4);
                players[nextPlayer].addToHand(dealtCards);
                skip = true;
                break;
        }
        return skip;
    }

    /**
     * 
     * @param currentPlayerPosition
     * @param skip
     */
    private void goToNextPlayer(int currentPlayerPosition, boolean skip) {
        int nextPosition = whoGoesNext(currentPlayerPosition, skip);
        System.out.println("\n");
        gameLag();
        playGame(nextPosition);
    }

    /**
     * Introduces lag to the game to make it easier to follow
     */
    private void gameLag() {
        final int milliseconds = 1000;
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Runs the Uno game
     * 
     * @param currentPlayerPosition The array index of the current Player in the
     *                              list of
     *                              Players
     */
    public void playGame(int currentPlayerPosition) {
        if (isGameOver == false) {
            boolean skip = false;

            logStartOfTurn(currentPlayerPosition);

            Player player = players[currentPlayerPosition];
            Card cardPlayed = player.makeMove(playingDeck, discardDeck);

            if (cardPlayed != null) {
                skip = handlePlayedCard(cardPlayed, skip, currentPlayerPosition, player);
            }

            goToNextPlayer(currentPlayerPosition, skip);
        }
    }

    /**
     * Prints the welcome text to the screen
     */
    private static void printWelcomeText() {
        System.out.println("""
                 **********                                  **     **
                /////**///                                  /**    /**
                    /**      *****  ****** **********       /**    /** *******   ******
                    /**     **///**//**//*//**//**//** *****/**    /**//**///** **////**
                    /**    /******* /** /  /** /** /**///// /**    /** /**  /**/**   /**
                    /**    /**////  /**    /** /** /**      /**    /** /**  /**/**   /**
                    /**    //******/***    *** /** /**      //*******  ***  /**//******
                    //      ////// ///    ///  //  //        ///////  ///   //  //////

                Welcome to Term-Uno. Play vanilla uno from the comfort of your terminal!
                """);
    }

    /**
     * Prints a short tutorial to the terminal
     */
    private static void printHelpText() {
        System.out.println("""
                ---------------------------- Tutorial -----------------------------------
                To play a card, type the card you would like to play.
                For example, if you have the card 'b+2', when it is your turn to play,
                type 'b+2' to play the card.

                If you do not have a playable card, type 'draw' to draw from the deck.

                When it is your turn, type 'help' to see this tutorial text again. 
                -------------------------- End of Tutorial ------------------------------
                """);
    }

    /**
     * Prompts the user for the settings they want to play the game with.
     * Then it sets up the game and runs it.
     */
    private static void runGame() {
        Scanner kb = new Scanner(System.in);

        System.out.print("How many computer opponents do you want to play (1-7): ");
        int numOfPlayers = kb.nextInt();

        System.out.print("How cards do you want to start with: ");
        int numOfCards = kb.nextInt();

        System.out.println();
        Game newGame = new Game(numOfPlayers, numOfCards);
        newGame.playGame(0);
        System.out.println("Game Over");
        kb.close();
    }

    /**
     * Entry point for the game
     * 
     * @param args Command Arguments
     *             Will be used in feature version of project
     */
    public static void main(String[] args) {
        printWelcomeText();
        printHelpText();
        runGame();
    }
}
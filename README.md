# ![term-uno](/imgs/term-uno.png)
This program is a recreation of vanilla uno using Java Object Orienented Programming.
It is run through the terminal, and the user plays the game by intereacting with prompts from the terminal
![Demo](imgs/text-based-uno-demo.gif)
Demo from a full game against two computer players.
## Symbols Guide
### The Cards
In Uno there are 5 different types of card colors
* red
* green
* blue
* yellow
* black/wild card (these cards allow you to change its color to one of the previous listen ones)

These are represented in the game as `r`, `g`, `b`, `y`, and `s` respectively

In Uno the cards are number 0-9 (inclusive) and in addition to that there are specials types of cards.
These special cards are the following
* Skip Card - skips the next player
* Reverse Card - changes the direction of the game
* Draw Two Card - the next player draws two cards and loses a turn
* Wild Card - changes the color of the card itself when played
* Draw Four WIld Card - the next player draws four cards, loses a turn, and the user gets to change the color of this card

These are represented as:
* `x` for skip cards
* `<>` for reverse cards
* `+2` for draw two cards
* `w` for wild cards
* `+4` for the draw four card
In Uno each card as a color and a face value that is either a number or a special action
Here are some examples our how an uno card would be represented in game:
* a yellow 5 card would be `y5`
* a red skip card would be `rx`
* a wild card would be `sw` and a draw four card would be `s+4`
    * note: after changing the color of one of these types of cards the `s` would be changed to the letter of the new color
The game represents face down cards as `u`, these are meant to simulate how the back of uno cards have say uno on the back


# Installing and Running the Game
Clone the reprository and cd into it `gitclone https://github.com/mkowusujr/Term-Uno.git`

Then run the following command to execute the project output and run the game, `java -cp output Game`.
You must have java installed on your machince to when that command.

# Credit
I got the font from [this site](https://fontmeme.com/uno-card-game-font/).
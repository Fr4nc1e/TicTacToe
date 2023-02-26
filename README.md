***Name : DENG Yuchen, Student ID : 57585082***

## Section A - Basic Information

### Project Title

1. English: Tic Tac Toe
2. Chinese: 井字游戏

### Abstract

**Tic-tac-toe**, **noughts and cross**, or **Xs and Os** is a for two players who take turns marking the spaces in a three-by-three grid with _X_ or _O_. The player who succeeds in placing three of their marks in a horizontal, vertical, or diagonal row is the winner. It is a solved game, with a forced draw assuming best play from both players.

### Impact and Objectives

1. Long-Term Imapct: It is an Android application with AI player, which is determined to enhance the experience of user.
2. Objectives: 
		1. The fundamental Tic Tac Toe game funcionalities.
		2. Making the app support multiple language
		3. Improve the game to support both portrait and landscape display mode
		4. In the basic game implementation, the human always goes first. Make the game fairer by alternating who gets to go first. 
		5. Keep track of how many games the user has won, the computer has won, and ties.
		6. To create options menu with two functions o Difficulty – to set the AI difficulty level to Easy (Level 1), Harder (Level 2), or Expert (Level 3) o Quit – to quit the app.
		7. Adding animation and sound effects to the game.

## Section B - Background

### General Background

A player can play a perfect game. of tic-tac-toe (to win or at least draw) if, each time it is their turn to play, they choose the first available move from the following list, as used in Newell and Simon's 1972 tic-tac-toe program.

1.  Win: If the player has two in a row, they can place a third to get three in a row.
2.  Block: If the opponent has two in a row, the player must play the third themselves to block the opponent.
3.  Fork: Cause a scenario where the player has two ways to win (two non-blocked lines of 2).
4.  Blocking an opponent's fork: If there is only one possible fork for the opponent, the player should block it. Otherwise, the player should block all forks in any way that simultaneously allows them to make two in a row. Otherwise, the player should make a two in a row to force the opponent into defending, as long as it does not result in them producing a fork. For example, if "X" has two opposite corners and "O" has the center, "O" must not play a corner move to win. (Playing a corner move in this scenario produces a fork for "X" to win.)
5.  Center: A player marks the center. (If it is the first move of the game, playing a corner move gives the second player more opportunities to make a mistake and may therefore be the better choice; however, it makes no difference between perfect players.)
6.  Opposite corner: If the opponent is in the corner, the player plays the opposite corner.
7.  Empty corner: The player plays in a corner square.
8.  Empty side: The player plays in a middle square on any of the four sides.

The first player, who shall be designated "X", has three possible strategically distinct positions to mark during the first turn. Superficially, it might seem that there are nine possible positions, corresponding to the nine squares in the grid. However, by rotating the board, we will find that, in the first turn, every corner mark is strategically equivalent to every other corner mark. The same is true of every edge (side middle) mark. From a strategic point of view, there are therefore only three possible first marks: corner, edge, or center. Player X can win or force a draw from any of these starting marks; however, playing the corner gives the opponent the smallest choice of squares which must be played to avoid losing. This might suggest that the corner 17is the best opening move for X, however another study shows that if the players are not perfect, an opening move in the center is best for X.

The second player, who shall be designated "O", must respond to X's opening mark in such a way as to avoid the forced win. Player O must always respond to a corner opening with a center mark, and to a center opening with a corner mark. An edge opening must be answered either with a center mark, a corner mark next to the X, or an edge mark opposite the X. Any other responses will allow X to force the win. Once the opening is completed, O's task is to follow the above list of priorities in order to force the draw, or else to gain a win if X makes a weak play.

More detailed, to guarantee a draw, O should adopt the following strategies:

-   If X plays a corner opening move, O should take center, and then an edge, forcing X to block in the next move. This will stop any forks from happening. When both X and O are perfect players and X chooses to start by marking a corner, O takes the center, and X takes the corner opposite the original. In that case, O is free to choose any edge as its second move. However, if X is not a perfect player and has played a corner and then an edge, O should not play the opposite edge as its second move, because then X is not forced to block in the next move and can fork.
-   If X plays edge opening move, O should take center or one of the corners adjacent to X, and then follow the above list of priorities, mainly paying attention to block forks.
-   If X plays the center opening move, O should take a corner, and then follow the above list of priorities, mainly paying attention to block forks.

When X plays corner first, and O is not a perfect player, the following may happen:

-   If O responds with a center mark (best move for them), a perfect X player will take the corner opposite the original. Then O should play an edge. However, if O plays a corner as its second move, a perfect X player will mark the remaining corner, blocking O's 3-in-a-row and making their own fork.
-   If O responds with a corner mark, X is guaranteed to win, by simply taking any of the other two corners and then the last, a fork. (since when X takes the third corner, O can only take the position between the two Xs. Then X can take the only remaining corner to win)
-   If O responds with an edge mark, X is guaranteed to win, by taking center, then O can only take the corner opposite the corner which X plays first. Finally, X can take a corner to create a fork, and then X will win on the next move.

When considering only the state of the board, and after taking into account board symmetries (i.e. rotations and reflections), there are only 138 terminal board positions. A combinatorics study of the game shows that when "X" makes the first move every time, the game outcomes are as follows:

-   91 distinct positions are won by (X)
-   44 distinct positions are won by (O)
-   3 distinct positions are drawn (often called a "cat's game")

Variations of tic-tac-toe include:

-   3-dimensional tic-tac-toe on a 3×3×3 board. In this game, the first player has an easy win by playing in the centre if 2 people are playing.

One can play on a board of 4x4 squares, winning in several ways. Winning can include: 4 in a straight line, 4 in a diagonal line, 4 in a diamond, or 4 to make a square.

Another variant, Qubic, is played on a 4×4×4 board; it was solved by Oren Patashnik in 1980 (the first player can force a win). Higher dimensional variations are also possible.

-   In misère tic-tac-toe, the player wins if the opponent gets _n_ in a row. A 3×3 game is a draw. More generally, the first player can draw or win on any board (of any dimension) whose side length is odd, by playing first in the central cell and then mirroring the opponent's moves.
-   In "wild" tic-tac-toe, players can choose to place either X or O on each move.
-   Number Scrabble or Pick15 is isomorphic to tic-tac-toe but on the surface appears completely different. Two players in turn say a number between one and nine. A particular number may not be repeated. The game is won by the player who has said three numbers whose sum is 15. If all the numbers are used and no one gets three numbers that add up to 15 then the game is a draw. Plotting these numbers on a 3×3 magic square shows that the game exactly corresponds with tic-tac-toe, since three numbers will be arranged in a straight line if and only if they total 15.
-   Another isomorphic game uses a list of nine carefully chosen words, for instance "try", "be", "on", "any", "boat", "or", "mare", "by", and "ten". Each player picks one word in turn and to win, a player must select three words with the same letter. The words may be plotted on a tic-tac-toe grid in such a way that a three-in-a-row line wins.
-   Numerical Tic Tac Toe is a variation invented by the mathematician Ronald Graham. The numbers 1 to 9 are used in this game. The first player plays with the odd numbers, the second player plays with the even numbers. All numbers can be used only once. The player who puts down 15 points in a line wins (sum of 3 numbers).
-   In the 1970s, there was a two player game made by Toys & Games called _Check Lines_, in which the board consisted of eleven holes arranged in a geometrical. pattern of twelve straight lines each containing three of the holes. Each player had exactly five tokens and played in turn placing one token in any of the holes. The winner was the first player whose tokens were arranged in two lines of three (which by definition were intersecting lines. If neither player had won by the tenth turn, subsequent turns consisted of moving one of one's own tokens to the remaining empty hole, with the constraint that this move could only be from an adjacent hole.
-   There is also a variant of the game with the classic 3×3 field, in which it is necessary to make two rows to win, while the opposing algorithm only needs one.
-   Quantum tic tac toe allows players to place a quantum superposition of numbers on the board, i.e. the players' moves are "superpositions" of plays in the original classical game. This variation was invented by Allan Goff of Novatia Labs.

### Market Research

Games played on three-in-a-row boards can be traced back to ancient Egypt, where such game boards have been found on roofing tiles dating from around 1300 BC.

An early variation of tic-tac-toe was played in the Roman Empire, around the first century BC. It was called _terni lapilli_ (_three pebbles at a time_) and instead of having any number of pieces, each player had only three; thus, they had to move them around to empty spaces to keep playing. The game's grid markings have been found chalked all over Rome. Another closely related ancient game is three men's morris which is also played on a simple grid and requires three pieces in a row to finish, and Picaria, a game of the Puebloans.

The different names of the game are more recent. The first print reference to "noughts and crosses" (nought being an alternative word for 'zero'), the British name, appeared in 1858, in an issue of _Notes and Queries_.  The first print reference to a game called "tick-tack-toe" occurred in 1884, but referred to "a children's game played on a slate, consisting of trying with the eyes shut to bring the pencil down on one of the numbers of a set, the number hit being scored". "Tic-tac-toe" may also derive from "tick-tack", the name of an old version of backgammon first described in 1558. The US renaming of "noughts and crosses" to "tic-tac-toe" occurred in the 20th century.

In 1952, _OXO_ (or _Noughts and Crosses_), developed by British computer scientist Sandy Douglas for the EDSAC computer at the University of Cambridge, became one of the first known video games. The computer player could play perfect games of tic-tac-toe against a human opponent.

In 1975, tic-tac-toe was also used by MIT students to demonstrate the computational power of Tinkertoy elements. The Tinkertoy computer, made out of (almost) only Tinkertoys, is able to play tic-tac-toe perfectly. It is currently on display at the Museum of Science, Boston.

## Section C - Design Details

### App Design

This app will support **portrait** and **landscape** mode, different languages such as **Chinese** and **English** are also supported. so the UI design will be introduced in two parts. 

-  Splash Screen
The both have the same splash screen.

[Pasted image 20230221133449](https://user-images.githubusercontent.com/75559690/221407524-e0c4f31c-d07d-4140-9725-4198f9cd8329.png)

#### Portrait Mode

-  Home Screen

![Pasted image 20230221143617](https://user-images.githubusercontent.com/75559690/221407535-126f48a7-19f7-468c-a4a4-dbf4033cb901.png)

In this screen, you can click the first button with text "Play with AI" to enter the game screen or you can click the second button to play with friends.

- Game Screen

![[Pasted image 20230221134949.png | 200]] ![[Pasted image 20230221143704.png | 200]]

The left one is the screen for playing with AI, the other one is the screen used for playing with friends.

In this screen, we can see there is a top app bar at the top of the screen. Inside the bar, there are a back button and a menu button.

![[Pasted image 20230221135827.png | 200]] ![[Pasted image 20230221140017.png | 200]] ![[Pasted image 20230221135745.png | 200]] 

The drop menu contains three different items.
- First, it is the link to the introduction of Tic Tac Toe game.
- Then, It is a difficulty selector, which is used to select different difficulty levels, including Easy, Harder, Expert. When you click the second item, there will be a AlertDialog showing up.
- At last, it is an item used to quit the app.

We can see at the top, there is a score board, which is used to store temporary game score of each user.

![[Pasted image 20230221144019.png]]

The game board is one the middle of the screen which is used to play the game. In the following pictures,
- The first one shows the click effect on the game board.
- The second and third one shows the victory screenshot of the app.
- The last one shows the draw screenshot of the app.

![[Pasted image 20230221144645.png|150]] ![[Pasted image 20230221144813.png|150]] ![[Pasted image 20230221144849.png|150]] ![[Pasted image 20230221144924.png|150]]

Before playing the game, you can see the buttons at the bottom end.
- The first one is used to determine whether you or the AI go first.

![[Pasted image 20230221144516.png|200]]

- The second one is used to start a game, once you click the "Start" button, you do not need to click again.
- The third button is used to play another game after the game is completed.

#### Landscape Mode

Since I have introduced the main function of the app, then, in the remaining display, I will not introduce the funtionalities again, only show the UI design.

- Home Screen

![[Pasted image 20230221150212.png | 600]]

- Game Screen

![[Pasted image 20230221150558.png|600]]
![[Pasted image 20230221150623.png|600]]

The first is screen that plays with AI. The other is screen that plays with others.

The game board is one the middle of the screen which is used to play the game. In the following pictures,
- The first one shows the click effect on the game board.
- The second and third one shows the victory screenshot of the app.
- The last one shows the draw screenshot of the app.

![[Pasted image 20230221150957.png|600]]![[Pasted image 20230221150916.png|600]]![[Pasted image 20230221150940.png|600]]![[Pasted image 20230221151025.png|600]]

### Use Case Diagram

![[Pasted image 20230221153029.png]]

### Methodology

This is a single frontend project developed by Jetpack Compose, which follows clean architecture and solid code princinples. 

In the home and game screen all use navigation and dagger-hilt libraries. By the way, the basic compose library is also used.

Then about how to add AI and divide the difficulty levels, I used a algorithm called minimax. Then inside the function, there is a parameter "depth", I used depth to divide different levels. From 3 to 9, there are three different levels.

```Kotlin
private fun minimax(  
        boardItems: List<Pair<Int, BoardCellValue>>,  
        depth: Int,  
        isMaximizing: Boolean  
    ): Int {  
        val result = viewModel.checkGameResult(boardItems.toMap())  
  
        if (result != GameResult.INCOMPLETE) {  
            return when (result) {  
                GameResult.PLAYER_O_WIN -> 10 - depth  
                GameResult.PLAYER_X_WIN -> depth - 10  
                else -> 0  
            }  
        }  
  
        val currentValue = if (isMaximizing) BoardCellValue.CROSS else BoardCellValue.CIRCLE  
        var bestScore = if (isMaximizing) Int.MIN_VALUE else Int.MAX_VALUE  
  
        for (i in boardItems.indices) {  
            if (boardItems[i].second == BoardCellValue.NONE) {  
                val newBoardItems = boardItems.toMutableList()  
                newBoardItems[i] = newBoardItems[i].copy(second = currentValue)  
                val score = minimax(newBoardItems, depth + 1, !isMaximizing)  
                bestScore = if (isMaximizing) {  
                    max(bestScore, score)  
                } else {  
                    min(bestScore, score)  
                }  
            }  
        }  
  
        return bestScore  
    }  
```


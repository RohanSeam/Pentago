# Pentago

The goal of this project was to engage a user in a two person game of Pentago through Python. The program used a minimax approach with several move look ahead and alpha-beta pruning. 

Rules:
Pentagois a 2-player game played on a 6x6grid. The players alternate turns. The two players are referred to here as "W"and "B", which also signifies the colors of the  tokens  (white  and  black)  they  place  on  the  board.  

STAR: Start with an empty board and decide who starts, and who’s playing what color. 
GOAL: The objectiveis to get five marbles in a row, in any direction, before your opponent does. 
PLAY: Each turnconsists of placing one marble, anywhere on the board and twisting any of the game blocks 90 degrees, in either direction. You can place your marble on one game block and twist any other game block. 
WIN: First to five in a row wins! 

Notes: 
  •If the board is filled without a winner declared, the game ends in a tie. 
  •Twisting the board can cause two players to win simultaneously,which is also a tie. 
  •It is possible to win the game by placing a tokenbefore twisting a game block -in this case the game block does not need to be twisted. 
  
A sample run is included within the repository readme.txt

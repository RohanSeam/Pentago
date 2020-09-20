// Rohan Seam 
// 11/1/2019
// TCSS 435
// Class to store a Pentago Board that can be used in external programs

import java.lang.StringBuilder;
import java.util.Random;

public class PentagoBoard {
   private char[][] gameBoard;
   private int[][] subBoard = {{0,  1,  2,  6,  7,  8, 12, 13, 14},
                               {3,  4,  5,  9, 10, 11, 15, 16, 17},
                               {18, 19, 20, 24, 25, 26, 30, 31, 32},
                               {21, 22, 23, 27, 28, 29, 33, 34, 35}};
   
   public PentagoBoard() {
      gameBoard = new char[6][6];
      for (int i = 0; i < gameBoard.length; i++) {
         for (int j = 0; j < gameBoard[0].length; j++) {
            gameBoard[i][j] = '-';
         }
      }
   }
   
   public StringBuilder displayBoard() {
      StringBuilder output = new StringBuilder();
      output.append("+---+---+\n");
      for (int i = 0; i < gameBoard.length; i++) {
         if (i == 3) {
            output.append("+---+---+\n");
         }
         output.append("|");
         for (int j = 0; j < gameBoard[0].length; j++) {
            if (j == 3) {
               output.append("|");
            }
            output.append(gameBoard[i][j]);
         }
         output.append("|\n");
      }
      output.append("+---+---+\n");
      return output;
   }
   
   public void addToken(char token, int pos, int index) {
      int firstIndex = subBoard[pos - 1][index - 1] / 6;
      int newIndex = 0;
      if (index == 2 || index == 5 || index == 8) {
         newIndex = 1;
      } else if (index == 3 || index == 6 || index == 9) {
         newIndex = 2;
      }
      if (pos == 2 || pos == 4) {
         newIndex += 3;
      }
      gameBoard[firstIndex][newIndex] = token;
   }
   
   public char determineWinner() {
      boolean solutionW = false;
      boolean solutionB = false;
      for (int i = 0; i < gameBoard.length; i++) {
         for (int j = 0; j < gameBoard[0].length; j++) {
            if (gameBoard[i][j] == 'w') {
               // Check horizontal (5 in row wins)
               if (j < 2 && gameBoard[i][j+1] == 'w' && gameBoard[i][j+2] == 'w' &&
                     gameBoard[i][j+3] == 'w' && gameBoard[i][j+4] == 'w') {
                  solutionW = true;
               // Check horizontal (5 in column wins)
               } else if (i < 2 && gameBoard[i+1][j] == 'w' && gameBoard[i+2][j] == 'w' &&
                     gameBoard[i+3][j] == 'w' && gameBoard[i+4][j] == 'w') {
                  solutionW = true;
               // Check diagonal (right)
               } else if (i < 2 && j < 2 && gameBoard[i+1][j+1] == 'w' && gameBoard[i+2][j+2] == 'w' 
                     && gameBoard[i+3][j+3] == 'w' && gameBoard[i+4][j+4] == 'w') {
                  solutionW = true;
               // Check diagonal (left)
               } else if (i > 4 && j > 4 && gameBoard[i-1][j-1] == 'w' && gameBoard[i-2][j-2] == 'w' 
                     && gameBoard[i-3][j-3] == 'w' && gameBoard[i-4][j-4] == 'w') {
                  solutionW = true;
               }
            } else if (gameBoard[i][j] == 'b') {
               // Check horizontal (5 in row wins)
               if (j < 2 && gameBoard[i][j+1] == 'b' && gameBoard[i][j+2] == 'b' &&
                     gameBoard[i][j+3] == 'b' && gameBoard[i][j+4] == 'b') {
                  solutionB = true;
               // Check horizontal (5 in column wins)
               } else if (i < 2 && gameBoard[i+1][j] == 'b' && gameBoard[i+2][j] == 'b' &&
                     gameBoard[i+3][j] == 'b' && gameBoard[i+4][j] == 'b') {
                  solutionB = true;
               // Check diagonal (right)
               } else if (i < 2 && j < 2 && gameBoard[i+1][j+1] == 'b' && gameBoard[i+2][j+2] == 'b' 
                     && gameBoard[i+3][j+3] == 'b' && gameBoard[i+4][j+4] == 'b') {
                  solutionB = true;
               // Check diagonal (left)
               } else if (i > 4 && j > 4 && gameBoard[i-1][j-1] == 'b' && gameBoard[i-2][j-2] == 'b' 
                     && gameBoard[i-3][j-3] == 'b' && gameBoard[i-4][j-4] == 'b') {
                  solutionB = true;
               }
            }
         }
      }
      if (solutionW && solutionB) {
         return 't';
      } else if (solutionW) {
         return 'w';
      } else if (solutionB) {
         return 'b';
      }
      return 'n';
   }
   
   public void rotateLeft(int pos) {
      rotateRight(pos);
      rotateRight(pos);
      rotateRight(pos);
   }
   
   public void rotateRight(int pos) {
      char[][] temp = {{'-','-','-'}, {'-','-','-'}, {'-','-','-'}};
      for (int i = 0; i < gameBoard.length; i++) {
         for (int j = gameBoard[0].length - 1; j >= 0; j--) {
            if (pos == 1 && i < 3 && j < 3) {
               temp[i][j] = gameBoard[i][j];
            } else if (pos == 2 && i < 3 && j > 2) {
               temp[i][j-3] = gameBoard[i][j];
            } else if (pos == 3 && i > 2 && j < 3) {
               temp[i-3][j] = gameBoard[i][j];
            } else if (pos == 4 && i > 2 && j > 2) {
               temp[i-3][j-3] = gameBoard[i][j];
            }
         }
      }
      char[][] temp2 = {{'-','-','-'}, {'-','-','-'}, {'-','-','-'}};
      int m = temp.length;
      int n = temp[0].length;
      for (int i = 0; i < m; i++) {
         for (int j = 0; j < n; j++) {
            temp2[j][m-1-i] = temp[i][j];
         }
      }
      int x = 0;
      int y = 0;
      for (int i = 0; i < gameBoard.length; i++) {
         for (int j = 0; j < gameBoard[0].length; j++) {
            if (pos == 1 && i < 3 && j < 3) {
               gameBoard[i][j] = temp2[x][y];
               y++;
            } else if (pos == 2 && i < 3 && j > 2) {
               gameBoard[i][j] = temp2[x][y];
               y++;
            } else if (pos == 3 && i > 2 && j < 3) {
               gameBoard[i][j] = temp2[x][y];
               y++;
            } else if (pos == 4 && i > 2 && j > 2) {
               gameBoard[i][j] = temp2[x][y];
               y++;
            } 
            if (y == 3) {
               x++;
               y = 0;
            } else if (x == 3 && y == 3) {
               break;
            }
         }
      }
   }
   
   public int[][] availableMoves() {
      int[][] moves = new int[6][6];
      for (int i = 0; i < gameBoard.length; i++) {
         for (int j = 0; j < gameBoard[0].length; j++) {
            if (gameBoard[i][j] == '-') {
               moves[i][j] = 1;
            } else {
               moves[i][j] = 0;
            }
         }
      }
      return moves;
   }
   
   public boolean isFull() {
      boolean full = true;
      for (int i = 0; i < gameBoard.length; i++) {
         for (int j = 0; j < gameBoard[0].length; j++) {
            if (gameBoard[i][j] == '-') {
               full = false;
               break;
            }
         }
      }
      return full;
   }
   
   public double getUtility() {
      Random rand = new Random();
      return rand.nextDouble();
   }
}
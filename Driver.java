// Rohan Seam 
// 11/1/2019
// TCSS 435
// Driver class for Pentago game

import java.util.Scanner;
import java.util.Random;
import java.io.IOException;
import java.io.PrintStream;
import java.io.File;
import java.util.Random;
import java.util.Arrays;

public class Driver {
   public static void main(String[] args) throws IOException {
      Scanner userIn = new Scanner(System.in);
      PrintStream fileOut = new PrintStream(new File("Output.txt"));
      String name;
      char player1Token;
      
      System.out.print("Player 1 Name: ");
      name = userIn.next();
      fileOut.print("User name: " + name + '\n');
      
      System.out.print("Player 1 Token Color (B or W): ");
      player1Token = userIn.next().toLowerCase().charAt(0);

      // Initialize new PentagoBoard
      PentagoBoard board = new PentagoBoard();
      board.displayBoard();
      
      char compToken = ' ';
      if (player1Token == 'w') {
         compToken = 'b';
      } else {
         compToken = 'w';
      }
      Computer comp = new Computer(board, compToken);
      
      Random rand = new Random();
      int randNum = rand.nextInt(2);
      int next = 0;
      if (randNum == 0) {
         System.out.println("Player first");
         fileOut.print("Player first\n");
      } else {
         System.out.println("Computer first");
         fileOut.print("Computer first\n");
         next = 1;
      }
      
      fileOut.print("Player token: " + player1Token + "\n");
      fileOut.print(board.displayBoard() + "\n\n");
      boolean playerWin = false;
      boolean compWin = false;
      int num1 = 3;
      int num2 = 3;
      
      while (true) {
         if (next == 0) {
            String move = "";
            System.out.print("Enter a move: ");
            move = userIn.next();
            String rotate = userIn.next();
            String pos = move.split("/")[0];
            String index = move.split("/")[1];
            fileOut.print(move + ' ' + rotate + '\n');
            board.addToken(player1Token, Integer.parseInt(pos), Integer.parseInt(index));
            System.out.println(board.displayBoard());
            fileOut.print(board.displayBoard().toString());
            if (board.determineWinner() == player1Token) {
               fileOut.print("Player wins!\n");
               playerWin = true;
            }
            char position = rotate.charAt(0);
            char direction = rotate.charAt(1);
            if (direction == 'l' || direction == 'L') {
               board.rotateLeft(Character.getNumericValue(position));
            } else {
               board.rotateRight(Character.getNumericValue(position));
            }
            if (board.determineWinner() == player1Token && !playerWin) {
               fileOut.print("Player wins!\n");
               playerWin = true;
            }
            next = 1;
         } else {
            num1 = rand.nextInt(4);
            int[] move = {num1+1,num2};
            num2++;
            if (num2 == 9) {
               num2 = 3;
            }
            board.addToken(compToken, move[0], move[1]);
            if (board.determineWinner() == compToken) {
               fileOut.print("Computer wins!\n");
               compWin = true;
            }
            int num3 = rand.nextInt(4) + 1;
            int num4 = rand.nextInt(2);
            char c = 'R';
            if (num4 == 0) {
               board.rotateLeft(num3);
               c = 'L';
            } else {
               board.rotateRight(num3);
            }
            fileOut.print(Arrays.toString(move) + ' ' + num3 + ' ' + c + '\n');
            if (board.determineWinner() == compToken) {
               fileOut.print("Computer wins!\n");
               compWin = true;
            }
            next = 0;
         }
         if (compWin || playerWin) {
            break;
         }
         if (board.isFull() && !compWin && !playerWin) {
            fileOut.print("Tie!\n");
            break;
         }
      }
      
      if (playerWin && !compWin) {
         System.out.println("Player Wins!");
      } else if (compWin && !playerWin) {
         System.out.println("Computer Wins!");
      } else {
         System.out.println("It's a tie!");
      }
   }
}

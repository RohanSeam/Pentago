// Rohan Seam 
// 11/1/2019
// TCSS 435
// Class for the AI, Minimax, and Alpha-Beta pruning algorithms

import java.util.ArrayList;

public class Computer {
   private int maxLimit;
   private String search;
   
   public Node tree;
   public PentagoBoard game;
   public char token;
   public Node currentNode;
   public int limit;
   
   public Computer(PentagoBoard game, char token) {
      maxLimit = 3;
      search = "AlphaBetaPruning";
   }


   class Node {
      public ArrayList<Node> children;
      public int value;
      public int depth;
      public int[] prevMove = {0, 0};
      public PentagoBoard game;
      public char token;
   
      public Node(PentagoBoard game, char token) {
         children = new ArrayList<Node>();
         value = 0;
         depth = 0;
         prevMove = new int[2];
         this.game = game;
         this.token = token;
      }
   
      public void getChildNodes(int[][] moves) {
         Node child = new Node(game, token);
         for (int i = 0; i < moves.length; i++) {
            for (int j = 0; j < moves[0].length; j++) {
               child = new Node(game, token);
               //child.preMove = new int[] {i, j};
               child.depth = this.depth + 1;
               child.children = new ArrayList<Node>();
               game.addToken(token, i, j);
               game.rotateLeft(1);
            }
         }
         boolean exists = false;
         for (Node kid : this.children) {
            if (child.value == kid.value) {
               exists = true;
               break;
            }
         }
         if (!exists) {
            children.add(child);
         }
      }
   }
      
      public int[] getMove(int currentValue) {
         if (tree == null) {
            tree = new Node(game, token);
            tree.depth = 0;
            tree.prevMove = null;
            currentNode = tree;
         } else {
            for (Node child : currentNode.children) {
               if (child.value == currentValue) {
                  currentNode = child;
                  break;
               }
            }
         }
         limit = currentNode.depth + maxLimit;
         Node next = new Node(game, token);
         if (search == "AlphaBetaPruning") {
            next = alphaBetaPruning(currentNode);
         } else {
            next = miniMax(currentNode);
         }
         currentNode = next;
         return next.prevMove;
      }
   
      public Node alphaBetaPruning(Node node) {
         double beta = Double.POSITIVE_INFINITY;
         double alpha = beta * -1;
         Node best = null;
         for (Node child : node.children) {
            child.value = (int)minimize(child, alpha, beta);
            if (child.value > alpha) {
               alpha = child.value;
               best = child;
            }
         }
         return best;
      }
      
      public double minimize(Node node, double alpha, double beta) {
         if (node.depth < maxLimit) {
            if (node.children.size() == 0) {
               node.getChildNodes(game.availableMoves());
            }
         }
         if (node.children.size() == 0) {
            return game.getUtility();
         }
         double value = Double.POSITIVE_INFINITY;
         for (Node child : node.children) {
            child.value = (int)maximize(child, alpha, beta);
            value = Math.min(value, child.value);
            if (value <= alpha) {
               return value;
            }
            beta = Math.min(beta, value);
         }
         return value;
      }
      
      public double maximize(Node node, double alpha, double beta) {
         if (node.depth < maxLimit) {
            if (node.children.size() == 0) {
               node.getChildNodes(game.availableMoves());
            }
         }
         if (node.children.size() == 0) {
            return game.getUtility();
         }
         double value = Double.POSITIVE_INFINITY * -1;
         for (Node child : node.children) {
            child.value = (int)minimize(child, alpha, beta);
            value = Math.max(value, child.value);
            if (value >= beta) {
               return value;
            }
            beta = Math.max(alpha, value);
         }
         return value;
      }
      
      public Node miniMax(Node node) {
         if (node.children.size() == 0) {
            node.getChildNodes(game.availableMoves());
         }
         double best = maximize2(node);
         Node bestChild = null;
         for (Node child : node.children) {
            if (child.value == best) {
               bestChild = child;
               break;
            }
         }
         return bestChild;
      }
      
      public double maximize2(Node node) {
         if (node.depth < maxLimit) {
            if (node.children.size() == 0) {
               node.getChildNodes(game.availableMoves());
            }
         }
         if (node.children.size() == 0) {
            return game.getUtility();
         }
         double biggest = Double.POSITIVE_INFINITY * -1;
         for (Node child : node.children) {
            child.value = (int)minimize2(child);
            biggest = Math.max(biggest, child.value);
         }
         return biggest;
      }
      
      public double minimize2(Node node) {
         if (node.depth < maxLimit) {
            if (node.children.size() == 0) {
               node.getChildNodes(game.availableMoves());
            }
         }
         if (node.children.size() == 0) {
            return game.getUtility();
         }
         double smallest = Double.POSITIVE_INFINITY;
         for (Node child : node.children) {
            child.value = (int)maximize2(child);
            smallest = Math.min(smallest, child.value);
         }
         return smallest;
      }
}
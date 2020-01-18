/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author User
 */
public class AlphaBetaPruning {

    private static int globalDepth = 6;
    
    public static Move runAlgorithm(int level){
        
        globalDepth = level;        
        return absMaximize(new Board(""), 0, Integer.MIN_VALUE, Integer.MAX_VALUE).getMove();
    }
    

    /*
        The following two methods are the alpha beta pruning algorithm
    */

    private static IntMovePair absMinimize(Board b, int depth, int alpha, int beta) {

        if (depth >= globalDepth) {
            return realMax(b);
        }

        Move bestMove = null;
        for (Move m : b.getAvailableMoves(false)) {

            Board copyBoard = new Board(b);
            copyBoard.move(m);
            copyBoard.changeTurn();

            IntMovePair im = absMaximize(copyBoard, depth + 1, alpha, beta);
            int score = im.getScore();

            //Alpha Beta
            if (score < beta) {
                beta = score;
                bestMove = m;
            }

            if (alpha >= beta) {
                break;
            }

        }

        //System.out.println(best);
        return new IntMovePair(beta, bestMove);

    }

    private static IntMovePair absMaximize(Board b, int depth, int alpha, int beta) {

        if (depth >= globalDepth) {
            return realMin(b);
        }

        Move bestMove = null;

        for (Move m : b.getAvailableMoves(true)) {

            Board copyBoard = new Board(b);
            copyBoard.move(m);
            copyBoard.changeTurn();

            IntMovePair im = absMinimize(copyBoard, depth + 1, alpha, beta);
            int score = im.getScore();

            //Alpha Beta
            if (score > alpha) {
                alpha = score;
                bestMove = m;
            }

            if (alpha >= beta) {
                break;
            }

        }

        //System.out.println(best);
        return new IntMovePair(alpha, bestMove);

    }

    private static IntMovePair realMin(Board copyBoard) {

        int lowest = Integer.MAX_VALUE;
        Move lowestMove = null;
        for (Move testMove : copyBoard.getAvailableMoves()) {

            Board fakeBoard = new Board(copyBoard);
            fakeBoard.move(testMove);

            int score = fakeBoard.boardScore();

            if (score < lowest) {
                lowest = score;
                lowestMove = testMove;
            }

        }
        //System.out.print("\n" + lowest);
        //copyBoard.printBoard();

        return new IntMovePair(lowest, lowestMove);

    }
    
    private static IntMovePair realMax(Board copyBoard) {

        int lowest = Integer.MIN_VALUE;
        Move lowestMove = null;
        for (Move testMove : copyBoard.getAvailableMoves()) {

            Board fakeBoard = new Board(copyBoard);
            fakeBoard.move(testMove);
            int score = fakeBoard.boardScore();
            //System.out.print(score + ", ");

            if (score > lowest) {
                lowest = score;
                lowestMove = testMove;
            }

        }
        //System.out.print("\n" + lowest);
        //copyBoard.printBoard();

        //System.out.println(lowest);
        return new IntMovePair(lowest, lowestMove);

    }
    
    /*
        Sorting
    */
    
    
    public static ArrayList<Move> sortMovesGreatest(ArrayList<Move> list, Board temp){
        IntMovePair[] scores = new IntMovePair[list.size()];
        
        for (int i = 0; i < scores.length; i++){
            
            Board copyBoard = new Board(temp);
            Move m = list.get(i);
            copyBoard.move(m);
            scores[i] = new IntMovePair(copyBoard.boardScore(), m);
            
        }

        
        if (scores.length < 3){
            Arrays.sort(scores);
            ArrayList<Move> tempList = new ArrayList<>();
            for (int i = 0; i < scores.length; i++){
                tempList.add(scores[i].getMove());
            }
            return tempList;
            
        } 
        
        ArrayList<Move> tempList = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            
            int greatest = Integer.MIN_VALUE;
            int atInt = -1;
            for (int j = 0; j < scores.length; j++){
                
                if (scores[j].getScore() > greatest){
                    greatest = scores[j].getScore();
                    atInt = j;
                }
                
            }
            tempList.add(scores[atInt].getMove());
            scores[atInt] = new IntMovePair(Integer.MIN_VALUE, new Move());
            
        }
        
        for (int i = 0; i < scores.length; i++){
            
            if (scores[i].getScore() != Integer.MIN_VALUE){
                tempList.add(scores[i].getMove());
            }
            
        }
        
        return tempList;
        
    }
    
    public static ArrayList<Move> sortMovesLeast(ArrayList<Move> list, Board temp){
        IntMovePair[] scores = new IntMovePair[list.size()];
        
        for (int i = 0; i < scores.length; i++){
            
            Board copyBoard = new Board(temp);
            Move m = list.get(i);
            copyBoard.move(m);
            scores[i] = new IntMovePair(copyBoard.boardScore(), m);
            
        }

        
        if (scores.length < 3){
            Arrays.sort(scores);
            ArrayList<Move> tempList = new ArrayList<>();
            for (int i = 0; i < scores.length; i++){
                tempList.add(scores[i].getMove());
            }
            return tempList;
            
        } 
        
        ArrayList<Move> tempList = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            
            int least = Integer.MAX_VALUE;
            int atInt = -1;
            for (int j = 0; j < scores.length; j++){
                
                if (scores[j].getScore() < least){
                    least = scores[j].getScore();
                    atInt = j;
                }
                
            }
            tempList.add(scores[atInt].getMove());
            scores[atInt] = new IntMovePair(Integer.MAX_VALUE, new Move());
            
        }
        
        for (int i = 0; i < scores.length; i++){
            
            if (scores[i].getScore() != Integer.MAX_VALUE){
                tempList.add(scores[i].getMove());
            }
            
        }
        
        return tempList;
        
    }
    
    
    
    /*
        Following two methods are the Minimax algorithm
    */
    
    private static IntMovePair absMaximize(Board b, int depth) {

        if (depth >= globalDepth) {
            return realMin(b);
        }

        int best = Integer.MIN_VALUE;
        Move bestMove = null;

        for (Move m : b.getAvailableMoves()) {

            Board copyBoard = new Board(b);
            copyBoard.move(m);
            copyBoard.changeTurn();

            IntMovePair im = absMinimize(copyBoard, depth + 1);
            int score = im.getScore();

            //Minimax
            if (score > best) {
                best = score;
                bestMove = m;
            }
        }

        //System.out.println(best);
        return new IntMovePair(best, bestMove);

    }

    private static IntMovePair absMinimize(Board b, int depth) {

        if (depth >= globalDepth) {
            return realMax(b);
        }

        int best = Integer.MAX_VALUE;
        Move bestMove = null;

        for (Move m : b.getAvailableMoves()) {

            Board copyBoard = new Board(b);
            copyBoard.move(m);
            copyBoard.changeTurn();

            IntMovePair im = absMaximize(copyBoard, depth + 1);
            int score = im.getScore();

            //Minimax
            if (score < best) {
                best = score;
                bestMove = m;
            }
        }

        //System.out.println(best);
        return new IntMovePair(best, bestMove);

    }
    
        
    private static void timeAlgorithms(){
        
        Board b = new Board("");
        
        long timeOne = 0;
        long timeTwo;
        long time;
        
        time = System.nanoTime();
        absMinimize(b, 0, Integer.MIN_VALUE, Integer.MAX_VALUE).getMove();
        timeTwo = (System.nanoTime() - time)/1_000_000;
        
        System.out.println("AlphaBeta: " + timeTwo);
        
        time = System.nanoTime();
        absMaximize(b, 0).getMove();
        timeOne = (System.nanoTime() - time)/1_000_000;
        System.out.println("  Minimax: " + timeOne);
        
    }
    
    

}

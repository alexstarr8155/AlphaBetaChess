/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import view.View;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class OpponentPlayer {

    public static ArrayList<Piece> pieces = OpponentPlayer.initialState();
    public static Piece selectedPiece;
    public static String colour = Board.BLACK;
    
    public static void makeMove(int level) {
        //System.out.println("running");
        
        //long time = System.currentTimeMillis();
        Move m = AlphaBetaPruning.runAlgorithm(level);
        //System.out.println(System.currentTimeMillis() - time);
        
        //System.out.println(m);
        m.getPiece().setCoord(m.getStartingPosition());

        for (Piece p : pieces) {
            //System.out.println(p + "" +  p.getCoord() + "|" + m.getPiece() + "" + m.getPiece().getCoord());
            if (p.equals(m.getPiece())) {
                p.move(m.getPlace());
                break;
            }
        }     
        
        Board board = new Board("");
        if (board.inCheckMate(Board.WHITE)){
            View.displayDialog(View.CHECKMATE);
        }

    }

    public static Move randomMove() {

        int r = (int) (Math.random() * pieces.size());
        Piece p = pieces.get(r);

        while (p.possibleMoves().isEmpty()) {
            r = (int) (Math.random() * pieces.size());
            p = pieces.get(r);
        }

        int r2 = (int) (Math.random() * p.possibleMoves().size());
        Coordinate c = p.possibleMoves().get(r2);

        while (!c.isValid()) {
            r2 = (int) (Math.random() * p.possibleMoves().size());
            c = p.possibleMoves().get(r2);
        }

        return new Move(p, c);
    }

    public static Piece pieceAt(Coordinate c) {

        for (Piece p : pieces) {
            if (p.getCoord().equals(c)) {
                return p;
            }
        }
        return null;

    }

    public static void removePiece(Piece p) {

        pieces.remove(p);

    }

    public static boolean containsPiece(Coordinate c) {

        return OpponentPlayer.pieceAt(c) != null;

    }

    public static ArrayList<Piece> initialState() {

        ArrayList<Piece> p = new ArrayList<>();

        p.add(new Queen("BKing.png", new Coordinate(3, 0), colour));
        
        p.add(new Rook("BRook.png", new Coordinate(0, 0), colour));
        p.add(new Rook("BRook.png", new Coordinate(7, 0), colour));
        
        p.add(new Bishop("BBishop.png", new Coordinate(2, 0), colour));
        p.add(new Bishop("BBishop.png", new Coordinate(5, 0), colour));

        p.add(new Pawn("BPawn.png", new Coordinate(4, 1), colour));
        p.add(new Pawn("BPawn.png", new Coordinate(3, 1), colour));
        p.add(new Pawn("BPawn.png", new Coordinate(2, 1), colour)); 
        p.add(new Pawn("BPawn.png", new Coordinate(1, 1), colour));
        p.add(new Pawn("BPawn.png", new Coordinate(6, 1), colour));
        p.add(new Pawn("BPawn.png", new Coordinate(0, 1), colour));
        p.add(new Pawn("BPawn.png", new Coordinate(7, 1), colour));
        p.add(new Pawn("BPawn.png", new Coordinate(5, 1), colour));
        
        p.add(new Knight("BKnight.png", new Coordinate(1, 0), colour));
        p.add(new Knight("BKnight.png", new Coordinate(6, 0), colour));
        
        p.add(new King("BQueen.png", new Coordinate(4, 0), colour));
        
        return p;

    }

}

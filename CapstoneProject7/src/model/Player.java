/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

public class Player {

    public static ArrayList<Piece> pieces = Player.initialState();
    public static Piece selectedPiece;
    public static String colour = Board.WHITE;    

    public static Piece pieceAt(Coordinate c) {

        for (Piece p : pieces) {
            if (p.getCoord().equals(c)) {
                return p;
            }
        }
        return null;

    }

    
    public static boolean containsPiece(Coordinate c){
        
        return pieceAt(c) != null;
        
    }
    
    public static void removePiece(Piece p){
        
        pieces.remove(p);
        
    }

    public static ArrayList<Piece> initialState() {

        ArrayList<Piece> p = new ArrayList<>();
        
        for (int i = 0; i < 8; i++) {

            p.add(new Pawn("WPawn.png", new Coordinate(i, 6), colour));

        }
        
        p.add(new Rook("WRook.png", new Coordinate(0, 7), colour));
        p.add(new Rook("WRook.png", new Coordinate(7, 7), colour));
        
        p.add(new Knight("WKnight.png", new Coordinate(1, 7), colour));
        p.add(new Knight("WKnight.png", new Coordinate(6, 7), colour));
        
        p.add(new Bishop("WBishop.png", new Coordinate(2, 7), colour));
        p.add(new Bishop("WBishop.png", new Coordinate(5, 7), colour));
        
        p.add(new Queen("WKing.png", new Coordinate(3, 7), colour));
        p.add(new King("WQueen.png", new Coordinate(4, 7), colour));
        
        return p;

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Pawn extends Piece {
    
    private static final int RANK = 1;
    int turnNumber;
    
    public Pawn(String path, Coordinate c, String team) {
        super(path, c, team, RANK);
        turnNumber = 0;
    }
    
    public Pawn(Coordinate c, String team, int rank) {
        super(c, team, rank);
    }
    
    public Pawn(Coordinate c, String team, int rank, int turnNumber) {
        super(c, team, RANK);
        this.turnNumber = 1;
    }
    
    public int getTurn(){
        
        return this.turnNumber;
        
    }
    
    @Override
    public ArrayList<Coordinate> possibleMoves(Board b) {
        
        ArrayList<Coordinate> a = new ArrayList<>();
        Coordinate c = new Coordinate(this.getCoord().getX(), this.getCoord().getY() - 1);
        
        if (this.team().equals("Black")) {
            c = new Coordinate(this.getCoord().getX(), this.getCoord().getY() + 1);
        }
        
        String colour;
        if (this.team().equals("Black")) {
            colour = Board.WHITE;
        } else {
            colour = Board.BLACK;
        }
        
        if (!b.containsPiece(c)) {
            a.add(c);
        }
        
        if (turnNumber == 0) {
            
            if (!b.containsPiece(c)) {
                
                c = new Coordinate(this.getCoord().getX(), this.getCoord().getY() - 2);
                
                if (this.team().equals("Black")) {
                    c = new Coordinate(this.getCoord().getX(), this.getCoord().getY() + 2);
                }
                
                if (!c.containsPiece()) {
                    a.add(c);
                }
            }
            
        }
        
        if (this.team().equals("White")) {
            c = new Coordinate(this.getCoord().getX() - 1, this.getCoord().getY() - 1);
            if (b.containsPiece(c, colour)) {
                a.add(c);
            }
            c = new Coordinate(this.getCoord().getX() + 1, this.getCoord().getY() - 1);
            if (b.containsPiece(c, colour)) {
                a.add(c);
            }
            
        } else {
            c = new Coordinate(this.getCoord().getX() - 1, this.getCoord().getY() + 1);
            if (b.containsPiece(c, colour)) {
                a.add(c);
            }
            c = new Coordinate(this.getCoord().getX() + 1, this.getCoord().getY() + 1);
            if (b.containsPiece(c, colour)) {
                a.add(c);
            }
        }        
        return a;
        
    }
    
    @Override
    public ArrayList<Coordinate> possibleMoves() {
        return this.possibleMoves(new Board(""));
    }
    
    @Override
    public boolean move(Coordinate c) {
        
        boolean moved = super.move(c);
        
        if (moved) {
            turnNumber++;
        }
        return this.getCoord().equals(c);
        
    }
    
    @Override
    public String toString() {
        return super.toString() + " Pawn";
    }
    
}

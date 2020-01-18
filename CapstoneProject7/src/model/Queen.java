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
public class Queen extends Piece {
    
    private static final int RANK = 7;

    public Queen(String filePath, Coordinate c, String team) {
        super(filePath, c, team, RANK);
    }
    
    public Queen(Coordinate c, String team, int rank){
        super(c, team, rank);
    }

    @Override
    public ArrayList<Coordinate> possibleMoves() {

        return possibleMoves(new Board(""));
    }
    
    @Override
    public ArrayList<Coordinate> possibleMoves(Board b){
        
        ArrayList<Coordinate> list = this.getStraightMoves(b);
        list.addAll(this.getDiagonalMoves(b));
        return list;
        
    }

}

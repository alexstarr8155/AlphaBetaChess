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
public class Bishop extends Piece {
    
    private static final int RANK = 4;

    public Bishop(String path, Coordinate c, String team) {
        super(path, c, team, RANK);
    }
    
    public Bishop(Coordinate c, String team, int rank){
        super(c, team, rank);
    }

    @Override
    public ArrayList<Coordinate> possibleMoves() {
        return this.possibleMoves(new Board(""));
    }
    
    @Override
    public ArrayList<Coordinate> possibleMoves(Board b) {
        ArrayList<Coordinate> temp = getDiagonalMoves(b);
        return temp;
    }

}



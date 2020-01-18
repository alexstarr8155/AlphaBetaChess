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
public class Rook extends Piece {

    private static final int RANK = 5;

    public Rook(String path, Coordinate c, String team) {
        super(path, c, team, RANK);
    }

    public Rook(Coordinate c, String team, int rank) {
        super(c, team, rank);
    }

    @Override
    public ArrayList<Coordinate> possibleMoves() {

        return this.possibleMoves(new Board(""));

    }

    @Override
    public ArrayList<Coordinate> possibleMoves(Board b) {

        ArrayList<Coordinate> temp = getStraightMoves(b);
        return temp;

    }

}

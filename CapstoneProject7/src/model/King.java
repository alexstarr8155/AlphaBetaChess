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
public class King extends Piece {

    private static final int RANK = 70;

    public King(String filePath, Coordinate c, String team) {
        super(filePath, c, team, RANK);
    }

    public King(Coordinate c, String team, int rank) {
        super(c, team, rank);
    }

    @Override
    public ArrayList<Coordinate> possibleMoves() {
        return this.possibleMoves(new Board(""));
    }

    @Override
    public ArrayList<Coordinate> possibleMoves(Board b) {

        ArrayList<Coordinate> a = new ArrayList<>();
        Coordinate c;

        String colour;

        if (this.team().equals("Black")) {
            colour = Board.WHITE;
        } else {
            colour = Board.BLACK;
        }

        for (int i = this.getCoord().getX() - 1; i <= this.getCoord().getX() + 1; i++) {
            for (int j = this.getCoord().getY() - 1; j <= this.getCoord().getY() + 1; j++) {

                c = new Coordinate(i, j);

                if (!((i < 0 || j < 0) || (i > 7 || j > 7)) && !b.containsPiece(c)) {
                    a.add(c);
                    //System.out.println(c + ", " + !c.containsPiece());
                } else if (!((i < 0 || j < 0) || (i > 7 || j > 7)) && b.containsPiece(c, colour)) {
                    a.add(c);
                }
            }
        }

        return a;

    }


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.ChessBoard;
import view.View;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author User
 */
public abstract class Piece{

    private String team;
    private ImageIcon icon;
    private Coordinate coord;
    private static String win = "none";
    private int rank;
    public ArrayList<Coordinate> possibleCaptures;
    //public static Board b = new Board();

    public Piece(String path, Coordinate c, String team, int rank) {

        this.icon = new ImageIcon(path);
        this.coord = c;
        this.team = team;
        this.possibleCaptures = new ArrayList<>();
        this.rank = rank;
    }

    public Piece(Coordinate c, String team, int rank) {
        this.coord = c;
        this.team = team;
        this.rank = rank;
    }

    public String team() {
        return this.team;
    }

    public int getRank() {
        return this.rank;
    }
    
    public abstract ArrayList<Coordinate> possibleMoves();
    public abstract ArrayList<Coordinate> possibleMoves(Board b);

    public boolean move(Coordinate c) {

        //b.move(new Move(this, c));
        Board b = new Board("");
        b.move(new Move(this, c));

        if (b.inCheckMate(Board.BLACK)) {
            View.displayDialog(View.WHITE_WINS);
        }

        if (b.isInCheck(this.team())) {
            if (this.team().equals(Board.WHITE)) {
                View.displayDialog(View.KING_IN_CHECK);
                if (b.inCheckMate(Board.WHITE)) {
                    View.displayDialog(View.CHECKMATE);
                }
                return false;
            } else if (ChessBoard.twoPlayer) {
                View.displayDialog(View.KING_IN_CHECK);
                if (b.inCheckMate(Board.BLACK)) {
                    View.displayDialog(View.WHITE_WINS);
                }
                return false;
            }
        }

        if (this.possibleMoves().contains(c)) {
            this.setCoord(c);
        } else {
            return false;
        }

        if (c.containsPiece(this.team)) {
            if (this.team.equals("Black")) {
                capture(Player.pieceAt(c));
            } else {
                capture(OpponentPlayer.pieceAt(c));
            }
        }

        return this.getCoord().equals(c);
    }

    public void capture(Piece p) {
        p.coord = new Coordinate(-1, -1);

        //System.out.println("Capturing " + p);
        if (p.team().equals("Black")) {
            OpponentPlayer.removePiece(p);
        } else {
            Player.removePiece(p);
        }

        if (p instanceof King) {
            win = this.team();
        }

    }

    public static String gameWinner() {
        return win;
    }

    public Coordinate getCoord() {
        return this.coord;
    }

    public void setCoord(Coordinate c) {
        this.coord = c;
    }

    public ArrayList<Coordinate> getDiagonalMoves(Board opponentBoard) {

        ArrayList<Coordinate> list = new ArrayList<>();

        String colour;
        if (team.equals("Black")) {
            colour = Board.WHITE;
        } else {
            colour = Board.BLACK;
        }

        int number = 1;

        boolean one = true;
        boolean two = true;
        boolean three = true;
        boolean four = true;

        while (number < 8) {

            int i = this.getCoord().getX() + number;
            int j = this.getCoord().getY() + number;

            int x = this.getCoord().getX() - number;
            int y = this.getCoord().getY() - number;

            Coordinate c = new Coordinate(i, j);
            if ((i >= 0 && i < 8) && (j >= 0 && j < 8) && one) {
                if (opponentBoard.containsPiece(c)) {
                    one = false;
                    if (opponentBoard.containsPiece(c, colour)) {
                        list.add(c);
                    }
                } else {
                    list.add(c);
                }
            }

            c = new Coordinate(x, y);
            if ((x >= 0 && x < 8) && (y >= 0 && y < 8) && two) {
                if (opponentBoard.containsPiece(c)) {
                    two = false;
                    if (opponentBoard.containsPiece(c, colour)) {
                        list.add(c);
                    }
                } else {
                    list.add(c);
                }

            }

            c = new Coordinate(i, y);
            if ((i >= 0 && i < 8) && (y >= 0 && y < 8) && three) {
                if (opponentBoard.containsPiece(c)) {
                    three = false;
                    if (opponentBoard.containsPiece(c, colour)) {
                        list.add(c);
                    }
                } else {
                    list.add(c);
                }
            }

            c = new Coordinate(x, j);
            if ((x >= 0 && x < 8) && (j >= 0 && j < 8) && four) {
                if (opponentBoard.containsPiece(c)) {
                    four = false;
                    if (opponentBoard.containsPiece(c, colour)) {
                        list.add(c);
                    }
                } else {
                    list.add(c);
                }
            }
            number++;
        }
        return list;

    }

    public ArrayList<Coordinate> getDiagonalMoves() {

        return getDiagonalMoves(new Board(""));
    }

    public ArrayList<Coordinate> getStraightMoves(Board opponentBoard) {

        ArrayList<Coordinate> a = new ArrayList<>();

        int x = this.getCoord().getX();
        int y = this.getCoord().getY();

        String colour;

        if (team.equals("Black")) {
            colour = Board.WHITE;
        } else {
            colour = Board.BLACK;
        }

        for (int i = y; i < 8; i++) {

            Coordinate c = new Coordinate(this.getCoord().getX(), i);

            opponentBoard.findPieceAt(c, colour);

            if (!opponentBoard.containsPiece(c) && !this.getCoord().equals(c)) {
                a.add(c);
            } else if (opponentBoard.containsPiece(c, colour)) {
                a.add(c);
                break;
            } else if (!this.getCoord().equals(c)) {
                break;
            }
        }

        for (int i = y; i >= 0; i--) {

            Coordinate c = new Coordinate(this.getCoord().getX(), i);
            if (!opponentBoard.containsPiece(c) && !this.getCoord().equals(c)) {
                a.add(c);
            } else if (opponentBoard.containsPiece(c, colour)) {
                a.add(c);
                break;
            } else if (!this.getCoord().equals(c)) {
                break;
            }
        }

        for (int i = x; i < 8; i++) {

            Coordinate c = new Coordinate(i, this.getCoord().getY());
            if (!opponentBoard.containsPiece(c) && !this.getCoord().equals(c)) {
                a.add(c);
            } else if (opponentBoard.containsPiece(c, colour)) {
                a.add(c);
                break;
            } else if (!this.getCoord().equals(c)) {
                break;
            }
        }
//        
        for (int i = x; i >= 0; i--) {

            Coordinate c = new Coordinate(i, this.getCoord().getY());
            if (!opponentBoard.containsPiece(c) && !this.getCoord().equals(c)) {
                a.add(c);
            } else if (opponentBoard.containsPiece(c, colour)) {
                a.add(c);
                break;
            } else if (!this.getCoord().equals(c)) {
                break;
            }

        }

        return a;

    }

    public ArrayList<Coordinate> getStraightMoves() {

        return getStraightMoves(new Board(""));
    }

    public ArrayList<Coordinate> getCaputurableMoves() {

        ArrayList<Coordinate> temp = new ArrayList<>();

        //System.out.println(this.possibleMoves());
        for (Coordinate c : this.possibleMoves()) {
            if (c.containsPiece(this.team)) {
                temp.add(c);

            }
            //System.out.println(c);
        }

        return temp;

    }

    public ImageIcon toImage() {
        return this.icon;
    }

    @Override
    public String toString() {

        return this.getRank() + " " + this.team + " " + this.getCoord();

    }

    public boolean equals(Piece p) {

        return this.rank == p.rank && this.getCoord().equals(p.getCoord());

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author student
 */
public class Board {

    private HashMap<String, ArrayList<Piece>> pieces;
    private ArrayList<Move> previousMoves;
    private Move undoMove;
    private boolean whiteTurn;

    public final static String WHITE = "White";
    public final static String BLACK = "Black";

    public Board(ArrayList<Piece> whiteArr, ArrayList<Piece> blackArr) {

        this.pieces = new HashMap<>();
        this.pieces.put(WHITE, copyOver(whiteArr, WHITE));
        this.pieces.put(BLACK, copyOver(blackArr, BLACK));
        this.whiteTurn = false;
        previousMoves = new ArrayList<>();

    }

    public Board(Board b) {

        this.pieces = new HashMap<>();
        this.pieces.put(WHITE, (ArrayList<Piece>) copyOver(b.getWhite(), WHITE));
        this.pieces.put(BLACK, (ArrayList<Piece>) copyOver(b.getBlack(), BLACK));
        this.whiteTurn = b.whiteTurn;
        previousMoves = new ArrayList<>();

    }

    //default board setup
    public Board(String s) {
        this(Player.pieces, OpponentPlayer.pieces);
    }

    public Board() {

        ArrayList<Piece> testWhite = new ArrayList<>();
        ArrayList<Piece> testBlack = new ArrayList<>();

        testBlack.add(new Queen("BKing.png", new Coordinate(3, 4), "Black"));

        testBlack.add(new Rook("BRook.png", new Coordinate(0, 0), "Black"));
        testBlack.add(new Rook("BRook.png", new Coordinate(7, 0), "Black"));

        testBlack.add(new Bishop("BBishop.png", new Coordinate(2, 0), "Black"));
        testBlack.add(new Bishop("BBishop.png", new Coordinate(2, 5), "Black"));

        testBlack.add(new Pawn("BPawn.png", new Coordinate(4, 1), "Black"));
        testBlack.add(new Pawn("BPawn.png", new Coordinate(3, 1), "Black"));
        testBlack.add(new Pawn("BPawn.png", new Coordinate(2, 1), "Black"));
        testBlack.add(new Pawn("BPawn.png", new Coordinate(1, 1), "Black"));
        testBlack.add(new Pawn("BPawn.png", new Coordinate(6, 1), "Black"));
        testBlack.add(new Pawn("BPawn.png", new Coordinate(0, 1), "Black"));
        testBlack.add(new Pawn("BPawn.png", new Coordinate(7, 1), "Black"));
        testBlack.add(new Pawn("BPawn.png", new Coordinate(5, 1), "Black"));

        testBlack.add(new Knight("BKnight.png", new Coordinate(1, 0), "Black"));
        testBlack.add(new Knight("BKnight.png", new Coordinate(6, 0), "Black"));

        testBlack.add(new King("BQueen.png", new Coordinate(4, 0), "Black"));

        testWhite.add(new Pawn("WPawn.png", new Coordinate(0, 6), "White"));
        testWhite.add(new Pawn("WPawn.png", new Coordinate(1, 6), "White"));

        for (int i = 4; i < 8; i++) {

            testWhite.add(new Pawn("WPawn.png", new Coordinate(i, 6), "White"));

        }

        testWhite.add(new Rook("WRook.png", new Coordinate(0, 7), "White"));
        testWhite.add(new Rook("WRook.png", new Coordinate(7, 7), "White"));

        testWhite.add(new Knight("WKnight.png", new Coordinate(1, 7), "White"));
        testWhite.add(new Knight("WKnight.png", new Coordinate(6, 7), "White"));

        testWhite.add(new Bishop("WBishop.png", new Coordinate(2, 7), "White"));
        testWhite.add(new Bishop("WBishop.png", new Coordinate(5, 7), "White"));

        testWhite.add(new Queen("WKing.png", new Coordinate(3, 7), "White"));
        testWhite.add(new King("WQueen.png", new Coordinate(4, 7), "White"));

        this.pieces = new HashMap<>();
        this.pieces.put(WHITE, testWhite);
        this.pieces.put(BLACK, testBlack);
        this.whiteTurn = false;
        previousMoves = new ArrayList<>();

    }

    private static ArrayList<Piece> copyOver(ArrayList<Piece> temp, String colour) {

        ArrayList<Piece> list = new ArrayList<>();
        for (Piece p : temp) {
            if (p instanceof Pawn) {
                Pawn pawn = (Pawn) p;
                list.add(new Pawn(p.getCoord(), colour, p.getRank(), pawn.getTurn()));
            } else if (p instanceof Knight) {
                list.add(new Knight(p.getCoord(), colour, p.getRank()));
            } else if (p instanceof Bishop) {
                list.add(new Bishop(p.getCoord(), colour, p.getRank()));
            } else if (p instanceof Rook) {
                list.add(new Rook(p.getCoord(), colour, p.getRank()));
            } else if (p instanceof Queen) {
                list.add(new Queen(p.getCoord(), colour, p.getRank()));
            } else if (p instanceof King) {
                list.add(new King(p.getCoord(), colour, p.getRank()));
            }
        }

        return list;

    }

    public void setTurn(boolean b) {
        this.whiteTurn = b;
    }

    public boolean gameIsOver() {
        return this.pieces.get(WHITE).isEmpty() || this.pieces.get(BLACK).isEmpty();
    }

    public Piece findPieceAt(Coordinate c) {

        Piece p = findPieceAt(c, WHITE);

        if (p == null) {
            p = findPieceAt(c, BLACK);
        }

        return p;
    }

    public Piece findPieceAt(Coordinate c, String colour) {

        for (Piece p : pieces.get(colour)) {
            if (p.getCoord().equals(c)) {
                return p;
            }
        }
        return null;
    }

    public boolean containsPiece(Coordinate c, String colour) {

        return findPieceAt(c, colour) != null;

    }

    public boolean containsPiece(Coordinate c) {
        return containsPiece(c, Board.WHITE) || containsPiece(c, Board.BLACK);
    }

    public void move(Move m, boolean print) {

        Piece toMove = null;

        //undoMove = new Move(m.getPiece(), m.getPiece().getCoord());
        for (Piece p : pieces.get(m.getPiece().team())) {
            if (p.equals(m.getPiece())) {
                toMove = p;
            }
        }

        if (toMove == null) {
            return;
        }

        Coordinate desiredLocation = m.getPlace();
        Piece p = findPieceAt(desiredLocation);

        if (p != null) {
            this.pieces.get(p.team()).remove(p);
        }

        if (m.getPlace().isValid()) {
            toMove.setCoord(m.getPlace());
            previousMoves.add(new Move(toMove, desiredLocation));
        }

    }

    public void move(Move m) {

        Piece toMove = null;
        //undoMove = new Move(m.getPiece(), m.getPiece().getCoord());
        for (Piece p : pieces.get(m.getPiece().team())) {
            if (p.equals(m.getPiece())) {
                toMove = p;
            }
        }

        if (toMove == null) {
            return;
        }

        Coordinate desiredLocation = m.getPlace();
        String colour;

        if (whiteTurn) {
            colour = BLACK;
        } else {
            colour = WHITE;
        }

        Piece p = findPieceAt(desiredLocation);

        if (p != null) {
            this.move(new Move(p, new Coordinate(-10, -10)));
            ArrayList<Piece> atColour = this.pieces.get(colour);
            atColour.remove(p);
            //System.out.println(p);
        }

        if (m.getPlace().isValid() || m.getPlace().equals(new Coordinate(-10, -10))) {
            toMove.setCoord(m.getPlace());
            previousMoves.add(new Move(toMove, desiredLocation));
        }

    }

    public boolean isInCheck(String team) {

        String otherTeam;

        if (team.equals(Board.WHITE)) {
            otherTeam = Board.BLACK;
        } else {
            otherTeam = Board.WHITE;
        }

        Coordinate king = null;
        for (Piece p : this.pieces.get(team)) {

            if (p instanceof King) {
                king = p.getCoord();
            }

        }

        if (king == null) {
            return false;
        }

        //System.out.println(this.pieces.get(otherTeam));
        for (Piece p : this.pieces.get(otherTeam)) {
            for (Coordinate c : p.possibleMoves(this)) {

                if (c.equals(king)) {
                    return true;
                }

            }
        }

        return false;

    }

    public boolean inCheckMate(String team) {

        if (!this.isInCheck(team)) {
            return false;
        }

        //System.out.println(this.pieces.get(team));
        for (Piece p : this.pieces.get(team)) {
            for (Coordinate c : p.possibleMoves(this)) {
                Board b = new Board(this);
                b.move(new Move(p, c));
                if (!b.isInCheck(team)) {
                    return false;
                }

            }

        }

        return true;

    }

    private String getOtherTeam(String team) {

        if (team.equals(BLACK)) {
            return WHITE;
        } else {
            return BLACK;
        }

    }

    public Move undo(Move m) {

        return new Move(m.getPiece(), m.getStartingPosition());

    }

    public int boardScore() {

        int val = 0;

        for (Piece p : pieces.get(WHITE)) {
            val -= p.getRank();
        }

        for (Piece p : pieces.get(BLACK)) {
            val += p.getRank();
        }

        return val;

    }

    public String getTurn() {
        if (whiteTurn) {
            return WHITE;
        } else {
            return BLACK;
        }
    }

    public void changeTurn() {
        whiteTurn = !whiteTurn;
    }

    public ArrayList<Move> getAvailableMoves() {

        ArrayList<Move> possibleMoves = new ArrayList<>();
        //System.out.println(pieces.get(this.getTurn()));
        for (Piece p : pieces.get(this.getTurn())) {
            //System.out.println(p.getStraightMoves());
            for (Coordinate c : p.possibleMoves(this)) {
                possibleMoves.add(new Move(p, c));
                //System.out.println(new Move(p, c));
            }
        }

        return possibleMoves;

    }

    public ArrayList<Move> getAvailableMoves(boolean greatest) {

        if (greatest) {
            return AlphaBetaPruning.sortMovesGreatest(getAvailableMoves(), this);
        } else {
            return AlphaBetaPruning.sortMovesLeast(getAvailableMoves(), this);
        }

    }

    public ArrayList<Piece> getWhite() {
        return pieces.get(WHITE);
    }

    public ArrayList<Piece> getBlack() {
        return pieces.get(BLACK);
    }

    public Move getLastMove() {

        return previousMoves.get(previousMoves.size() - 1);

    }

    @Override
    public String toString() {

        return this.boardScore() + "";//this.pieces.get(white) + "" + this.pieces.get(black);

    }

    public void printBoard() {

        System.out.println();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (findPieceAt(new Coordinate(i, j), WHITE) != null) {

                    System.out.print(findPieceAt(new Coordinate(i, j), WHITE).getRank() + "* ");

                } else if (findPieceAt(new Coordinate(i, j), BLACK) != null) {

                    System.out.print(findPieceAt(new Coordinate(i, j), BLACK).getRank() + "- ");

                } else {
                    System.out.print("0 ");
                }

            }
            System.out.println();

        }
        System.out.println();

    }

}

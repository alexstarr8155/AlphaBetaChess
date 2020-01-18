/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Board;
import model.Coordinate;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;
import model.MoveThread;
import model.OpponentPlayer;
import model.Player;

public class ChessBoard {

    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private final ChessBoardSquares chessBoardSquares = new ChessBoardSquares();
    private JPanel chessBoard;
    private MoveThread mt;
    public boolean moveAllowed = true;
    public static boolean twoPlayer;
    public boolean whiteTurn = true;

    public ChessBoard(int level) {
        mt = new MoveThread(this, level);
        twoPlayer = false;
        OpponentPlayer.pieces = OpponentPlayer.initialState();
        Player.pieces = Player.initialState();
        setUpBoard();
    }

    public ChessBoard(int level, boolean twoPlayer) {
        this(level);
        this.twoPlayer = twoPlayer;
    }

    public final void setUpBoard() {
        chessBoard = new JPanel(new GridLayout(0, 8));
        chessBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(chessBoard);

        for (int i = 0; i < chessBoardSquares.board.length; i++) {
            for (int j = 0; j < chessBoardSquares.board[i].length; j++) {
                JButton b = new JButton();

                if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(Color.LIGHT_GRAY);
                }
                chessBoardSquares.board[j][i] = b;
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
//                if (j == 0) {
//
//                    chessBoard.add(new JLabel("" + (i + 1), SwingConstants.CENTER));
//
//                }
                chessBoard.add(chessBoardSquares.board[j][i]);

            }
        }

        update();
        setUpButtons();

    }

    public void setUpButtons() {

        for (int i = 0; i < chessBoardSquares.board.length; i++) {
            for (int j = 0; j < chessBoardSquares.board[i].length; j++) {

                JButton b = chessBoardSquares.board[i][j];

                int numi = i;
                int numj = j;

                ActionListener boardListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //update();
                        if (twoPlayer) {
                            runGame(numi, numj, whiteTurn);
                        } else {
                            runGame(numi, numj);
                        }

                    }
                };

                b.addActionListener(boardListener);

            }
        }
    }

    public void runGame(int numi, int numj, boolean white) {

        if (white) {
            if (!new Coordinate(numi, numj).containsPiece(Board.BLACK)) {
                boolean successful = false;
                try {
                    successful = Player.selectedPiece.move(new Coordinate(numi, numj));

                    if (successful) {
                        Player.selectedPiece = null;
                    }
                } catch (NullPointerException n) {
                };

                if (successful) {
                    whiteTurn = !whiteTurn;
                }

            } else if (Player.containsPiece(new Coordinate(numi, numj))) {
                Player.selectedPiece = Player.pieceAt(new Coordinate(numi, numj));
            }
        } else {
            if (!new Coordinate(numi, numj).containsPiece(Board.WHITE)) {
                boolean successful = false;
                try {
                    successful = Player.selectedPiece.move(new Coordinate(numi, numj));
                    if (successful) {
                        Player.selectedPiece = null;
                    }
                } catch (NullPointerException n) {
                }
                if (successful) {
                    whiteTurn = !whiteTurn;
                }

            } else if (OpponentPlayer.containsPiece(new Coordinate(numi, numj))) {
                Player.selectedPiece = OpponentPlayer.pieceAt(new Coordinate(numi, numj));
            }
        }

        update();
    }

    public void runGame(int numi, int numj) {

        boolean moveSuccessful = false;
        if (Player.pieceAt(new Coordinate(numi, numj)) == null && moveAllowed) {
            moveSuccessful = Player.selectedPiece.move(new Coordinate(numi, numj));
            //update();

        } else if (moveAllowed) {
            Player.selectedPiece = Player.pieceAt(new Coordinate(numi, numj));
        }

        update();

        if (moveSuccessful) {
            moveAllowed = false;
            new Thread(mt).start();
        }

        update();

    }

    public void update() {
        //System.out.println(gui.getHeight());

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
                    chessBoardSquares.board[i][j].setIcon(new ImageIcon("blank.png"));
                    chessBoardSquares.board[i][j].setBackground(Color.WHITE);
                    //chessBoardSquares.board[i][j].setOpaque(false);
                } else {
                    chessBoardSquares.board[i][j].setIcon(new ImageIcon("blank.png"));
                    chessBoardSquares.board[i][j].setBackground(Color.LIGHT_GRAY);
                    //chessBoardSquares.board[i][j].setOpaque(false);
                }

                if (Player.pieceAt(new Coordinate(i, j)) != null) {

                    chessBoardSquares.board[i][j].setIcon(Player.pieceAt(new Coordinate(i, j)).toImage());

                }

                if (OpponentPlayer.pieceAt(new Coordinate(i, j)) != null) {

                    chessBoardSquares.board[i][j].setIcon(OpponentPlayer.pieceAt(new Coordinate(i, j)).toImage());

                }

            }

        }

    }

    public void lightPossibleMoves() {

        if (Player.selectedPiece != null) {

            for (Coordinate c : Player.selectedPiece.possibleMoves()) {

                int i = c.getX();
                int j = c.getY();

                chessBoardSquares.board[i][j].setBackground(Color.CYAN);
                //chessBoardSquares.board[i][j].setIcon(new ImageIcon("blank.png"));
            }

        } else {

            setGridColours();

        }

    }

    public void setGridColours() {

        for (int i = 0; i < chessBoardSquares.board.length; i++) {
            for (int j = 0; j < chessBoardSquares.board[i].length; j++) {
                JButton b = new JButton();

                if (Player.pieceAt(new Coordinate(i, j)) == null) {

                    if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
                        b.setBackground(Color.WHITE);
                    } else {
                        b.setBackground(Color.LIGHT_GRAY);
                    }
                    chessBoardSquares.board[j][i] = b;

                }
            }
        }
    }

//    public void play() {
//
//        if (!whiteTurn) {
//            OpponentPlayer.makeMove();
//            whiteTurn = true;
//        }
//
//        update();
//
//    }
    public JPanel returnFrame() {
        return gui;
    }

}

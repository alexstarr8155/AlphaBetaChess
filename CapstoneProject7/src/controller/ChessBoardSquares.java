/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.swing.JButton;

/**
 *
 * @author User
 */
public class ChessBoardSquares {
    
    public JButton[][] board;
    private boolean [][] containsPiece;
    
    public ChessBoardSquares(){        
        board = new JButton[8][8];
        containsPiece = new boolean[8][8];      
    }

    public JButton getSquares(int i, int j) {
        return board[i][j];
    }
    
    public void setContainsPiece(int i, int j, boolean contains){        
        containsPiece[i][j] = contains;        
    }
    
    public boolean containsPieceAt(int i, int j){        
        return containsPiece[i][j];        
    }
        
    
}

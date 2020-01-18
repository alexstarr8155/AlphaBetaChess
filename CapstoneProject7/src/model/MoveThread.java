/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.ChessBoard;

/**
 *
 * @author User
 */
public class MoveThread implements Runnable{

    private ChessBoard cb;
    private int level;
    public MoveThread(ChessBoard cb, int level){
        this.cb = cb;
        this.level = level;
    }
    
    @Override
    public void run() {
        OpponentPlayer.makeMove(level);
        cb.update();
        cb.moveAllowed = true;
    }
}

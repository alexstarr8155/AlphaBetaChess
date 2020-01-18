/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author User
 */
public class IntMovePair implements Comparable{
    
    int score;
    Move m;

    public IntMovePair(int score, Move m) {
        this.score = score;
        this.m = m;
    }    
    
    public int getScore() {
        return score;
    }

    public Move getMove() {
        return m;
    }

    @Override
    public int compareTo(Object o) {
        return this.score - ((IntMovePair)o).score;        
    }
    
    @Override
    public String toString(){
        return this.score + "";
    }
    
    
}

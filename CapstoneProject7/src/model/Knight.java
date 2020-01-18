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
public class Knight extends Piece{
    
    private static final int RANK = 3;
    
    public Knight (String filePath, Coordinate c, String team){        
        super(filePath, c, team, RANK);        
    }
    
    public Knight(Coordinate c, String team, int rank){
        super(c, team, rank);
    }
    
    @Override
    public ArrayList<Coordinate> possibleMoves(){
        
        return this.possibleMoves(new Board(""));
        
    }
    
    @Override
    public ArrayList<Coordinate> possibleMoves(Board b){
        
        ArrayList<Coordinate> a = new ArrayList<>();
        
        String colour;
        
        if (this.team().equals("Black")){
            colour = Board.WHITE;
        } else {
            colour = Board.BLACK;
        }
        
        for (int i = -2; i <= 2; i++){
            
            for (int j = -2; j <= 2; j++){
                
                if (Math.abs(i) + Math.abs(j) == 3){
                    
                    int x = this.getCoord().getX() + i;
                    int y = this.getCoord().getY() + j;
                    
                    Coordinate c = new Coordinate(x, y);
                    
                    if ((x >= 0 && x < 8) && (y >= 0 && y < 8) && !b.containsPiece(c)){                        
                        a.add(c);                        
                    } else if (b.containsPiece(c, colour)){
                        a.add(c);
                    }
                }                
            }            
        }
             
        return a;
        
    }
    
}

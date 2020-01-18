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
public class Move {
    
    private Piece toMove;
    private Coordinate placeToGo;
    private Coordinate startingPosition;
    
    public Move(Piece p, Coordinate c){        
        this.toMove = p;
        this.placeToGo = c;    
        this.startingPosition = p.getCoord();
    }
    
    public Move(){}
    

    public Piece getPiece() {
        return toMove;
    }

    public void setPiece(Piece toMove) {
        this.toMove = toMove;
    }

    public Coordinate getPlace() {
        return placeToGo;
    }

    public void setPlace(Coordinate placeToGo) {
        this.placeToGo = placeToGo;
    }
    
    public int value(){        
        return Player.pieceAt(placeToGo).getRank();        
    }
    
    public void move(){        
        this.getPiece().move(this.getPlace());        
    }
    
    public Coordinate getStartingPosition(){
        return this.startingPosition;
    }
    
    public int checkSurrounding(){   
        
        int value = 0;        
        for (Piece p : Player.pieces){   
            System.out.println(p.getCaputurableMoves());
            if (p.getCaputurableMoves().contains(this.getPlace())){
                value = (0 - this.getPiece().getRank());
                break;                
            }    
        }
        
        return value;
    }
    
    public void simulateMove(ArrayList<Piece> p){
        
        for (int i = 0; i < p.size(); i++){            
            if (p.get(i).equals(this.toMove)){
                p.get(i).setCoord(this.placeToGo);
                break;
            }            
        }
        
    }
    
    @Override
    public String toString(){
        return this.getPiece() + " move from " + startingPosition + " to " + this.getPlace();
    }
    
    
    
}

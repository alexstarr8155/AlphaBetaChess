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
public class Coordinate {
    
    int x, y;
    
    public Coordinate(int x, int y){
        
        this.x = x;
        this.y = y;
        
    }
    
    public Coordinate(){}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public boolean containsPiece() {

        return OpponentPlayer.containsPiece(this) || Player.containsPiece(this);

    }

    public boolean containsPiece(String otherTeam) {

        if (otherTeam.equals("Black")) {
            return Player.containsPiece(this);
        } else {
            return OpponentPlayer.containsPiece(this);
        }

    }
    
    @Override
    public boolean equals(Object c){
        
        if (!(c instanceof Coordinate)){            
            return false;            
        }
        
        Coordinate coord = (Coordinate)c;        
        return this.getX() == coord.getX() && this.getY() == coord.getY();
                
    }
    
    public boolean isValid(){
        
        return this.getX() >= 0 && this.getX() < 8 && this.getY() >= 0 && this.getY() < 8;
        
    }
    
    @Override
    public String toString(){
        
        return ("(" + this.getX() + ", " + this.getY() + ")");
        
    }
    
}

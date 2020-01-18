/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import view.View;
import javax.swing.*;

/**
 *
 * @author User
 */
public class Main {

    public static void main(String[] args) {

        Runnable r;
        r = new Runnable() {
            
            @Override
            public void run() {

                View.setUp();
                
            }

        };
        SwingUtilities.invokeLater(r);

    }
    

}

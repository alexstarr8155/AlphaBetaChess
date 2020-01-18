/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author User
 */
public class ImagePanel extends JPanel {

    private Image image;
    private Image image2;

    public ImagePanel(String imagePath, String imagePath2) {
        try {
            this.image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println(imagePath);
        };

        try {
            this.image2 = ImageIO.read(new File(imagePath2));
        } catch (IOException e) {
            System.out.println(imagePath2);
        };
    }

    public ImagePanel(String imagePath) {
        try {
            this.image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
           System.out.println(imagePath);
        };

        image2 = null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 5, 5, this);

        if (image2 != null) {
            g.drawImage(image2, this.getWidth() - 100, 5, this);
        }
    }
}

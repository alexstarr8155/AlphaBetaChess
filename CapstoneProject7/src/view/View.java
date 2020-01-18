/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ChessBoard;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author User
 */
public class View {

    private static JFrame chessFrame, openingFrame;
    public static final String KING_IN_CHECK = "King in Check, Try Again";
    public static final String CHECKMATE = "Checkmate, Black Wins";
    public static final String WHITE_WINS = "Checkmate, You Have Won";
    private static String[] pieceNames = {"Pawn", "Rook", "Knight", "Bishop", "Queen", "King"};

    public static void setUp() {
        openingFrame = new JFrame("Chess Game");
        openingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        openingFrame.setLocationByPlatform(true);
        openingFrame.setResizable(false);
        openingFrame.setMinimumSize(openingFrame.getSize());
        openingFrame.setSize(480, 300);
        //openingFrame.pack();
        openingFrame.setLocationRelativeTo(null);
        openingFrame.setVisible(true);

        int firstNum = (int) (Math.random() * pieceNames.length);
        int secondNum = (int) (Math.random() * pieceNames.length);
        while (firstNum == secondNum) {
            secondNum = (int) (Math.random() * pieceNames.length);
        }

        JPanel panel = new ImagePanel("B" + pieceNames[firstNum] + ".png", "W" + pieceNames[secondNum] + ".png");
        panel.setLayout(null);
        openingFrame.add(panel);

        JButton button = new JButton("Begin Game");
        button.setFont(new Font("Calibri", Font.BOLD, 24));
        button.setBounds(5, 215, 465, 50);
        panel.add(button);

        JLabel title = new JLabel("Game Setup");
        title.setFont(new Font("Calibri", Font.BOLD, 36));
        title.setBounds(145, 15, 450, 35);
        panel.add(title);

        JLabel playerLabel = new JLabel("Human or AI Opponent?: ");
        playerLabel.setBounds(25, 100, 500, 25);
        playerLabel.setFont(new Font("Calibri", Font.BOLD, 24));
        panel.add(playerLabel);

        JRadioButton radioButton1 = new JRadioButton("Two-Player");
        radioButton1.setFont(new Font("Calibri", Font.PLAIN, 14));
        radioButton1.setBounds(320, 125, 100, 15);
        panel.add(radioButton1);

        JRadioButton radioButton2 = new JRadioButton("One-Player");
        radioButton2.setFont(new Font("Calibri", Font.PLAIN, 14));
        radioButton2.setBounds(320, 100, 100, 15);
        panel.add(radioButton2);
        radioButton2.setSelected(true);

        ButtonGroup group = new ButtonGroup();
        group.add(radioButton1);
        group.add(radioButton2);

        JLabel label = new JLabel("Choose Opponent Difficulty: ");
        label.setFont(new Font("Calibri", Font.BOLD, 24));
        label.setBounds(25, 155, 500, 25);
        panel.add(label);

        String[] names = {"Advanced", "Intermediate", "Beginner 2", "Beginner 1"};
        JComboBox<String> levels = new JComboBox<>(names);
        levels.setSelectedIndex(1);
        levels.setFont(new Font("Calibri", Font.ITALIC, 18));
        levels.setBounds(320, 155, 135, 25);
        panel.add(levels);

        radioButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                levels.setEnabled(false);
            }

        });

        radioButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                levels.setEnabled(true);
            }

        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int level = getLevel((String) levels.getSelectedItem());
                chessFrame = setUpChessBoardFrame(radioButton1.isSelected(), level);
                //System.out.println(level);
                chessFrame.setLocationRelativeTo(null);
                openingFrame.setVisible(false);
                chessFrame.setVisible(true);
                chessFrame.setEnabled(true);

            }
        });
    }

    private static int getLevel(String t) {

        int num;
        switch (t) {

            case "Beginner 1":
                num = 2;
                break;
            case "Beginner 2":
                num = 3;
                break;
            case "Intermediate":
                num = 4;
                break;
            case "Advanced":
                num = 5;
                break;

            default:
                num = -1;
                break;

        }

        return num;
    }

    private static JFrame setUpChessBoardFrame(boolean twoPlayer, int level) {

        JFrame f = new JFrame("Chess Game");

        ChessBoard chessBoard = new ChessBoard(level, twoPlayer);
        f.add(chessBoard.returnFrame());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationByPlatform(true);
        f.pack();
        f.setMinimumSize(f.getSize());
        f.setSize(1058, 818);

        return f;

    }

    public static void displayDialog(String message) {

        JPanel panel = new JPanel();

        int width = 200;
        int height = 90;

        JDialog dialog = new JDialog();
        dialog.setPreferredSize(new Dimension(width, height));
        dialog.setSize(width, height);
        dialog.setLocation(((int) chessFrame.getLocation().getX() + chessFrame.getWidth() / 2) - width / 2,
                ((int) chessFrame.getLocation().getY() + chessFrame.getHeight() / 2) - height / 2);
        dialog.add(panel);

        JLabel label = new JLabel(message);
        label.setBounds(15, 5, 200, 10);
        panel.add(label);

        JButton button = new JButton("OK");
        button.setBounds(100, 100, 50, 20);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dialog.setVisible(false);

                if (message.equals(View.CHECKMATE) || message.equals(View.WHITE_WINS)) {
                    openingFrame.setVisible(true);
                    chessFrame.setVisible(false);
                }
            }
        });

        dialog.setResizable(false);
        dialog.setVisible(true);

    }

}

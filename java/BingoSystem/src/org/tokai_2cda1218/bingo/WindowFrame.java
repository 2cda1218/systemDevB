package org.tokai_2cda1218.bingo;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowFrame {
    private BingoMain bingoMain;
    private JLabel numberLabel;
    private JTextArea history;

    public WindowFrame(){
        bingoMain = new BingoMain();
    }

    public void CreateWindow(){
        //create frame
        var f = new JFrame("B!NGO");
        //exit
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame status
        f.setLayout(new BorderLayout());
        f.setSize(600, 400);
        f.setLocation(200, 200);
        //frame activate
        f.setVisible(true);
        //add button East
        var b = new JButton("Generate");
        f.add("East",b);
        //history number field
        history = new JTextArea();
        history.setEditable(false);
        history.setFont(new Font("",Font.PLAIN,48));
        f.add(new JScrollPane(history),BorderLayout.SOUTH);
        //display latest num
        numberLabel = new JLabel("",SwingConstants.CENTER);
        numberLabel.setFont(new Font("",Font.BOLD,96));
        f.add(numberLabel,BorderLayout.CENTER);

        //button action
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event){
                try{
                    int num = bingoMain.generateNum();
                    numberLabel.setText(String.valueOf(num));
                    history.append(num + ",");
                }
                catch (IllegalStateException e){
                    numberLabel.setText("all number is generated");
                }
            }
        });
        f.validate();
    }
}

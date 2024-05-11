package edu.hitsz.swing;

import edu.hitsz.application.CommonGame;
import edu.hitsz.application.EasyGame;
import edu.hitsz.application.HardGame;
import edu.hitsz.application.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu {


    private JButton commonButton;
    private JButton simpleTableButton;
    private JPanel MainPanel;
    private JButton easyButton;
    private JButton hardButton;
    private JComboBox comboBox;
    private JLabel musicLabel;


    public StartMenu() {
        commonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.difficulty = "COMMON";
                Main.game = new EasyGame();
                Main.cardPanel.add(Main.game);
                Main.cardLayout.last(Main.cardPanel);
                Main.game.action();
            }
        });
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.difficulty = "EASY";
                Main.game = new CommonGame();
                Main.cardPanel.add(Main.game);
                Main.cardLayout.last(Main.cardPanel);
                Main.game.action();
            }
        });
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.difficulty = "HARD";
                Main.game = new HardGame();
                Main.cardPanel.add(Main.game);
                Main.cardLayout.last(Main.cardPanel);
                Main.game.action();
            }
        });
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                if("关".equals((String) combo.getSelectedItem())){
                    Main.isPlayMusic = false;
                }else{
                    Main.isPlayMusic = true;
                }
                System.out.println("Selected Option: " + Main.isPlayMusic);
            }
        });
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }

}

package edu.hitsz.swing;

import edu.hitsz.application.Main;
import edu.hitsz.dao.RankDao;
import edu.hitsz.dao.impl.RankDaoImpl;
import edu.hitsz.dao.pojo.Rank;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScoreTable {
    private JPanel mainPanel;
    private JPanel topPanel;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private JScrollPane tableScrollPanel;
    private JTable scoreTable;
    private JLabel headerLabel;
    private JPanel bottomPanel;
    private JButton deleteButton;
    private JLabel difficultyLabel;
    private JLabel difficultyInputLabel;
    private JButton returnButton;

    private String[][] tableData;
    
    private RankDao rankDao = new RankDaoImpl();

    private DefaultTableModel model;
    private final String[] columnName = {"名次","玩家名","得分","记录时间"};


    public ScoreTable() {
        tableScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        rankDao.select();
        tableData = rankDao.toArray();
        model = new DefaultTableModel(tableData, columnName){
            @Override
            public boolean isCellEditable(int row, int col){
                return false;
            }
        };

        //JTable并不存储自己的数据，而是从表格模型那里获取它的数据
        scoreTable.setModel(model);
        tableScrollPanel.setViewportView(scoreTable);

        difficultyInputLabel.setText(Main.difficulty);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = scoreTable.getSelectedRow();
                System.out.println(row);
                int result = JOptionPane.showConfirmDialog(deleteButton,
                        "是否确定中删除？");
                if (JOptionPane.YES_OPTION == result && row != -1) {
                    model.removeRow(row);
                    rankDao.delete(rankDao.selectById(row));
                }
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.cardLayout.first(Main.cardPanel);
            }
        });
    }
    public void inputName(){
        //创建一个输入界面
        JPanel customPanel = new JPanel(new BorderLayout(5, 5));
        JLabel label = new JLabel("<html>游戏结束,你的得分为"+Main.game.getScore()+"<br>请输入名字记录得分：</html>");
        JTextField textField = new JTextField(20);
        customPanel.add(label, BorderLayout.NORTH);
        customPanel.add(textField, BorderLayout.SOUTH);
        int option = JOptionPane.showConfirmDialog(null, customPanel, "输入", JOptionPane.OK_CANCEL_OPTION);
        if(option==JOptionPane.OK_OPTION){
            Rank rank = new Rank();
            rank.setScore(Main.game.getScore());
            rank.setName(textField.getText());
            rank.setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd hh:mm")));
            rankDao.add(rank);
        }
        rankDao.select();
        String[][] datas = rankDao.toArray();
        model = new DefaultTableModel(datas,columnName){
            @Override
            public boolean isCellEditable(int row, int col){
                return false;
            }
        };
        scoreTable.setModel(model);
        tableScrollPanel.setViewportView(scoreTable);
    }
}

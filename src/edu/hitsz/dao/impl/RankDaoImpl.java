package edu.hitsz.dao.impl;

import edu.hitsz.dao.RankDao;
import edu.hitsz.dao.pojo.Rank;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author linyu
 */
public class RankDaoImpl implements RankDao {

    private List<Rank> ranks;
    private String filePath = "persons.txt";

    public RankDaoImpl() {
        ranks = new ArrayList<>();
        // 文件路径

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 假设每行数据格式为 "姓名,年龄"
                String[] data = line.split(",");
                if (data.length == 3) {
                    // 去除空格
                    String name = data[0].trim();
                    int score = Integer.parseInt(data[1].trim());
                    String time = data[2].trim();
                    // 解析为整数
                    int age = Integer.parseInt(data[1].trim());
                    Rank rank = new Rank(name,score,time);
                    ranks.add(rank);
                } else {
                    System.err.println("Invalid data format in line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Rank> select() {
        ranks.sort(Comparator.comparing(Rank::getScore).reversed());
        return ranks;
    }

    @Override
    public void add(Rank rank) {
        ranks.add(rank);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true))) {
            String line = rank.getName() + "," + rank.getScore()+","+rank.getTime();
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Rank rank){
        ranks.remove(rank);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,false))) {
            for (Rank rank1 : ranks) {
                String line = rank1.getName() + "," + rank1.getScore()+","+rank1.getTime();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[][] toArray(){
        String[][] datas = new String[ranks.size()][];
        int i = 0;
        for (Rank rank : ranks) {
            datas[i] = new String[]{String.valueOf(i+1),rank.getName(),String.valueOf(rank.getScore()),rank.getTime()};
            i++;
        }
        return datas;
    }

    @Override
    public Rank selectById(int id) {
        return ranks.get(id);
    }
}

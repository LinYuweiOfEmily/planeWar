package edu.hitsz.dao.pojo;

import java.time.LocalDateTime;

/**
 * @author linyu
 */
public class Rank {
    private String name;
    private int score;
    private String time;

    public Rank() {
    }

    public Rank(String name, int score, String time) {
        this.name = name;
        this.score = score;
        this.time = time;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}

package edu.hitsz.dao;

import edu.hitsz.dao.pojo.Rank;

import java.util.List;

/**
 * @author linyu
 */
public interface RankDao {
    /**
     * @return 查询所有分数
     */
    List<Rank> select();

    /**
     * @param rank
     * 添加数据
     */
    void add(Rank rank);

    void delete(Rank rank);

    String[][] toArray();
    Rank selectById(int id);
}

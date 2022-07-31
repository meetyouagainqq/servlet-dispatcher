package com.atguigu.fruit.dao.impl;

import com.atguigu.fruit.base.BaseDao;
import com.atguigu.fruit.dao.FruitDao;
import com.atguigu.fruit.entity.Fruit;

import java.util.List;

/**
 * @author sunshine
 * @version 1.0
 * @date 2022年07月28日 9:02
 * @description
 */
public class FruitDaoImpl extends BaseDao<Fruit> implements FruitDao {
    @Override
    public List<Fruit> getFruitList(String keyword,Integer pageNo) {
        return super.executeQuery("select * from t_fruit where fname like ? limit ?,5","%"+keyword+"%", (pageNo - 1) * 5);
    }

    @Override
    public boolean insertFruit(Fruit fruit) {
        String sql = "insert into t_fruit  values(0,?,?,?,?)";
        int count = super.executeUpdate(sql, fruit.getFname(), fruit.getPrice(), fruit.getFcount(), fruit.getRemark());
        return count > 0;
    }

    @Override
    public boolean updateFruit(Fruit fruit) {
        String sql = "update t_fruit set fcount = ? where fid = ?";
        int result = super.executeUpdate(sql, fruit.getFid());
        return result > 0;
    }

    @Override
    public Fruit getFruitByName(String fname) {
        String sql = "select * from t_fruit where fname like ?";
        return super.load(sql, fname);
    }

    @Override
    public boolean deleteFruit(String fname) {
        String sql = "delete from t_fruit where fname like ?";
        return super.executeUpdate(sql, fname) > 0;
    }

    @Override
    public Fruit getFruitById(Integer id) {
        String sql = "select * from t_fruit where fid = ?";
        return super.load(sql, id);
    }

    @Override
    public void updateFruitById(Fruit fruit) {
        String sql = "update t_fruit set fname = ?,price = ?,fcount = ?,remark = ? where fid = ?";
        super.executeUpdate(sql, fruit.getFname(), fruit.getPrice(), fruit.getFcount(), fruit.getRemark(), fruit.getFid());
    }

    @Override
    public void deleteFruitById(Integer id) {
        String sql = "delete from t_fruit where fid = ?";
        super.executeUpdate(sql, id);
    }

    @Override
    public int getFruitCount(String keyword) {
        String sql = "select count(*) from t_fruit where fname like ?";
        return ((Long) super.executeComplexQuery(sql,"%"+keyword+"%")[0]).intValue();
    }
}

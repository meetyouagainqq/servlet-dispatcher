package com.atguigu.fruit.dao.impl;

import cn.hutool.core.util.StrUtil;
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
    public List<Fruit> getFruitList() {
        return super.executeQuery("select * from t_fruit");
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
        super.executeUpdate(sql,fruit.getFname(),fruit.getPrice(),fruit.getFcount(),fruit.getRemark(), fruit.getFid());
    }
}

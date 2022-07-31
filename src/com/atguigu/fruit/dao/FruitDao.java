package com.atguigu.fruit.dao;

import com.atguigu.fruit.entity.Fruit;



import java.util.List;

/**
 * @author sunshine
 * @version 1.0
 * @date 2022年07月27日 22:26
 * @description
 */
public interface FruitDao {
    List<Fruit> getFruitList(String keyword,Integer pageNo);

    boolean insertFruit(Fruit fruit);

    boolean updateFruit(Fruit fruit);

    //根据名称查询特定库存
    Fruit getFruitByName(String fname);

    boolean deleteFruit(String fname);

    Fruit getFruitById(Integer id);

    void updateFruitById(Fruit fruit);

    void deleteFruitById(Integer id);
    int getFruitCount(String keyword);
}

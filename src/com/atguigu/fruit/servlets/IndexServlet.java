package com.atguigu.fruit.servlets;

import com.atguigu.fruit.dao.FruitDao;
import com.atguigu.fruit.dao.impl.FruitDaoImpl;
import com.atguigu.fruit.entity.Fruit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author sunshine
 * @version 1.0
 * @date 2022年07月29日 9:20
 * @description
 */
@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FruitDao fruitDao=new FruitDaoImpl();
        List<Fruit> fruitList = fruitDao.getFruitList();
       //fruitList.forEach(System.out::println);
        HttpSession session = req.getSession();
        session.setAttribute("fruitList",fruitList);
        super.processTemplate("index",req,resp);
    }
}

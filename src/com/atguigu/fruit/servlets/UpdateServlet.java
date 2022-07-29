package com.atguigu.fruit.servlets;

import com.atguigu.fruit.dao.FruitDao;
import com.atguigu.fruit.dao.impl.FruitDaoImpl;
import com.atguigu.fruit.entity.Fruit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author sunshine
 * @version 1.0
 * @date 2022年07月29日 22:08
 * @description
 */
@WebServlet("/update.do")
public class UpdateServlet  extends ViewBaseServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FruitDao fruitDao=new FruitDaoImpl();
        req.setCharacterEncoding("UTF-8");
        String fid = req.getParameter("fid");
        int id = Integer.parseInt(fid);
        String fname = req.getParameter("fname");
        String price = req.getParameter("price");
        int priceValue = Integer.parseInt(price);
        String fcount = req.getParameter("fcount");
        int countValue = Integer.parseInt(fcount);
        String remark = req.getParameter("remark");
        fruitDao.updateFruitById(new Fruit(id,fname,priceValue,countValue,remark));
        //super.processTemplate("index",req,resp);
        resp.sendRedirect("index");
    }
}

package com.atguigu.fruit.servlets;

import cn.hutool.core.util.StrUtil;
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
 * @date 2022年07月29日 21:23
 * @description
 */
@WebServlet("/edit.do")
public class EditServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fid = req.getParameter("fid");
        if (!StrUtil.isBlank(fid)) {
            int id = Integer.parseInt(fid);
            FruitDao fruitDao = new FruitDaoImpl();
            Fruit fruit = fruitDao.getFruitById(id);
            req.setAttribute("fruit", fruit);
            super.processTemplate("edit", req, resp);
        }
    }
}

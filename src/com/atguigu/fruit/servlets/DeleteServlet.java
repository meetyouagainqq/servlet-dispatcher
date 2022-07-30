package com.atguigu.fruit.servlets;

import cn.hutool.core.util.StrUtil;
import com.atguigu.fruit.dao.FruitDao;
import com.atguigu.fruit.dao.impl.FruitDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author sunshine
 * @version 1.0
 * @date 2022年07月29日 23:12
 * @description
 */
@WebServlet("/delete.do")
public class DeleteServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FruitDao fruitDao = new FruitDaoImpl();
        String fid = req.getParameter("fid");
        if (!StrUtil.isBlank(fid)) {
            int id = Integer.parseInt(fid);
            fruitDao.deleteFruitById(id);
            resp.sendRedirect("index");
        }
    }
}

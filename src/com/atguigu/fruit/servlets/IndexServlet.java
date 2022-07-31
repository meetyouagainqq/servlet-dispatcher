package com.atguigu.fruit.servlets;

import cn.hutool.core.util.StrUtil;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        Integer pageNo = 1;
        FruitDao fruitDao = new FruitDaoImpl();
        String oper = req.getParameter("oper");
        String keyword = null;
        if (!StrUtil.isBlank(oper) && "search".equals(oper)) {
            pageNo = 1;
            keyword = req.getParameter("keyword");
            if (StrUtil.isBlank(keyword)) {
                keyword = "";
            }
            session.setAttribute("keyword", keyword);
        } else {
            String pageNoValue = req.getParameter("pageNo");
            if (!StrUtil.isBlank(pageNoValue)) {
                pageNo = Integer.parseInt(pageNoValue);
            }
            Object keyword1 = session.getAttribute("keyword");
            if (keyword1 != null) {
                keyword = (String) keyword1;
            } else {
                keyword = "";
            }
        }
        session.setAttribute("pageNo", pageNo);
        List<Fruit> fruitList = fruitDao.getFruitList(keyword, pageNo);
        session.setAttribute("fruitList", fruitList);
        int fruitCount = fruitDao.getFruitCount(keyword);
        int totalPage = fruitCount / 5;
        totalPage = fruitCount % 5 == 0 ? totalPage : totalPage + 1;
        session.setAttribute("totalPage", totalPage);
        super.processTemplate("index", req, resp);


//        String pageNoValue = req.getParameter("pageNo");
//        if (!StrUtil.isBlank(pageNoValue)) {
//            pageNo = Integer.parseInt(pageNoValue);
//        }
//        List<Fruit> fruitList = fruitDao.getFruitList(pageNo);
//
//        session.setAttribute("pageNo", pageNo);
//        session.setAttribute("fruitList", fruitList);
//        int fruitCount = fruitDao.getFruitCount();
//        int totalPage = fruitCount / 5;
//        totalPage = fruitCount % 5 == 0 ? totalPage : totalPage + 1;
//        session.setAttribute("totalPage", totalPage);
//        super.processTemplate("index", req, resp);
    }
}

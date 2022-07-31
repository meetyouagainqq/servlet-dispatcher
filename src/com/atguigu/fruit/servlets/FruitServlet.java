package com.atguigu.fruit.servlets;

import cn.hutool.core.util.StrUtil;
import com.atguigu.fruit.dao.FruitDao;
import com.atguigu.fruit.dao.impl.FruitDaoImpl;
import com.atguigu.fruit.entity.Fruit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author sunshine
 * @version 1.0
 * @date 2022年07月31日 16:54
 * @description
 */
@WebServlet("/fruit")
public class FruitServlet extends ViewBaseServlet {
    FruitDao fruitDao = new FruitDaoImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String operate = req.getParameter("operate");
        if (StrUtil.isBlank(operate)) {
            operate= "index";
        }
        switch (operate) {
            case "index":
                index(req, resp);
                break;
            case "add":
                add(req,resp);
                break;
            case "del":
                del(req,resp);
                break;
            case "edit":
                edit(req,resp);
                break;
            case "update":
                update(req,resp);
                break;
            default:
                throw new RuntimeException("operation值非法!");

        }

    }


    private void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        Integer pageNo = 1;

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
    }
    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String fname = req.getParameter("fname");
        int price = Integer.parseInt(req.getParameter("price"));
        int fcount=Integer.parseInt(req.getParameter("fcount"));
        String remark = req.getParameter("remark");
        fruitDao.insertFruit(new Fruit(0,fname,price,fcount,remark));
        resp.sendRedirect("fruit");
    }
   private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fid = req.getParameter("fid");
        if (!StrUtil.isBlank(fid)) {
            int id = Integer.parseInt(fid);
            fruitDao.deleteFruitById(id);
            resp.sendRedirect("fruit");
        }
    }
    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fid = req.getParameter("fid");
        if (!StrUtil.isBlank(fid)) {
            int id = Integer.parseInt(fid);
            Fruit fruit = fruitDao.getFruitById(id);
            req.setAttribute("fruit", fruit);
            super.processTemplate("edit", req, resp);
        }
    }
    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        resp.sendRedirect("fruit");
    }
}

package com.cy.store.controller;

import com.cy.store.entity.Cart;
import com.cy.store.service.ICartService;
import com.cy.store.util.JsonResult;
import com.cy.store.vo.CartVO;
import com.sun.corba.se.spi.orb.ParserImplBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("carts")
public class CartController extends BaseController {
    @Autowired
    ICartService cartService;

    @RequestMapping("add_to_cart")
    public JsonResult<Void> addToCart(Integer pid, Integer amount, HttpSession session) {
        Integer uid = (Integer) session.getAttribute("uid");
        String username = (String) session.getAttribute("username");
        cartService.addToCart(uid, pid, amount, username);
        return new JsonResult<Void>(OK);
    }

    @GetMapping({"", "/"})
    public JsonResult<List<CartVO>> getVOByUid(HttpSession session) {
        Integer uid = (Integer) session.getAttribute("uid");
        return new JsonResult<List<CartVO>>(OK, cartService.getVOByUid(uid));
    }

    @RequestMapping("{cid}/num/add")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session) {
        Integer uid = (Integer) session.getAttribute("uid");
        String username = (String) session.getAttribute("username");
        return new JsonResult<Integer>(OK, cartService.addNum(cid, uid, username));
    }

    @RequestMapping("{cid}/num/reduce")
    public JsonResult<Integer> reduceNum(@PathVariable("cid") Integer cid, HttpSession session) {
        Integer uid = (Integer) session.getAttribute("uid");
        String username = (String) session.getAttribute("username");
        return new JsonResult<Integer>(OK, cartService.reduceNum(cid, uid, username));
    }

    @RequestMapping("{cid}/remove_to_cart")
    public JsonResult<Void> removeToCart(@PathVariable("cid") Integer cid, HttpSession session) {
        Integer uid = (Integer) session.getAttribute("uid");
        String username = (String) session.getAttribute("username");
        cartService.remove(cid, uid, username);
        return new JsonResult<Void>(OK);
    }

    @GetMapping("list")
    public JsonResult<List<CartVO>> getVOByCids(Integer[] cids, HttpSession session) {
        Integer uid = (Integer) session.getAttribute("uid");
        return new JsonResult<List<CartVO>>(OK, cartService.getVOByCids(uid,cids));
    }

}

package com.nhy.demo.mall.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nhy.demo.mall.entity.Order;
import com.nhy.demo.mall.entity.OrderItem;
import com.nhy.demo.mall.entity.pojo.ResultBean;
import com.nhy.demo.mall.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    //打开订单列表页面
    @RequestMapping("/toList.html")
    public String toOrderList() {
        return "mall/order/list";
    }

    @RequestMapping("/toPay.html")
    public String toOrderPayment(int id, Map<String, Object> map) {
        Order order = orderService.findById(id);
        map.put("order", order);
        return "mall/order/list";
    }

    //查询用户订单列表
    @RequestMapping("/list.do")
    @ResponseBody
    public ResultBean<List<Order>> listData(HttpServletRequest request) {
        List<Order> orders = orderService.findUserOrder(request);
        return new ResultBean<>(orders);
    }

    //查询订单详情
    @RequestMapping("/getDetail.do")
    @ResponseBody
    public ResultBean<List<OrderItem>> getDetail(int orderId) {
        List<OrderItem> orderItems = orderService.findItems(orderId);
        return new ResultBean<>(orderItems);
    }

    //提交订单
    @RequestMapping("/submit.do")
    public void submit(String name, String phone, String addr, HttpServletRequest request, HttpServletResponse response) throws Exception {
        orderService.submit(name, phone, addr, request, response);
    }

    //支付
    @RequestMapping("pay.do")
    @ResponseBody
    public ResultBean<Boolean> pay(int orderId, HttpServletResponse response) throws IOException {
        orderService.pay(orderId);
        return new ResultBean<>(true);
    }

    //确认收货
    @RequestMapping("receive.do")
    @ResponseBody
    public ResultBean<Boolean> receive(int orderId, HttpServletResponse response) throws IOException {
        orderService.receive(orderId);
        return new ResultBean<>(true);
    }
}

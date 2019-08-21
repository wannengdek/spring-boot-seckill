package spring.mooc.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import spring.mooc.seckill.bean.MiaoshaUser;
import spring.mooc.seckill.bean.OrderInfo;
import spring.mooc.seckill.redis.RedisService;
import spring.mooc.seckill.result.CodeMsg;
import spring.mooc.seckill.result.Result;
import spring.mooc.seckill.service.GoodsService;
import spring.mooc.seckill.service.MiaoshaUserService;
import spring.mooc.seckill.service.OrderService;
import spring.mooc.seckill.vo.GoodsVo;
import spring.mooc.seckill.vo.OrderDetailVo;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
    MiaoshaUserService userService;
	
	@Autowired
    RedisService redisService;

	@Autowired
    OrderService orderService;

	@Autowired
    GoodsService goodsService;

	
    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model, MiaoshaUser user, @RequestParam("orderId")long orderId)
    {
        if (user==null)
        {
             return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo order = orderService.getOrderById(orderId);
        if (order==null)
        {
            return  Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);

        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(order);
        vo.setGoods(goods);
        return Result.success(vo);


    }
    
}

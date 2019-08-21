package spring.mooc.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import spring.mooc.seckill.bean.MiaoshaOrder;
import spring.mooc.seckill.bean.MiaoshaUser;
import spring.mooc.seckill.bean.OrderInfo;
import spring.mooc.seckill.redis.RedisService;
import spring.mooc.seckill.result.CodeMsg;
import spring.mooc.seckill.result.Result;
import spring.mooc.seckill.service.GoodsService;
import spring.mooc.seckill.service.MiaoshaService;
import spring.mooc.seckill.service.MiaoshaUserService;
import spring.mooc.seckill.service.OrderService;
import spring.mooc.seckill.vo.GoodsVo;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

	@Autowired
	MiaoshaUserService userService;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	MiaoshaService miaoshaService;




    @RequestMapping(value = "/do_miaosha",method = RequestMethod.POST)
	@ResponseBody
    public Result<OrderInfo> list(Model model, MiaoshaUser user,
					   @RequestParam("goodsId")long goodsId) {
    	model.addAttribute("user", user);
		//由于使用了  argumentResolver   直接可以获取到  user  然后将它加入到 model 中
    	if(user == null) {
    		return Result.error(CodeMsg.SESSION_ERROR);
    	}
    	//判断库存
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    	int stock = goods.getStockCount();
		System.out.println("goods"+goods.toString());
    	if(stock <= 0) {
			System.out.println("秒杀结束");
    		return Result.error(CodeMsg.MIAO_SHA_OVER);
    	}
    	//判断是否已经秒杀到了
    	MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
    	if(order != null) {
    		return Result.error(CodeMsg.REPEATE_MIAOSHA);
    	}
    	//减库存 下订单 写入秒杀订单
    	OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
        return Result.success(orderInfo);
    }
}

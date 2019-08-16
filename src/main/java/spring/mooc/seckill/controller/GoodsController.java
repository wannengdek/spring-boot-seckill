package spring.mooc.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import spring.mooc.seckill.bean.MiaoshaUser;
import spring.mooc.seckill.redis.RedisService;
import spring.mooc.seckill.service.GoodsService;
import spring.mooc.seckill.service.MiaoshaUserService;
import spring.mooc.seckill.vo.GoodsVo;

import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	MiaoshaUserService userService;
	
	@Autowired
	RedisService redisService;

	@Autowired
	GoodsService goodsService;
	
//    @RequestMapping(value = "/to_list",method = RequestMethod.GET)
//    public String list(Model model, MiaoshaUser user) {
//    	model.addAttribute("user", user);
//        return "goods_list";
//    }

	@RequestMapping(value = "/to_list",method = RequestMethod.GET)
	public String list(Model model,MiaoshaUser user) {
		model.addAttribute("user", user);
		//查询商品列表
		List<GoodsVo> goodsList = goodsService.listGoodsVo();
		model.addAttribute("goodsList", goodsList);
		return "goods_list";
	}

	@RequestMapping("/to_detail/{goodsId}")
	public String detail(Model model,MiaoshaUser user,
						 @PathVariable("goodsId")long goodsId) {

		model.addAttribute("user", user);

		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		System.out.println(goods);
		model.addAttribute("goods", goods);

		long startAt = goods.getStartDate().getTime();
		long endAt = goods.getEndDate().getTime();
		long now = System.currentTimeMillis();

		int miaoshaStatus = 0;
		int remainSeconds = 0;
		if(now < startAt ) {//秒杀还没开始，倒计时
			miaoshaStatus = 0;
			remainSeconds = (int)((startAt - now )/1000);
		}else  if(now > endAt){//秒杀已经结束
			miaoshaStatus = 2;
			remainSeconds = -1;
		}else {//秒杀进行中
			miaoshaStatus = 1;
			remainSeconds = 0;
		}
		model.addAttribute("miaoshaStatus", miaoshaStatus);
		model.addAttribute("remainSeconds", remainSeconds);
		return "goods_detail";
	}


}

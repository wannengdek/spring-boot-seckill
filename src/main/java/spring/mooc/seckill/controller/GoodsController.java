package spring.mooc.seckill.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import spring.mooc.seckill.bean.MiaoshaUser;
import spring.mooc.seckill.redis.GoodsKey;
import spring.mooc.seckill.redis.RedisService;
import spring.mooc.seckill.result.Result;
import spring.mooc.seckill.service.GoodsService;
import spring.mooc.seckill.service.MiaoshaUserService;
import spring.mooc.seckill.vo.GoodsDetailVo;
import spring.mooc.seckill.vo.GoodsVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

	@Autowired
	ThymeleafViewResolver thymeleafViewResolver;

	@Autowired
	ApplicationContext applicationContext;
	
//    @RequestMapping(value = "/to_list",method = RequestMethod.GET)
//    public String list(Model model, MiaoshaUser user) {
//    	model.addAttribute("user", user);
//        return "goods_list";
//    }

	/**
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @param user
	 * @return
	 * 页面缓存:
	 * 10*1000   qps 998
	 */
	@RequestMapping(value = "/to_list",produces = "text/html")
	@ResponseBody
	public String list(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user) {
		model.addAttribute("user", user);
		//查询商品列表
		//取缓存
		String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
		if (!StringUtils.isEmpty(html))
		{
			System.out.println("htmllllll");
			return html;
		}
		List<GoodsVo> goodsList = goodsService.listGoodsVo();
		model.addAttribute("goodsList", goodsList);
//		//手动渲染
//		IWebContext ctx =new WebContext(request,response,
//				request.getServletContext(),request.getLocale(),model.asMap());
//		html = thymeleafViewResolver.getTemplateEngine().process("goods_list.html", ctx);


//		WebContext wtx = new WebContex(request,response,request.getServletContext(),
//				request.getLocale(),model.asMap());
//
//		html = thymeleafViewResolver.getTemplateEngine().process("goods_list",wtx);

		WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
		html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);

//		System.out.println(html);
		if (!StringUtils.isEmpty(html))
		{
			System.out.println("set redis");
			redisService.set(GoodsKey.getGoodsList, "", html);
		}
		return html;
	}

//	@RequestMapping(value = "/to_detail/{goodsId}",produces = "text/html")
//	@ResponseBody
//	public String detail(HttpServletRequest request, HttpServletResponse response,Model model,MiaoshaUser user,
//						 @PathVariable("goodsId")long goodsId) {
//
//		model.addAttribute("user", user);
//		//由于使用了  argumentResolver   直接可以获取到  user  然后将它加入到 model 中
//
//
//		//取缓存
//		String html = redisService.get(GoodsKey.getGoodsDetail, ""+goodsId, String.class);
//		if (!StringUtils.isEmpty(html))
//		{
//			System.out.println("htmllllll");
//			return html;
//		}
//		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
//		System.out.println(goods);
//		model.addAttribute("goods", goods);
//
//		long startAt = goods.getStartDate().getTime();
//		long endAt = goods.getEndDate().getTime();
//		long now = System.currentTimeMillis();
//
//		int miaoshaStatus = 0;
//		int remainSeconds = 0;
//		if(now < startAt ) {//秒杀还没开始，倒计时
//			miaoshaStatus = 0;
//			remainSeconds = (int)((startAt - now )/1000);
//		}else  if(now > endAt){//秒杀已经结束
//			miaoshaStatus = 2;
//			remainSeconds = -1;
//		}else {//秒杀进行中
//			miaoshaStatus = 1;
//			remainSeconds = 0;
//		}
//		model.addAttribute("miaoshaStatus", miaoshaStatus);
//		model.addAttribute("remainSeconds", remainSeconds);
////		return "goods_detail";
//
//
//		WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
//		html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
//
////		System.out.println(html);
//		if (!StringUtils.isEmpty(html))
//		{
//			System.out.println("set redis");
//			redisService.set(GoodsKey.getGoodsDetail, "", html);
//		}
//		return html;
//	}

	/**
	 * 页面静态化处理
	 * @param request
	 * @param response
	 * @param model
	 * @param user
	 * @param goodsId
	 * @return
	 */
	@RequestMapping(value="/detail/{goodsId}")
	@ResponseBody
	public Result<GoodsDetailVo> detail(MiaoshaUser user,
										@PathVariable("goodsId")long goodsId) {
		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
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
		GoodsDetailVo vo = new GoodsDetailVo();
		vo.setGoods(goods);
		vo.setUser(user);
		vo.setRemainSeconds(remainSeconds);
		vo.setMiaoshaStatus(miaoshaStatus);
		return Result.success(vo);
	}

	/**
	 * 页面静态化处理
	 * @param request
	 * @param response
	 * @param model
	 * @param user
	 * @param goodsId
	 * @return
	 */
	@RequestMapping(value = "/to_detail2/{goodsId}",produces = "text/html")
	@ResponseBody
	public String detail2(HttpServletRequest request, HttpServletResponse response,Model model,MiaoshaUser user,
						 @PathVariable("goodsId")long goodsId) {

		model.addAttribute("user", user);
		//由于使用了  argumentResolver   直接可以获取到  user  然后将它加入到 model 中


		//取缓存
		String html = redisService.get(GoodsKey.getGoodsDetail, ""+goodsId, String.class);
		if (!StringUtils.isEmpty(html))
		{
			System.out.println("htmllllll");
			return html;
		}
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
//		return "goods_detail";


		WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
		html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);

//		System.out.println(html);
		if (!StringUtils.isEmpty(html))
		{
			System.out.println("set redis");
			redisService.set(GoodsKey.getGoodsDetail, "", html);
		}
		return html;
	}
}

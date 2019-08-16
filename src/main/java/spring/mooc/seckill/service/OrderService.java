package spring.mooc.seckill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.mooc.seckill.bean.MiaoshaOrder;
import spring.mooc.seckill.bean.MiaoshaUser;
import spring.mooc.seckill.bean.OrderInfo;
import spring.mooc.seckill.mapper.OrderMapper;
import spring.mooc.seckill.vo.GoodsVo;

import java.util.Date;

@Service
public class OrderService {
	
	@Autowired
	OrderMapper orderMapper;
	
	public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
		return orderMapper.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
	}

	/**
	 * 创建订单
	 * @param user
	 * @param goods
	 * @return
	 */
	@Transactional
	public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCreateDate(new Date());
		orderInfo.setDeliveryAddrId(0L);
		orderInfo.setGoodsCount(1);
		orderInfo.setGoodsId(goods.getId());
		orderInfo.setGoodsName(goods.getGoodsName());
		orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
		orderInfo.setOrderChannel(1);
		orderInfo.setStatus(0);
		orderInfo.setUserId(user.getId());

		long orderId = orderMapper.insert(orderInfo);
		System.out.println("秒杀订单id为:"+orderId);

		MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
		miaoshaOrder.setGoodsId(goods.getId());
		miaoshaOrder.setOrderId(orderId);
		miaoshaOrder.setUserId(user.getId());
		System.out.println("秒杀订单是:"+miaoshaOrder.toString());

		orderMapper.insertMiaoshaOrder(miaoshaOrder);
		return orderInfo;
	}


	public void deleteOrders() {
		orderMapper.deleteOrders();
		orderMapper.deleteMiaoshaOrders();
	}
}

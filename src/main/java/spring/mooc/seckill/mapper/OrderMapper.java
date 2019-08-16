package spring.mooc.seckill.mapper;

import org.apache.ibatis.annotations.*;
import spring.mooc.seckill.bean.MiaoshaOrder;
import spring.mooc.seckill.bean.OrderInfo;

/**
 * @author dk
 */
@Mapper
public interface OrderMapper {
	
	public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

	/**新建订单  使用数据库返回的订单id 作为 秒杀订单的内容.
	 * @param orderInfo
	 * @return
	 */
	public long insert(OrderInfo orderInfo);

	/** 新建秒杀订单
	 * @param miaoshaOrder
	 * @return
	 */
	public int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

	public OrderInfo getOrderById(@Param("orderId")long orderId);

	public void deleteOrders();

	public void deleteMiaoshaOrders();

}

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

	/**新建订单
	 * @param orderInfo
	 * @return
	 */
	@Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
			+ "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
	@SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
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

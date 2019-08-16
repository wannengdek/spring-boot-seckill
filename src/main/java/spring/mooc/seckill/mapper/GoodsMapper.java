package spring.mooc.seckill.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import spring.mooc.seckill.bean.MiaoshaGoods;
import spring.mooc.seckill.vo.GoodsVo;

import java.util.List;

@Mapper
public interface GoodsMapper {
	
	public List<GoodsVo> listGoodsVo();

	public GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);


	/** 减少库存
	 * @param g
	 * @return
	 */
	public int reduceStock(MiaoshaGoods g);

	public int resetStock(MiaoshaGoods g);
}

package spring.mooc.seckill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.mooc.seckill.bean.MiaoshaGoods;
import spring.mooc.seckill.mapper.GoodsMapper;
import spring.mooc.seckill.vo.GoodsVo;

import java.util.List;

@Service
public class GoodsService {
	
	@Autowired
	GoodsMapper goodsMapper;
	
	public List<GoodsVo> listGoodsVo(){
		return goodsMapper.listGoodsVo();
	}

	public GoodsVo getGoodsVoByGoodsId(long goodsId) {
		return goodsMapper.getGoodsVoByGoodsId(goodsId);
	}

	public void reduceStock(GoodsVo goods) {
		MiaoshaGoods g = new MiaoshaGoods();
		g.setGoodsId(goods.getId());
		goodsMapper.reduceStock(g);
	}
	
	
	
}

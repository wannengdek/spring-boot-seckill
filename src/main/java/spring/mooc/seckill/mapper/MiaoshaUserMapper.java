package spring.mooc.seckill.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import spring.mooc.seckill.bean.MiaoshaUser;

@Mapper
public interface MiaoshaUserMapper {


	public MiaoshaUser getById(@Param("id") long id);


	public MiaoshaUser insert(MiaoshaUser miaoshaUser);

	public void update(MiaoshaUser toBeUpdate);


}

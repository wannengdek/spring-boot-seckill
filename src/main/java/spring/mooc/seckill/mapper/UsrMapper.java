package spring.mooc.seckill.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import spring.mooc.seckill.bean.Usr;

/**
 * @author : dk
 * @date : 2019/8/13 23:42
 * @description :
 */

@Mapper
public interface UsrMapper {

    public Usr test(@Param("id") int id);
}

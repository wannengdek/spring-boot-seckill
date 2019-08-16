package spring.mooc.seckill.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import spring.mooc.seckill.bean.User;

@Mapper
public interface UserMapper {

    public User getById(@Param("id") int id);

    public int insert(User user);
}

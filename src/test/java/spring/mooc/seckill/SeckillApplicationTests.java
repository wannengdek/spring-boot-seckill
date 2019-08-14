package spring.mooc.seckill;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring.mooc.seckill.mapper.MiaoshaUserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillApplicationTests {



    @Autowired
    MiaoshaUserMapper miaoshaUserMapper;


    @Test
    public void getById() {
        miaoshaUserMapper.getById(1);
    }

}

package spring.mooc.seckill;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring.mooc.seckill.bean.Employee;
import spring.mooc.seckill.mapper.EmployeeMapper;
import spring.mooc.seckill.mapper.UserMapper;
import spring.mooc.seckill.mapper.UsrMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    UserMapper userMapper;
    @Autowired
    UsrMapper usrMapperl;

    @Test
    public void test() {
        Employee employee = employeeMapper.getEmpById(1);
        System.out.println(employee);
    }

    @Test
    public void contextLoads() {
      userMapper.getById(1);

    }

}

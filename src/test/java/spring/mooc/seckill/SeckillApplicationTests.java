package spring.mooc.seckill;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring.mooc.seckill.bean.Employee;
import spring.mooc.seckill.bean.MiaoshaUser;
import spring.mooc.seckill.mapper.EmployeeMapper;
import spring.mooc.seckill.mapper.MiaoshaUserMapper;
import spring.mooc.seckill.util.MD5Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillApplicationTests {



    @Autowired
    MiaoshaUserMapper miaoshaUserMapper;


    @Test
    public void getById() {
        miaoshaUserMapper.getById(1);
    }



    @Autowired
    EmployeeMapper employeeMapper;
    @Test
    public void insertEmp() {
        Employee employee = new Employee();
        employee.setEmail("1");
        employee.setGender(1);
        employee.setdId(2);
        employeeMapper.insertEmp(employee);
        System.out.println(employee.getdId());
    }



    @Test
    public void insert() {
        List<MiaoshaUser> users = new ArrayList<MiaoshaUser>();
        //生成用户
        for(int i=0;i<10;i++) {
            MiaoshaUser user = new MiaoshaUser();
            user.setId(13000000000L+i);
            user.setLoginCount(1);
            user.setNickname("user"+i);
            user.setRegisterDate(new Date());
            user.setSalt("1a2b3c");
            user.setPassword(MD5Util.inputPassToDbPass("123456"+i, user.getSalt()));
            users.add(user);
        }
        for (int j= 0 ; j <10;j++)
        {
            MiaoshaUser user1 = users.get(j);
            System.out.println(user1.getId()+","+MD5Util.inputPassToFormPass("123456"+j));
            miaoshaUserMapper.insert(user1);
//			System.out.println(user1.getId()+","+MD5Util.inputPassToFormPass("123456"+j)+"--"+MD5Util.inputPassToDbPass("123456"+j, user1.getSalt()));
        }
    }

}

package spring.mooc.seckill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.mooc.seckill.bean.User;
import spring.mooc.seckill.mapper.UserMapper;

@Service
public class UserService {
	
	@Autowired
	UserMapper userMapper;
	
	public User getById(int id) {
		 return userMapper.getById(id);
	}

	@Transactional
	public boolean tx() {
		User u1= new User();
		u1.setId(2);
		u1.setName("2222");
		userMapper.insert(u1);

		User u2= new User();
		u2.setId(1);
		u2.setName("11111");
		userMapper.insert(u2);

		return true;
	}
	
}

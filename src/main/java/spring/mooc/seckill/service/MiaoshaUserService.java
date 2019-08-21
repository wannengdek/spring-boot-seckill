package spring.mooc.seckill.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.mooc.seckill.bean.MiaoshaUser;
import spring.mooc.seckill.exception.GlobalException;
import spring.mooc.seckill.mapper.MiaoshaUserMapper;
import spring.mooc.seckill.redis.MiaoshaUserKey;
import spring.mooc.seckill.redis.RedisService;
import spring.mooc.seckill.result.CodeMsg;
import spring.mooc.seckill.util.MD5Util;
import spring.mooc.seckill.util.UUIDUtil;
import spring.mooc.seckill.vo.LoginVo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@Service
public class MiaoshaUserService {


	public static final String COOKI_NAME_TOKEN = "token";

	@Autowired
	MiaoshaUserMapper miaoshaUserMapper;

	@Autowired
	RedisService redisService;

	public MiaoshaUser getById(long id) {
		//取缓存
		MiaoshaUser user = redisService.get(MiaoshaUserKey.getById, ""+id, MiaoshaUser.class);
		if(user != null) {
			return user;
		}
		//取数据库
		user = miaoshaUserMapper.getById(id);
		if(user != null) {
			redisService.set(MiaoshaUserKey.getById, ""+id, user);
		}
		return user;
	}

	// http://blog.csdn.net/tTU1EvLDeLFq5btqiK/article/details/78693323
	public boolean updatePassword(String token, long id, String formPass) {
		//取user
		MiaoshaUser user = getById(id);
		if(user == null) {
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}
		//更新数据库
		MiaoshaUser toBeUpdate = new MiaoshaUser();
		toBeUpdate.setId(id);
		toBeUpdate.setPassword(MD5Util.formPassToDBPass(formPass, user.getSalt()));
		miaoshaUserMapper.update(toBeUpdate);
		//处理缓存
		redisService.delete(MiaoshaUserKey.getById, ""+id);
		user.setPassword(toBeUpdate.getPassword());
		redisService.set(MiaoshaUserKey.token, token, user);
		return true;
	}


	/**
	 * 得到token  并延长有效期
	 * @param response
	 * @param token
	 * @return
	 */
	public MiaoshaUser getByToken(HttpServletResponse response, String token) {
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
		//判断用户是否为空 不为空则 延长有效期  重新设置一个key
		if(user != null) {
			addCookie(response, token, user);
//			System.out.println("新增cookie ");
		}
		return user;
	}



	public String login(HttpServletResponse response, LoginVo loginVo) {

		if(loginVo == null) {
			throw new GlobalException(CodeMsg.SERVER_ERROR);
			//抛出全局异常
		}
		String mobile = loginVo.getMobile();
		String formPass = loginVo.getPassword();
		//判断手机号是否存在
		MiaoshaUser user = getById(Long.parseLong(mobile));

		if(user == null) {
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}
		//验证密码
		System.out.println(user.getNickname()+"验证密码");
		String dbPass = user.getPassword();
		String saltDB = user.getSalt();
		String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
		if(!calcPass.equals(dbPass)) {
			throw new GlobalException(CodeMsg.PASSWORD_ERROR);
		}
		//生成cookie
		String token = UUIDUtil.uuid();
		addCookie(response, token, user);
		return token;
	}
	private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
		redisService.set(MiaoshaUserKey.token, token, user);
//		System.out.println("添加新的key");
		Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
		cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
		cookie.setPath("/");
		response.addCookie(cookie);
	}

}

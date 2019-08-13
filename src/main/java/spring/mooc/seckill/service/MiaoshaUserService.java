//package spring.mooc.seckill.service;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import spring.mooc.seckill.bean.MiaoshaUser;
//import spring.mooc.seckill.exception.GlobalException;
//import spring.mooc.seckill.mapper.MiaoshaUserDao;
//import spring.mooc.seckill.redis.MiaoshaUserKey;
//import spring.mooc.seckill.redis.RedisService;
//import spring.mooc.seckill.result.CodeMsg;
//import spring.mooc.seckill.util.MD5Util;
//import spring.mooc.seckill.util.UUIDUtil;
//import spring.mooc.seckill.vo.LoginVo;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletResponse;
//
//@Service
//public class MiaoshaUserService {
//
//
//	public static final String COOKI_NAME_TOKEN = "token";
//
//	@Autowired
//	MiaoshaUserDao miaoshaUserDao;
//
//	@Autowired
//	RedisService redisService;
//
//	public MiaoshaUser getById(long id) {
//		return miaoshaUserDao.getById(id);
//	}
//
//
//	public MiaoshaUser getByToken(HttpServletResponse response, String token) {
//		if(StringUtils.isEmpty(token)) {
//			return null;
//		}
//		MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
//		//延长有效期
//		if(user != null) {
//			addCookie(response, token, user);
//		}
//		return user;
//	}
//
//
//	public boolean login(HttpServletResponse response, LoginVo loginVo) {
//		if(loginVo == null) {
//			throw new GlobalException(CodeMsg.SERVER_ERROR);
//		}
//		String mobile = loginVo.getMobile();
//		String formPass = loginVo.getPassword();
//		//判断手机号是否存在
//		MiaoshaUser user = getById(Long.parseLong(mobile));
//		if(user == null) {
//			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
//		}
//		//验证密码
//		String dbPass = user.getPassword();
//		String saltDB = user.getSalt();
//		String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
//		if(!calcPass.equals(dbPass)) {
//			throw new GlobalException(CodeMsg.PASSWORD_ERROR);
//		}
//		//生成cookie
//		String token	 = UUIDUtil.uuid();
//		addCookie(response, token, user);
//		return true;
//	}
//
//	private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
//		redisService.set(MiaoshaUserKey.token, token, user);
//		Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
//		cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
//		cookie.setPath("/");
//		response.addCookie(cookie);
//	}
//
//}

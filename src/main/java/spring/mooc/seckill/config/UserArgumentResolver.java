package spring.mooc.seckill.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import spring.mooc.seckill.bean.MiaoshaUser;
import spring.mooc.seckill.exception.GlobalException;
import spring.mooc.seckill.result.CodeMsg;
import spring.mooc.seckill.service.MiaoshaUserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 接口说明：
     * supportsParameter：用于判定是否需要处理该参数分解，返回true为需要，并会去调用下面的方法resolveArgument。
     * resolveArgument：真正用于处理参数分解的方法，返回的Object就是controller方法上的形参对象。
     */

	@Autowired
	MiaoshaUserService userService;

    /**
     *  判断是否为指定对象
     * @param parameter
     * @return
     */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> clazz = parameter.getParameterType();
		return clazz== MiaoshaUser.class;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
								  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

		String paramToken = request.getParameter(MiaoshaUserService.COOKI_NAME_TOKEN);

		try {
			String cookieToken = getCookieValue(request, MiaoshaUserService.COOKI_NAME_TOKEN);
			//判断是否有cookie
			if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
//				System.out.println("清除cookie 后没有 cookie 返回null");
				return new GlobalException(CodeMsg.SERVER_ERROR);
			}
			String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
//			System.out.println("判断是否有cookie----resolveArgument");
			return userService.getByToken(response, token);
		}catch (Exception e1){
				return new GlobalException(CodeMsg.SERVER_ERROR);
		}
	}

    /**
     * 得到指定的cookie
     * @param request
     * @param cookiName
     * @return
     */
	private String getCookieValue(HttpServletRequest request, String cookiName) {
		Cookie[]  cookies = request.getCookies();
		if(cookies == null || cookies.length<=0)
		{
			return  null;
		}
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals(cookiName)) {
				return cookie.getValue();
			}
		}
		return null;
	}

}

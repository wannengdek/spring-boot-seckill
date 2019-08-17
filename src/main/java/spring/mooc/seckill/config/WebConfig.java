package spring.mooc.seckill.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @author dk
 */
@Configuration
public class WebConfig  extends WebMvcConfigurerAdapter{

	@Autowired
	UserArgumentResolver userArgumentResolver;

	/**
	 * 框架会掉这个方法, 往controller 中赋值
	 *
	 * @param argumentResolvers
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(userArgumentResolver);
	}
}

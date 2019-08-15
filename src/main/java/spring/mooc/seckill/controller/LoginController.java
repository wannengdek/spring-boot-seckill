package spring.mooc.seckill.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import spring.mooc.seckill.redis.RedisService;
import spring.mooc.seckill.result.Result;
import spring.mooc.seckill.service.MiaoshaUserService;
import spring.mooc.seckill.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
    MiaoshaUserService miaoshaUserService;
	
	@Autowired
    RedisService redisService;

	private static Logger log = LoggerFactory.getLogger(LoginController.class);
    
    @RequestMapping(value = "/to_login",method = RequestMethod.GET)
    public String toLogin(Model model) {
        model.addAttribute("name", "Joshua");
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
        log.info(loginVo.toString());
        boolean login = miaoshaUserService.login(response,loginVo);
        return Result.success(true);
    }
}

package spring.mooc.seckill.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import spring.mooc.seckill.redis.RedisService;
import spring.mooc.seckill.result.CodeMsg;
import spring.mooc.seckill.result.Result;
import spring.mooc.seckill.service.MiaoshaUserService;
import spring.mooc.seckill.util.ValidatorUtil;
import spring.mooc.seckill.vo.LoginVo;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
    MiaoshaUserService miaoshaUserService;
	
	@Autowired
    RedisService redisService;


	private static Logger log = LoggerFactory.getLogger(LoginController.class);
    
    @RequestMapping("/to_login")
    public String toLogin(Model model) {
        model.addAttribute("name", "Joshua");
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(LoginVo loginVo) {
        log.info(loginVo.toString());
        String passInput = loginVo.getPassword();
        String mobile =loginVo.getMobile();
        if (StringUtils.isEmpty(passInput))
        {
            return Result.error(CodeMsg.PASSWORD_EMPTY);
        }
        if (StringUtils.isEmpty(mobile))
        {
            return Result.error(CodeMsg.MOBILE_EMPTY);
        }
        if (!ValidatorUtil.isMobile(mobile))
        {
            return  Result.error(CodeMsg.MOBILE_ERROR);
        }
        boolean login = miaoshaUserService.login(loginVo);
        if (login)
        {
            System.out.println("success");
            return Result.success(true);
        }
        else
        {
            return Result.error(CodeMsg.MOBILE_ERROR);
        }
    }





}

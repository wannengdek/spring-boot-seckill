package spring.mooc.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import spring.mooc.seckill.result.CodeMsg;
import spring.mooc.seckill.result.Result;

/**
 * @author : dk
 * @date : 2019/8/13 22:35
 * @description :
 */

@Controller
public class HelloController {
    @RequestMapping("/test")
    @ResponseBody
    public String test(Model model)
    {
        model.addAttribute("d","ss");
        return "index";
    }
    //1.rest api json输出 2.页面
    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello() {
        return Result.success("hello,imooc");
        // return new Result(0, "success", "hello,imooc");
    }

    @RequestMapping("/helloError")
    @ResponseBody
    public Result<String> helloError() {
        return Result.error(CodeMsg.SERVER_ERROR);
        //return new Result(500102, "XXX");
    }

    @RequestMapping("/thymeleaf")
    public String  thymeleaf(Model model) {
        model.addAttribute("name", "Joshua");
        return "hello";
    }
}

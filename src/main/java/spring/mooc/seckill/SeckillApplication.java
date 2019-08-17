package spring.mooc.seckill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("spring.mooc.seckill.mapper")
// 打成 war\包需要配置  extends SpringBootServletInitializer
public class SeckillApplication  {

    public static void main(String[] args) {
        SpringApplication.run(SeckillApplication.class, args);
    }

//    @Override
//    @Bean
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
//    {
//        return builder.sources(SeckillApplication.class);
//    }




    // 打jar包
//public class SeckillApplication  {
//
//    public static void main(String[] args) {
//        SpringApplication.run(SeckillApplication.class, args);
//    }

}

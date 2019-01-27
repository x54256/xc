package cn.x5456.xc.manage_cms;

import cn.x5456.xc.exception.CommonExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * 主启动类
 * @author x5456
 */
// 排除spring的自动装配
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EntityScan("cn.x5456.xc.domain.cms")//扫描CMS的实体类
@Import(CommonExceptionHandler.class)   // 引入全局异常处理
//@ComponentScan(basePackages={"cn.x5456.xc.exception"})
//@ComponentScan(basePackages={"cn.x5456.xc.api"})//扫描接口
//@ComponentScan(basePackages={"cn.x5456.xc.manage_cms"})//扫描本项目下的所有类（只有一个的时候可以不写）
public class ManageCmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsApplication.class,args);
    }
}

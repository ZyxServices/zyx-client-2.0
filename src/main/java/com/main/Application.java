package com.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan("com.*")
@Configuration
@ImportResource(value = {"dubbo-consumer.xml"}) //加入spring的bean的xml文件
public class Application {

    //https://github.com/mybatis/mybatis-spring-boot
    //http://blog.csdn.net/isea533/article/details/50359390
    //https://github.com/abel533/MyBatis-Spring-Boot/blob/master/pom.xml
    //https://github.com/abel533/MyBatis-Spring-Boot
    //http://spring.io/guides/tutorials/spring-boot-oauth2/
    //http://localhost:8080/cities/view/1
    //https://github.com/rajithd/spring-boot-oauth2
    //http://localhost:8080/user/view/1
    public static void main(String[] args) {
//		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("classpath:dubbo-consumer.xml");
//		context.start();
//
//		UserFacade uf= (UserFacade) context.getBean("userFacade");
//		User u=uf.getById((long) 1);
//		System.out.println(u+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");


        SpringApplication.run(Application.class, args);

        //启动后访问这个地址，但是确保zyx-server的Dubbo-privider启动
        //http:localhost:8080/user/view/1

    }

}

package com.zyx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
//http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
//http://blog.csdn.net/catoop/article/details/50668896
//http://blog.csdn.net/jia20003/article/details/50700736
public class SwaggerConfig {


    /**
     * 可以定义多个组，比如本类中定义把test和demo区分开了
     * （访问页面就可以看到效果了）
     */
  /* @Bean
    public Docket testApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("account")
         //       .genericModelSubstitutes(DeferredResult.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/v1/account")//api测试请求地址
                .select()
               // .paths(PathSelectors.regex("/common/.*"))//过滤的接口
                .paths(PathSelectors.regex("/*"))//过滤的接口
                .build()
                .apiInfo(testApiInfo());
    }*/
    
	
	    
   
   
  /*  @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.any()) // 对所有api进行监控
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build();
    }*/
  /*  @Bean
    public Docket shopApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("shop")
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.zyx.controller.shop"))
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build();
    }*/
    @Bean
    public Docket commonApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("common")
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.zyx.controller.common"))
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build();
    }

    @Bean
    public Docket pgApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("pg")
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.zyx.controller.pg"))
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build()
                .apiInfo(pgApiInfo());
    }

    @Bean
    public Docket accountApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("account")
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.zyx.controller.account"))
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build()
                .apiInfo(testApiInfo());
    }

    @Bean
    public Docket activityApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("activity")
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.zyx.controller.activity"))
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build()
                .apiInfo(activityApiInfo());
    }

    @Bean
    public Docket liveApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("live")
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.zyx.controller.live"))
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build()
                .apiInfo(liveApiInfo());
    }

    @Bean
    public Docket collectionApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("collection")
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.zyx.controller.collection"))
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build()
                .apiInfo(collectionApiInfo());
    }


    @Bean
    public Docket systemApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("system")
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.zyx.controller.system"))
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build()
                .apiInfo(systemApiInfo());
    }

    @Bean
    public Docket attentionApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("attention")
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.zyx.controller.attention"))
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build()
                .apiInfo(attentionApiInfo());
    }

    private ApiInfo attentionApiInfo() {

        ApiInfo apiInfo = new ApiInfo("关注接口API",//大标题
                "1、关注相关接口",//小标题
                "1.0",//版本
                "NO terms of service",
                new Contact("魏民升", "http://112.74.112.143:8081/ui/Delta/index.html", "449598723@qq.com"),// 作者
                "体育家",//链接显示文字
                "http://112.74.112.143:8081/ui/Delta/index.html"//网站链接
        );


        return apiInfo;
    }

    private ApiInfo testApiInfo() {
        ApiInfo apiInfo = new ApiInfo("用户接口API",//大标题
                "1、用户收货地址服务API。2、用户公共接口API。3、用户登录相关API。4、用户签到接口API。5、用户注册接口API。6、用户密码修改API。7、用户信息相关接口。",//小标题
                "1.0",//版本
                "NO terms of service",
                new Contact("魏民升", "http://112.74.112.143:8081/ui/Delta/index.html", "449598723@qq.com"),// 作者
                "体育家",//链接显示文字
                "http://112.74.112.143:8081/ui/Delta/index.html"//网站链接
        );

        return apiInfo;
    }

    private ApiInfo activityApiInfo() {
        ApiInfo apiInfo = new ApiInfo("活动API",//大标题
                "活动",//小标题
                "0.1",//版本
                "成都term",
                new Contact("舒子栋", "http://112.74.112.143:8081/ui/Delta/index.html", ""),// 作者
                "体育家",//链接显示文字
                "http://112.74.112.143:8081/ui/Delta/index.html "//网站链接
        );

        return apiInfo;
    }


    private ApiInfo liveApiInfo() {
        ApiInfo apiInfo = new ApiInfo("直播接口API",//大标题
                "图文直播，视频直播",//小标题
                "0.1",//版本
                "成都term",
                new Contact("邓清海", "http://112.74.112.143:8081/ui/Delta/index.html", ""),// 作者
                "智悠行",//链接显示文字
                "http://112.74.112.143:8081/ui/Delta/index.html "//网站链接
        );

        return apiInfo;
    }

    private ApiInfo collectionApiInfo() {
        ApiInfo apiInfo = new ApiInfo("收藏接口API",//大标题
                "收藏",//小标题
                "0.1",//版本
                "成都term",
                new Contact("邓清海", "http://112.74.112.143:8081/ui/Delta/index.html", ""),// 作者
                "智悠行",//链接显示文字
                "http://112.74.112.143:8081/ui/Delta/index.html "//网站链接
        );

        return apiInfo;
    }

    private ApiInfo systemApiInfo() {
        ApiInfo apiInfo = new ApiInfo("系统接口API",//大标题
                "首推，系统",//小标题
                "0.1",//版本
                "成都term",
                new Contact("邓清海", "http://112.74.112.143:8081/ui/Delta/index.html", ""),// 作者
                "智悠行",//链接显示文字
                "http://112.74.112.143:8081/ui/Delta/index.html "//网站链接
        );

        return apiInfo;
    }

    //    this.title = title;
//    this.description = description;
//    this.version = version;
//    this.termsOfServiceUrl = termsOfServiceUrl;
//    this.contact = contact;
//    this.license = license;
//    this.licenseUrl = licenseUrl;
    private ApiInfo pgApiInfo() {
        ApiInfo apiInfo = new ApiInfo("操场Api",//大标题
                "圈子，动态，帖子相关api",//小标题
                "0.1",//版本
                "暂无",
                new Contact("肖伟", "暂无", "xiaowei@perfect-cn.cn"),//作者
                "智悠行",//链接显示文字
                "http://112.74.112.143:8081/ui/Delta/index.html "//网站链接
        );

        return apiInfo;
    }


    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}

> SpringBoot

+ SpringBoot继承的controller处理错误的类 **BasicErrorController**

> SpringMVC - RequestMapping中参数的详解

+ RequestMapping是一个用来处理请求地址映射的注解，可用于类或者方法上。
    - 用在类上，表示类中所有响应请求的方法都是以该地址作为父路径
    
+ RequestMapping注解有六个属性，分成三类进行说明
    - value，method
        * value： 指定请求的实际地址，指定的地址可以是URI Template模式
        * method： 指定请求的Http类型，GET、POST、PUT、DELETE
    - consumes，produces
        * consumes：指定处理请求的提交内容类型（Content-Type），例如application/json，text/html
        * produces：指定返回的内容类型，仅当request请求头中的（Accept）类型中包含指定类型才返回
    - params，headers
        * params：指定request中必须包含某些参数值，才让该方法进行处理
        * headers：指定request中必须包含某些指定的header值，才能让该方法处理请求
        
        
> SpringMVC 拦截机制

+ filter 过滤器 j2ee， `能够拿到原始的http请求和信息；但是没有拿到真实处理请求的信息`
    - 自定义过滤器 implements javax.servlet.Filter，重写其方法，注意在dofilter中调用dofilter执行之后的过滤器
        * 加载到过滤器链上的方法有两种： 
            + 自定义上添加@Component
            + @Configuration @Bean 需要 new FilterRegisterationBean().setFilter(自定义); 还可以声明在哪些URL上生效； 
                返回FilterRegisterationBean
+ interceptor 拦截器 `springmvc提供，无法拦截参数；即可以拿到原始HTTP请求和响应，又可以拿到真实处理请求的类和方法的信息；但是拿不到方法被调用时候的参数值` 
    - 自定义拦截器 implements HandlerInterceptor
    - 使其生效，需要在配置类中继承WebMvcConfigurer 重写 addInterceptor
+ Aspect Filter切片AOP： `解决interceptor无法拦截参数的问题；但是拿不到原始的http请求和信息；`
    - 声明切入点（注解）
        * 在哪些方法上起作用
        * 在什么时候起作用
    - 增强（方法）
        * 起作用时执行的业务逻辑
    - 使用demo
        * @AspectJ声明为切面，@Component注入到springbean中
        * @Around("") 环绕 @see https://docs.spring.io/spring/docs/5.1.1.RELEASE/spring-framework-reference/core.html#aop-pointcuts， 
            定义的方法中参数ProceedingJoinPoint， 可以通过它获取请求参数；pjp.proceed(); 返回我们定义的方法返回的实体
            
+ 拦截机制执行的顺序
    - Filter start
        - Interceptor start
            - ControllerAdvice start
                - Aspect start
                    - Controller start
                    - Controller end
                - Aspect end
            - ControllerAdvice end
        - Interceptor end
    - Filter end
    
> SpringMVC 文件的上传和下载 

+ file.transferTo(anotherFile);
+ commons-io 包下的 IOUtils.copy(inputStream, outputStream); 将inputStream 复制到 outputStream

> 使用swagger自动生成html文档，可以顺便了解下rap

+ 根据代码自动生成html文档
    - 应用相关依赖jar
        * springfox-swagger
        * springfox-swagger-ui 可视化界面
    - 在启动项中添加@EnableSwagger2 
    - 通过注解自定义一些信息
        * @ApiOperation(value = "查询服务") 用在controller方法上
        * 参数为封装类型的：@ApiModelProperty(valur = "") 用在参数字段上，用于字段的说明
        * 参数为基本类型的：在方法的参数前添加@ApiParam(value = "") 


> 使用WireMock快速伪造RESTful服务

+ 独立的服务器 @see http://wiremock.org/docs/running-standalone/ 并启动
+ 添加依赖jar com.github.tomakehurst.wiremock

 
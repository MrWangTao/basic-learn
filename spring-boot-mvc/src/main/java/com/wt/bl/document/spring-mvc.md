> spring mvc init
+ two methods
    - java
        * （implements）`WebApplicationInitializer` class and override method `onStartup`
    - xml
+ Interceptors must implement **HandlerIntercepter** `org.springframework.web.servlet`
+ interface `HandlerExceptionResolver` 
+ `DispatchServlet` use `LocaleResolver` to complete internationalization
+ URI patterns 
    - the syntax {varName:regex} declares a URI variable with a regular expressions. 
    - e.g.
        * @GetMapping("/{name:[a-z-]+}-{version:\\d\\.\\d\\.\\d}{ext:\\.[a-z]+}")
        * public void handle(@PathVariable String version, @PathVariable String ext) {
              // ...
          }
    - @GetMapping(path = "/name", 
        produces="application/json;charset=UTF-8", 
        consumes = "application/json")
        * `produces`: request mapping based on the Accept request header and the list of content types that a controller method produces（返回类型）
        * `consumes`: request mapping based on Content-Type(请求类型)
        * `params`
        * `headers`
        * **tips**： You can match Content-Type and Accept with the headers condition but it is better to use consumes and produces instead.
+ Annotated Controller        
    + Handler Mehtods
        - @MatrixVariable
            * `Note that` you need to enable the use of matrix variables. 
                - In the MVC java config you need to set a UrlPathHelper with removeSemicolonContent via Path Matching.
                - In the MVC XML namespace, user <mvc:annotation-driven enable-matrix-variables="true"/>
        - @RequestParam
            * Method parameters using this annotation are required by default, but you can specify that a method parameter 
            is optional by setting @RequestParam's required flag to false or by declaring the argument with an `java.util.Optional` wrapper.
            * declared as Map<String, String> or MultiValueMap<String, String>, map all requestParams
        - @RequestHeader
            * bind a request header to method argument in a controller
            * declared as Map<String, String> , MultiValueMap<String, String> or HttpHeaders map all header values
            * e.g. @RequestHeader("Accept") may be of type String but also String[] or List<String>
        - @CookieValue
            * bind the value of an HTTP cookie to method argument in a controller
        - @ModelAttribute
        - @SessionAttributes
        - @SessionAttribute
        - @RequestAttribute
        - 重定向过程中传递非URI参数
            * `Redirect attributes` 
                * `RequestMappingHandlerAdapter` 提供了一个`ignoreDefaultModelOnRedirect`，如果controller中的方法是重定向，这个属性会用来
                    表示默认model中的内容从不被使用。我们想要传递这些参数，需要在controller方法中声明`RedirectAttributes`，如果不这样做，没有任何
                    参数会被传递到重定向的视图`RedirectView`
                * 为了保证向后兼容，在MVC namespace 和 MVC java 配置中保留这个标识， 默认值为false
            * `Flash Attributes` flash attributes 被保存在HTTP session中，并且不会再URL中出现 
                * `FlashMap` 用于保存flash属性
                * `FlashMapManager` 用于存储、检索和管理FlashMap实例
                * static method RequestContextUtils ， Spring MVC中通过这个静态方法在任何地方都可以获得FlashMap实例 
                * 在重定向的场景中可以是用@RequestMapping 方法中接收一个RedirectAttributes，用它来添加flash attributes
                * 虽然并不能完全消除并发带来的问题，但是仍然可以通过重定向URL中已有的信息大大减少它。因此，建议主要针对重定向方案使用它
        - Multipart
            * 在我们启用`MultipartResolver`启用后，POST方式请求的"multipart/form-data"格式数据内容被解析并且作为固定的请求参数
            * 在**Servelet 3.0**之后的解析，可以使用`javax.servlet.http.Part`代替 Spring's `Multipart`
            * 可以定义一个实体来接收表单参数，仅仅将对应的Multipart属性定义出来即可
            * 通过RESTful形式的非浏览器客户，我们可以使用@RequestParam来接收成为一个String类型，但是我们想要被反序列化为一个Json，那么在`HttpMessageConverter`转换之后，
                使用@RequestPart来访问multipart
                * @RequestPart可以和`javax.validation.Valid`或者`Spring's Validated`一起使用，用于标准的bean验证
                * 默认的validation errors会产生MethodArgumentNotValidException，转换成400（BAD_REQUEST）响应。
                * **另外** 验证错误可以通过`Errors`或者`BindingResult`参数处理
        - @RequestBody
            * 这个注解读取请求体，通过`HttpMessageConverter`反序列化成一个Object。（可以通过MVC Config 去配置或者自定义消息转换器）、
            * 可以和`javax.validation.Valid`或者`Spring's Validated`一起使用，用于标准的bean验证
            * 默认的validation errors会产生MethodArgumentNotValidException，转换成400（BAD_REQUEST）响应。
            * **另外** 验证错误可以通过`Errors`或者`BindingResult`参数处理
        - HttpEntity
            * 和@RequestBody上使用方式相像，但是它是基于暴露请求头和请求体的容器对象
        - @ResponseBody
            * 使用在方法上，通过`HttpMessageConverter`序列化成返回的响应体，用在Reactive Type上
            * 使用在方法上会被所有的method继承，如：@RestController == @Controller + @ResponseBody
            * 可以通过`MVC Config`的`MessageConverters`属性来进行配置或者自定义消息转换器
            * 可以和 JSON serialization views 一起使用
        - ResponseEntity 
            * 和@ResponseBody上使用方式相像，但是它是基于特定请求头和请求体的容器对象
        - Jackson 
            - JSON
                * SpringMVC 为jackson的序列化视图提供内置支持，允许仅渲染Object中所有字段的子集。
                  与@ResponseBody或者ResponseEntity控制器方法一起使用，需要使用@JsonView批注来激活序列化视图类
                * @JsonView允许一组视图类，但是每个控制器方法只能指定一个，所以如果需要激活多个视图类，需要使用复合接口
            - JsonP
                * @ResponseBody或ResponseEntity要使用JSONP，需要定义一个使用@ControllerAdvice注解并继承`AbstractJsonpResponseBodyAdvice`
                * 对于依赖视图解析的控制器，当请求参数中有名为`jsonp`或`callback`的参数，JSONP会自动启用。通过`jsonpParameterNames`属性可以在自定义这些名称
    + Model Methods 
        - @ModelAttribute
            * 该注解可以用作@RequestMapping的方法参数去创建或者访问一个模型并绑定到请求上；也可以用作方法级别的参数，目的不是处理请求，而是在请求处理之前添加常用的
                模型属性
            * 一个控制器可以有多个@ModelAttribute方法。在@RequestMapping方法之前，所有的这些方法被调用。通过@ControllerAdvice，@ModelAttribute在控制器间可以共享
            * @ModelAttribute 有很灵活的方法签名。和@RequestMapping方法有很多相同的参数，除了@ModelAttribute本身和所有与请求体相关的信息
    + Binder Methods
        - @InitBinder
            * 可以用在@Controller和@ControllerAdvice类中，用于定义基于String类型的请求值（e.g. 请求参数，路径变量，请求头，cookies，其它）的类型转换
            * 也可以用作绑定请求参数到@ModelAttribute 参数（i.e. 命令对象`对象`）的类型转换
    + Exception Methods
        - @ExceptionHandler
            * 用在一个@Controller中，可以被用在同一个控制器中请求处理期间的异常处理
            * @ExceptionHandler 方法也可以有其他参数，比如`HttpServletRequest`, 它的返回值类型可以是被解析成视图名称的String字符串，一个ModelAndView对象，
                ResponseEntity，或者也可以添加@ResponseBody注解
            * 在特定控制器或通知bean的处理程序方法中，根目录异常的优先于匹配当前异常； 但是，较高优先级的@ControllerAdvice上的原因匹配仍然优先于较低优先级
                的通知bean上的任何匹配（无论是根目录还是原因级别）。在使用多层级异常处理机制时，需要将优先级的执行顺序在异常处理器上声明
        - REST API exceptions
    + Controller Advice
        - 一般来讲，@ExceptionHandler，@InitBinder，@ModelAttribute应用在@Controller类中，那么用于@ControllerAdvice or @RestControllerAdvice中，实现全局控制
        - 默认情况下，此注解应用于每一个请求（i.e. 所有的控制器中），但是可以用此注解的属性来缩小控制范围
    + `BindingResult` 数据绑定结果，例如是否存在error等
+ URI Links
    + UriComponents
        - 相比 `java.net.URI`,UriComponents 有一个专用的 `UriComponentsBuilder` 并且支持 URI 模板变量
    + UriBuilder
        - RestTemplate 和 WebClient 都可以用来创建 uri
        - DefaultUriBuilderFactory 是有状态的、可以进行复用的，e.g. 基本的URL
        - UriComponentsBuilder 是无状态的，每个URL都需要一个UriComponentsBuilder
    + URI Encoding
        - 在UriComponents中URI默认的编码方式工作方式如下：
            * URI变量可扩展
            * 每个URI组件进行单独编码
    + Servlet request relative
    + Links to controllers
    + Links in views
+ Async Requests
    + 支持异步返回值的两个返回值类型： `DeferredResult` 和 `Callable`（java.util.concurrent.Callable）
        * DeferredResult 从一个不同的线程中以异步的方式产生返回值，e.g. 响应外部事件（JMS消息），定时任务或者其他
        * Callable,通过配置TaskExecutor执行定时任务返回值
    + Processing
        - Servlet异步请求概述
            * ServletRequest 可以通过调用request.startAsync()实现异步。这样做的作用类似过滤器，可以退出，但是响应会保持打开状态，以便稍后完成处理
            * 调用request.startAsync()返回可以进一步控制异步操作的处理对象`AsyncContext`。 e.g. 它提供了一个类似ServletAPI中请求转发的调度方法，但是
                它允许在Servlet容器线程上的应用恢复请求处理
            * ServletRequest 提供了可以访问当前调度类型的方法，可以用来区分当前初始请求的类型，是异步调度，请求转发还是其他的调度类型
        - DeferredResult Processing
            * 控制器返回`DeferredResult`并且在一些可以访问它的内存队列或者列表中保存它
            * Spring MVC调用request.startAsync()
            * 同时，DispatchServlet 和 所有配置的过滤器退出请求的处理线程，但是响应保持打开
            * 应用在一些线程中设置DeferredResult，并且Spring MVC 调度请求到Servlet容器
            * DispatchServlet被再次调用，并且异步处理恢复的操作产生返回值
        - Callable Processing
            * 控制器返回一个Callable
            * SpringMVC调用request.startAsync(),并提交Callable到任务执行器TashExecutor，在一个分离的线程中处理
            * 同时， DispatchServlet和所有的过滤器退出Servlet容器，但是响应处于打开状态
            * 最后，Callable生成返回结果，SpringMVC调度请求返回Servlet容器去完成处理
            * DispatchServlet被再次调用，并且异步处理恢复的操作从Callable产生返回值
        - Exception Handling
        - Interception
        - SpringMVC compared to WebFlux
            * 和 WebFlux 最主要的区别是 WebFlux 是基于非阻塞I/O
            * SpringMVC 在控制器方法上不支持异步或反映类型， e.g. @RequestBody, @RequestPart，也没有明确支持异步和反映类型作为模型属性
    + HTTP Streaming
        - DeferredResult 和 Callable 可以被用到一个简单的异步返回值。如果我们想要生成多个异步值并且将这些值写入到Response
        - Objects
            * 返回值类型 `ResponseBodyEmitter`
            * `ResponseBodyEmitter` 也可以用作 `ResponseEntity` 中的实体， 允许自定义响应的状态和标头
        - SSEEmitter
            * `ResponseBodyEmitter` 的子类
            *  Internet Explorer 不支持 Server-Sent 事件
        - Raw data 原始数据
            * StreamingResponseBody 返回值
            * 有时绕过消息转换并直接流式传输到响应的OutputStream。e.g.文件下载
            * `StreamingResponseBody` 也可以用作 `ResponseEntity` 中的实体， 允许自定义响应的状态和标头
    + Reactive types
    + Configuration
        - 异步请求处理的特性必须是Servletcontainer级别的启用。MVC 配置也为异步请求暴露了一些配置
        - Servlet 容器
            * 想要启动异步请求处理，需要在Filter 和 Servlet 声明时，将asyncSupported属性设置为true。另外Filter mappings 被声明成处理 
                `javax.servlet.DispatchType` 的 `ASYNC`
            * 在java 配置中，如果使用 `AbstractAnnotationConfigDispatcherServletInitializer` 初始化 Servlet 容器，异步请求自动完成配置
            * 使用web.xml配置，需要在DispatchServlet和Filter 声明时， 添加<async-supported>true</async-supported>， 在filter映射中也要添加
                <dispatcher>ASYNC</dispatcher>
        - Spring MVC
            * java config - 使用WebMvcConfigurer下的configureAsyncSupport回调
            * xml namespace - 使用在`<mvc:annotation-driven>` 下使用<async-support>元素
+ CORS `跨域资源共享`
    + 每个HandlerMapping 基于 CorsConfiguration映射使用URL模式单独进行配置
    + 了解更多更加高级的自定义
        * CorsConfiguration
        * CorsProcessor, DefaultCorsProcessor
        * AbstractHandlerMapping
    + @CrossOrigin
        - 默认情况下
            * 所有的origins
            * 所有的请求头headers
            * 所有映射的控制器方法 HTTP methods
            * 默认情况下allowedCredentials 不被启动，因为如果一旦建立暴露具体用户信息的信任级别，可能会出现cookies泄漏和CSRF ，所以要在适当时候使用
            * maxAge设置为 30 minutes
        - 可以用在class上，也可以用在method上，也可以class和method组合使用
            * 用在class上，所有的methods都会继承
            * 同时使用，也会遵循继承，但是method优先于class
    + 全局配置
        - 默认情况下全局配置支持
            * 所有的origins
            * 所有的请求头headers
            * GET、HEAD 和 POST 方法
            * 默认情况下allowedCredentials 不被启动，因为如果一旦建立暴露具体用户信息的信任级别，可能会出现cookies泄漏和CSRF ，所以要在适当时候使用
            * maxAge设置为 30 minutes
        - java config
            * 实现 **WebMvcConfigurer** 
            * class annotation： @Configuration  @EnableWebMvc
            * method ： addCorsMappings(CorsRegistry corsRegistry)
        - XML config
            * `<mvc:cors>` 
            * `<mvc:mapping/>`
            * `<mvc:cors/>`
    + CORS Filter
        - 内置支持 `CorsFilter`
        - **Spring Security 内置支持CORS**
        - 通过 CorsConfigurationSource 类配filter
+ Web Security
+ HTTP Caching
    + Cache-Control
        - CacheControl 静态方法设置Cache-Control
    + Static resources
        - 实现 `WebMvcConfigurer` 重写 addResourceHandler(ResourceHandlerRegistry registry)
        - xml
            * <mvc:resources mapping="/resources/**" location="/public-resources/">
            *      <mvc:cache-control max-age="3600" cache-public="true"/>
            *  </mvc:resources>
    + @Controller caching
    + ETag Filter
+ View Technologies
    + Thymeleaf和SpringMVC集成（被Thymeleaf工程管理），相关配置Bean有
        * ServletContextTemplateResolver
        * SpringTemplateEngine
        * ThymeleafViewResolver
    + FreeMarker
        * 一个模板引擎，用于生成HTML到电子邮件等的任何类型的文本输出
        * Spring Framework 内置集成SpringMVC和FreeMarker模板
        * 配置方式有 java 配合和xml
    + Groovy MarkUp （配置方法和FreeMarker雷同）
        * 目标是生成类XML标记（e.g. XML，XHTML，HTML5等等），也可以用作生成基于内容的任何形式文本
        * Spring Framework内置集成SpringMVC和Groovy MarkUp
        * 配置方式有 java 配合和xml
        * 和传统的模板引擎不一样，Groovy MarkUp依赖一种DSL（数字模拟语言）的编译语言
    + Script View
        * Spring Framework有一个内置的集成，可以将Spring MVC与任何可以在JSR-223 Java脚本引擎之上运行的模板库一起使用
        * 但是必须要实现`ScriptEngine`、`Invocable`接口
        * **需要继续再看一下**
    + JSP & JSTL
    + Tiles
        * `org.springframework.web.servlet.view.tiles3`
        * 相关类
            * org.springframework.web.servlet.view.tiles3.TilesConfigurer
            * UrlBasedViewResolver or ResourceBundleViewResolver
    + RSS，ATOM
        * `org.springframework.web.servlet.view.feed`
        * 继承自 `AbstractFeedView`， 并且需要重写buildFeedEntries() 和 buildFeedMetadata()
    + PDF, EXCEL  **可以通过model数据很简单的生成PDF文稿或者Excel电子表格**
        * 需要引入 Apache POI 仓库
    + **Jackson**
        * 使用Jackson仓库中的`ObjectMapper`的`MappingJackson2JsonView`使用JSON将内容响应出去
        * `MappingJackson2JsonView` -> `XmlMapper`，如果一个模型包含多个实体，序列化过程需要明确指定bean的属性ModelKey；
                        如果包含单个实体，则会自动序列化
    + XML
        * org.springframework.oxm.`XML Marshaller` -> `MarshallingView`
    + XSTL 
        * XML 的翻译语言
        * `Bean`: @EnableWebMvc @Configuration class注解， implements `WebMvcConfigurer`， @Bean method注解， return XsltViewResolver
        * `Controller`
        * `Transformation`
+ **MVC Config**
    + 启用 MVC 配置
        - java -> @EnableWebMvc
        - xml -> <mvc:annotation-driven/>
    + MVC 配置的 API
        - java 中 实现 `WebMvcConfigurer` 接口
        - xml 配置可以查看 Spring MVC XML schema，查询<mvc:annotation-driven/> 的子元素
    + Type conversion 类型转换
        - 默认情况下 `Number` 和 `Date` 的类型被安装，支持 @NumberFormat 和 @DateTimeFormat 注解
        - Java 中 ，实现 WebMvcConfigurer 接口， 重写addFormatters(FormatterRegistry registry)
        - xml 中，bean `org.springframework.format.support.FormattingConversionServiceFactoryBean`
    + Validation 验证
        - 默认情况下，如果类路径上存在Bean验证，例如 Hibernate Validator，LocalValidatorFactoryBean注册为全局Validator， 用于与控制器方法参数一起使用
            @Valid 和 Validated
        - java 配置
        - xml 配置
        - 需要注册一个本地Validator controller中
    + **Interceptors 拦截器**
        - java配置
        - xml 配置
    + Content Types
    + **Message Converters**
        - 在java配置中，如果你想要替换SpringMVC创建的默认转换器，可以通过重写 configureMessageConverters() 来自定义HttpMessageConverter，如果想要自定义或者
            添加一些转换器，可以通过重写 extendMessageConverters() 
        - This builder customizes Jackson’s default properties with the following ones:
            * `DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES` is disabled.
            * `MapperFeature.DEFAULT_VIEW_INCLUSION` is disabled.
        - 如果下面这些类型在类路径下被检测到，将会自动注册这些模块
            * `jackson-datatype-jdk7`: support for Java 7 types like java.nio.file.Path.
            * `jackson-datatype-joda`: support for Joda-Time types.
            * `jackson-datatype-jsr310`: support for Java 8 Date & Time API types.
            * `jackson-datatype-jdk8`: support for other Java 8 types like Optional.
        - **注意**使用Jackson XML 支持启用缩进除了`woodstox-core-asl`之外还需要`jackson-dataformat-xml`依赖
        - 其他一些Jackson模型
            * jackson-datatype-money: support for javax.money types (unofficial module)，支持钱的类型
            * jackson-datatype-hibernate: support for Hibernate specific types and properties (including lazy-loading aspects) 支持Hibernated的特殊类型和属性
    + View Controller 视图控制器
    + View Resolvers 视图解析器
    + Static Resources 静态资源
    + Default Servlet
        - DefaultServletHttpRequestHandler 默认配置，URL映射到"/**"
    + Path Mapping
    + Advanced Java Config 高级java配置
        - DelegatingWebMvcConfiguration 
            * 提供了默认的SpringMVC配置
            * 检测并委派给WebMvcConfigurer 去定制化配置
            * 自定义的WebMvcConfigurer可以去掉@EnbaleWebMvc，通过DelegatingWebMvcConfiguration直接对配置进行扩展
    + Advanced XML Config
        - MVC namespace没有高级的模型
        - 可以使用Spring ApplicationContext 中的 `BeanPostProcessor` 接口去定制化属性。（implements BeanPostProcessor）
        - **注意**，实现了BeanPostProcessor接口的方法，需要显示在XMl中声明或者通过<component scan />声明检测
+ HTTP/2
    + Servlet API确实公开了一个与HTTP / 2相关的构造。`javax.servlet.http.PushBuilder`可用于主动将资源推送到客户端，并且它作为@RequestMapping方法的方法参数受支持
    
### REST Clients
+ RestTemplate
    + 具有同步API并依赖于阻塞式I/O， 对于具有低并发性的客户端方案，是可以的
+ WebClient
    + 功能齐全，流畅的api，依赖于非阻塞式的I/O，支持高并发。适用于流式场景
    
### Testing
+ 对SpringMVC应用的spring-test总结的可用参数
    + Servlet API Mocks
    + TestContext Framework
    + Spring MVC Test 
        - MockMvc  i.e. 支持注解并使用SpringMVC基础完成测试，没有使用HTTP服务
    + Client-side REST
    + WebTestClient
        - 用于测试WebFlux应用，也可以在一个Http 连接之上对任何服务，使用点对点的集成测试
        - 非阻塞式，响应式并很好的使用测试异步和流的场景
### **WebSockets**
> 包含对Servlet堆栈的支持，包含原始WebSocket消息传递，通过`SockJS`的WebSocket仿真， 以及通过STOMP作为webSocket上的子协议的PUB-SUB（发布-订阅）消息传递
+ 介绍
> WebSocket 协议 RFC 6455 提供了一个标准的方法，通过一个简单的TCP连接在客户端和服务端直接创建一个全双工双向的通讯渠道
> 不同于HTTP的TCP协议，但是设计为使用端口80和443通过HTTP工作，并允许重用现有的防火墙规则
> **低延迟，高频率和高容量的组合**，是使用WebSocket的最佳选择
+ WebSocket API （需要自己配置 @EnableWebSocket 实现 WebSocketConfigurer接口）
    + WebSocketHandler
        - 创建WebSocket和实现WebSocketHandler接口一样简单，或者说更像是扩展TextWebSocketHandler or BinaryWebSocketHandler:
    + WebSocket HandShake
        - 高级参数 RequestUpgradeStrategy
    + Deployment
    + Server config
        - 每一个底层websocket引擎都会暴露一些控制运行期的属性，例如缓存区大小、空闲超时等
        - 对于Tomcat, WildFly, and GlassFish， 配置 `ServletServerContainerFactoryBean` 到我们自己的websocket配置
            * 添加@EnableWebSocket， 实现 WebSocketConfigurer，@bean生成一个ServletServerContainerFactoryBean
            * 设置文本缓冲区和二进制缓存区，`maxTextMessageBufferSize` 和 `maxBinaryMessageBufferSize`
            * 对于客户端的websocket配置，我们应该使用XML中的`WebSocketContainerFactoryBean`或者java配置的`ContainerProvider.getWebSocketContainer()`
        - 对于Jetty
            * 添加@EnableWebSocket， 实现 WebSocketConfigurer，重写registerWebSocketHandlers方法
            * 我们将要定义一个jetty 的 `WebSocketServerFactory` 的bean， 并将其添加到Spring的DefaultHandshakeHandler
        - Allowed origins 允许跨域
            * WebSocket and SockJS 允许的请求源可以进行配置
    + SockJS Fallback
        - SockJS专为在浏览器中使用而设计。它竭尽全力使用各种技术支持各种浏览器版本。有关SockJS传输类型和浏览器的完整列表。
        - 传输分为3大类：WebSocket，HTTP Streaming和HTTP Long Polling(HTTP的长轮询)。
        - Spring 的 WebSocket 和 SockJS不依赖SpringMVC，它可以在SockJsHttpRequestHandler的帮助下很简单的集成到HTTP服务的环境
        - 在浏览器的角度，应用可以使用模拟了W3C WebSocket API，并且可以依赖于它运行的浏览器选择最优的参数和服务端进行沟通的  sockjs-client
        - IE8，9
            * Spring的SockJs支持包含了一个交 sessionCookieNeeded属性，默认情况下是开启的，因为大多数的Java应用依赖于JSESSIONID cookie；
                如果应用不需要它，可以关掉这个属性，而且SockJs client 在IE8，9中应该选择 xdr-streaming
            * Spring Security 3.2+ 提供了在每个响应上设置 `X-Frame-Options`，默认情况下java配置中设置为 DENY；在3.2的Spring Security XML的namespace中不能
                设置这个头信息，但是可以被配置
                * 信息通过iframe框传输，`X-Frame-Options` 的值有 DENY， SAMEORIGIN，和 ALLOW_FROM<origin>
                * 可以用于防止点击劫持
            * 可以通过java配置和XML namespace进行相应配置
    + Heartbeats
    + Client disconnects
        - 在Servlet容器中，是通过Servlet3的异步支持完成的，允许退出处理一个请求的Servlet容器，继续通过另一个线程将信息响应出去
        - 网络IO故障可能仅仅因为客户端已经断开连接而发生，这可能会使用不必要的堆栈跟踪填充日志。Spring尽最大努力识别代表客户端断开连接（特定于每个服务器）
            的此类网络故障，并使用 `AbstractSockJsSession` 中定义的专用日志类别`DISCONNECTED_CLIENT_LOG_CATEGORY`记录最小的信息。如果需要查看堆栈信息，
            请将日志类型设置为`REACE`
    + SockJs 和 CORS
        - 可以通过Spring的SockJsService的suppressCors属性使这些CORS headers失效
        - SockJS 的一些headers
            * "Access-Control-Allow-Origin" - 从"Origin"的请求标头的值初始化
            * "Access-Control-Allow-Credentials" - 通常设置为true.
            * "Access-Control-Request-Headers" - 从设备请求标头的值初始化.
            * "Access-Control-Allow-Methods" - the HTTP methods 传输支持 (see TransportType enum).
            * "Access-Control-Max-Age" - 设置为 31536000 (1 year).
        - 对于具体的实现，需要查看 `AbstractSockJsService` 中的 `addCorsHeaders` 和 源码中的枚举 `TransportType`
        - 另外，如果CORS的配置允许它考虑使用SockJs端点前缀排除URL，使用Spring的`SockJsService` 处理
    + SockJsClient
        - SockJs java Client是为了在没有使用浏览器的远程SockJs端点之间建立连接而提供的。
        - SockJs java client支持"WebSocket"、"xhr-streaming"、"xhr-polling"传输， 其余的只适用于浏览器
        - WebSocketTransport 可以通过以下方式配置
            * 在JSR-356运行期的 `StandardWebSocketClient`
            * Jetty 9+ native WebSocket API的 `JettyWebSocketClient`
            * Spring中 `WebSocketClient` 接口的任何实现类
        - 根据定义 XhrTransport 支持 "shr-streaming" 和 "xhr-polling"，因为从客户端的角度来看，除了用于连接服务器的URL没有其他区别。目前有两种实现方法
            * 对于HTTP 请求，使用Spring 的RestTemplate的实现类 `RestTemplateXhrTransport`
            * 对于HTTP请求，使用Jetty中的HttpClient的实现类 `JettyXhrTransport`   **用于高并发**
+ STOMP
    + 概括
        - 面向文本的协议
        - 可以点对点，也可以一对多
    + 优势
        - 如何是Spring MVC 和其他的web结构也可以提供丰富的功能呢
            * 没有必要自定义消息协议和消息格式
            * 可以使用STOMP的客户端，其中包括Spring Framework中的java客户端
            * 诸如 RabbitMQ、ActiveMQ等等，可以用来管理订阅和广播消息
            * 应用程序逻辑可以根据STOMP目标标头和使用单个WebSocketHandler为给定连接处理原始WebSocket消息，以任意数量的@Controller和消息路由到它们。
            * 使用SpringSecurity根据STOMP的目标和消息类型保护消息
    + 启动STOMP enable STOMP
        - 基于WebSocket支持的STOMP在spring-messaging和spring-websocket模型中是可以使用的。一旦使用这些依赖，我们可以通过带有`SockJs FallBack`的WebSocket暴露STOMP端点
        - java配置
            * 使用class annotation @Configuration 和 @EnableWebSocketMessageBroker
            * 实现 WebSocketMessageBrokerConfigurer接口
            * 重写 registerStompEndpoints(StompEndPointRegister registry) 和 configureMessageBroker(MessageBrokerRegistry config)
        - xml namespace 与java配置同理
            * <websocket:message-broker application-destination-prefix="/app">
            *     <websocket:stomp-endpoint path="/portfolio">
            *         <websocket:sockjs/>
            *     </websocket:stomp-endpoint>
            *     <websocket:simple-broker prefix="/topic, /queue"/>
            * </websocket:message-broker>
        - 对于来自浏览器的连接，可以使用sockjs-client；对于STOMP，很多应用程序使用的jmesnil/stomp-websocket library (also known as stomp.js)，但是目前已经不再维护了；
            目前，积极维护并且不断发展的是JSteunou / webstomp-client
    + Flow of Messages 信息流
        - spring-messaging模块包含了对源于SpringIntegration信息应用的基础支持，后期被提取并整合到Spring Framework中， 以便在Spring项目和应用程序场景中得到广泛的应用
        - 可用的消息抽象类
            * Message ：对包含头和有效负载信息的简单呈现
            * MessageHandler ：消息处理
            * MessageChannel ：发送消息的渠道，支持生产者和消费者之间的松耦合
            * SubscribableChannel ： 有MessageHandler订阅者的MessageChannel
            * ExecutorSubscribableChannel ： 对于传递消息的执行器
        - **更多消息：包括流程图和介绍需查看官方文档**
    + Handler methods
        - @Controller类的方法支持@MessageMapping注释。它可以用于将方法映射到消息目标，也可以与类型级@MessageMapping结合使用，以表示控制器中所有带注释方法的共享映射。
        - 默认情况下，目的映射被看做 ant风格和斜杠分割的路径模式，如：/foo*, /foo/** 等等。它们还可以包含模板变量，例如“/ foo / {id}”然后可以通过@ DestinationVariable-annotated方法参数引用。
        - @MessageMapping方法支持以下方法参数：
            * 消息方法参数，用于访问正在处理的完整消息。
            * @ Payload-annotated参数，用于访问消息的有效负载，使用org.springframework.messaging.converter.MessageConverter转换。**由于默认情况下采用注释，因此不需要存在注释**。使用验证注释（如@Validated）注释的Payload方法参数将受JSR-303验证。
            * @Header-annotated参数，用于访问特定标头值以及使用org.springframework.core.convert.converter.Converter进行类型转换（如有必要）。
            * @ Headers-annotated方法参数，必须也可以赋值给java.util.Map，以便访问消息中的所有头文件。
            * MessageHeaders方法参数，用于访问所有标头的映射。
            * MessageHeaderAccessor，SimpMessageHeaderAccessor或StompHeaderAccessor，用于通过类型化访问器方法访问标头。
            * @ DestinationVariable-annotated arguments用于访问从消息目标中提取的模板变量。必要时，值将转换为声明的方法参数类型。
            * java.security.Principal方法参数，反映在WebSocket HTTP握手时登录的用户。
        - 消息级别的注解 @SendTo 可以用作指定任何其他目的地。也可以设置在类级别实现共享目的地
        - 可以通过ListenableFuture或CompletableFuture / CompletionStage返回类型签名异步提供响应消息，类似于MVC处理程序方法中的延迟结果
        - @SubscribeMapping注释可用于将订阅请求映射到@Controller方法。它在方法级别上受支持，
            但也可以与类型级别@MessageMapping批注结合使用，该批注表示同一控制器中所有消息处理方法的共享映射。
        - 默认情况下，@ SubsscribeMapping方法的返回值作为消息直接发送回连接的客户端，并且不通过代理。这对于实现请求 - 回复消息交互很有用;例如，在初始化应用程序UI时获取应用程序数据。
            或者，也可以使用@SendTo注释@SubscribeMapping方法，在这种情况下，使用指定的目标目标将结果消息发送到“brokerChannel”。
        - 在某些情况下，控制器可能需要在运行时使用AOP代理进行修饰。例如，如果您选择在控制器上直接使用@Transactional注释。在这种情况下，对于控制器而言，我们建议使用基于类的代理。
            这通常是控制器的默认选择。但是，如果控制器必须实现不是Spring Context回调的接口（例如InitializingBean，* Aware等），则可能需要显式配置基于类的代理。
            例如，使用<tx：annotation-driven />，更改为<tx：annotation-driven proxy-target-class =“true”/>。
    + Send Messages
        - 任何应用组件都可以发送消息至`brokerChannel`。最简单的方式就是注入 `SimpMessagingTemplate`
    + Simple Broker
        - 内置、简单的消息中间件处理来自客户的订阅请求，存储它们到内存，并广播消息到对应目标的连接中客户端
        - 这个中间件支持的目标模式： path-like，ant风格
    + External Broker 外部中间件， e.g. RabbitMQ,ActiveMQ
        - 简单的中间件对于刚开始的学习比较有用，它只支持STOMP命令的子集，依赖于简单消息的循环发送，不适用于集群。
        - java 配置，Spring配置来启动STOMP中间件转接
            * class annotations @Configuration @EnableWebSocketMessageBroker
            * 实现WebSocketMessageBrokerConfigurer
            * 重写 registerStompEndpoint(StompEndpoingRegistry registry) , configureMessageBroker(MessageBrokerRegistry registry)
        - XML namespace
            - 同理与java配置
        - 为了实现将消息转发到外部消息中间件，需要和中间件建立TCP连接，通过WebSocket session 将所有的信息从中间件发送给客户端。对于TCP连接的管理来说，
            需要引入的依赖有 `org.projectreactor:reactor-net` 和  `io.netty:netty-all`
    + Connect to Broker 连接到中间件
    + Dot as Separator 点作为分割符
    + Authentication 认证
    + Token Authentication token认证
    + User Destinations  用户目标
    + Events and Interception 事件和拦截器
        - BrokerAvailablityEvent： 表示中间件何时可用何时不可用
        - SessionConnectEvent：在收到指示新客户端会话开始的新STOMP CONNECT时发布。该事件包含表示连接的消息，包括会话ID，用户信息（如果有）以及客户端可能已发送的任何自定义标头。
            这对于跟踪客户端会话很有用。
        - SessionConnectedEvent： 中间件发送STOMP CONNECTED框架响应一个CONNECT，发布一个精简版。在SessionConnectEvent之后，创建完整版STOMP session
        - SessionSubscribeEvent  - 在收到新的STOMP SUBSCRIBE时发布。
        - SessionUnsubscribeEvent  - 在收到新的STOMP UNSUBSCRIBE时发布。
        - SessionDisconnectEvent  - 在STOMP会话结束时发布。 DISCONNECT可能已从客户端发送，也可能在WebSocket会话关闭时自动生成。在某些情况下，每个会话可能会多次发布此事件。
            对于多个断开连接事件，组件应该是幂等的。
        - 使用功能齐全的代理时，STOMP“代理中继”会自动重新连接“系统”连接，以防代理暂时不可用。
            但是，客户端连接不会自动重新连接。假设启用了心跳，客户端通常会注意到代理在10秒内没有响应。客户端需要实现自己的重新连接逻辑。
            * class annotations @Configuration @EnableWebSocketMessageBroker
            * 实现WebSocketMessageBrokerConfigurer
            * 重写 configureClientInboundChannel(ChannelRegistration registration){registration.setInterceptors(new MyChannelInterceptor());}
            * new MyChannelInterceptor()  继承自 `ChannelInterceptorAdapter` 重写 preSend(Message<?> message, MessageChannel channel)
    + STOMP client 客户端
        - Spring提供的STOMP基于WebSocket客户端，和基于TCP客户端两种
        - STOMP协议支持receipt
            * StompSession 提供了 setAutoReceipt(boolean)，在每个发送和订阅上添加receipt Header
            * 手动添加StompHeaders
            * 这两者在发送和订阅中会返回一个用于返回是够注册成功的一个回调 `Receiptable`
            * **注意** 为了支持这个特性，客户端必须配置一个 TaskScheduler和receipt过期前的时间，默认是15s
    + WebSocket Scope 应用范围
    + Performance 性能
        - 实际上WebSocket服务施加了信息大小的限制，e.g. tomcat是8K，Jetty是16K
            * 使用缓冲区可以解决信息量过大的问题
        - 基于WebSocket支持的Spring STOMP可以配置STOMP信息的最大值，不管WebSocket服务器指定的信息大小
        - 简单的中间件不可能完成多个应用实例的缩放，然而使用全特性的中间件 e.g. RabbitMQ可以
    + Monitoring 监测
        - @EnableWebSocketMessageBroker or <websocket:message-broker> 
            * 使用这个基础组件会自动收集统计重要信息，提供深入了解应用的内部状态。
            * 这个配置也声明一个可以在一个地方收集所有有用信息的类WebSocketMessageBrokerStats，默认每30min记录一次INFO日志
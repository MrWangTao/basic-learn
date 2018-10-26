@see https://springcloud.cc/spring-cloud-dalston.html#_spring_cloud_stream
### Spring Cloud Stream
+ 介绍
    + Spring Cloud Stream是构建消息驱动的微服务应用程序的框架。Spring Cloud Stream基于Spring Boot建立独立的生产级Spring应用程序，
        并使用Spring Integration提供与消息代理的连接。
    + 提供了来自几家供应商的中间件的意见配置，介绍了持久发布订阅语义，消费者组和分区的概念
+ 使用
    + @EnableBinding 应用于应用程序的启动项上，以便立即连接到消息代理， 可以用来构造器上用来绑定该类
    + @StreamListener 添加在方法上，使得该方法接收流处理的事件
    + Spring Cloud Stream 提供了接口 Source`输出通道`、Sink`输入通道`、Processor`响应式发布-订阅者`
    + 自定义**接口**， @Input注释用于方法上，标识输入通道， 通过该通道接收到的消息进入到应用程序；@Output注释用于接口的方法上，标识输出
        通道，发布的消息将通过该通道离开应用程序。**注意**@Input和@Output注释可以使用`频道名称`作为参数；如果没有提供，将使用注释方法
        的名称
        
### 主要概念
+ 应用模型
    + 一个Spring Cloud Stream应用程序由一个中间件中立的核心组成。 
        该应用程序通过Spring Cloud Stream注入到其中的输入和输出通道与外界进行通信
        渠道通过中间件`特定的Binder`实现连接到外部代理，中间件会对Binder抽象根据自身特性来完成实现
+ Binder抽象
    + Spring Cloud Stream为Kafka和Rabbit MQ提供Binder实现
    + Binder抽象使得Spring Cloud Stream可以灵活地连接到中间件
    + Spring Boot配置中，如：spring.cloud.stream.binding.<input/output channel>.destination: value
        - <input/output channel> 输入或输出通道
        - value Kafka主题或者RabbitMQ交换
    + Spring Cloud Stream自动检测并使用类路径中找到binder。可以在应用程序中打包多个绑定器，并在运行时选择绑定器。（如何实现？）
+ 持续发布-订阅支持
    + 应用之间的通信遵循发布订阅模式，通过共享广播数据
    + 通过共享主题而不是点对点队列进行所有通信可以减少微服务之间的耦合
+ 消费者组
    + 通过创建给定应用程序的多个实例来扩展的能力同样重要，应用程序的不同实例被放置在竞争的消费者关系中，并且只有一个消费者预期处理给定的消息。
    + Spring Cloud Stream通过消费者组来模拟此行为（与Kafka中的消费者组类似）
        - 每个`消费者`可以使用spring.cloud.stream.bindings.<channel-name>.group: <group-name>属性来指定组名称 **？？存在疑问？？**
    + 订阅给定目标的所有组都会受到已发布数据的副本，但是每个组中只有一个成员从该目的地接收给定消息。默认情况下，当未指定组时，Spring Cloud
        Stream 将应用程序分配给所有消费者组发布-订阅关系的匿名独立单个成员消费者组
    + 消费者类型
        - Message-driven 消息驱动，可以理解为异步处理
        - Polled轮询，可以看做同步
+ 持久化
    + 消费者组的订阅是持久的。也就是说，绑定实现确保组预订是持久的，一旦已经创建了一个组至少一个订阅，即使组中的所有应用程序都被停止，组也将
        接收消息
    + **注意**匿名订阅本质是不持久的。对于某些bingder（如：Rabbitmq），可以具有非持久组的订阅
    + 通常，当将应用绑定到目的地时，最好始终指定消费者组。在扩展Spring Cloud Stream应用程序时，必须为每个输入绑定指定一个使用者组。这样可
        以防止应用程序的实例收到重复的消息。**？？？**
+ 分区支持
    + Spring Cloud Stream提供对给定应用程序的多个实例之间的`分区数据`的支持。
        - **理解** 一个或多个生产者应用程序实例将数据发送到多个消费者应用程序实例，并确保由`共同特征标识的数据`由相同的消费者实例处理
    + 分区是状态处理中的一个关键概念
    
### 编程模型
+ 生产和消费消息
    + 本地Spring Integration支持
    + Spring Integration支持Spring Cloud Stream提供自己的@StreamListener注释，以及Spring消息传递的注释，e.g. @MessageMapping，
        @JmsListener，@RabbitListener。 @StreamListener注释提供了一种更简单的处理入站邮件的模型，**特别是处理涉及内容类型管理和类型强制
        的用例时**。
    + Spring Cloud Stream提供了一种可扩展的MessageConverter机制，用于通过绑定通道处理数据转换，并且在这种情况下，将调度到使用@StreamListener
        注释的方法。
    + 使用@StreamListener将消息分派到多个方法
        - Spring Cloud Stream 支持根据条件向在输入通道上注册的多个@StreamListener方法发送消息
        - 有条件调度的资格，必须满足
            * 不能有返回值
            * 必须是一个单独的消息处理方法（不支持）
        - 条件通过注释的condition属性中的SpEL表达式指定，并为每个消息进行评估。匹配条件的所有处理程序将在同一个线程中被调用，并且不必对调用发生
            的顺序做出假设。在发送事件的时候添加headers在接受的时候配置上对应的条件即可。`暂时理解为参数类型`
        - **注意**通过@StreamListener条件进行调度仅对单个消息的处理程序支持，不适用反应式处理程序
    + 反应式编程(对java的要求，需要8+)
        - Spring Cloud Stream支持使用反应性API，其中将传入和传出的数据作为连续数据流处理。通过spring-cloud-stream-reactive提供对反应性Api的
            的支持，需要将其添加到项目依赖中
        - 具有反应性API的编程模型是声明式的，不是指定如何处理每个单独的消息，可以使用描述从入站到出站数据流的功能转换的运算符
        - 反应式编程模型还使用@StreamListener注释来设置反应处理程序。差异在于：
            * @StreamListener注释不能指定输入或输出，因为它们作为参数提供，并从方法返回值;
            * 必须使用@Input和@Output注释方法的参数，指示输入和分别输出的数据流连接到哪个输入或输出;
            * 方法的返回值（如果有的话）将用@Output注释，表示要发送数据的输入。